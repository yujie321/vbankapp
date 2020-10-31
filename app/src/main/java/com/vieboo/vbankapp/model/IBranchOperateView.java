package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseView;
import com.vieboo.vbankapp.data.PassengerSummeryVo;
import com.vieboo.vbankapp.data.SecureRecordVo;

import java.util.List;

public interface IBranchOperateView extends IBaseView {
    void setLast7dayPeriodStatic(List<SecureRecordVo> secureRecordVoList);

    void setTodayPassengerStatic(List<SecureRecordVo> secureRecordVoList);

    void setTodayPassengerSummery(PassengerSummeryVo passengerSummeryVo);
}
