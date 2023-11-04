package com.sai.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sai.gold.R;
import com.sai.pojo.IdActivationHistory;

import java.util.ArrayList;


public class IdActivationHistoryAdapter extends RecyclerView.Adapter<IdActivationHistoryAdapter.HistoryViewHolder> {
    Context context;
    ArrayList<IdActivationHistory> idActivationHistories;

    public IdActivationHistoryAdapter(Context context, ArrayList<IdActivationHistory> idActivationHistories) {
        this.context = context;
        this.idActivationHistories = idActivationHistories;
    }

    @NonNull
    @Override
    public HistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView  = layoutInflater.inflate(R.layout.custom_id_activation_history_layout, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryViewHolder holder, int position) {
        IdActivationHistory idActivationHistory=  idActivationHistories.get(position);
        holder.tvUserID.setText(idActivationHistory.getActivated_by());
        holder.tvUserName.setText("User Name: "+idActivationHistory.getUser_name());
        holder.tvClientID.setText("Client Id: "+idActivationHistory.getUser_id());
        holder.tvAmount.setText("Plan Amount: "+idActivationHistory.getPackage_amount());
        holder.tvDate.setText("Date: "+idActivationHistory.getPlan_date());
    }

    @Override
    public int getItemCount() {
        return idActivationHistories.size();
    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {
        TextView tvUserID,tvUserName,tvClientID,tvAmount,tvDate;
        public HistoryViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvUserID=itemView.findViewById(R.id.tvUserID);
//            tvUserName=itemView.findViewById(R.id.tvUserName);
//            tvClientID=itemView.findViewById(R.id.tvClientID);
//            tvAmount=itemView.findViewById(R.id.tvAmount);
//            tvDate=itemView.findViewById(R.id.tvDate);
        }
    }
}
