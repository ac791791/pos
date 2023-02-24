package com.increff.employee.controller;

import java.util.ArrayList;
import java.util.List;

import com.increff.employee.dto.ProductDto;
import com.increff.employee.service.ApiException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.ProductData;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.service.ProductService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/api/product")
public class ProductController {


	@Autowired
	private ProductDto dto;
	
	@ApiOperation(value = "Adding a Product")
	@RequestMapping(method =RequestMethod.POST)
	public void add(@RequestBody ProductForm form) throws ApiException {
		dto.add(form);
	}
	
	@ApiOperation(value = "Deleting a product of a given id")
	@RequestMapping(value ="/{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable int id) throws ApiException {
		dto.delete(id);
	}
	

	@ApiOperation(value = "Getting a product of a given id")
	@RequestMapping(value ="/{id}", method=RequestMethod.GET)
	public ProductData get(@PathVariable int id) {
		return dto.get(id);
	}
	
	@ApiOperation(value = "Getting all Products")
	@RequestMapping(method = RequestMethod.GET)
	public List<ProductData> getAll(){
		return dto.getAll();
	}
	
	@ApiOperation(value = "Updating a Product")
	@RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody ProductForm form) {
		dto.update(id,form);
	}
	
	
	

}
