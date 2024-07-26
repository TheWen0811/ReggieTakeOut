package com.jlw.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.jlw.dto.DishDto;
import com.jlw.pojo.Dish;
import org.springframework.stereotype.Service;

@Service
public interface DishService extends IService<Dish> {

    public void saveWithFlavor(DishDto dishDto);

    public DishDto getByIdWithFlavor(Long id);

    void updateWithFlavor(DishDto dishDto);
}
