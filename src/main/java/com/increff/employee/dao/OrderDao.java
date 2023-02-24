package com.increff.employee.dao;

import com.increff.employee.pojo.OrderPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public class OrderDao extends AbstractDao {
    private static String select_id="select p from OrderPojo p where id=:id";
    private static String select_time="select p from OrderPojo p where time=:time";
    private static String select_all="select p from OrderPojo p";
    private static String delete_id="delete from OrderPojo where id=:id";

    @Transactional
    public void insert(OrderPojo p){
        em.persist(p);
    }

    public void delete(int id){
        Query query= em.createQuery(delete_id);
        query.setParameter("id",id);
        query.executeUpdate();
    }
    public OrderPojo select(int id){
        TypedQuery<OrderPojo> query=em.createQuery(select_id,OrderPojo.class);
        query.setParameter("id",id);
        return getSingle(query);
    }
    public OrderPojo select(LocalDateTime time){
        TypedQuery<OrderPojo> query=em.createQuery(select_time,OrderPojo.class);
        query.setParameter("time",time);
        return getSingle(query);
    }

    public List<OrderPojo> selectAll(){
        TypedQuery<OrderPojo> query=em.createQuery(select_all,OrderPojo.class);
        return query.getResultList();
    }
    public void update(){

    }


}
