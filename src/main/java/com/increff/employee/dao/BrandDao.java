package com.increff.employee.dao;

import java.util.List;
import javax.transaction.Transactional;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import com.increff.employee.pojo.BrandPojo;




@Repository
public class BrandDao extends AbstractDao {

	private static String delete_id ="delete from BrandPojo p where id=:id";
	private static String select_id ="select p from BrandPojo p where id=:id";
	private static String select_all="select p from BrandPojo p";
	private static String select_byBrandCategory="select p from BrandPojo p where brand=:brand AND category=:category";
	
	
	
	@Transactional
	public void insert(BrandPojo p) {
		em.persist(p);
	}
	
	public void delete(int id) {
		Query query= em.createQuery(delete_id);
		query.setParameter("id", id);
		query.executeUpdate();
	}
	public BrandPojo select(int id) {
		TypedQuery<BrandPojo> query= em.createQuery(select_id,BrandPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}

	public BrandPojo selectByBrandCategory(String brand,String category){
		TypedQuery<BrandPojo> query=em.createQuery(select_byBrandCategory,BrandPojo.class);
		query.setParameter("brand",brand);
		query.setParameter("category",category);
		return  getSingle(query);
	}
	
	public List<BrandPojo> selectAll(){
		TypedQuery<BrandPojo> query=em.createQuery(select_all, BrandPojo.class);
		return query.getResultList();
	}
	
	public void update(BrandPojo p) {
		
	}
	
}
