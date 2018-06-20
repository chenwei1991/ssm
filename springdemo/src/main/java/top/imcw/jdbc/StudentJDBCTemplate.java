package top.imcw.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

@Repository
public class StudentJDBCTemplate implements StudentDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Autowired
    private DataSource dataSource;

    public void create(String name, Integer age) {
        String SQL = "insert into test_student (name, age) values (?, ?)";
        jdbcTemplateObject.update(SQL, name, age);
//        System.out.println("Created Record Name = " + name + " Age = " + age);
        return;
    }

    @Override
    public int create(Integer age, String name) {
        String SQL = "insert into test_student (name, age) values (?, ?)";
        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplateObject.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, name);
            statement.setInt(2, age);
            return statement;
        }, holder);
        return holder.getKey().intValue();
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


}
