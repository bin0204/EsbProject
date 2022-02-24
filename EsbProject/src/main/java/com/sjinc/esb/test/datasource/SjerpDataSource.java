package com.sjinc.esb.test.datasource;

import javax.sql.DataSource;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
public class SjerpDataSource {
   private String prefix;
   private Environment env;

   public SjerpDataSource(Environment env) {
      this.env = env;
      this.prefix = "SJERP_DS"; //
   }

   @Bean("DATASOURCE_SJERP")
   public DataSource dataSource() {
      DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create();
      dataSourceBuilder.driverClassName(getProperty("datasource.driver-class-name"));
      dataSourceBuilder.url(getProperty("datasource.jdbc-url"));
      dataSourceBuilder.username(getProperty("datasource.username"));
      dataSourceBuilder.password(getProperty("datasource.password"));

      HikariDataSource dataSource = (HikariDataSource) dataSourceBuilder.build();
      dataSource.setPoolName(getProperty("datasource.hikari.name"));
      dataSource.setMinimumIdle(Integer.valueOf(getProperty("datasource.hikari.minimum-idle").trim()));
      dataSource.setMaximumPoolSize(Integer.valueOf(getProperty("datasource.hikari.maximum-pool-size").trim()));

      return dataSource;
   }

   @Bean("DATASOURCE_SJERP_TX_MANAGER")
   DataSourceTransactionManager dataSourceTransactionManager() {
      DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
      dataSourceTransactionManager.setDataSource(dataSource());
      return dataSourceTransactionManager;
   }

   @Bean("DATASOURCE_SJERP_TX_MANAGER_SQL_SESSION_FACTORY")
   public SqlSessionFactory sqlSessionFactory(@Qualifier("DATASOURCE_SJERP") DataSource dataSource,
         ApplicationContext applicationContext) throws Exception {

      SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
      sqlSessionFactoryBean.setDataSource(dataSource);

      sqlSessionFactoryBean.setConfigLocation(applicationContext.getResource("classpath:MyBatisConfig.xml"));
      sqlSessionFactoryBean.setMapperLocations(applicationContext.getResources("classpath*:mappers/SJERP/*.xml"));

      return sqlSessionFactoryBean.getObject();
   }

   @Bean("SJERP_SQL_SESSION")
   SqlSessionTemplate sqlSessionTemplate(
         @Qualifier("DATASOURCE_SJERP_TX_MANAGER_SQL_SESSION_FACTORY") SqlSessionFactory sqlSessionFactory) {
      return new SqlSessionTemplate(sqlSessionFactory);
   }

   protected String getProperty(String key) {
      String prefixKey = String.format("%s.%s", prefix, key);
      return env.getProperty(prefixKey);
   }
}
