package top.imcw.jdbc;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository("bbb")
public class StudentJDBCTemplate implements StudentDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Autowired
    private DataSource dataSource;

    @Autowired
    private PlatformTransactionManager transactionManager;

    public void create(String name, Integer age) {
        String SQL = "insert into test_student (name, age) values (?, ?)";
        jdbcTemplateObject.update(SQL, name, age);
//        System.out.println("Created Record Name = " + name + " Age = " + age);
        return;
    }

    public Student getStudent(Integer id) {
        String SQL = "select * from test_student where id = ?";
        Student student = jdbcTemplateObject.queryForObject(SQL, new Object[]{id}, new StudentMapper());
        return student;
    }

    public List<Student> listStudents() {
        String SQL = "select * from test_student";
        List<Student> students = jdbcTemplateObject.query(SQL, new StudentMapper());
        return students;
    }

    public void delete(Integer id) {
        String SQL = "delete from test_student where id = ?";
        jdbcTemplateObject.update(SQL, id);
//        System.out.println("Deleted Record with ID = " + id);
        return;
    }

    public void update(Integer id, Integer age) {
        String SQL = "update test_student set age = ? where id = ?";
        jdbcTemplateObject.update(SQL, age, id);
//        System.out.println("Updated Record with ID = " + id);
        return;
    }

    public Student getStudent1(Integer id) {
        SqlParameterSource in = new MapSqlParameterSource().addValue("in_id", id);
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(dataSource).withProcedureName("getRecord");
        Map<String, Object> out = jdbcCall.execute(in);
        Student student = new Student();
        student.setId(id);
        student.setName((String) out.get("out_name"));
        student.setAge((Integer) out.get("out_age"));
        return student;
    }

    public void create(String name, Integer age, Integer marks, Integer year) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String SQL1 = "insert into test_student (name, age) values (?, ?)";
            jdbcTemplateObject.update(SQL1, name, age);
            // Get the latest student id to be used in Marks table
            String SQL2 = "select max(id) from test_student";
            int sid = jdbcTemplateObject.queryForObject(SQL2, int.class);
            String SQL3 = "insert into test_marks(sid, marks, year) values (?, ?, ?)";
            jdbcTemplateObject.update(SQL3, sid, marks, year);
//            System.out.println("Created Name = " + name + ", Age = " + age);
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            System.out.println("Error in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        }
    }

    public void create1(String name, Integer age, Integer marks, Integer year) {
        TransactionDefinition def = new DefaultTransactionDefinition();
        TransactionStatus status = transactionManager.getTransaction(def);
        try {
            String SQL1 = "insert into test_student (name, age) values (?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplateObject.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setInt(2, age);
                return statement;
            }, keyHolder);

            // Get the latest student id to be used in Marks table
            int sid = keyHolder.getKey().intValue();
            String SQL3 = "insert into test_marks(sid, marks, year) values (?, ?, ?)";
            jdbcTemplateObject.update(SQL3, sid, marks, year);
//            System.out.println("Created Name = " + name + ", Age = " + age);
            transactionManager.commit(status);
        } catch (DataAccessException e) {
            System.out.println("Error in creating record, rolling back");
            transactionManager.rollback(status);
            throw e;
        }
    }

    public void create2(String name, Integer age, Integer marks, Integer year) {
        try {
            String SQL1 = "insert into test_student (name, age) values (?, ?)";
            KeyHolder keyHolder = new GeneratedKeyHolder();
            jdbcTemplateObject.update(connection -> {
                PreparedStatement statement = connection.prepareStatement(SQL1, Statement.RETURN_GENERATED_KEYS);
                statement.setString(1, name);
                statement.setInt(2, age);
                return statement;
            }, keyHolder);

            // Get the latest student id to be used in Marks table
            int sid = keyHolder.getKey().intValue();
            String SQL3 = "insert into test_marks1(sid, marks, year) values (?, ?, ?)";
            jdbcTemplateObject.update(SQL3, sid, marks, year);
//            System.out.println("Created Name = " + name + ", Age = " + age);
        } catch (DataAccessException e) {
//            System.out.println("Error in creating record, rolling back");
            throw e;
        }
    }

    public List<StudentMarks> listStudentMarks() {
        String SQL = "select * from test_student, test_marks where test_student.id=test_marks.sid";
        List<StudentMarks> studentMarks = jdbcTemplateObject.query(SQL, new StudentMarksMapper());
        return studentMarks;
    }
}
