package com.example.controller;

import com.example.common.Result;
import com.example.entity.ClassInfo;
import com.example.entity.XuankeInfo;
import com.example.service.XuankeInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/xuankeInfo")
public class XuankeInfoController {
    @Resource
    private XuankeInfoService xuankeInfoService;

    @GetMapping
    public Result findAll() {
        List<XuankeInfo> list = xuankeInfoService.findAll();
        return Result.success(list);
    }

    @GetMapping("/page")
    public Result findPage(HttpServletRequest request, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<XuankeInfo> pageInfo = xuankeInfoService.findPage(request, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @GetMapping("/{search}")
    public Result findPageSearch(@PathVariable String search, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<XuankeInfo> pageInfo = xuankeInfoService.findPageSearch(search, pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        xuankeInfoService.delete(id);
        return Result.success();
    }
}
