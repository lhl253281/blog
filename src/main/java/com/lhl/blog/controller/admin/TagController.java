package com.lhl.blog.controller.admin;


import com.lhl.blog.pojo.Tag;

import com.lhl.blog.service.BlogService;
import com.lhl.blog.service.TagService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    private TagService tagService;
    @Autowired
    private BlogService blogService;

    //类型列表
    @GetMapping("tags")
    public String tags(@PageableDefault(size = 12,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        Page<Tag> tags = tagService.listTag(pageable);
        model.addAttribute("page",tags);
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "admin/tags";
    }

    //标签新增页面接口
    @GetMapping("tags/input")
    public String input(Model model){

        model.addAttribute("tag",new Tag());

        return "admin/tags-input";
    }
    //编辑并回显类型
    @GetMapping("tags/{id}/input")
    public String edittag(Model model,@PathVariable Long id){

        model.addAttribute("tag",tagService.getTag(id));

        return "admin/tags-input";
    }

    //新增提交操作
    @PostMapping("/tags")
    public String post(@Valid Tag tag, BindingResult result,RedirectAttributes attributes){
        Tag tag1 = tagService.findByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","该分类已存在");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.saveTag(tag);
        if(t == null){
            attributes.addFlashAttribute("message","新增操作失败！");
        }else {
            attributes.addFlashAttribute("message","新增操作成功！");
        }
        return "redirect:/admin/tags";
    }
    //修改提交操作
    @PostMapping("/tags/{id}")
    public String post(@Valid Tag tag, BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        Tag tag1 = tagService.findByName(tag.getName());
        if(tag1 != null){
            result.rejectValue("name","nameError","该分类已存在");
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag t = tagService.updateTag(id,tag);
        if(t == null){
            attributes.addFlashAttribute("message","修改操作失败！");
        }else {
            attributes.addFlashAttribute("message","修改操作成功！");
        }
        return "redirect:/admin/tags";
    }

    @GetMapping("tags/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){


        tagService.deleteTag(id);

        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/tags";

    }

}
