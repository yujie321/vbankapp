package com.vieboo.vbankapp;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.toollib.base.BaseActivity;
import com.qmuiteam.qmui.arch.QMUIFragment;
import com.vieboo.vbankapp.fragment.CarManagerFragment;
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

//    @Override
//    protected void onResume() {
//        super.onResume();
//        //隐藏状态栏
//        if(SmdtManager.create(getApplicationContext()) != null){
//            SmdtManager.create(getApplicationContext()).smdtSetStatusBar(getApplicationContext(), false);
//        }
//    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            // 获得当前得到焦点的View，一般情况下就是EditText
            View v = getCurrentFocus();
            if (isShouldHideInput(v, ev)) {
                hideSoftInput(v.getWindowToken());
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘
     */
    private boolean isShouldHideInput(View v, MotionEvent event) {
        if ((v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0], top = l[1], bottom = top + v.getHeight(), right = left
                    + v.getWidth();
            // 点击EditText的事件，忽略它。
            return !(event.getX() > left) || !(event.getX() < right)
                    || !(event.getY() > top) || !(event.getY() < bottom);
        }
        return false;
    }

    /**
     * 隐藏软件盘
     */
    private void hideSoftInput(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) {
                im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

}
