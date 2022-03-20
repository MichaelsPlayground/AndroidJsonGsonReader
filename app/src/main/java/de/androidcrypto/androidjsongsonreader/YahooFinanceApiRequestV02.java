package de.androidcrypto.androidjsongsonreader;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class YahooFinanceApiRequestV02 {

    //
    public static void main(String apiKey) throws IOException {
        // https://www.yahoofinanceapi.com/pricing


        // get symbols: https://de.finance.yahoo.com
        // yahoo docs: https://www.yahoofinanceapi.com/tutorial
        // api docs: https://www.yahoofinanceapi.com
        // ishares commodities: EXXY.DE
        // xtrackers s&p500 equal weight XDEW.DE  Xtrackers S&P 500 Equal Weight UCITS ETF (XDEW.L)
        // IE00BJ0KDQ92 MSCI World UCITS ETF (XDWD.DE)

        if (apiKey.equals("")) {
            System.out.println("*** ERROR *** no API key given");
            return;
        }

        // get name
        System.out.println("*** get name ***");

        /*
        curl -X 'GET' \
  'https://yfapi.net/v8/finance/spark?interval=1d&range=6mo&symbols=%20XDEW.DE' \
  -H 'accept: application/json' \
  -H 'X-API-KEY: xxx'
         */
        // https://yfapi.net/v8/finance/spark?interval=1d&range=6mo&symbols=%20XDEW.DE

        // search for symbols
        // https://yfapi.net/v6/finance/autocomplete?region=DE&lang=de&query=iShares%20Nasdaq%20100%20UCITS%20ETF
/*
{
  "ResultSet": {
    "Query": "iShares Nasdaq 100 UCITS ETF",
    "Result": [
      {
        "symbol": "SXRV.SG",
        "name": "iShares NASDAQ 100 UCITS ETF",
        "exch": "STU",
        "type": "M",
        "exchDisp": "Stuttgart",
        "typeDisp": "Fonds"
      },
      {
        "symbol": "CNX1.L",
        "name": "iShares VII Public Limited Company -  iShares NASDAQ 100 UCITS ETF",
        "exch": "LSE",
        "type": "E",
        "exchDisp": "London",
        "typeDisp": "ETF"
      },
      {
        "symbol": "CSNDX.SW",
        "name": "iShares VII Public Limited Company -  iShares NASDAQ 100 UCITS ETF",
        "exch": "EBS",
        "type": "E",
        "exchDisp": "Schweiz",
        "typeDisp": "ETF"
      },
      {
        "symbol": "CSNDX.MI",
        "name": "iShares VII Public Limited Company -  iShares NASDAQ 100 UCITS ETF",
        "exch": "MIL",
        "type": "E",
        "exchDisp": "Mailand",
        "typeDisp": "ETF"
      },
      {
        "symbol": "NQSE.SG",
        "name": "iShares NASDAQ 100 UCITS ETF US",
        "exch": "STU",
        "type": "M",
        "exchDisp": "Stuttgart",
        "typeDisp": "Fonds"
      },
      {
        "symbol": "ISVAF",
        "name": "iShares VII Public Limited Company -  iShares NASDAQ 100 UCITS ETF",
        "exch": "PNK",
        "type": "E",
        "exchDisp": "OTC-Märkte",
        "typeDisp": "ETF"
      },
      {
        "symbol": "CNDX.L",
        "name": "iShares VII Public Limited Company -  iShares NASDAQ 100 UCITS ETF",
        "exch": "LSE",
        "type": "E",
        "exchDisp": "London",
        "typeDisp": "ETF"
      },
      {
        "symbol": "NQSE.F",
        "name": "iShares VII Public Limited Company -  iShares NASDAQ 100 UCITS ETF",
        "exch": "FRA",
        "type": "E",
        "exchDisp": "Frankfurt",
        "typeDisp": "ETF"
      },
      {
        "symbol": "SXRV.F",
        "name": "iShares VII Public Limited Company -  iShares NASDAQ 100 UCITS ETF",
        "exch": "FRA",
        "type": "E",
        "exchDisp": "Frankfurt",
        "typeDisp": "ETF"
      },
      {
        "symbol": "CNDX.AS",
        "name": "iShares VII Public Limited Company -  iShares NASDAQ 100 UCITS ETF",
        "exch": "AMS",
        "type": "E",
        "exchDisp": "Amsterdam",
        "typeDisp": "ETF"
      }
    ]
  }
}
 */
/*
       https://yfapi.net/v8/finance/spark?interval=1d&range=1mo&symbols=NQSE.F%2CXDEW.DE
{
  "XDEW.DE": {
    "timestamp": [
      1644566400,
      1644825600,
      1644912000,
      1644998400,
      1645084800,
      1645171200,
      1645430400,
      1645516800,
      1645603200,
      1645689600,
      1645776000,
      1646035200,
      1646121600,
      1646208000,
      1646294400,
      1646380800,
      1647016559
    ],
    "symbol": "XDEW.DE",
    "close": [
      72.16,
      71.3,
      71.86,
      71.65,
      71.32,
      70.83,
      70.33,
      70.49,
      69.91,
      69.15,
      71.4,
      71.94,
      71.33,
      72.54,
      72.76,
      72.67,
      72.13
    ],
    "dataGranularity": 300,
    "end": null,
    "start": null,
    "previousClose": null,
    "chartPreviousClose": 72.16
  },
  "NQSE.F": {
    "timestamp": [
      1644562800,
      1644822000,
      1644908400,
      1644994800,
      1645081200,
      1645167600,
      1645426800,
      1645513200,
      1645599600,
      1645686000,
      1645772400,
      1646031600,
      1646118000,
      1646204400,
      1646290800,
      1646377200,
      1647026643
    ],
    "symbol": "NQSE.F",
    "close": [
      9.275,
      9.093,
      9.281,
      9.293,
      9.124,
      8.963,
      8.747,
      8.932,
      8.737,
      8.59,
      8.908,
      8.971,
      8.974,
      9.113,
      8.931,
      8.896,
      8.559
    ],
    "dataGranularity": 300,
    "end": null,
    "start": null,
    "previousClose": null,
    "chartPreviousClose": 9.275
  }
}
 */

        //URL urlName = new URL("https://data.lemon.markets/v1/instruments/?isin=IE00BJ0KDQ92&mic=XMUN");
        //URL urlName = new URL("https://yfapi.net/v8/finance/spark?interval=1d&range=6mo&symbols=%20XDEW.DE");

        // Xtrackers S&P 500 Equal Weight UCITS ETF IE00BLNMYC90 XDEW.DE
        // Xtrackers msci world IE00BJ0KDQ92 XDWD.DE

        // 1 symbol
        //URL urlName = new URL("https://yfapi.net/v8/finance/spark?interval=1d&range=1mo&symbols=%20XDEW.DE");
        // 2 symbols
        URL urlName = new URL("https://yfapi.net/v8/finance/spark?interval=1d&range=1mo&symbols=XDEW.DE%2CXDWD.DE");

        //URL urlName = new URL("https://yfapi.net/v8/finance/spark?interval=1d&range=5y&symbols=%20XDEW.DE");

        // 2 stocks mit comma separated
        // https://yfapi.net/v8/finance/spark?interval=1d&range=1mo&symbols=NQSE.F%2CXDEW.DE

        HttpURLConnection httpName = (HttpURLConnection) urlName.openConnection();

        httpName.setRequestProperty("X-API-KEY", apiKey);

        System.out.println(httpName.getResponseCode() + " ResponseMessage: " + httpName.getResponseMessage());

        BufferedReader brName = new BufferedReader(new InputStreamReader((httpName.getInputStream())));
        StringBuilder sbName = new StringBuilder();
        String outputName;
        while ((outputName = brName.readLine()) != null) {
            sbName.append(outputName);
        }
        String dataName = sbName.toString();
        System.out.println("*** Content start:\n" + dataName + "\n*** content end ***");
/*
I/System.out: {"XDEW.DE":{"symbol":"XDEW.DE","timestamp":[1631516400,1631602800,1631689200,1631775600,
1631862000,1632121200,1632207600,1632294000,1632380400,1632466800,1632726000,1632812400,1632898800,
1632985200,1633071600,1633330800,1633417200,1633503600,1633590000,1633676400,1633935600,1634022000,
1634108400,1634194800,1634281200,1634540400,1634626800,1634713200,1634799600,1634886000,1635145200,
1635231600,1635318000,1635404400,1635490800,1635753600,1635840000,1635926400,1636012800,1636099200,
1636358400,1636444800,1636531200,1636617600,1636704000,1636963200,1637049600,1637136000,1637222400,
1637308800,1637568000,1637654400,1637740800,1637827200,1637913600,1638172800,1638259200,1638345600,
1638432000,1638518400,1638777600,1638864000,1638950400,1639036800,1639123200,1639382400,1639468800,
1639555200,1639641600,1639728000,1639987200,1640073600,1640160000,1640246400,1640592000,1640678400,
1640764800,1640847600,1641196800,1641283200,1641369600,1641456000,1641542400,1641801600,1641888000,
1641974400,1642060800,1642147200,1642406400,1642492800,1642579200,1642665600,1642752000,1643011200,
1643097600,1643184000,1643270400,1643356800,1643616000,1643702400,1643788800,1643875200,1643961600,
1644220800,1644307200,1644393600,1644480000,1644566400,1644825600,1644912000,1644998400,1645084800,
1645171200,1645430400,1645516800,1645603200,1645689600,1645776000,1646035200,1646121600,1646208000,
1646294400,1646380800,1647016559],"close":[67.93,67.61,67.74,68.05,67.85,66.68,66.89,67.46,68.39,
68.46,68.99,68.08,68.61,68.17,67.79,67.52,68.6,67.76,69.57,69.15,69.37,68.98,68.44,69.56,70.17,
70.03,70.11,70.63,70.44,70.92,71.48,71.63,70.9,70.39,71.29,71.43,71.75,71.83,72.28,72.78,72.68,
72.56,73.14,73.15,73.56,73.92,74.64,74.26,73.8,73.73,74.56,73.94,74.63,74.74,71.87,72.75,71.6,
72.04,71.03,71.37,72.55,74.11,73.34,73.61,73.22,73.07,72.92,72.57,74.01,73.39,71.08,72.86,73.27,
74.1,74.4,75.07,74.99,75.24,74.96,76.01,75.9,74.92,74.65,73.54,74.04,74.26,74.49,73.38,74.2,73.34,
72.96,73.42,71.67,69.24,69.98,71.42,71.93,70.52,71.74,72.38,72.6,71.83,70.77,71.25,71.82,72.85,
72.63,72.16,71.3,71.86,71.65,71.32,70.83,70.33,70.49,69.91,69.15,71.4,71.94,71.33,72.54,72.76,
72.67,72.13],"end":null,"start":null,"dataGranularity":300,"previousClose":null,"chartPreviousClose":67.96}}
 */
        // split string
        System.out.println("split string");
        String[] parts = dataName.split(",");
        int partsLength = parts.length;
        System.out.println("parts: " + partsLength);
        for (int i = 0; i < partsLength; i++) {
            System.out.println("p: " + i + " d: " + parts[i]);
        }
        System.out.println("*** END ***");

        System.out.println("responseMethod: " + httpName.getRequestMethod());

        System.out.println("search substring timestamp\":[");
        int startTimestamp = -1;
        int endTimestamp = -1;

        // search startTimestamp
        for (int i = 0; i < partsLength; i++) {
            int pos = searchPosition(parts[i],"timestamp\":[");
            if (pos > -1) {
                startTimestamp = i;
                break;
            }
        }
        if (startTimestamp < 0) {
            System.out.println("Timestamp in JSON nicht gefunden");
            // todo what to do when no data is found
            return;
        }
        // now correct parts with timestamp
        int pos = searchPosition(parts[startTimestamp], "timestamp\":[");
        System.out.println("pos: " + pos);
        String firstData = parts[startTimestamp].substring(pos);
        System.out.println("firstData: " + firstData);
        parts[startTimestamp] = firstData;

        // now search for end timestamp
        for (int i = startTimestamp; i < partsLength; i++) {
            int posEnd = searchPosition(parts[i], "]");
            if (posEnd > -1) {
                System.out.println("end timestamp found at " + i + " posEnd: " + posEnd);
                endTimestamp = i;
                break; // end for
            }
        }
        System.out.println("endTimestamp: " + endTimestamp);
        parts[endTimestamp] = parts[endTimestamp].replace("]", "");
        System.out.println("last timestamp: " + parts[endTimestamp]);

        // printout timestamps
        System.out.println("*** printout timestamps ***");
        for (int i = startTimestamp; i <= endTimestamp; i++) {
            System.out.println("i: " + i + " d: " + parts[i] + " date: " + Utils.unixDateToDate(parts[i]));
        }

        // search for closePrice
        System.out.println("search substring close\":[");
        int startClosePrice = -1;
        int endClosePrice = -1;

        // search startClosePrice
        for (int i = 0; i < partsLength; i++) {
            int posClose = searchPosition(parts[i],"close\":[");
            if (posClose > -1) {
                startClosePrice = i;
                break;
            }
        }
        if (startClosePrice < 0) {
            System.out.println("Close price in JSON nicht gefunden");
            // todo what to do when no data is found
            return;
        }
        // now correct parts with close
        pos = searchPosition(parts[startClosePrice], "close\":[");
        System.out.println("pos: " + pos);
        firstData = parts[startClosePrice].substring(pos);
        System.out.println("firstData: " + firstData);
        parts[startClosePrice] = firstData;
        // now search for end closePrice
        for (int i = startClosePrice; i < partsLength; i++) {
            int posEnd = searchPosition(parts[i], "]");
            if (posEnd > -1) {
                System.out.println("end closePrice found at " + i + " posEnd: " + posEnd);
                endClosePrice = i;
                break; // end for
            }
        }
        System.out.println("endClosePrice: " + endClosePrice);
        parts[endClosePrice] = parts[endClosePrice].replace("]", "");
        System.out.printf("last endClosePrice: " + parts[endClosePrice]);

        // printout closePrice
        System.out.println("*** printout closePrices ***");
        for (int i = startClosePrice; i <= endClosePrice; i++) {
            //System.out.println("i: " + i + " d: " + parts[i]);
        }

        // search for symbol
        System.out.println("search substring symbol\":\"");
        int startSymbol = -1;

        // search startSymbol
        for (int i = 0; i < partsLength; i++) {
            pos = searchPosition(parts[i],"symbol\":\"");
            if (pos > -1) {
                startSymbol = i;
                break;
            }
        }
        if (startSymbol < 0) {
            System.out.println("Symbol in JSON nicht gefunden");
            // todo what to do when no data is found
            return;
        }
        // now correct parts with symbol
        pos = searchPosition(parts[startSymbol], "symbol\":\"");
        System.out.println("pos: " + pos);
        firstData = parts[startSymbol].substring(pos);
        System.out.println("firstData: " + firstData.replace("\"", ""));
        parts[startSymbol] = firstData.replace("\"", "");


        // get data
        // parseName(dataName); // yahoo responds no name

        // read only year end value
        System.out.println("*** get prices ***");
        /*
        URL url = new URL("https://data.lemon.markets/v1/ohlc/d1/?isin=IE00BJ0KDQ92&mic=XMUN&from=2021-12-30&to=2022-01-03");
        HttpURLConnection http = (HttpURLConnection) url.openConnection();

        http.setRequestProperty("Authorization", "Bearer xxx");

        System.out.println(http.getResponseCode() + " ResponseMessage: " + http.getResponseMessage());

        BufferedReader br = new BufferedReader(new InputStreamReader((http.getInputStream())));
        StringBuilder sb = new StringBuilder();
        String output;
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        String data = sb.toString();
        System.out.println("Content:\n" + data);
*/
        //parsePrices(dataName);

        // boerse frankfurt https://www.boerse-frankfurt.de/etf/xtrackers-msci-world-ucits-etf-1c/kurshistorie/historische-kurse-und-umsaetze
        // 30.12.21	86,67	86,706	86,96	86,666	3.565.841	41.111
        // https://www.boerse-muenchen.de/Etf/IE00BJ0KDQ92
        /*
        // now page 2
        System.out.println("*** page 2 ***");
        url = new URL("https://data.lemon.markets/v1/ohlc/d1/?isin=IE00BJ0KDQ92&mic=XMUN&from=2021-12-08&to=2022-01-05&page=2");
        http = (HttpURLConnection) url.openConnection();
        http.setRequestProperty("Authorization", "Bearer xxx");

        System.out.println(http.getResponseCode() + " ResponseMessage: " + http.getResponseMessage());
        br = new BufferedReader(new InputStreamReader((http.getInputStream())));
        sb = new StringBuilder();
        output = "";
        while ((output = br.readLine()) != null) {
            sb.append(output);
        }
        data = sb.toString();
        System.out.println("Content:\n" + data);

         */

        httpName.disconnect();

    }

    // return the first position AFTER the searchString
    private static int searchPosition(String completeString, String searchString) {
        int position = -1; // default, not found
        position = completeString.indexOf(searchString);
        if (position > -1) {
            int length = searchString.length();
            position = position + length;
        }
        return position;
    }


    // uses GSON
    // import com.google.gson.JsonArray;
    //import com.google.gson.JsonObject;
    //import com.google.gson.JsonParser;
    // https://devqa.io/how-to-parse-json-in-java/
    public static void parseName(String json) {
        //JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        // https://stackoverflow.com/questions/60771386/jsonparser-is-deprecated
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //String pageName = jsonObject.getAsJsonObject("pageInfo").get("pageName").getAsString();
        //System.out.println(pageName);

        JsonArray arr = jsonObject.getAsJsonArray("results");
        for (int i = 0; i < arr.size(); i++) {
            String post_id = arr.get(i).getAsJsonObject().get("name").getAsString();
            System.out.println("Name: " + post_id);
        }

    }

    public static void parseNameOrg(String json) {
        //JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        // https://stackoverflow.com/questions/60771386/jsonparser-is-deprecated
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //String pageName = jsonObject.getAsJsonObject("pageInfo").get("pageName").getAsString();
        //System.out.println(pageName);

        JsonArray arr = jsonObject.getAsJsonArray("results");
        for (int i = 0; i < arr.size(); i++) {
            String post_id = arr.get(i).getAsJsonObject().get("name").getAsString();
            System.out.println("Name: " + post_id);
        }

    }
    public static void parsePrices(String json) {
        //JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //String pageName = jsonObject.getAsJsonObject("pageInfo").get("pageName").getAsString();
        //System.out.println(pageName);

        JsonArray arr = jsonObject.getAsJsonArray("results");
        for (int i = 0; i < arr.size(); i++) {
            String isin = arr.get(i).getAsJsonObject().get("isin").getAsString();
            System.out.println("ISIN: " + isin);
            String last = arr.get(i).getAsJsonObject().get("l").getAsString();
            System.out.println("Last price: " + last);
            String time = arr.get(i).getAsJsonObject().get("t").getAsString();
            System.out.println("Time: " + time);
        }

    }

    public static void parsePricesOrg(String json) {
        //JsonObject jsonObject = new JsonParser().parse(json).getAsJsonObject();
        JsonObject jsonObject = JsonParser.parseString(json).getAsJsonObject();

        //String pageName = jsonObject.getAsJsonObject("pageInfo").get("pageName").getAsString();
        //System.out.println(pageName);

        JsonArray arr = jsonObject.getAsJsonArray("timestamp");
        for (int i = 0; i < arr.size(); i++) {
            String isin = arr.get(i).getAsJsonObject().get("isin").getAsString();
            System.out.println("ISIN: " + isin);
            String last = arr.get(i).getAsJsonObject().get("l").getAsString();
            System.out.println("Last price: " + last);
            String time = arr.get(i).getAsJsonObject().get("t").getAsString();
            System.out.println("Time: " + time);
        }

    }

    // vahoo finance result:
// {"XDEW.DE":{"dataGranularity":300,"timestamp":[1631516400,1631602800,1631689200,1631775600,1631862000,1632121200,1632207600,1632294000,1632380400,1632466800,1632726000,1632812400,1632898800,1632985200,1633071600,1633330800,1633417200,1633503600,1633590000,1633676400,1633935600,1634022000,1634108400,1634194800,1634281200,1634540400,1634626800,1634713200,1634799600,1634886000,1635145200,1635231600,1635318000,1635404400,1635490800,1635753600,1635840000,1635926400,1636012800,1636099200,1636358400,1636444800,1636531200,1636617600,1636704000,1636963200,1637049600,1637136000,1637222400,1637308800,1637568000,1637654400,1637740800,1637827200,1637913600,1638172800,1638259200,1638345600,1638432000,1638518400,1638777600,1638864000,1638950400,1639036800,1639123200,1639382400,1639468800,1639555200,1639641600,1639728000,1639987200,1640073600,1640160000,1640246400,1640592000,1640678400,1640764800,1640847600,1641196800,1641283200,1641369600,1641456000,1641542400,1641801600,1641888000,1641974400,1642060800,1642147200,1642406400,1642492800,1642579200,1642665600,1642752000,1643011200,1643097600,1643184000,1643270400,1643356800,1643616000,1643702400,1643788800,1643875200,1643961600,1644220800,1644307200,1644393600,1644480000,1644566400,1644825600,1644912000,1644998400,1645084800,1645171200,1645430400,1645516800,1645603200,1645689600,1645776000,1646035200,1646121600,1646208000,1646294400,1646380800,1647016559],"symbol":"XDEW.DE","previousClose":null,"chartPreviousClose":67.96,"close":[67.93,67.61,67.74,68.05,67.85,66.68,66.89,67.46,68.39,68.46,68.99,68.08,68.61,68.17,67.79,67.52,68.6,67.76,69.57,69.15,69.37,68.98,68.44,69.56,70.17,70.03,70.11,70.63,70.44,70.92,71.48,71.63,70.9,70.39,71.29,71.43,71.75,71.83,72.28,72.78,72.68,72.56,73.14,73.15,73.56,73.92,74.64,74.26,73.8,73.73,74.56,73.94,74.63,74.74,71.87,72.75,71.6,72.04,71.03,71.37,72.55,74.11,73.34,73.61,73.22,73.07,72.92,72.57,74.01,73.39,71.08,72.86,73.27,74.1,74.4,75.07,74.99,75.24,74.96,76.01,75.9,74.92,74.65,73.54,74.04,74.26,74.49,73.38,74.2,73.34,72.96,73.42,71.67,69.24,69.98,71.42,71.93,70.52,71.74,72.38,72.6,71.83,70.77,71.25,71.82,72.85,72.63,72.16,71.3,71.86,71.65,71.32,70.83,70.33,70.49,69.91,69.15,71.4,71.94,71.33,72.54,72.76,72.67,72.13],"end":null,"start":null}}

/*
    // uses fasterxml jackson
    // import com.fasterxml.jackson.core.JsonFactory;
    //import com.fasterxml.jackson.core.JsonProcessingException;
    //import com.fasterxml.jackson.databind.JsonNode;
    //import com.fasterxml.jackson.databind.ObjectMapper;
    public static void parse(String json) throws JsonProcessingException {
        JsonFactory factory = new JsonFactory();

        ObjectMapper mapper = new ObjectMapper(factory);
        JsonNode rootNode = mapper.readTree(json);

        Iterator<Map.Entry<String,JsonNode>> fieldsIterator = rootNode.fields();
        while (fieldsIterator.hasNext()) {

            Map.Entry<String,JsonNode> field = fieldsIterator.next();
            System.out.println("Key: " + field.getKey() + "\tValue:" + field.getValue());
        }
    }
*/

/*
Historic data
https://data.lemon.markets/v1/ohlc/d1/

https://data.lemon.markets/v1/ohlc/d1/
request = requests.get("https://data.lemon.markets/v1/ohlc/h1/?isin=isin&mic=mic&from=2021-11-01", headers={"Authorization": "Bearer YOUR-API-KEY"})

xxx

request = requests.get("https://data.lemon.markets/v1/quotes/?isin=isin&mic=mic&from=2021-11-01", headers={"Authorization": "Bearer YOUR-API-KEY"})

curl https://reqbin.com/echo/get/json
   -H "Accept: application/json"
   -H "Authorization: Bearer {token}“

curl https://data.lemon.markets/v1/quotes/?isin=isin&mic=mic&from=2021-11-01
   -H "Authorization: Bearer
„

Gibt einen wert:
curl https://data.lemon.markets/v1/ohlc/h1/?isin=IE00BJ0KDQ92&mic=XMUN&from=2021-12-01
   -H "Authorization: Bearer xxx"

Mehrere Tage:
curl https://data.lemon.markets/v1/ohlc/d1/?isin=IE00BJ0KDQ92&mic=XMUN&from=2021-12-08&to=2022-01-05
   -H "Authorization: Bearer xxx"

Online curl
https://reqbin.com/req/c-hlt4gkzd/curl-bearer-token-authorization-header-example

Dort kann auch der curl code in Java konvertiert werden

URL url = new URL("https://data.lemon.markets/v1/ohlc/d1/?isin=IE00BJ0KDQ92&mic=XMUN&from=2021-12-08&to=2022-01-05");
HttpURLConnection http = (HttpURLConnection)url.openConnection();
http.setRequestProperty("Authorization", "Bearer xxx");

System.out.println(http.getResponseCode() + " " + http.getResponseMessage());
http.disconnect();
*/
}
/*
*** get name ***
200 ResponseMessage: OK
Content:
{"results":[{"isin":"IE00BJ0KDQ92","wkn":"A1XB5U","name":"X(IE)-MSCI WORLD 1C","title":"XTR.(IE) - MSCI WORLD","symbol":"XDWD","type":"etf","venues":[{"name":"Börse München - Gettex","title":"Gettex","mic":"XMUN","is_open":true,"tradable":true,"currency":"EUR"}]}],"previous":null,"next":null,"total":1,"page":1,"pages":1}
Name: X(IE)-MSCI WORLD 1C
*** get prices ***
200 ResponseMessage: OK
Content:
{"results":[{"isin":"IE00BJ0KDQ92","o":86.57,"h":86.948,"l":86.57,"c":86.7,"t":"2021-12-30T00:00:00.000+00:00","mic":"XMUN"},{"isin":"IE00BJ0KDQ92","o":86.534,"h":87.06,"l":86.248,"c":87.05,"t":"2022-01-03T00:00:00.000+00:00","mic":"XMUN"}],"previous":null,"next":null,"total":2,"page":1,"pages":1}
ISIN: IE00BJ0KDQ92
Last price: 86.57
Time: 2021-12-30T00:00:00.000+00:00
ISIN: IE00BJ0KDQ92
Last price: 86.248
Time: 2022-01-03T00:00:00.000+00:00
 */