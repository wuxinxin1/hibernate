package com.wxx.hibernate.relation.one2one2;

/**
 * Created by Administrator on 2019/3/17/017.
 */
public class Manage {
    private int id;
    private String magName;

    private Department department;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getMagName() {
        return magName;
    }

    public void setMagName(String magName) {
        this.magName = magName;
    }

    @Override
    public String toString() {
        return "Manage{" +
                "id=" + id +
                ", magName='" + magName + '\'' +
                ", department=" + department +
                '}';
    }
}
