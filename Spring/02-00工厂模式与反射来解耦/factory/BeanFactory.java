package com.itheima.factory;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.util.Properties;

/**
 * 一个创建Bean对象的工厂
 * Bean:在计算机中，有可重用组件的含义
 * JavaBean:用java语言编写的可重用组件
 *  javabean > 实体类
 *  它就是创建我们的service和dao对象的。
 *  第一个：需要一个配置文件来配置我们的service和dao
 *      配置的内容：唯一标识=全限定类名（key=value）
 *  第二个：通过读取配置文件中内容，反射创建对象
 *
 * 我的配置文件可以是xml也可以是properties(此处用properties因为更简单)
 */
public class BeanFactory {
    //定义一个Properties对象
    private static Properties props;

    //使用静态代码块为properties对象赋值
    static {
        try {
            //实例化对象
            props = new Properties();
            //获取properties文件流对象
            InputStream in = BeanFactory.class.getClassLoader().
                    getResourceAsStream("bean.properties");//".../target/classes/bean.properties"
            props.load(in);

        } catch (Exception e) {
            throw new ExceptionInInitializerError("初始化properties失败");
        }
    }

    /**
     * 根据bean名称获取bean对象
     * @param beanName
     * @return
     */
    public static Object getBean(String beanName){
        Object bean = null;
        String beanPath = props.getProperty(beanName);
        try {
            bean = Class.forName(beanPath).getConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }
}
