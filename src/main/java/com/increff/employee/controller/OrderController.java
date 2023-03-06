package com.increff.employee.controller;


import com.increff.employee.dto.OrderDto;
import com.increff.employee.model.OrderData;
import com.increff.employee.model.OrderForm;
import com.increff.employee.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController

public class OrderController {

    @Autowired
    private OrderDto dto;


    @ApiOperation(value = "Adding a Order like edit order")
    @RequestMapping(path ="api/order/single",method = RequestMethod.POST)
    public void addOrder(){
        dto.addOrder();
    }

    @ApiOperation(value = "Deleting a order")
    @RequestMapping(path ="api/order/{orderId}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int orderId){
        dto.delete(orderId);
    }

    @ApiOperation(value = "Getting a order by OrderId")
    @RequestMapping(path ="api/order/{orderId}", method = RequestMethod.GET)
    public OrderData get(@PathVariable int orderId){
        return dto.get(orderId);
    }

    @ApiOperation(value = "Getting max order id")
    @RequestMapping(path ="api/order/single",method = RequestMethod.GET)
    public OrderData getMaxId(){
        return dto.getMaxId();
    }


    @ApiOperation(value = "Getting all orders")
    @RequestMapping(path = "api/order",method = RequestMethod.GET)
    public List<OrderData> getAll(){
        return dto.getAll();
    }



}
