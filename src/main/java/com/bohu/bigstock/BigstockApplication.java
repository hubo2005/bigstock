package com.bohu.bigstock;

import com.imadcn.framework.idworker.generator.SnowflakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource({"classpath:META-INF/idworker-ctx.xml"})
@ComponentScan(basePackages = "com.bohu")
public class BigstockApplication {

	public static void main(String[] args) {
		SpringApplication.run(BigstockApplication.class, args);
	}


}
