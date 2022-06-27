package com.zte.zshop.service;

import com.zte.zshop.common.exception.LoginErrorException;
import com.zte.zshop.entity.Customer;

/**
 * Author:helloboy
 * Date:2022-05-13 11:31
 * Description:<描述>
 */
public interface CustomerService {

    public Customer login(String loginName, String password)throws LoginErrorException;
}
