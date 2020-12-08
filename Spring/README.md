# JdbcDemo1.java
- 程序的耦合：程序间的依赖关系
  - 包括 类之间的依赖、方法间的依赖
- 程序的解耦：降低程序间的依赖关系
  - 实际开发中：编译器不依赖，运行期间依赖
  - 解耦的思路：
    - 第一步：使用反射来创建对象，避免使用new关键
    - 第二步：通过读取配置文件，来获取要创建的对象全限定类名