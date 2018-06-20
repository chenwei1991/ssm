package top.imcw.jdbc;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

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
     * 获取自动生成的主键
     */
    @Test
    public void test3() {
        ApplicationContext context = new AnnotationConfigApplicationContext(Config.class);
        StudentDAO dao = context.getBean(StudentDAO.class);
        int id = dao.create(13, "张三");
        System.out.println(id);
    }

}
