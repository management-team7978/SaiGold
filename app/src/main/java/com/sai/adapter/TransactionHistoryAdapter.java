package com.sai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sai.gold.R;
import com.sai.pojo.TransactionHistory;

import java.util.ArrayList;

public class TransactionHistoryAdapter extends RecyclerView.Adapter<TransactionHistoryAdapter.TransactionViewHolder> {
    Context context;
    ArrayList<TransactionHistory> transactionHistories;

    public TransactionHistoryAdapter(Context context, ArrayList<TransactionHistory> transactionHistories) {
        this.context = context;
        this.transactionHistories = transactionHistories;
    }

    @NonNull
    @Override
    public TransactionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView  = layoutInflater.inflate(R.layout.custom_transaction_history_layout, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new TransactionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TransactionViewHolder holder, int position) {
        TransactionHistory transactionHistory=  transactionHistories.get(position);
        //holder.tvAmountStatus.setText(transactionHistory.getStatus());
        holder.tvAmount.setText("Amount:" +transactionHistory.getAmount());
        holder.tvRemark.setText("Remark: "+transactionHistory.getRemark());
        holder.tvDate.setText("Date: "+transactionHistory.getTrans_date());

//        if (transactionHistory.getStatus().equalsIgnoreCase("Credit")){
//            holder.tvAmountStatus.setText(transactionHistory.getStatus());
//            holder.lnr.setBackgroundResource(R.drawable.credit);
//        }else if (transactionHistory.getStatus().equalsIgnoreCase("Debit")){
//            holder.tvAmountStatus.setText(transactionHistory.getStatus());
//            holder.lnr.setBackgroundResource(R.drawable.debit);
//        }
    }

    @Override
    public int getItemCount() {
        return transactionHistories.size();
    }

    public class TransactionViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmountStatus,tvAmount,tvRemark,tvDate;
        LinearLayout lnr;

        public TransactionViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvAmountStatus=itemView.findViewById(R.id.tvAmountStatus);
//            tvAmount=itemView.findViewById(R.id.tvAmount);
//            tvRemark=itemView.findViewById(R.id.tvRemark);
//            tvDate=itemView.findViewById(R.id.tvDate);
//            lnr=itemView.findViewById(R.id.lnr);
        }
    }
}
