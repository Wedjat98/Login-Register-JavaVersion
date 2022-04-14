package cn.lionlemon.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class UserController {
    @RequestMapping("/quick")
    public String save(){
        System.out.println("Controller save running............");
        return "success.html";
    }
}
