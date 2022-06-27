package com.zte.zshop.front.shoppingcart;

import com.zte.zshop.service.shoppingcart.ShoppingCart;

import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2022-05-14 17:15
 * Description:<描述>
 * 购物车工具类：获取购物车对象
 * 使用单例设计模式
 */
public class ShoppingCartUtils {

    /*
        逻辑：
        从session作用域中获取购物车对象，若session中没有该对象，创建一个新的对象，放入session作用域，若有，直接返回
        (懒汉式单例)
     */
    public static ShoppingCart getShppingCart(HttpSession session){
       ShoppingCart sc= (ShoppingCart) session.getAttribute("shoppingcart");
       if(sc==null){
           sc = new ShoppingCart();
           session.setAttribute("shoppingcart",sc);
       }

       return sc;
    }

    //根据时间生成一个订单号
    public static String getOrderIdByTime(){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random = new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }


        return newDate+result;

    }




}
