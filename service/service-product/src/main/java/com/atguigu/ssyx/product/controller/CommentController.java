package com.atguigu.ssyx.product.controller;


import com.atguigu.ssyx.product.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 商品评价 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@RestController
@RequestMapping("/sys/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{message}")
    public String sendMessage(@PathVariable("message") String message) {
        commentService.sendMessage(message);
        return "发送成功";
    }

}

