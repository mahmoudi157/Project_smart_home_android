package com.smart.home;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;

public class SignupActivity extends AppCompatActivity {

    private HashMap<String, Object> map = new HashMap<>();


    private LinearLayout linear12_name;
    private LinearLayout linear14_email;
    private LinearLayout linear6_pass;
    private EditText name;
    private EditText email;
    private EditText pass;
    private Button kayit_button;

    private Intent i = new Intent();
    private RequestNetwork Request;
    private RequestNetwork.RequestListener _Request_request_listener;
    private SharedPreferences loginData;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_signup);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        linear12_name = findViewById(R.id.linear12_name);
        linear14_email = findViewById(R.id.linear14_email);
        linear6_pass = findViewById(R.id.linear6_pass);
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        pass = findViewById(R.id.pass);

        kayit_button = findViewById(R.id.kayit_button);
        Request = new RequestNetwork(this);
        loginData = getSharedPreferences("loginData", Activity.MODE_PRIVATE);

        kayit_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                map = new HashMap<>();
                map.put("username", name.getText().toString().trim());
                map.put("email", email.getText().toString().trim());
                map.put("password", pass.getText().toString().trim());
                Request.setParams(map, RequestNetworkController.REQUEST_BODY);
                Request.startRequestNetwork(RequestNetworkController.POST, "https://smart-home-53xp.onrender.com/api/auth/signup", "signup", _Request_request_listener);
                _Loading(true);
            }
        });

        _Request_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                if (_tag.equals("signup")) {
                    _Loading(false);
                    try{
                        map = new Gson().fromJson(_response, new TypeToken<HashMap<String, Object>>(){}.getType());
                        if (map.containsKey("accessToken")) {
                            loginData.edit().putString("userid", map.get("userId").toString()).commit();
                            loginData.edit().putString("token", map.get("accessToken").toString()).commit();
                            i.setClass(getApplicationContext(), MainActivity.class);
                            startActivity(i);
                            finish();
                        }
                        else {

                        }
                    }catch(Exception e){

                    }
                }
                else {

                }
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;

            }
        };
    }

    private void initializeLogic() {
        _rippleRoundStroke(linear12_name, "#ffffff", "#FFF0F0F0", 100, 3, "#673ab7");
        _rippleRoundStroke(linear14_email, "#ffffff", "#FFF0F0F0", 100, 3, "#673ab7");
        _rippleRoundStroke(linear6_pass, "#ffffff", "#FFF0F0F0", 100, 3, "#673ab7");
        _rippleRoundStroke(kayit_button, "#689F38", "#7e57c2", 100, 0, "#673ab7");
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        super.onBackPressed();
        i.setClass(getApplicationContext(), MainActivity.class);
        startActivity(i);
        finish();
    }
    public void _rippleRoundStroke(final View _view, final String _focus, final String _pressed, final double _round, final double _stroke, final String _strokeclr) {
        android.graphics.drawable.GradientDrawable GG = new android.graphics.drawable.GradientDrawable();
        GG.setColor(Color.parseColor(_focus));
        GG.setCornerRadius((float)_round);
        GG.setStroke((int) _stroke,
                Color.parseColor("#" + _strokeclr.replace("#", "")));
        android.graphics.drawable.RippleDrawable RE = new android.graphics.drawable.RippleDrawable(new android.content.res.ColorStateList(new int[][]{new int[]{}}, new int[]{ Color.parseColor("#FF757575")}), GG, null);
        _view.setBackground(RE);
    }


    public void _SX_CornerRadius_4(final View _view, final String _color1, final String _color2, final double _str, final double _n1, final double _n2, final double _n3, final double _n4) {
        android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();

        gd.setColor(Color.parseColor(_color1));

        gd.setStroke((int)_str, Color.parseColor(_color2));

        gd.setCornerRadii(new float[]{(int)_n1,(int)_n1,(int)_n2,(int)_n2,(int)_n3,(int)_n3,(int)_n4,(int)_n4});

        _view.setBackground(gd);

        _view.setElevation(8);
    }


    public void _Loading(final boolean _Loading) {
        if (_Loading) {
            if (coreprog == null){
                coreprog = new ProgressDialog(this);
                coreprog.setCancelable(false);
                coreprog.setCanceledOnTouchOutside(false);

                coreprog.requestWindowFeature(Window.FEATURE_NO_TITLE);  coreprog.getWindow().setBackgroundDrawable(new android.graphics.drawable.ColorDrawable(Color.TRANSPARENT));

            }
            coreprog.show();
            coreprog.setContentView(R.layout.loading);


            LinearLayout ProgBG = (LinearLayout)coreprog.findViewById(R.id.ProgBG);

            LinearLayout back = (LinearLayout)coreprog.findViewById(R.id.BG);

            android.graphics.drawable.GradientDrawable gd = new android.graphics.drawable.GradientDrawable();
            gd.setColor(Color.parseColor("#FFFFFF")); /* color */
            gd.setCornerRadius(40); /* radius */
            gd.setStroke(0, Color.WHITE); /* stroke heigth and color */
            ProgBG.setBackground(gd);
        }
        else {
            if (coreprog != null){
                coreprog.dismiss();
            }
        }
    }
    private ProgressDialog coreprog;
    {
    }


    @Deprecated
    public void showMessage(String _s) {
        Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
    }





    @Deprecated
    public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
        ArrayList<Double> _result = new ArrayList<Double>();
        SparseBooleanArray _arr = _list.getCheckedItemPositions();
        for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
            if (_arr.valueAt(_iIdx))
                _result.add((double)_arr.keyAt(_iIdx));
        }
        return _result;
    }


}