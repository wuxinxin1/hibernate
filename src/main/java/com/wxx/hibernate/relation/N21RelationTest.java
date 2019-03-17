package com.wxx.hibernate.relation;

import com.wxx.hibernate.config.News;
import com.wxx.hibernate.config.Worker;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

/**
 * 测试多对一关系
 * Created by Administrator on 2019/3/17/017.
 */
public class N21RelationTest {

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    @Before
    public void init(){
        //创建sessionFactory
        Configuration configuration=new Configuration().configure("hibernate4.cfg.xml");
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
    public void saveTest(){

        /**
         * 一共执行三条insert语句，先插入主表数据，再根据主表的id来去插入从表的两条数据
         */
        /*Customer customer = new Customer();
        customer.setName("wuxinxin");

        Order order = new Order();
        order.setName("order_01");
        order.setCustomer(customer);

        Order order1 = new Order();
        order1.setName("order_02");
        order1.setCustomer(customer);

        session.save(customer);
        session.save(order);
        session.save(order1);*/


        /**
         * 一共会执行五条语句，三条添加语句，两条更新语句
         * a.先插入从表数据，由于主表数据不存在，先将外键设置为null
         * b.将主表数据插入
         * c.外键在主表中的数据已经存在，更新从表中外键的数据
         */
        /*Customer customer = new Customer();
        customer.setName("liujing");

        Order order = new Order();
        order.setName("order_03");
        order.setCustomer(customer);

        Order order1 = new Order();
        order1.setName("order_04");
        order1.setCustomer(customer);

        session.save(order);
        session.save(order1);

        session.save(customer);*/

        /**
         * 使用游离对象的主表id进行插入从表数据，如果主表这个游离对象的id不存在，那么会抛出异常
         */
        Customer customer = new Customer();
        customer.setName("wcc");
        customer.setId(3);

        Order order = new Order();
        order.setName("order_05");
        order.setCustomer(customer);

        Order order1 = new Order();
        order1.setName("order_06");
        order1.setCustomer(customer);

        session.save(order);
        session.save(order1);

    }

    @Test
    public void selectTest(){

        /**
         * 会发生级联查询，当使用到Customer的时候才会再去获取customer的数据，有个懒加载的作用
         */
        Order o = (Order)session.get(Order.class, 1);

        /**
         * 由于返回的Customer是个代理对象，如果关闭session,再去获取数据会发生LazyInitializationException
         */
        session.close();
        /**
         * toString方法中使用Customer数据，发送两条sql语句数据
         */
        System.out.println(o);
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
