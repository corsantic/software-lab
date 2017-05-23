package com.kocaeli.houseviewer.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

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

public class HouseDetailActivity extends AppCompatActivity {
    private static final String HOUSE_DETAIL_URL = "http://ferdielik.me:7070/rest/house/detail/";

    private House house;
    private LinearLayout imagesLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.house_detail);
        Long houseId = getIntent().getExtras().getLong("houseId");

        imagesLayout = (LinearLayout) findViewById(R.id.images);


        updateHouseList(houseId);
    }

    private void updateHouseList(Long houseId) {
        StringRequest myReq = new StringRequest(Request.Method.GET, HOUSE_DETAIL_URL + houseId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
                house = gson.fromJson(response, House.class);

                fillInfo(house);

            }
        }, null) {
        };
        myReq.setShouldCache(false);

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(myReq);
    }

    private void fillInfo(House house) {
        for (Image image : house.getImages()) {

            ImageView imageView = new ImageView(this);

            imageView.setLayoutParams(new android.view.ViewGroup.LayoutParams(1280, 720));
            imageView.setMaxHeight(1080);
            imageView.setMaxWidth(720);
            image.setUrl("http://lorempixel.com/400/200/");//set etmemisiz reis :D
            //// TODO: 23.5.2017 farklı url alması saglancak
            Picasso.with(getBaseContext()).load(image.getUrl()).into(imageView);
            imagesLayout.addView(imageView);
        }
        TextView textView = (TextView) findViewById(R.id.text);
        textView.setText("Şehir:" + house.getCity() + "\n"
                + "Tip:" + house.getType() + "\n"
                + "Alan:" + house.getArea() + "\n" +
                "Oda Sayısı:" + house.getRoomCount()
                + "\n" +
                "Yapılış Tarihi:" + house.getBuildingAge() + "\n"
                + "Kat:" + house.getFloor() + "\n" +
                "Fiyat:" + house.getPrice() + "\n" +
                "Açıklama:" + house.getDescription() + "\n");


//
//        private Long id;
//
//        private String city;
//        private String type;
//        private Integer area;
//        private Integer roomCount;
//        private Integer buildingAge;
//        private Integer floor;
//        private Double price;
//        private String description;


        // other info

    }
}