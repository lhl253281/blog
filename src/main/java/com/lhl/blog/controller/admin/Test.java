package com.lhl.blog.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
public class Test {

    @GetMapping("/test")
    public String test(HttpSession session){
        String name = "liuhonglei";
//        model.addAttribute("name",name);
        session.setAttribute("name",name);
        return "admin/test";
    }


    public static void main(String[] args) {

        Integer i = new Random().nextInt(1000);
        String s = i.toString();

        String http = "https://unsplash.it/800/450?image="+s;

        System.out.println(http);


    }


}
