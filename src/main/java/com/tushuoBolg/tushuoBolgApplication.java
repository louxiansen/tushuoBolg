package com.tushuoBolg;

import com.tushuoBolg.filter.casFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@ServletComponentScan
public class tushuoBolgApplication {

	public static void main(String[] args) {
		SpringApplication.run(tushuoBolgApplication.class, args);
	}
}
