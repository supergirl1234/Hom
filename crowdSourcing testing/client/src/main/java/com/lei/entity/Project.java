package com.lei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/*工程师所做的项目*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Project {
    public String phone_number;//测试该项目的工程师
    public  Integer project_id;//该测试项目的编号id
    public String project_name;//该测试项目的名字
    public String project_type;//该测试项目的类型
    public Date start;//执行该项目的开始时间
    public Date end; //完成该项目的结束时间

}
