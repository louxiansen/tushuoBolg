package com.tushuoBolg.config;

import com.tushuoBolg.component.UserLoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Created by hyrj on 2019/10/18.
 */
@Configuration
public class CasInterceptorConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new UserLoginInterceptor())
                .addPathPatterns("/manager")
                .addPathPatterns("/user")
                .order(1);
    }
}
