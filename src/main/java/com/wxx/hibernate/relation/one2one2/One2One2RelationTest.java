package com.wxx.hibernate.relation.one2one2;

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
public class One2One2RelationTest {

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    @Before
    public void init(){
        //创建sessionFactory
        Configuration configuration=new Configuration().configure("hibernate7.cfg.xml");
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
        Department o = (Department)session.get(Department.class, 1);

        System.out.println(o);

        Manage manage = o.getManage();
        System.out.println(manage);

        /*System.out.println("=====================");
        Manage o1 = (Manage)session.get(Manage.class, 1);

        System.out.println(o1);

        Department manage1 = o1.getDepartment();
        System.out.println(manage1);*/

    }

    @Test
    public void saveTest(){

        /**
         * 1.先插入manage,再插入department,会执行insert两次操作
         * 1.先插入department,再插入manage,会执行insert两次操作,并且sql语句顺序和上面一致，
         *   这是因为department的id生成方式是根据manage生成的
         */
        Manage manage = new Manage();
        manage.setMagName("MANG_B");

        Department department = new Department();
        department.setDepName("DET_B");
        department.setManage(manage);


        session.save(department);
        session.save(manage);

        /*session.save(manage);
        session.save(department);*/


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
