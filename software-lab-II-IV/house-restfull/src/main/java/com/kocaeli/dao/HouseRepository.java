package com.kocaeli.dao;

import org.springframework.data.repository.CrudRepository;

import com.kocaeli.entity.House;

public interface HouseRepository extends CrudRepository<House, Long>
{


}