package com.wxx.hibernate.relation.subclassmap;

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
 * 测试多对一关系
 * Created by Administrator on 2019/3/17/017.
 */
public class SubclassMapRelationTest {

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    @Before
    public void init(){
        //创建sessionFactory
        Configuration configuration=new Configuration().configure("hibernate9.cfg.xml");
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

    /**
     *   会产生多态查询的效果，并且父类子类公用一张表
     *   1.当查询person表的时候，如果查询到的记录条是属于父类的，那么返回父类类型对象，如果查到的是子类记录，那么转换为子类对象返回
     *   2.由于有辨别者列，父类和子类公用一张表
     *
     *   缺点：
     *   1.需要额外添加一个和数据无关的辨别者列
     *   2.如果继承层次比较深，那么字段会表较多
     *   3.子类的列无法添加非空约束
     */

    @Test
    public void selectTest(){
        Person o =(Person) session.get(Person.class, 2);

        Person o1 =(Person) session.get(Person.class, 1);
    }

    /**
     * 1.插入数据的时候，辨别者列是根据配置自动放入的，不需要我们关心
     */
    @Test
    public void saveTest(){
        //保存一个父类对象
        Person person = new Person();
        person.setName("wuxinxin");
        person.setAge(18);

        //保存一个子类对象
        Student student=new Student();
        student.setName("刘静");
        student.setAge(19);
        student.setSchool("吉安三中");

        session.save(person);
        session.save(student);
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
