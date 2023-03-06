package com.increff.employee.util;

import com.increff.employee.model.*;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderPojo;
import com.increff.employee.pojo.ProductPojo;


public class ConvertFunction {



    //BrandDto Converts
    public static BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setId(p.getId());
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        return d;
    }

    public static BrandPojo convert(BrandForm f) {
        BrandPojo p= new BrandPojo();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
        return p;
    }

    // ProductDto converts
    public static ProductPojo productConvert(ProductForm f) {
        ProductPojo p= new ProductPojo() ;
        p.setBarcode(f.getBarcode());
        p.setbrandCategory(f.getbrandCategory());
        p.setName(f.getName());
        p.setMrp(f.getMrp());
        return p;
    }

    public static ProductData productConvert(ProductPojo p) {

        ProductData d= new ProductData();
        d.setId(p.getId());
        d.setBarcode(p.getBarcode());
        d.setbrandCategory(p.getbrandCategory());
        d.setName(p.getName());
        d.setMrp(p.getMrp());

        return d;
    }

    // Inventory Converts

    public static InventoryPojo inventoryConvert(InventoryForm f) {
        InventoryPojo p = new InventoryPojo();
        p.setId(f.getId());
        p.setQuantity(f.getQuantity());
        return p;
    }

    public static InventoryData inventoryConvert(InventoryPojo p) {
        InventoryData d= new InventoryData();
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());

        return d;
    }

    //Order Convert
    public static OrderData orderConvert(OrderPojo p){
        OrderData d= new OrderData();
        d.setId(p.getId());
        d.setTime(p.getTime());
        return d;
    }

    //OrderItem Converts


}
