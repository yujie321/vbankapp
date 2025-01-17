package com.vieboo.vbankapp.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.toollib.base.BaseListFragment;
import com.example.toollib.util.AmountUtil;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.NavigationAdapter;
import com.vieboo.vbankapp.adapter.NoticeListAdapter;
import com.vieboo.vbankapp.data.Navigation;
import com.vieboo.vbankapp.data.NoticeListVO;
import com.vieboo.vbankapp.data.PadInfoVo;
import com.vieboo.vbankapp.data.PassengerVO;
import com.vieboo.vbankapp.data.PlayInfo;
import com.vieboo.vbankapp.data.StaticTodaySummeryVo;
import com.vieboo.vbankapp.model.IHomeModel;
import com.vieboo.vbankapp.model.IHomeView;
import com.vieboo.vbankapp.model.impl.HomeModel;
import com.vieboo.vbankapp.utils.BarChartUtil;
import com.vieboo.vbankapp.utils.GridSpacingItemDecoration;
import com.vieboo.vbankapp.utils.PieChartUtil;
import com.vieboo.vbankapp.weight.RankDialog;
import com.vieboo.vbankapp.weight.VideoViewDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 首页
 */
public class HomeFragment extends BaseListFragment<IHomeModel, NoticeListAdapter> implements IHomeView, View.OnTouchListener {

    @BindView(R.id.tvHomeTitle)
    TextView tvHomeTitle;
    @BindView(R.id.tcHomeDate)
    TextClock tcHomeDate;
    @BindView(R.id.tvClockInNum)
    TextView tvClockInNum;
    @BindView(R.id.tvRank)
    TextView tvRank;
    @BindView(R.id.barChart)
    BarChart barChart;
    @BindView(R.id.tvPassengerNum)
    TextView tvPassengerNum;
    @BindView(R.id.tvPassengerDesc)
    TextView tvPassengerDesc;
    @BindView(R.id.ivDown)
    ImageView ivDown;
    @BindView(R.id.tvPercentage)
    TextView tvPercentage;
    @BindView(R.id.tvSecurityPeopleNumber)
    TextView tvSecurityPeopleNumber;
    @BindView(R.id.tvSecurityRegion)
    TextView tvSecurityRegion;
    @BindView(R.id.pieChart)
    PieChart pieChart;
    @BindView(R.id.rvNavigationList)
    RecyclerView rvNavigationList;

    @BindView(R.id.videoView1)
    VideoView videoView1;
    @BindView(R.id.videoView2)
    VideoView videoView2;
    @BindView(R.id.videoView3)
    VideoView videoView3;
    @BindView(R.id.videoView4)
    VideoView videoView4;
    @BindView(R.id.rlRank)
    RelativeLayout rlRank;


    private NoticeListAdapter noticeListAdapter;
    private NavigationAdapter navigationAdapter;

    private final static String[] xAxisValue = new String[]{
            "设备", "人员", "通知公告", "规章制度", "进出记录", "呼叫记录"
    };

    private Handler handler = null;
    private Runnable runnable = null;

    List<VideoView> videoViewList = new ArrayList<>();

    static HomeFragment newInstance() {
        Bundle args = new Bundle();
        HomeFragment fragment = new HomeFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_home;
    }

