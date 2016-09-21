package com.xipypr.translategoogle;

import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private EditText text;
    private TextView translated;
    private Button translateBtn;
    private final String URL = "https://translate.yandex.net";
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
    private  final  String KEY = "trnsl.1.1.20160920T232746Z.510a77b0433a5d1b.ecc459db27ef26cc96ea88728d9fa2f4a9f7b256";

 Retrofit  restAdapter = new Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create(gson))
        .baseUrl(URL)
        .build();

final Link intf =  restAdapter.create(Link.class);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        text = (EditText)findViewById(R.id.text);
        translated=(TextView)findViewById(R.id.translated);
        translateBtn=(Button)findViewById(R.id.translate);


        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Map<String,String> mapJson = new HashMap<>();
                mapJson.put("key",KEY);
                mapJson.put("lang","en-ru");
                mapJson.put("text",text.getText().toString());

                Call<Object> call = intf.translate(mapJson);
                try {
                    Response<Object> res = call.execute();

                    Map<String,String> map = gson.fromJson(res.body().toString(), Map.class);

                    for(Map.Entry e : map.entrySet()){
                        if(e.getKey().equals("text")){
                            translated.setText(e.getValue().toString());
                        }
                    }


                } catch (IOException e) {
                    e.printStackTrace();
                }


            }
        });






    }
}
