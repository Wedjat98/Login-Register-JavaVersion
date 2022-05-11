package cn.lionlemon.web.servlet;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private final int width=100,height=50;
    private int index,x1,x2,y1,y2;
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ServletOutputStream servletOutputStream = resp.getOutputStream();
//        resp.setContentType("text/html;charset=utf-8");
        BufferedImage image = new BufferedImage(width,height,BufferedImage.TYPE_3BYTE_BGR);
        Graphics graphics = image.getGraphics();
        graphics.setColor(Color.pink);
        graphics.fillRect(0,0,width,height);
        graphics.setColor(Color.blue);
        graphics.drawRect(0,0,width-1,height-1);

        String str = "abcdefghijkmnpqrstuvwxyzABCDEFGHGKLMNPRQTUVWXYZ23456789";

        Random ram = new Random();
        for (int i = 0; i < 4; i++) {
            index = ram.nextInt(str.length());
            char charities = str.charAt(index);
            graphics.drawString(charities+"",width/6*i+10,height/2);

        }
        graphics.setColor(Color.green);
        for (int i = 0; i < 10; i++) {
            x1 = ram.nextInt(width);
            x2 = ram.nextInt(width);
            y1 = ram.nextInt(height);
            y2 = ram.nextInt(height);
            graphics.drawLine(x1,y1,x2,y2);
        }

//        resp.sendRedirect("/download");

        ImageIO.write(image,"jpg",resp.getOutputStream());


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doPost(req,resp);
    }
}
