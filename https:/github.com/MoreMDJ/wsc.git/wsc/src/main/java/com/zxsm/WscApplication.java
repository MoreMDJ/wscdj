package com.zxsm;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class WscApplication extends SpringBootServletInitializer{
	
	// war add
	@Override  
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {  
        return application.sources(WscApplication.class);  
    } 
	//war end
	
	public static void main(String[] args) {
		SpringApplication.run(WscApplication.class, args);
	}
}
