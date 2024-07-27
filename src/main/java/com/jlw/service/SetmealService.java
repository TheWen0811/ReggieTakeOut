package com.jlw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlw.dto.SetmealDto;
import com.jlw.pojo.Setmeal;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealService extends IService<Setmeal> {
    /** 
    * @Description: 新增套餐，同时需要保存套餐和菜品的关联关系
    * @Param: 
    * @return: 
    * @Author: jlw
    * @Date: 
    */
    public void saveWithDish(SetmealDto setmealDto);
    /**
     * @Description: 删除套餐，同时需要删除套餐和菜品的关联关系
     * @Param:
     * @return:
     * @Author: jlw
     * @Date:
     */
    public void removeWithDish( List<Long> ids);
}
