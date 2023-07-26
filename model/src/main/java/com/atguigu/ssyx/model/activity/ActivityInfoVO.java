package com.atguigu.ssyx.model.activity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

/**
 * @author kuanggong
 * @date 2023/7/24 10:19
 * @project_name atguigu-ssyx-parent
 */
@Data
public class ActivityInfoVO {

    @ApiModelProperty(value = "活动名称")
    private String activityName;

    @ApiModelProperty(value = "开始时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date startTime;

    @ApiModelProperty(value = "结束时间")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date endTime;


}
