package com.atguigu.ssyx.product.config;

import com.atguigu.ssyx.common.exception.SsyxException;
import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.common.result.ResultCodeEnum;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author kuanggong
 */
@ControllerAdvice(basePackages = {"com.atguigu.ssyx"})
@Order(9999)
public class GlobalExceptionHandler {
    //自定义异常处理
    @ExceptionHandler(SsyxException.class)
    @ResponseBody
    public Result error(SsyxException exception) {
        return Result.build(null, exception.getCode(), exception.getMessage());
    }

    @ExceptionHandler(Exception.class) //异常处理器
    @ResponseBody  //返回json数据
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.build(null, ResultCodeEnum.FAIL.getCode(), e.getMessage());
    }


}
