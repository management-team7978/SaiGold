package com.sai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;


import com.sai.gold.R;
import com.sai.pojo.MoneyWithdrawHistory;

import java.util.ArrayList;

public class MoneyWithdrawHistoryAdapter extends RecyclerView.Adapter<MoneyWithdrawHistoryAdapter.HistoryViewHolder>{
    Context context;
    ArrayList<MoneyWithdrawHistory> moneyWithdrawHistories;
    boolean onVisible = true;

    public MoneyWithdrawHistoryAdapter(Context context, ArrayList<MoneyWithdrawHistory> moneyWithdrawHistories) {
        this.context = context;
        this.moneyWithdrawHistories = moneyWithdrawHistories;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView  = layoutInflater.inflate(R.layout.custom_withdraw_history_layout, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        MoneyWithdrawHistory moneyWithdrawHistory=  moneyWithdrawHistories.get(position);
        holder.tvAmount.setText("Withdrawal amount "+moneyWithdrawHistory.getWithdraw_amt());
        holder.tvAccountName.setText(moneyWithdrawHistory.getHolder_name());
        holder.tvBankName.setText(moneyWithdrawHistory.getBank_name());
        holder.tvIFSCCode.setText(moneyWithdrawHistory.getIfsc());
        holder.tvAccNo.setText(moneyWithdrawHistory.getAccount_no());
        holder.tvTransId.setText(moneyWithdrawHistory.getTransaction_id());
        holder.tvBankUTR.setText(moneyWithdrawHistory.getBank_utr());
        holder.tvPayAmount.setText(moneyWithdrawHistory.getPay_amount());
        holder.tvMessage.setText(moneyWithdrawHistory.getErrormessage());
        holder.tvStatus.setText("Withdrawal "+moneyWithdrawHistory.getTransaction_status());
        holder.tvDate.setText(moneyWithdrawHistory.getPay_date());
        holder.tvAdminCharge.setText(moneyWithdrawHistory.getAdmin_charge());


//        if (moneyWithdrawHistory.getTransaction_status().equalsIgnoreCase("success")){
//            holder.tvStatus.setText("Your withdrawal amount "+moneyWithdrawHistory.getWithdraw_amt()+" is successful.");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
//        }else if (moneyWithdrawHistory.getTransaction_status().equalsIgnoreCase("pending")){
//            holder.tvStatus.setText("Your withdrawal amount "+moneyWithdrawHistory.getWithdraw_amt()+" is pending");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.yellow));
//        }else if (moneyWithdrawHistory.getTransaction_status().equalsIgnoreCase("failed")){
//            holder.tvStatus.setText("Your withdrawal amount "+moneyWithdrawHistory.getWithdraw_amt()+" is failed");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
//        }

//        if (moneyWithdrawHistory.getTransaction_status().equalsIgnoreCase("success")){
//            holder.tvStatus.setText("Your withdrawal amount "+moneyWithdrawHistory.getWithdraw_amt()+" is successful.");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.green));
//            holder.imgwithdrawStatus.setImageResource(R.drawable.correct);
//        }else if (moneyWithdrawHistory.getTransaction_status().equalsIgnoreCase("pending")){
//            holder.tvStatus.setText("Your withdrawal amount "+moneyWithdrawHistory.getWithdraw_amt()+" is pending");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.yellow));
//            holder.imgwithdrawStatus.setImageResource(R.drawable.pending);
//        }else if (moneyWithdrawHistory.getTransaction_status().equalsIgnoreCase("failed")){
//            holder.tvStatus.setText("Your withdrawal amount "+moneyWithdrawHistory.getWithdraw_amt()+" is failed");
//            holder.tvStatus.setTextColor(ContextCompat.getColor(context, R.color.red));
//            holder.imgwithdrawStatus.setImageResource(R.drawable.cross);
//        }

        holder.rlViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onVisible = !onVisible; // Toggle the value of onVisible
                if (onVisible) {
                    holder.lnrAllDetails.setVisibility(View.VISIBLE);
                } else {
                    holder.lnrAllDetails.setVisibility(View.GONE);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return moneyWithdrawHistories.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmount,tvAccountName,tvBankName,tvAccNo,tvIFSCCode,tvTransId,tvBankUTR,tvAdminCharge,
        tvPayAmount,tvMessage,tvStatus,tvDate;
        ImageView imgwithdrawStatus,img;
        RelativeLayout rlViewMore;
        LinearLayout lnrAllDetails;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvAmount=itemView.findViewById(R.id.tvAmount);
//            tvAccountName=itemView.findViewById(R.id.tvAccountName);
//            tvAccNo=itemView.findViewById(R.id.tvAccNo);
//            tvBankName=itemView.findViewById(R.id.tvBankName);
//            tvIFSCCode=itemView.findViewById(R.id.tvIFSCCode);
//            tvTransId=itemView.findViewById(R.id.tvTransId);
//            tvBankUTR=itemView.findViewById(R.id.tvBankUTR);
//            tvAdminCharge=itemView.findViewById(R.id.tvAdminCharge);
//            tvPayAmount=itemView.findViewById(R.id.tvPayAmount);
//            tvMessage=itemView.findViewById(R.id.tvMessage);
//             tvStatus=itemView.findViewById(R.id.tvStatus);
//            tvDate=itemView.findViewById(R.id.tvDate);
//
//            imgwithdrawStatus=itemView.findViewById(R.id.imgwithdrawStatus);
//            rlViewMore=itemView.findViewById(R.id.rlViewMore);
//            lnrAllDetails=itemView.findViewById(R.id.lnrAllDetails);
//            img=itemView.findViewById(R.id.img);
        }
    }
}
