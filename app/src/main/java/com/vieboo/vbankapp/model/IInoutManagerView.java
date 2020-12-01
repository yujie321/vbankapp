package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseListView;
import com.vieboo.vbankapp.data.PersonInOutVO;
import com.vieboo.vbankapp.data.SecureRecordVo;

import java.util.List;

public interface IInoutManagerView extends IBaseListView {

    void refreshPersonInOut(List<PersonInOutVO> personInOutVOS);

    void loadMorePersonInOut(List<PersonInOutVO> personInOutVOS);

    void setAccessPersonInOut(List<PersonInOutVO> personInOutVOS);

    void setTodayPersoninoutStatic(List<SecureRecordVo> secureRecordVoList);
}
