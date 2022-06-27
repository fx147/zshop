package com.zte.zshop.service.impl;

import com.zte.zshop.common.constants.Constant;
import com.zte.zshop.common.exception.SysuserNotExistException;
import com.zte.zshop.dao.SysuserDao;
import com.zte.zshop.dto.SysuserDto;
import com.zte.zshop.entity.Role;
import com.zte.zshop.entity.Sysuser;
import com.zte.zshop.params.SysuserParams;
import com.zte.zshop.service.SysuserService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Author:helloboy
 * Date:2022-04-24 9:13
 * Description:<描述>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class SysuserServiceImpl implements SysuserService {

    @Autowired
    private SysuserDao sysuserDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Sysuser> findAll() {
        return sysuserDao.selectAll();
    }

    @Override
    public Sysuser findById(int id) {
        return sysuserDao.selectById(id);
    }

    @Override
    public void add(SysuserDto sysuserdto) {
        Sysuser sysuser = new Sysuser();
        try {
            PropertyUtils.copyProperties(sysuser,sysuserdto);
            //默认为有效转态
            sysuser.setIsValid(Constant.SYSUSER_VALID);
            //默认时间是当前时间
            sysuser.setCreateDate(new Date());
            //设置角色id
            Role role = new Role();
            role.setId(sysuserdto.getRoleId());
            sysuser.setRole(role);
            sysuserDao.insert(sysuser);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void modify(SysuserDto sysuserdto) {

    }


    @Override
    public void modifyStatus(int id) {
        Sysuser sysuser= sysuserDao.selectById(id);
        Integer isValid= sysuser.getIsValid();
        if(isValid==Constant.SYSUSER_VALID){
            isValid=Constant.SYSUSER_INVALID;
        }
        else{
            isValid=Constant.SYSUSER_VALID;
        }
        sysuserDao.updateStatus(id,isValid);

    }

    @Override
    public boolean checkName(String loginName) {
        Sysuser sysuser= sysuserDao.selectByName(loginName);
        if(sysuser!=null){
            return false;
        }
        return true;
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public List<Sysuser> findByParams(SysuserParams sysuserParams) {
        return sysuserDao.selectByParams(sysuserParams);
    }

    @Override
    @Transactional(propagation = Propagation.SUPPORTS,readOnly = true)
    public Sysuser login(String loginName, String password)throws SysuserNotExistException {
        Sysuser sysuser=sysuserDao.selectByLoginNameAndPass(loginName,password,Constant.SYSUSER_VALID);
        if(sysuser!=null){
            return sysuser;
        }
        throw new SysuserNotExistException("用户名或密码不能为空");
    }
}
