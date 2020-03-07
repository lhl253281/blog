package com.lhl.blog.controller;

import com.lhl.blog.exception.NotFoundException;
import com.lhl.blog.pojo.Tag;
import com.lhl.blog.service.BlogService;
import com.lhl.blog.service.TagService;
import com.lhl.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
@Controller
public class TagShowController {

    @Autowired
    private TagService TagService;

    @Autowired
    private BlogService blogService;

    @GetMapping("tags/{id}")
    public String tags(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                Pageable pageable, @PathVariable Long id, Model model){


        List<Tag> tags = TagService.listTagTop(10000);


        if(id == -1){
            if(tags == null){

                throw new NotFoundException("没有任何标签存在");
            }


            id = tags.get(0).getId();


        }

        BlogQuery blogQuery = new BlogQuery();

        model.addAttribute("tags",tags);
        model.addAttribute("page",blogService.listBlog(pageable,id));
        model.addAttribute("activeTagId",id);
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "tags";
    }



}
