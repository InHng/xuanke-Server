package com.example.service;

import com.example.dao.*;
import com.example.entity.*;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import javax.print.DocFlavor;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Service
public class XuankeInfoService {
    @Resource
    private XuankeInfoDao xuankeInfoDao;
    @Resource
    private ZhuanyeInfoDao zhuanyeInfoDao;
    @Resource
    private TeacherInfoDao teacherInfoDao;
    @Resource
    private StudentInfoDao studentInfoDao;
    @Resource
    private ClassInfoDao classInfoDao;

    public List<XuankeInfo> findAll() {
        return xuankeInfoDao.selectAll();
    }

    public void add(XuankeInfo xuankeInfo) {
        xuankeInfoDao.insertSelective(xuankeInfo);
    }

    public PageInfo<XuankeInfo> findPage(HttpServletRequest request, Integer pageNum, Integer pageSize) {
        Account user = (Account) request.getSession().getAttribute("user");
        PageHelper.startPage(pageNum, pageSize);
        List<XuankeInfo> list;
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException("-1", "登陆已失效，请重新登陆");
        }
        if (user.getLevel() == 1) {
            list = xuankeInfoDao.selectAll();
        } else if (user.getLevel() == 2) {
            list = xuankeInfoDao.findByCondition(user.getId(), null);
        } else {
            list = xuankeInfoDao.findByCondition(null, user.getId());
        }

        for (XuankeInfo xuankeInfo : list) {
            ZhuanyeInfo zhuanyeInfo = zhuanyeInfoDao.selectByPrimaryKey(xuankeInfo.getZhuanyeId());
            TeacherInfo teacherInfo = teacherInfoDao.selectByPrimaryKey(xuankeInfo.getTeacherId());
            StudentInfo studentInfo = studentInfoDao.selectByPrimaryKey(xuankeInfo.getStudentId());
            xuankeInfo.setZhuanyeName(zhuanyeInfo.getName());
            xuankeInfo.setTeacherName(teacherInfo.getName());
            xuankeInfo.setStudentName(studentInfo.getName());
        }
        return PageInfo.of(list);
    }

    public PageInfo<XuankeInfo> findPageSearch(String search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<XuankeInfo> list = xuankeInfoDao.findBySearch(search);
        return PageInfo.of(list);
    }

    public XuankeInfo find(String name, Long teacherId, Long studentId) {
        return xuankeInfoDao.find(name, teacherId, studentId);
    }

    public void delete(Long id) {
        XuankeInfo xuankeInfo = xuankeInfoDao.selectByPrimaryKey(id);
        ClassInfo classInfo = classInfoDao.findByNameAndTeacher(xuankeInfo.getName(), xuankeInfo.getTeacherId());
        StudentInfo studentInfo = studentInfoDao.selectByPrimaryKey(xuankeInfo.getStudentId());
        xuankeInfoDao.deleteByPrimaryKey(id);
        studentInfo.setScore(studentInfo.getScore() - xuankeInfo.getScore());
        classInfo.setYixuan(classInfo.getYixuan() - 1);
        classInfoDao.updateByPrimaryKeySelective(classInfo);
        studentInfoDao.updateByPrimaryKeySelective(studentInfo);
    }
}
