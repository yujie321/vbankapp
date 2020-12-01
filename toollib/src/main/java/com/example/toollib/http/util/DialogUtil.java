package com.example.toollib.http.util;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Window;

import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

/**
 * @author Administrator
 * @date 2019/5/8 0008
 */
public class DialogUtil {

    private static QMUITipDialog tipLoadingDialog;

    /** 正在加载的loading */
    public static QMUITipDialog getTipLoading(Context context, String msg) {
        tipLoadingDialog = new QMUITipDialog.Builder(context).setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                .setTipWord(TextUtils.isEmpty(msg) ? "" : msg)
                .create();
        Window window = tipLoadingDialog.getWindow();
        if (window != null){
            window.setGravity(Gravity.CENTER);
        }
        return tipLoadingDialog;
    }

    /**
     *  取消dialog
     */
    public static void tipLoadingDismiss() {
        if (tipLoadingDialog != null && tipLoadingDialog.isShowing()) {
            tipLoadingDialog.dismiss();
            tipLoadingDialog = null;
        }
    }
}
