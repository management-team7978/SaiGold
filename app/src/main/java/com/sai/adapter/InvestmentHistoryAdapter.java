package com.sai.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sai.gold.R;
import com.sai.pojo.MyInvestment;

import java.util.ArrayList;

public class InvestmentHistoryAdapter extends RecyclerView.Adapter<InvestmentHistoryAdapter.InvestmentViewHolder>{
    Context context;
    ArrayList<MyInvestment> myInvestments;

    public InvestmentHistoryAdapter(Context context, ArrayList<MyInvestment> myInvestments) {
        this.context = context;
        this.myInvestments = myInvestments;
    }

    @NonNull
    @Override
    public InvestmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView  = layoutInflater.inflate(R.layout.custom_my_investment_layout, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new InvestmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull InvestmentViewHolder holder, int position) {
        MyInvestment myInvestment=  myInvestments.get(position);
        holder.tvAmount.setText("Package Amount: "+myInvestment.getPackage_amount());
        holder.tvValidityDays.setText("Validity Day: "+myInvestment.getValidity_day());
        holder.tvHoldingDays.setText("Holding Day: "+myInvestment.getTotal_days());
        holder.tvDate.setText("Plan Activation Date: "+myInvestment.getPlan_dateplan_date());

        holder.imgDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent i = new Intent(context, InvoiceActivity.class);
//                i.putExtra("pid",myInvestment.getPid());
//                i.putExtra("user_id",myInvestment.getUser_id());
//                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return myInvestments.size();
    }

    public class InvestmentViewHolder extends RecyclerView.ViewHolder {
        TextView tvAmount,tvValidityDays,tvHoldingDays,tvDate;
        ImageView imgDownload;
        public InvestmentViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvAmount=itemView.findViewById(R.id.tvAmount);
//            tvValidityDays=itemView.findViewById(R.id.tvValidityDays);
//            tvHoldingDays=itemView.findViewById(R.id.tvHoldingDays);
//            tvDate=itemView.findViewById(R.id.tvDate);
//            imgDownload=itemView.findViewById(R.id.imgDownload);
        }
    }
}
