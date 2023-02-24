package com.increff.employee.dto;


import com.increff.employee.model.BrandData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BrandReportDto {

    @Autowired
    private BrandDto brandDto;

    public List<BrandData> getAll(){
        List<BrandData> result = brandDto.getAll();
        return result;
    }
}
