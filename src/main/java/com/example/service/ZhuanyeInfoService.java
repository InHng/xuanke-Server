package com.example.service;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.dao.XueyuanInfoDao;
import com.example.dao.ZhuanyeInfoDao;
import com.example.entity.TeacherInfo;
import com.example.entity.XueyuanInfo;
import com.example.entity.ZhuanyeInfo;
import com.example.exception.CustomException;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class ZhuanyeInfoService {
    @Resource
    private ZhuanyeInfoDao zhuanyeInfoDao;
    @Resource
    private XueyuanInfoDao xueyuanInfoDao;

    public void add(ZhuanyeInfo zhuanyeInfo) {
        // 查询数据库中有没有同名专业
        TeacherInfo info = zhuanyeInfoDao.findByName(zhuanyeInfo.getName());
        if (ObjectUtil.isNotEmpty(info)) { // 存在同名，提示用户名已存在
            throw new CustomException("-1", "该专业已存在");
        }
        zhuanyeInfoDao.insertSelective(zhuanyeInfo);
    }


    public List<ZhuanyeInfo> findAll() {
        return zhuanyeInfoDao.selectAll();
    }

    public void deleteById(Long id) {
        zhuanyeInfoDao.deleteByPrimaryKey(id);
    }

    public PageInfo<ZhuanyeInfo> findPage(Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ZhuanyeInfo> list = zhuanyeInfoDao.selectAll();
        for (ZhuanyeInfo zhuanyeInfo : list) {
            XueyuanInfo xueyuanInfo = xueyuanInfoDao.selectByPrimaryKey(zhuanyeInfo.getXueyuanId());
            zhuanyeInfo.setXueyuanName(xueyuanInfo.getName());
        }
        return PageInfo.of(list);
    }

    public PageInfo<ZhuanyeInfo> findPageSearch(String search, Integer pageNum, Integer pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<ZhuanyeInfo> list = zhuanyeInfoDao.findBySearch(search);
        return PageInfo.of(list);
    }
}
