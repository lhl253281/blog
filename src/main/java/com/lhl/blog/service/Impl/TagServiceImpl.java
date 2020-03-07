package com.lhl.blog.service.Impl;

import com.lhl.blog.dao.TagRepository;
import com.lhl.blog.exception.NotFoundException;
import com.lhl.blog.pojo.Tag;
import com.lhl.blog.pojo.Type;
import com.lhl.blog.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    private TagRepository tagRepository;

    @Override
    @Transactional
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    @Transactional
    public Tag getTag(Long id) {


        return tagRepository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public Page<Tag> listTag(Pageable pageable) {

        return tagRepository.findAll(pageable);
    }

    @Override
    @Transactional
    public Tag updateTag(Long id, Tag Tag) {
        Tag t = tagRepository.findById(id).orElse(null);
        if( t == null){
            throw new NotFoundException("该标签不存在");
        }
        BeanUtils.copyProperties(Tag,t);
        return tagRepository.save(t);


    }

    @Override
    @Transactional
    public Tag findByName(String name) {

        return tagRepository.findByName(name);
    }

    @Override
    @Transactional
    public void deleteTag(Long id) {
    tagRepository.deleteById(id);
    }

    @Override
    @Transactional
    public List<Tag> listTag() {

        return tagRepository.findAll();
    }

    @Override
    public List<Tag> listTag(String ids) {

        return tagRepository.findAllById(convertToList(ids));
    }

    @Override
    public List<Tag> listTagTop(Integer size) {

        Sort sort = Sort.by(Sort.Direction.DESC, "blogs.size");

        Pageable pageable = PageRequest.of(0, size,sort);

        return tagRepository.findTop(pageable);
    }

    private List<Long> convertToList(String ids) {
        List<Long> list = new ArrayList<>();
        if (!"".equals(ids) && ids != null) {
            String[] idarray = ids.split(",");
            for (int i=0; i < idarray.length;i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }


}
