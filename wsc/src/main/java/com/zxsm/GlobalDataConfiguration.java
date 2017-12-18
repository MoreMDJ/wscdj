//package com.zxsm;
//
//
//import javax.sql.DataSource;
//
//import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
//import org.springframework.boot.context.properties.ConfigurationProperties;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Primary;
//
//@Configuration
//public class GlobalDataConfiguration {
//	@Bean(name="primaryDataSource")  
//    @Primary
//    @ConfigurationProperties(prefix="datasource.primary")  
//    public DataSource primaryDataSource() {  
//        System.out.println("-------------------- primaryDataSource init ---------------------");  
//        return DataSourceBuilder
//        		.create()
//        		.build();
//    }
//}
