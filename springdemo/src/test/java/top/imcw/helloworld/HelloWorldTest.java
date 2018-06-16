package top.imcw.helloworld;

import org.junit.Test;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;

public class HelloWorldTest {
    /**
     * 第一个例子
     */
    @Test
    public void test1() {
        ApplicationContext context = new ClassPathXmlApplicationContext("helloworld/Beans.xml");
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        System.out.println(obj.getMessage());
    }

    /**
     * XmlBeanFactory
     */
    @Test
    public void test2() {
        XmlBeanFactory factory = new XmlBeanFactory(new ClassPathResource("helloworld/Beans.xml"));
        HelloWorld obj = (HelloWorld) factory.getBean("helloWorld");
        System.out.println(obj.getMessage());
    }

    /**
     * 文件系统读取配置
     */
    @Test
    public void test3() {
        ApplicationContext context = new FileSystemXmlApplicationContext("C:/workspace/sss/springdemo/src/main/resources/helloworld/Beans.xml");
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        System.out.println(obj.getMessage());
    }

    /**
     * BeanPostProcessor
     */
    @Test
    public void test4() {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("helloworld/Beans-4.xml");
        HelloWorld obj = (HelloWorld) context.getBean("helloWorld");
        System.out.println(obj.getMessage());
        context.registerShutdownHook();
    }
}
