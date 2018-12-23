package com.wxx.hibernate.hello;

import com.sun.org.apache.bcel.internal.generic.NEW;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.sql.Date;

/**
 * Created by Administrator on 2018/12/22/022.
 */
public class HelloTest {

    public static void main(String[] args) throws InterruptedException {
        //加载配置文件,创建SessionFactory
        Configuration configuration=new Configuration().configure("/hibernate1.cfg.xml");

        ServiceRegistry serviceRegistry=new ServiceRegistryBuilder().applySettings(configuration.getProperties())
                .buildServiceRegistry();

        SessionFactory sessionFactory=configuration.buildSessionFactory(serviceRegistry);

        //创建session
        Session session = sessionFactory.openSession();

        //开启事务
        Transaction transaction = session.beginTransaction();

        //执行操作
        session.save(new News("java","java",new Date(System.currentTimeMillis())));

        //News news = (News)session.get(News.class, 4);

        //System.out.println(news);
        //提交事务
        transaction.commit();

        //关闭session
        session.close();


        //Thread.sleep(20000);
        //关闭SessionFactory
        sessionFactory.close();

    }

}
