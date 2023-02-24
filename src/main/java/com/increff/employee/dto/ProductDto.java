package com.increff.employee.dto;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.service.ProductService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductDto {

    @Autowired
    private ProductService service;
    @Autowired
    private BrandService brandService;

    public void add(ProductForm form) throws ApiException {
        ProductPojo p= convert(form);
        normalize(p);
        service.add(p);
    }

    public void delete(int id) throws ApiException {
        service.delete(id);
    }

    public ProductData get(int id) {
        ProductPojo p= service.get(id);
        return convert(p);
    }

    public List<ProductData> getAll(){
        List<ProductData> list1= new ArrayList<ProductData>();
        List<ProductPojo> list2= service.getAll();

        for(ProductPojo p: list2) {
            list1.add(convert(p));
        }
        return list1;
    }

    public List<ProductPojo> getByBrandCategory(int brandCategory ){
        return service.getByBrandCategory(brandCategory);
    }

    public void update(int id, ProductForm form) {
        service.update(id, convert(form));
    }





    //Conversion Functions
    protected ProductPojo convert(ProductForm f) {
        ProductPojo p= new ProductPojo() ;
        p.setBarcode(f.getBarcode());
        p.setbrandCategory(f.getbrandCategory());
        p.setName(f.getName());
        p.setMrp(f.getMrp());
        return p;
    }

    protected ProductData convert(ProductPojo p) {
        BrandPojo brandPojo= brandService.get(p.getbrandCategory());
        ProductData d= new ProductData();
        d.setId(p.getId());
        d.setBarcode(p.getBarcode());
        d.setbrandCategory(p.getbrandCategory());
        d.setName(p.getName());
        d.setMrp(p.getMrp());
        d.setBrand(brandPojo.getBrand());
        d.setCategory(brandPojo.getCategory());
        return d;
    }


    protected static void normalize(ProductPojo p) {
        p.setBarcode(StringUtil.toLowerCase(p.getBarcode()));
        p.setName(StringUtil.toLowerCase(p.getName()));
    }
}

