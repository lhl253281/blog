package com.lhl.blog.controller;


import com.lhl.blog.exception.NotFoundException;
import com.lhl.blog.pojo.Blog;
import com.lhl.blog.pojo.Type;
import com.lhl.blog.pojo.User;
import com.lhl.blog.service.BlogService;
import com.lhl.blog.service.TagService;
import com.lhl.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model) {

        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("types",typeService.listTypeTop(6));
        model.addAttribute("tags",tagService.listTagTop(10));
        model.addAttribute("recommendBlogs",blogService.listRecommendBlogTop(8));
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "index";

    }

    @PostMapping("/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"},
            direction = Sort.Direction.DESC) Pageable pageable, Model model,@RequestParam String query){

        String querys = "%"+query+"%";


        model.addAttribute("page", blogService.listBlog(querys, pageable));
        model.addAttribute("query", query);
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));

        return "search";
    }

    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){




        model.addAttribute("blog",blogService.getBlogAndConvert(id));
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        
        return "blog";
    }






}