package com.vieboo.vbankapp;

import android.os.Bundle;

import com.example.toollib.base.BaseActivity;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.vieboo.vbankapp.fragment.SplashFragment;

public class MainActivity extends BaseActivity {

    @Override
    public int getContextViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.tool_lib_QMUI_AppTheme);
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            init(SplashFragment.newInstance());
        }
    }

    private void init(QMUIFragment qmuiFragment) {
        getSupportFragmentManager()
                .beginTransaction()
                .add(getContextViewId(), qmuiFragment, qmuiFragment.getClass().getSimpleName())
                .addToBackStack(qmuiFragment.getClass().getSimpleName())
                .commit();
    }

}
