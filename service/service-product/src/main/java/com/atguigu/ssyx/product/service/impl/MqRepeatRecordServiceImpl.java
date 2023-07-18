package com.atguigu.ssyx.product.service.impl;

import com.atguigu.ssyx.model.base.MqRepeatRecord;
import com.atguigu.ssyx.product.mapper.MqRepeatRecordMapper;
import com.atguigu.ssyx.product.service.MqRepeatRecordService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * mq去重表 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Service
public class MqRepeatRecordServiceImpl extends ServiceImpl<MqRepeatRecordMapper, MqRepeatRecord> implements MqRepeatRecordService {

}
