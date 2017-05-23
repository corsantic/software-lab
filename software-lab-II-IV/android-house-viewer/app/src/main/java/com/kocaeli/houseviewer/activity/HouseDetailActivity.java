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
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class HouseDetailActivity extends AppCompatActivity
{
    private static final String HOUSE_DETAIL_URL = "http://ferdielik.me:7070/rest/house/detail/";

    private House house;
    private LinearLayout imagesView;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.house_detail);
        Long houseId = getIntent().getExtras().getLong("houseId");

        imagesView = (LinearLayout) findViewById(R.id.images);


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
            imagesView.addView(buildImageView(image));
        }
        TextView textView = (TextView) findViewById(R.id.houseInfo);
        textView.setText("Şehir:" + house.getCity() + "\n"
                + "Tip:" + house.getType() + "\n"
                + "Alan:" + house.getArea() + "\n" +
                "Oda Sayısı:" + house.getRoomCount()
                + "\n" +
                "Yapılış Tarihi:" + house.getBuildingAge() + "\n"
                + "Kat:" + house.getFloor() + "\n" +
                "Fiyat:" + house.getPrice() + "\n" +
                "Açıklama:" + house.getDescription() + "\n");

    }

    private ImageView buildImageView(Image image)
    {
        ImageView imageView = new ImageView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(10, 10, 10, 20);
        lp.gravity = Gravity.CENTER;
        imageView.setLayoutParams(lp);
        Picasso.with(getBaseContext()).load(image.getUrl()).into(imageView);
        return imageView;
    }
}