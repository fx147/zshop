package com.zte.zshop.front.shoppingcart;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2022-05-21 9:56
 * Description:<描述>
 */
public class Test {

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

    public static void main(String[] args) {
        String time=getOrderIdByTime();
        System.out.println(time);
    }
}
