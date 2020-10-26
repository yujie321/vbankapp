package com.vieboo.vbankapp.fragment;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.download.DownLoadUtil;

import java.io.File;

import butterknife.BindView;

public class PDFFragment extends BaseFragment {

    private static final String PDF_URL = "pdf_url";
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;

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

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void initView() {
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setAllowContentAccess(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setAllowUniversalAccessFromFileURLs(true);

        initData();
    }

    private void initData() {
        DownLoadUtil.downLoad(getActivity(), getPdfUrl(), new DownLoadUtil.OnDownloadListener() {
            @Override
            public void onDownloadSuccess(File file) {
                String path = file.getPath();
                webView.loadUrl("file:///android_asset/pdfjs/web/viewer.html?file=" + path);
            }

            @Override
            public void onDownloading(int progress) {
                progressBar.setProgress(progress);
                if (progress >= 100 && getActivity() != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onDownloadFailed() {
            }
        });
    }


    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    protected String getActivityTitle() {
        return null;
    }

    public String getPdfUrl() {
        return getArguments() != null ? getArguments().getString(PDF_URL) : "";
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
