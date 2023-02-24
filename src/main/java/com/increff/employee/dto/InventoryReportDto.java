package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryReportData;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class InventoryReportDto {

    @Autowired
    private ProductDto productDto;

    @Autowired
    private BrandDto brandDto;

    @Autowired
    private InventoryDto inventoryDto;

    public List<InventoryReportData> getAll(){
        List<InventoryReportData> list1= new ArrayList<InventoryReportData>();

        List<BrandData> list2 = brandDto.getAll();

        for (BrandData d: list2){
            InventoryReportData data = new InventoryReportData();
            data.setBrand(d.getBrand());
            data.setCategory(d.getCategory());

            int quantity=0;
            List<ProductPojo> productPojoList= productDto.getByBrandCategory(d.getId());
            for(ProductPojo p: productPojoList){
                int productId= p.getId();
                InventoryData inventoryData= inventoryDto.get(productId);
                quantity+=inventoryData.getQuantity();
            }
            data.setQuantity(quantity);
            list1.add(data);
        }

        return list1;
    }





}
