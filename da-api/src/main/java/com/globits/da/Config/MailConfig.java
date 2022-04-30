package com.globits.da.Config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import com.globits.da.WorkSpaceConstants;

@Configuration
public class MailConfig {
	
	@Autowired
	private Environment env;
    @Bean
    public JavaMailSender getJavaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(env.getProperty("spring.mail.host"));
        mailSender.setPort(Integer.parseInt(env.getProperty("spring.mail.port")));
        mailSender.setDefaultEncoding(env.getProperty("spring.mail.default-encoding"));
        mailSender.setUsername(env.getProperty("spring.mail.username"));
        mailSender.setPassword(env.getProperty("spring.mail.password"));

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol",env.getProperty("spring.mail.protocol"));
        props.put("mail.smtp.auth", env.getProperty("spring.main.allow-bean-definition-overriding"));
        props.put("mail.smtp.starttls.enable", env.getProperty("spring.main.allow-bean-definition-overriding"));
        props.put("mail.mime.charset", env.getProperty("spring.mail.default-encoding"));
        props.put("mail.debug", env.getProperty("spring.mail.debug"));
        return mailSender;
    }

}