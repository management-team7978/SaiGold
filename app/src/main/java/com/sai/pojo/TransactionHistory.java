package com.sai.pojo;

public class TransactionHistory {
    private String user_id;
    private String status;
    private String amount;
    private String remark;
    private String trans_date;

    public TransactionHistory(String user_id, String status, String amount, String remark, String trans_date) {
        this.user_id = user_id;
        this.status = status;
        this.amount = amount;
        this.remark = remark;
        this.trans_date = trans_date;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTrans_date() {
        return trans_date;
    }

    public void setTrans_date(String trans_date) {
        this.trans_date = trans_date;
    }
}
