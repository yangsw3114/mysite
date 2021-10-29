package com.douzone.config.app;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:com/douzone/mysite/config/app/jdbc.properties")
public class DBConfig {
	
	@Autowired
	private Environment env;
	
	@Bean
	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName(env.getProperty("jdbc.driverClassName"));
//		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		
		dataSource.setUrl(env.getProperty("jdbc.url"));
//		dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/webdb?characterEncoding=utf8");
		
		dataSource.setUsername(env.getProperty("jdbc.username"));
//		dataSource.setUsername("webdb");
		
		dataSource.setPassword(env.getProperty("jdbc.password"));
//		dataSource.setPassword("webdb");
		
		dataSource.setInitialSize(env.getProperty("jdbc.initialSize", Integer.class));
//		dataSource.setInitialSize(100);
		
		dataSource.setMaxActive(env.getProperty("jdbc.maxActive",Integer.class));
//		dataSource.setMaxActive(200);
		
		return dataSource;
	}
}