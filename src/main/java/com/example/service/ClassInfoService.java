package com.example.service;

import com.example.dao.ClassInfoDao;
import com.example.dao.TeacherInfoDao;
import com.example.dao.ZhuanyeInfoDao;
import com.example.entity.ClassInfo;
import com.example.entity.TeacherInfo;
import com.example.entity.ZhuanyeInfo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ClassInfoService {
    @Resource
    private ClassInfoDao classInfoDao;
    @Resource
    private ZhuanyeInfoDao zhuanyeInfoDao;
    @Resource
    private TeacherInfoDao teacherInfoDao;

    public void add(ClassInfo classInfo) {
        classInfoDao.insertSelective(classInfo);
    }

    public List<ClassInfo> findAll() {
        return classInfoDao.selectAll();
    }

    public void update(ClassInfo classInfo) {
        classInfoDao.updateByPrimaryKeySelective(classInfo);
    }

    public void delete(Long id) {
        classInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<ClassInfo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ClassInfo> list = classInfoDao.selectAll();
        for (ClassInfo classInfo : list) {
            ZhuanyeInfo zhuanyeInfo = zhuanyeInfoDao.selectByPrimaryKey(classInfo.getZhuanyeId());
            classInfo.setZhuanyeName(zhuanyeInfo.getName());
            TeacherInfo teacherInfo = teacherInfoDao.selectByPrimaryKey(classInfo.getTeacherId());
            classInfo.setTeacherName(teacherInfo.getName());
        }
        return PageInfo.of(list);
    }

    public PageInfo<ClassInfo> findPageSearch(String search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ClassInfo> list = classInfoDao.findBySearch(search);
        return PageInfo.of(list);
    }
}
