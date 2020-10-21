package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseListView;
import com.vieboo.vbankapp.data.SecureResultVO;

import java.util.List;

public interface ISecurityOperateView extends IBaseListView {


    void refreshSecure(List<SecureResultVO> secureResultVOS);

    void loadMoreSecure(List<SecureResultVO> secureResultVOS);

}
