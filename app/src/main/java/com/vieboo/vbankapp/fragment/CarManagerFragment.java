package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.example.toollib.base.BaseListFragment;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.DateUtil;
import com.example.toollib.util.Log;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.AmEscortAdapter;
import com.vieboo.vbankapp.data.EscortInfo;
import com.vieboo.vbankapp.data.RouteNodeInfo;
import com.vieboo.vbankapp.http.ServiceUrl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class CarManagerFragment extends BaseListFragment {

    @BindView(R.id.tvEscortCar)
    TextView tvEscortCar;
    @BindView(R.id.ivEscortHead)
    ImageView ivEscortHead;
    @BindView(R.id.tvEscortName)
    TextView tvEscortName;
    @BindView(R.id.tvEscortPosition)
    TextView tvEscortPosition;
    @BindView(R.id.tvEscortNumber)
    TextView tvEscortNumber;

    @BindView(R.id.ivEscortHeadTwo)
    ImageView ivEscortHeadTwo;
    @BindView(R.id.tvEscortNameTwo)
    TextView tvEscortNameTwo;
    @BindView(R.id.tvEscortPositionTwo)
    TextView tvEscortPositionTwo;
    @BindView(R.id.tvEscortNumberTwo)
    TextView tvEscortNumberTwo;

    @BindView(R.id.tvEscortStatus)
    TextView tvEscortStatus;

    @BindView(R.id.tvEstimatedTime)
    TextView tvEstimatedTime;
    @BindView(R.id.ivHandover)
    ImageView ivHandover;
    @BindView(R.id.tvHandoverName)
    TextView tvHandoverName;
    @BindView(R.id.tvHandoverPosition)
    TextView tvHandoverPosition;
    @BindView(R.id.tvHandoverNumber)
    TextView tvHandoverNumber;

    @BindView(R.id.ivHandoverTwo)
    ImageView ivHandoverTwo;
    @BindView(R.id.tvHandoverNameTwo)
    TextView tvHandoverNameTwo;
    @BindView(R.id.tvHandoverPositionTwo)
    TextView tvHandoverPositionTwo;
    @BindView(R.id.tvHandoverNumberTwo)
    TextView tvHandoverNumberTwo;
    @BindView(R.id.ivEscortHandover)
    ImageView ivEscortHandover;
    @BindView(R.id.relAmEscort)
    RecyclerView relAmEscort;
    @BindView(R.id.tvEscortCarPM)
    TextView tvEscortCarPM;
    @BindView(R.id.tvEscortHeadPM)
    ImageView tvEscortHeadPM;
    @BindView(R.id.tvEscortNamePM)
    TextView tvEscortNamePM;
    @BindView(R.id.tvEscortPositionPM)
    TextView tvEscortPositionPM;
    @BindView(R.id.tvEscortNumberPM)
    TextView tvEscortNumberPM;

    @BindView(R.id.tvEscortHeadPMTwo)
    ImageView tvEscortHeadPMTwo;
    @BindView(R.id.tvEscortNamePMTwo)
    TextView tvEscortNamePMTwo;
    @BindView(R.id.tvEscortPositionPMTwo)
    TextView tvEscortPositionPMTwo;
    @BindView(R.id.tvEscortNumberPMTwo)
    TextView tvEscortNumberPMTwo;

    @BindView(R.id.tvEscortStatusPM)
    TextView tvEscortStatusPM;

    @BindView(R.id.ivHandoverPM)
    ImageView ivHandoverPM;
    @BindView(R.id.tvHandoverNamePM)
    TextView tvHandoverNamePM;
    @BindView(R.id.tvHandoverPositionPM)
    TextView tvHandoverPositionPM;
    @BindView(R.id.tvHandoverNumberPM)
    TextView tvHandoverNumberPM;

    @BindView(R.id.ivHandoverPMTwo)
    ImageView ivHandoverPMTwo;
    @BindView(R.id.tvHandoverNamePMTwo)
    TextView tvHandoverNamePMTwo;
    @BindView(R.id.tvHandoverPositionPMTwo)
    TextView tvHandoverPositionPMTwo;
    @BindView(R.id.tvHandoverNumberPMTwo)
    TextView tvHandoverNumberPMTwo;

    @BindView(R.id.ivEscortHandoverPM)
    ImageView ivEscortHandoverPM;

    @BindView(R.id.relAmEscortPM)
    RecyclerView relAmEscortPM;

    public static CarManagerFragment newInstance() {
        Bundle args = new Bundle();
        CarManagerFragment fragment = new CarManagerFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_car_manager;
    }

    @Override
    public void initView() {
        super.initView();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        relAmEscort.setLayoutManager(layoutManager);
    }

    @Override
    public void onBaseRefresh(RefreshLayout refreshLayout) {
        String date = DateUtil.dateToString(new Date(), "yyyy-MM-dd");
        RxUtils.getObservable(ServiceUrl.getUserApi().getEscort(date))
                .compose(bindToLifecycle())
                .subscribe(new BaseHttpRxObserver<List<EscortInfo>>() {
                    @Override
                    protected void onSuccess(List<EscortInfo> escortInfoList) {
                        //上午数据初始化
                        amInitData(new EscortInfo());
                        //下午数据初始化
                        pmInitData(new EscortInfo());
                        finishRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        finishRefresh();
                    }
                });
    }

    /**
     * 上午押运信息
     *
     * @param escortInfo 押运信息
     */
    private void amInitData(EscortInfo escortInfo) {
        //押运车辆
        tvEscortCar.setText("沪A 11200");
        //押运人员1
        GlideManager.loadImage(getActivity(), "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2945493666,4036116043&fm=15&gp=0.jpg", ivEscortHead);
        tvEscortName.setText("肖笑笑");
        tvEscortPosition.setText("押运员");
        tvEscortNumber.setText("100121");
        //押运人员2
        GlideManager.loadImage(getActivity(), "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2913490221,3376015757&fm=26&gp=0.jpg", ivEscortHeadTwo);
        tvEscortNameTwo.setText("黄晓");
        tvEscortPositionTwo.setText("押运员");
        tvEscortNumberTwo.setText("100121");
        //押运状态
        tvEscortStatus.setText("完成");

        //到达时间
        tvEstimatedTime.setText("2020-10-10 11:00:00");
        //交接1
        GlideManager.loadImage(getActivity(), "https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=2945493666,4036116043&fm=15&gp=0.jpg", ivHandover);
        tvHandoverName.setText("肖笑笑");
        tvHandoverPosition.setText("押运员");
        tvHandoverNumber.setText("100121");

        //交接2
        GlideManager.loadImage(getActivity(), "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=2913490221,3376015757&fm=26&gp=0.jpg", ivHandoverTwo);
        tvHandoverNameTwo.setText("黄晓");
        tvHandoverPositionTwo.setText("押运员");
        tvHandoverNumberTwo.setText("100121");

        ivEscortHandover.setBackgroundResource(R.drawable.ic_handover_nor);

        //押运路线

        AmEscortAdapter amEscortAdapter = new AmEscortAdapter();
        amEscortAdapter.setNewInstance(getRouteNodeInfo());
        amEscortAdapter.setOnItemClickListener(amEscortItem);
        relAmEscort.setAdapter(amEscortAdapter);
    }

    private List<RouteNodeInfo> getRouteNodeInfo() {
        List<RouteNodeInfo> routeNodeInfoList = new ArrayList<>();
        RouteNodeInfo shengHang = new RouteNodeInfo();
        shengHang.setEstimateArriveTime("2020-10-10 11:00:00");
        shengHang.setCurrent(false);
        shengHang.setStatus(0);
        shengHang.setName("省行");
        routeNodeInfoList.add(shengHang);

        RouteNodeInfo huangpu = new RouteNodeInfo();
        huangpu.setEstimateArriveTime("2020-10-10 11:00:00");
        huangpu.setCurrent(false);
        huangpu.setStatus(0);
        huangpu.setName("黄埔");
        routeNodeInfoList.add(huangpu);

        RouteNodeInfo minghang = new RouteNodeInfo();
        minghang.setEstimateArriveTime("2020-10-10 11:00:00");
        minghang.setCurrent(false);
        minghang.setStatus(0);
        minghang.setName("闵行");
        routeNodeInfoList.add(minghang);

        RouteNodeInfo hongqiao = new RouteNodeInfo();
        hongqiao.setEstimateArriveTime("2020-10-10 11:00:00");
        hongqiao.setCurrent(false);
        hongqiao.setStatus(0);
        hongqiao.setName("虹桥");
        routeNodeInfoList.add(hongqiao);

        RouteNodeInfo shanghai = new RouteNodeInfo();
        shanghai.setEstimateArriveTime("2020-10-10 11:00:00");
        shanghai.setCurrent(true);
        shanghai.setStatus(0);
        shanghai.setName("上海");
        routeNodeInfoList.add(shanghai);

        RouteNodeInfo hainan = new RouteNodeInfo();
        hainan.setEstimateArriveTime("2020-10-10 11:00:00");
        hainan.setCurrent(false);
        hainan.setStatus(1);
        hainan.setName("海南");
        routeNodeInfoList.add(hainan);

        RouteNodeInfo qinghai = new RouteNodeInfo();
        qinghai.setEstimateArriveTime("2020-10-10 11:00:00");
        qinghai.setCurrent(false);
        qinghai.setStatus(2);
        qinghai.setName("青海");
        routeNodeInfoList.add(qinghai);

        return routeNodeInfoList;
    }

    private OnItemClickListener amEscortItem = (adapter, view, position) -> {
        List<?> data = adapter.getData();
        RouteNodeInfo routeNodeInfo = (RouteNodeInfo) data.get(position);
        Log.i("routeNodeInfo = " + routeNodeInfo.toString());
    };


    private void pmInitData(EscortInfo escortInfo) {

    }

    @OnClick({R.id.ivBack, R.id.ivEscortHandover, R.id.ivEscortHandoverPM})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                popBackStack();
                break;
            case R.id.ivEscortHandover:
                //上午押运交接
                break;
            case R.id.ivEscortHandoverPM:
                //下午押运交接
                break;
        }
    }

    @Override
    public boolean getEnableLoadMore() {
        return false;
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
