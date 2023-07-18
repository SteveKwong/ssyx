package com.atguigu.ssyx.product.mapper;

import com.atguigu.ssyx.model.base.MqRepeatRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * mq去重表 Mapper 接口
 * </p>
 *
 * @author qkuang
 * @since 2023-07-12
 */
@Mapper
public interface MqRepeatRecordMapper extends BaseMapper<MqRepeatRecord> {

}
