package com.birthdayreminder;

import java.util.Calendar;

/**
 * 农历转换工具类
 * 简化版本，主要用于农历日期的基本处理
 */
public class LunarCalendar {
    
    // 农历月份名称
    private static final String[] LUNAR_MONTHS = {
        "正月", "二月", "三月", "四月", "五月", "六月",
        "七月", "八月", "九月", "十月", "冬月", "腊月"
    };
    
    // 农历日期名称
    private static final String[] LUNAR_DAYS = {
        "初一", "初二", "初三", "初四", "初五", "初六", "初七", "初八", "初九", "初十",
        "十一", "十二", "十三", "十四", "十五", "十六", "十七", "十八", "十九", "二十",
        "廿一", "廿二", "廿三", "廿四", "廿五", "廿六", "廿七", "廿八", "廿九", "三十"
    };
    
    /**
     * 获取农历月份名称
     */
    public static String getLunarMonthName(int month) {
        if (month >= 1 && month <= 12) {
            return LUNAR_MONTHS[month - 1];
        }
        return String.valueOf(month) + "月";
    }
    
    /**
     * 获取农历日期名称
     */
    public static String getLunarDayName(int day) {
        if (day >= 1 && day <= 30) {
            return LUNAR_DAYS[day - 1];
        }
        return String.valueOf(day) + "日";
    }
    
    /**
     * 格式化农历日期显示
     */
    public static String formatLunarDate(int month, int day) {
        return getLunarMonthName(month) + getLunarDayName(day);
    }
    
    /**
     * 简单的农历转阳历近似计算
     * 注意：这是一个简化版本，实际农历转换需要复杂的天文计算
     * 这里主要用于演示，实际应用中建议使用专业的农历转换库
     */
    public static Calendar lunarToSolar(int lunarYear, int lunarMonth, int lunarDay) {
        Calendar calendar = Calendar.getInstance();
        
        // 简化处理：假设农历比阳历平均晚约20-50天
        // 实际应用中需要使用准确的农历转换算法
        calendar.set(lunarYear, lunarMonth - 1, lunarDay);
        calendar.add(Calendar.DAY_OF_YEAR, 30); // 简化偏移
        
        return calendar;
    }
    
    /**
     * 检查是否为农历闰月
     * 简化版本，实际需要查表计算
     */
    public static boolean isLeapMonth(int year, int month) {
        // 简化处理，实际需要复杂计算
        return false;
    }
    
    /**
     * 获取农历年份的天数
     */
    public static int getLunarYearDays(int year) {
        // 简化处理，农历年一般354-384天
        return 354;
    }
}