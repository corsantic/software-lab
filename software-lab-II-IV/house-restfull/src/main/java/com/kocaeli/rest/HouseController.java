package com.kocaeli.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kocaeli.entity.House;
import com.kocaeli.service.HouseService;

@RestController
@RequestMapping(produces = "application/json")
public class HouseController
{
    @Autowired
    private HouseService houseService;

    @RequestMapping("/")
    public Iterable<House> findAll()
    {
        return houseService.findAll();
    }

    @RequestMapping("/house/detail")
    public House find(@RequestParam("id") Long id)
    {
        return houseService.loadWithDetail(id);
    }

    @RequestMapping(value = "/house/save", method = RequestMethod.POST,  consumes = "application/json")
    public void saveOrUpdate(@RequestParam("house") House house)
    {
        houseService.saveOrUpdate(house);
    }


    @RequestMapping(value = "/house/delete", method = RequestMethod.POST, consumes = "application/json")
    public void delete(@RequestParam("id") Long id)
    {
        houseService.delete(id);
    }
}
