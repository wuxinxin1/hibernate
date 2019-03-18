package com.wxx.hibernate.relation.many2many;

import com.wxx.hibernate.relation.one2many.Customer;
import com.wxx.hibernate.relation.one2many.Order;
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

import static com.sun.xml.internal.ws.dump.LoggingDumpTube.Position.Before;

/**
 * 测试多对多
 *
 * 实现原理，添加两张有多对多关系的表，再添加一张中间表，中间表的两个字段刚好是前面两张表的主键，
 * 并且中间表的两个字段值都约束于前面两张表两个主键的取值，所以再为这个中间表的字段添加两个外键约束即可，
 * 并且两个字段的综合设置为中间表的主键
 * Created by Administrator on 2019/3/17/017.
 */
public class Many2ManyRelationTest {

    private SessionFactory sessionFactory;

    private Session session;

    private Transaction transaction;

    @Before
    public void init(){
        //创建sessionFactory
        Configuration configuration=new Configuration().configure("hibernate8.cfg.xml");
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
        Emploee o = (Emploee)session.get(Emploee.class, 2);


        Set<Project> projects = o.getProjects();

        for (Project project :projects) {
            System.out.println(project);
        }
    }

    @Test
    public void saveTest(){
        Emploee emploee = new Emploee();
        emploee.setEmpName("刘静");

        Project project = new Project();
        project.setProName("iphone");

        Project project1 = new Project();
        project1.setProName("ipad");

        //设置关联关系
        emploee.getProjects().add(project);
        emploee.getProjects().add(project1);

        //执行保存
        session.save(project);
        session.save(project1);

        session.save(emploee);

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
