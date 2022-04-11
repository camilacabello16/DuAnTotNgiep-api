package com.globits.da.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.globits.da.WorkSpaceConstants;

@RestController
@RequestMapping("/api/send-email")
public class RestSimpleEmailController {

    @Autowired
    public JavaMailSender emailSender;

    @PostMapping
    public Boolean sendSimpleEmail(@RequestParam(name = "email") String email) throws IOException, MessagingException {

        // Create a Simple MailMessage.
    	MimeMessage message = emailSender.createMimeMessage();
    	MimeMessageHelper helper = new MimeMessageHelper(message, true, "utf-8");
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream("email_content_html.txt");
        StringBuilder resultStringBuilder = new StringBuilder();
        try (BufferedReader br
          = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append("\n");
            }
        }
        helper.setTo(email);
        helper.setSubject("Test Simple Email");
        message.setContent(resultStringBuilder.toString(),"text/html");

        // Send Message!
        this.emailSender.send(message);

        return true;
    }

}
