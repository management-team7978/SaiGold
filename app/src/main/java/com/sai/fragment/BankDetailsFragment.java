package com.sai.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

public class BankDetailsFragment extends Fragment {

    EditText edtHolderName,edtBankName,edtIcode,edtAccNumber;
    String uuid="";
    TextView tvSubmit;
    RelativeLayout rlLoader;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v= inflater.inflate(R.layout.fragment_bank_details, container, false);
        edtHolderName=v.findViewById(R.id.edtHolderName);
        edtBankName=v.findViewById(R.id.edtBankName);
        edtIcode=v.findViewById(R.id.edtIcode);
        edtAccNumber=v.findViewById(R.id.edtAccNumber);
        tvSubmit=v.findViewById(R.id.tvSubmit);
        uuid= SharedPreference.get("uuid");
        rlLoader=v.findViewById(R.id.rlLoader);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String hName=edtHolderName.getText().toString();
                String bName=edtBankName.getText().toString();
                String iCode=edtIcode.getText().toString();
                String aNumber=edtAccNumber.getText().toString();

                if (hName.equals("")||bName.equals("")||iCode.equals("")||aNumber.equals("")){
                    Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    addBankDetails(uuid,hName,bName,iCode,aNumber);
                }
            }
        });

        getBankDetails(SharedPreference.get("uuid"));


        return v;
    }

    private void getBankDetails(String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.getbank, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("pri","get bank =>>"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        edtAccNumber.setText(jsonObject.getString("account_no"));
                        edtBankName.setText(jsonObject.getString("bank_name"));
                        edtHolderName.setText(jsonObject.getString("holder_name"));
                        edtIcode.setText(jsonObject.getString("ifcs"));
                        tvSubmit.setText("Update");
                    }else {
                        rlLoader.setVisibility(View.GONE);
                        clear();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    rlLoader.setVisibility(View.GONE);
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
                Log.i("pri",""+params);
                return  params;
            }
        };
        AppController.getInstance().add(request);
    }

    private void addBankDetails(String uuid, String hName, String bName, String iCode, String aNumber) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.addbankdetails, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("register","add bank details =>>"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        getBankDetails(uuid);
                    }else {
                        rlLoader.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    rlLoader.setVisibility(View.GONE);
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
                params.put("hname",hName);
                params.put("bname",bName);
                params.put("anumber",aNumber);
                params.put("icode",iCode);

                Log.i("pri",""+params);
                return  params;
            }
        };
        AppController.getInstance().add(request);
    }

    private void clear() {
        edtBankName.setText("");
        edtAccNumber.setText("");
        edtHolderName.setText("");
        edtIcode.setText("");
    }
}