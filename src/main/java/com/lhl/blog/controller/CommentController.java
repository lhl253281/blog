package com.lhl.blog.controller;


import com.lhl.blog.pojo.Comment;
import com.lhl.blog.pojo.User;
import com.lhl.blog.service.BlogService;
import com.lhl.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class CommentController {

    @Autowired
    private CommentService commentservice;
    @Autowired
    private BlogService blogService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("comments/{blogId}")
    public String comments(@PathVariable Long blogId, Model model){

        model.addAttribute("comments",commentservice.listCommentByBlogId(blogId));



        return "blog :: commentList";


    }

    @PostMapping("comments")
    public String post(Comment comment, HttpSession session){

        User user = (User) session.getAttribute("user");

        System.out.println("用户信息是："+user);

        if(user != null ){

            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());

        }else{

            comment.setAdminComment(false);

            comment.setAvatar(avatar);
        }


        Long blogId = comment.getBlog().getId();
        comment.setBlog(blogService.getBlog(blogId));

        commentservice.saveComment(comment);

        return "redirect:/comments/" + comment.getBlog().getId();
    }


}
