package com.increff.employee.pojo;


import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(indexes = {@Index(columnList = "time")},name = "Orders")

public class OrderPojo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
