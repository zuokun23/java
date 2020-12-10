package com.itheima.ui;

import com.itheima.dao.IAccountDao;
import com.itheima.service.IAccountService;
import com.itheima.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * 模拟一个表现层，用于调用业务层
 */
public class Client {

    /**
     * 获取spring的ioc核心容器，并根据id获取对象
     * ApplicationContext的三个常用实现类：
     *      ClassPathXmlApplicationContext:它可以加载路径下的配置文件，要求配置文件必须在类路径下。不在的话，加载不了。
     *      FileSystemXmlApplicationContext：它可以加载磁盘任意路径下的配置文件（必须有访问权限）
     *      AnnotationConfigApplicationContext：它用于读取注解配置创建容器
     * 核心容器的两个接口引发出的问题：
     *      ApplicationContext:-------单例对象适用时（总共只有一个实例，不如早创建）
     *      （它在构建核心容器时，创建对象采取的策略是
     *      立即加载的方式。也就是说，只要一读取完配置文件马上就创建文件中配置的对象。）
     *                         -------多例对象时采用延迟加载
     *                         -------这个接口非常智能，所有选择这个
     *      BeanFactory:--------多例对象适用（反正对象次数不限定，加载xml实例一个没有必要）
     *      （它在创建核心容器时，创建对象采取的策略是延迟加载的方式。
     *      也就是说，什么时候根据id获取对象了，什么时候才真正的创建对象。）
     *
     * @param args
     */
    public static void main(String[] args) {
        //1.获取核心容器对象
        //ApplicationContext ac = new ClassPathXmlApplicationContext("bean.xml");
        String path = Client.class.getClassLoader().getResource("").toString();
        ApplicationContext ac = new FileSystemXmlApplicationContext(path + "bean.xml");
        //2.根据id获取Bean对象
        IAccountService as = (IAccountService) ac.getBean("accountService");//方法一：强转
        IAccountDao adao = ac.getBean("accountDao",IAccountDao.class);// 方法二：传入字节码
        System.out.println(as);
        System.out.println(adao);
        //as.saveAccount();

        //---------BeanFactory-----------
//
//        Resource resource = new ClassPathResource("bean.xml");
//        BeanFactory factory = new XmlBeanFactory(resource);
//        IAccountService as1 = (IAccountService) factory.getBean("accountService");
//        System.out.println(as1);

    }
}
