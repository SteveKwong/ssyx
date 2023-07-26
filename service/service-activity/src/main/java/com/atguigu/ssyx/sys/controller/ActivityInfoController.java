package com.atguigu.ssyx.sys.controller;


import com.atguigu.ssyx.common.result.Result;
import com.atguigu.ssyx.model.activity.ActivityInfo;
import com.atguigu.ssyx.model.activity.ActivityInfoVO;
import com.atguigu.ssyx.sys.service.ActivityInfoService;
import com.atguigu.ssyx.vo.activity.ActivityRuleVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;

/**
 * <p>
 * 活动表 前端控制器
 * </p>
 *
 * @author qkuang
 * @since 2023-07-21
 */
@RestController
@RequestMapping("/sys/activity-info")
@CrossOrigin
public class ActivityInfoController {
    /**
     * service
     */
    @Autowired
    private ActivityInfoService activityInfoService;

    /**
     * 获取活动列表
     *
     * @return 属性列表
     */
    @ApiOperation("获取活动列表")
    @GetMapping("{page}/{limit}")
    public Result<IPage<ActivityInfo>> getActivities(@PathVariable("page") Long start,
                                                     @PathVariable("limit") Long limit,
                                                     @Valid ActivityInfoVO activityInfo
    ) {
        Page<ActivityInfo> page = new Page<>(start, limit);
        ActivityInfo activityInfo1 = new ActivityInfo();
        BeanUtils.copyProperties(activityInfo, activityInfo1);
        IPage<ActivityInfo> skuInfos = activityInfoService.getActivities(page, activityInfo1);
        return Result.ok(skuInfos);
    }

    /**
     * 获取活动规则以及规则下的商品列表
     * 该接口的功能为:通过活动的id 获取活动的详细信息，满减信息，以及参与该满减信息的商品的集合信息
     *
     * @return 属性列表
     */
    @ApiOperation("获取活动规则以及规则下的商品列表")
    @GetMapping("getactivityruleandproducts/{id}")
    public Result<HashMap<String, Object>> getActivityRuleAndProducts(@PathVariable("id") Long activityId) {
        HashMap<String, Object> searchResult = activityInfoService.getActivityRuleAndProducts(activityId);
        return Result.ok(searchResult);
    }

    /**
     * 活动里面添加规则数据
     * 就是说,一个活动里面,可以有多个满减信息
     *
     * @return 属性列表
     */
    @ApiOperation("活动里面添加规则数据")
    @GetMapping("save/rulestoactivity")
    public Result<String> saveRulesToActivity(@RequestBody ActivityRuleVo activityRuleVo) {
        activityInfoService.saveRulesToActivity(activityRuleVo);
        return Result.ok("");
    }
}

