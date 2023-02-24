package com.increff.employee.dto;

import com.increff.employee.model.OrderItemData;
import com.increff.employee.model.OrderItemForm;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.OrderItemService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Repository
public class OrderItemDto {

    @Autowired
    private OrderItemService service;
    @Autowired
    private ProductService productService;


    public void add(OrderItemForm form) throws ApiException {
        service.add(convert(form));
    }
    public void delete_id(@PathVariable int id){
        service.delete_id(id);
    }
    public List<OrderItemData> get(@PathVariable int orderId){
        List<OrderItemData> list1= new ArrayList<OrderItemData>();
        List<OrderItemPojo> list2= service.get(orderId);

        for(OrderItemPojo pojo: list2){
            list1.add(convert(pojo));
        }

        return list1;

    }

    public OrderItemData get_id(int id){

         return convert(service.get_id(id));
    }

    public List<OrderItemData> getAll(){
        List<OrderItemData> list1= new ArrayList<OrderItemData>();
        List<OrderItemPojo> list2= service.getAll();
        for(OrderItemPojo pojo: list2){
            list1.add(convert(pojo));
        }
        return list1;
    }
    public void update(int id,OrderItemForm form) throws ApiException{
        service.update(id, convert(form));
    }


    //Conversion Functions

    private OrderItemData convert(OrderItemPojo p){
        ProductPojo productPojo=productService.get(p.getProductId());
        OrderItemData d= new OrderItemData();
        d.setId(p.getId());
        d.setOrderId(p.getOrderId());
        d.setProductId(p.getProductId());
        d.setQuantity(p.getQuantity());
        d.setSellingPrice(p.getSellingPrice());
        d.setBarcode(productPojo.getBarcode());
        return d;
    }

    private OrderItemPojo convert(OrderItemForm f) throws ApiException {

        ProductPojo productPojo= productService.get(f.getBarcode());
        if(productPojo==null){
            throw new ApiException("Sorry, "+ f.getBarcode()+" do not exist.");
        }
        OrderItemPojo p= new OrderItemPojo();
        p.setOrderId(f.getOrderId());
        p.setProductId(productPojo.getId());
        p.setQuantity(f.getQuantity());
        p.setSellingPrice(f.getSellingPrice());
        return p;
    }
}


