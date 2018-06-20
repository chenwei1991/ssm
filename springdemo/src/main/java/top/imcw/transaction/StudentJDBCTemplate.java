package top.imcw.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;

@Repository
public class StudentJDBCTemplate implements StudentDAO {
    @Autowired
    private JdbcTemplate jdbcTemplateObject;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

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
//        String SQL1 = "insert into test_student (name, age) values (?, ?)";

        transactionTemplate.execute(new TransactionCallbackWithoutResult() { // 使用TransactionCallback可以有返回值，使用TransactionCallbackWithoutResult则没有返回值
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                create2(name, age, marks, year);
            }
        });

    }

    @Override
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

    public void create3(String name, Integer age, Integer marks, Integer year) {
        create2(name, age, marks, year);
    }

    public void create4(String name, Integer age, Integer marks, Integer year) {
        create2(name, age, marks, year);
    }

    public List<StudentMarks> listStudentMarks() {
        String SQL = "select * from test_student, test_marks where test_student.id=test_marks.sid";
        List<StudentMarks> studentMarks = jdbcTemplateObject.query(SQL, new StudentMarksMapper());
        return studentMarks;
    }
}
