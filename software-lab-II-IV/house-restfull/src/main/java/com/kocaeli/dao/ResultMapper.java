package com.kocaeli.dao;

import java.sql.ResultSet;

import com.kocaeli.entity.House;
import com.kocaeli.entity.Image;

public class ResultMapper
{
    public static House resultSetHouseMapping(ResultSet rs)
    {
        House house = new House();
        try
        {
            house.setArea(rs.getInt("area"));
            house.setBuildingAge(rs.getInt("building_age"));
            house.setCity(rs.getString("city"));
            house.setFloor(rs.getInt("floor"));
            house.setId(rs.getLong("id"));
            house.setDescription(rs.getString("description"));
            house.setType(rs.getString("type"));
            house.setPrice(rs.getDouble("price"));
            house.setRoomCount(rs.getInt("room_count"));
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return house;
    }

    public static Image resultSetHouseImageMapping(ResultSet rs)
    {
        Image image = new Image();
        try
        {
            image.setId(rs.getLong("id"));
            image.setHouseId(rs.getLong("house_id"));
            image.setUrl(rs.getString("url"));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return image;
    }
}
