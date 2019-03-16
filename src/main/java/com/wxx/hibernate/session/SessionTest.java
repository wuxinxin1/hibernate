package com.wxx.hibernate.session;

import com.wxx.hibernate.hello.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.After;
import org.junit.Assert;
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
     * 2.session的flush(),这个方法的作用是用来保证session缓存中的数据一致的，如果缓存中数据发生变化，那么flush就会检测到，并且发送对应的sql语句
     *   总之flush()方法是用来保持session中的数据和数据库中的数据一致的，保证缓存数据的可信性
     *   flush()执行的时机：
     *   a.在做事务提交之前，会执行flush(),如果发现session中数据和数据库中数据不一致，那么会发送
     *   b.手动调用flush()方法，来检查数据一致性，并且可能发送相应的sql
     *   c.执行hql和qbc操作，为了保证查询到的数据是最新的，也会先进行flush()
     *
     * 3.session的refresh()
     *
     * 4.session的clear()会清除缓存中的数据
     */
    @Test
    public void sessionCache(){
        /**
         * 关于第一点，所以由于缓存问题，两次获取到的是同一个对象实例
         */
        /*News o =(News) session.get(News.class, 1);
        System.out.println(o);

        News o1 =(News) session.get(News.class, 1);
        System.out.println(o1);

        Assert.assertTrue(o==o1);*/

        /**
         * 2.1修改了session中的数据，导致数据不一致，在commit()中调用flush()方法时，会发送sql
         */
        /*News o =(News) session.get(News.class, 1);
        o.setAuth("java");*/

        /**
         * 2.2修改了session中的数据，导致数据不一致，不等到commit中的flush()方法，而是手动去调用flush(),提前发送sql语句
         */
        /*News o =(News) session.get(News.class, 1);
        o.setAuth("php");
        session.flush();*/

        /**
         * 2.3没有修改session中的数据，数据一致，手动去调用flush(),由于数据一致，不会发送sql
         */
        /*News o =(News) session.get(News.class, 1);
        session.flush();*/

        /**
         * 2.4.修改session中的数据，在执行hql和qbc操作操作时，为了查到的数据准确，会先发送update的sql语句，再发送select的sql语句
         *     如果数据没做修改,在执行hql和qbc操作操作时,仍然会去发送select的sql语句
         */
        /*News o =(News) session.get(News.class, 1);
        o.setAuth("java");

        News o1 = (News)session.createCriteria(News.class).uniqueResult();
        boolean iseq=o==o1;*/

        /**
         * 3.会强制发送查询语句，确保是数据库中最新的数据
         */
       /* News o =(News) session.get(News.class, 1);

        session.refresh(o);
        //session.flush();
        News o1 =(News) session.get(News.class, 1);*/

        /**
         *4. clear()会清除缓存中的数据，导致再获取的时候就要再次发送sql语句查询
         */
        News o =(News) session.get(News.class, 1);

        session.clear();
        News o1 =(News) session.get(News.class, 1);


    }

    /**
     * 方法测试
     * 1.save()
     * 2.persist()
     * 3.update()
     * 4.saveOrUpdate()
     * 5.delete()
     */
    @Test
    public void  sessionMethods(){
        /**
         * 1 save方法测试
         *   a.save()方法将一个临时对象转换为持久对象
         *   b.save()方法会为临时对象添加id属性
         *   c.临时对象添加的id属性无效
         *   d.持久化对象的id不能修改，否则抛出异常
         */
        /*News news = new News("AA", "aa", new java.util.Date());
        System.out.println(news);
        session.save(news);
        System.out.println(news);*/

       /* News news = new News("AA", "aa", new java.util.Date());
        news.setId(100);
        System.out.println(news);
        session.save(news);
        news.setId(200);
        System.out.println(news);*/

        /**
         * 2.persist（）方法也是将记录持久化，将一个临时对象转换为持久化对象
         *
         * 和save()区别：在临时对象设置id将导致持久化失败，直接抛出异常
         */
        /*News news = new News("AA", "aa", new java.util.Date());
        //news.setId(100);
        System.out.println(news);

        session.persist(news);*/

        /**
         * 3.update()方法用于更新一条记录，将游离状态转到持久化状态
         *   a.当一个对象是持久化状态的时候（session可以感知），修改里面的属性值，在commit()里面的flush()会检查且触发update语句
         *   b.当关闭当前session,那么当前对象处于游离状态，那么session是感知不到的，这个时候改变游离状态值，必须手动调用update()
         *   3.当当前游离状态的对象的id在数据库中不存在，那么会抛出异常
         *   4.session中不能存在两个id相同的对象
         */
        /*News o = (News)session.get(News.class, 1);
        o.setAuth("java");*/

        /*News o = (News)session.get(News.class, 1);

        transaction.commit();
        session.close();
        //o.setId(200);游离状态可以修改id
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        o.setAuth("java11");
        session.update(o);*/

        /*News o = (News)session.get(News.class, 1);

        transaction.commit();
        session.close();
        session = sessionFactory.openSession();
        transaction = session.beginTransaction();

        News o2 = (News)session.get(News.class, 1);//关联了一个id==1的对象
        o.setAuth("java11");
        session.update(o);//视图将游离转换为持久，由于id相同会失败*/

        /**
         * 4.saveOrUpdate(),将一个对象进行insert或者update；如果对象为临时对象（id=null）那么做insert操作；
         *                  如果为游离对象(id在数据库中存在，但是不在session中)，那么做update操作
         *    a.如果为游离状态，但是id不存在，那么update将会抛出异常
         */
        /*News news = new News("BB", "bb", new java.util.Date());
        session.saveOrUpdate(news);*/

        /*News news = new News("BBB", "bbb", new java.util.Date());
        news.setId(12);
        session.saveOrUpdate(news);*/

        /**
         * 5.delete()方法主要就是将游离和持久对象进行删除，如果游离中的id不存在，那么将抛出异常，删除失败
         *   a.在删除之后，这个对象的id仍然存在，这个对象已经删除，如果这个对象再进行save()，那么这个id视为无效，自动生成id进行保存操作
         *   b.在删除之后，这个对象的id仍然存在，如果进行update()那么这个id已经不存在了,会抛出异常
         *   3.在删除后，最好考虑将id设置为null
         */
        /*News news = new News();
        news.setId(11);

        session.delete(news);*/

        News o = (News)session.get(News.class, 14);
        session.delete(o);
        System.out.println(o);

        //session.save(o);
        session.update(o);
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
