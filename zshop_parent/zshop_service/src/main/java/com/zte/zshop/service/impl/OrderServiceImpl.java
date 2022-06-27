package com.zte.zshop.service.impl;

import com.zte.zshop.dao.OrderDao;
import com.zte.zshop.dto.OrderDto;
import com.zte.zshop.entity.Customer;
import com.zte.zshop.entity.Order;
import com.zte.zshop.service.OrderService;
import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Author:helloboy
 * Date:2022-05-21 14:29
 * Description:<描述>
 */
@Service
@Transactional(propagation = Propagation.REQUIRED,rollbackFor = Exception.class)
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDao orderDao;

    @Override
    public void addOrder(OrderDto orderDto) {
        try {
            Order order = new Order();
            PropertyUtils.copyProperties(order,orderDto);
            Customer customer = new Customer();
            customer.setId(orderDto.getCustomerId());
            order.setCustomer(customer);
            orderDao.insertOrder(order);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
