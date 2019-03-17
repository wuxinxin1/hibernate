package com.wxx.hibernate.relation.one2one2;

/**
 * 11
 * Created by Administrator on 2019/3/17/017.
 */
public class Department {
    private int id;
    private String depName;

    private Manage manage;

    public Manage getManage() {
        return manage;
    }

    public void setManage(Manage manage) {
        this.manage = manage;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDepName() {
        return depName;
    }

    public void setDepName(String depName) {
        this.depName = depName;
    }
}
