package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseListView;
import com.vieboo.vbankapp.data.VQDResultVO;

import java.util.List;

public interface IDeviceStatusView extends IBaseListView {

    void refreshVQD(List<VQDResultVO> vqdResultVOS);

    void loadMoreVQD(List<VQDResultVO> vqdResultVOS);

}
