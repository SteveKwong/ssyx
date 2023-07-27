package com.atguigu.ssyx.home.service;

import java.util.Map;

/**
 * @author kuanggong
 * @date 2023/7/27 09:28
 * @project_name atguigu-ssyx-parent
 */
public interface HomeService {
    /**
     * 展示主页数据
     * @return 查询到的结果以map的形式进行封装
     */
    Map<Object, Object> showHome();
}
