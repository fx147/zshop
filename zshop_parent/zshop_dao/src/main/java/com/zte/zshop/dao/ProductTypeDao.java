package com.zte.zshop.dao;

import com.zte.zshop.entity.ProductType;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author:helloboy
 * Date:2022-04-08 10:55
 * Description:<描述>
 */

public interface ProductTypeDao {

    public List<ProductType> findAll();

    public ProductType selectByName(String productTypeName);

    public void insert(@Param("name") String productTypeName,@Param("status") int enable);

    ProductType selectById(Integer id);

    public void deleteById(Integer id);

    public void updateName(@Param("id") Integer id,@Param("name")String name);

    public void updateStatus(@Param("id") Integer id,@Param("status")Integer status);

    public List<ProductType> selectByStatus(int status);
}
