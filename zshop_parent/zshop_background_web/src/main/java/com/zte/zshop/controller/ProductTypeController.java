package com.zte.zshop.controller;

import com.github.pagehelper.PageInfo;
import com.zte.zshop.common.constants.Constant;
import com.zte.zshop.common.constants.ResponseResult;
import com.zte.zshop.common.exception.ProductTypeExistException;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.service.ProductTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author:helloboy
 * Date:2022-04-08 10:42
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/productType")
public class ProductTypeController {

    @Autowired
    private ProductTypeService productTypeService;

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){

        //没有页号，默认第一页
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum= Constant.PAGE_NUM;
        }
        PageInfo<ProductType> pageInfo= productTypeService.findAll(pageNum, Constant.PAGE_SIZE);
        model.addAttribute("data",pageInfo);


        //返回产品列表页面
        return "productTypeManager";

    }
    @RequestMapping("/add")
    //将返回的java对象转换成js能够识别的json数据
    @ResponseBody
    public ResponseResult add(@RequestParam("name") String productTypeName){
        ResponseResult result = new ResponseResult();
        try {
            productTypeService.add(productTypeName);
            result.setStatus(Constant.RESPONSE_STATUS_SUCCESS);
            result.setMessage("添加成功");
        }
        catch (ProductTypeExistException e){
            //e.printStackTrace();
            result.setStatus(Constant.RESPONSE_STATUS_FAILURE);
            result.setMessage("商品类型已经存在");
        }

        return result;

    }

    @RequestMapping("/findById")
    @ResponseBody
    public ResponseResult findById(Integer id){
       ProductType productType= productTypeService.findById(id);
       return ResponseResult.success(productType);


    }

    @RequestMapping("/modifyName")
    @ResponseBody
    public ResponseResult modifyName(Integer id,String name){
        try {
            productTypeService.modifyName(id, name);
            return ResponseResult.success("修改商品类型成功");
        }
        catch (ProductTypeExistException e){
            return ResponseResult.fail(e.getMessage());
        }


    }

    //删除商品类型
    @RequestMapping("/deleteById")
    @ResponseBody
    public ResponseResult deleteById(Integer id){
        productTypeService.removeById(id);
        return  ResponseResult.success("删除成功");
    }

    //更新状态
    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(Integer id,Integer status){
        productTypeService.modifyStatus(id,status);
        return ResponseResult.success("更新状态成功");
    }

}
