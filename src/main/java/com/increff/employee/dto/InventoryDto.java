package com.increff.employee.dto;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.InventoryService;
import com.increff.employee.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import static com.increff.employee.util.ConvertFunction.*;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryDto {

    @Autowired
    private InventoryService service;
    @Autowired
    private ProductService productService;


    public InventoryData get(int id) {
        InventoryData d=inventoryConvert(service.get(id));
        ProductPojo productPojo= productService.get(d.getId());
        d.setName(productPojo.getName());
        d.setBarcode(productPojo.getBarcode());
        return d;

    }

    public List<InventoryData> getAll(){
        List<InventoryData> list1= new ArrayList<InventoryData>();
        List<InventoryPojo> list2 = service.getAll();
        for(InventoryPojo p: list2) {
            InventoryData d=inventoryConvert(p);
            ProductPojo productPojo= productService.get(d.getId());
            d.setName(productPojo.getName());
            d.setBarcode(productPojo.getBarcode());
            list1.add(d);
        }
        return list1;

    }

    public void update(int id,InventoryForm form) {
        service.update(id, inventoryConvert(form));
    }

    public void topUpdate(InventoryForm form) throws ApiException {
        ProductPojo productPojo= productService.get(form.getBarcode());
        if(productPojo==null){
            throw new ApiException("Couldn't Update: Product with given barcode do not exist");
        }
        int id= productPojo.getId();
        service.update(id,inventoryConvert(form));
    }


}


