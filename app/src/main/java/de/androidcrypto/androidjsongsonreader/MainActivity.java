package de.androidcrypto.androidjsongsonreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

public class MainActivity extends AppCompatActivity {

    // gson: https://mvnrepository.com/artifact/com.google.code.gson/gson
    // tutorial: https://camposha.info/android-examples/android-gson/#gsc.tab=0

    Button readWriteSimpleJson, readYahooApiJson, readDynamicJson,
            readYahooDynamicJson, yahooApi, generateSimpleJson,
            grabJson, grabYahooJson, storeYahooApiKey, loadYahooApiKey
            ;

    TextView data;

    String jsonString = "{\"XDEW.DE\":{\"timestamp\":[1646985600,1647621384],\"symbol\":\"XDEW.DE\",\"previousClose\":null,\"chartPreviousClose\":72.13,\"end\":null,\"start\":null,\"close\":[72.13,73.67],\"dataGranularity\":300}}";
    String jsonStringMo = "{\"XDEW.DE\":{\"timestamp\":[1645171200,1645430400,1645516800,1645603200,1645689600,1645776000,1646035200,1646121600,1646208000,1646294400,1646380800,1646640000,1646726400,1646812800,1646899200,1646985600,1647621384],\"symbol\":\"XDEW.DE\",\"chartPreviousClose\":70.83,\"close\":[70.83,70.33,70.49,69.91,69.15,71.4,71.94,71.33,72.54,72.76,72.67,72.8,71.34,71.5,70.94,72.13,73.67],\"dataGranularity\":300,\"previousClose\":null,\"end\":null,\"start\":null}}";
    String jsonStringMo2Symbols = "{\"XDWD.DE\":{\"symbol\":\"XDWD.DE\",\"timestamp\":[1645171200,1645430400,1645516800,1645603200,1645689600,1645776000,1646035200,1646121600,1646208000,1646294400,1646380800,1646640000,1646726400,1646812800,1646899200,1646985600,1647621379],\"close\":[79.792,78.874,79.034,78.56,77.532,80.018,80.554,79.944,80.86,80.574,79.796,79.464,77.958,78.966,78.268,79.24,82.242],\"end\":null,\"start\":null,\"dataGranularity\":300,\"previousClose\":null,\"chartPreviousClose\":79.792},\"XDEW.DE\":{\"symbol\":\"XDEW.DE\",\"timestamp\":[1645171200,1645430400,1645516800,1645603200,1645689600,1645776000,1646035200,1646121600,1646208000,1646294400,1646380800,1646640000,1646726400,1646812800,1646899200,1646985600,1647621384],\"close\":[70.83,70.33,70.49,69.91,69.15,71.4,71.94,71.33,72.54,72.76,72.67,72.8,71.34,71.5,70.94,72.13,73.67],\"end\":null,\"start\":null,\"dataGranularity\":300,\"previousClose\":null,\"chartPreviousClose\":70.83}}";

    List<PriceList> csvList = new ArrayList<>();
    String[] csvHeaderPrices = {"timestamp", "close"};

    String apiKeyFromSharedPreferences = "";

    Intent storeYahooApiKeyIntent;
    
    // https://developer.android.com/training/data-storage/shared-preferences#java

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        generateSimpleJson = findViewById(R.id.btnGenerateSimpleJson);
        readYahooApiJson = findViewById(R.id.btnReadYahooApiJson);
        readWriteSimpleJson = findViewById(R.id.btnReadWriteSimpleJson);
        yahooApi = findViewById(R.id.btnYahooApi);
        readDynamicJson = findViewById(R.id.btnReadDynamicJson);
        readYahooDynamicJson = findViewById(R.id.btnReadYahooDynamicJson);
        grabJson = findViewById(R.id.btnGrabJson);
        grabYahooJson = findViewById(R.id.btnGrabYahooJson);
        storeYahooApiKey = findViewById(R.id.btnStoreYahooApiKey);
        loadYahooApiKey = findViewById(R.id.btnLoadYahooApiKey);

        data = findViewById(R.id.tvSimpleJson);

        storeYahooApiKeyIntent = new Intent(MainActivity.this, StoreYahooApiKey.class);

