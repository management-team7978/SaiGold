package com.sai.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sai.adapter.IndirectReferralAdapter;
import com.sai.gold.LoginScreenActivity;
import com.sai.gold.R;
import com.sai.listener.IndirectReferralListener;
import com.sai.pojo.IndirectReferral;
import com.sai.util.AppController;
import com.sai.util.Keys;
import com.sai.util.SharedPreference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class IndirectReferralFragment extends Fragment {
    RecyclerView recyclerIndirectReferral;
    IndirectReferralAdapter indirectReferralAdapter;
    ArrayList<IndirectReferral> indirectReferrals;
    RelativeLayout rlNotFound,rlLoader;
    SwipeRefreshLayout swipeRefresh;
    String uuid="";
    RelativeLayout rlNoDataTwo;
    //    TextView tvNoDataMessage;
    //  AppCompatButton buttonMainList;
    LinearLayout lllevels;
    TextView tvlvlone,tvlvltwo,tvlvlthree,tvlvlfour,tvlvlfive,tvlvlsix,tvlvlseven,tvlvleight;
    String val="0";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_indirect_referral, container, false);
//        FirebaseAnalytics.getInstance(getActivity());
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        recyclerIndirectReferral=v.findViewById(R.id.recyclerIndirectReferral);
        swipeRefresh=v.findViewById(R.id.swipeRefresh);
        rlLoader=v.findViewById(R.id.rlLoader);
        rlNotFound=v.findViewById(R.id.rlNotFound);
//        rlNoData=v.findViewById(R.id.relativeLayoutInDirectRefeeralNoData);
        rlNoDataTwo=v.findViewById(R.id.relativeLayoutInDirectRefeeralNoDatatwo);
//        tvNoDataMessage=v.findViewById(R.id.textViewinDirectReferralMessage);
        // buttonMainList=v.findViewById(R.id.buttonInDirectRefferralnodata);
        tvlvlone=v.findViewById(R.id.tvlvlone);
        tvlvltwo=v.findViewById(R.id.tvlvltwo);
        tvlvlthree=v.findViewById(R.id.tvlvlthree);
        tvlvlfour=v.findViewById(R.id.tvlvlfour);
        tvlvlfive=v.findViewById(R.id.tvlvlfive);
        tvlvlsix=v.findViewById(R.id.tvlvlsix);
        tvlvlseven=v.findViewById(R.id.tvlvlseven);
        tvlvleight=v.findViewById(R.id.tvlvleight);
//        tvlvlnine=v.findViewById(R.id.tvlvlnine);
//        tvlvlten=v.findViewById(R.id.tvlvlten);
//        tvlvleleven=v.findViewById(R.id.tvlvleleven);
//        tvlvltwelve=v.findViewById(R.id.tvlvltwelve);
        lllevels=v.findViewById(R.id.lllevels);

        recyclerIndirectReferral.setLayoutManager(new LinearLayoutManager(getActivity()));

        indirectReferrals=new ArrayList<IndirectReferral>();
        recyclerIndirectReferral.setHasFixedSize(true);


        if (SharedPreference.contains("indirect")){
            SharedPreference.removeKey("indirect");
        }

        if (SharedPreference.contains("uuid")) {
            uuid= SharedPreference.get("uuid");
            getinDirectReferral(uuid,SharedPreference.get("userid"),"1");
            Log.i("priyu","uuid=?>>>"+uuid);
        }else {
            Intent i=new Intent(getActivity(), LoginScreenActivity.class);
            startActivity(i);
            getActivity().finish();
        };

        // getIndirectReferal(uuid);
        swipeRefresh.setColorSchemeColors(getResources().getColor(R.color.black));
        swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
