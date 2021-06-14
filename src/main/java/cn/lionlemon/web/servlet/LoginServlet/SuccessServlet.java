package cn.lionlemon.web.servlet.LoginServlet;

import cn.lionlemon.domain.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
@WebServlet("/successServlet")
public class SuccessServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        User user = (User) req.getAttribute("user");


        if (user!=null) {
            resp.setContentType("text/html;charset=utf-8");
            resp.getWriter().println("<h1>欢迎小猪猪</h1><h2>登录成功！宁的用户名是"+user.getUsername()+"</h2>");
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
