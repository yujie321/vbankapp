package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseView;
import com.sdses.idCard.IdInfo;
import com.vieboo.vbankapp.data.SpinnerVO;

import java.util.List;

public interface IAddPersonalView extends IBaseView {

    /**
     * 设置部门
     */
    void setDepartment(List<SpinnerVO> spinnerVOS);

    /**
     * 设置职务
     */
    void setPositions(List<SpinnerVO> spinnerVOS);

    /**
     * 设置权限
     */
    void setJurisdiction(List<SpinnerVO> spinnerVOS);

    /**
     * 选中的部门id
     * @return id
     */
    int getDepartment();


    void getIdInfo(IdInfo info);

    void notObtained();
}
