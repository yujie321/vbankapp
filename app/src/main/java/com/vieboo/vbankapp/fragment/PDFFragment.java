package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.just.agentwebX5.AgentWebX5;
import com.just.agentwebX5.DefaultWebClient;
import com.vieboo.vbankapp.R;

public class PDFFragment extends BaseFragment {

    private static final String PDF_URL = "pdfUrl";

    public static PDFFragment newInstance(String pdfUrl) {
        Bundle args = new Bundle();
        args.putString(PDF_URL, pdfUrl);
        PDFFragment fragment = new PDFFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_pdf;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //NestedScrollAgentWebView webView = new NestedScrollAgentWebView(getActivity());
//        AgentWebX5.with(this)
//                //传入AgentWeb的父控件。
//                .setAgentWebParent(view.findViewById(R.id.layPDFFragment),
//                        new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT))
//                //设置进度条颜色与高度，-1为默认值，高度为2，单位为dp。
//                .useDefaultIndicator(getResources().getColor(R.color.colorAccent))
//                //严格模式 Android 4.2.2 以下会放弃注入对象 ，使用AgentWebView没影响。
//                .setSecurityType(AgentWebX5.SecurityType.default_check)
//                //参数1是错误显示的布局，参数2点击刷新控件ID -1表示点击整个布局都刷新， AgentWeb 3.0.0 加入。
//                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
//                .createAgentWeb()//创建AgentWeb。
//                .ready()//设置 WebSettings。
//                //WebView载入该url地址的页面并显示。
//                .go(getPdfUrl());
    }

    @Override
    public void initView() {

    }

    public String getPdfUrl() {
        return getArguments() != null ? getArguments().getString(PDF_URL) : "";
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    @Override
    public boolean isCustomView() {
        return false;
    }

    @Override
    protected boolean translucentFull() {
        return true;
    }
}
