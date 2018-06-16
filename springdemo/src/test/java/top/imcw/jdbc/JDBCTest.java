package top.imcw.jdbc;

import org.aopalliance.aop.Advice;
import org.junit.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import java.util.List;

public class JDBCTest {
    /**
     * 普通CRUD
     */
    @Test
    public void test1() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        StudentDAO dao = context.getBean(StudentDAO.class);
        dao.create("Zara", 11);
        dao.create("Nuha", 2);
        dao.create("Ayan", 15);
        System.out.println("------Listing Multiple Records--------");
        List<Student> students = dao.listStudents();
        for (Student record : students) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            System.out.println(", Age : " + record.getAge());
        }
        System.out.println("----Updating Record with ID = 2 -----");
        dao.update(2, 20);
        System.out.println("----Listing Record with ID = 2 -----");
        Student student = dao.getStudent(2);
        System.out.print("ID : " + student.getId());
        System.out.print(", Name : " + student.getName());
        System.out.println(", Age : " + student.getAge());
        System.out.println("----Deleting Record with ID = 1 -----");
        dao.delete(1);
        System.out.println("------Listing Multiple Records--------");
        List<Student> students1 = dao.listStudents();
        for (Student record : students1) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            System.out.println(", Age : " + record.getAge());
        }

    }

    /**
     * 执行存储过程
     */
    @Test
    public void test2() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        StudentDAO dao = context.getBean(StudentDAO.class);
        Student student = dao.getStudent1(2);
        System.out.print("Name : " + student.getName());
        System.out.println(", Age : " + student.getAge());
    }

    /**
     * 编程式事务
     */
    @Test
    public void test3() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate) context.getBean("studentJDBCTemplate");
        System.out.println("------Records creation--------");
        studentJDBCTemplate.create("Zara", 11, 99, 2010);
        studentJDBCTemplate.create("Nuha", 20, 97, 2010);
        studentJDBCTemplate.create("Ayan", 25, 100, 2011);
        System.out.println("------Listing all the records--------");
        List<StudentMarks> studentMarks = studentJDBCTemplate.listStudentMarks();
        for (StudentMarks record : studentMarks) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            System.out.print(", Marks : " + record.getMarks());
            System.out.print(", Year : " + record.getYear());
            System.out.println(", Age : " + record.getAge());
        }
    }

    /**
     * KeyHolder获取自动生成的ID
     */
    @Test
    public void test4() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate) context.getBean("studentJDBCTemplate");
        System.out.println("------Records creation--------");
        studentJDBCTemplate.create1("KeyHolder", 11, 99, 2010);
//        studentJDBCTemplate.create("Nuha", 20, 97, 2010);
//        studentJDBCTemplate.create("Ayan", 25, 100, 2011);
        System.out.println("------Listing all the records--------");
        List<StudentMarks> studentMarks = studentJDBCTemplate.listStudentMarks();
        for (StudentMarks record : studentMarks) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            System.out.print(", Marks : " + record.getMarks());
            System.out.print(", Year : " + record.getYear());
            System.out.println(", Age : " + record.getAge());
        }
    }

    /**
     * 声明式事务（配置文件）
     */
    @Test
    public void test5() {
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/Beans.xml");
        ProxyFactoryBean bean = context.getBean(ProxyFactoryBean.class);
        System.out.println(bean.getObjectType());

        StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate) (bean.getObject());

//        StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate) context.getBean("aaa");

        System.out.println("------Records creation--------");
        studentJDBCTemplate.create2("Zara", 99, 99, 2010);
//        studentJDBCTemplate.create("Nuha", 20, 97, 2010);
//        studentJDBCTemplate.create("Ayan", 25, 100, 2011);
        System.out.println("------Listing all the records--------");
        List<StudentMarks> studentMarks = studentJDBCTemplate.listStudentMarks();
        for (StudentMarks record : studentMarks) {
            System.out.print("ID : " + record.getId());
            System.out.print(", Name : " + record.getName());
            System.out.print(", Marks : " + record.getMarks());
            System.out.print(", Year : " + record.getYear());
            System.out.println(", Age : " + record.getAge());
        }
    }



    public void test6(){
        ApplicationContext context = new ClassPathXmlApplicationContext("jdbc/Beans.xml");
        StudentJDBCTemplate studentJDBCTemplate = (StudentJDBCTemplate) context.getBean("bbb");
        ProxyFactoryBean factory = new ProxyFactoryBean();
        factory.setTarget(studentJDBCTemplate);

        //声明一个aspectj切点
        AspectJExpressionPointcut cut = new AspectJExpressionPointcut();

        //设置需要拦截的方法-用切点语言来写
        cut.setExpression("execution(* top.imcw.jdbc.StudentJDBCTemplate.create2(..))");//拦截:空参返回值为int的run方法


//        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
//        transactionInterceptor.setTransactionManager();
//        transactionInterceptor.setTransactionAttributeSource((method, aClass) -> new DefaultTransactionAttribute());
//
//        Advice advice = new MethodInterceptor() {
//            @Override
//            public Object invoke(MethodInvocation invocation) throws Throwable {
//                System.out.println("放行前拦截...");
//                Object obj = invocation.proceed();//放行
//                System.out.println("放行后拦截...");
//                return obj;
//            }
//        };
//
//        //切面=切点+通知
//        Advisor advisor = new DefaultPointcutAdvisor(cut,advice);
//        factory.addAdvisor(advisor);
//        Person p = (Person) factory.getObject();
//
//        p.run();
//        p.run(10);
//        p.say();
//        p.sayHi("Jack");
//        p.say("Tom", 666);
    }

}
