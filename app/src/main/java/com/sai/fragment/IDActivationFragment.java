package com.sai.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class IDActivationFragment extends Fragment {
    Spinner spSelectWallet,spSelectAmount;
    TextView tvFundBalance,tvMainBalance,tvAdminCharge;
    String sp_amount_value="",uuid="",sp_wallet="";
    ArrayList<String> amount=new ArrayList<String>();
    TextView tvSubmit;
    EditText edtUserId,edtUserName;
    RelativeLayout rlLoader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_i_d_activation, container, false);

        spSelectWallet=v.findViewById(R.id.spSelectWallet);
        spSelectAmount=v.findViewById(R.id.spAmount);
        tvMainBalance=v.findViewById(R.id.tvMainBalance);
        tvFundBalance=v.findViewById(R.id.tvFundBalance);
        tvSubmit=v.findViewById(R.id.tvSubmit);
        edtUserId=v.findViewById(R.id.edtUserId);
        edtUserName=v.findViewById(R.id.edtUserName);
        rlLoader=v.findViewById(R.id.rlLoader);
        tvAdminCharge=v.findViewById(R.id.tvAdminCharge);

        uuid= SharedPreference.get("uuid");
        getFunMainBalance(SharedPreference.get("uuid"));
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Main Balance");
        //categories.add("Fund Balance");

        getPackageList(uuid);
        spSelectWallet.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_list, categories));


        spSelectAmount.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sp_amount_value= (String) parent.getSelectedItem();
                Log.i("pri","my model=>"+sp_amount_value);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spSelectWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (((String) parent.getSelectedItem()).equals("Main Balance")){
                    sp_wallet="main_balance";
                    Log.i("pri","wallet=>"+sp_wallet);
                }else if (((String) parent.getSelectedItem()).equals("Fund Balance")){
                    sp_wallet="fund_wallet";
                    Log.i("pri","wallet=>"+sp_wallet);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        edtUserId.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                getUsername(edtUserId.getText().toString());
            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String uName=edtUserName.getText().toString().trim();
                String uid=edtUserId.getText().toString().trim();
                if (uid.equals("")){
                    edtUserId.setError("please enter User id");
                    Toast.makeText(getActivity(), "Please enter User Id", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (uName.equals("")){
                        edtUserName.setError("please enter correct user Id");
                        Toast.makeText(getActivity(), "Please correct enter User Id", Toast.LENGTH_SHORT).show();
                    }else {
                        submitActivation(uid,sp_amount_value,sp_wallet,uuid);
                    }

                }
            }
        });
        return v;
    }

    private void submitActivation(String uid, String sp_amount_value, String sp_wallet,String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.idactivator, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("register","add Activation =>>"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        getFunMainBalance(uuid);
                        clear();
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
                params.put("uid",uid);
                params.put("amount",sp_amount_value);
                params.put("wallet_type",sp_wallet);

                Log.i("pri","params=>>> "+params);
                return  params;
            }
        };
        AppController.getInstance().add(request);
    }

    private void clear() {
        edtUserId.setText("");
        edtUserName.setText("");
    }

    private void getPackageList(String uuid) {
        StringRequest request = new StringRequest(Request.Method.POST, Keys.URL.packages, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("pri", "Drop down List=>" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {

                        JSONArray dataArray  = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            Log.i("pri","dataobj"+dataobj);
                            String amount1=dataobj.getString("amount");
                            amount.add(amount1);
                            spSelectAmount.setAdapter(new ArrayAdapter<String>(getActivity(),R.layout.custom_spinner_list, amount));

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //rl_progress.setVisibility(View.VISIBLE);
                error.printStackTrace();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("uuid",uuid);

                return  params;
            }

        };

        AppController.getInstance().add(request);
    }

    private void getFunMainBalance(String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.getalllimitsbal, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("pri","get fund main balance =>>"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.i("pri","response=>"+jsonObject);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        JSONArray dataArray  = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            Log.i("pri","dataobj"+dataobj);
                            tvFundBalance.setText(dataobj.getString("fundwallet"));
                            tvMainBalance.setText(dataobj.getString("earnwallet"));
                            tvAdminCharge.setText(dataobj.getString("admin_charge"));
                        }
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

                return  params;
            }
        };
        AppController.getInstance().add(request);
    }

    private void getUsername(String s) {
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.getusername, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("register","register =>>"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.i("register","response=>"+jsonObject);
                    if (jsonObject.getString("status").equals("true")){
                        edtUserName.setText(jsonObject.getString("username"));
                    }else {
                        edtUserName.setText("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("user_id",s);

                return  params;
            }
        };
        AppController.getInstance().add(request);
    }
}