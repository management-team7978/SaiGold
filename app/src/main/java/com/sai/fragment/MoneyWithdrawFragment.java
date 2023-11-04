package com.sai.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

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
import com.sai.gold.LoginScreenActivity;
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


public class MoneyWithdrawFragment extends Fragment {
    Spinner spSelectWallet;
    String uuid="";
    TextView tvMainBalance,tvAdminCharge,tvMinWithdraw,tvMaxWithdraw,tvSubmit,tvTitle,tvNotApplicableMessage;
    RelativeLayout rlLoader;
    String sp_wallet="",fund_balance="",main_balance="",date="";
    CardView cardform,cardNotKYC,cardNotEligible;
    EditText edAmount;
    SwipeRefreshLayout swipeRefresh;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_money_withdraw, container, false);
//        FirebaseAnalytics.getInstance(getActivity());
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        tvMainBalance=v.findViewById(R.id.tvMainBalance);
        tvAdminCharge=v.findViewById(R.id.tvAdminCharge);
        tvMinWithdraw=v.findViewById(R.id.tvMinWithdraw);
        tvMaxWithdraw=v.findViewById(R.id.tvMaxWithdraw);
        edAmount=v.findViewById(R.id.edAmount);
        rlLoader=v.findViewById(R.id.rlLoader);
        tvSubmit=v.findViewById(R.id.tvSubmit);
        tvTitle=v.findViewById(R.id.tvTitle);
        tvNotApplicableMessage=v.findViewById(R.id.tvNotApplicableMessage);
        swipeRefresh=v.findViewById(R.id.swipeRefresh);
        cardform=v.findViewById(R.id.cardform);
        cardNotKYC=v.findViewById(R.id.cardNotKYC);
        cardNotEligible=v.findViewById(R.id.cardNotEligible);

        uuid= SharedPreference.get("uuid");

        getFunMainBalance(uuid);
        getBankDetails(uuid);

        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.black));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFunMainBalance(uuid);
                getBankDetails(uuid);
            }
        });

        spSelectWallet=v.findViewById(R.id.spSelectWallet);
        // Spinner Drop down elements
        List<String> categories = new ArrayList<String>();
        categories.add("Main Balance");
        categories.add("Fund Balance");

        spSelectWallet.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.custom_spinner_list, categories));

        spSelectWallet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (((String) parent.getSelectedItem()).equals("Main Balance")){
                    sp_wallet="main_balance";
                    Log.i("pri","wallet=>"+sp_wallet);
                    tvTitle.setText("AVAILABLE BALANCE");
                    tvMainBalance.setText("Main Balance: "+main_balance);

                }else if (((String) parent.getSelectedItem()).equals("Fund Balance")){
                    sp_wallet="fund_wallet";
                    tvTitle.setText("FUND BALANCE");
                    Log.i("pri","wallet=>"+sp_wallet);
                    tvMainBalance.setText("Fund Balance: "+fund_balance);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String amount = edAmount.getText().toString().trim();
                if (sp_wallet.equals("")||amount.equals("")){
                    Toast.makeText(getActivity(), "Please select wallet type and amount", Toast.LENGTH_SHORT).show();
                }else{
                    AddMoneyWithdrawRequest(uuid,amount,sp_wallet);
                }
            }
        });


//        Date todaysdate = new Date();
//        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        String fulldate = format.format(todaysdate);
//        date = fulldate.substring(fulldate.lastIndexOf("-")+1);
//
//        Log.i("prrri","lastWord=>"+date);

        return v;
    }

    private void getDateList() {
        swipeRefresh.setRefreshing(false);
        StringRequest request = new StringRequest(Request.Method.POST, Keys.URL.withdrawaldates, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("prrri", "Date List=>" + response);
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")) {
                        cardNotEligible.setVisibility(View.GONE);
                        cardform.setVisibility(View.VISIBLE);

//                        JSONArray dataArray  = jsonObject.getJSONArray("data");
//
//                        ArrayList<String> datelist = new ArrayList<>();
//
//                        for (int i = 0; i < dataArray.length(); i++) {
//                            datelist.add(String.valueOf(dataArray.getInt(i)));
//                            Log.i("prrri"," date list"+datelist.get(i));
//                        }
//                        Log.i("prrri","current date"+date);
//                        if (datelist.contains(date)){
//                            cardform.setVisibility(View.VISIBLE);
//                            cardNotEligible.setVisibility(View.GONE);
//                            Log.i("prrri","date is present ");
                    }else {
                        cardform.setVisibility(View.GONE);
                        cardNotEligible.setVisibility(View.VISIBLE);
                        tvNotApplicableMessage.setText(jsonObject.getString("message"));
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

    private void AddMoneyWithdrawRequest(String uuid, String amount, String sp_wallet) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.moneywithdraw, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("register","add money =>>"+response);

                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        edAmount.setText("");
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        getDateList();
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
                //params.put("uuid",uuid);
                params.put("uuid",uuid);
                params.put("amount",amount);
                params.put("wallet_type",sp_wallet);

                Log.i("pri","uuid=>"+uuid);
                Log.i("pri","amount=>"+amount);
                Log.i("pri","wakllet=>"+sp_wallet);
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
                swipeRefresh.setRefreshing(false);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.i("pri","response=>"+jsonObject);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        JSONArray dataArray  = jsonObject.getJSONArray("data");
                        for (int i = 0; i < dataArray.length(); i++) {
                            JSONObject dataobj = dataArray.getJSONObject(i);
                            Log.i("pri","dataobj"+dataobj);
                            tvAdminCharge.setText("Admin Charge: "+dataobj.getString("admin_charge"));
                            tvMainBalance.setText("Main Balance: "+dataobj.getString("earnwallet"));
                            tvMaxWithdraw.setText("Maximum Withdraw: "+dataobj.getString("max_withdraw"));
                            tvMinWithdraw.setText("Minimum Withdraw: "+dataobj.getString("min_withdraw"));

                            fund_balance=dataobj.getString("fundwallet");
                            main_balance=dataobj.getString("earnwallet");
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


    private void getBankDetails(String uuid) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.getbank, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("pri","get bank =>>"+response);
                swipeRefresh.setRefreshing(false);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        getDateList();
                    }else {
                        rlLoader.setVisibility(View.GONE);
                        cardform.setVisibility(View.GONE);
                        cardNotEligible.setVisibility(View.GONE);
                        cardNotKYC.setVisibility(View.VISIBLE);

                        if (jsonObject.getString("message").equalsIgnoreCase("uuid missmatch logout")) {
                            if (SharedPreference.contains("uuid")) {
                                SharedPreference.removeKey("uuid");
                            }
                            Intent i = new Intent(getActivity(), LoginScreenActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }
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
}