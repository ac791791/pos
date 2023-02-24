package com.increff.employee.service;

import com.increff.employee.model.SalesReportData;
import com.increff.employee.dao.ReportDao;
import com.increff.employee.model.SalesReportForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportDao dao;

    @Transactional
    public List<SalesReportData> getSalesReport(SalesReportForm form){

        return dao.getSalesReport(form);
    }


}
