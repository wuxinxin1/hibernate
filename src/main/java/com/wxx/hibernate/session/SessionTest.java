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
     * load和get的区别
     * 1）load会懒加载对象，需要使用的时候才发送sql语句，返回一个代理对象，get会立即加载对象发送sql语句
     * 2）load在查询没有结果的时候会抛异常，get则返回null
     * 3)load在调用完后马上关闭session会抛异常，get可以正常得到结果
    */
    @Test
    public void loadTest(){
        News news = (News)session.load(News.class, 1);

        session.close();
        System.out.println("是否发送sql语句");

        System.out.println(news);
    }

    @Test
    public void getTest(){
        News news = (News)session.get(News.class, 1);

        session.close();
        System.out.println("是否发送sql语句");

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

    /**
     * session缓存问题
     * 1.session中会持有一个持久化对象的引用
     *
     */
    @Test
    public void sessionCache(){
        /**
         * 关于第一点，所以由于缓存问题，两次获取到的是同一个对象实例
         */
        News o =(News) session.get(News.class, 1);
        System.out.println(o);

        News o1 =(News) session.get(News.class, 1);
        System.out.println(o1);
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
