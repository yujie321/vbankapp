package com.vieboo.vbankapp.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.example.toollib.base.BaseFragment;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.adapter.PunchRecordAdapter;
import com.vieboo.vbankapp.adapter.StaffStyleAdapter;
import com.vieboo.vbankapp.data.PunchRecordVO;
import com.vieboo.vbankapp.data.RecordVO;
import com.vieboo.vbankapp.face.FaceRectView;
import com.vieboo.vbankapp.model.IPersonnelControlModel;
import com.vieboo.vbankapp.model.IPersonnelControlView;
import com.vieboo.vbankapp.model.impl.PersonnelControlModel;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 人员管控
 */
public class PersonnelControlFragment extends BaseFragment<IPersonnelControlModel> implements IPersonnelControlView {

    @BindView(R.id.faceRectView)
    FaceRectView faceRectView;
    @BindView(R.id.rvStaffStyle)
    RecyclerView rvStaffStyle;
    @BindView(R.id.rvPunchRecord)
    RecyclerView rvPunchRecord;

    public static PersonnelControlFragment newInstance() {
        Bundle args = new Bundle();
        PersonnelControlFragment fragment = new PersonnelControlFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected IPersonnelControlModel initModule() {
        return new PersonnelControlModel();
    }

    @Override
    public int getContentView() {
        return R.layout.fragment_personnel_control;
    }

    @Override
    public void initView() {
        //员工风采
        rvStaffStyle.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        //rvStaffStyle.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, false));
        iModule.getStaffStyle();
        //打卡记录
        rvPunchRecord.setLayoutManager(new StaggeredGridLayoutManager(5, StaggeredGridLayoutManager.VERTICAL));
        //rvPunchRecord.addItemDecoration(new GridSpacingItemDecoration(spanCount, spacing, false));
        iModule.getPunchRecord();
    }

    @OnClick({R.id.ivBack, R.id.ivClockIn, R.id.ivClockOut, R.id.ivAddPersonal})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ivBack:
                //返回
                popBackStack();
                break;
            case R.id.ivClockIn:
                //上班打卡
                iModule.clockIn();
                break;
            case R.id.ivClockOut:
                //下班打卡
                iModule.clockOut();
                break;
            case R.id.ivAddPersonal:
                //新增人员
                startFragment(AddPersonalFragment.newInstance());
                break;
        }
    }

    @Override
    public void setStaffStyle(List<PunchRecordVO> passengerVO) {
        //员工风采
        StaffStyleAdapter staffStyleAdapter = new StaffStyleAdapter();
        staffStyleAdapter.addData(passengerVO);
        rvStaffStyle.setAdapter(staffStyleAdapter);
    }

    @Override
    public void setPunchRecord(List<RecordVO> recordVOS) {
        PunchRecordAdapter passengerVO = new PunchRecordAdapter();
        passengerVO.addData(recordVOS);
        rvPunchRecord.setAdapter(passengerVO);
    }

    @Override
    public String getPersonalId() {
        // TODO: 2020/10/19 人脸比对成功后 返回 id
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
