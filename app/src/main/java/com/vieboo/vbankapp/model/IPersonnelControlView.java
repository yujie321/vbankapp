package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseView;
import com.vieboo.vbankapp.data.PunchRecordVO;
import com.vieboo.vbankapp.data.RecordVO;

import java.util.List;

public interface IPersonnelControlView extends IBaseView {


    void setStaffStyle(List<PunchRecordVO> passengerVO);

    void setPunchRecord(List<RecordVO> recordVOS);

    String getPersonalId();
}
