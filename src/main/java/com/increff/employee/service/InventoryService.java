package com.increff.employee.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.increff.employee.dao.InventoryDao;
import com.increff.employee.pojo.InventoryPojo;

@Service
public class InventoryService {

	@Autowired
	private InventoryDao dao;
	
	@Transactional
	public void add(InventoryPojo p) {
		dao.insert(p);
	}
	
	@Transactional
	public void delete(int id) {
		dao.delete(id);
	}
	
	@Transactional
	public InventoryPojo get(int id) {
		return dao.select(id);
	}
	
	@Transactional
	public List<InventoryPojo> getAll(){
		return dao.selectAll();
	}
	
	@Transactional
	public void update(int id, InventoryPojo p) {
		InventoryPojo ex = dao.select(id);
		ex.setQuantity(p.getQuantity());
	}
}
