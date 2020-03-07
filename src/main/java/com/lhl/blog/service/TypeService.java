package com.lhl.blog.service;

import com.lhl.blog.pojo.Type;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TypeService {

    //新增
    Type saveType(Type type);
    //通过id查询
    Type getType(Long id);
    //分页查询
    Page<Type> listType(Pageable pageable);
    //更新
    Type updateType(Long id,Type type);
    //根据名字查找
    Type findByName(String name);
    //删除
    void deleteType(Long id);
    //查询所有
    List<Type>  listType();

    List<Type> listTypeTop(Integer size);


}
