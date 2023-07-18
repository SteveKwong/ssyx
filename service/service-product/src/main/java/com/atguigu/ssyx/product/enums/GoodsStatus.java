package com.atguigu.ssyx.product.enums;

/**
 * <p>
 * Project Name: recharge-portal
 * <br>
 * Description:
 * <br>
 * File Name: OrderFailRetCodeEnum
 * <br>
 * Copyright: Copyright (C) 2020 All Rights Reserved.
 * <br>
 *
 * @author: vegaxh
 * <p>
 * Date                 Author      Version     Description
 * ------------------------------------------------------------------
 * 2020/11/30 10:29     |vegaxh      |v1.0.1       |Create
 */
public enum GoodsStatus {

    GOODS_UP("1","商品上架"),
    GOODS_DOWN("0","商品下架"),
    NO_CHECK("0","未审核"),
    PASS_CHECK("1","审核通过"),
    NEW_PERSON("1","开启新人专享"),
    NOT_NEW_PERSON("0","不开启新人专享");



    private String code;
    private String msg;

    GoodsStatus(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

}
