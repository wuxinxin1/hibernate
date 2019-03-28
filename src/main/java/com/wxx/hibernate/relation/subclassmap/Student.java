package com.wxx.hibernate.relation.subclassmap;

/**
 * Created by Administrator on 2019/3/21/021.
 */
public class Student extends Person {
    private String school;

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }
}
