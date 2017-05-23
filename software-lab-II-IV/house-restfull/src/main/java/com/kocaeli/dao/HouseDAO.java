package com.kocaeli.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.kocaeli.entity.House;
import com.kocaeli.entity.Image;

public class HouseDAO
{
    Connection connection;
    
    public List<House> loadAllHouses() throws Exception
    {
        List<House> houses = new ArrayList<>();

        ResultSet rs = executeQuery("select * from house");
        while (rs.next())
        {
            houses.add(ResultMapper.resultSetHouseMapping(rs));
        }
        connection.close();
        return houses;
    }

    public House loadHouseDetailById(Long id) throws Exception
    {
        ResultSet rs = executeQuery("select * from house where id = ?", id);
        House house = ResultMapper.resultSetHouseMapping(rs);
        house.setImages(loadImagesByHouseId(id));
        connection.close();
        return house;
    }

    public List<Image> loadImagesByHouseId(Long id) throws Exception
    {
        List<Image> images = new ArrayList<>();
        ResultSet rs = executeQuery("select * from image where id = ?", id);
        while (rs.next())
        {
            images.add(ResultMapper.resultSetHouseImageMapping(rs));
        }
        connection.close();
        return images;
    }

    private ResultSet executeQuery(String sql, Object... args) throws Exception
    {
        createConnection();
        PreparedStatement statement = connection.prepareStatement(sql);
        for (int i = 0; i < args.length; i++)
        {
            statement.setObject(i + 1, args[i]);

        }
        return statement.executeQuery();
    }

    private void createConnection()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection("jdbc:sqlite:/opt/kou.db");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}