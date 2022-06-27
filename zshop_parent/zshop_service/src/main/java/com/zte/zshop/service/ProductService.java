package com.zte.zshop.service;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.dto.ProductDto;
import com.zte.zshop.entity.Product;
import com.zte.zshop.params.ProductParam;
import com.zte.zshop.service.shoppingcart.ShoppingCart;
import org.apache.commons.fileupload.FileUploadException;

import java.io.OutputStream;
import java.util.List;

/**
 * Author:helloboy
 * Date:2022-04-16 14:51
 * Description:<描述>
 */
public interface ProductService {

    public void add(ProductDto productDto) throws FileUploadException;

    public boolean checkName(String name);

    public PageInfo<Product> findAll(Integer pageNum, int pageSize);

    public Product findById(Integer id);

    public void modifyProduct(ProductDto productDto) throws FileUploadException;

    public void removeProduct(Integer id);

    public void getImage(String path, OutputStream out);

    public List<Product> findByParamforFront(ProductParam productParam);

    public boolean addToCart(int id, ShoppingCart sc);

    public void removeItemFromShoppingCart(ShoppingCart sc, int id);

    public void clearShoppingCart(ShoppingCart sc);

    public void removeItemsFromShoppingCart(ShoppingCart sc, int[] ids);

    public void modifyItemQuantity(ShoppingCart sc, int id, int quantity);
}
