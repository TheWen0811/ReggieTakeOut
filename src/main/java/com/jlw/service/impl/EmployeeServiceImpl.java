package com.jlw.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jlw.mapper.EmployeeMapper;
import com.jlw.pojo.Employee;
import com.jlw.service.EmployeeService;
import org.springframework.stereotype.Service;

/**
 * @program: reggie_take_out
 * @description:
 * @author: lydms
 * @create: 2024-07-24 12:11
 **/

@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
