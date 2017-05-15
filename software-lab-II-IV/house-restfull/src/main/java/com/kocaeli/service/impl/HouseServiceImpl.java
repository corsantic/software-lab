package com.kocaeli.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kocaeli.dao.HouseRepository;
import com.kocaeli.entity.House;
import com.kocaeli.entity.Image;
import com.kocaeli.service.HouseService;
import com.kocaeli.service.ImageService;

@Service
public class HouseServiceImpl implements HouseService
{
    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private ImageService imageService;

    @Override
    public Iterable<House> findAll()
    {
        return houseRepository.findAll();
    }

    @Override
    public Iterable<House> findAllWithImages()
    {
        Iterable<House> all = houseRepository.findAll();
        all.forEach(house ->
        {
            Iterable<Image> houseImages = imageService.findByHouse(house.getId());
            house.setImages(houseImages);
        });

        return all;
    }

    @Override
    public void saveOrUpdate(House house)
    {
        houseRepository.save(house); // or update?
        house.getImages().forEach(imageService::save);
    }

    @Override
    public void delete(Long houseId)
    {
        imageService.deleteByHouse(houseId);
        houseRepository.deleteById(houseId);
    }

    @Override
    public House loadWithDetail(Long id)
    {
        House house = houseRepository.findById(id).get();
        house.setImages(imageService.findByHouse(house.getId()));
        return house;
    }
}
