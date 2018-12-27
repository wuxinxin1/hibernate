package com.wxx.hibernate.session;

import com.wxx.hibernate.hello.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.imageio.spi.ServiceRegistry;
import java.sql.Date;

/**
 * hibernate状态转换测试
 * 1.临时对象转换为持久对象 save(),pesist
 * Created by Administrator on 2018/12/22/022.
 */
public class SessionTest {

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    @Before
    public void init(){
        //创建sessionFactory
        Configuration configuration=new Configuration().configure("hibernate2.cfg.xml");
        org.hibernate.service.ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties())
                .buildServiceRegistry();
        sessionFactory=configuration.buildSessionFactory(serviceRegistry);

        //打开一个session
        session = sessionFactory.openSession();

        //开启事务
        transaction = session.beginTransaction();

    }

    /**
     * update方法测试
     * 1).当session中的对象属性被改变时，session可以被感知，会在flush时会自动执行update方法
     * 2)update方法可以将游离状态的对象转换为持久状态的对象
     */
    @Test
    public void updateTest(){

        News news=(News)session.get(News.class,1);
        System.out.println(news);

        //将对象转变为游离对象，关闭当前session
        //transaction.commit();
        //session.close();

        //修改持久对象当中的值，从而执行update语句
        //news.setAuth("sun");
        //session.update(news);

        System.out.println(news);
    }


    @Test
    public void t(){
        session.save(new com.wxx.hibernate.session.News("java","java",new Date(System.currentTimeMillis())));
    }

    /**
     * 调用persist()方法进行状态转换:使一个临时对象转换为持久对象
     * 和save()的区别：
     * 1）在pesist之前设置id,不会执行insert语句，会直接抛出异常
     */
    @Test
    public void persistTest(){
        News news = new News("java", "java",  new Date(System.currentTimeMillis()));

        //在pesist之前设置id,不会执行insert语句，会直接抛出异常
        //news.setId(6);

        System.out.println(news);

        session.persist(news);

        System.out.println(news);

        //对持久化对象Id进行修改，这个是不允许的，会抛异常
        //news.setId(5);

        //System.out.println(news);
    }

    /**
     * 调用save()方法进行状态转换:使一个临时对象转换为持久对象
     *  1）为对象分配id
     *  2)在flush缓存时，会执行insert语句
     *  3)在save之前设置id,但是是无效，还是由数据库分配
     *  4)持久化Id不能进行修改
     */
    @Test
    public void saveTest(){
        News news = new News("java", "java",  new Date(System.currentTimeMillis()));

        //在save之前设置id,但是是无效，还是由数据库分配
        //news.setId(4);

        System.out.println(news);

        session.save(news);

        System.out.println(news);

        //对持久化对象Id进行修改，这个是不允许的，会抛异常
        //news.setId(5);

        //System.out.println(news);
    }

    @After
    public void after(){
        //提交事务
       // transaction.commit();
        //关闭一个session
        //session.close();
        //关闭sessionFactory
        sessionFactory.close();
    }
}
