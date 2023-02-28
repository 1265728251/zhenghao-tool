package com.xdap.tool.controller;
import com.alibaba.fastjson.JSONObject;
import com.xdap.tool.service.IToolService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/custom/tool")
public class toolController {

    @Autowired
    private IToolService iCostCalculationService;

    /*按时间区间计算两个段天数的交集天数*/
    @ResponseBody
    @PostMapping(value = "/getday", produces = "application/json;charset=UTF-8")
    public String getday(@RequestBody JSONObject jsonParam) {
            log.info("请求的数据：{}",jsonParam.toJSONString());
            return iCostCalculationService.getDay(jsonParam).toJSONString();
        }
    /*按月份区间计算两个段天数的交集天数*/
    @ResponseBody
    @PostMapping(value = "/monthgetday", produces = "application/json;charset=UTF-8")
    public String monthGetDay(@RequestBody JSONObject jsonParam) {
        log.info("请求的数据：{}",jsonParam.toJSONString());
        return iCostCalculationService.monthGetDay(jsonParam).toJSONString();
    }

    /*把以上两个接口统一掉*/
    @ResponseBody
    @PostMapping(value = "/getall", produces = "application/json;charset=UTF-8")
    public String GetDayAll(@RequestBody JSONObject jsonParam) {
        log.info("请求的数据：{}",jsonParam.toJSONString());

        String json = "{}";

        try{

            if (jsonParam.get("choice").equals("按时间区间")) {
                json = iCostCalculationService.getDay(jsonParam).toJSONString();
            }else if (jsonParam.get("choice").equals("按自然月")){
                json = iCostCalculationService.monthGetDay(jsonParam).toJSONString();
            }else if (jsonParam.get("choice").equals("所有时间")){
                jsonParam.put("StartOfStatistics",jsonParam.get("expenseStart"));
                jsonParam.put("endOfStatistics",jsonParam.get("expenseEnt"));
                json = iCostCalculationService.getDay(jsonParam).toJSONString();

            }
            log.info("返回值：{}",json);
        }catch (Exception e){
            log.error(e.toString());
        }

        return json;
    }


    /*个人证书按分类筛选*/
    @ResponseBody
    @PostMapping(value = "/check", produces = "application/json;charset=UTF-8")
    public String check(@RequestBody JSONObject jsonParam) {
        log.info("请求的数据：{}",jsonParam.toJSONString());
        return iCostCalculationService.ICheck(jsonParam).toJSONString();
    }


}
