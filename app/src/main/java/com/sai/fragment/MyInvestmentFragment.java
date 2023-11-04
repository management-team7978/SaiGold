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
import com.sai.adapter.InvestmentHistoryAdapter;
import com.sai.gold.R;
import com.sai.pojo.MyInvestment;
import com.sai.util.AppController;
import com.sai.util.Keys;
import com.sai.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyInvestmentFragment extends Fragment {
    RecyclerView recyclerMyInvestment;
    ArrayList<MyInvestment> investmentArrayList;
    InvestmentHistoryAdapter investmentHistoryAdapter;
    RelativeLayout rlNotFound,rlLoader;
    SwipeRefreshLayout swipeRefresh;
    String uuid="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_my_investment, container, false);
//        FirebaseAnalytics.getInstance(getActivity());
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        recyclerMyInvestment=view.findViewById(R.id.recyclerMyInvestment);
        swipeRefresh=view.findViewById(R.id.swipeRefresh);
        rlLoader=view.findViewById(R.id.rlLoader);
        rlNotFound=view.findViewById(R.id.rlNotFound);
        recyclerMyInvestment.setLayoutManager(new LinearLayoutManager(getActivity()));
        investmentArrayList=new ArrayList<MyInvestment>();
        recyclerMyInvestment.setHasFixedSize(true);

        uuid= SharedPreference.get("uuid");
        getMyPlan(uuid);

        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.black));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                investmentArrayList.clear();
                getMyPlan(uuid);
            }
        });


        return view;
    }

    private void getMyPlan(String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.myplans, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                swipeRefresh.setRefreshing(false);
                Log.i("pri",response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            investmentArrayList.add(new MyInvestment(
                                    jsonObject1.getString("user_id"),
                                    jsonObject1.getString("package_amount"),
                                    jsonObject1.getString("total_days"),
                                    jsonObject1.getString("validity_day"),
                                    jsonObject1.getString("pid"),
                                    jsonObject1.getString("plan_date")));
                        }
                        investmentHistoryAdapter= new InvestmentHistoryAdapter(getActivity(),investmentArrayList);
                        recyclerMyInvestment.setAdapter(investmentHistoryAdapter);
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