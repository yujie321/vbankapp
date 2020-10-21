package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseListView;
import com.vieboo.vbankapp.data.PersonInOutVO;

import java.util.List;

public interface IInoutManagerView extends IBaseListView {

    void refreshPersonInOut(List<PersonInOutVO> personInOutVOS);

    void loadMorePersonInOut(List<PersonInOutVO> personInOutVOS);

    void setAccessPersonInOut(List<PersonInOutVO> personInOutVOS);

}
