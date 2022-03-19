package de.androidcrypto.androidjsongsonreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class StoreYahooApiKey extends AppCompatActivity {

    Button saveYahooApiKey;
    EditText yahooApiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_yahoo_api_key);

        saveYahooApiKey = findViewById(R.id.btnSaveYahooApiKey);
        yahooApiKey = findViewById(R.id.yahooApiKey);

        saveYahooApiKey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String key = yahooApiKey.getText().toString();
                Context context = v.getContext();
                SharedPreferences sharedPref = context.getSharedPreferences(
                        getString(R.string.preference_file_key), Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putString(getString(R.string.yahoo_api_key), key);
                editor.apply();
                Toast.makeText(getApplicationContext(),"Yahoo API key gespeichert",Toast.LENGTH_SHORT);
            }
        });
    }
}