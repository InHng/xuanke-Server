package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.ResultCode;
import com.example.dao.TeacherInfoDao;
import com.example.entity.Account;
import com.example.entity.AdminInfo;
import com.example.entity.TeacherInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TeacherInfoService {
    @Resource
    private TeacherInfoDao teacherInfoDao;

    public Account login(String name, String password) {
        // 通过 用户名 和 密码 去数据库里查一条数据
        AdminInfo teacherInfo = teacherInfoDao.findByNameAndPass(name, password);
        if (ObjectUtil.isEmpty(teacherInfo)) {
            throw new CustomException("-1", "用户名、密码或角色错误");
        }
        return teacherInfo;
    }

    public TeacherInfo findById(Long id) {
        return teacherInfoDao.selectByPrimaryKey(id);
    }

    public void update(TeacherInfo teacherInfo) {
        teacherInfoDao.updateByPrimaryKeySelective(teacherInfo);
    }

    public List<TeacherInfo> findAll() {
        return teacherInfoDao.selectAll();
    }

    public void add(TeacherInfo teacherInfo) {
        // 查询数据库中有没有同名教师
        TeacherInfo info = teacherInfoDao.findByName(teacherInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) { // 存在同名，提示用户名已存在
            throw new CustomException(ResultCode.USER_EXIST_ERROR);
        }
        if (ObjectUtil.isEmpty(teacherInfo.getPassword())) { // 如果没有密码则初始化默认密码123456
            teacherInfo.setPassword("123456");
        }
        teacherInfoDao.insertSelective(teacherInfo);
    }

    public void deleteById(Long id) {
        teacherInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<TeacherInfo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TeacherInfo> list = teacherInfoDao.selectAll();
        return PageInfo.of(list);
    }

    public PageInfo<TeacherInfo> findPageSearch(String search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<TeacherInfo> list = teacherInfoDao.findByLikeName(search);
        return PageInfo.of(list);
    }
}
