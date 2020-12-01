package com.example.toollib.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.toollib.R;
import com.example.toollib.R2;
import com.example.toollib.base.BaseFragment;
import com.example.toollib.data.IBaseModule;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * @author Administrator
 * @date 2019/8/5 0005
 * <p>
 * 大图查看
 */
public class ImgPreviewFragment extends BaseFragment {

    public static final String POSITION = "position";
    public static final String URL_LIST = "url_list";

    @BindView(R2.id.viewPager)
    ViewPager mViewPager;

    public static ImgPreviewFragment newInstance(int position, ArrayList<String> url) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        args.putStringArrayList(URL_LIST, url);
        ImgPreviewFragment fragment = new ImgPreviewFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IBaseModule initModule() {
        return null;
    }

    @Override
    public void initView() {
        List<String> list = getList();
        if (list != null) {
            mViewPager.setAdapter(new ImgPreviewPageAdapter(getChildFragmentManager(), list));
            mViewPager.setCurrentItem(getPosition());
        }
    }

    @Override
    public int getContentView() {
        return R.layout.tool_lib_fragment_img_preview;
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
    protected String getActivityTitle() {
        return null;
    }

    private List<String> getList() {
        if (getArguments() != null) {
            return getArguments().getStringArrayList(URL_LIST);
        }
        return null;
    }

    private int getPosition() {
        if (getArguments() != null) {
            return getArguments().getInt(POSITION);
        }
        return 0;
    }

    class ImgPreviewPageAdapter extends FragmentPagerAdapter {
        private List<String> urlList;

        public ImgPreviewPageAdapter(FragmentManager fm, List<String> urlList) {
            super(fm);
            this.urlList = urlList;
        }

        @Override
        public Fragment getItem(int position) {
            return ImgPreviewDetailFragment.newInstance((position + 1), urlList.size(), urlList.get(position));
        }

        @Override
        public int getCount() {
            return urlList.size();
        }
    }
}
