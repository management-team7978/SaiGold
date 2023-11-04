package com.sai.pojo;

public class IdActivationHistory {
    private String activated_by;
    private String package_amount;
    private String user_id;
    private String plan_date;
    private String user_name;

    public IdActivationHistory(String activated_by, String package_amount, String user_id, String plan_date, String user_name) {
        this.activated_by = activated_by;
        this.package_amount = package_amount;
        this.user_id = user_id;
        this.plan_date = plan_date;
        this.user_name = user_name;
    }

    public String getActivated_by() {
        return activated_by;
    }

    public void setActivated_by(String activated_by) {
        this.activated_by = activated_by;
    }

    public String getPackage_amount() {
        return package_amount;
    }

    public void setPackage_amount(String package_amount) {
        this.package_amount = package_amount;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getPlan_date() {
        return plan_date;
    }

    public void setPlan_date(String plan_date) {
        this.plan_date = plan_date;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
