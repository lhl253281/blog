package com.lhl.blog.controller.admin;


import com.lhl.blog.pojo.Type;
import com.lhl.blog.service.BlogService;
import com.lhl.blog.service.TypeService;
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
public class TypeController {
    @Autowired
    private TypeService typeService;

    @Autowired
    private BlogService blogService;


    //类型列表
    @GetMapping("types")
    public String types(@PageableDefault(size = 12,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable,
                        Model model){
        Page<Type> types = typeService.listType(pageable);
        model.addAttribute("page",types);
        model.addAttribute("newblogs",blogService.listRecommendBlogTop(3));
        return "admin/types";
    }

    //标签新增页面接口
    @GetMapping("types/input")
    public String input(Model model){

        model.addAttribute("type",new Type());

        return "admin/types-input";
    }
    //编辑并回显类型
    @GetMapping("types/{id}/input")
    public String editType(Model model,@PathVariable Long id){

        model.addAttribute("type",typeService.getType(id));

        return "admin/types-input";
    }

    //新增提交操作
    @PostMapping("/types")
    public String post(@Valid Type type, BindingResult result,RedirectAttributes attributes){
        Type type1 = typeService.findByName(type.getName());
        if(type1 != null){
            result.rejectValue("name","nameError","该分类已存在");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
      Type t = typeService.saveType(type);
      if(t == null){
        attributes.addFlashAttribute("message","新增操作失败！");
        }else {
        attributes.addFlashAttribute("message","新增操作成功！");
        }
      return "redirect:/admin/types";
    }
    //修改提交操作
    @PostMapping("/types/{id}")
    public String post(@Valid Type type, BindingResult result,@PathVariable Long id,RedirectAttributes attributes){
        Type type1 = typeService.findByName(type.getName());
        if(type1 != null){
            result.rejectValue("name","nameError","该分类已存在");
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type t = typeService.updateType(id,type);
        if(t == null){
            attributes.addFlashAttribute("message","修改操作失败！");
        }else {
            attributes.addFlashAttribute("message","修改操作成功！");
        }
        return "redirect:/admin/types";
    }

    @GetMapping("types/{id}/delete")
    public String delete(@PathVariable Long id,RedirectAttributes attributes){


        typeService.deleteType(id);

        attributes.addFlashAttribute("message","删除成功！");
        return "redirect:/admin/types";

    }

}
