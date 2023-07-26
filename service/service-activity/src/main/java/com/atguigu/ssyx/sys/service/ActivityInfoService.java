package com.atguigu.ssyx.sys.service;

import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;

/**
 * <p>
 * 活动表 服务类
 * </p>
 *
 * @author qkuang
 * @since 2023-07-21
 */
public interface ActivityInfoService extends IService<ActivityInfo> {

    /**
     * 获取活动列表
     *
     * @param page         page对象
     * @param activityInfo 参数传递
     * @return 查询结果
     */
    IPage<ActivityInfo> getActivities(Page<ActivityInfo> page, ActivityInfo activityInfo);

    /**
     * 获取活动规则以及规则下的商品列表
     *
     * @param activityId 活动的id
     * @return 规则详情和参与规则的商品集合
     */
    HashMap<String, Object> getActivityRuleAndProducts(Long activityId);

    /**
     * 保存规则到活动当中
     *
     * @param activityRuleVo 规则信息
     */
    void saveRulesToActivity(ActivityRuleVo activityRuleVo);
}
