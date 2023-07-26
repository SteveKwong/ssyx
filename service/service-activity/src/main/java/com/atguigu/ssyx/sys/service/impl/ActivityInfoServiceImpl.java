package com.atguigu.ssyx.sys.service.impl;

import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.activity.ActivityRule;
import com.atguigu.ssyx.model.activity.ActivitySku;
import com.atguigu.ssyx.sys.mapper.ActivityInfoMapper;
import com.atguigu.ssyx.sys.mapper.ActivityRuleMapper;
import com.atguigu.ssyx.sys.mapper.ActivitySkuMapper;
import com.atguigu.ssyx.sys.remoteinvo.pojo.ProductFeign;
import com.atguigu.ssyx.sys.service.ActivityInfoService;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 活动表 服务实现类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-21
 */
@Service
public class ActivityInfoServiceImpl extends ServiceImpl<ActivityInfoMapper, ActivityInfo> implements ActivityInfoService {
    @Autowired
    private ActivityRuleMapper activityRuleMapper;
    @Autowired
    private ActivitySkuMapper activitySkuMapper;
    @Autowired
    private ProductFeign productFeign;

    /**
     * 获取活动列表
     *
     * @param page         page对象
     * @param activityInfo 参数传递
     * @return 活动列表
     */
    @Override
    public IPage<ActivityInfo> getActivities(Page<ActivityInfo> page, ActivityInfo activityInfo) {

        LambdaQueryWrapper<ActivityInfo> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(activityInfo.getActivityName())
                , ActivityInfo::getActivityName, activityInfo.getActivityName());
        wrapper.ge(activityInfo.getStartTime() != null && activityInfo.getEndTime() == null,
                ActivityInfo::getStartTime, activityInfo.getStartTime());
        wrapper.le(activityInfo.getEndTime() != null && activityInfo.getStartTime() == null,
                ActivityInfo::getStartTime, activityInfo.getEndTime());
        if (activityInfo.getEndTime() != null && activityInfo.getStartTime() != null) {
            wrapper.ge(ActivityInfo::getStartTime, activityInfo.getStartTime())
                    .le(ActivityInfo::getEndTime, activityInfo.getEndTime());
        }
        IPage<ActivityInfo> activityInfoPage = baseMapper.selectPage(page, wrapper);
        return activityInfoPage;
    }

    /**
     * 获取活动的规则信息和绑定的商品的信息
     *
     * @param activityId 活动的id
     * @return 查询结果
     */
    @Override
    public HashMap<String, Object> getActivityRuleAndProducts(Long activityId) {
        // 先进行规则表的查询 activity_rule表
        LambdaQueryWrapper<ActivityRule> ruleWrapper = new LambdaQueryWrapper<>();
        ruleWrapper.eq(ActivityRule::getActivityId, activityId);
        List<ActivityRule> activityRules = activityRuleMapper.selectList(ruleWrapper);
        // 通过活动id查询 sku_id的列表
        LambdaQueryWrapper<ActivitySku> activityWrapper = new LambdaQueryWrapper<>();
        activityWrapper.eq(ActivitySku::getActivityId, activityId);
        List<ActivitySku> activitySkus = activitySkuMapper.selectList(activityWrapper);
        // sku_id列表远程查询商品信息集合
        List<Long> skuIds = activitySkus.stream().map(ActivitySku::getSkuId).collect(Collectors.toList());
        Result<Object> skuInfoByActivityId = productFeign.getSkuInfoByActivityId(skuIds);
        HashMap<String, Object> resultMap = new HashMap<>(2);
        // 规则集合; sku商品的集合;商品的列表
        resultMap.put("activityRules", activityRules);
        resultMap.put("skuInfoByActivityId", skuInfoByActivityId);
        return resultMap;
    }

    /**
     * 给活动添加规则信息
     *
     * @param activityRuleVo 规则信息
     */
    @Override
    public void saveRulesToActivity(ActivityRuleVo activityRuleVo) {
        // 如果存在则删除重新添加

        // 不存在则插入

    }
}
