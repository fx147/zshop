package com.zte.zshop.common.constants;

/**
 * Author:helloboy
 * Date:2022-04-09 14:35
 * Description:<描述>
 *     响应结果对象，该对象用于封装用户返回信息
 */
public class ResponseResult {
    //状态码
    private Integer status;

    //消息
    private String message;

    //返回数据
    private Object data;

    public ResponseResult() {
    }

    public ResponseResult(Integer status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public static ResponseResult success(Object obj){
        return  new ResponseResult(Constant.RESPONSE_STATUS_SUCCESS,"success",obj);
    }
    public static ResponseResult success(String message){
        return  new ResponseResult(Constant.RESPONSE_STATUS_SUCCESS,message,null);
    }
    public static ResponseResult fail(String message){
        return  new ResponseResult(Constant.RESPONSE_STATUS_FAILURE,message,null);
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}

