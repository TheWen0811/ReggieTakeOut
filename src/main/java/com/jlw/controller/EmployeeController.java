package com.jlw.controller;

import com.alibaba.druid.mock.MockBlob;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.jlw.pojo.Employee;
import com.jlw.service.EmployeeService;
import com.jlw.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

/**
 * @program: reggie_take_out
 * @description:员工登录
 * @author: lydms
 * @create: 2024-07-24 12:13
 **/
@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest httpServletRequest, @RequestBody Employee employee){
        //md5加密
        String password = employee.getPassword();
        password=DigestUtils.md5DigestAsHex(password.getBytes());
        //根据页面提交的用户名查询数据库
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername,employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);
        //没有查询结果返回登陆失败
        if(emp==null){
            return R.error("登陆失败");
        }
        if(!emp.getPassword().equals(password)){
            return R.error("登陆失败");
        }
        if(emp.getStatus()==0){
            return R.error("账号已禁用");
        }
        httpServletRequest.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }
    /**
    * @Description: 员工退出
    * @Param: 
    * @return: 
    * @Author: lydms
    * @Date: 
    */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的员工id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");
    }
}
