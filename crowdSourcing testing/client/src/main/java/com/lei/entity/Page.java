package com.lei.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*分页*/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Page {
    /*总记录数*/
    public int totalRecord;
    //每一页显示的数据条数
    public int pageSize;
    //页码数
    public int pageIndex;
    //总页数
    public int totalPage;
    /*所有的数据*/
    public List<Project> list;
}
