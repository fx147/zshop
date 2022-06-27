package com.zte.zshop.entity;

import java.io.Serializable;

/**
 * Author:helloboy
 * Date:2022-04-08 10:53
 * Description:<描述>
 */
public class ProductType implements Serializable {

    private Integer id;

    private String name;

    private Integer status;

    public ProductType() {
    }

    public ProductType(Integer id, String name, Integer status) {
        this.id = id;
        this.name = name;
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
