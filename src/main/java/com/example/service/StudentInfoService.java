package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.ResultCode;
import com.example.dao.StudentInfoDao;
import com.example.dao.XueyuanInfoDao;
import com.example.entity.Account;
import com.example.entity.ClassInfo;
import com.example.entity.StudentInfo;
import com.example.entity.XueyuanInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class StudentInfoService {
    @Resource
    private StudentInfoDao studentInfoDao;
    @Resource
    private XueyuanInfoDao xueyuanInfoDao;

    public Account login(String name, String password) {
        // 通过 用户名 和 密码 去数据库里查一条数据
        StudentInfo studentInfo = studentInfoDao.findByNameAndPass(name, password);
        if (ObjectUtil.isEmpty(studentInfo)) {
            throw new CustomException("-1", "用户名、密码或角色错误");
        }
        return studentInfo;
    }

    public StudentInfo findById(Long id) {
        return studentInfoDao.selectByPrimaryKey(id);
    }

    public void update(StudentInfo studentInfo) {
        studentInfoDao.updateByPrimaryKeySelective(studentInfo);
    }

    public List<StudentInfo> findAll() {
        return studentInfoDao.selectAll();
    }

    public void add(StudentInfo studentInfo) {
        StudentInfo info = studentInfoDao.findByName(studentInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) {
            // 数据库中有同名学生了
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(studentInfo.getPassword())) {
            studentInfo.setPassword("123456");
        }
        studentInfoDao.insertSelective(studentInfo);
    }

    public void deleteById(Long id) {
        studentInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<StudentInfo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentInfo> list = studentInfoDao.selectAll();
        for (StudentInfo studentInfo : list) {
            XueyuanInfo xueyuanInfo = xueyuanInfoDao.selectByPrimaryKey(studentInfo.getXueyuanId());
            studentInfo.setXueyuanName(xueyuanInfo.getName());
        }
        return PageInfo.of(list);
    }

    public PageInfo<StudentInfo> findPageSearch(String search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<StudentInfo> list = studentInfoDao.findByLikeName(search);
        return PageInfo.of(list);
    }

    public void xuanke(ClassInfo classInfo, Long studentId) {
        StudentInfo studentInfo = studentInfoDao.selectByPrimaryKey(studentId);
        studentInfo.setScore(studentInfo.getScore() + classInfo.getScore());
        studentInfoDao.updateByPrimaryKeySelective(studentInfo);
    }
}
