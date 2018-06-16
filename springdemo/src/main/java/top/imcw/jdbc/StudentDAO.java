package top.imcw.jdbc;

import java.util.List;


public interface StudentDAO {
    void create(String name, Integer age);

    Student getStudent(Integer id);

    List<Student> listStudents();

    void delete(Integer id);

    void update(Integer id, Integer age);

    Student getStudent1(Integer id);

    void create(String name, Integer age, Integer marks, Integer year);

    void create1(String name, Integer age, Integer marks, Integer year);

    void create2(String name, Integer age, Integer marks, Integer year);

    List<StudentMarks> listStudentMarks();
}