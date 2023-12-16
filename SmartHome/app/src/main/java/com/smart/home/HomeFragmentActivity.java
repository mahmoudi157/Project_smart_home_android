package com.smart.home;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TimerTask;

public class HomeFragmentActivity extends Fragment {

    private String date = "";
    private String h = "";
    private String t = "";
    private HashMap<String, Object> map = new HashMap<>();

    private ArrayList<HashMap<String, Object>> listedata = new ArrayList<>();


    private LinearLayout linear2;

    private LinearLayout linear20;

    private AlertDialog.Builder d;
    private RequestNetwork req;
    private RequestNetwork.RequestListener _req_request_listener;
    private SharedPreferences Data;
    private TimerTask ti;
    private SharedPreferences loginData;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater _inflater, @Nullable ViewGroup _container, @Nullable Bundle _savedInstanceState) {
        View _view = _inflater.inflate(R.layout.activity_home, _container, false);
        initialize(_savedInstanceState, _view);
        initializeLogic();
        return _view;
    }

    private void initialize(Bundle _savedInstanceState, View _view) {

        linear2 = _view.findViewById(R.id.linear2);

        linear20 = _view.findViewById(R.id.linear20);
        d = new AlertDialog.Builder(getActivity());
        req = new RequestNetwork((Activity) getContext());
        Data = getContext().getSharedPreferences("Data", Activity.MODE_PRIVATE);
        loginData = getContext().getSharedPreferences("loginData", Activity.MODE_PRIVATE);

        _req_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {
                final String _tag = _param1;
                final String _response = _param2;
                final HashMap<String, Object> _responseHeaders = _param3;
                try{
                    if (_tag.equals("capture")) {
                        listedata = new Gson().fromJson(_response, new TypeToken<ArrayList<HashMap<String, Object>>>(){}.getType());
                        if (listedata.size() > 0) {
                            for(int _repeat19 = 0; _repeat19 < (int)(listedata.size()); _repeat19++) {
                                date = listedata.get((int)_repeat19).get("date").toString().substring((int)(10), (int)(18));
                                t = listedata.get((int)_repeat19).get("temp").toString();
                                h = listedata.get((int)_repeat19).get("humid").toString();
                                slc.addItem(Double.parseDouble(t), date, null, null);
                                hum.addItem(Double.parseDouble(h), date, null, null);
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

                Toast.makeText(getActivity(), _message, Toast.LENGTH_SHORT).show();
            }
        };
    }

    private void initializeLogic() {
        map = new HashMap<>();
        map.put("Authorization", "Bearer ".concat(loginData.getString("token", "")));
        slc = new LineChart(getActivity (), 100);

        slc.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));

        linear2.addView(slc);

        slc.setLineDividerColor(Color.GRAY);

        slc.setLineOuterColor(Color.BLACK);

        slc.setDotColor(Color.parseColor("#2196F3"));

        //slc.setDotSize((int)getDip(2));

        slc.setDividerTotal(5);

        slc.setLineStrokeSize(2f);

        slc.setEnableDividerGrid(true);
        slc.setEnableLinesDot(false);
        slc.setEnableOuterLine(true);

        slc.setStartFrom(50);
        hum = new LineChart(getActivity (), 100);

        hum.setLayoutParams(new LinearLayout.LayoutParams(-1,-1));

        linear20.addView(hum);

        hum.setLineDividerColor(Color.GRAY);

        hum.setLineOuterColor(Color.BLACK);

        hum.setDotColor(Color.parseColor("#2196F3"));

        hum.setDividerTotal(5);

        hum.setLineStrokeSize(2f);

        hum.setEnableDividerGrid(true);
        hum.setEnableLinesDot(false);
        hum.setEnableOuterLine(true);


        hum.setStartFrom(50);


        req.setHeaders(map);
        req.startRequestNetwork(RequestNetworkController.GET, "https://smart-home-53xp.onrender.com/api/data/capture/", "capture", _req_request_listener);
    }

    public void _initLib() {
    }
    private LineChart slc,hum;
    public static class LineChart extends FrameLayout {



        public static interface OnItemClicked {
            public void onTap(int pos, ItemData a);
        }

        public class DividerData {
            public double value;
            public String title, subtitle;
        }

        public class ItemData {
            public double value;
            public String title, subtitle;
            public Object extra;
            public int color;
        }

        private ArrayList<DividerData> divider = new ArrayList<>();

        private ArrayList<ItemData> items = new ArrayList<>();

        private boolean dividerGrid, linesDot, outerLines = true;

        private double max, start;

        private Paint base_line_paint, base_dot_paint, line_paint, divider_line_paint, bottomTextPaint;

        private int divider_total = 0;
        private int dot_size = (int) dip(3);
        private int base_line_size = (int) dip(1);
        private int scoop = 0;

        private float fili, stroke, fixedHeight, bottomTextSize;

        protected float ld = 0;

        public LineChart(Context a, double b) {
            super(a);
            base_line_paint = new Paint();
            base_dot_paint = new Paint();
            line_paint = new Paint();
            divider_line_paint = new Paint();
            bottomTextPaint = new Paint();
            max = b;
            start = 0;
            setBackgroundColor(Color.TRANSPARENT);
            fili = base_line_size / 2;
            stroke = 1f;
            bottomTextSize = (float)dip(7);
            initColors();
            initPaintsSize();
        }

        protected void initPaintsSize() {
            base_line_paint.setStrokeWidth(2f);
            line_paint.setStrokeWidth(base_line_size);
            divider_line_paint.setStrokeWidth(1f);
            bottomTextPaint.setTextAlign(android.graphics.Paint.Align.CENTER);
            bottomTextPaint.setTextSize(bottomTextSize);
            bottomTextPaint.setAntiAlias(true);
        }

        protected void initColors() {
            base_line_paint.setColor(Color.parseColor("#0097A7"));
            base_dot_paint.setColor(Color.parseColor("#00BCD4"));
            line_paint.setColor(Color.parseColor("#4DD0E1"));
            divider_line_paint.setColor(Color.parseColor("#E0F7FA"));
            bottomTextPaint.setColor(Color.GRAY);
        }

        private void setLineDividerColor(int a) {
            divider_line_paint.setColor(a);
            invalidate();
        }

        private void setDotColor(int a) {
            base_dot_paint.setColor(a);
            invalidate();
        }

        private void setLineOuterColor(int a) {
            base_line_paint.setColor(a);
            invalidate();
        }

        private void setDotSize(int a) {
            dot_size = a;
            invalidate();
        }

        private void setStartFrom(double a) {
            start = a;
            invalidate();
        }

        private void setLineStrokeSize(float a) {
            line_paint.setStrokeWidth(a);
            invalidate();
        }

        private void setEnableDividerGrid(boolean a) {
            dividerGrid = a;
            invalidate();
        }

        private void setEnableLinesDot(boolean a) {
            linesDot = a;
            invalidate();
        }

        private void setEnableOuterLine(boolean a) {
            outerLines = a;
            invalidate();
        }

        private void setScoop(int a) {
            if (a >= 3) {
                scoop = a;
                invalidate();
            }
        }

        private void clearScoop() {
            if (scoop > 0) {
                scoop = 0;
                invalidate();
            }
        }

        @Override
        protected void onDraw(Canvas a) {
            fixedHeight = a.getHeight() - bottomTextSize - 2f;

            if (dividerGrid == true && divider_total > 0) {
                for (int i = 0; i < divider_total; i++) {
                    a.drawLine(0, (float)(i * (fixedHeight/divider_total)), getWidth(), (float)(i * (fixedHeight/divider_total)), divider_line_paint);
                }
            };

            if (dividerGrid == true && divider.size() > 0) {
                for (int i = 0; i < divider.size(); i++) {
                    if (divider.get(i).value <= max) {
                        a.drawLine(0, (float)(fixedHeight - (divider.get(i).value / max) * fixedHeight), getWidth(), (float)(fixedHeight - (divider.get(i).value / max) * fixedHeight), divider_line_paint);
                    }
                }
            };

            if (items.size() > 0) {
                ld = getWidth() / items.size();
                for (int i = 0; i < items.size(); i++) {
                    line_paint.setColor(items.get(i).color);
                    if (i == 0) {
                        a.drawLine(0, (float)(fixedHeight - (start / max) * fixedHeight), ld, (float)(fixedHeight - (items.get(i).value / max) * fixedHeight), line_paint);
                    } else {
                        a.drawLine(ld * i, (float)(fixedHeight - (items.get(i - 1).value / max) * fixedHeight), (i + 1) * ld, (float)(fixedHeight - (items.get(i).value / max) * fixedHeight), line_paint);
                    };
                }
            };

            if (outerLines) {
                a.drawLine(fili,0,fili,fixedHeight,base_line_paint);
                a.drawLine(0,fixedHeight-base_line_size,getWidth(),fixedHeight-base_line_size, base_line_paint);
            };

            if (linesDot == true && items.size() > 0) {
                for (int i = 0; i < items.size(); i++) {
                    a.drawCircle((i + 1) * ld, (float)(fixedHeight - (items.get(i).value / max) * fixedHeight), dot_size, base_dot_paint);
                }
            }

            String b;
            for (int i = 0; i < items.size(); i++) {
                b = items.get(i).title;
                a.drawText(b == null ? "-" : b, (i + 1) * ld, fixedHeight + bottomTextSize, bottomTextPaint);
            }

        }

        private void setDividerTotal(int a) {
            divider_total = a;
            invalidate();
        }

        private void addDivider(double a, String b, String c) {
            DividerData dt = new DividerData();
            dt.value = a;
            dt.title = b;
            dt.subtitle = c;
            divider.add(dt);
            invalidate();
        }

        private void addItem(double a, String b, String c, Object d, int e) {
            ItemData id = new ItemData();
            id.value = a;
            id.title = b;
            id.subtitle = c;
            id.extra = d;
            id.color = e == 0 ? Color.parseColor("#4DD0E1") : e;
            items.add(id);
            if (a >= max) {
                max = a + 2;
            };
            invalidate();
        }

        private void addItem(double a, String b, String c, Object d) {
            addItem(a, b, c, d, 0);
        }

        protected float dip(int _input) {
            return android.util.TypedValue.applyDimension(android.util.TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
        }

    }
    {
    }

}