package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.product.Comment;
import com.atguigu.ssyx.product.mapper.CommentMapper;
import com.atguigu.ssyx.product.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品评价 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Service
@EnableBinding(Source.class)
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private Source source;
    @Override
    public void sendMessage(String message) {
        source.output().send(MessageBuilder.withPayload(message).build());
    }
}
