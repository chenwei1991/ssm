package top.imcw.transaction;

import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.ProxyFactoryBean;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
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
import org.springframework.transaction.interceptor.TransactionProxyFactoryBean;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.util.ArrayList;

@Configuration
@ComponentScan("top.imcw.transaction")
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

    @Bean
    public TransactionTemplate transactionTemplate(@Autowired DataSourceTransactionManager transactionManager) {
        TransactionTemplate template = new TransactionTemplate();
        template.setTransactionManager(transactionManager);
        return template;
    }

    @Bean
    public TransactionProxyFactoryBean transactionProxyFactoryBean(@Autowired DataSourceTransactionManager transactionManager, @Autowired StudentJDBCTemplate studentJDBCTemplate) {
        TransactionProxyFactoryBean bean = new TransactionProxyFactoryBean();
        bean.setTransactionAttributeSource((method, aClass) -> new DefaultTransactionAttribute());
        bean.setTransactionManager(transactionManager);
        bean.setTarget(studentJDBCTemplate);
        bean.setProxyInterfaces(new Class[]{StudentDAO.class});
//        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
//        pointcut.setExpression("execution(* top.imcw.transaction.StudentJDBCTemplate.create3(..))");
//        bean.setPointcut(pointcut);
        return bean;
    }

    @Bean
    public ProxyFactoryBean proxyFactoryBean(@Autowired DataSourceTransactionManager transactionManager, @Autowired StudentJDBCTemplate studentJDBCTemplate) {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(* top.imcw.transaction.StudentJDBCTemplate.create4(..))");

        TransactionInterceptor transactionInterceptor = new TransactionInterceptor();
        transactionInterceptor.setTransactionAttributeSource((method, aClass) -> new DefaultTransactionAttribute());
        transactionInterceptor.setTransactionManager(transactionManager);

        Advisor advisor = new DefaultPointcutAdvisor(pointcut, transactionInterceptor);


        ProxyFactoryBean factoryBean = new ProxyFactoryBean();
        factoryBean.setInterfaces(new Class[]{StudentDAO.class});
        factoryBean.setTarget(studentJDBCTemplate);
        factoryBean.addAdvisor(advisor);
        return factoryBean;
    }

//    public BeanNameAutoProxyCreator beanNameAutoProxyCreator(){
//        BeanNameAutoProxyCreator creator =new BeanNameAutoProxyCreator();
//        creator.setBeanNames(new String[]{""});
//    }
}
