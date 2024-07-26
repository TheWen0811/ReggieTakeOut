package com.jlw.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jlw.pojo.Category;
import com.jlw.service.CategoryService;
import com.jlw.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Lang;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @program: reggie_take_out
 * @description:分类管理
 * @author: jlw
 * @create: 2024-07-26 11:08
 **/

@RestController
@RequestMapping("/category")
@Slf4j

public class CategoryController {
    /**
     * @Description: 新增分类
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @Autowired
    private CategoryService categoryService;
    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("category{}",category);
        categoryService.save(category);
        return R.success("新增分类成功");
    }

    /**
     * @Description: 分页查询
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @GetMapping("/page")
    public R<Page> page(int page,int pageSize){
        log.info("page={},pageSize={}",page,pageSize);
        //分页构造器
        Page<Category> pageInfo=new Page<>(page,pageSize);
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        //添加排序条件根据sort进行排序
        queryWrapper.orderByDesc(Category::getSort);
        //进行分页查询
        categoryService.page(pageInfo,queryWrapper);
        return R.success(pageInfo);
    }
    /**
     * @Description: 删除分类
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @DeleteMapping
    public R<String> delete(Long ids)
    {    log.info("删除分类，id为：{}",ids);

        categoryService.remove(ids);
        return R.success("分类信息删除成功");
    }
    /**
     * @Description: 根据id修改分类信息
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    @PutMapping
    public R<String> update(@RequestBody Category category){
        log.info("修改分类信息：{}",category);

        categoryService.updateById(category);

        return R.success("修改分类信息成功");
    }
    /** 
    * @Description: 根据条件查询分类数据
    * @Param: 
    * @return: 
    * @Author: jlw
    * @Date: 
    */
    @GetMapping("/list")
    public R<List<Category>> list(Category category){
        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper=new LambdaQueryWrapper<>();
        //添加条件
        queryWrapper.eq(category.getType()!=null,Category::getType,category.getType());
        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);
        return R.success(list);
    }
}
