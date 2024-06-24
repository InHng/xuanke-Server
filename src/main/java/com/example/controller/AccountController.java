package com.example.controller;

import cn.hutool.core.util.ObjectUtil;
import com.example.common.Result;
import com.example.entity.Account;
import com.example.entity.AdminInfo;
import com.example.entity.StudentInfo;
import com.example.entity.TeacherInfo;
import com.example.service.AdminInfoService;
import com.example.service.StudentInfoService;
import com.example.service.TeacherInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 跟账号相关的借口
 */
@RestController
@RequestMapping
public class AccountController {

    @Resource
    private AdminInfoService adminInfoService;
    @Resource
    private TeacherInfoService teacherInfoService;
    @Resource
    private StudentInfoService studentInfoService;

    /**
     * 登陆功能
     * @param user 登陆用户信息
     * @param request request请求
     * @return Result
     */
    @PostMapping("/login")
    public Result login(@RequestBody Account user, HttpServletRequest request) {
        // 校验数据有没有填
        if (ObjectUtil.isEmpty(user.getName()) || ObjectUtil.isEmpty(user.getPassword())
                || ObjectUtil.isEmpty(user.getLevel())) {
            return Result.error("-1", "请完善输入信息");
        }
        Integer level = user.getLevel();
        Account loginUser = new Account();
        if (level == 1) {
            // 管理员登陆
            loginUser = adminInfoService.login(user.getName(), user.getPassword());
        } else if (level == 2) {
            // 教师登陆
            loginUser = teacherInfoService.login(user.getName(), user.getPassword());
        } else if (level == 3) {
            // 学生登陆
            loginUser = studentInfoService.login(user.getName(), user.getPassword());
        }
        // 在 session 里把用户信息存一份
        request.getSession().setAttribute("user", loginUser);
        return Result.success(loginUser);
    }

    @PostMapping("/register")
    public Result register(@RequestBody Account user, HttpServletRequest request) {
        // 校验数据有没有填
        if (ObjectUtil.isEmpty(user.getName()) || ObjectUtil.isEmpty(user.getPassword())
                || ObjectUtil.isEmpty(user.getLevel())) {
            return Result.error("-1", "请完善输入信息");
        }
        Integer level = user.getLevel();
        if (level == 2) {
            TeacherInfo teacherInfo = new TeacherInfo();
            BeanUtils.copyProperties(user, teacherInfo);
            // 教师注册
            teacherInfoService.add(teacherInfo);
        } else if (level == 3) {
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(user, studentInfo);
            // 学生注册
            studentInfoService.add(studentInfo);
        }
        return Result.success();
    }

    /**
     * 获取当前登陆用户
     * @param request
     * @return
     */
    @GetMapping("/getUser")
    public Result getUser(HttpServletRequest request) {
        // 先从 session 里面获取当前存的登陆用户信息
        Account user = (Account) request.getSession().getAttribute("user");
        // 判断当前登陆的用户是什么角色
        Integer level = user.getLevel();
        if (level == 1) {
            // 获取管理员信息
            AdminInfo adminInfo = adminInfoService.findById(user.getId());
            return Result.success(adminInfo);
        } else if (level == 2) {
            // 获取教师信息
            TeacherInfo teacherInfo = teacherInfoService.findById(user.getId());
            return Result.success(teacherInfo);
        } else if (level == 3) {
            // 获取学生信息
            StudentInfo studentInfo = studentInfoService.findById(user.getId());
            return Result.success(studentInfo);
        }
        return Result.success(new Account());
    }

    @PostMapping("/updatePassword")
    public Result updatePassword(@RequestBody Account account, HttpServletRequest request) {
        Account user = (Account) request.getSession().getAttribute("user");
        String oldPassword = account.getPassword();
        if (!user.getPassword().equals(oldPassword)) {
            return Result.error("-1", "原密码输入错误");
        }
        String newPassword = account.getNewPassword();
        Integer level = user.getLevel();
        if (level == 1) {
            AdminInfo adminInfo = new AdminInfo();
            BeanUtils.copyProperties(user, adminInfo);
            adminInfo.setPassword(newPassword);
            adminInfoService.update(adminInfo);
        } else if (level == 2) {
            TeacherInfo teacherInfo = new TeacherInfo();
            BeanUtils.copyProperties(user, teacherInfo);
            teacherInfo.setPassword(newPassword);
            teacherInfoService.update(teacherInfo);
        } else if (level == 3) {
            StudentInfo studentInfo = new StudentInfo();
            BeanUtils.copyProperties(user, studentInfo);
            studentInfo.setPassword(newPassword);
            studentInfoService.update(studentInfo);
        }
        request.getSession().setAttribute("user", null);
        return Result.success();
    }

    @GetMapping("/logout")
    public Result logout(HttpServletRequest request) {
        request.getSession().setAttribute("user", null);
        return Result.success();
    }
}
