package com.shyy.travel.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyConfig implements WebMvcConfigurer {

    @Autowired
    private InterceptorConfig interceptorConfig;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }



    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //registry.addInterceptor(interceptorConfig).addPathPatterns()
        registry.addInterceptor(interceptorConfig).addPathPatterns("/admin/**").excludePathPatterns("/admin/login").excludePathPatterns("/admin/adminlogin").excludePathPatterns("/admin/captcha.jpg");
    }

    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("forward:/dist/view");

        registry.addViewController("/admin").setViewName("forward:/admin/adminlogin");
        registry.addViewController("/admin").setViewName("forward:/admin/captcha.jpg");

        registry.setOrder(Ordered.HIGHEST_PRECEDENCE);
    }
}
