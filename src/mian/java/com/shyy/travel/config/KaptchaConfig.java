package com.shyy.travel.config;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class KaptchaConfig {


    @Bean(name="kaptcha")
    public DefaultKaptcha kaptcha(){

        DefaultKaptcha kaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        //验证码的设置
        properties.setProperty("kaptcha.border","no");
        properties.setProperty("kaptcha.textproducer.char.length","4");//验证码个数
        properties.setProperty("kaptcha.textproducer.font.color","black");//字体颜色
        properties.setProperty("kaptcha.noise.color","white");
        Config config = new Config(properties);
        kaptcha.setConfig(config);

        return  kaptcha;

    }



}
