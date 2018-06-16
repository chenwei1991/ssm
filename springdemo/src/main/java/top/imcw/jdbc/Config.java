package top.imcw.jdbc;

import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.transaction.interceptor.DefaultTransactionAttribute;
import org.springframework.transaction.interceptor.TransactionInterceptor;

import javax.sql.DataSource;

@Configuration
@ComponentScan("top.imcw.jdbc")
public class Config {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUsername("tech-yanfa04");
        dataSource.setPassword("123546");
        dataSource.setUrl("jdbc:mysql://192.168.2.5:3306/management");
        return dataSource;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(@Autowired DataSource dataSource) {
        return new JdbcTemplate(dataSource);
    }


    @Bean
    public DataSourceTransactionManager dataSourceTransactionManager(@Autowired DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean("aaa")
    public ProxyFactoryBean proxyFactoryBean(@Autowired StudentDAO target, @Autowired DataSourceTransactionManager transactionManager) {
        // 构造advice
        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionManager(transactionManager);
        transactionInterceptor.setTransactionAttributeSource((method, aClass) -> new DefaultTransactionAttribute());

        // 构造pointcut
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* top.imcw.jdbc.StudentJDBCTemplate.create2(..))");

        // 构造advisor
        DefaultPointcutAdvisor advisor = new DefaultPointcutAdvisor();
        advisor.setAdvice(transactionInterceptor);
        advisor.setPointcut(pointcut);

        // 构造proxyFactoryBean
        ProxyFactoryBean proxyFactoryBean = new ProxyFactoryBean();
        proxyFactoryBean.setTarget(target);
        proxyFactoryBean.addAdvisor(advisor);
        return proxyFactoryBean;
    }
}
