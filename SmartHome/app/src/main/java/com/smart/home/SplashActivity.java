package com.smart.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

public class SplashActivity extends AppCompatActivity {

    private Timer _timer = new Timer();

    private HashMap<String, Object> m = new HashMap<>();


    private RequestNetwork net;
    private RequestNetwork.RequestListener _net_request_listener;
    private Intent i = new Intent();
    private TimerTask t;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_splash);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {

        net = new RequestNetwork(this);

        _net_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                i.setClass(getApplicationContext(), MainActivity.class);
                startActivity(i);
                finish();
            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {

                net.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "", _net_request_listener);
            }
        };
    }

    private void initializeLogic() {
        net.startRequestNetwork(RequestNetworkController.POST, "https://google.com", "", _net_request_listener);
    }

    public void _ClickEffect(final View _view) {
        TypedValue typedValue = new TypedValue();

        getApplicationContext().getTheme().resolveAttribute(16843868, typedValue, true);

        _view.setBackgroundResource(typedValue.resourceId);

        _view.setClickable(true);
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