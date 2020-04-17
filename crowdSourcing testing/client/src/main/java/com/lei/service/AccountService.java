package com.lei.service;

import com.lei.entity.Account;
import com.lei.entity.EngineerInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;


@Component
public class AccountService {
    @Autowired
    public  JdbcTemplate jdbcTemplate;
    /*判断用户是否注册过*/
    public  boolean isRegister(String phone_number){
       // String sql="select * from engineer where phone_number=?";

        List<Map<String,Object>> list= jdbcTemplate.queryForList("select * from account where phone_number=?", new Object[]{phone_number});

        /*如果没有查询到，就是没有注册过*/
        if(list.size()==0){
            return  true;
        }
        /*否则就是注册过*/
        return  false;
    }

    /*登陆时，从数据库查询，该用户*/
    public   List<Map<String,Object>>  isLogin(String phone_number){
       // String sql="select password from engineer where phone_number=?";
        List<Map<String,Object>> list=jdbcTemplate.queryForList("select * from account where phone_number=?",new Object[]{phone_number});
        /*如果没有查询到，就说明还未注册*/
        return  list;
    }
    /*注册时，添加用户*/
    public   boolean addEngineer(Account account){
        String sql="insert into account (username,phone_number,password) values(?,?,?)";
        int result=jdbcTemplate.update(sql,account.getUsername(),account.getPhone_number(),account.getPassword());
        if(result!=0){
            return true;
        }
        return  false;
    }

    /*从数据库中获取某个手机号用户的昵称*/
    public Account getAccountUserName(String phone_number){

        String sql="select * from account where phone_number=?";
        RowMapper<Account> rowMapper=new BeanPropertyRowMapper<>(Account.class);
        Account account= jdbcTemplate.queryForObject(sql,rowMapper,phone_number);

        return  account;

    }
}
