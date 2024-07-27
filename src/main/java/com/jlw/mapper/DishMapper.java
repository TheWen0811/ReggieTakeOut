package com.jlw.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.jlw.pojo.Dish;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface DishMapper extends BaseMapper<Dish> {
    void outOfStock(List<String> ids);
    void inStock(List<String> ids);
}

