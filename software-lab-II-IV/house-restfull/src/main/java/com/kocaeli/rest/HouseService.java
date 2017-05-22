package com.kocaeli.rest;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.kocaeli.dao.HouseDAO;
import com.kocaeli.entity.House;

@Path("/house")
@Resource
public class HouseService
{
    private HouseDAO houseDAO;

    @PostConstruct
    void init()
    {
        houseDAO = new HouseDAO();
    }

    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public List<House> getAll() throws Exception
    {
        return houseDAO.loadAllHouses();
    }

    @GET
    @Path("/detail/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public House getWithDetail(@PathParam("id") Long id) throws Exception
    {
        return houseDAO.loadHouseDetailById(id);
    }

}