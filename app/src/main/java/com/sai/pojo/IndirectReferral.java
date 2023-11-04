package com.sai.pojo;

public class IndirectReferral {
    private String user_id;
    private String user_name;
    private String joining_date;
    private String package_amount;
    private String activation_status_code;
    private String activation_status;

    public IndirectReferral(String user_id, String user_name, String joining_date, String package_amount, String activation_status_code, String activation_status) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.joining_date = joining_date;
        this.package_amount = package_amount;
        this.activation_status_code = activation_status_code;
        this.activation_status = activation_status;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getJoining_date() {
        return joining_date;
    }

    public void setJoining_date(String joining_date) {
        this.joining_date = joining_date;
    }

    public String getPackage_amount() {
        return package_amount;
    }

    public void setPackage_amount(String package_amount) {
        this.package_amount = package_amount;
    }

    public String getActivation_status_code() {
        return activation_status_code;
    }

    public void setActivation_status_code(String activation_status_code) {
        this.activation_status_code = activation_status_code;
    }

    public String getActivation_status() {
        return activation_status;
    }

    public void setActivation_status(String activation_status) {
        this.activation_status = activation_status;
    }
}
