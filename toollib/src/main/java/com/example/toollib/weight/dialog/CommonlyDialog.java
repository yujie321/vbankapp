package com.example.toollib.weight.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import com.example.toollib.R;
import com.example.toollib.util.DensityUtil;

/**
 * @author Administrator
 * @date 2019/7/5 0005
 */
public class CommonlyDialog {

    private AlertDialog alertDialog;
    private BaseCallback baseCallback;

    public static CommonlyDialog getInstance() {
        return new CommonlyDialog();
    }

    public CommonlyDialog initView(Context mContext) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.tool_lib_dialog_commonly, null);
        alertDialog = new AlertDialog.Builder(mContext).create();
        alertDialog.setView(view);
        return this;
    }


    /**
     * 点击外部 不消失
     */
    public CommonlyDialog isCancelable() {
        alertDialog.setCancelable(false);
        return this;
    }

    /**
     * dialog  是否显示
     *
     * @return boolean
     */
    public boolean isShow() {
        if (alertDialog != null) {
            return alertDialog.isShowing();
        }
        return false;
    }

    public void showDialog() {
        if (alertDialog != null) {
            alertDialog.show();
            Window window = alertDialog.getWindow();
            if (window != null) {
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.getDecorView().setPadding(DensityUtil.dp2px(32), 0, DensityUtil.dp2px(32), 0);
            }
        }
    }

    public interface BaseCallback {
        /**
         * 确定
         */
        void sure();
    }

    public CommonlyDialog setICallBase(BaseCallback baseCallback) {
        this.baseCallback = baseCallback;
        return this;
    }


}
