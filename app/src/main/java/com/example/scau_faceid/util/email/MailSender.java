package com.example.scau_faceid.util.email;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {

    public boolean sendTextMail(final com.example.scau_faceid.util.email.MailInfo mailInfo){
        //判断是否需要身份验证
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();

        if(mailInfo.isValidate()){
            //如果需要身份验证，则创建密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUserName(),mailInfo.getPassword());
        }
        //根据会话属性和密码验证器构造一个发送邮件的session
        Session sendMailSession = Session.getDefaultInstance(pro,authenticator);

        try {
            //根据session创建一个邮件消息
            Message mailMassage = new MimeMessage(sendMailSession);
            //创建邮件的发送者地址
            Address from = new InternetAddress(mailInfo.getFromAddress());
            //设置邮件消息的发送者
            mailMassage.setFrom(from);
            //创建邮件的接收地址，并设置到邮件消息中
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMassage.setRecipient(Message.RecipientType.TO,to);
            //设置邮件的主题
            mailMassage.setSubject(mailInfo.getSubject());
            //设置邮件的发送时间
            mailMassage.setSentDate(new Date());

            //设置邮件的主要内容
            String mailContent = mailInfo.getContent();
            mailMassage.setText(mailContent);

            System.out.println("=====================");
            try{
                Transport.send(mailMassage);
            }catch (Exception e){
                System.out.println(e.fillInStackTrace());
            }
            //发送邮件

            System.out.println(mailMassage);
            System.out.println("----------------------");
            return true;
        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }
}
