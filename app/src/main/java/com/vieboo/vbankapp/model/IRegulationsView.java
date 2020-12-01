package com.vieboo.vbankapp.model;

import android.widget.EditText;

import com.example.toollib.data.base.IBaseListView;
import com.vieboo.vbankapp.data.RegulationsVO;

import java.util.List;

public interface IRegulationsView extends IBaseListView {

    EditText getEditText();

    void refreshRegulationsSuccess(List<RegulationsVO> regulationsVOList);

    void loadMoreRegulationsSuccess(List<RegulationsVO> regulationsVOList);

}
