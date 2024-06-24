package com.example.controller;

import com.example.common.Result;
import com.example.entity.StudentInfo;
import com.example.entity.XueyuanInfo;
import com.example.service.XueyuanInfoService;
import com.github.pagehelper.PageInfo;
import org.apache.commons.collections4.Put;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/xueyuanInfo")
public class XueyuanInfoController {
    @Resource
    private XueyuanInfoService xueyuanInfoService;

    @GetMapping
    public Result findAll() {
        List<XueyuanInfo> list =  xueyuanInfoService.findAll();
        return Result.success(list);
    }

    @PostMapping
    public Result add(@RequestBody XueyuanInfo xueyuanInfo) {
        xueyuanInfoService.add(xueyuanInfo);
        return Result.success();
    }

    @PutMapping
    public Result update(@RequestBody XueyuanInfo xueyuanInfo) {
        xueyuanInfoService.update(xueyuanInfo);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        xueyuanInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<XueyuanInfo> pageInfo = xueyuanInfoService.findPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @GetMapping("/{search}")
    public Result findPageSearch(@PathVariable String search, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<XueyuanInfo> pageInfo = xueyuanInfoService.findPageSearch(search, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
