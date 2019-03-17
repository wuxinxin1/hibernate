package com.wxx.hibernate.relation.one2one;

import com.wxx.hibernate.relation.Customer;
import com.wxx.hibernate.relation.Order;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * 测试一对一关系
 * 实现一对一关系的第一种方案:  在一张表设置多对一的关系，设置一个外键，但是这个外键添加唯一约束
 * Created by Administrator on 2019/3/17/017.
 */
public class One2OneRelationTest {

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    @Before
    public void init(){
        //创建sessionFactory
        Configuration configuration=new Configuration().configure("hibernate6.cfg.xml");
        org.hibernate.service.ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties())
                .buildServiceRegistry();
        sessionFactory=configuration.buildSessionFactory(serviceRegistry);

        //打开一个session
        session = sessionFactory.openSession();

        //开启事务
        transaction = session.beginTransaction();

    }


    @Test
    public void testCompn(){

    }

    @Test
    public void selectTest(){
        Department o = (Department)session.get(Department.class, 3);

        System.out.println(o.getManage());
    }

    @Test
    public void testSave(){

       /* Manage manage = new Manage();
        manage.setMagName("mag_B");

        Department department = new Department();
        department.setDepName("dep_B");

        //设置关联关系
        department.setManage(manage);

        //会执行两次insert
        session.save(manage);
        session.save(department);

        //会执行一次update,两次insert
        session.save(department);
        session.save(manage);*/


        Manage manage = new Manage();
        manage.setId(1);

        Department department = new Department();
        department.setDepName("dep_C");

        //设置关联关系
        department.setManage(manage);

        session.save(department);



    }


    @After
    public void after(){
        //提交事务
        transaction.commit();
        //关闭一个session
        session.close();
        //关闭sessionFactory
        sessionFactory.close();
    }

}
