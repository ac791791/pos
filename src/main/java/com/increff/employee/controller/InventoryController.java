package com.increff.employee.controller;


import java.util.List;

import com.increff.employee.dto.InventoryDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.increff.employee.model.InventoryData;
import com.increff.employee.model.InventoryForm;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api
@RestController
@RequestMapping("/api/inventory")
public class InventoryController {


	@Autowired
	private InventoryDto dto;

	@ApiOperation(value = "Getting a inventory by given id")
	@RequestMapping(value ="/{id}", method = RequestMethod.GET)
	public InventoryData get(@PathVariable int id) {
		return dto.get(id);
	}

	@ApiOperation(value = "Getting all inventory")
	@RequestMapping(method = RequestMethod.GET)
	public List<InventoryData> getAll() {
		return dto.getAll();

	}

	@ApiOperation(value = "Updating Table Inventory")
	@RequestMapping(value ="/{id}", method = RequestMethod.PUT)
	public void update(@PathVariable int id, @RequestBody InventoryForm form) {
		dto.update(id, form);
	}

	@ApiOperation(value = "Updating top Inventory")
	@RequestMapping(method = RequestMethod.PUT)
	public void topUpdate(@RequestBody InventoryForm form){
		dto.topUpdate(form);
	}

}

