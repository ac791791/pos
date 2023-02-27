package com.increff.employee.service;

import com.increff.employee.dto.AbstractUnitTest;
import com.increff.employee.model.ProductForm;
import com.increff.employee.pojo.ProductPojo;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class ProductServiceTest extends AbstractUnitTest {

    @Autowired
    private ProductService service;

    private final String barcode="b1000";
    private final int brandCategory=1;
    private final String name="curd";
    private final double mrp=25.12;

    @Before
    public void setUp() throws ApiException {

        ProductPojo p = new ProductPojo();
        p.setBarcode(barcode);
        p.setbrandCategory(brandCategory);
        p.setName(name);
        p.setMrp(mrp);
        service.add(p);
    }

    @Test
    public void testAdd() throws ApiException {

        ProductPojo p = new ProductPojo();
        p.setBarcode("barcode");
        p.setbrandCategory(2);
        p.setName("name");
        p.setMrp(50.25);
        service.add(p);
    }

    @Test
    public void testZeroBrandCategoryAdd() throws ApiException {
        try {
            ProductPojo p = new ProductPojo();
            p.setBarcode("barcode");
            p.setbrandCategory(0);
            p.setName("name");
            p.setMrp(50.25);
            service.add(p);

        }catch (ApiException e){
            assertEquals("Please Select Brand/Category Option",e.getMessage());
        }
    }

    @Test
    public void testDuplicateBarcodeAdd() throws ApiException {
        try {
            ProductPojo p = new ProductPojo();
            p.setBarcode(barcode);
            p.setbrandCategory(1);
            p.setName("name");
            p.setMrp(50.25);
            service.add(p);

        }catch (ApiException e){
            assertEquals("Couldn't Add: Given Barcode already exist",e.getMessage());
        }
    }



}
