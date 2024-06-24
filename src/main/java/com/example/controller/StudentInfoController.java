package com.example.controller;

import com.example.common.Result;
import com.example.entity.StudentInfo;
import com.example.service.StudentInfoService;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/studentInfo")
public class StudentInfoController {
    @Resource
    private StudentInfoService studentInfoService;

    @PutMapping
    public Result update(@RequestBody StudentInfo studentInfo) {
        studentInfoService.update(studentInfo);
        return Result.success();
    }

    @GetMapping
    public Result findAll() {
        List<StudentInfo> list = studentInfoService.findAll();
        return Result.success(list);
    }

    @PostMapping
    public Result add(@RequestBody StudentInfo studentInfo) {
        studentInfoService.add(studentInfo);
        return Result.success();
    }

    @DeleteMapping("/{id}")
    public Result deleteById(@PathVariable Long id) {
        studentInfoService.deleteById(id);
        return Result.success();
    }

    @GetMapping("/page")
    public Result findPage(@RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<StudentInfo> pageInfo = studentInfoService.findPage(pageNum, pageSize);
        return Result.success(pageInfo);
    }

    @GetMapping("/{search}")
    public Result findPageSearch(@PathVariable String search, @RequestParam Integer pageNum, @RequestParam Integer pageSize) {
        PageInfo<StudentInfo> pageInfo = studentInfoService.findPageSearch(search, pageNum, pageSize);
        return Result.success(pageInfo);
    }
}
