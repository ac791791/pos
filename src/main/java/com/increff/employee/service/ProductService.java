package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import com.increff.employee.pojo.InventoryPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.increff.employee.dao.ProductDao;
import com.increff.employee.pojo.ProductPojo;
import com.increff.employee.pojo.BrandPojo;

@Service
public class ProductService {

	
	@Autowired
	private ProductDao dao;
	@Autowired
	private InventoryService inventoryService;
	@Autowired
	private BrandService brandService;
	
	@Transactional
	public void add(ProductPojo p) throws ApiException {
		if(p.getbrandCategory()==0){
			throw new ApiException("Please Select Brand/Category Option");
		}
		ProductPojo pojo= dao.select(p.getBarcode());
		if(pojo==null) {
			dao.insert(p);
			inventoryService.add(convert(p));
		}
		else {
			throw new ApiException("Couldn't Add: Given Barcode already exist");
		}
	}
	
	@Transactional
	public void delete(int id) throws ApiException {
		int inventory=inventoryService.get(id).getQuantity();
		if (inventory==0) {
			dao.delete(id);
			inventoryService.delete(id);
		}
		else {
			throw new ApiException("Couldn't Delete: Inventory is not Zero.");
		}
	}
	
	@Transactional
	public ProductPojo get(int id) {
		return dao.select(id);
	}
	@Transactional
	public ProductPojo get(String barcode) {
		return dao.select(barcode);
	}
	
	@Transactional
	public List<ProductPojo> getAll(){
		return dao.selectAll();
	}

	@Transactional
	public List<ProductPojo> getByBrandCategory(int brandCategory ){
		return dao.selectByBrandCategory(brandCategory);
	}
	
	@Transactional
	public void update(int id, ProductPojo p) {
		
		ProductPojo ex = dao.select(id);
		ex.setName(p.getName());
		ex.setMrp(p.getMrp());
		dao.update(ex);
	}

	private InventoryPojo convert(ProductPojo p){
		InventoryPojo ip= new InventoryPojo();
		ip.setId(p.getId());
		ip.setQuantity(0);
		return ip;
	}
}
