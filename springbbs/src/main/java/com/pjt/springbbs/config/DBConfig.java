package com.pjt.springbbs.config;

import java.beans.PropertyVetoException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mchange.v2.c3p0.ComboPooledDataSource;

@Configuration
public class DBConfig {
	@Bean
	public ComboPooledDataSource dataSource() throws PropertyVetoException {
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		dataSource.setDriverClass("org.gjt.mm.mysql.Driver");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/bbs?useUnicode=true&characterEncoding=utf8");
		dataSource.setUser("root");
		dataSource.setPassword("0199");
		dataSource.setMaxPoolSize(200);
		dataSource.setCheckoutTimeout(60000);
		dataSource.setMaxIdleTime(1800);
		dataSource.setIdleConnectionTestPeriod(600);
		
		return dataSource;
	}
}
