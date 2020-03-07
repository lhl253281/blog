package com.lhl.blog.controller.admin;


import com.lhl.blog.pojo.Blog;
import com.lhl.blog.pojo.User;
import com.lhl.blog.service.BlogService;
import com.lhl.blog.service.TagService;
import com.lhl.blog.service.TypeService;
import com.lhl.blog.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.Random;


@RequestMapping("/admin")
@Controller
public class BlogsController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;


    @GetMapping("/blogs")
    public String blogs(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC)
                                    Pageable pageable, BlogQuery blog, Model model){
        System.out.println("blogs==========");
        model.addAttribute("types",typeService.listType());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "admin/blogs";
    }


    @PostMapping("/blogs/search")
    public String search(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        System.out.println("blogs==========");

        model.addAttribute("page",blogService.listBlog(pageable,blog));

        return "/admin/blogs :: blogList";
    }

    //新增博文页面
    @GetMapping("/blogs/input")
    public String input(Model model){
        //如果是修改则回显数据
        Integer i = new Random().nextInt(1000);
        String s = i.toString();

        String http = "https://unsplash.it/800/450?image="+s;


        Blog blog = new Blog();
        blog.setCover(http);
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("blog",blog);
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "admin/blogs-input";

    }
    //修改博文回显页面
    @GetMapping("/blogs/{id}/input")
    public String editInput(@PathVariable Long id, Model model){
        //如果是修改则回显数据
        model.addAttribute("types",typeService.listType());
        model.addAttribute("tags",tagService.listTag());

        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "admin/blogs-input";
    }



    //提交博文
    @PostMapping("/blogs")
    public String post(Blog blog, RedirectAttributes attributes, HttpSession session) {


        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }

        if (b == null ) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(RedirectAttributes attributes,@PathVariable Long id){

        blogService.deleteBlog(id);
        attributes.addFlashAttribute("message","删除成功");


        return "redirect:/admin/blogs";
    }




}
