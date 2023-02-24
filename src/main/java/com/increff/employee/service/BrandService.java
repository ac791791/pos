package com.increff.employee.service;

import java.util.List;
import javax.transaction.Transactional;

import com.increff.employee.pojo.ProductPojo;
import org.apache.fop.datatypes.Length;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.BrandDao;
import com.increff.employee.pojo.BrandPojo;



@Service
public class BrandService {
	
	@Autowired
	private BrandDao dao;
	@Autowired
	private ProductService productService;
	
	@Transactional
	public void add(BrandPojo p) throws ApiException {
		BrandPojo pojo=dao.selectByBrandCategory(p.getBrand(),p.getCategory());
		if(pojo==null) {
			dao.insert(p);
		}
		else {
			throw new ApiException("Couldn't Add: Given Brand Category already exist");
		}
	}
	@Transactional
	public void delete(int id) throws ApiException {
		List<ProductPojo> list=productService.getByBrandCategory(id);
		for(ProductPojo pojo:list){
			productService.delete(pojo.getId());
		}
		dao.delete(id);
	}
	@Transactional
	public BrandPojo get(int id) {
		return dao.select(id);
	}
	@Transactional
	public List<BrandPojo> getAll(){
		return dao.selectAll();
	}
	
	@Transactional
	public void update(int id, BrandPojo p) throws ApiException {

		BrandPojo pojo=dao.selectByBrandCategory(p.getBrand(),p.getCategory());
		if(pojo==null) {
			BrandPojo ex= dao.select(id);
			ex.setBrand(p.getBrand());
			ex.setCategory(p.getCategory());
			dao.update(p);
		}
		else {
			throw new ApiException("Couldn't Update: Given Brand Category already exist");
		}
	}


}
