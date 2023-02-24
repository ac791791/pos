package com.increff.employee.service;

import com.increff.employee.dao.OrderItemDao;
import com.increff.employee.pojo.InventoryPojo;
import com.increff.employee.pojo.OrderItemPojo;
import com.increff.employee.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
public class OrderItemService {

    @Autowired
    private OrderItemDao dao;
    @Autowired
    private InventoryService inventoryService;
    @Autowired
    private ProductService productService;

    @Transactional
    public void add(OrderItemPojo p) throws ApiException {
        int productId=p.getProductId(); // Getting product id from OrderItemPojo

        decreaseInventory(inventoryService.get(productId), p.getQuantity());
        dao.insert(p);

    }
    @Transactional
    public void delete(int orderId) {
        List<OrderItemPojo> pojos= dao.select(orderId);
        for(OrderItemPojo p:pojos){
            increaseInventory(inventoryService.get(p.getProductId()),p.getQuantity());
        }
        dao.delete(orderId);
    }

    @Transactional
    public void delete_id(int id){
        OrderItemPojo pojo=dao.select_id(id);
        increaseInventory(inventoryService.get(pojo.getProductId()),pojo.getQuantity() );
        dao.delete_id(id);
    }

    @Transactional
    public List<OrderItemPojo> get(int orderId){
        return dao.select(orderId);
    }

    @Transactional
    public OrderItemPojo get_id(int id){
        return dao.select_id(id);
    }
    @Transactional
    public List<OrderItemPojo> getAll(){
        return dao.selectAll();
    }

    @Transactional
    public void update(int id, OrderItemPojo p) throws ApiException {
        OrderItemPojo pojo= dao.select_id(id);
        pojo.setSellingPrice(p.getSellingPrice());

        InventoryPojo inventoryPojo=inventoryService.get(pojo.getProductId());
        increaseInventory(inventoryPojo,pojo.getQuantity());

        decreaseInventory(inventoryPojo,p.getQuantity());
        pojo.setQuantity(p.getQuantity());
    }
//    @Transactional
//    public void update(int orderId, List<OrderItemPojo> newPojos) throws ApiException {
//        List<OrderItemPojo> pojos=dao.select(orderId); // Pojo list obtained by orderId
//
//        // First we are increasing inventory then making quantity of that particular product zero.
//        for(OrderItemPojo pojo : pojos){
//            InventoryPojo inventoryPojo= inventoryService.get(pojo.getProductId());
//            increaseInventory(inventoryPojo,pojo.getQuantity() );
//            pojo.setQuantity(0);
//        }
//
//        /* Linking pojos to their productId */
//        HashMap<Integer,OrderItemPojo> orderItemPojoHashMap= new HashMap<Integer,OrderItemPojo>();
//        for(OrderItemPojo pojo : pojos){
//            orderItemPojoHashMap.put(pojo.getProductId(),pojo);
//        }
//
//        for(OrderItemPojo newPojo: newPojos){
//            if(orderItemPojoHashMap.containsKey(newPojo.getProductId())==false){
//                newPojo.setOrderId(orderId);
//                add(newPojo);
//            }
//            else{
//                int productId=newPojo.getProductId();
//                InventoryPojo inventoryPojo= inventoryService.get(productId);
//                decreaseInventory(inventoryPojo,newPojo.getQuantity() );
//                orderItemPojoHashMap.get(productId).setQuantity(newPojo.getQuantity());
//                orderItemPojoHashMap.get(productId).setSellingPrice(newPojo.getSellingPrice());
//            }
//        }
//        for(OrderItemPojo pojo:pojos){
//            if(pojo.getQuantity()==0)
//                dao.delete(pojo.getOrderId());
//        }
//    }

    
    private void decreaseInventory(InventoryPojo p, int quantity) throws ApiException {
        int inventoryQuantity=p.getQuantity();
        if(inventoryQuantity<quantity){
            throw new ApiException("Sorry, this much quantity is not present");
        }
        else 
            p.setQuantity(inventoryQuantity-quantity);
    }

    private void increaseInventory(InventoryPojo p, int quantity){
        int inventoryQuantity=p.getQuantity();
        p.setQuantity(inventoryQuantity+quantity);
    }


}
