package com.kocaeli.dao;

import org.springframework.data.repository.CrudRepository;

import com.kocaeli.entity.Image;

public interface ImageRepository extends CrudRepository<Image, Long>
{
    Iterable<Image> findAllByHouseId(Long houseId);

    void deleteAllByHouseId(Long houseId);
}