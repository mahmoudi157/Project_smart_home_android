package com.smart.home;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.util.ArrayList;
import java.util.HashMap;

public class HistoriqueFragmentActivity extends Fragment {

    private HashMap<String, Object> map = new HashMap<>();

    private ArrayList<HashMap<String, Object>> listhisto = new ArrayList<>();

    private ListView listview1;

    private SharedPreferences loginData;
    private RequestNetwork request;
    private RequestNetwork.RequestListener _request_request_listener;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
        View _view = _inflater.inflate(R.layout.fragment_historique_activity, _container, false);
        initialize(_savedInstanceState, _view);
        initializeLogic();
        return _view;
    }

    private void initialize(Bundle _savedInstanceState, View _view) {
        listview1 = _view.findViewById(R.id.listview1);
        loginData = getContext().getSharedPreferences("loginData", Activity.MODE_PRIVATE);
        request = new RequestNetwork((Activity) getContext());

        _request_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                if (_tag.equals("history")) {
                    listhisto = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                    listview1.setAdapter(new Listview1Adapter(listhisto));
                    ((BaseAdapter)listview1.getAdapter()).notifyDataSetChanged();
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
        request.setHeaders(map);
        request.startRequestNetwork(RequestNetworkController.GET, "https://smart-home-53xp.onrender.com/api/history/", "history", _request_request_listener);
    }

    public class Listview1Adapter extends BaseAdapter {

        ArrayList<HashMap<String, Object>> _data;

        public Listview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
            _data = _arr;
        }

        @Override
        public int getCount() {
            return _data.size();
        }

        @Override
        public HashMap<String, Object> getItem(int _index) {
            return _data.get(_index);
        }

        @Override
        public long getItemId(int _index) {
            return _index;
        }

        @Override
        public View getView(final int _position, View _v, ViewGroup _container) {
            LayoutInflater _inflater = getActivity().getLayoutInflater();
            View _view = _v;
            if (_view == null) {
                _view = _inflater.inflate(R.layout.costumehis, null);
            }

            final androidx.cardview.widget.CardView cardview1 = _view.findViewById(R.id.cardview1);
            final LinearLayout linear3 = _view.findViewById(R.id.linear3);
            final LinearLayout linear4 = _view.findViewById(R.id.linear4);
            final LinearLayout linear5 = _view.findViewById(R.id.linear5);
            final ImageView imageview3 = _view.findViewById(R.id.imageview3);
            final TextView event = _view.findViewById(R.id.event);
            final TextView Auto = _view.findViewById(R.id.Auto);
            final TextView Date = _view.findViewById(R.id.Date);
            final TextView hum = _view.findViewById(R.id.hum);
            final TextView tump = _view.findViewById(R.id.tump);

            if (_data.get((int)_position).containsKey("event")) {
                event.setText("Event :".concat(_data.get((int)_position).get("event").toString()));
            }
            if (_data.get((int)_position).containsKey("auto")) {
                Auto.setText("Mode auto :".concat(_data.get((int)_position).get("auto").toString()));
            }
            if (_data.get((int)_position).containsKey("date")) {
                Date.setText("Date :".concat(_data.get((int)_position).get("date").toString().substring((int)(0), (int)(14))));
            }
            if (_data.get((int)_position).containsKey("temp")) {
                tump.setText("Température :".concat(_data.get((int)_position).get("temp").toString()));
            }
            if (_data.get((int)_position).containsKey("humid")) {
                hum.setText("Humidité :".concat(_data.get((int)_position).get("humid").toString()));
            }

            return _view;
        }
    }
}