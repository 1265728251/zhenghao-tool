package com.xdap.tool.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.xdap.tool.service.IToolService;
import com.xdap.tool.utils.TimeTool;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;

/**
 * @author:杨智
 * @create: 2023-01-30 11:50
 * @Description: 证书费用统计实现类
 */
@Slf4j
@Service
public class ToolServiceImpl implements IToolService {


    @Override
    public JSONObject getDay(JSONObject jsonObject) {
        int day =getIntersectionDays(jsonObject.toJSONString());
        JSONObject jsonObject1 = new JSONObject();
        jsonObject1.put("intersectionDay",day);
        jsonObject1.put("expenseId",jsonObject.get("expenseId"));
        jsonObject1.put("state","ok");
        return jsonObject1;
    }

    @Override
    public  JSONObject monthGetDay(JSONObject jsonObject) {
        /*首先把月份装换为xyz日期格式*/
        TimeTool timeTool =new TimeTool();

        String start =jsonObject.get("month").toString()+"-01";

        String end = timeTool.addMonth(start);

        JSONObject jsonObject1 =new JSONObject();
        jsonObject1.put("expenseStart",jsonObject.get("expenseStart"));
        jsonObject1.put("expenseEnt",jsonObject.get("expenseEnt"));
        jsonObject1.put("StartOfStatistics",start);
        jsonObject1.put("endOfStatistics",end);
        jsonObject1.put("expenseId",jsonObject.get("expenseId"));

        int day =getIntersectionDays(jsonObject1.toJSONString());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("intersectionDay",day);
        jsonObject2.put("expenseId",jsonObject.get("expenseId"));
        jsonObject2.put("state","ok");

        return jsonObject2;
    }



    public JSONObject ICheck(JSONObject jsonObject) {

        JSONObject jsonObject1 =new JSONObject();
        jsonObject1.put("expenseId",jsonObject.get("expenseId"));
        jsonObject1.put("CertificateNo",jsonObject.get("CertificateNo"));
        jsonObject1.put("OpenOrNot",jsonObject.get("OpenOrNot"));
        JSONObject StatisticalZL = new JSONObject();
        System.out.println(jsonObject1.get("OpenOrNot").equals("开"));
/*
        StatisticalZL = (JSONObject) JSON.parse(jsonObject.get("StatisticalZL").toString());

        JSONObject StatisticalFL = new JSONObject();
        StatisticalFL = (JSONObject) JSON.parse(jsonObject.get("StatisticalFL").toString());

        JSONObject StatisticalDJ = new JSONObject();
        StatisticalDJ = (JSONObject) JSON.parse(jsonObject.get("StatisticalDJ").toString()) ;*/

        if(jsonObject1.get("OpenOrNot").equals("开")){

            if (!jsonObject.get("StatisticalZL").equals(jsonObject.get("ZL"))||jsonObject.get("StatisticalFL")==null){
                jsonObject1.put("status","否");
                log.info("124{}",jsonObject.get("StatisticalFL")==null);
                return jsonObject1;
            }else if (!jsonObject.get("StatisticalFL").equals(jsonObject.get("FL"))||jsonObject.get("StatisticalDJ")==null){
                jsonObject1.put("status","否");
                log.info("sss");
                return jsonObject1;
            }else if (!jsonObject.get("StatisticalDJ").equals(jsonObject.get("DJ"))){
                log.info("3");
                jsonObject1.put("status","否");
                return jsonObject1;
            }

            jsonObject1.put("status","是");
            return jsonObject1;

        }else {
            jsonObject1.put("status","是");
            return jsonObject1;
        }


    }
    /*public static void main(String[] args) {

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("expenseStart","2021-10-12");
        jsonObject.put("expenseEnt","2021-12-12");
       *//* jsonObject.put("StartOfStatistics","2021-10-14");
        jsonObject.put("endOfStatistics","2023-12-23");*//*
        jsonObject.put("month","2021-10");
        jsonObject.put("expenseId","abc");

        *//*首先把月份装换为xyz日期格式*//*
        TimeTool timeTool =new TimeTool();

        String start =jsonObject.get("month").toString()+"-01";

        String end = timeTool.addMonth(start);

        JSONObject jsonObject1 =new JSONObject();
        jsonObject1.put("expenseStart",jsonObject.get("expenseStart"));
        jsonObject1.put("expenseEnt",jsonObject.get("expenseEnt"));
        jsonObject1.put("StartOfStatistics",start);
        jsonObject1.put("endOfStatistics",end);
        jsonObject1.put("expenseId",jsonObject.get("expenseId"));

        int day =getIntersectionDays(jsonObject1.toJSONString());
        JSONObject jsonObject2 = new JSONObject();
        jsonObject2.put("intersectionDay",day);
        jsonObject2.put("expenseId",jsonObject.get("expenseId"));
        jsonObject2.put("state","ok");

        System.out.println(jsonObject2);
    }*/
  /*  public static void main(String[] args) {
        String str = "{\"ZL\":\"执业证书\",\"StatisticalFL\":\"消防工程师\",\"StatisticalDJ\":\"二级消防工程师\",\"FL\":\"消防工程师\",\"DJ\":\"二级消防工程师\",\"expenseId\":\"F202302270124\",\"StatisticalZY\":\"无\",\"CertificateNo\":\"fsafsafds-9\",\"OpenOrNot\":\"开\",\"StatisticalZL\":\"执业书\",\"ZY\":\"计算机网络技术\"}";

        JSONObject jsonObject = IChec((JSONObject) JSON.parse(str));
        System.out.println(jsonObject);
    }
*/
    public static int getIntersectionDays(String inputJson) {
        TimeTool time =new TimeTool();
        // 解析输入JSON字符串
        JSONObject jsonObject = JSON.parseObject(inputJson);
        String expenseId = (String) jsonObject.get("expenseId");
        long expenseStart = time.parseTimestamp(jsonObject.get("expenseStart").toString());
        long expenseEnt = time.parseTimestamp(jsonObject.get("expenseEnt").toString());
        long StartOfStatistics = time.parseTimestamp(jsonObject.get("StartOfStatistics").toString());
        long endOfStatistics = time.parseTimestamp(jsonObject.get("endOfStatistics").toString());

        // 计算时间段交集
        long start = Math.max(expenseStart, StartOfStatistics);
        long end = Math.min(expenseEnt, endOfStatistics);
        int intersectionDays = 0;
        if (start <= end) {
            // 计算交集天数
            Calendar calendar = Calendar.getInstance();
            calendar.setTimeInMillis(start);
            Date startDate = calendar.getTime();
            calendar.setTimeInMillis(end);
            Date endDate = calendar.getTime();
            intersectionDays = (int) ((endDate.getTime() - startDate.getTime()) / (1000 * 60 * 60 * 24));
        }

        // 返回结果
        return intersectionDays;
    }
}
