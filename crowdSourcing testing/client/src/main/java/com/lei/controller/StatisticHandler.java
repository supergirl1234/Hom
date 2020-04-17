package com.lei.controller;

import com.lei.entity.Account;
import com.lei.entity.Record1;
import com.lei.service.AccountService;
import com.lei.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/*统计分析*/
@Controller
public class StatisticHandler {
    @Autowired
    public ProjectService projectService;

    @Autowired
    public AccountService accountService;

    /*统计系统有多少测试工程师*/
    /*统计每月的该工程师的任务量*/
    /*统计该工程师任务的功能类型所占比例：界面测试、性能测试、还是可靠性测试等*/
    /*统计该工程师任务的对象类型所占比例：是苹果还是安卓*/
    /*统计该工程师任务的平均测试完成情况*/


    /*去往统计分析的总页面*/
    @GetMapping("/Statistic/{phone_number}")
    public String Statistic(@PathVariable("phone_number") String phone_number, HttpServletRequest request) {
       Account account=accountService.getAccountUserName(phone_number);
        request.setAttribute("account", account);
        return "statistic";
    }

    /*去往charts1页面*/
    @GetMapping("/toEcharts1/{phone_number}")
    public String toEcharts1(@PathVariable("phone_number") String phone_number, Model model) {


        model.addAttribute("phone_number", phone_number);
        return "echarts1";
    }

    /*统计该工程师任务的对象类型所占比例：是苹果还是安卓*/
    @GetMapping("/projectType")
    @ResponseBody
    public List<Record1> getAllType(@RequestParam(value = "phone_number" ) String phone_number, HttpServletRequest request) {
        List<Map<String, Object>> allList = projectService.getProjectType(phone_number);
        System.out.println(allList);
        List<Record1> list = new ArrayList<>();
        for (int i = 0; i < allList.size(); i++) {
            Map<String, Object> map = allList.get(i);
            Record1 record=new Record1();
            int j=0;
            String str="";
            Long num=0L;
            for(Map.Entry<String,Object> item:map.entrySet()){

                   if(j==0){
                      str= (String) item.getValue();
                      j++;
                   }else{
                        num= (Long) item.getValue();
                   }

            }
            record.setType(str);
            record.setNumber(num);

            list.add(record);
        }
        System.out.println(list);
        return list;
    }
}
