package com.increff.employee.dto;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryDto {

    @Autowired
    private InventoryService service;
    @Autowired
    private ProductService productService;


    public InventoryData get(int id) {
        return convert(service.get(id));
    }

    public List<InventoryData> getAll(){
        List<InventoryData> list1= new ArrayList<InventoryData>();
        List<InventoryPojo> list2 = service.getAll();
        for(InventoryPojo p: list2) {
            list1.add(convert(p));
        }
        return list1;

    }

    public void update(int id,InventoryForm form) {
        service.update(id, convert(form));
    }

    public void topUpdate(InventoryForm form){
        ProductPojo productPojo= productService.get(form.getBarcode());
        int id= productPojo.getId();
        service.update(id,convert(form));
    }

    private InventoryPojo convert(InventoryForm f) {
        InventoryPojo p = new InventoryPojo();
        p.setId(f.getId());
        p.setQuantity(f.getQuantity());
        return p;
    }

    private InventoryData convert(InventoryPojo p) {
        InventoryData d= new InventoryData();
        d.setId(p.getId());
        d.setQuantity(p.getQuantity());

        ProductPojo productPojo= productService.get(p.getId());
        d.setName(productPojo.getName());
        d.setBarcode(productPojo.getBarcode());
        return d;
    }

}


