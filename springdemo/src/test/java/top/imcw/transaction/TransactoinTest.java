package top.imcw.transaction;

import org.junit.Test;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;

import java.util.List;

public class TransactoinTest {
    /**
     * 编程式事务，使用TransactionManagement
     */
    @Test
    public void test1() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate) context.getBean("studentJDBCTemplate");
        System.out.println("------Records creation--------");
        studentJDBCTemplate.create("Zara", 11, 99, 2010);
        studentJDBCTemplate.create("Nuha", 20, 97, 2010);
        studentJDBCTemplate.create("Ayan", 25, 100, 2011);
        printList(studentJDBCTemplate);
    }

    private void printList(StudentDAO dao) {
        System.out.println("------Listing all the records--------");
        List<StudentMarks> studentMarks = dao.listStudentMarks();
        for (StudentMarks record : studentMarks) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            System.out.print(", Marks : " + record.getMarks());
            System.out.print(", Year : " + record.getYear());
            System.out.println(", Age : " + record.getAge());
        }
    }


    /**
     * 编程式事务，使用TransactionTemplate
     */
    @Test
    public void test2() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        StudentDAO studentJDBCTemplate = context.getBean(StudentDAO.class);
        System.out.println("------Records creation--------");
        studentJDBCTemplate.create1("Zara111", 11, 99, 2010);
        printList(studentJDBCTemplate);
    }

    /**
     * 声明式事务（TransactionProxyFactoryBean）
     */
    @Test
    public void test3() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        TransactionProxyFactoryBean proxyFactoryBean = context.getBean(TransactionProxyFactoryBean.class);
        StudentDAO dao = (StudentDAO) proxyFactoryBean.getObject();
        dao.create3("Zara11", 11, 99, 2010);
        printList(dao);
    }

    /**
     * 声明式事务（ProxyFactoryBean & Advisor）
     */
    @Test
    public void test4() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        ProxyFactoryBean proxyFactoryBean = context.getBean(ProxyFactoryBean.class);
        StudentDAO dao = (StudentDAO) proxyFactoryBean.getObject();
        dao.create4("Zara11", 11, 99, 2010);
        printList(dao);
    }

    @Test
    public void test5() {

    }
}
