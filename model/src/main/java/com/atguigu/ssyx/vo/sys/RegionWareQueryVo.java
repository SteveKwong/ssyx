package com.atguigu.ssyx.vo.sys;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author kuanggong
 */
@Data
public class RegionWareQueryVo {
	
	@ApiModelProperty(value = "关键字")
	private String keyword;

}

