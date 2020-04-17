package com.lei.service;

import com.lei.entity.EngineerInfo;
import com.lei.entity.Page;
import com.lei.entity.Project;
import com.lei.entity.Record1;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
public class ProjectService {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    /*获取该工程师执行的所有项目*/
    public List<Project> getAllProject(String phone_number) {
        String sql = "select * from project where phone_number=?";
        RowMapper<Project> rowMapper = new BeanPropertyRowMapper<>(Project.class);
        /*  return  jdbcTemplate.queryForObject(sql,rowMapper,phone_number);*/
        List<Project> list = jdbcTemplate.query(sql, new Object[]{phone_number}, rowMapper);
        return  list;
    }

    /*获取该工程师执行的所有项目的分页*//*
    public Page getPage(String phone_number){
        List<Project> list=getAllProject(phone_number);
        Page page=new Page();
        page.setPageSize(10);
        page.setTotalPage((int) Math.ceil(list.size()/page.getPageSize()));
        page.setList(list);
        page.setTotalRecord(list.size());
        return  page;
    }*/

    /*获取测试的任务的类型：苹果还是安卓*/
    public  List<Map<String,Object>>  getProjectType(String phone_number){


        String sql="select project_type ,count(project_type)  from project  where phone_number=? group by project_type";
        List<Map<String,Object>> list=jdbcTemplate.queryForList(sql, new Object[]{phone_number});
        System.out.println(list);
        return  list;
    }
}
