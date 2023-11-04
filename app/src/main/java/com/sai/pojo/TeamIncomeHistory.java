package com.sai.pojo;

public class TeamIncomeHistory {
    private String user_id;
    private String amount;
    private String label;
    private String refer_id;
    private String referName;
    private String date;

    public TeamIncomeHistory(String user_id, String amount, String label, String refer_id, String referName, String date) {
        this.user_id = user_id;
        this.amount = amount;
        this.label = label;
        this.refer_id = refer_id;
        this.referName = referName;
        this.date = date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getRefer_id() {
        return refer_id;
    }

    public void setRefer_id(String refer_id) {
        this.refer_id = refer_id;
    }

    public String getReferName() {
        return referName;
    }

    public void setReferName(String referName) {
        this.referName = referName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
