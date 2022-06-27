package com.zte.zshop.service;

import com.zte.zshop.common.exception.SysuserNotExistException;
import com.zte.zshop.dto.SysuserDto;
import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.params.SysuserParams;

import java.util.List;

/**
 * Author:helloboy
 * Date:2022-04-24 9:10
 * Description:<描述>
 */
public interface SysuserService {


    public List<Sysuser> findAll();

    public Sysuser findById(int id);

    public void add(SysuserDto sysuserdto);

    public void modify(SysuserDto sysuserdto);

    public void modifyStatus(int id);

    public boolean checkName(String loginName);

    public List<Sysuser> findByParams(SysuserParams sysuserParams);

    public Sysuser login(String loginName, String password)throws SysuserNotExistException;
}
