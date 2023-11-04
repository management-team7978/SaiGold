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
import com.sai.listener.IndirectReferralListener;
import com.sai.pojo.IndirectReferral;

import java.util.ArrayList;


public class IndirectReferralAdapter extends RecyclerView.Adapter<IndirectReferralAdapter.IndirectViewHolder> {
    Context context;
    ArrayList<IndirectReferral> indirectReferrals;
    private static IndirectReferralListener categoryclick;

    public IndirectReferralAdapter(Context context, ArrayList<IndirectReferral> indirectReferrals) {
        this.context = context;
        this.indirectReferrals = indirectReferrals;
    }

    @NonNull
    @Override
    public IndirectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View itemView  = layoutInflater.inflate(R.layout.custom_indirect_referral_layout, parent, false);
        itemView.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.WRAP_CONTENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new IndirectViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull IndirectViewHolder holder, int position) {
        IndirectReferral indirectReferral=  indirectReferrals.get(position);
        holder.tvUserName.setText("User Name: "+indirectReferral.getUser_name());
        holder.tvUserId.setText("User Id: "+indirectReferral.getUser_id());
        holder.tvDate.setText("Joining date on "+indirectReferral.getJoining_date());

//        if (indirectReferral.getActivation_status_code().equals("0")){
//            holder.lnr.setBackgroundResource(R.drawable.direct_green);
//            holder.tvStatus.setText(indirectReferral.getActivation_status());
//
//        }else if (indirectReferral.getActivation_status_code().equals("1")){
//            holder.lnr.setBackgroundResource(R.drawable.direct_red);
//            holder.tvStatus.setText(indirectReferral.getActivation_status());
//        }else if (indirectReferral.getActivation_status_code().equals("2")){
//            holder.lnr.setBackgroundResource(R.drawable.direct_yellow);
//            holder.tvStatus.setText(indirectReferral.getActivation_status());
//        }

        holder.tvViewReferral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (categoryclick!=null){
                    categoryclick.click(indirectReferral.getUser_id());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return indirectReferrals.size();
    }

    public class IndirectViewHolder extends RecyclerView.ViewHolder {
        TextView tvStatus,tvUserName,tvUserId,tvDate,tvViewReferral;
        LinearLayout lnr;
        public IndirectViewHolder(@NonNull View itemView) {
            super(itemView);
//            tvStatus=itemView.findViewById(R.id.tvStatus);
//            tvUserName=itemView.findViewById(R.id.tvUserName);
//            tvUserId=itemView.findViewById(R.id.tvUserId);
//            tvDate=itemView.findViewById(R.id.tvDate);
//            lnr=itemView.findViewById(R.id.lnr);
//            tvViewReferral=itemView.findViewById(R.id.tvViewReferral);

        }
    }

    public  static void bindListener(IndirectReferralListener Listener){
        categoryclick=Listener;
    }
}
