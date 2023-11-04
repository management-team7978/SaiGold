package com.sai.pojo;

public class MoneyWithdrawHistory {
    private String user_id;
    private String transaction_id;
    private String withdraw_amt;
    private String bank_name;
    private String holder_name;
    private String ifsc;
    private String account_no;
    private String payment_method;
    private String pay_date;
    private String transaction_status;
    private String tds;
    private String admin_charge;
    private String pay_amount;
    private String myorderid;
    private String bank_utr;
    private String errormessage;
    private String imts_charge;
    private String gateway_status;
    private String sender_name;

    public MoneyWithdrawHistory(String user_id, String transaction_id, String withdraw_amt, String bank_name, String holder_name, String ifsc, String account_no, String payment_method, String pay_date, String transaction_status, String tds, String admin_charge, String pay_amount, String myorderid, String bank_utr, String errormessage, String imts_charge, String gateway_status, String sender_name) {
        this.user_id = user_id;
        this.transaction_id = transaction_id;
        this.withdraw_amt = withdraw_amt;
        this.bank_name = bank_name;
        this.holder_name = holder_name;
        this.ifsc = ifsc;
        this.account_no = account_no;
        this.payment_method = payment_method;
        this.pay_date = pay_date;
        this.transaction_status = transaction_status;
        this.tds = tds;
        this.admin_charge = admin_charge;
        this.pay_amount = pay_amount;
        this.myorderid = myorderid;
        this.bank_utr = bank_utr;
        this.errormessage = errormessage;
        this.imts_charge = imts_charge;
        this.gateway_status = gateway_status;
        this.sender_name = sender_name;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTransaction_id() {
        return transaction_id;
    }

    public void setTransaction_id(String transaction_id) {
        this.transaction_id = transaction_id;
    }

    public String getWithdraw_amt() {
        return withdraw_amt;
    }

    public void setWithdraw_amt(String withdraw_amt) {
        this.withdraw_amt = withdraw_amt;
    }

    public String getBank_name() {
        return bank_name;
    }

    public void setBank_name(String bank_name) {
        this.bank_name = bank_name;
    }

    public String getHolder_name() {
        return holder_name;
    }

    public void setHolder_name(String holder_name) {
        this.holder_name = holder_name;
    }

    public String getIfsc() {
        return ifsc;
    }

    public void setIfsc(String ifsc) {
        this.ifsc = ifsc;
    }

    public String getAccount_no() {
        return account_no;
    }

    public void setAccount_no(String account_no) {
        this.account_no = account_no;
    }

    public String getPayment_method() {
        return payment_method;
    }

    public void setPayment_method(String payment_method) {
        this.payment_method = payment_method;
    }

    public String getPay_date() {
        return pay_date;
    }

    public void setPay_date(String pay_date) {
        this.pay_date = pay_date;
    }

    public String getTransaction_status() {
        return transaction_status;
    }

    public void setTransaction_status(String transaction_status) {
        this.transaction_status = transaction_status;
    }

    public String getTds() {
        return tds;
    }

    public void setTds(String tds) {
        this.tds = tds;
    }

    public String getAdmin_charge() {
        return admin_charge;
    }

    public void setAdmin_charge(String admin_charge) {
        this.admin_charge = admin_charge;
    }

    public String getPay_amount() {
        return pay_amount;
    }

    public void setPay_amount(String pay_amount) {
        this.pay_amount = pay_amount;
    }

    public String getMyorderid() {
        return myorderid;
    }

    public void setMyorderid(String myorderid) {
        this.myorderid = myorderid;
    }

    public String getBank_utr() {
        return bank_utr;
    }

    public void setBank_utr(String bank_utr) {
        this.bank_utr = bank_utr;
    }

    public String getErrormessage() {
        return errormessage;
    }

    public void setErrormessage(String errormessage) {
        this.errormessage = errormessage;
    }

    public String getImts_charge() {
        return imts_charge;
    }

    public void setImts_charge(String imts_charge) {
        this.imts_charge = imts_charge;
    }

    public String getGateway_status() {
        return gateway_status;
    }

    public void setGateway_status(String gateway_status) {
        this.gateway_status = gateway_status;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }
}
