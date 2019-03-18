package com.wxx.hibernate.relation.many2many;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: wuxinxin
 * @date: 2019/3/18
 */
public class EmpPro {
    private int id;
    private Emploee empId;
    private Project proId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Emploee getEmpId() {
        return empId;
    }

    public void setEmpId(Emploee empId) {
        this.empId = empId;
    }

    public Project getProId() {
        return proId;
    }

    public void setProId(Project proId) {
        this.proId = proId;
    }
}
