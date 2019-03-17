package com.wxx.hibernate.config;

/**
 * Created by Administrator on 2019/3/17/017.
 */
public class Pay {
    private int dayMoney;
    private int monthMoney;
    private int yearMoney;

    private Worker worker;

    public Worker getWorker() {
        return worker;
    }

    public void setWorker(Worker worker) {
        this.worker = worker;
    }

    public int getDayMoney() {
        return dayMoney;
    }

    public void setDayMoney(int dayMoney) {
        this.dayMoney = dayMoney;
    }

    public int getMonthMoney() {
        return monthMoney;
    }

    public void setMonthMoney(int monthMoney) {
        this.monthMoney = monthMoney;
    }

    public int getYearMoney() {
        return yearMoney;
    }

    public void setYearMoney(int yearMoney) {
        this.yearMoney = yearMoney;
    }

    @Override
    public String toString() {
        return "Pay{" +
                "dayMoney=" + dayMoney +
                ", monthMoney=" + monthMoney +
                ", yearMoney=" + yearMoney +
                ",+ Worker{" +
                    "id=" + worker.getId() +
                    ", name='" + worker.getName() + '\'' +

                '}';
    }
}
