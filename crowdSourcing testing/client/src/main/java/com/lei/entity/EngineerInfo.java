package com.lei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*信息审核的表*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EngineerInfo {
    private  String phone_number;//工程师登录的手机号
    private String realName;//工程师真实姓名
    private String ID;//工程师身份证号
    private String email;//工程师个人邮箱
    private  String enterprise;//工程师所在企业
    private  String enterpriseEmail;//企业邮箱：name@企业域名
}
