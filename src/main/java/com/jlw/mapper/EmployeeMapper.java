package com.jlw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlw.pojo.Employee;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
