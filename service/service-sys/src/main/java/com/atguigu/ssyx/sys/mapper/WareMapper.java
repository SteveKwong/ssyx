package com.atguigu.ssyx.sys.mapper;

import com.atguigu.ssyx.model.sys.Ware;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * 仓库表 Mapper 接口
 * </p>
 *
 * @author qkuang
 * @since 2023-07-11
 */
@Mapper
public interface WareMapper extends BaseMapper<Ware> {

    /**
     * 通过地区code获取当地的仓库列表
     *
     * @param regionCode 地区id
     * @return 仓库列表
     */
    @Select("SELECT  ware.name\n" +
            "FROM region\n" +
            "JOIN region_ware ON region.id = region_ware.region_id\n" +
            "JOIN ware ON  ware.id= region_ware.ware_id\n" +
            "WHERE region.id = #{id}")
    List<Ware> selectWareListByRegionCode(@Param("id") String regionCode);
}
