package com.kocaeli.entity;

public class House
{
    private Long id;

    private String city;
    private String type;
    private Integer area;
    private Integer roomCount;
    private Integer buildingAge;
    private Integer floor;
    private Double price;
    private String description;

    private Iterable<Image> images;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getType()
    {
        return type;
    }

    public void setType(String type)
    {
        this.type = type;
    }

    public Integer getArea()
    {
        return area;
    }

    public void setArea(Integer area)
    {
        this.area = area;
    }

    public Integer getRoomCount()
    {
        return roomCount;
    }

    public void setRoomCount(Integer roomCount)
    {
        this.roomCount = roomCount;
    }

    public Integer getBuildingAge()
    {
        return buildingAge;
    }

    public void setBuildingAge(Integer buildingAge)
    {
        this.buildingAge = buildingAge;
    }

    public Integer getFloor()
    {
        return floor;
    }

    public void setFloor(Integer floor)
    {
        this.floor = floor;
    }

    public Double getPrice()
    {
        return price;
    }

    public void setPrice(Double price)
    {
        this.price = price;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public Iterable<Image> getImages()
    {
        return images;
    }

    public void setImages(Iterable<Image> images)
    {
        this.images = images;
    }
}