package com.wxx.hibernate.config;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.sql.Timestamp;
import java.util.Date;

/**
 * hibernate状态转换测试
 * 1.临时对象转换为持久对象 save(),pesist
 * Created by Administrator on 2018/12/22/022.
 */
public class ConfigTest {

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    @Before
    public void init(){
        //创建sessionFactory
        Configuration configuration=new Configuration().configure("hibernate3.cfg.xml");
        org.hibernate.service.ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties())
                .buildServiceRegistry();
        sessionFactory=configuration.buildSessionFactory(serviceRegistry);

        //打开一个session
        session = sessionFactory.openSession();

        //开启事务
        transaction = session.beginTransaction();

    }

    /**
     * hbm.xml配置问题
     * 1.hibernate-mapping的package属性是用来指定class的包名的，在class标签中可以省去包名（但一个News.hbm.xml文件定义多个class时，优势更明显）
     * 2.class 的dynamic-update="true" 用于动态生成update语句，没有修改的值，将不会出现在sql语句中
     * 3.type指定实体类，某字段的类型;hibernate,sql,java之间有类型映射关系，可以查看映射表格
     *  4.时间问题：
     *    a.在java中表示时间的有两个类：java.util.Date和 java.util.calendar
     *    b.在jdbc中支持了java.util.Date的扩展，即三个子类:java.sql.Date(年月日),java.sql.Time(时间),java.sql.TimeStamp
     * 5.sql-type,用来精确影射
     */
    @Test
    public void test(){
        News o = (News)session.get(News.class, 1);
        System.out.println(o);
    }

    @Test
    public void save(){
        News news = new News("shiro详解", "wuxinxin", new Date());
        session.save(news);
    }

    @Test
    public void update(){
        News o = (News)session.get(News.class, 1);
        o.setAuth("aab");
    }

    /**
     * 1.添加一条记录
     * 2.查询一条记录
     */
    @Test
    public void testCompn(){
        /*Worker worker=new Worker();

        Pay pay=new Pay();
        pay.setDayMoney(100);
        pay.setMonthMoney(2200);
        pay.setYearMoney(26400);

        worker.setPay(pay);
        worker.setName("wxx");

        session.save(worker);*/

        Worker o = (Worker)session.get(Worker.class, 2);

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
