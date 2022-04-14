package cn.lionlemon.web.servlet;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.FileInputStream;
import java.io.IOException;


@WebServlet("/downloadServlet")
public class DownloadServlet  extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String filename = req.getParameter("filename");
        ServletContext servletContext = this.getServletContext();
        String realPath = servletContext.getRealPath("/img/h.jpg");
        FileInputStream fileInputStream = new FileInputStream(realPath);

        ServletOutputStream outputStream = resp.getOutputStream();
        byte[] buff =new byte[1024*8];
        int len =fileInputStream.read(buff);
        String mimeType = servletContext.getMimeType(filename);
        resp.setHeader("content-type",mimeType);
        resp.setHeader("content-disposition","attachment;filename="+filename);
        while (len!= -1){
            outputStream.write(buff,0,len);
        }
        fileInputStream.close();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}