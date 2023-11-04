package com.sai.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    TextView tvName,tvEmail,tvPhone,tvDate,tvPan,tvAadhar;
    String uuid="";
    RelativeLayout rlLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_profile, container, false);
//        FirebaseAnalytics.getInstance(getActivity());
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        tvName=v.findViewById(R.id.tvName);
        tvEmail=v.findViewById(R.id.tvEmail);
        tvPhone=v.findViewById(R.id.tvPhone);
        tvDate=v.findViewById(R.id.tvDate);
        tvPan=v.findViewById(R.id.tvPan);
        tvAadhar=v.findViewById(R.id.tvAadhar);
        rlLoader=v.findViewById(R.id.rlLoader);
        uuid= SharedPreference.get("uuid");
        getProfileDetails(uuid);
        return v;
    }

    private void getProfileDetails(String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.getProfile, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("pri","profile =>>"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.i("pri","response=>"+jsonObject);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        tvName.setText(jsonObject.getString("user_name"));
                        tvAadhar.setText(jsonObject.getString("adhar"));
                        tvEmail.setText(jsonObject.getString("email"));
                        tvPan.setText(jsonObject.getString("pan"));
                        tvDate.setText(jsonObject.getString("joining_date"));
                        tvPhone.setText(jsonObject.getString("mobile"));
                    }else {
                        rlLoader.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    rlLoader.setVisibility(View.GONE);
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                rlLoader.setVisibility(View.GONE);
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