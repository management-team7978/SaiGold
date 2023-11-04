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
import com.sai.adapter.DirectReferralAdapter;
import com.sai.gold.R;
import com.sai.pojo.DirectReferral;
import com.sai.util.AppController;
import com.sai.util.Keys;
import com.sai.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DirectReferralFragment extends Fragment {

    RecyclerView recyclerDirectReferral;
    ArrayList<DirectReferral> directReferrals;
    DirectReferralAdapter directReferralAdapter;
    String uuid="";
    SwipeRefreshLayout swipeRefreshLayout;
    RelativeLayout rlNotFound,rlLoader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =  inflater.inflate(R.layout.fragment_direct_referral, container, false);

        recyclerDirectReferral=v.findViewById(R.id.recyclerDirectReferral);
        recyclerDirectReferral.setLayoutManager(new LinearLayoutManager(getActivity()));
        swipeRefreshLayout=v.findViewById(R.id.swipeRefresh);
        directReferrals=new ArrayList<DirectReferral>();
        rlLoader=v.findViewById(R.id.rlLoader);
        rlNotFound=v.findViewById(R.id.rlNotFound);


        directReferralAdapter= new DirectReferralAdapter(getActivity(),directReferrals);
        recyclerDirectReferral.setAdapter(directReferralAdapter);

        uuid= SharedPreference.get("uuid");

        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.black));
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                directReferrals.clear();
                getDirectReferral(uuid);
            }
        });
        getDirectReferral(uuid);

        return v;
    }

    private void getDirectReferral(String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.getDirect_referral, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                swipeRefreshLayout.setRefreshing(false);
                Log.i("pri",response);
                try{
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")){
                        JSONArray jsonArray=jsonObject.getJSONArray("data");
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject1=jsonArray.getJSONObject(i);
                            directReferrals.add(new DirectReferral(
                                    jsonObject1.getString("serial"),
                                    jsonObject1.getString("user_id"),
                                    jsonObject1.getString("user_name"),
                                    jsonObject1.getString("joining_date"),
                                    jsonObject1.getString("activation_status_code"),
                                    jsonObject1.getString("activation_status")));
                        }
                        directReferralAdapter= new DirectReferralAdapter(getActivity(),directReferrals);
                        recyclerDirectReferral.setAdapter(directReferralAdapter);
                    }else {
                        rlLoader.setVisibility(View.GONE);
                        rlNotFound.setVisibility(View.VISIBLE);
                    }
                    rlLoader.setVisibility(View.GONE);
                }catch (JSONException e){
                    e.printStackTrace();
                    rlLoader.setVisibility(View.GONE);
                }
                swipeRefreshLayout.setRefreshing(false);

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(getActivity(), "Technical problem arises", Toast.LENGTH_SHORT).show();
                swipeRefreshLayout.setRefreshing(false);
                rlLoader.setVisibility(View.GONE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("uuid",uuid);
                Log.i("pori","param="+params);
                return  params;
            }
        };
        AppController.getInstance().add(request);
    }
}