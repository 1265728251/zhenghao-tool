package com.xdap.tool.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author:杨智
 * @create: 2023-02-17 15:42
 * @Description: 把年月日的时间格式转换为时间戳
 */
@Slf4j
@Component
public class TimeTool {
    public  long parseTimestamp(String dateString) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = dateFormat.parse(dateString);
            return date.getTime();
        } catch (ParseException e) {
            // 日期格式不正确，抛出异常
            throw new RuntimeException("Invalid date format: " + dateString);
        }
    }

    /*在原来日期基础上加一个月*/
    public String addMonth(String dateString){

        // 定义日期格式化器
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        // 解析日期字符串
        LocalDate date = LocalDate.parse(dateString, formatter);

        // 添加一个月
        LocalDate newDate = date.plus(Period.ofMonths(1));

        // 格式化新日期为字符串
        String newDateStr = newDate.format(formatter);

        log.info(newDateStr);
        return newDateStr;
    }
}
