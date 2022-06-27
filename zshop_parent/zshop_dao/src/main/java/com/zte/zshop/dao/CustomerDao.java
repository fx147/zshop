package com.zte.zshop.dao;

import com.zte.zshop.entity.Customer;
import org.apache.ibatis.annotations.Param;

/**
 * Author:helloboy
 * Date:2022-05-13 14:32
 * Description:<描述>
 */
public interface CustomerDao {
    public Customer selectByLoginNameAndPass(@Param("loginName") String loginName, @Param("password") String password,@Param("isValid") Integer isValid);
}
