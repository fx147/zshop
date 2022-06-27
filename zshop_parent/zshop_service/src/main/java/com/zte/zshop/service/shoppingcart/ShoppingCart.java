package com.zte.zshop.service.shoppingcart;

import com.zte.zshop.entity.Product;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Author:helloboy
 * Date:2022-05-14 16:42
 * Description:<描述>
 * 购物车对象
 * 该对象用于封装购物车具体逻辑功能，完成购物逻辑
 *
 */
public class ShoppingCart {

    private Map<Integer,ShoppingCartItem> products = new HashMap<>();

    public Map<Integer, ShoppingCartItem> getProducts() {
        return products;
    }

    /*
        向购物车添加一件商品
        逻辑：
        查看当前购物车是否已经有该商品，如果有，不增加记录，只进行数量+1，如果没有，在购物车中新增一条记录，数量初始化为1
     */
    public void addProduct(Product product){
        ShoppingCartItem sci=products.get(product.getId());
        if(sci==null){
            sci=  new ShoppingCartItem(product);
            products.put(product.getId(),sci);
        }
        else{
           sci.increment();
        }
    }

    //查看该购物车中是否有该商品
    public boolean hasProduct(int id){
        return products.containsKey(id);

    }

    //获取购物车中商品数量之和
    public int getProductNumber(){
        int total=0;
        for(ShoppingCartItem sci:products.values()){
            total+=sci.getQuantity();
        }
        return total;
    }

    //获取购物车中记录集合
    public Collection<ShoppingCartItem> getItems(){
        return products.values();
    }

    //获取购物车中的商品总价
    public float getTotalMoney(){
        float total=0;
        for(ShoppingCartItem sci:getItems()){
            total+=sci.getItemMoney();
        }
        return total;
    }

    //判断购物车是否为空
    public boolean isEmpty(){
        return products.isEmpty();
    }


    //清空购物车
    public void clear(){
        products.clear();
    }

    //移除指定id的购物明细
    public void removeItem(int id){
        products.remove(id);
    }

    //修改指定购物明细的数量
    public void updateItemQuantity(int id,int quantity){
      ShoppingCartItem sci=  products.get(id);
      if(sci!=null){
          sci.setUqantity(quantity);
      }

    }





}
