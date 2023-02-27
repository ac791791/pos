package com.increff.employee.dao;

import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ReportDao extends AbstractDao{

    private static String salesReport= "SELECT brandPojo.brand as brand, brandPojo.category as category,SUM(orderItemPojo.quantity) as quantity,SUM(orderItemPojo.sellingPrice) as revenue "+
            "FROM OrderPojo orderPojo "+
            "INNER JOIN OrderItemPojo orderItemPojo ON orderPojo.id=orderItemPojo.orderId "+
            "INNER JOIN ProductPojo productPojo ON orderItemPojo.id=productPojo.orderId "+
            "INNER JOIN BrandPojo brandPojo ON productPojo.brandCategory=brandPojo.id "+
            "WHERE brandPojo.brand LIKE :brand "+
            "AND brandPojo.category LIKE :category "+
            "GROUP BY brandPojo.brand,brandPojo.category";




    public List<SalesReportData> getSalesReport(SalesReportForm form){
        TypedQuery<SalesReportData> query= em.createQuery(salesReport, SalesReportData.class);
//        query.setParameter("startDate", form.getStartDate());
//        query.setParameter("endDate", form.getEndDate());
        query.setParameter("brand", "%"+form.getBrand()+"%");
        query.setParameter("category", "%"+form.getCategory()+"%");
        return query.getResultList();
    }

    public List<Tuple> getSalesReport1(SalesReportForm form){
        TypedQuery<Tuple> query=em.createQuery(salesReport,Tuple.class);
        query.setParameter("brand","%"+form.getBrand()+"%");
        query.setParameter("category", "%"+form.getCategory()+"%");
        return query.getResultList();

    }


}
