package com.atguigu.ssyx.vo.acl;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * @author ASUS
 */
@Data
@ApiModel(description = "用户添加操作")
public class AdminRoleVo implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 用户的id
     */
    @NotNull(message = "用户id不得为空")
    @ApiModelProperty(value = "用户id")
    private Long userid;
    /**
     * 用户新增的权限id
     */
    @ApiModelProperty(value = "权限id")
    private List<Long> roles;

}