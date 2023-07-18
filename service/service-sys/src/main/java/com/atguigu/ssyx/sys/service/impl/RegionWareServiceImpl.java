package com.atguigu.ssyx.sys.service.impl;

import com.atguigu.ssyx.model.sys.RegionWare;
import com.atguigu.ssyx.sys.mapper.RegionWareMapper;
import com.atguigu.ssyx.sys.service.IRegionWareService;
import com.atguigu.ssyx.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-11
 */
@Service
@SuppressWarnings({"unchecked,rawtypes"})
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements IRegionWareService {

    /**
     * 查询对象
     *
     * @param pageParam         查询参数
     * @param regionWareQueryVo 返回对象结果
     * @return 查询page结果
     */
    @Override
    public IPage<RegionWare> queryAllRegionWare(Page<RegionWare> pageParam, RegionWareQueryVo regionWareQueryVo) {
        // 关键字查询
        String keyword = regionWareQueryVo.getKeyword();
        LambdaQueryWrapper<RegionWare> wrapper = null;
        if (StringUtils.isNotEmpty(keyword)) {
            wrapper = new LambdaQueryWrapper<>();
            wrapper.like(RegionWare::getRegionName, keyword)
                    .or()
                    .like(RegionWare::getWareName, keyword);
        }
        return baseMapper.selectPage(pageParam, wrapper);
    }
}
