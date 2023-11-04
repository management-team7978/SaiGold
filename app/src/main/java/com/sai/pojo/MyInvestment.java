package com.sai.pojo;

public class MyInvestment {
    private String user_id;
    private String package_amount;
    private String total_days;
    private String validity_day;
    private String pid;
    private String plan_dateplan_date;

    public MyInvestment(String user_id, String package_amount, String total_days, String validity_day, String pid, String plan_dateplan_date) {
        this.user_id = user_id;
        this.package_amount = package_amount;
        this.total_days = total_days;
        this.validity_day = validity_day;
        this.pid = pid;
        this.plan_dateplan_date = plan_dateplan_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPackage_amount() {
        return package_amount;
    }

    public void setPackage_amount(String package_amount) {
        this.package_amount = package_amount;
    }

    public String getTotal_days() {
        return total_days;
    }

    public void setTotal_days(String total_days) {
        this.total_days = total_days;
    }

    public String getValidity_day() {
        return validity_day;
    }

    public void setValidity_day(String validity_day) {
        this.validity_day = validity_day;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPlan_dateplan_date() {
        return plan_dateplan_date;
    }

    public void setPlan_dateplan_date(String plan_dateplan_date) {
        this.plan_dateplan_date = plan_dateplan_date;
    }
}
