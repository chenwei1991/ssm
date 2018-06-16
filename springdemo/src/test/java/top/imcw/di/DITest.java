package top.imcw.di;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class DITest {
    /**
     * 注入（自动 autowire/手动）
     */
    @Test
    public void test() {
        ApplicationContext context = new ClassPathXmlApplicationContext("di/Beans.xml");
        Mobile mobile = (Mobile) context.getBean("mobile");
        mobile.runApp();
    }
}
