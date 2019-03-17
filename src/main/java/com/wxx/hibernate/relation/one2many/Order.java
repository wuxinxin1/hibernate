package com.wxx.hibernate.relation.one2many;

/**
 * Created by Administrator on 2019/3/17/017.
 */
public class Order {
    private int id;
    private String name;
    private Customer customer;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
