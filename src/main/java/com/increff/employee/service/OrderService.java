package com.increff.employee.service;

import com.increff.employee.dao.OrderDao;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderDao dao;
    @Autowired
    private OrderItemService orderItemService;

    @Transactional(rollbackOn = ApiException.class)
    public void add(OrderPojo p, List<OrderItemPojo> pojos) throws ApiException {

        dao.insert(p);
        for(OrderItemPojo pojo: pojos){
            pojo.setOrderId(p.getId());
            orderItemService.add(pojo);
        }

    }

    @Transactional
    public void delete(int orderId){
        dao.delete(orderId);
        orderItemService.delete(orderId);
    }
    @Transactional
    public OrderPojo get(int id){
        return dao.select(id);
    }
    @Transactional
    public List<OrderPojo> getAll(){
        return dao.selectAll();
    }

    @Transactional
    public void update(int orderId, List<OrderItemPojo> pojos) throws ApiException {

    }
}
