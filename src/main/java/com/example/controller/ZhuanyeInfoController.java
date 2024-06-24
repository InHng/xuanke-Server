package com.example.controller;

import com.example.common.Result;
import com.example.entity.XueyuanInfo;
import com.example.entity.ZhuanyeInfo;
import com.example.service.ZhuanyeInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/zhuanyeInfo")
public class ZhuanyeInfoController {
    @Resource
    private ZhuanyeInfoService zhuanyeInfoService;

    @PostMapping
    public Result add(@RequestBody ZhuanyeInfo zhuanyeInfo) {
        zhuanyeInfoService.add(zhuanyeInfo);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<ZhuanyeInfo> list = zhuanyeInfoService.findAll();
        return Result.success(list);
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        zhuanyeInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ZhuanyeInfo> pageInfo = zhuanyeInfoService.findPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @GetMapping("/{search}")
    public Result findPageSearch(@PathVariable String search, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<ZhuanyeInfo> pageInfo = zhuanyeInfoService.findPageSearch(search, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
