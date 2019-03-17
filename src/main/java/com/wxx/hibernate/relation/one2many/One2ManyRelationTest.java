package com.wxx.hibernate.relation.one2many;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * 测试多对一关系
 * Created by Administrator on 2019/3/17/017.
 */
public class One2ManyRelationTest {

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    @Before
    public void init(){
        //创建sessionFactory
        Configuration configuration=new Configuration().configure("hibernate5.cfg.xml");
        org.hibernate.service.ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties())
                .buildServiceRegistry();
        sessionFactory=configuration.buildSessionFactory(serviceRegistry);

        //打开一个session
        session = sessionFactory.openSession();

        //开启事务
        transaction = session.beginTransaction();

    }

    @Test
    public void selectTest(){

        /**
         * 查询一对多关系，根据Customer来查询相关的order,会有延迟加载，如果session关闭，那么会抛出异常
         */
        Customer customer = (Customer)session.get(Customer.class, 5);

        //关闭，如果后面使用数据，将会抛出延迟加载异常
        session.close();

        //不使用就不会去关联查询从表数据
        Set<Order> orders = customer.getOrders();

        for (Order order:orders
             ) {
            System.out.println(order);
        }

    }

    @Test
    public void  deleteTest(){
        /**
         * 对1的一方进行删除
         * 1.如果多的一方不存在对这个1的引用，那么可以删除，如果还有多的一方进行了引用，那么会抛出异常
         * 2.如果需要强制删除，需要解除关联关系，具体的参考  cascade:一些级联操作的设置，详细了解查阅文档
         */
        Customer customer = new Customer();
        customer.setId(5);
        session.delete(customer);
    }

    @Test
    public void testCompn(){

    }

    @Test
    public void saveTest(){

        /**
         * 通过customer的id进行插入订单操作
         */
        Customer customer = new Customer();
        customer.setName("BB");
        customer.setId(5);

        Order order = new Order();
        order.setName("order04");
        order.setCustomer(customer);

        Order order1 = new Order();
        order1.setName("order05");
        order1.setCustomer(customer);


        //session.save(customer);
        session.save(order);
        session.save(order1);

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
