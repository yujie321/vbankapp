package com.example.toollib.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.toollib.R;
import com.example.toollib.R2;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;
import com.example.toollib.manager.GlideManager;
import com.github.chrisbanes.photoview.PhotoView;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 */
public class ImgPreviewDetailFragment extends BaseFragment {
    public static final String PAGE = "page";
    public static final String TOTAL = "total";
    public static final String URL = "url";

    @BindView(R2.id.photoView)
    PhotoView photoView;
    @BindView(R2.id.tvPage)
    TextView tvPage;
    @BindView(R2.id.tvPageTotal)
    TextView tvPageTotal;

    public static ImgPreviewDetailFragment newInstance(int page , int total,String url) {
        Bundle args = new Bundle();
        args.putString(URL, url);
        args.putInt(PAGE , page);
        args.putInt(TOTAL , total);
        ImgPreviewDetailFragment fragment = new ImgPreviewDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        statusBarWhite();
        GlideManager.loadImage(getActivity(), getUrl(), photoView);
        photoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popBackStack();
            }
        });

        tvPage.setText(getPage());
        tvPageTotal.setText(getTotal());
    }

    @Override
    public int getContentView() {
        return R.layout.tool_lib_fragment_img_preview_detail;
    }

    private String getUrl() {
        if (getArguments() != null) {
            return getArguments().getString(URL);
        }
        return "";
    }

    private String getPage() {
        if (getArguments() != null) {
            return String.valueOf(getArguments().getInt(PAGE));
        }
        return "";
    }

    private String getTotal() {
        if (getArguments() != null) {
            return String.valueOf(getArguments().getInt(TOTAL));
        }
        return "";
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

    @Override
    public void onDestroyView() {
        statusBarBlack();
        super.onDestroyView();
    }
}
