package com.sai.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sai.adapter.MoneyWithdrawHistoryAdapter;
import com.sai.gold.R;
import com.sai.pojo.MoneyWithdrawHistory;
import com.sai.util.AppController;
import com.sai.util.Keys;
import com.sai.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class WithdrawHistoryFragment extends Fragment {

    RecyclerView recyclerWithdrawHistory;
    MoneyWithdrawHistoryAdapter moneyWithdrawHistoryAdapter;
    ArrayList<MoneyWithdrawHistory> moneyWithdrawHistories;
    RelativeLayout rlNotFound,rlLoader;
    SwipeRefreshLayout swipeRefresh;
    String uuid="";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_withdraw_history, container, false);
//        FirebaseAnalytics.getInstance(getActivity());
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        recyclerWithdrawHistory=v.findViewById(R.id.recyclerWithdrawHistory);
        swipeRefresh=v.findViewById(R.id.swipeRefresh);
        rlLoader=v.findViewById(R.id.rlLoader);
        rlNotFound=v.findViewById(R.id.rlNotFound);

        recyclerWithdrawHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerWithdrawHistory.setHasFixedSize(true);
        moneyWithdrawHistories=new ArrayList<MoneyWithdrawHistory>();

        uuid= SharedPreference.get("uuid");

        getWithdrawHistory(uuid);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.black));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                moneyWithdrawHistories.clear();
                getWithdrawHistory(uuid);
            }
        });
        return v;
    }

    private void getWithdrawHistory(String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.withdrawhistory, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                swipeRefresh.setRefreshing(false);
                Log.i("pri",response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);

                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            moneyWithdrawHistories.add(new MoneyWithdrawHistory(
                                    jsonObject1.optString("user_id"),
                                    jsonObject1.optString("transaction_id"),
                                    jsonObject1.optString("withdraw_amt"),
                                    jsonObject1.optString("bank_name"),
                                    jsonObject1.optString("holder_name"),
                                    jsonObject1.optString("ifsc"),
                                    jsonObject1.optString("account_no"),
                                    jsonObject1.optString("payment_method"),
                                    jsonObject1.optString("pay_date"),
                                    jsonObject1.optString("transaction_status"),
                                    jsonObject1.optString("tds"),
                                    jsonObject1.optString("admin_charge"),
                                    jsonObject1.optString("pay_amount"),
                                    jsonObject1.optString("myorderid"),
                                    jsonObject1.optString("bank_utr"),
                                    jsonObject1.optString("errormessage"),
                                    jsonObject1.optString("imts_charge"),
                                    jsonObject1.optString("gateway_status"),
                                    jsonObject1.optString("sender_name")));

                        }
                        moneyWithdrawHistoryAdapter= new MoneyWithdrawHistoryAdapter(
                                getActivity(),moneyWithdrawHistories);
                        recyclerWithdrawHistory.setAdapter(moneyWithdrawHistoryAdapter);
                    }else {
                        rlLoader.setVisibility(View.GONE);
                        rlNotFound.setVisibility(View.VISIBLE);
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                }
                swipeRefresh.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                rlLoader.setVisibility(View.GONE);
                Toast.makeText(getActivity(), "Technical problem arises", Toast.LENGTH_SHORT).show();
                swipeRefresh.setRefreshing(false);
            }
        })
        {
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