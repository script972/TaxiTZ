package com.example.script972.taxitz.model;

import java.util.Date;

/**
 * Created by script972 on 20.04.2017.
 */

public class Order {
    private long id;
    private String point1;
    private String point2;
    private String description;
    private double price;
    private Date date;

    public Order(String point1, String point2, String description, double price, Date date) {
        this.point1 = point1;
        this.point2 = point2;
        this.description = description;
        this.price = price;
        this.date = date;
    }

    public Order(long id, String point1, String point2, String description, double price, Date date) {
        this.id = id;
        this.point1 = point1;
        this.point2 = point2;
        this.description = description;
        this.price = price;
        this.date = date;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPoint1() {
        return point1;
    }

    public void setPoint1(String point1) {
        this.point1 = point1;
    }

    public String getPoint2() {
        return point2;
    }

    public void setPoint2(String point2) {
        this.point2 = point2;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    @Override
    public String toString() {
        return "Order{" +
                "point1='" + point1 + '\'' +
                ", point2='" + point2 + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", date=" + date +
                '}';
    }
}
