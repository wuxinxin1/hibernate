package com.wxx.hibernate.relation.one2many;

import java.util.Set;

/**
 * Created by Administrator on 2019/3/17/017.
 */
public class Customer {
    private int id;
    private String name;
    private Set<Order> orders;

    public Set<Order> getOrders() {
        return orders;
    }

    public void setOrders(Set<Order> orders) {
        this.orders = orders;
    }

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

}
