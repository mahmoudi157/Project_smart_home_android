package com.smart.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;



public class ControleFragmentActivity extends Fragment {

    private HashMap<String, Object> etatlomp = new HashMap<>();
    private String st = "";
    private HashMap<String, Object> map = new HashMap<>();
    private String auto = "";

    private ArrayList<HashMap<String, Object>> listlamp = new ArrayList<>();


    private TextView textview6;
    private Switch switch1;
    private ImageView imagelomp1_1;
    private ImageView imagelomp1;

    private ImageView imagelomp3_3;
    private ImageView imagelomp3;

    private ImageView imagelomp2_2;
    private ImageView imagelomp2;
    private TextView textview5;
    private ImageView imagelomp4_4;
    private ImageView imagelomp4;
    private MediaPlayer m;
    private RequestNetwork Request;
    private RequestNetwork.RequestListener _Request_request_listener;
    private SharedPreferences loginData;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
        View _view = _inflater.inflate(R.layout.fragment_controle_activity, _container, false);
        initialize(_savedInstanceState, _view);
        initializeLogic();
        return _view;
    }

    private void initialize(Bundle _savedInstanceState, View _view) {

        textview6 = _view.findViewById(R.id.textview6);
        switch1 = _view.findViewById(R.id.switch1);
        imagelomp1_1 = _view.findViewById(R.id.imagelomp1_1);
        imagelomp1 = _view.findViewById(R.id.imagelomp1);

        imagelomp3_3 = _view.findViewById(R.id.imagelomp3_3);
        imagelomp3 = _view.findViewById(R.id.imagelomp3);
        imagelomp2_2 = _view.findViewById(R.id.imagelomp2_2);
        imagelomp2 = _view.findViewById(R.id.imagelomp2);
        textview5 = _view.findViewById(R.id.textview5);
        imagelomp4_4 = _view.findViewById(R.id.imagelomp4_4);
        imagelomp4 = _view.findViewById(R.id.imagelomp4);
        Request = new RequestNetwork((Activity) getContext());
        loginData = getContext().getSharedPreferences("loginData", Activity.MODE_PRIVATE);

        textview6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {

            }
        });

        switch1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                Request.setHeaders(map);
                if (switch1.isChecked()) {
                    Request.startRequestNetwork(RequestNetworkController.PUT, "https://smart-home-53xp.onrender.com/api/data/mode/true", "mode", _Request_request_listener);
                }
                else {
                    Request.startRequestNetwork(RequestNetworkController.PUT, "https://smart-home-53xp.onrender.com/api/data/mode/false", "mode", _Request_request_listener);
                }
            }
        });

        switch1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton _param1, boolean _param2) {
                final boolean _isChecked = _param2;

            }
        });

        imagelomp1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (listlamp.get((int)0).get("etat").toString().equals("1")) {
                    m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.bbb);
                    imagelomp1.setImageResource(R.drawable.icon_off);
                    imagelomp1_1.setImageResource(R.drawable.icon_1);
                    _sentlampetat("lam1", 0);
                }
                else {
                    m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.aaa);
                    imagelomp1.setImageResource(R.drawable.icon_on);
                    imagelomp1_1.setImageResource(R.drawable.icon_2);
                    _sentlampetat("lam1", 1);
                }
            }
        });

        imagelomp3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (listlamp.get((int)2).get("etat").toString().equals("1")) {
                    m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.bbb);
                    _sentlampetat("lam3", 0);
                    imagelomp3.setImageResource(R.drawable.icon_off);
                    imagelomp3_3.setImageResource(R.drawable.icon_1);
                }
                else {
                    m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.aaa);
                    _sentlampetat("lam3", 1);
                    imagelomp3.setImageResource(R.drawable.icon_on);
                    imagelomp3_3.setImageResource(R.drawable.icon_2);
                }
            }
        });

        imagelomp2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (listlamp.get((int)1).get("etat").toString().equals("1")) {
                    m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.bbb);
                    _sentlampetat("lam2", 0);
                    imagelomp2.setImageResource(R.drawable.icon_off);
                    imagelomp2_2.setImageResource(R.drawable.icon_1);
                }
                else {
                    m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.aaa);
                    _sentlampetat("lam2", 1);
                    imagelomp2.setImageResource(R.drawable.icon_on);
                    imagelomp2_2.setImageResource(R.drawable.icon_2);
                }
            }
        });

        imagelomp4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View _view) {
                if (listlamp.get((int)3).get("etat").toString().equals("1")) {
                    m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.bbb);
                    _sentlampetat("lam4", 0);
                    imagelomp4.setImageResource(R.drawable.icon_on);
                    imagelomp4_4.setImageResource(R.drawable.icon_1);
                }
                else {
                    m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.aaa);
                    imagelomp4.setImageResource(R.drawable.icon_off);
                    imagelomp4_4.setImageResource(R.drawable.icon_2);
                    _sentlampetat("lam4", 1);
                }
            }
        });

        _Request_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    auto = _response;
                    if (_tag.equals("mode")) {
                        switch1.setChecked(_response.equals("true"));
                        if (_response.equals("true")) {

                        }
                        else {

                        }
                    }
                    else {
                        if (_tag.equals("etatlomp")) {
                            listlamp = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                            if (listlamp.size() > 0) {
                                _setmodelamp(Double.parseDouble(listlamp.get((int)0).get("etat").toString()), Double.parseDouble(listlamp.get((int)1).get("etat").toString()), Double.parseDouble(listlamp.get((int)2).get("etat").toString()), Double.parseDouble(listlamp.get((int)3).get("etat").toString()));
                            }
                        }
                        else {
                            if (_tag.equals("changlamp")) {
                                Request.startRequestNetwork(RequestNetworkController.GET, "https://smart-home-53xp.onrender.com/api/data/lamp/", "etatlomp", _Request_request_listener);
                            }
                            else {

                            }
                        }
                    }
                }catch(Exception e){

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
        map = new HashMap<>();
        map.put("Authorization", "Bearer ".concat(loginData.getString("token", "")));
        Request.setHeaders(map);
        Request.startRequestNetwork(RequestNetworkController.GET, "https://smart-home-53xp.onrender.com/api/data/mode/", "mode", _Request_request_listener);
        Request.startRequestNetwork(RequestNetworkController.GET, "https://smart-home-53xp.onrender.com/api/data/lamp/", "etatlomp", _Request_request_listener);
        etatlomp = new HashMap<>();
        etatlomp.put("lomp1", "1");
        etatlomp.put("lomp2", "1");
        etatlomp.put("lomp3", "1");
        etatlomp.put("lomp4", "1");
    }

    public void _setmodelamp(final double _lam1, final double _lam2, final double _lam3, final double _lam4) {
        if (_lam1 == 1) {
            m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.bbb);
            imagelomp1.setImageResource(R.drawable.icon_off);
            imagelomp1_1.setImageResource(R.drawable.icon_1);
        }
        else {
            m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.aaa);
            imagelomp1.setImageResource(R.drawable.icon_on);
            imagelomp1_1.setImageResource(R.drawable.icon_2);
        }
        if (_lam2 == 1) {
            m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.bbb);
            imagelomp2.setImageResource(R.drawable.icon_off);
            imagelomp2_2.setImageResource(R.drawable.icon_1);
        }
        else {
            m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.aaa);
            imagelomp2.setImageResource(R.drawable.icon_on);
            imagelomp2_2.setImageResource(R.drawable.icon_2);
        }
        if (_lam3 == 1) {
            m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.bbb);
            imagelomp3.setImageResource(R.drawable.icon_off);
            imagelomp3_3.setImageResource(R.drawable.icon_1);
        }
        else {
            m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.aaa);
            imagelomp3.setImageResource(R.drawable.icon_on);
            imagelomp3_3.setImageResource(R.drawable.icon_2);
        }
        if (_lam4 == 1) {
            m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.bbb);
            imagelomp4.setImageResource(R.drawable.icon_off);
            imagelomp4_4.setImageResource(R.drawable.icon_1);
        }
        else {
            m = MediaPlayer.create(getContext().getApplicationContext(), R.raw.aaa);
            imagelomp4.setImageResource(R.drawable.icon_on);
            imagelomp4_4.setImageResource(R.drawable.icon_2);
        }
    }


    public void _sentlampetat(final String _lomp, final double _etat) {
        Request.startRequestNetwork(RequestNetworkController.PUT, "https://smart-home-53xp.onrender.com/api/data/lamp/".concat(_lomp.concat("/".concat(String.valueOf((long)(_etat))))), "changlamp", _Request_request_listener);
    }

}