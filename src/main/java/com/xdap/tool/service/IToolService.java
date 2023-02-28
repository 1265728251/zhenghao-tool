package com.xdap.tool.service;

import com.alibaba.fastjson.JSONObject;

public interface IToolService {

    /*计算两个时间范围的交集，返回天数*/
    JSONObject getDay(JSONObject jsonObject);

    /*计算两个时间范围的交集，返回天数*/
     JSONObject  monthGetDay(JSONObject jsonObject);

     /*个人证书分类*/
    JSONObject  ICheck(JSONObject jsonObject);
}
