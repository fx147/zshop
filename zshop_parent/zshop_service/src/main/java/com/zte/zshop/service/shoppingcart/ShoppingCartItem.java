package com.zte.zshop.service.shoppingcart;

import com.zte.zshop.entity.Product;

/**
 * Author:helloboy
 * Date:2022-05-14 15:24
 * Description:<描述>
 * 购物车明细对象
 */
public class ShoppingCartItem {

    //产品对象
    private Product product;

    //总价
    private int quantity;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(Product product) {
        this.product = product;
        this.quantity = 1;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setUqantity(int quantity) {
        this.quantity = quantity;
    }

    //获取该商品在购物车中的总价
    public double getItemMoney(){
        return product.getPrice()* this.quantity;
    }

    //默认商品数量+1
    public void increment(){
        this.quantity++;
    }
}
