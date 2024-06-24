package com.example.controller;

import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.ClassInfo;
import com.example.entity.StudentInfo;
import com.example.entity.XuankeInfo;
import com.example.exception.CustomException;
import com.example.service.ClassInfoService;
import com.example.service.StudentInfoService;
import com.example.service.XuankeInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.BeanUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/classInfo")
public class ClassInfoController {
    @Resource
    private ClassInfoService classInfoService;
    @Resource
    private XuankeInfoService xuankeInfoService;
    @Resource
    private StudentInfoService studentInfoService;

    @PostMapping
    public Result add(@RequestBody ClassInfo classInfo) {
        classInfoService.add(classInfo);
        return Result.success();
    }

    @PostMapping("/xuanke")
    public Result xuanke(@RequestBody ClassInfo classInfo, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        if (ObjectUtils.isEmpty(user)) {
            throw new CustomException("-1", "登陆已失效，请重新登陆");
        }
        XuankeInfo info = xuankeInfoService.find(classInfo.getName(), classInfo.getTeacherId(), user.getId());
        if (!ObjectUtils.isEmpty(info)) {
            throw new CustomException("-1", "您已经选过该门课");
        }
        XuankeInfo xuankeInfo = new XuankeInfo();
        BeanUtils.copyProperties(classInfo, xuankeInfo);
        xuankeInfo.setId(null);
        xuankeInfo.setStudentId(user.getId());
        xuankeInfoService.add(xuankeInfo);
        classInfo.setYixuan(classInfo.getYixuan() + 1);
        studentInfoService.xuanke(classInfo, xuankeInfo.getStudentId());
        classInfoService.update(classInfo);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody ClassInfo classInfo) {
        classInfoService.update(classInfo);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        classInfoService.delete(id);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<ClassInfo> list = classInfoService.findAll();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ClassInfo> pageInfo = classInfoService.findPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @GetMapping("/{search}")
    public Result findPageSearch(@PathVariable String search, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ClassInfo> pageInfo = classInfoService.findPageSearch(search, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
