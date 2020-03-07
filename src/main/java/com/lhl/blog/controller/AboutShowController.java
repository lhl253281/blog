package com.lhl.blog.controller;


import com.lhl.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutShowController {
    @Autowired
    BlogService blogService;

    @GetMapping("/about")
    public  String about(Model model){


        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "about";
    }
}
