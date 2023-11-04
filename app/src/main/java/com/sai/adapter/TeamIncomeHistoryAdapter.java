package com.sai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.sai.gold.R;
import com.sai.pojo.TeamIncomeHistory;

import java.util.ArrayList;

public class TeamIncomeHistoryAdapter extends RecyclerView.Adapter<TeamIncomeHistoryAdapter.IncomeHistoryViewHolder> {
    Context context;
    ArrayList<TeamIncomeHistory> teamIncomeHistorylist;

    public TeamIncomeHistoryAdapter(Context context, ArrayList<TeamIncomeHistory> teamIncomeHistory) {
        this.context = context;
        this.teamIncomeHistorylist = teamIncomeHistory;
    }

    @NonNull
    @Override
    public IncomeHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView  = layoutInflater.inflate(R.layout.custom_team_income_history_layout, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new IncomeHistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IncomeHistoryViewHolder holder, int position) {
        TeamIncomeHistory teamIncomeHistory=  teamIncomeHistorylist.get(position);
        holder.tvAmount.setText("Amount: "+teamIncomeHistory.getAmount());
        holder.tvLevel.setText("Level: "+teamIncomeHistory.getLabel());
        holder.tvReferralId.setText("Reference Id: "+teamIncomeHistory.getRefer_id());
        holder.tvReferralName.setText("Reference name: "+teamIncomeHistory.getReferName());
        holder.tvDate.setText("Date: "+teamIncomeHistory.getDate());
    }

    @Override
    public int getItemCount() {
        return teamIncomeHistorylist.size();
    }

    public class IncomeHistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvLevel,tvReferralId,tvReferralName,tvAmount,tvDate;
        public IncomeHistoryViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvAmount=itemView.findViewById(R.id.tvAmount);
//            tvLevel=itemView.findViewById(R.id.tvLevel);
//            tvReferralId=itemView.findViewById(R.id.tvReferralID);
//            tvReferralName=itemView.findViewById(R.id.tvReferralName);
//            tvDate=itemView.findViewById(R.id.tvDate);

        }
    }
}
