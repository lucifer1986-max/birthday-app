package com.birthdayreminder;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "birthdays")
public class Birthday {
    @PrimaryKey(autoGenerate = true)
    public int id;
    
    public String name;
    public int year;
    public int month;
    public int day;
    public boolean isLunar; // true为农历，false为阳历
    public String relation; // 关系（如：朋友、亲戚等）
    public String note; // 备注
    
    public Birthday() {}
    
    public Birthday(String name, int year, int month, int day, boolean isLunar) {
        this.name = name;
        this.year = year;
        this.month = month;
        this.day = day;
        this.isLunar = isLunar;
    }
    
    // 获取显示用的日期字符串
    public String getDateString() {
        String calendar = isLunar ? "农历" : "阳历";
        return String.format("%s %d年%d月%d日", calendar, year, month, day);
    }
    
    // 获取年龄
    public int getAge() {
        java.util.Calendar now = java.util.Calendar.getInstance();
        int currentYear = now.get(java.util.Calendar.YEAR);
        return currentYear - year;
    }
}