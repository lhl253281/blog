package com.lhl.blog.controller.admin;


import com.lhl.blog.pojo.User;
import com.lhl.blog.service.BlogService;
import com.lhl.blog.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Random;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private BlogService blogService;

    @Autowired
    private UserService service;

    @GetMapping
    public String loginPage(){

        return "admin/login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes attributes, Model model)
    {

        Integer i = new Random().nextInt(1000);
        String s = i.toString();
        String http = "https://unsplash.it/1000/400?image="+s;


        System.out.println("============login");

        User user = service.checkUser(username,password);

        System.out.println(user);
        if(user != null){


            user.setPassword(null);
            session.setAttribute("user",user);

            model.addAttribute("picture",http);
            model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
            return "admin/index";
        }else{



            attributes.addFlashAttribute("msg","用户名或密码错误");
            return "redirect:/admin";
        }



    }

    @GetMapping("/logout")
    public String logout(HttpSession session){

        session.removeAttribute("user");

        return "redirect:/admin";
    }

}
