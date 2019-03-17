package com.wxx.hibernate.config;

/**
 * 用于测试hibernate的组件配置方式
 * Created by Administrator on 2019/3/17/017.
 */
public class Worker {
    private int id;
    private String name;
    private Pay pay;

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

    public Pay getPay() {
        return pay;
    }

    public void setPay(Pay pay) {
        this.pay = pay;
    }

    @Override
    public String toString() {
        return "Worker{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", pay=" + pay +
                '}';
    }
}
