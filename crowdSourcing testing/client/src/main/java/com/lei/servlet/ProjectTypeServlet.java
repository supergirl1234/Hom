package com.lei.servlet;

import com.google.gson.Gson;
import com.lei.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/projectTypeServlet")
public class ProjectTypeServlet extends HttpServlet {
    @Autowired
    public ProjectService projectService;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Map<String,Object>> list= projectService.getProjectType(request.getParameter("phone_number"));
        //将list中的对象转换为Json格式的数组
        Gson gson = new Gson();
        String json = gson.toJson(list);

       System.out.println(json);

        //将json数据返回给客户端
        response.setContentType("text/html; charset=utf-8");
        response.getWriter().write(json);
    }
}
