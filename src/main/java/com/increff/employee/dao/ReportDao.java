package com.increff.employee.dao;

import com.increff.employee.model.SalesReportData;
import com.increff.employee.model.SalesReportForm;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class ReportDao extends AbstractDao{

//    private static String salesReport= "SELECT new com.increff.employee.modal.SalesReportData(brandPojo.brand, brandPojo.category,SUM(orderItemPojo.quantity),SUM(orderItemPojo.sellingPrice)) "+
//            "FROM OrderPojo orderPojo "+
//            "INNER JOIN OrderItemPojo orderItemPojo ON orderPojo.id=orderItemPojo.orderId "+
//            "INNER JOIN ProductPojo productPojo ON orderItemPojo.id=productPojo.orderId "+
//            "INNER JOIN BrandPojo brandPojo ON productPojo.brandCategory=brandPojo.id "+
//            "WHERE brandPojo.brand LIKE :brand "+
//            "AND brandPojo.category LIKE :category "+
//            "GROUP BY brandPojo.brand,brandPojo.category";
//

    private static  String salesReport="SELECT new com.increff.employee.modal.SalesReportData(brandPojo.brand, brandPojo.category, SUM(orderItemPojo.quantity), SUM(orderItemPojo.sellingPrice))\n" +
            "FROM com.increff.employee.pojo.OrderPojo orderPojo\n" +
            "INNER JOIN com.increff.employee.pojo.OrderItemPojo orderItemPojo ON orderPojo.id = orderItemPojo.orderId\n" +
            "INNER JOIN com.increff.employee.pojo.ProductPojo productPojo ON orderItemPojo.productId = productPojo.id\n" +
            "INNER JOIN com.increff.employee.pojo.BrandPojo brandPojo ON productPojo.brandCategory = brandPojo.id\n" +
            "WHERE brandPojo.brand LIKE :brand AND brandPojo.category LIKE :category\n" +
            "GROUP BY brandPojo.brand, brandPojo.category";
    public List<SalesReportData> getSalesReport(SalesReportForm form){
        TypedQuery<SalesReportData> query= em.createQuery(salesReport, SalesReportData.class);
//        query.setParameter("startDate", form.getStartDate());
//        query.setParameter("endDate", form.getEndDate());
        query.setParameter("brand", "%"+form.getBrand()+"%");
        query.setParameter("category", "%"+form.getCategory()+"%");
        return query.getResultList();
    }

}
