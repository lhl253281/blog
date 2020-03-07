package com.lhl.blog.service;

import com.lhl.blog.pojo.Blog;
import com.lhl.blog.vo.BlogQuery;
import org.jboss.logging.BasicLogger;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;
import java.util.Map;

public interface BlogService {

    Blog getBlog(Long id);
    Blog getBlogAndConvert(Long id);



    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    //根据id查询
    Page<Blog> listBlog(Pageable pageable,Long tagId);

    Page<Blog> listBlog(String query,Pageable pageable);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    List<Blog> listRecommendBlogTop(Integer size);

    //博客归档
    Map<String,List<Blog>> archiveBlog();

    //博客条数
    Long countBlog();
}
