package com.example.scau_faceid.util.email;

public class SendMailUtil {
    public static void main(String[] args) {

        new SendMailUtil("2188982126@qq.com","123456");
    }


    private static final String host = "smtp.qq.com";
    private static final String post = "587";
    private static final String fromAdd = "1959479486@qq.com";
    private static final String fromPsw = "lolwzpmityhjbagc";//授权码，授权码会在启用邮箱的POP3/SMTP服务时给到

    private static String code;


    public SendMailUtil(String toADD,String code){
        this.code=code;
        send(toADD);
    }

    public static void send(String toADD){

        final MailInfo mailInfo = createMail(toADD);
        final MailSender sms = new MailSender();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("多线程部分");
                sms.sendTextMail(mailInfo);
            }
        }).start();
    }

    private static MailInfo createMail(String toAdd){
        final MailInfo mailInfo = new MailInfo();
        mailInfo.setMailServerHost(host);
        mailInfo.setMailServerPort(post);
        mailInfo.setValidate(true);
        mailInfo.setUserName(fromAdd);//邮箱地址
        mailInfo.setPassword(fromPsw);//邮箱密码
        mailInfo.setFromAddress(fromAdd);//发送的邮箱
        mailInfo.setToAddress(toAdd);//发到哪个邮箱
        mailInfo.setSubject("Scau_FaceID");//发送标题
        mailInfo.setContent("验证码："+code);//发送文本
        return mailInfo;
    }
}
