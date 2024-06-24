package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.ResultCode;
import com.example.dao.XueyuanInfoDao;
import com.example.entity.StudentInfo;
import com.example.entity.TeacherInfo;
import com.example.entity.XueyuanInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class XueyuanInfoService {
    @Resource
    private XueyuanInfoDao xueyuanInfoDao;

    public void add(XueyuanInfo xueyuanInfo) {
        // 查询数据库中有没有同名学院
        TeacherInfo info = xueyuanInfoDao.findByName(xueyuanInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) { // 存在同名，提示用户名已存在
            throw new CustomException("-1", "该学院已存在");
        }
        xueyuanInfoDao.insertSelective(xueyuanInfo);
    }

    public List<XueyuanInfo> findAll() {
        return xueyuanInfoDao.selectAll();
    }

    public void update(XueyuanInfo xueyuanInfo) {
        xueyuanInfoDao.updateByPrimaryKeySelective(xueyuanInfo);
    }

    public void deleteById(Long id) {
        xueyuanInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<XueyuanInfo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<XueyuanInfo> list = xueyuanInfoDao.selectAll();
        return PageInfo.of(list);
    }

    public PageInfo<XueyuanInfo> findPageSearch(String search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<XueyuanInfo> list = xueyuanInfoDao.findByLikeName(search);
        return PageInfo.of(list);
    }
}
