package com.kocaeli.houseviewer.activity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.kocaeli.houseviewer.R;
import com.kocaeli.houseviewer.entity.House;
import com.kocaeli.houseviewer.entity.Image;
import com.squareup.picasso.Picasso;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class HouseDetailActivity extends AppCompatActivity
{
    private static final String HOUSE_DETAIL_URL = "http://ferdielik.me:7070/rest/house/detail/";

    private House house;
    private LinearLayout imagesLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.house_detail);
        Long houseId = getIntent().getExtras().getLong("houseId");

        imagesLayout = (LinearLayout) findViewById(R.id.images);

        updateHouseList(houseId);
    }

    private void updateHouseList(Long houseId)
    {
        StringRequest myReq = new StringRequest(Request.Method.GET, HOUSE_DETAIL_URL + houseId, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Gson gson = new Gson();
                house = gson.fromJson(response, House.class);

                fillInfo(house);

            }
        }, null)
        {
        };
        myReq.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(myReq);
    }

    private void fillInfo(House house)
    {
        for (Image image : house.getImages())
        {
            ImageView imageView = new ImageView(this);

            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(80,60));
            imageView.setMaxHeight(20);
            imageView.setMaxWidth(20);

            Picasso.with(getBaseContext()).load(image.getUrl()).into(imageView);
            imagesLayout.addView(imageView);
        }

        // other info

    }
}