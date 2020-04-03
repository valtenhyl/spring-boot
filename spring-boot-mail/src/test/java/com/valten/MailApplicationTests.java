package com.valten;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;

@SpringBootTest
class MailApplicationTests {

    @Autowired
    private JavaMailSender mailSender;

    // 发送简单邮件
    @Test
    void sendSimpleMailMessage() {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setSubject("小白你好啊~");
        mailMessage.setText("谢谢狂神说Java的系列课程~");
        mailMessage.setFrom("491889884@qq.com");
        mailMessage.setTo("491889884@qq.com");

        mailSender.send(mailMessage);
    }

    // 发送复杂邮件
    @Test
    void sendMimeMessage() throws MessagingException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setSubject("小白你好啊~");
        helper.setText("<h1 style='color:red;'>谢谢狂神说Java的系列课程~</h1>", true);
        helper.addAttachment("1.jpg", new File("C:\\Users\\tao\\Pictures\\Saved Pictures\\002.jpg"));
        helper.addAttachment("README.md", new File("C:\\Users\\tao\\Desktop\\apache-tomcat-9.0.24-psi-probe\\README.md"));
        helper.setFrom("491889884@qq.com");
        helper.setTo("491889884@qq.com");

        mailSender.send(mimeMessage);
    }

}
