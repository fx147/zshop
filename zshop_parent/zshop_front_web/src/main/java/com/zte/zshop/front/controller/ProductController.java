package com.zte.zshop.front.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.common.constants.Constant;
import com.zte.zshop.common.constants.ResponseResult;
import com.zte.zshop.dto.OrderDto;
import com.zte.zshop.entity.Product;
import com.zte.zshop.entity.ProductType;
import com.zte.zshop.front.shoppingcart.ShoppingCartUtils;
import com.zte.zshop.params.ProductParam;
import com.zte.zshop.service.OrderService;
import com.zte.zshop.service.ProductService;
import com.zte.zshop.service.ProductTypeService;
import com.zte.zshop.service.shoppingcart.ShoppingCart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:helloboy
 * Date:2022-04-30 9:16
 * Description:<描述>
 */
@Controller
@RequestMapping("/front/product")
public class ProductController {

    @Autowired
    private ProductTypeService productTypeService;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderService orderService;

    @ModelAttribute("productTypes")
    public List<ProductType> loadProductTypes(){

       List<ProductType> productTypes= productTypeService.findEnable(Constant.PRODUCT_TYPE_ENABLE);
       return productTypes;

    }

    @RequestMapping("/main")
    public String main(ProductParam productParam, Integer pageNum, Model model){

        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,Constant.PAGE_SIZE_FRONT);
        List<Product> products= productService.findByParamforFront(productParam);
        PageInfo<Product> pageInfo = new PageInfo<>(products);
        model.addAttribute("data",pageInfo);
        model.addAttribute("productParam",productParam);
        return "main";
    }

    @RequestMapping("/showPic")
    public void showPic(String image, OutputStream out)throws IOException{
        //把http请求读取为流
        URL url = new URL(image);
        URLConnection urlConnection = url.openConnection();
        InputStream is = urlConnection.getInputStream();
        BufferedOutputStream bos = new BufferedOutputStream(out);
        byte[] data = new byte[4096];
        int size = is.read(data);
        while(size!=-1){
            bos.write(data,0,size);
            size=is.read(data);
        }
        is.close();
        bos.flush();
        bos.close();
    }

    @RequestMapping("/addToCart")
    @ResponseBody
    public ResponseResult addToCart(int id, HttpSession session){
        //从session中获取购物车对象
        ShoppingCart sc= ShoppingCartUtils.getShppingCart(session);
        //调用service中的addToCart方法，完成购物逻辑
        boolean flag=productService.addToCart(id,sc);
        if(flag){
            return ResponseResult.success("购物成功");
        }
        else{
            return ResponseResult.fail("购物失败");
        }
    }

    /*@RequestMapping("/removeItemById")
    @ResponseBody
    public ResponseResult removeItemById(int id,HttpSession session){
       ShoppingCart sc= ShoppingCartUtils.getShppingCart(session);
       productService.removeItemFromShoppingCart(sc,id);
       if(sc.isEmpty()){
           //返回购物车已空提示
           return ResponseResult.fail("购物车已空");
       }
       //重新计算总价
       Float totalMoney=sc.getTotalMoney();
       return ResponseResult.success(totalMoney);
    }*/

    @RequestMapping("/removeItemByIds")
    @ResponseBody
    public ResponseResult removeItemByIds(int[] ids,HttpSession session){
       ShoppingCart sc= ShoppingCartUtils.getShppingCart(session);
       productService.removeItemsFromShoppingCart(sc,ids);
       if(sc.isEmpty()){
           //返回购物车已空提示
           return ResponseResult.fail("购物车已空");
       }
       //重新计算总价
       Float totalMoney=sc.getTotalMoney();
       return ResponseResult.success(totalMoney);
    }

    //清空购物车
    @RequestMapping("/clear")
    @ResponseBody
    public ResponseResult clear(HttpSession session){
      ShoppingCart sc=  ShoppingCartUtils.getShppingCart(session);
      productService.clearShoppingCart(sc);
      return ResponseResult.success("清空购物车成功");
    }

    //生成订单
    @RequestMapping("/generateOrder")
    @ResponseBody
    public ResponseResult generateOrder(OrderDto orderDto,HttpSession session){

        try {
            String no=ShoppingCartUtils.getOrderIdByTime();
            orderDto.setNo(no);
            orderDto.setCreateDate(new Date());
            orderService.addOrder(orderDto);
            //session.invalidate();
            session.removeAttribute("shoppingcart");
            return ResponseResult.success(orderDto);
        } catch (Exception e) {
            //e.printStackTrace();
            return ResponseResult.fail("生成订单失败");
        }
    }

    //更新商品数量
    @RequestMapping("/updateItemQuantity")
    @ResponseBody
    public Map<String,Object> updateItemQuantity(int id, int quantity, HttpSession session){
        ShoppingCart sc=ShoppingCartUtils.getShppingCart(session);
        productService.modifyItemQuantity(sc,id,quantity);
        //返回json格式数据
        Map<String,Object> result = new HashMap<>();
        result.put("itemMoney",sc.getProducts().get(id).getItemMoney());
        result.put("totalMoney",sc.getTotalMoney());
        return result;
    }


}
