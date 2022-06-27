package com.zte.zshop.service;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.common.exception.ProductTypeExistException;
import com.zte.zshop.entity.ProductType;

import java.util.List;

/**
 * Author:helloboy
 * Date:2022-04-08 14:18
 * Description:<描述>
 */
public interface ProductTypeService {

    public PageInfo<ProductType> findAll(Integer page, Integer rows);

    public void add(String productTypeName)throws ProductTypeExistException;

    public ProductType findById(Integer id);

    public void modifyName(Integer id, String name)throws ProductTypeExistException;

    public void removeById(Integer id);

    public void modifyStatus(Integer id,Integer status);

    public List<ProductType> findEnable(int status);
}
