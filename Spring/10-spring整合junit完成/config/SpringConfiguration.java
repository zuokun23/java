package config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.apache.commons.dbutils.QueryRunner;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.*;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * 该类是一个配置类，它的作用和bean.xml是一样的
 * spring中的新注解
 * @Configuration
 *  作用：指定当前类是一个配置类
 *  细节1：当配置类作为AnnotationConfigApplicationContext对象创建的参数时，该注解可以不写。
 *  细节2：@Configuration和@ComponentScan两者都要有，new AnnotationConfigApplicationContext(xx
 *      .class)可以视为对该类的@Configuration和@ComponnetScan都加上了
 * @ComponentScan
 *  作用：用于通过注解指定spring在创建容器时要扫描的包
 *  属性：value--它和basePackages的作用是一样的，都是用于指定创建容器时要扫描的包（@ComponentScan("com.itheima")）
 *      我们使用此注解等于在xml中配置了1
 *
 * @Bean
 *  作用：用于把当前方法的返回值作为bean对象存入spring的ioc容器中
 *  属性：name--用于指定bean的id。当不写时，默认值是当前方法的名称。
 *  细节：当我们使用注解配置方法时，如果方法有参数，spring框架会去容器中查找有没有可用的bean对象。
 *      查找的方式和Autowired注解的作用是一样的
 * @Import
 *  作用：用于导入其他配置类
 *  属性：用于指定其他配置类的字节码，当我们使用Import的注解后，有Import注解的类就是父配置类，而导入的都是子配置类
 *      相当于new AnnotationConfigApplicationContext(xx.class)
 *
 * @PropertySource
 *  作用：用于指定propertied文件的位置
 *  属性：value指定文件的名称和路径。关键字：classpath表示类路径下
 */
//@Configuration
//@ComponentScan("config")
@Import(JdbcConfig.class)
public class SpringConfiguration {

    /**
     * 用于创建一个QueryRunner对象
     * @param dataSource
     * @return
     */
    @Bean(name="runner")
    //@Scope("prototype")
    public QueryRunner createQueryRunner(@Qualifier("dataSource1") DataSource dataSource){
        return new QueryRunner(dataSource);
    }


}
