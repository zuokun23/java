import MyAnnotation.MyAfter;
import MyAnnotation.MyBefore;
import MyAnnotation.MyTest;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 这个就是注解三部曲中最重要的：读取注解并操作
 * 相当于我们使用Junit时看不见的那部分（在隐蔽的角落里帮我们执行标注@Test的方法）
 */
public class MyJunitFrameWork {
    public static void main(String[] args) throws Exception {
        //1.先找到测试类的字节码：EmployeeDAOTest
        Class clazz = EmployeeDAOTest.class;
        Object obj = clazz.getConstructor().newInstance();

        //2.获取EmployeeDAOTest类中所有的公共方法
        Method[] methods = clazz.getMethods();

        //3.迭代每一个Method对象，判断哪些方法上使用了注解
        List<Method> myBeforeList = new ArrayList<>();
        List<Method> myAfterList = new ArrayList<>();
        List<Method> myTestList = new ArrayList<>();
        for(Method method : methods){
            if(method.isAnnotationPresent(MyTest.class)){
                myTestList.add(method);
            }else if(method.isAnnotationPresent(MyBefore.class)){
                myBeforeList.add(method);
            }else if(method.isAnnotationPresent(MyAfter.class)){
                myAfterList.add(method);
            }
        }

        //执行方法测试
        for(Method testMethod : myTestList){
            //先执行@MyBefore的方法
            for(Method beforeMethod : myBeforeList) {
                beforeMethod.invoke(obj);
            }
            //测试方法
            testMethod.invoke(obj);
            for(Method afterMethod : myAfterList){
                afterMethod.invoke(obj);
            }
        }
    }
}
