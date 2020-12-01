package com.vieboo.vbankapp.view;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;

import me.jessyan.autosize.AutoSize;

public class AutoWebView extends WebView {

    public AutoWebView(Context context) {
        super(context);
    }

    public AutoWebView(Context context , AttributeSet attrs) {
        super(context , attrs);
    }

    @Override
    public void setOverScrollMode(int mode) {
        super.setOverScrollMode(mode);
        AutoSize.autoConvertDensityOfGlobal((Activity) getContext());
    }


}
