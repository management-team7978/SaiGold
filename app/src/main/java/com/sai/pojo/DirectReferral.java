package com.sai.pojo;

public class DirectReferral {
    private String serial;
    private String user_id;
    private String user_name;
    private String joining_date;
    private String activation_status_code;
    private String activation_status;

    public DirectReferral(String serial, String user_id, String user_name, String joining_date, String activation_status_code, String activation_status) {
        this.serial = serial;
        this.user_id = user_id;
        this.user_name = user_name;
        this.joining_date = joining_date;
        this.activation_status_code = activation_status_code;
        this.activation_status = activation_status;
    }

    public String getSerial() {
        return serial;
    }

    public void setSerial(String serial) {
        this.serial = serial;
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
