package com.jlw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlw.dto.DishDto;
import com.jlw.pojo.Category;
import com.jlw.pojo.Dish;
import com.jlw.service.CategoryService;
import com.jlw.service.DishFlavorService;
import com.jlw.service.DishService;
import com.jlw.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @program: reggie_take_out
 * @description: 菜品管理
 * @author: jlw
 * @create: 2024-07-26 15:40
 **/
@RestController
@RequestMapping("/dish")
@Slf4j
public class DishController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;

    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R<String> save(@RequestBody DishDto dishDto) {
        dishService.saveWithFlavor(dishDto);
        return R.success("新增菜品成功");
    }

    /**
     * @Description: 菜品信息分页查询
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize, String name) {

        //构造分页构造器对象
        Page<Dish> pageInfo = new Page<>(page, pageSize);
        Page<DishDto> dishDtoPage = new Page<>();

        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.like(name != null, Dish::getName, name);
        //添加排序条件
        queryWrapper.orderByDesc(Dish::getUpdateTime);

        //执行分页查询
        dishService.page(pageInfo, queryWrapper);

        //对象拷贝
        BeanUtils.copyProperties(pageInfo, dishDtoPage, "records");

        List<Dish> records = pageInfo.getRecords();

        List<DishDto> list = records.stream().map((item) -> {
            DishDto dishDto = new DishDto();

            BeanUtils.copyProperties(item, dishDto);

            Long categoryId = item.getCategoryId();//分类id
            //根据id查询分类对象
            Category category = categoryService.getById(categoryId);

            if (category != null) {
                String categoryName = category.getName();
                dishDto.setCategoryName(categoryName);
            }
            return dishDto;
        }).collect(Collectors.toList());

        dishDtoPage.setRecords(list);

        return R.success(dishDtoPage);
    }
    /** 
    * @Description: 根据id查询菜品信息和对应口味信息
    * @Param: 
    * @return: 
    * @Author: jlw
    * @Date: 
    */
    @GetMapping("/{id}")
    public R<DishDto> get(@PathVariable Long id){
        DishDto dishDto=dishService.getByIdWithFlavor(id);
        return R.success(dishDto);
    }

    /**
     * @Description: 修改菜品
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @PutMapping
    public R<String> update(@RequestBody DishDto dishDto) {

        dishService.updateWithFlavor(dishDto);

        return R.success("修改菜品成功");
    }
    /**
     * @Description: 根据条件查询对应菜品
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
        //条件构造器
        LambdaQueryWrapper<Dish> queryWrapper = new LambdaQueryWrapper<>();
        //添加过滤条件
        queryWrapper.eq(dish.getCategoryId()!=null,Dish::getCategoryId,dish.getCategoryId());
        queryWrapper.eq(Dish::getStatus,1);
        //添加排序条件
        queryWrapper.orderByAsc(Dish::getSort).orderByDesc(Dish::getUpdateTime);

        List<Dish> list=dishService.list(queryWrapper);

        return R.success(list);
    }

}