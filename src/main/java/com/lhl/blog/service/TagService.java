package com.lhl.blog.service;

import com.lhl.blog.pojo.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface TagService {

    //新增
    Tag saveTag(Tag tag);
    //通过id查询
    Tag getTag(Long id);
    //分页查询
    Page<Tag> listTag(Pageable pageable);
    //更新
    Tag updateTag(Long id,Tag tag);
    //根据名字查找
    Tag findByName(String name);
    //删除
    void deleteTag(Long id);
    //查询所有
    List<Tag> listTag();
    //根据一组id查询
    List<Tag> listTag(String ids);

    List<Tag> listTagTop(Integer size);

}
