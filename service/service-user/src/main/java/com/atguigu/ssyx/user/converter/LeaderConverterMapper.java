package com.atguigu.ssyx.user.converter;

import com.atguigu.ssyx.enums.user.Leader;
import com.atguigu.ssyx.vo.user.LeaderAddressVo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author kuanggong
 * @date 2023/7/19 13:50
 * @project_name atguigu-ssyx-parent
 */
@Mapper
public interface LeaderConverterMapper {


    /**
     * 使用工厂方法获取Mapper实例
     */
    LeaderConverterMapper INSTANCE = Mappers.getMapper(LeaderConverterMapper.class);

    /**
     * 获取leader模型
     *
     * @param ob
     * @return
     */
    @Mappings({
            @Mapping(target = "skuId", source = "id"),
            @Mapping(target = "skuName", source = "skuName"),
            @Mapping(target = "price", source = "price"),
            @Mapping(target = "marketPrice", source = "marketPrice"),
            @Mapping(target = "stock", source = "stock"),
            @Mapping(target = "sales", source = "sale"),
    })
    LeaderAddressVo converterLeaderToLeaderVO(Leader leader);
}
