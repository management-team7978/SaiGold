package com.sai.fragment;

import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sai.gold.R;
import com.sai.util.AppController;
import com.sai.util.Keys;
import com.sai.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class DashboardFragment extends Fragment {
    TextView tvMainBalance,tvWallet,tvTotalDirect,tvPaidFreeDirect,tvDirectIncome,tvSuccessWithdraw,
            tvDailyProfit,tvLeaveIncome,tvTotalIncome;
    String uuid="";
    RelativeLayout rlLoader;
    SwipeRefreshLayout swipeRefresh;
    TextView tvUserName,tvUserID,tvActivationStatus,tvKycStatus,tvDate,txtAlert;
    CardView cardView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_dashboard, container, false);

//        FirebaseAnalytics.getInstance(getActivity());
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        tvMainBalance=v.findViewById(R.id.tvMainBalance);
        tvWallet=v.findViewById(R.id.tvWallet);
        tvTotalDirect=v.findViewById(R.id.tvTotalDirect);
        tvPaidFreeDirect=v.findViewById(R.id.tvPaidFreeDirect);
        tvDirectIncome=v.findViewById(R.id.tvDirectIncome);
        tvSuccessWithdraw=v.findViewById(R.id.tvSuccessWithdraw);
        tvDailyProfit=v.findViewById(R.id.tvDailyProfit);
        tvLeaveIncome=v.findViewById(R.id.tvLeaveIncome);
        tvTotalIncome=v.findViewById(R.id.tvTotalIncome);
        rlLoader=v.findViewById(R.id.rlLoader);
        swipeRefresh=v.findViewById(R.id.swipeRefresh);
        tvDate=v.findViewById(R.id.tvDate);
        tvKycStatus=v.findViewById(R.id.tvKycStatus);
        tvActivationStatus=v.findViewById(R.id.tvActivationStatus);
        tvUserID=v.findViewById(R.id.tvUserID);
        tvUserName=v.findViewById(R.id.tvUserName);
        cardView=v.findViewById(R.id.cardView);
        txtAlert=v.findViewById(R.id.txtAlert);

        uuid= SharedPreference.get("uuid");
        getDashboard(uuid);

        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.black));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDashboard(uuid);
            }
        });

        Animation anim = new AlphaAnimation(1f, 0f);
        anim.setDuration(800); // duration of each animation cycle
        anim.setRepeatCount(Animation.INFINITE); // repeat infinitely
        anim.setRepeatMode(Animation.REVERSE); // reverse the animation

        cardView.setBackgroundResource(R.drawable.cardview_flashing);
        cardView.startAnimation(anim);

        return v;
    }

    private void getDashboard(String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.dashboard, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                swipeRefresh.setRefreshing(false);
                Log.i("pri","dashboard =>>"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.i("pri","response=>"+jsonObject);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        JSONArray dataArray  = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            Log.i("pri", "dataobj" + dataobj);

                            tvMainBalance.setText(dataobj.getString("earnwallet"));
                            tvWallet.setText(dataobj.getString("inr_wallet"));
                            tvTotalDirect.setText("Total Direct "+"("+dataobj.getString("total_directs")+")");
                            tvPaidFreeDirect.setText("Free:"+dataobj.getString("free_directs")+" "+"Paid:"+dataobj.getString("paid_directs"));
                            tvDirectIncome.setText(dataobj.getString("direct_income"));
                            tvDailyProfit.setText(dataobj.getString("daily_income_wallet"));
                            tvLeaveIncome.setText(dataobj.getString("level_income"));
                            tvTotalIncome.setText(dataobj.getString("total_income"));
                            tvUserID.setText(dataobj.getString("userid"));
                            tvUserName.setText(dataobj.getString("username"));
                            tvDate.setText(dataobj.getString("joining_date"));
                            tvUserID.setText(dataobj.getString("userid"));
                            tvUserID.setText(dataobj.getString("userid"));

                            if (dataobj.get("kyc_status_code").equals("1")){
                                tvKycStatus.setBackgroundResource(R.drawable.green_activated_status);
                                tvKycStatus.setText(dataobj.getString("kyc_status"));
                            }else if (dataobj.get("kyc_status_code").equals("0")){
                                tvKycStatus.setBackgroundResource(R.drawable.red_activated_status);
                                tvKycStatus.setText(dataobj.getString("kyc_status"));
                            }

                            if (dataobj.get("activation_state_code").equals("2")){
                                tvActivationStatus.setBackgroundResource(R.drawable.yellow_id_activation);
                                tvActivationStatus.setText(dataobj.getString("activation_state"));
                            }else if (dataobj.get("activation_state_code").equals("1")){
                                tvActivationStatus.setBackgroundResource(R.drawable.red_activated_status);
                                tvActivationStatus.setText(dataobj.getString("activation_state"));
                            }else if (dataobj.get("activation_state_code").equals("0")){
                                tvActivationStatus.setBackgroundResource(R.drawable.green_activated_status);
                                tvActivationStatus.setText(dataobj.getString("activation_state"));
                            }
                            if (dataobj.getString("successfull_withdrawls").equals("null")){
                                tvSuccessWithdraw.setText("0");
                            }else {
                                tvSuccessWithdraw.setText(dataobj.getString("successfull_withdrawls"));
                            }

                            if (dataobj.isNull("withdraw_validity")){
                                cardView.setVisibility(View.GONE);
                            }else {
                                cardView.setVisibility(View.VISIBLE);
                                txtAlert.setText("Please activate your Id OR else your id will be blocked in "+ dataobj.getString("withdraw_validity") + " days");
                            }
                        }

                        //  Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }else {
                        rlLoader.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    rlLoader.setVisibility(View.GONE);
                    e.printStackTrace();
                }
                swipeRefresh.setRefreshing(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                rlLoader.setVisibility(View.GONE);
                error.printStackTrace();
                swipeRefresh.setRefreshing(false);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("uuid",uuid);
                return  params;
            }
        };
        AppController.getInstance().add(request);
    }

}