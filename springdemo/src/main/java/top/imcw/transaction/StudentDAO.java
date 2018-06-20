package top.imcw.transaction;

import java.util.List;


public interface StudentDAO {

    void create(String name, Integer age, Integer marks, Integer year);

    void create1(String name, Integer age, Integer marks, Integer year);

    void create2(String name, Integer age, Integer marks, Integer year);

    void create3(String name, Integer age, Integer marks, Integer year);

    void create4(String name, Integer age, Integer marks, Integer year);

    List<StudentMarks> listStudentMarks();
}