package com.kocaeli.service;


import com.kocaeli.entity.House;

public interface HouseService
{
    Iterable<House> findAll();

    Iterable<House> findAllWithImages();

    void saveOrUpdate(House house);

    void delete(Long houseId);

    House loadWithDetail(Long id);
}
