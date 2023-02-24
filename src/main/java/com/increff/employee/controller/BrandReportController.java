package com.increff.employee.controller;

import com.increff.employee.dto.BrandReportDto;
import com.increff.employee.model.BrandData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class BrandReportController {

    @Autowired
    private BrandReportDto dto;

    @ApiOperation(value = "Getting Brand Report")
    @RequestMapping(path="/api/brandReport", method = RequestMethod.GET)
    public List<BrandData> getAll(){
        return dto.getAll();
    }
}
