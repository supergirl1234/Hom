package com.lei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*被测试的项目的找bug信息*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectBug {
    public Integer project_id;//项目id
    public String bug_name;// "缺陷名称",
    public String phone_number;// "测试人员手机号",
    public Integer status;//  "提交状态",
    public String grade;// "缺陷等级",
    public boolean isCommit;// "是否采纳"
}
