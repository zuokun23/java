package com.itheima.jdbc;

import java.sql.*;

/**
 *程序的耦合：程序间的依赖关系
 *  包括 类之间的依赖、方法间的依赖
 *程序的解耦：降低程序间的依赖关系
 *  实际开发中：编译器不依赖，运行期间依赖
 *  解耦的思路：
 *      第一步：使用反射来创建对象，避免使用new关键字
 *      第二步：通过读取配置文件，来获取要创建的对象全限定类名
 */
public class JdbcDemo {

    public static void main(String[] args) throws SQLException, ClassNotFoundException {
        //1注册驱动
        //DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
        Class.forName("com.mysql.cj.jdbc.Driver");
        //2获取连接
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/eesy_mybatis",//?useUnicode=true&amp;characterEncoding=utf-8&amp;useSSL=false
                "root","12345678");
        //3获取操作数据库的预处理对象
        PreparedStatement preparedStatement = connection.prepareStatement("select * from account");
        //4执行sql，得到结果集
        ResultSet resultSet = preparedStatement.executeQuery();
        //5遍历结果集
        while(resultSet.next()){
            System.out.println(resultSet.getString("name"));
        }
        //6释放资源
        resultSet.close();
        preparedStatement.close();
        connection.close();
    }
}
