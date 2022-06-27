package com.zte.zshop.dao;

import com.zte.zshop.entity.Product;
import com.zte.zshop.params.ProductParam;

import java.util.List;

/**
 * Author:helloboy
 * Date:2022-04-16 15:29
 * Description:<描述>
 */
public interface ProductDao {
    public void insert(Product product);

    public Product selectByName(String name);

    public List<Product> selectAll();

    public Product selectById(Integer id);

    public void update(Product product);

    public void deleteById(Integer id);

    public List<Product> selectByParams(ProductParam productParam);
}
