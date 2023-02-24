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

    public void add(List<OrderForm> forms) throws ApiException {
        OrderPojo p= new OrderPojo();
        p.setTime(java.time.LocalDateTime.now());
        service.add(p,convert(forms));

    }
    public void delete(int orderId){
        service.delete(orderId);
    }

    public OrderData get(int orderId){
        return convert(service.get(orderId));
    }
    public List<OrderData> getAll(){
        List<OrderData> list1= new ArrayList<OrderData>();
        List<OrderPojo> list2= service.getAll();
        for(OrderPojo pojo:list2){
            list1.add(convert(pojo));
        }
        return list1;
    }

    public void update(int orderId, List<OrderForm> forms ) throws ApiException {
        service.update(orderId,convert(forms));
    }


    // convert functions
    public List<OrderItemPojo> convert(List<OrderForm> forms) throws ApiException {
        List<OrderItemPojo> pojos= new ArrayList<OrderItemPojo>();

        for(OrderForm form : forms)
        {
            OrderItemPojo pojo = new OrderItemPojo();
            ProductPojo productPojo= productService.get(form.getBarcode());
            if(productPojo==null){
                throw new ApiException("Sorry, "+ form.getBarcode()+" do not exist.");
            }
            pojo.setProductId(productPojo.getId());
            pojo.setQuantity(form.getQuantity());
            pojo.setSellingPrice(form.getSellingPrice());
            pojos.add(pojo);
        }
        return pojos;
    }

    private OrderData convert(OrderPojo p){
        OrderData d= new OrderData();
        d.setId(p.getId());
        d.setTime(p.getTime());
        return d;
    }

}

