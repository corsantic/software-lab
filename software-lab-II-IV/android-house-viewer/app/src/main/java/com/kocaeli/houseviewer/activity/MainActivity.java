package com.kocaeli.houseviewer.activity;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kocaeli.houseviewer.R;
import com.kocaeli.houseviewer.adapter.CustomAdapter;
import com.kocaeli.houseviewer.entity.House;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity
{
    private static final String HOUSES_URL = "http://ferdielik.me:7070/rest/house/all";
    List<House> houseList = new ArrayList<>();

    ListView listView;

    CustomAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        updateHouseList();

        listView = (ListView) findViewById(R.id.list);
        listView.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                House house = houseList.get(position);
                Intent intent = new Intent(getBaseContext(), HouseDetailActivity.class);
                intent.putExtra("houseId", house.getId());

                startActivity(intent);
            }
        });


        adapter = new CustomAdapter(houseList, getBaseContext());
        listView.setAdapter(adapter);
    }


    private void updateHouseList() // todo: surekli calismali
    {
        StringRequest myReq = new StringRequest(Request.Method.GET, HOUSES_URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Gson gson = new Gson();
                Type listType = new TypeToken<ArrayList<House>>()
                {
                }.getType();

                List<House> houses = gson.fromJson(response, listType);
                houseList.clear();
                houseList.addAll(houses);

                adapter.notifyDataSetChanged();

            }
        }, new Response.ErrorListener()
        {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                System.out.println(error.getMessage());
            }
        })
        {

        };
        myReq.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(myReq);
    }


}