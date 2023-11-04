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
import com.sai.pojo.DirectReferral;

import java.util.ArrayList;

public class DirectReferralAdapter extends RecyclerView.Adapter<DirectReferralAdapter.DirectViewHolder> {
    Context context;
    ArrayList<DirectReferral> directReferrals;

    public DirectReferralAdapter(Context context, ArrayList<DirectReferral> directReferrals) {
        this.context = context;
        this.directReferrals = directReferrals;
    }

    @NonNull
    @Override
    public DirectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView  = layoutInflater.inflate(R.layout.custom_direct_referral_layout, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new DirectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DirectViewHolder holder, int position) {
        DirectReferral directReferral=  directReferrals.get(position);
       // holder.tvStatus.setText(directReferral.getActivation_status());
        holder.tvUserName.setText("User Name: "+directReferral.getUser_name());
        holder.tvUserId.setText("User Id: "+directReferral.getUser_id());
        holder.tvDate.setText("Date: "+directReferral.getJoining_date());

//        if (directReferral.getActivation_status_code().equals("0")){
//            holder.lnr.setBackgroundResource(R.drawable.direct_green);
//            holder.tvStatus.setText(directReferral.getActivation_status());
//        }else if (directReferral.getActivation_status_code().equals("1")){
//            holder.lnr.setBackgroundResource(R.drawable.direct_red);
//            holder.tvStatus.setText(directReferral.getActivation_status());
//        }else if (directReferral.getActivation_status_code().equals("2")){
//            holder.lnr.setBackgroundResource(R.drawable.direct_yellow);
//            holder.tvStatus.setText(directReferral.getActivation_status());
//        }
    }

    @Override
    public int getItemCount() {
        return directReferrals.size();
    }

    public class DirectViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus,tvUserName,tvUserId,tvDate;
        LinearLayout lnr;

        public DirectViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvStatus=itemView.findViewById(R.id.tvStatus);
//            tvUserName=itemView.findViewById(R.id.tvUserName);
//            tvUserId=itemView.findViewById(R.id.tvUserId);
//            tvDate=itemView.findViewById(R.id.tvDate);
//            lnr=itemView.findViewById(R.id.lnr);

        }
    }
}
