package com.smart.home;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.util.TypedValue;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeActivity extends AppCompatActivity {


    private LinearLayout linear1;
    private ViewPager viewpager1;
    private BottomNavigationView bottomnavigation1;

    private FragmentFragmentAdapter fragment;
    private AlertDialog.Builder d;
    private RequestNetwork r;
    private RequestNetwork.RequestListener _r_request_listener;

    @Override
    protected void onCreate(Bundle _savedInstanceState) {
        super.onCreate(_savedInstanceState);
        setContentView(R.layout.activity_home);
        initialize(_savedInstanceState);
        initializeLogic();
    }

    private void initialize(Bundle _savedInstanceState) {
        linear1 = findViewById(R.id.linear1);
        viewpager1 = findViewById(R.id.viewpager1);
        bottomnavigation1 = findViewById(R.id.bottomnavigation1);
        fragment = new FragmentFragmentAdapter(getApplicationContext(), getSupportFragmentManager());
        d = new AlertDialog.Builder(this);
        r = new RequestNetwork(this);

        viewpager1.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int _position, float _positionOffset, int _positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int _position) {
                bottomnavigation1.setSelectedItemId(_position);
            }

            @Override
            public void onPageScrollStateChanged(int _scrollState) {

            }
        });

        bottomnavigation1.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                final int _itemId = item.getItemId();
                if (_itemId == 0) {
                    viewpager1.setCurrentItem((int)0);
                }
                if (_itemId == 1) {
                    viewpager1.setCurrentItem((int)1);
                }
                if (_itemId == 2) {
                    viewpager1.setCurrentItem((int)2);
                }
                return true;
            }
        });

        _r_request_listener = new RequestNetwork.RequestListener() {
            @Override
            public void onResponse(String _param1, String _param2, HashMap<String, Object> _param3) {

                final HashMap<String, Object> _responseHeaders = _param3;

            }

            @Override
            public void onErrorResponse(String _param1, String _param2) {
                final String _tag = _param1;
                final String _message = _param2;

            }
        };
    }

    private void initializeLogic() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT) {
            Window w =HomeActivity.this.getWindow();
            w.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            w.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS); w.setStatusBarColor(0xFF000321);
        }
        bottomnavigation1.getMenu().add(0, 0, 0, "Statics").setIcon(R.drawable.icons8);
        bottomnavigation1.getMenu().add(0, 1, 0, "Contrôle").setIcon(R.drawable.ic_settings_white);
        bottomnavigation1.getMenu().add(0, 2, 0, "Histoire").setIcon(R.drawable.ic_assignment_white);
        fragment.setTabCount(3);
        viewpager1.setAdapter(fragment);
        int[] colorsCRNFG = { Color.parseColor("#000321"), Color.parseColor("#000321") }; android.graphics.drawable.GradientDrawable CRNFG = new android.graphics.drawable.GradientDrawable(android.graphics.drawable.GradientDrawable.Orientation.TOP_BOTTOM, colorsCRNFG);
        CRNFG.setCornerRadii(new float[]{(int)0,(int)0,(int)0,(int)0,(int)19,(int)19,(int)19,(int)19});
        CRNFG.setStroke((int) 0, Color.parseColor("#000000"));
        linear1.setElevation((float) 5);
        linear1.setBackground(CRNFG);

        //Paste this code in (add source directly block) asd block
        //Milz
    }

    public class FragmentFragmentAdapter extends FragmentStatePagerAdapter {

        Context context;
        int tabCount;

        public FragmentFragmentAdapter(Context context, FragmentManager manager) {
            super(manager);
            this.context = context;
        }

        public void setTabCount(int tabCount) {
            this.tabCount = tabCount;
        }

        @Override
        public int getCount() {
            return tabCount;
        }

        @Override
        public CharSequence getPageTitle(int _position) {

            return null;
        }

        @Override
        public Fragment getItem(int _position) {
            try{
                if (_position == 0) {
                    return new HomeFragmentActivity();
                }
                if (_position == 1) {
                    return new ControleFragmentActivity();
                }
                if (_position == 2) {
                    return new HistoriqueFragmentActivity();
                }
            }catch(Exception e){

            }
            return null;
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        d.setIcon(R.drawable.ic_lock_outline_black);
        d.setMessage("Exit?");
        d.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {
                finishAffinity();
            }
        });
        d.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface _dialog, int _which) {

            }
        });
        d.create().show();
    }
    public void _ClickEffect(final View _view) {
        TypedValue typedValue = new TypedValue();

        getApplicationContext().getTheme().resolveAttribute(16843868, typedValue, true);

        _view.setBackgroundResource(typedValue.resourceId);

        _view.setClickable(true);
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