        // be careful with these 2 lines
        // https://stackoverflow.com/a/9289190/8166854
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        storeYahooApiKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(storeYahooApiKeyIntent);
            }
        });

        loadYahooApiKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                String key = sharedPref.getString(getString(R.string.yahoo_api_key), "");
                System.out.println("key: " + key);
                data.setText(key);
                apiKeyFromSharedPreferences = key;
            }
        });

        grabYahooJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("*** grab Yahoo JSON ***");

                csvList.clear();
                csvList.add(new PriceList("date", "timestamp", "closePrice"));


                Type listType = new TypeToken<Map<String, Object>>() {
                }.getType();
                Gson gson = new Gson();
                // 1 symbol
                //Map<String, Object> myList = gson.fromJson(jsonStringMo, listType);
                // 2 symbols
                Map<String, Object> myList = gson.fromJson(jsonStringMo2Symbols, listType);

                // first element is dynamic = Yahoo symbol name, e.g. XDEW.DE
                for (Map.Entry<String, Object> m : myList.entrySet()) {
                    System.out.println("==============");
                    if (m.getValue() instanceof String) {
                        // get String value
                    } else { // if value is an Object
                        // m.getValue().toString() contains the data
                        System.out.println("Symbol Name: " + m.getKey());
                        String jsonString1 = m.getValue().toString();
                        System.out.println("jsonString1: " + jsonString1);
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = new JSONObject(jsonString1);
                            String name = jsonObj.getString("symbol");
                            System.out.println("symbol: ");
                            System.out.println(name);

                            //String timestamp = jsonObj.getString("timestamp");
                            //System.out.println("timestamp");
                            JSONArray arrTimestamp = jsonObj.getJSONArray("timestamp");
                            System.out.println("timestamp entries: " + arrTimestamp.length());
                            //System.out.println("closePrice");
                            JSONArray arrClosePrice = jsonObj.getJSONArray("close");
                            System.out.println("closePrice entries: " + arrClosePrice.length());
                            if (arrTimestamp.length() != arrClosePrice.length()) {
                                System.out.println("ungleiche Anzahl von timestamps und closePrices");
                                System.out.println("Einlesen nicht möglich");
                                return;
                            }
                            for (int i = 0; i< arrTimestamp.length(); i++){
                                //System.out.println("i: " + i);
                                Long ts = Long.valueOf(0);
                                ts = arrTimestamp.getLong(i);
                                //System.out.println(String.valueOf(ts));
                                Double cp = Double.valueOf(0);
                                cp = arrClosePrice.getDouble(i);
                                //System.out.println(String.valueOf(cp));
                                String date = unixDateToDate(String.valueOf(ts));
                                PriceList csvRecord = new PriceList(date ,String.valueOf(ts), String.valueOf(cp));
                                csvList.add(csvRecord);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
                System.out.println("csvList entries: " + csvList.size());
                for (int i = 0; i < csvList.size(); i++) {
                    System.out.println("i: " + i +
                            " dt: " + csvList.get(i).getDate() +
                            " ts: " + csvList.get(i).getDateUnix() +
                            " cp: " + csvList.get(i).getClosePrice());
                }
            }
        });

        grabJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("*** grab JSON ***");
                // ist in der lage ein yahoo json mit mehreren symbols einzulesen
                // aktuell werden alle werte hintereinander in die csvList geschrieben
                // wichtig zur nutzung: separate dateien für jedes symbol
                Type listType = new TypeToken<Map<String, Object>>() {
                }.getType();
                Gson gson = new Gson();
                Map<String, Object> myList = gson.fromJson(jsonStringMo, listType);

                // first element is dynamic = Yahoo symbol name, e.g. XDEW.DE
                for (Map.Entry<String, Object> m : myList.entrySet()) {
                    System.out.println("==============");
                    if (m.getValue() instanceof String) {
                        // get String value
                    } else { // if value is an Object
                        // m.getValue().toString() contains the data
                        System.out.println("Symbol Name: " + m.getKey());
                        String jsonString1 = m.getValue().toString();
                        System.out.println("jsonString1: " + jsonString1);
                        JSONObject jsonObj = null;
                        try {
                            jsonObj = new JSONObject(jsonString1);
                            String name = jsonObj.getString("symbol");
                            System.out.println("symbol: ");
                            System.out.println(name);

                            //String timestamp = jsonObj.getString("timestamp");
                            System.out.println("timestamp");
                            JSONArray arrTimestamp = jsonObj.getJSONArray("timestamp");

                            for (int i = 0; i< arrTimestamp.length(); i++){
                                System.out.println("i: " + i);
                                Long ts = Long.valueOf(0);
                                ts = arrTimestamp.getLong(i);
                                System.out.println(String.valueOf(ts));
                            }

                            System.out.println("closePrice");
                            JSONArray arrClosePrice = jsonObj.getJSONArray("close");
                            for (int i = 0; i< arrClosePrice.length(); i++){
                                System.out.println("i: " + i);
                                Double ts = Double.valueOf(0);
                                ts = arrClosePrice.getDouble(i);
                                System.out.println(String.valueOf(ts));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }
            }
        });

        readYahooDynamicJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("read dynamic JSON");
                //String jsonString = "{\"XDEW.DE\":{\"timestamp\":[1646985600,1647621384],\"symbol\":\"XDEW.DE\",\"previousClose\":null,\"chartPreviousClose\":72.13,\"end\":null,\"start\":null,\"close\":[72.13,73.67],\"dataGranularity\":300}}";
                String jsonString = jsonStringMo2Symbols; //
                Type listType = new TypeToken<Map<String, Object>>() {
                }.getType();
                Gson gson = new Gson();
                Map<String, Object> myList = gson.fromJson(jsonString, listType);

                JsonParser parser = new JsonParser();

                for (Map.Entry<String, Object> m : myList.entrySet()) {
                    System.out.println("==============");
                    if (m.getValue() instanceof String) {
                        // get String value
                    } else { // if value is an Object

                        System.out.println("VIP Sec: Name: " + m.getKey());
                        Map<String, Object> myList1 = gson.fromJson(m.getValue().toString(), listType);
                        for (Map.Entry<String, Object> m1 : myList1.entrySet()) {
                            if (!(m1.getValue() instanceof String)) {
                                Map<String, Object> myList2 = gson.fromJson(m1.getValue().toString(), listType);
                                for (Map.Entry<String, Object> m2 : myList2.entrySet()) {
                                    if (!(m2.getValue() instanceof String)) {
                                        Map<String, Object> myList3 = gson.fromJson(m2.getValue().toString(), listType);
                                        for (Map.Entry<String, Object> m3 : myList3.entrySet()) {
                                            if (m3.getKey().equals("virtualAddresses")) {
                                                System.out.println("VIP Sec: IP Address: " + m3.getValue());
                                            } else if (m3.getKey().equals("pool")) {
                                                System.out.println("Pool Sec: Name: " + m3.getValue());
                                            } else if (m3.getKey().equals("monitors")) {
                                                JsonArray monitors = parser.parse(m3.getValue().toString()).getAsJsonArray();
                                                int count = 0;
                                                while (count < monitors.size()) {
                                                    String monitor = monitors.get(count).getAsString();
                                                    System.out.println("Monitor: " + monitor);
                                                    count++;
                                                }
                                            } else if (m3.getKey().equals("members")) {
                                                JsonArray members = parser.parse(m3.getValue().toString()).getAsJsonArray();
                                                int count = 0;
                                                while (count < members.size()) {
                                                    // Parsing as Object to key values by key directly
                                                    JsonObject mem = members.get(count).getAsJsonObject();
                                                    String port = mem.get("servicePort").getAsString();
                                                    System.out.println("Port: " + port);
                                                    JsonElement ipAddrs = mem.get("serverAddresses");
                                                    if (ipAddrs.isJsonArray()) {
                                                        JsonArray ips = ipAddrs.getAsJsonArray();
                                                        int c = 0;
                                                        while (c < ips.size()) {
                                                            String ip = ips.get(c).getAsString();
                                                            System.out.println("IP: " + ip);
                                                            c++;
                                                        }
                                                    }
                                                    count++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        });

        readDynamicJson.setOnClickListener(new View.OnClickListener() {
            // https://ashishontech.xyz/read-dynamic-object-from-json-using-gson/
            @Override
            public void onClick(View v) {
                System.out.println("read dynamic JSON");
                String jsonString = "{\n" +
                        "    \"Sample_01\": {\n" +
                        "        \"class\": \"Tenant\",\n" +
                        "        \"A1\": {\n" +
                        "            \"class\": \"Application\",\n" +
                        "            \"template\": \"http\",\n" +
                        "            \"serviceMain\": {\n" +
                        "                \"class\": \"Service_HTTP\",\n" +
                        "                \"virtualAddresses\": [\n" +
                        "                    \"10.0.1.10\"\n" +
                        "                ],\n" +
                        "                \"pool\": \"web_poolddd\"\n" +
                        "            },\n" +
                        "            \"web_poolddd\": {\n" +
                        "                \"class\": \"Pool\",\n" +
                        "                \"monitors\": [\n" +
                        "                    \"http\"\n" +
                        "                ],\n" +
                        "                \"members\": [\n" +
                        "                    {\n" +
                        "                        \"servicePort\": 80,\n" +
                        "                        \"serverAddresses\": [\n" +
                        "                            \"192.0.13.10\",\n" +
                        "                            \"192.0.14.11\"\n" +
                        "                        ]\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"Sample_20\": {\n" +
                        "        \"class\": \"Tenant\",\n" +
                        "        \"A1\": {\n" +
                        "            \"class\": \"Application\",\n" +
                        "            \"template\": \"http\",\n" +
                        "            \"serviceMain\": {\n" +
                        "                \"class\": \"Service_HTTP\",\n" +
                        "                \"virtualAddresses\": [\n" +
                        "                    \"10.2.2.2\"\n" +
                        "                ],\n" +
                        "                \"pool\": \"web_pool_data\"\n" +
                        "            },\n" +
                        "            \"web_pool_data\": {\n" +
                        "                \"class\": \"Pool\",\n" +
                        "                \"monitors\": [\n" +
                        "                    \"http\"\n" +
                        "                ],\n" +
                        "                \"members\": [\n" +
                        "                    {\n" +
                        "                        \"servicePort\": 80,\n" +
                        "                        \"serverAddresses\": [\n" +
                        "                            \"192.0.10.10\",\n" +
                        "                            \"192.0.10.11\"\n" +
                        "                        ]\n" +
                        "                    }\n" +
                        "                ]\n" +
                        "            }\n" +
                        "        }\n" +
                        "    }\n" +
                        "}";
                Type listType = new TypeToken<Map<String, Object>>() {
                }.getType();
                Gson gson = new Gson();
                Map<String, Object> myList = gson.fromJson(jsonString, listType);

                JsonParser parser = new JsonParser();

                for (Map.Entry<String, Object> m : myList.entrySet()) {
                    System.out.println("==============");
                    if (m.getValue() instanceof String) {
                        // get String value
                    } else { // if value is an Object

                        System.out.println("VIP Sec: Name: " + m.getKey());
                        Map<String, Object> myList1 = gson.fromJson(m.getValue().toString(), listType);
                        for (Map.Entry<String, Object> m1 : myList1.entrySet()) {
                            if (!(m1.getValue() instanceof String)) {
                                Map<String, Object> myList2 = gson.fromJson(m1.getValue().toString(), listType);
                                for (Map.Entry<String, Object> m2 : myList2.entrySet()) {
                                    if (!(m2.getValue() instanceof String)) {
                                        Map<String, Object> myList3 = gson.fromJson(m2.getValue().toString(), listType);
                                        for (Map.Entry<String, Object> m3 : myList3.entrySet()) {
                                            if (m3.getKey().equals("virtualAddresses")) {
                                                System.out.println("VIP Sec: IP Address: " + m3.getValue());
                                            } else if (m3.getKey().equals("pool")) {
                                                System.out.println("Pool Sec: Name: " + m3.getValue());
                                            } else if (m3.getKey().equals("monitors")) {
                                                JsonArray monitors = parser.parse(m3.getValue().toString()).getAsJsonArray();
                                                int count = 0;
                                                while (count < monitors.size()) {
                                                    String monitor = monitors.get(count).getAsString();
                                                    System.out.println("Monitor: " + monitor);
                                                    count++;
                                                }
                                            } else if (m3.getKey().equals("members")) {
                                                JsonArray members = parser.parse(m3.getValue().toString()).getAsJsonArray();
                                                int count = 0;
                                                while (count < members.size()) {
                                                    // Parsing as Object to key values by key directly
                                                    JsonObject mem = members.get(count).getAsJsonObject();
                                                    String port = mem.get("servicePort").getAsString();
                                                    System.out.println("Port: " + port);
                                                    JsonElement ipAddrs = mem.get("serverAddresses");
                                                    if (ipAddrs.isJsonArray()) {
                                                        JsonArray ips = ipAddrs.getAsJsonArray();
                                                        int c = 0;
                                                        while (c < ips.size()) {
                                                            String ip = ips.get(c).getAsString();
                                                            System.out.println("IP: " + ip);
                                                            c++;
                                                        }
                                                    }
                                                    count++;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }


            }
        });

        generateSimpleJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("generate simple JSON");
            }
        });


        readWriteSimpleJson.setOnClickListener(new View.OnClickListener() {
            // https://camposha.info/android-examples/android-gson/#gsc.tab=0
            @Override
            public void onClick(View v) {
                System.out.println("*** read + write simple JSON");

                // init class
                Place place = new Place();
                place.setName("World");
                Human human = new Human();
                human.setMessage("Hi");
                human.setPlace(place);

                // convert to json
                //Gson gson = new Gson();
                //String jsonString = gson.toJson(human);
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                String jsonString = gson.toJson(human);
                System.out.println("json " + jsonString); // print "json {"message":"Hi","place":{"name":"World"}}"
                // json {"message":"Hi","place":{"name":"World"}}
                // convert from json
                Human newHuman = gson.fromJson(jsonString, Human.class);
                newHuman.say();
                // Hi , World!
            }
        });

        readYahooApiJson.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("*** read Yahoo API JSON");
                String jsonString = "{\"XDEW.DE\":{\"timestamp\":[1646985600,1647621384],\"symbol\":\"XDEW.DE\",\"previousClose\":null,\"chartPreviousClose\":72.13,\"end\":null,\"start\":null,\"close\":[72.13,73.67],\"dataGranularity\":300}}";

                //String nestedJSON = "{\"id\":\"1\",\"message\":\"web_didload\",\"content\":{\"success\":1}}";
                Gson gson = new Gson();
                LinkedTreeMap result = gson.fromJson(jsonString, LinkedTreeMap.class);
                System.out.println(result);
                boolean containTimestamp = result.containsKey("timestamp");
                System.out.println("json contains timestamp: " + containTimestamp);

                /*
                Gson gson = new Gson();
                Yahoo newYahoo = gson.fromJson(jsonString, Yahoo.class);
                try {
                    newYahoo.say();
                } catch (NullPointerException e) {
                    System.out.println("null pointer Exception");
                    System.out.println(e);
                }

                 */
            }
        });

        // result from yahoo api
/*
{"XDEW.DE":{"timestamp":[1646985600,1647621384],"symbol":"XDEW.DE","previousClose":null,"chartPreviousClose":72.13,"end":null,"start":null,"close":[72.13,73.67],"dataGranularity":300}}
{"XDEW.DE":{"timestamp":[1646985600,1647621384],
            "symbol":"XDEW.DE",
            "previousClose":null,
            "chartPreviousClose":72.13,
            "end":null,
            "start":null,
            "close":[72.13,73.67],
            "dataGranularity":300}
           }

{"XDEW.DE":{"timestamp":[1645171200,1645430400,1645516800,1645603200,1645689600,1645776000,1646035200,1646121600,1646208000,1646294400,1646380800,1646640000,1646726400,1646812800,1646899200,1646985600,1647621384],"symbol":"XDEW.DE","chartPreviousClose":70.83,"close":[70.83,70.33,70.49,69.91,69.15,71.4,71.94,71.33,72.54,72.76,72.67,72.8,71.34,71.5,70.94,72.13,73.67],"dataGranularity":300,"previousClose":null,"end":null,"start":null}}

 */
        yahooApi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    YahooFinanceApiRequestV02.main(apiKeyFromSharedPreferences);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    // converts a unix timestamp to a date in format yyyy-mm-dd
    static public String unixDateToDate(String unixDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        long dateL = Long.parseLong(unixDate);
        LocalDateTime dateTimeLocal = LocalDateTime.ofInstant(Instant.ofEpochSecond(dateL), TimeZone.getDefault().toZoneId());
        return dateTimeLocal.format(formatter);
    }

    private static class Yahoo {
        private String yahooSymbol;
        private String data;

        public String getYahooSymbol() {
            return yahooSymbol;
        }

        public void setYahooSymbol(String yahooSymbol) {
            yahooSymbol = yahooSymbol;
        }

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public void say() {
            System.out.println("YS: " + getYahooSymbol());
            System.out.println("data: " + getData());
        }
    }

    private static class Human {
        private String message;
        private Place place;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public Place getPlace() {
            return place;
        }

        public void setPlace(Place place) {
            this.place = place;
        }

        public void say() {
            System.out.println();
            System.out.println(getMessage() + " , " + getPlace().getName() + "!");
        }
    }

    private static class Place {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}