package com.github.brick.action.flow.sample.spring;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan(basePackages = "com.github.brick.action.flow")
@EnableAspectJAutoProxy
public class Config {

}
