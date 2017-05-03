package com.kocaeli.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kocaeli.dao.ImageRepository;
import com.kocaeli.entity.Image;
import com.kocaeli.service.ImageService;

@Service
public class ImageServiceImpl implements ImageService
{
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Iterable<Image> findAll()
    {
        return imageRepository.findAll();
    }

    @Override
    public Iterable<Image> findByHouse(Long houseId)
    {
        return imageRepository.findAllByHouseId(houseId);
    }

    @Override
    public void deleteByHouse(Long houseId)
    {
        imageRepository.deleteAllByHouseId(houseId);
    }

    @Override
    public void save(Image image)
    {
        imageRepository.save(image);
    }
}
