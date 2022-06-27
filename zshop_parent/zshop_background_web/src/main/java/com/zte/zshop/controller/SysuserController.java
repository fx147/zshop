package com.zte.zshop.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.zte.zshop.common.constants.Constant;
import com.zte.zshop.common.constants.ResponseResult;
import com.zte.zshop.dto.SysuserDto;
import com.zte.zshop.entity.Role;
import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.params.SysuserParams;
import com.zte.zshop.service.RoleService;
import com.zte.zshop.service.SysuserService;
import com.zte.zshop.vo.SysuserVO;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Author:helloboy
 * Date:2022-04-08 10:31
 * Description:<描述>
 */
@Controller
@RequestMapping("/backend/sysuser")
public class SysuserController {

    @Autowired
    private SysuserService sysuserService;

    @Autowired
    private RoleService roleService;

    @ModelAttribute("roles")
    public List<Role> loadRoles(){
        return roleService.findAll();
    }

    @RequestMapping("/login")
    public String login(String loginName,String password,HttpSession session,Model model){

        try {
            Sysuser sysuser= sysuserService.login(loginName,password);
            session.setAttribute("sysuser",sysuser);
            return "main";
        } catch (Exception e) {
            e.printStackTrace();
            model.addAttribute("errorMsg","登录异常");
            return "login";

        }
    }

    @RequestMapping("/findAll")
    public String findAll(Integer pageNum,Model model){

        if(ObjectUtils.isEmpty(pageNum)){
            pageNum = Constant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,3);
        List<Sysuser> sysusers= sysuserService.findAll();
        PageInfo<Sysuser> pageInfo = new PageInfo<>(sysusers);
        model.addAttribute("data",pageInfo);
        return "sysuserManager";
    }

    @RequestMapping("/add")
    @ResponseBody
    public ResponseResult add(SysuserVO sysuserVO){
        SysuserDto sysuserDto = new SysuserDto();
        try {
            PropertyUtils.copyProperties(sysuserDto,sysuserVO);
            sysuserService.add(sysuserDto);
            return ResponseResult.success("添加成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("添加失败");
        }

    }

    @RequestMapping("/checkName")
    @ResponseBody
    public Map<String,Object> checkName(String loginName){

        Map<String,Object> map = new HashMap<>();
        boolean res=sysuserService.checkName(loginName);
        //如果名字不存在，可用
        if(res){
            map.put("valid",true);
        }
        else{
            map.put("valid",false);
            map.put("message","账号【"+loginName+"】已经存在");
        }


        return map;

    }

    @RequestMapping("/findByParams")
    public String findByParams(SysuserParams sysuserParams,Integer pageNum,Model model){
        if(ObjectUtils.isEmpty(pageNum)){
            pageNum=Constant.PAGE_NUM;
        }
        PageHelper.startPage(pageNum,Constant.PAGE_SIZE);
        List<Sysuser> sysusers= sysuserService.findByParams(sysuserParams);
        PageInfo<Sysuser> pageInfo = new PageInfo<>(sysusers);
        model.addAttribute("data",pageInfo);
        model.addAttribute("sysuserParams",sysuserParams);
        return "sysuserManager";
    }

    @RequestMapping("/modifyStatus")
    @ResponseBody
    public ResponseResult modifyStatus(int id){

        try {
            sysuserService.modifyStatus(id);
            return ResponseResult.success("更新成功");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseResult.fail("更新失败");
        }
    }

    @RequestMapping("/image")
    //实现2个功能：
    //1:4个随机字符(数字，字符(a-zA-Z))
    //2:将该字符生成一张图片
    public void image(HttpServletResponse resp, HttpSession session) throws IOException {

        //创建图片缓冲区
        BufferedImage bi = new BufferedImage(68,22,BufferedImage.TYPE_INT_RGB);
        //创建画笔
        Graphics gr = bi.getGraphics();
        Color c = new Color(200,150,255);
        gr.setColor(c);
        //在画布上绘制一个矩形框
        gr.fillRect(0,0,68,22);



        char[] ch = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        int len = ch.length;
        int index;
        for(int i=0;i<4;i++){
            index= random.nextInt(len);
            //重新设置画笔颜色
            gr.setColor(new Color(random.nextInt(88),random.nextInt(100),random.nextInt(120)));
            gr.drawString(ch[index]+"",(i*15)+3,18);
            sb.append(ch[index]);
        }
        //System.out.println(sb.toString());
        session.setAttribute("picCode",sb.toString());
        //将改图片写入输出流
        ImageIO.write(bi,"jpg",resp.getOutputStream());

    }

    @RequestMapping("/checkCode")
    @ResponseBody
    public Map<String,Object> checkCode(String code,HttpSession session){
        Map<String,Object> map = new HashMap<>();
        String picCode=(String)session.getAttribute("picCode");
        if(picCode.equalsIgnoreCase(code)){
            map.put("valid",true);
        }
        else{
            map.put("valid",false);
        }
        return map;
    }






}
