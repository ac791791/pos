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
@RequestMapping("/api/order")
public class OrderController {

    @Autowired
    private OrderDto dto;

    @ApiOperation(value = "Adding a order")
    @RequestMapping( method = RequestMethod.POST)
    public void add(@RequestBody List<OrderForm> forms) throws ApiException {
        dto.add(forms);
    }

    @ApiOperation(value = "Deleting a order")
    @RequestMapping(value ="/{orderId}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int orderId){
        dto.delete(orderId);
    }

    @ApiOperation(value = "Getting a order by OrderId")
    @RequestMapping(value ="/{orderId}", method = RequestMethod.GET)
    public OrderData get(@PathVariable int orderId){
        return dto.get(orderId);
    }

    @ApiOperation(value = "Getting all orders")
    @RequestMapping(method = RequestMethod.GET)
    public List<OrderData> getAll(){
        return dto.getAll();
    }

    @ApiOperation(value = "Updating a existing order")
    @RequestMapping(value ="/{orderId}", method = RequestMethod.PUT)
    private void update(@PathVariable int orderId, @RequestBody List<OrderForm> forms) throws ApiException {
        dto.update(orderId,forms);
    }


}
