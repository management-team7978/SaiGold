package com.sai.fragment;

import android.content.Intent;
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
import com.sai.gold.LoginScreenActivity;
import com.sai.gold.R;
import com.sai.util.AppController;
import com.sai.util.Keys;
import com.sai.util.SharedPreference;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordFragment extends Fragment {
    EditText edtOldPass,edtNewPass,edtConfirmPass;
    TextView tvSubmit;
    String uuid;
    RelativeLayout rlLoader;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_change_password, container, false);
//        FirebaseAnalytics.getInstance(getActivity());
//        FirebaseCrashlytics.getInstance().setCrashlyticsCollectionEnabled(true);
        edtConfirmPass=v.findViewById(R.id.edtConfirmPass);
        edtOldPass=v.findViewById(R.id.edtOldPass);
        edtNewPass=v.findViewById(R.id.edtNewPass);
        tvSubmit=v.findViewById(R.id.tvSubmit);
        uuid= SharedPreference.get("uuid");
        rlLoader=v.findViewById(R.id.rlLoader);

        tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPass=edtOldPass.getText().toString().trim();
                String newPass=edtNewPass.getText().toString().trim();
                String confirmPass=edtConfirmPass.getText().toString().trim();

                if (oldPass.equals("")||newPass.equals("")||confirmPass.equals("")){
                    Toast.makeText(getActivity(), "Please fill all the fields", Toast.LENGTH_SHORT).show();
                }else {
                    userChangePassword(uuid,oldPass,newPass,confirmPass);
                }

            }
        });
        return v;
    }

    private void userChangePassword(String uuid, String oldPass, String newPass, String confirmPass) {
        rlLoader.setVisibility(View.VISIBLE);
        StringRequest request=new StringRequest(Request.Method.POST, Keys.URL.changePassword, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("Change Pass","changePass =>>"+response);
                try {
                    JSONObject jsonObject=new JSONObject(response);
                    Log.i("Change Pass","response=>"+jsonObject);
                    if (jsonObject.getString("status").equals("true")){
                        rlLoader.setVisibility(View.GONE);
                        Toast.makeText(getActivity(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(getActivity(), LoginScreenActivity.class);
                        startActivity(intent);

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
                params.put("old_password",oldPass);
                params.put("new_password",newPass);
                params.put("confirm_password",confirmPass);

                Log.i("change pass","uuid=>"+uuid+" old_password=>"+oldPass+" confirm_password=>"+confirmPass+" new_password=>"+newPass);

                return  params;
            }
        };
        AppController.getInstance().add(request);
    }
}