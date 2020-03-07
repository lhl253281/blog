package com.lhl.blog.service.Impl;

import com.lhl.blog.dao.TypeRepository;
import com.lhl.blog.exception.NotFoundException;
import com.lhl.blog.pojo.Type;
import com.lhl.blog.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class TypeServiceImpl implements TypeService {




    @Autowired
    private TypeRepository typeRepository;

    @Override
    @Transactional
    public Type saveType(Type type) {
        return typeRepository.save(type);
    }

    @Override
    @Transactional
    public Type getType(Long id) {
        return typeRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Page<Type> listType(Pageable pageable) {
        return typeRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Type updateType(Long id, Type type) {
        Type t = typeRepository.findById(id).orElse(null);
        if( t == null){
            throw new NotFoundException("该类型不存在");
        }
        BeanUtils.copyProperties(type,t);
        return typeRepository.save(t);

    }

    @Override
    public Type findByName(String name) {
        return typeRepository.findByName(name);
    }


    @Override
    @Transactional
    public void deleteType(Long id) {

        typeRepository.deleteById(id);

    }

    @Override
    public List<Type> listType() {
        return typeRepository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC,"blogs.size");


        Pageable pageable = PageRequest.of(0,size,sort);

        return typeRepository.findTop(pageable);
    }
}
