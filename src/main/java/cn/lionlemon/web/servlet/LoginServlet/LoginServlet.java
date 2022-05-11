package cn.lionlemon.web.servlet.LoginServlet;

import cn.lionlemon.dao.UserDao;
import cn.lionlemon.type.User;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//设置编码
        req.setCharacterEncoding("utf-8");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        User loginUser =new User();
        loginUser.setEmail(username);
        loginUser.setPassword(password);
        UserDao dao =new UserDao();
        User user = dao.login(loginUser);
        if (user == null){
            req.getRequestDispatcher("/failServlet").forward(req,resp);
        }else {
            req.setAttribute("user",user);
            req.getRequestDispatcher("/successServlet").forward(req,resp);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req,resp);
    }
}
