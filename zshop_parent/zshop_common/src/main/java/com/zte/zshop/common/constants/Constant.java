package com.zte.zshop.common.constants;

/**
 * Author:helloboy
 * Date:2022-04-08 16:54
 * Description:<描述>
 */
public class Constant {

    //当前页：默认第一页
    public static final int PAGE_NUM=1;

    //每一页显示的条数,默认5条
    public static final int PAGE_SIZE=3;

    //响应状态码：1--> 成功
    public static final int RESPONSE_STATUS_SUCCESS=1;
    //响应状态码：2--> 失败
    public static final int RESPONSE_STATUS_FAILURE=2;
    //响应状态码：3--> 未授权
    public static final int RESPONSE_NO_PERMISSION=3;

    //商品类型的启用:1--->启用
    public static final int PRODUCT_TYPE_ENABLE=1;
    //商品类型的禁用:2--->禁用
    public static final int PRODUCT_TYPE_DISABLE=0;

    //系统用户的有效状态：1-->有效
    public static final Integer SYSUSER_VALID=1;

    //系统用户的有效状态：0-->无效
    public static final Integer SYSUSER_INVALID=0;

    public static final Integer PAGE_SIZE_FRONT = 8;
    public static final String SEPARATOR = "/";
}
