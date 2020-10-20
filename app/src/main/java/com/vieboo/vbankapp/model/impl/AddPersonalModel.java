package com.vieboo.vbankapp.model.impl;

import com.example.toollib.data.BaseModule;
import com.example.toollib.data.base.BaseCallback;
import com.example.toollib.http.HttpResult;
import com.example.toollib.http.observer.BaseHttpRxObserver;
import com.example.toollib.http.observer.BaseHttpZipRxObserver;
import com.example.toollib.http.util.RxUtils;
import com.vieboo.vbankapp.data.AddPersonalInitVO;
import com.vieboo.vbankapp.data.AuthVO;
import com.vieboo.vbankapp.data.DepartmentVO;
import com.vieboo.vbankapp.data.PositionsVO;
import com.vieboo.vbankapp.data.SpinnerVO;
import com.vieboo.vbankapp.http.ServiceUrl;
import com.vieboo.vbankapp.model.IAddPersonalModel;
import com.vieboo.vbankapp.model.IAddPersonalView;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.functions.Function3;
import io.reactivex.schedulers.Schedulers;

public class AddPersonalModel extends BaseModule<IAddPersonalView> implements IAddPersonalModel {

    @Override
    public void initData() {
        //部门
        Observable<HttpResult<List<DepartmentVO>>> departmentZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().findDepartment().subscribeOn(Schedulers.io()));

        //职务
        Observable<HttpResult<List<PositionsVO>>> positionsZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().findPositions().subscribeOn(Schedulers.io()));

        //权限
        Observable<HttpResult<List<AuthVO>>> authZip = RxUtils.getObservableZip(
                ServiceUrl.getUserApi().findAuth().subscribeOn(Schedulers.io()));

        Observable<Object> compose = Observable.zip(departmentZip, positionsZip, authZip,
                (Function3<HttpResult<List<DepartmentVO>>, HttpResult<List<PositionsVO>>,
                        HttpResult<List<AuthVO>>, Object>) (departmentVO, positionsVO, authVO) -> {
                    AddPersonalInitVO addPersonalInitVO = new AddPersonalInitVO();
                    addPersonalInitVO.setDepartmentVOS(departmentVO.getData());
                    addPersonalInitVO.setPositionsVOS(positionsVO.getData());
                    addPersonalInitVO.setAuthVOS(authVO.getData());
                    return addPersonalInitVO;
                }).compose(mViewRef.get().bindLifecycle());
        BaseHttpZipRxObserver instance = BaseHttpZipRxObserver.getInstance();
        instance.setContext(mContext.get());
        instance.httpZipObserver(compose, new BaseCallback() {
            @Override
            public void onSuccess(Object object) {
                AddPersonalInitVO addPersonalInitVO = (AddPersonalInitVO) object;
                setDepartment(addPersonalInitVO.getDepartmentVOS());
                setPositions(addPersonalInitVO.getPositionsVOS());
                setJurisdiction(addPersonalInitVO.getAuthVOS());
            }
        });
    }

    private void setDepartment(List<DepartmentVO> departmentVOS) {
        List<SpinnerVO> spinnerVOS = new ArrayList<>();
        for (DepartmentVO departmentVO : departmentVOS) {
            SpinnerVO spinnerVO = new SpinnerVO();
            spinnerVO.setId(departmentVO.getId());
            spinnerVO.setName(departmentVO.getDepartmentName());
            spinnerVOS.add(spinnerVO);
        }
        mViewRef.get().setDepartment(spinnerVOS);
    }

    private void setPositions(List<PositionsVO> positionsVOS) {
        List<SpinnerVO> spinnerVOS = new ArrayList<>();
        for (PositionsVO positionsVO : positionsVOS) {
            SpinnerVO spinnerVO = new SpinnerVO();
            spinnerVO.setId(positionsVO.getId());
            spinnerVO.setName(positionsVO.getPositionName());
            spinnerVOS.add(spinnerVO);
        }
        mViewRef.get().setPositions(spinnerVOS);
    }

    private void setJurisdiction(List<AuthVO> authVOS) {
        List<SpinnerVO> spinnerVOS = new ArrayList<>();
        for (AuthVO authVO : authVOS) {
            SpinnerVO spinnerVO = new SpinnerVO();
            spinnerVO.setId(authVO.getId());
            spinnerVO.setName(authVO.getAuthExplain());
            spinnerVOS.add(spinnerVO);
        }
        mViewRef.get().setJurisdiction(spinnerVOS);
    }



}