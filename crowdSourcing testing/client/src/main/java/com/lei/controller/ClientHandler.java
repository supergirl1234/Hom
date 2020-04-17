package com.lei.controller;

import com.lei.entity.Account;
import com.lei.entity.EngineerInfo;
import com.lei.entity.Page;
import com.lei.entity.Project;
import com.lei.service.AccountService;
import com.lei.service.EngineerInfoService;
import com.lei.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;


/*注册登录*/
@Controller
public class ClientHandler {
    @Autowired
    public AccountService accountService;

    @Autowired
    public ProjectService projectService;

    /*首页,注册登录界面*/
    @GetMapping("/page")
    public String page(){
        return "page";
    }

    /*登录判断*/
    @GetMapping("/doLogin")
    public String login(@RequestParam("phone_number") String phone_number,
                        @RequestParam("password") String password, HttpServletRequest request,Model model){
        List<Map<String,Object>> list=accountService.isLogin(phone_number);
        /*如果没查询到，说明还未注册，则转到注册页面*/
        if(list.size()==0){
            return "register";
        }
        /*如果是密码不匹配，则转到登录页面，再次登录*/
        if(!list.get(0).containsValue(password)){
            return  "page";
        }

        /*向home界面传递登录者的登录手机号*/
        /*从数据库中获取该手机号的用户的昵称*/
        Account account =accountService.getAccountUserName(phone_number);
        //request.setAttribute("account",account);
        model.addAttribute("account",account);
        /*并且获取该工程师的所有项目*/
        List<Project> allProjects=projectService.getAllProject(phone_number);
        model.addAttribute("allProjects",allProjects);
      /*  Page page=projectService.getPage(phone_number);
        model.addAttribute("allPage",page);*/
        return  "home";
    }

   /*跳转到注册页面*/
    @GetMapping("/registerPage")
    public String registerPage(){
        return "register";
    }


    /*注册判断*/
    @GetMapping("/doRegister")
    public String register(@RequestParam("username") String username,
                           @RequestParam("phone_number") String phone_number,
                           @RequestParam("password") String password){

        boolean isRegisted=accountService.isRegister(phone_number);
        /*如果没有注册过*/
        if(isRegisted){
            /*添加用户*/
            Account account=new Account();
            account.setUsername(username);
            account.setPhone_number(phone_number);
            account.setPassword(password);
           boolean result= accountService.addEngineer(account);
           /*添加成功，则到登录界面去登录*/
           if(result){
               return "page";
           }
        }
        return "register";
    }

    /*到首页*/
    @GetMapping("/home")
    public  String home(){
        return "home";
    }

}
