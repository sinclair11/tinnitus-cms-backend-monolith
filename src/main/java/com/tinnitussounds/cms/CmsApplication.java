package com.tinnitussounds.cms;

import com.tinnitussounds.cms.config.RsaKeyProperties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.PropertySource;

@SpringBootApplication
@EnableAutoConfiguration
@PropertySource("application.properties")
@EnableConfigurationProperties(RsaKeyProperties.class)
public class CmsApplication {
	public static void main(String[] args) {
		SpringApplication.run(CmsApplication.class, args);
	}
}
