
package com.hfzq.demodb.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.sql.DataSource;
import java.sql.SQLException;

/**
 * @author Ted Wang
 * @created 2020/2/21 下午3:47
 */
@Configuration
@Slf4j
public class DruidConfig {
    /**
     *  url
     */
    @Value("${spring.datasource.url}")
    private String dbUrl;
    /**
     *username
     */
    @Value("${spring.datasource.username}")
    private String username;
    /**
     *password
     */
    @Value("${spring.datasource.password}")
    private String password;
    /**
     *driverClassName
     */
    @Value("${spring.datasource.driver-class-name}")
    private String driverClassName;
    /**
     * initialSize
     */
    @Value("${spring.datasource.initialSize}")
    private int initialSize;
    /**
     *minIdle
     */
    @Value("${spring.datasource.minIdle}")
    private int minIdle;
    /**
     *maxActive
     */
    @Value("${spring.datasource.maxActive}")
    private int maxActive;
    /**
     * maxWait
     */
    @Value("${spring.datasource.maxWait}")
    private int maxWait;
    /**
     * timeBetweenEvictionRunsMillis
     */
    @Value("${spring.datasource.timeBetweenEvictionRunsMillis}")
    private int timeBetweenEvictionRunsMillis;
    /**
     *
     */
    @Value("${spring.datasource.minEvictableIdleTimeMillis}")
    private int minEvictableIdleTimeMillis;
    /**
     *
     */
    @Value("${spring.datasource.validationQuery}")
    private String validationQuery;
    /**
     *
     */
    @Value("${spring.datasource.testWhileIdle}")
    private boolean testWhileIdle;
    /**
     *
     */
    @Value("${spring.datasource.testOnBorrow}")
    private boolean testOnBorrow;
    /**
     *
     */
    @Value("${spring.datasource.testOnReturn}")
    private boolean testOnReturn;
    /**
     *
     */
    @Value("${spring.datasource.poolPreparedStatements}")
    private boolean poolPreparedStatements;
    /**
     *
     */
    @Value("${spring.datasource.maxPoolPreparedStatementPerConnectionSize}")
    private int maxPoolPreparedStatementPerConnectionSize;
    /**
     *
     */
    @Value("${spring.datasource.filters}")
    private String filters;
    /**
     *
     */
    @Value("{spring.datasource.connectionProperties}")
    private String connectionProperties;
    
    @Bean
    @Primary
    public DataSource dataSource() {
        DruidDataSource datasource = new DruidDataSource();
        log.info("----------- druid datasource ----------");
        
        datasource.setUrl(this.dbUrl);
        datasource.setUsername(username);
        datasource.setPassword(password);
        datasource.setDriverClassName(driverClassName);
        
        //configuration
        datasource.setInitialSize(initialSize);
        datasource.setMinIdle(minIdle);
        datasource.setMaxActive(maxActive);
        datasource.setMaxWait(maxWait);
        datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
        datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
        datasource.setValidationQuery(validationQuery);
        datasource.setTestWhileIdle(testWhileIdle);
        datasource.setTestOnBorrow(testOnBorrow);
        datasource.setTestOnReturn(testOnReturn);
        datasource.setPoolPreparedStatements(poolPreparedStatements);
        datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
        try {
            datasource.setFilters(filters);
        } catch (SQLException e) {
            log.error("druid configuration Exception", e);
        }
        datasource.setConnectionProperties(connectionProperties);
        
        return datasource;
    }
    
    @Bean
    public ServletRegistrationBean statViewServlet() {
        // 创建servlet注册实体
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(new StatViewServlet());
        // druid监控统计路径
        servletRegistrationBean.addUrlMappings("/druid/*");
        // 设置ip白名单(没有配置或者为空，则允许所有访问)
        servletRegistrationBean.addInitParameter("allow", "");
        // 设置ip黑名单,如果allow与deny共同存在时,deny优先于allow
        servletRegistrationBean.addInitParameter("deny", "");
        // 设置控制台管理用户
        servletRegistrationBean.addInitParameter("loginUsername", "admin");
        servletRegistrationBean.addInitParameter("loginPassword", "admin");
        // 是否可以重置数据
        servletRegistrationBean.addInitParameter("resetEnable", "true");
        return servletRegistrationBean;
    }
    
    @Bean
    public FilterRegistrationBean statFilter() {
        // 创建过滤器
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
        filterRegistrationBean.addUrlPatterns("/*");
        // 忽略过滤的形式
        filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
        return filterRegistrationBean;
    }


//    public static void main(String[] args) {
//        MySqlStatementParser mySqlStatementParser = new MySqlStatementParser("select * from cpic_connectors;select * from cpic_cron_cfg where name like '%;%';");
//        List<SQLStatement>  sqlStatements=mySqlStatementParser.parseStatementList();
//
//        for(SQLStatement sqlStatement:sqlStatements){
//            log.info("#### "+sqlStatement.toString());
//            if(sqlStatement instanceof  SQLSelectStatement){
//                SQLSelectStatement sqlSelectStatement=new SQLSelectStatement();
//                sqlSelectStatement=(SQLSelectStatement) sqlStatement;
//
//                SQLSelect sqlSelect = sqlSelectStatement.getSelect();
//                SQLSelectQuery sqlSelectQuery = sqlSelect.getQuery();
//
//                log.info( "############query##############"+sqlSelectQuery.toString());
//                MySqlOutputVisitor where = new MySqlOutputVisitor(new StringBuilder());
//
//                MySqlSelectQueryBlock mySqlSelectQueryBlock = (MySqlSelectQueryBlock) sqlSelectQuery;
//
//                // 获取表名
//                System.out.println("############table_name##############");
//                MySqlOutputVisitor tableName = new MySqlOutputVisitor(new StringBuilder());
//                mySqlSelectQueryBlock.getFrom().accept(tableName);
//                System.out.println(tableName.getAppender());
//
//                //   获取查询字段
//                System.out.println("############查询字段##############");
//                System.out.println(mySqlSelectQueryBlock.getSelectList());
//            }
//        }
//
//    }
}
