package com.increff.employee.dto;

import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.OrderService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static com.increff.employee.util.ConvertFunction.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderDto {

    @Autowired
    private OrderService service;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrderItemService orderItemService;


    public void addOrder(){
        OrderPojo p= new OrderPojo();
        p.setTime(java.time.LocalDateTime.now());
        service.addOrder(p);
    }
    public void delete(int orderId){
        service.delete(orderId);
    }

    public OrderData get(int orderId){
        return orderConvert(service.get(orderId));
    }

    public OrderData getMaxId(){
        return orderConvert(service.getMaxId());
    }
    public List<OrderData> getAll(){
        List<OrderData> list1= new ArrayList<OrderData>();
        List<OrderPojo> list2= service.getAll();
        for(OrderPojo pojo:list2){
            list1.add(orderConvert(pojo));
        }
        return list1;
    }


}

