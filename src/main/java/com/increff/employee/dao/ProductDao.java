package com.increff.employee.dao;

import java.util.List;
import javax.transaction.Transactional;
import org.springframework.stereotype.Repository;
import com.increff.employee.pojo.ProductPojo;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

@Repository
public class ProductDao extends AbstractDao {

	private static String delete_id="delete from ProductPojo where id=:id";
	private static String select_id="select p from ProductPojo p where id=:id";
	private static String select_all="select p from ProductPojo p";
	private static  String select_barcode="select p from ProductPojo p where barcode=:barcode";

	private static String select_brandCategory="select p from ProductPojo p where brandCategory=:brandCategory";
	
	@Transactional
	public void insert(ProductPojo p) {
		em.persist(p);
	}
	
	public void delete(int id) {
		Query query= em.createQuery(delete_id);
		query.setParameter("id",id);
		query.executeUpdate();
	}

	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = em.createQuery(select_id, ProductPojo.class);
		query.setParameter("id", id);
		return getSingle(query);
	}
	public ProductPojo select(String barcode) {
		TypedQuery<ProductPojo> query = em.createQuery(select_barcode, ProductPojo.class);
		query.setParameter("barcode", barcode);
		return getSingle(query);
	}
	public List<ProductPojo> selectAll(){
		TypedQuery<ProductPojo> query= em.createQuery(select_all, ProductPojo.class);
		return query.getResultList();
	}

	public List<ProductPojo> selectByBrandCategory(int brandCategory){
		TypedQuery<ProductPojo> query = em.createQuery(select_brandCategory, ProductPojo.class);
		query.setParameter("brandCategory",brandCategory);
		return query.getResultList();
	}
	public void update(ProductPojo ex) {
		
	}
}
