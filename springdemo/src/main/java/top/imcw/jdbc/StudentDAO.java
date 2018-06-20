package top.imcw.jdbc;

import java.util.List;


public interface StudentDAO {
    void create(String name, Integer age);

    int create(Integer age,String name);

    Student getStudent(Integer id);

    List<Student> listStudents();

    void delete(Integer id);

    void update(Integer id, Integer age);

    Student getStudent1(Integer id);
}