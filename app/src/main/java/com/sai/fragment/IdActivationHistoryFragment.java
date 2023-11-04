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
import com.sai.adapter.IdActivationHistoryAdapter;
import com.sai.gold.R;
import com.sai.pojo.IdActivationHistory;
import com.sai.util.AppController;
import com.sai.util.Keys;
import com.sai.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IdActivationHistoryFragment extends Fragment {
    RecyclerView recyclerIdActivationHistory;
    ArrayList<IdActivationHistory> idActivationHistories;
    IdActivationHistoryAdapter idActivationHistoryAdapter;
    RelativeLayout rlNotFound,rlLoader;
    SwipeRefreshLayout swipeRefresh;
    String uuid="";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_id_activation_history, container, false);
//        FirebaseAnalytics.getInstance(getActivity());
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        recyclerIdActivationHistory=v.findViewById(R.id.recyclerIdActivationHistory);
        swipeRefresh=v.findViewById(R.id.swipeRefresh);
        rlLoader=v.findViewById(R.id.rlLoader);
        rlNotFound=v.findViewById(R.id.rlNotFound);

        recyclerIdActivationHistory.setLayoutManager(new LinearLayoutManager(getActivity()));
        idActivationHistories=new ArrayList<IdActivationHistory>();
        recyclerIdActivationHistory.setHasFixedSize(true);

        uuid= SharedPreference.get("uuid");

        getActivationHistory(uuid);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.black));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                idActivationHistories.clear();
                getActivationHistory(uuid);
            }
        });
        return v;
    }

    private void getActivationHistory(String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.idactivatorhist, new Response.Listener<String>() {
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
                            idActivationHistories.add(new IdActivationHistory(
                                    jsonObject1.getString("activated_by"),
                                    jsonObject1.getString("package_amount"),
                                    jsonObject1.getString("user_id"),
                                    jsonObject1.getString("plan_date"),
                                    jsonObject1.getString("user_name")));
                        }
                        idActivationHistoryAdapter= new IdActivationHistoryAdapter(
                                getActivity(),idActivationHistories);
                        recyclerIdActivationHistory.setAdapter(idActivationHistoryAdapter);
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