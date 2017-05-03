package com.kocaeli.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.kocaeli.entity.House;
import com.kocaeli.service.HouseService;

@Controller
public class HouseController
{

    @Autowired
    private HouseService houseService;

    @GetMapping
    @ResponseBody
    public Iterable<House> findAll()
    {
        return houseService.findAllWithImages();
    }


    @PostMapping("/save")
    public void saveOrUpdate(@RequestParam("house") House house)
    {
        houseService.saveOrUpdate(house);
    }


    @PostMapping("/delete")
    public void delete(@RequestParam("id") Long id)
    {
        houseService.delete(id);
    }

}
