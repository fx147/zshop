package com.zte.zshop.dao;

import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.params.SysuserParams;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Author:helloboy
 * Date:2022-04-24 8:42
 * Description:<描述>
 */
public interface SysuserDao{

    public List<Sysuser> selectAll();

    public Sysuser selectById(int id);

    public void insert(Sysuser sysuser);

    public void update(Sysuser sysuser);

    public void updateStatus(@Param("id") int id, @Param("isValid") int isValid);


    public Sysuser selectByName(String loginName);

    public List<Sysuser> selectByParams(SysuserParams sysuserParams);

    public Sysuser selectByLoginNameAndPass(@Param("loginName") String loginName,@Param("password") String password, @Param("isValid") Integer isValid);
}
