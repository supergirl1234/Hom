package com.lei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*账号表*/
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Account {
    public  String username;//昵称
    public  String phone_number;//手机号
    public  String password;//密码
}
