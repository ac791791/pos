package com.increff.employee.dto;

import com.increff.employee.model.BrandData;
import com.increff.employee.model.BrandForm;
import com.increff.employee.pojo.BrandPojo;
import com.increff.employee.service.ApiException;
import com.increff.employee.service.BrandService;
import com.increff.employee.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class BrandDto {

    @Autowired
    private BrandService service;


    public void add(BrandForm form) throws ApiException {
        BrandPojo p=convert(form);
        normalize(p);
        service.add(p);
    }

    public void delete(int id) throws ApiException {
        service.delete(id);
    }

    public BrandData get(int id) {
        BrandPojo p= service.get(id);
        return convert(p);
    }

    public List<BrandData> getAll(){
        List<BrandData> list1= new ArrayList<BrandData>();
        List<BrandPojo> list2=service.getAll();

        for(BrandPojo p:list2) {
            list1.add(convert(p));
        }
        return list1;
    }

    public void update(int id,BrandForm form) throws ApiException {
        BrandPojo p= convert(form);
        normalize(p);
        service.update(id, p);

    }



    //Conversion functions
    protected BrandData convert(BrandPojo p) {
        BrandData d = new BrandData();
        d.setId(p.getId());
        d.setBrand(p.getBrand());
        d.setCategory(p.getCategory());
        return d;
    }

    protected BrandPojo convert(BrandForm f) {
        BrandPojo p= new BrandPojo();
        p.setBrand(f.getBrand());
        p.setCategory(f.getCategory());
        return p;
    }
    protected static void normalize(BrandPojo p) {
        p.setBrand(StringUtil.toLowerCase(p.getBrand()));

        p.setCategory(StringUtil.toLowerCase(p.getCategory()));
    }
}
