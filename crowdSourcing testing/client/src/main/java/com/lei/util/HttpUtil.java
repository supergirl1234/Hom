package com.lei.util;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;
import java.util.Date;

public class HttpUtil {

    public static String emailAccount;
    public static String emailPassword="bbbiscfdosopdjdi";//发件人邮箱授权码
    public static String emailSMTPHost="smtp.qq.com";
    public static String receiveMailAccount;

    /*创建一封邮件*/
    public static MimeMessage createMimeMessage(Session session, String sendMail, String receiveMail, String html) {
        /*1、创建一封邮件对象*/
        MimeMessage message = new MimeMessage(session);
        try {

            /*2、发件人*/
            message.setFrom(new InternetAddress(sendMail, "发件人", "UTF-8"));
            /*3、收件人*/
            message.setRecipient(MimeMessage.RecipientType.TO,new InternetAddress(receiveMail,"UTF-8"));
           /*4、邮件主题*/
           message.setSubject("邮箱验证","UTF-8");
           /*5、邮件正文*/
            message.setContent(html,"text/html;charset=UTF-8");
           /*6、设置发送时间*/
            message.setSentDate(new Date());
            /*7、保存设置*/
            message.saveChanges();
            return  message;
        } catch (MessagingException | UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return  message;
    }
}
