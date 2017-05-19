package com.kocaeli.houseviewer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Spinner;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kocaeli.houseviewer.entity.House;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends Activity {
    Spinner sp;
    int chosen;
    TextView t1, t2, t3, t4;
    ProgressDialog pDialog;
    String veri_string;
    public class AppConstants {

        public static final String URL = "http://ferdielik.me:8081";


        /* private Constructor to avoid instanciating this class */
        private AppConstants() {}
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // activity_main.xml de oluşturduğumuz textview leri koda tanıtıyoruz.
        t1 = (TextView) findViewById(R.id.text);
        t2 = (TextView) findViewById(R.id.text2);
        t3 = (TextView) findViewById(R.id.text3);
        t4 = (TextView) findViewById(R.id.text4);

        // activity_main.xml de oluşturduğumuz spineri koda tanıtıyoruz.
        sp = (Spinner) findViewById(R.id.spinner1);

        // Spinera Listener ekliyoruz
        sp.setOnItemSelectedListener(new OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1,
                                       int arg2, long arg3) {
                chosen = arg2; // spinerdan secilen değerin sonra değerini
                // alıyoruz. kaçıncı sırada olduqunu
                if (chosen != 0) { // seçilen deger ilk deger değilse yani Kişi
                    // seçiniz yazısı değilse
                    pDialog = new ProgressDialog(MainActivity.this);
                    pDialog.setMessage("Kişi Bilgileri Getiriliyor...");
                    pDialog.setIndeterminate(true);
                    pDialog.setCancelable(false); // ProgressDialog u iptal
                    // edilemez
                    // hale getirdik.
                    pDialog.show();
                    try {
                        getData();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {//OnItemSelectedListener implement methodu
            }
        });
    }

    public void getData() throws IOException {
        // TODO adding request to POST method and URL
        // Burada issteğimizi oluşturuyoruz, method parametresi olarak post
        // seçiyoruz ve url'imizi const'dan alıyoruz.


        StringRequest myReq = new StringRequest(Request.Method.GET, AppConstants.URL, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<House>>(){}.getType();

                List<House> houses = gson.fromJson(response, listType); //PersonClass'mızdan obje oluşturuyoruz böylelikle set ettiğimiz verilere ulaşacağız.
                pDialog.dismiss(); // ProgresDialog u kapatıyoruz.
            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println(error.getMessage());
            }
        }) {

            protected Map<String, String> getParams()
                    throws com.android.volley.AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                // TODO seçilen kişinin id'sini kisi parametresine ekliyoruz
                params.put("kisi", chosen + "");
                return params;
            };
        };
        myReq.setShouldCache(false); //cache kapatıyoruz

        RequestQueue queue = Volley.newRequestQueue(this);

        // Request(İstek)'i Volley'in Requst sırasına atıyoruz
        // Adding request to volley request queue
        queue.add(myReq);

    }

}