package com.lhl.blog.controller;


import com.lhl.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ArchiveShowController {


    @Autowired
    BlogService blogService;


    @GetMapping("/archives")
    public  String archives(Model model){


        model.addAttribute("archiveMap",blogService.archiveBlog());


        model.addAttribute("blogCount",blogService.countBlog());

        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));

        return "archives";
    }



}