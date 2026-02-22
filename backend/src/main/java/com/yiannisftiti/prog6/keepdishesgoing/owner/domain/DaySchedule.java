package com.yiannisftiti.prog6.keepdishesgoing.owner.domain;

import java.time.LocalTime;

public class DaySchedule {

    private Day day;
    private LocalTime opentime;
    private LocalTime closetime;

    public enum Day{
        MONDAY,
        TUESDAY,
        WEDNESDAY,
        THURSDAY,
        FRIDAY,
        SATURDAY,
        SUNDAY;
    }

    public DaySchedule(){}

    public DaySchedule(Day day, LocalTime opentime, LocalTime closetime){
        this.day=day;
        this.opentime=opentime;
        this.closetime=closetime;
    }

    public Day getDay() {
        return this.day;
    }

    public LocalTime getOpentime() {
        return this.opentime;
    }

    public LocalTime getClosetime() {
        return this.closetime;
    }

    public boolean isOpenAt(LocalTime time) {
        return !time.isBefore(opentime) && !time.isAfter(closetime);
    }

    public void setDay(Day day) {
        this.day = day;
    }

    public void setOpentime(LocalTime opentime) {
        this.opentime = opentime;
    }

    public void setClosetime(LocalTime closetime) {
        this.closetime = closetime;
    }
}