    @Override
    public void initView() {
        super.initView();
        videoViewList.clear();
        videoView1.setBackgroundResource(R.drawable.bg_home_one);
        videoView2.setBackgroundResource(R.drawable.bg_home_two);
        videoView3.setBackgroundResource(R.drawable.bg_home_three);
        videoView4.setBackgroundResource(R.drawable.bg_home_four);
        videoViewList.add(videoView1);
        videoViewList.add(videoView2);
        videoViewList.add(videoView3);
        videoViewList.add(videoView4);
        tcHomeDate.setFormat24Hour("yyyy年MM月dd HH:mm   EEEE");

        int spanCount = 3;
        int spacing = 3;
        rvNavigationList.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, false));
        navigationAdapter = new NavigationAdapter();
        navigationAdapter.setOnItemClickListener(onItemClickListener);
        rvNavigationList.setAdapter(navigationAdapter);

        //获取pad信息
        iModule.getPadInfo();
        //获取播放列表
        iModule.getPlayInfo();
        //当日总览设置
        iModule.getTodaySummary();
        //今日客流信息
        iModule.getPassenger();
        //押运信息
        iModule.getEscortInfo();
        //设置导航菜单
        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 3);
        rvNavigationList.setLayoutManager(layoutManager);
        iModule.getNavigationList();
        rlRank.setOnClickListener(this::onClick);
    }

    @Override
    public void setPadInfo(PadInfoVo padInfoVo) {
        tvHomeTitle.setText(padInfoVo.getName());
    }

    @Override
    public void setPlayInfo(List<PlayInfo> playInfoList) {
        for (int i = 0; i < playInfoList.size(); i++) {
            MediaController mediaController = new MediaController(getContext());
            if (playInfoList.get(i) != null) {
                if (playInfoList.get(i).getRtspUrl() != null) {
                    videoViewList.get(i).setBackground(null);
                    Uri rtspUrl = Uri.parse(playInfoList.get(i).getRtspUrl());
                    videoViewList.get(i).setVideoURI(rtspUrl);
                    videoViewList.get(i).setMediaController(null);
                    videoViewList.get(i).setTag(rtspUrl);
                    videoViewList.get(i).setOnTouchListener(this);
                    // 设置准备完成监听
                    videoViewList.get(i).setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                        @Override
                        public void onPrepared(MediaPlayer mp) {
                            // 准备完成后开始播放
                            mp.start();
                            // 设置静音
                            mp.setVolume(0, 0);
                        }
                    });
                    videoViewList.get(i).start();
                }
            }
        }
    }

    @Override
    public void setTodaySummary(StaticTodaySummeryVo staticTodaySummeryVo) {
        tvClockInNum.setText(String.valueOf(staticTodaySummeryVo.getOnDutyPersonNum()));
        //排名
        SpannableString spannableString = new SpannableString(getString(R.string.rank, String.valueOf(staticTodaySummeryVo.getBranchRank())));
        spannableString.setSpan(new AbsoluteSizeSpan(30), 1, spannableString.length() - 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#4DDCF6")), 1, spannableString.length() - 1, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvRank.setText(spannableString);
        //柱状图设置
        initBarChart(staticTodaySummeryVo);
    }

    @Override
    public void setPassenger(PassengerVO passengerVO) {
        int passengerNum = passengerVO.getPassengerNum();
        int lastPassengerNum = passengerVO.getLastPassengerNum();
        tvPassengerNum.setText(String.valueOf(passengerNum));

        SpannableString spannableString = new SpannableString(getString(R.string.compared_day_before,
                String.valueOf(lastPassengerNum), String.valueOf(passengerNum)));
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#00D2FF")),
                5, String.valueOf(lastPassengerNum).length() + 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#FF8600")),
                String.valueOf(lastPassengerNum).length() + 5, spannableString.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tvPassengerDesc.setText(spannableString);

        int compare = 0;
        if (passengerNum > lastPassengerNum) {
            compare = (int) ((AmountUtil.div(passengerNum, lastPassengerNum, 2) - 1) * 100);
            ivDown.setImageResource(R.drawable.ic_up);
        } else if (passengerNum < lastPassengerNum) {
            compare = (int) ((1 - AmountUtil.div(passengerNum, lastPassengerNum, 2)) * 100);
            ivDown.setImageResource(R.drawable.ic_down);
        } else {
            ivDown.setImageResource(R.drawable.ic_up_down);
        }
        tvPercentage.setText(getString(R.string.compare, String.valueOf(compare)));

        tvSecurityPeopleNumber.setText(getString(R.string.security_people_number, String.valueOf(passengerVO.getGuardPersonNum())));
        tvSecurityRegion.setText(getString(R.string.security_region, String.valueOf(passengerVO.getGuardRegionNum())));

        initPieChart(passengerVO);
    }

    private void initBarChart(StaticTodaySummeryVo staticTodaySummeryVo) {
        if (getActivity() != null) {
            // 不显示描述
            barChart.getDescription().setEnabled(false);
            barChart.setExtraBottomOffset(10);
            barChart.setExtraTopOffset(30);
            // 设置坐标轴
            BarChartUtil.setBarChartAxis(getActivity(), barChart, xAxisValue);
            // 设置数据
            BarChartUtil.setBarChartData(getActivity(), barChart, staticTodaySummeryVo);
            //数据显示动画，从左往右依次显示
            barChart.animateX(1500);
            barChart.invalidate();
        }
    }

    private void initPieChart(PassengerVO passengerVO) {
        PieChartUtil.setPieChart(pieChart);
        pieChart.setData(PieChartUtil.setData(getActivity(), pieChart, passengerVO));
        pieChart.highlightValues(null);
        pieChart.invalidate();
    }

    @Override
    public NoticeListAdapter getBaseListAdapter() {
        noticeListAdapter = new NoticeListAdapter();
        return noticeListAdapter;
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        NoticeListVO noticeListVO = (NoticeListVO) view.findViewById(R.id.ivIsRead).getTag();
        iModule.requestReadNotice(noticeListVO.getId(), position, noticeListVO.getContent());
    }

    @Override
    public void readNotice(int position, String pdfUrl) {
        List<NoticeListVO> data = noticeListAdapter.getData();
        data.get(position).setNoticeStatus(1);
        noticeListAdapter.notifyDataSetChanged();
        startFragment(PDFFragment.newInstance(pdfUrl));
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        //刷新
        iModule.requestNoticeList();
    }

    @Override
    public void onBaseLoadMoreRequested() {
        //加载
        iModule.requestNoticeList();
    }

    @Override
    public void refreshNoticeListSuccess(List<NoticeListVO> passengerVO) {
        if (noticeListAdapter != null) {
            noticeListAdapter.setList(passengerVO);
        }
    }

    @Override
    public void loadMoreNoticeListSuccess(List<NoticeListVO> passengerVO) {
        if (noticeListAdapter != null) {
            noticeListAdapter.addData(passengerVO);
        }
    }

    @Override
    public void setNavigationList(List<Navigation> navigationList) {
        navigationAdapter.addData(navigationList);
    }

    @OnClick(R.id.ivHomeSetting)
    public void btnSubmit() {
        //设置
        startFragmentForResult(SettingFragment.newInstance(), -1);
    }

    private OnItemClickListener onItemClickListener = (adapter, view, position) -> {
        switch (position) {
            case 0:
                //人员管控
                startFragment(PersonnelControlFragment.newInstance());
                break;
            case 1:
                //设备状态
                startFragment(DeviceStatusFragment.newInstance());
                break;
            case 2:
                //网点运营
                startFragment(BranchOperateFragment.newInstance());
                break;
            case 3:
                //车辆管控
                startFragment(CarManagerFragment.newInstance());
                break;
            case 4:
                //安保运营
                startFragment(SecurityOperateFragment.newInstance());
                break;
            case 5:
                //进出管理
                startFragment(InoutManagerFragment.newInstance());
                break;
            case 6:
                //规则制度
                startFragment(RegulationsFragment.newInstance());
                break;
            case 7:
                //呼叫管理
                break;
            case 8:
                //呼叫管理
                startFragment(new RecordManagerFragment());
                break;
        }
    };

    @Override
    protected void onFragmentResult(int requestCode, int resultCode, Intent data) {
        super.onFragmentResult(requestCode, resultCode, data);
        if (requestCode == -1 && resultCode == 1) {
            iModule.getPadInfo();
            iModule.getTodaySummary();
            //今日客流信息
            iModule.getPassenger();
            //押运信息
            iModule.getEscortInfo();
            iModule.requestNoticeList();
            //initView();
        }
    }

    @Override
    protected IHomeModel initModule() {
        return new HomeModel();
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
    public void onDestroy() {
        handler.removeCallbacks(runnable);
        super.onDestroy();
    }

    @Override
    public void onStop() {
        for (int i = 0; i < videoViewList.size(); i++) {
            videoViewList.get(i).stopPlayback();
            videoViewList.get(i).suspend();
        }
        handler.removeCallbacks(runnable);
        super.onStop();
    }

    @Override
    public void onResume() {
        //启动刷新线程
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                iModule.getPadInfo();
                iModule.getTodaySummary();
                //今日客流信息
                iModule.getPassenger();
                //押运信息
                iModule.getEscortInfo();
                iModule.requestNoticeList();
                handler.postDelayed(this, 30000);
            }
        };
        handler.postDelayed(runnable, 30000);

        iModule.getPlayInfo();
        super.onResume();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()) {
            case R.id.videoView1:
                Uri rtspUrl = (Uri) videoView1.getTag();
                showVideoViewDialog(rtspUrl);
                break;
            case R.id.videoView2:
                Uri rtspUrl2 = (Uri) videoView2.getTag();
                showVideoViewDialog(rtspUrl2);
                break;
            case R.id.videoView3:
                Uri rtspUrl3 = (Uri) videoView3.getTag();
                showVideoViewDialog(rtspUrl3);
                break;
            case R.id.videoView4:
                Uri rtspUrl4 = (Uri) videoView4.getTag();
                showVideoViewDialog(rtspUrl4);
                break;
        }
        return false;
    }

    private void showVideoViewDialog(Uri rtspUrl) {
        VideoViewDialog instance = VideoViewDialog.getInstance();
        instance.initView(getActivity())
                .setRtspUrl(rtspUrl)
                .showDialog();
    }

    @OnClick({})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rlRank: {
                RankDialog rankDialog = RankDialog.getInstance();
                rankDialog.initView(getActivity())
                        .showDialog();
                break;
            }
        }
    }
}
