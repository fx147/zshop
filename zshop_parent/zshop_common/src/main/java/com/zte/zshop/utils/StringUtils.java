package com.zte.zshop.utils;

import com.zte.zshop.common.constants.Constant;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2022-04-16 16:18
 * Description:<描述>
 * 字符串工具类
 */
public class StringUtils {

    //根据得到的文件名称，生成一个对应的随机文件名称
    //如：afdf.ftf.jpg
    public static String renameFileName(String fileName){

        int dotIndex = fileName.lastIndexOf(".");
        //获取后缀，如.jpg
        String suffix=fileName.substring(dotIndex);

        return new SimpleDateFormat("yyyyMMddhhmmss").format(new Date())+new Random().nextInt(100) +suffix;

    }

    //通过哈希算法获取二级目录
    public static String generateRandomDir(String fileName) {
        int hashCode= fileName.hashCode();
        //获取一级目录
        //得到1-16以内的数字文件夹
        int dir1 = hashCode & 0xf;

        //得到1-16以内的二级目录
        int dir2=(hashCode & 0xf0)>>4;

        return Constant.SEPARATOR +dir1+Constant.SEPARATOR+dir2;

    }

    public static void main(String[] args) {
        String dieName=generateRandomDir("mike1");
        System.out.println(dieName);
    }
}
