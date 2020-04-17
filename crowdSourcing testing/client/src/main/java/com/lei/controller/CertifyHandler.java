package com.lei.controller;

import com.lei.entity.EngineerInfo;
import com.lei.service.EngineerInfoService;
import com.lei.service.ProjectService;
import com.lei.util.HttpUtil;
import com.lei.util.htmlText;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

/*
* PathVariable只能用于接收url路径上的参数,例如这种/Certify/{phone_number}，
* 而RequestParam只能用于接收请求带的params，例如这种;http://localhost:8081/SendEmailServlet?email="+email
* */
/*信息审核*/
@Controller
public class CertifyHandler {

    @Autowired
    public EngineerInfoService engineerInfoService;



    /*跳转到邮箱认证页面*/
    @GetMapping("/Certify/{phone_number}")
    public String Certify(@PathVariable("phone_number") String phone_number, Model model){
        /*先查看是否已经进行了信息审核，如果已经进行了，则直接现实信息*/
        List<EngineerInfo> engineerInfoList= engineerInfoService.getEngineerInfo(phone_number);
        if(engineerInfoList.size()!=0){
            model.addAttribute("engineerInfo",engineerInfoList.get(0));
            return "engineerInfo";
        }else {
            model.addAttribute("phone_number", phone_number);
            return "certification";
        }
    }

    /*企业邮箱认证*/
    @GetMapping("/doCertify/{phone_number}")
    public String doCertify(@PathVariable("phone_number") String phone_number,HttpServletRequest request, HttpServletResponse response,Model model){

        /*获取session中的验证码*/
        String sessionCode=(String)request.getSession().getAttribute("code");
        System.out.println(sessionCode);
        /**/
        if(sessionCode!=null){
            /*获取页面提交的验证码*/
            String inputCode=request.getParameter("code");
            System.out.println("页面提交的验证码："+inputCode);
            if (sessionCode.toLowerCase().equals(inputCode.toLowerCase())){
                /*验证成功*/
               /* Cookie[] cookies=request.getCookies();
                String phone_number=cookies[0].getValue();*/
               /* String phone_number= (String) model.getAttribute("phone_number");
                String name=request.getParameter("username");*/
                String name=request.getParameter("username");
                String ID=request.getParameter("idCard");
                String email=request.getParameter("email");
                String enterprise=request.getParameter("enterprise");
                String enterpriseEmail=request.getParameter("enterpriseEmail");
                EngineerInfo engineerInfo=new EngineerInfo();
                engineerInfo.setPhone_number(phone_number);
                engineerInfo.setRealName(name);
                engineerInfo.setID(ID);
                engineerInfo.setEmail(email);
                engineerInfo.setEnterprise(enterprise);
                engineerInfo.setEnterpriseEmail(enterpriseEmail);
                /*把信息注册到数据库*/
                engineerInfoService.addEngineerInfo(engineerInfo);

                /*并且删除session中的验证码*/
                request.removeAttribute("code");
                return "success";//
            }else{
                /*验证失败*/
                return "certification";
            }

        }
        return  null;
    }

   // @GetMapping("/SendEmailServlet/{email}")
    @GetMapping("/SendEmailServlet")
    @ResponseBody
    public void SendEmailServlet(@RequestParam("enterpriseEmail") String email, HttpServletRequest request, HttpServletResponse response){
        try {
            /* 获取邮箱*/
            //String email = (String) request.getAttribute("email");

            /*  给该邮箱发送邮件*/
            HttpUtil.receiveMailAccount = email;

            /* 1、创建参数配置，用于连接邮箱服务器的参数配置*/
            Properties properties = new Properties();
            /* 开启debug调式*/
            properties.setProperty("mail.debug", "true");
            /*  发送服务器需要身份验证*/
            properties.setProperty("mail.smpt.auth", "true");
            /*   设置右键服务器的主机名*/
            properties.setProperty("mail.host", HttpUtil.emailSMTPHost);
            /*    发送邮件协议名称*/
            properties.setProperty("mail.transport.protocol", "smtp");

            /*    2、根据配置创建会话对象，用于和邮件服务器交互*/
            Session session = Session.getInstance(properties);
            /*   设置debug，可以查看详细的发送log*/
            session.setDebug(true);

            /*  3、创建一封邮件*/
            // String code = RandomUtil.getRandom();
            String code =UUID.randomUUID().toString().substring(0,6);//验证码6位
            System.out.println("邮箱验证码：" + code);
            String html = htmlText.html(code);
            MimeMessage message = HttpUtil.createMimeMessage(session, email,HttpUtil.receiveMailAccount, html);


            /*  4、根据session获取邮件传输对象*/
            Transport transport = session.getTransport();
            /* 5、使用邮箱账号和密码连接邮箱服务器emailAccount,必须于message中的发件人一致*/
            transport.connect(email,HttpUtil.emailPassword);
            /* 6、发送邮件*/
            transport.sendMessage(message,message.getAllRecipients());
            /* 7、关闭连接*/
            transport.close();
            /*  8、写入session*/
            request.getSession().setAttribute("code",code);

        } catch (MessagingException e) {
            e.printStackTrace();
        }


    }
}
