package com.increff.employee.service;

import com.increff.employee.dto.AbstractUnitTest;
import com.increff.employee.model.BrandData;
import com.increff.employee.pojo.BrandPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class BrandServiceTest extends AbstractUnitTest {

    @Autowired
    private BrandService service;

    private final String brand="amul";
    private final String category="dairy";

    @Before
    public void setUp() throws ApiException {
        BrandPojo p=new BrandPojo();
        p.setBrand(brand);
        p.setCategory(category);
        service.add(p);
    }

    @Test
    public void testAdd() throws ApiException {
        BrandPojo p=new BrandPojo();
        p.setBrand("brand");
        p.setCategory("category");
        service.add(p);
    }

    @Test
    public void testGetAll() throws ApiException {
        List<BrandPojo> list=service.getAll();
        int size= list.size();
        assertEquals(1,size);

        BrandPojo p2=new BrandPojo();
        p2.setBrand("brand2");
        p2.setCategory("category2");
        service.add(p2);

        List<BrandPojo> list2=service.getAll();
        int size2=list2.size();
        assertEquals(2,size2);

    }

    @Test
    public void testGet(){
        List<BrandPojo> list=service.getAll();
        for(BrandPojo pojo: list){
            BrandPojo getPojo= service.get(pojo.getId());
            assertEquals(pojo.getBrand(),getPojo.getBrand());
            assertEquals(pojo.getCategory(),getPojo.getCategory());

        }
    }

    @Test
    public void testUpdate() throws ApiException {
        List<BrandPojo> list=service.getAll();
        for(BrandPojo pojo: list){
            BrandPojo newPojo= new BrandPojo();
            newPojo.setBrand("new_brand");
            newPojo.setCategory("new_category");
            service.update(pojo.getId(), newPojo);

            BrandPojo getPojo= service.get(pojo.getId());
            assertEquals("new_brand",newPojo.getBrand());
            assertEquals("new_category",newPojo.getCategory());
        }
    }

    @Test
    public void testDelete() throws ApiException {
        List<BrandPojo> list=service.getAll();
        for(BrandPojo pojo: list){
            service.delete(pojo.getId());
        }

        List<BrandPojo> list2=service.getAll();
        int size= list2.size();
        assertEquals(0,size);
    }
}
