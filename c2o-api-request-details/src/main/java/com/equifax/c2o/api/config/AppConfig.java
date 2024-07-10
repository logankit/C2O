package com.equifax.c2o.api.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = "com.equifax.c2o.api")
@EnableJpaRepositories(basePackages = "com.equifax.c2o.api.repository")
public class AppConfig {
}
