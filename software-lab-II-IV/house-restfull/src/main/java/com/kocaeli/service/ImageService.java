package com.kocaeli.service;


import com.kocaeli.entity.Image;

public interface ImageService
{
    Iterable<Image> findAll();

    Iterable<Image> findByHouse(Long houseId);

    void deleteByHouse(Long houseId);

    void save(Image image);
}