//                indirectReferrals.clear();
//                getIndirectReferal(uuid);

                if (SharedPreference.contains("uuid")) {
//                    LayoutAnimationController anim = AnimationUtils.loadLayoutAnimation(getActivity(), R.anim.layout_animation_right_to_left);
//                    recyclerView.setLayoutAnimation(anim);
                    if (SharedPreference.contains("indirect")){
                        indirectReferrals.clear();
                        getinDirectReferral(SharedPreference.get("uuid"),SharedPreference.get("lvl"+val+"user"),"2");
                    }
                    else {
                        indirectReferrals.clear();
                        getinDirectReferral(SharedPreference.get("uuid"),"1",SharedPreference.get("userid"));
                    }
                }else {
                    Intent i=new Intent(getActivity(), LoginScreenActivity.class);
                    startActivity(i);
                    getActivity().finish();
                }
            }

        });

        IndirectReferralAdapter.bindListener(new IndirectReferralListener() {
            @Override
            public void click(String userid1) {
                getinDirectReferral(SharedPreference.get("uuid"),userid1,"0");
            }
        });

        tvlvlone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("priyu","btn levl 1");
                SharedPreference.save("indirect","0");
                getinDirectReferral(SharedPreference.get("uuid"),SharedPreference.get("lvl1user"),"0");
            }
        });
        tvlvltwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference.save("indirect","1");
                getinDirectReferral(SharedPreference.get("uuid"),SharedPreference.get("lvl2user"),"0");
            }
        });
        tvlvlthree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference.save("indirect","2");
                getinDirectReferral(SharedPreference.get("uuid"),SharedPreference.get("lvl3user"),"0");
            }
        });
        tvlvlfour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference.save("indirect","3");
                getinDirectReferral(SharedPreference.get("uuid"),SharedPreference.get("lvl4user"),"0");
            }
        });
        tvlvlfive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference.save("indirect","4");
                getinDirectReferral(SharedPreference.get("uuid"),SharedPreference.get("lvl5user"),"0");
            }
        });
        tvlvlsix.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference.save("indirect","5");
                getinDirectReferral(SharedPreference.get("uuid"),SharedPreference.get("lvl6user"),"0");
            }
        });
        tvlvlseven.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference.save("indirect","6");
                getinDirectReferral(SharedPreference.get("uuid"),SharedPreference.get("lvl7user"),"0");
            }
        });
        tvlvleight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreference.save("indirect","7");
                getinDirectReferral(SharedPreference.get("uuid"),SharedPreference.get("lvl8user"),"0");
            }
        });


        return v;
    }

    private void getinDirectReferral(String uuid,String userid,String type) {
        Log.i("priyu","btn levl 1 meth");
        indirectReferrals.clear();
        recyclerIndirectReferral.setVisibility(View.GONE);
        rlNoDataTwo.setVisibility(View.GONE);
        rlNotFound.setVisibility(View.GONE);
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.getIndirect_referral, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                rlLoader.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                Log.i("priyu","btn levl 1 meth req");
                Log.i("priyu","Dashboard=>"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    if (jsonObject.getBoolean("status")){
                        if (type.equals("0") || type.equals("1")) {
                            if (SharedPreference.contains("indirect")) {
                                int y = Integer.parseInt(SharedPreference.get("indirect")) + 1;
                                SharedPreference.save("indirect", y);
                                String x = "lvl" + y + "user";
                                SharedPreference.save(x, userid);
                            } else {
                                SharedPreference.save("indirect", 1);
                                SharedPreference.save("lvl1user", userid);
                            }
                            if (SharedPreference.contains("indirect")) {
                                hiddenparts();
                            }
                        }
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject1 =jsonArray.getJSONObject(i);
                            indirectReferrals.add(new IndirectReferral(
                                    jsonObject1.getString("user_id"),
                                    jsonObject1.getString("user_name"),
                                    jsonObject1.getString("joining_date"),
                                    jsonObject1.getString("package_amount"),
                                    jsonObject1.getString("activation_status_code"),
                                    jsonObject1.getString("activation_status")));
                        }
                    }else {

                        if (jsonObject.getString("message").equals("uuid missmatch logout")){
                            SharedPreference.removeKey("uuid");
                            SharedPreference.removeKey("userid");
                            Intent i=new Intent(getActivity(),LoginScreenActivity.class);
                            startActivity(i);
                            getActivity().finish();
                        }else {
                            if (type.equals("0")){
                                if (SharedPreference.contains("indirect")) {
                                    int y = Integer.parseInt(SharedPreference.get("indirect")) + 1;
                                    SharedPreference.save("indirect", y);
                                    String x = "lvl" + y + "user";
                                    SharedPreference.save(x, uuid);
                                } else {
                                    SharedPreference.save("indirect", 1);
                                    SharedPreference.save("lvl1user", uuid);
                                }
                                if (SharedPreference.contains("indirect")) {
                                    hiddenparts();
                                }
//                                tvNoDataMessage.setText("No direct referral for selected user");
//                                buttonMainList.setVisibility(View.VISIBLE);
                                rlNotFound.setVisibility(View.GONE);
                                rlNoDataTwo.setVisibility(View.VISIBLE);

                            }else {
                                if (SharedPreference.contains("indirect")){
                                    SharedPreference.removeKey("indirect");
                                }
//                                tvNoDataMessage.setText("No direct referral");
//                                buttonMainList.setVisibility(View.GONE);
                                rlNotFound.setVisibility(View.VISIBLE);
                                rlNoDataTwo.setVisibility(View.GONE);
                            }

                        }
                    }

                    recyclerIndirectReferral.setVisibility(View.VISIBLE);
                    indirectReferralAdapter = new IndirectReferralAdapter(getActivity(),indirectReferrals);
                    indirectReferralAdapter.notifyDataSetChanged();
                    recyclerIndirectReferral.setAdapter(indirectReferralAdapter);


                } catch (JSONException e) {
                    e.printStackTrace();
                    swipeRefresh.setRefreshing(false);
                    Toast.makeText(getActivity(), "Technical problem arises", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                rlLoader.setVisibility(View.GONE);
                swipeRefresh.setRefreshing(false);
                // showDialog("Please check your Internet connection");
            }


        }
        ) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("uuid",uuid);
                params.put("userid",userid);
                //params.put("userid",SharedPreference.get("uuid"));
                Log.i("pri","uuid=>>>>"+uuid);
                Log.i("pri","userid=>>>>"+userid);
                return  params;
            }
        };
        AppController.getInstance().add(request);
    }

    private void hiddenparts() {
        lllevels.setVisibility(View.VISIBLE);
        int h=R.drawable.viewlevelripple;
        int s=R.drawable.levelback;
        int v = View.VISIBLE;
        int g = View.GONE;
        if (SharedPreference.get("indirect").equals("1")) {
            val = "1";
            markback(s,h,h,h,h,h,h,h,h,h,h,h);
            hidepart(v,g,g,g,g,g,g,g,g,g,g,g);
        }
        else if (SharedPreference.get("indirect").equals("2")){
            val = "2";
            markback(h,s,h,h,h,h,h,h,h,h,h,h);
            hidepart(v,v,g,g,g,g,g,g,g,g,g,g);
        }
        else if (SharedPreference.get("indirect").equals("3")){
            val = "3";
            markback(h,h,s,h,h,h,h,h,h,h,h,h);
            hidepart(v,v,v,g,g,g,g,g,g,g,g,g);
        }
        else if (SharedPreference.get("indirect").equals("4")){
            val = "4";
            markback(h,h,h,s,h,h,h,h,h,h,h,h);
            hidepart(v,v,v,v,g,g,g,g,g,g,g,g);
        }
        else if (SharedPreference.get("indirect").equals("5")){
            val = "5";
            markback(h,h,h,h,s,h,h,h,h,h,h,h);
            hidepart(v,v,v,v,v,g,g,g,g,g,g,g);
        }
        else if (SharedPreference.get("indirect").equals("6")){
            val = "6";
            markback(h,h,h,h,h,s,h,h,h,h,h,h);
            hidepart(v,v,v,v,v,v,g,g,g,g,g,g);
        }
        else if (SharedPreference.get("indirect").equals("7")){
            val = "7";
            markback(h,h,h,h,h,h,s,h,h,h,h,h);
            hidepart(v,v,v,v,v,v,v,g,g,g,g,g);
        }
        else {
            val = "8";
            markback(h,h,h,h,h,h,h,s,h,h,h,h);
            hidepart(v,v,v,v,v,v,v,v,g,g,g,g);
        }


    }

    private void hidepart(int v, int g, int g1, int g2, int g3, int g4, int g5, int g6, int g7, int g8,int g9, int g10) {
        tvlvlone.setVisibility(v);
        tvlvltwo.setVisibility(g);
        tvlvlthree.setVisibility(g1);
        tvlvlfour.setVisibility(g2);
        tvlvlfive.setVisibility(g3);
        tvlvlsix.setVisibility(g4);
        tvlvlseven.setVisibility(g5);
        tvlvleight.setVisibility(g6);
//        tvlvlnine.setVisibility(g7);
//        tvlvlten.setVisibility(g8);
//        tvlvleleven.setVisibility(g9);
//        tvlvltwelve.setVisibility(g10);

    }

    private void markback(int s, int h, int i, int h1, int i1, int h2, int i2, int h3, int i3, int h4, int i4, int h5) {
        tvlvlone.setBackgroundResource(s);
        tvlvltwo.setBackgroundResource(h);
        tvlvlthree.setBackgroundResource(i);
        tvlvlfour.setBackgroundResource(h1);
        tvlvlfive.setBackgroundResource(i1);
        tvlvlsix.setBackgroundResource(h2);
        tvlvlseven.setBackgroundResource(i2);
        tvlvleight.setBackgroundResource(h3);
//        tvlvlnine.setBackgroundResource(i3);
//        tvlvlten.setBackgroundResource(h4);
//        tvlvleleven.setBackgroundResource(i4);
//        tvlvltwelve.setBackgroundResource(h5);

    }
}