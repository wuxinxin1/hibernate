package com.wxx.hibernate.relation.many2many;

import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: wuxinxin
 * @date: 2019/3/18
 */
public class Project {
    private int proId;
    private String proName;

    private Set<EmpPro> empPros;

    public int getProId() {
        return proId;
    }

    public void setProId(int proId) {
        this.proId = proId;
    }

    public String getProName() {
        return proName;
    }

    public void setProName(String proName) {
        this.proName = proName;
    }

    public Set<EmpPro> getEmpPros() {
        return empPros;
    }

    public void setEmpPros(Set<EmpPro> empPros) {
        this.empPros = empPros;
    }
}
