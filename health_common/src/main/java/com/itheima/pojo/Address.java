package com.itheima.pojo;

import java.io.Serializable;

public class Address implements Serializable {
    private Integer id;//主键
    private String checkAddress;//体检地址
    private double longitude;//经度
    private double dimension;//维度
    private String description;//描述

    public void setId(Integer id) {
        this.id = id;
    }

    public void setCheckAddress(String checkAddress) {
        this.checkAddress = checkAddress;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public void setDimension(double dimension) {
        this.dimension = dimension;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getCheckAddress() {
        return checkAddress;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getDimension() {
        return dimension;
    }

    public String getDescription() {
        return description;
    }
}
