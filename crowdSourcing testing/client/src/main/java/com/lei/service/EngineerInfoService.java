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
public class EngineerInfoService {
    @Autowired
    public JdbcTemplate jdbcTemplate;

    /*信息审核时，向数据库中添加登录用户的信息*/
    public void addEngineerInfo(EngineerInfo engineerInfo) {
        String sql = "insert into engineerInfo values(?,?,?,?,?,?)";
        jdbcTemplate.update(sql, engineerInfo.getPhone_number(), engineerInfo.getRealName(), engineerInfo.getID(),
                engineerInfo.getEmail(), engineerInfo.getEnterprise(), engineerInfo.getEnterpriseEmail());

    }

    /*从数据库中查找工程师的审核的信息*/
    public List<EngineerInfo> getEngineerInfo(String phone_number) {

        String sql = "select * from engineerInfo where phone_number=?";
        RowMapper<EngineerInfo> rowMapper = new BeanPropertyRowMapper<>(EngineerInfo.class);
        /*  return  jdbcTemplate.queryForObject(sql,rowMapper,phone_number);*/
        List<EngineerInfo> list = jdbcTemplate.query(sql, new Object[]{phone_number}, rowMapper);

        return list;
    }

}
