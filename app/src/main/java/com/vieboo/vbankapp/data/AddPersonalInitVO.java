package com.vieboo.vbankapp.data;

import java.util.ArrayList;
import java.util.List;

public class AddPersonalInitVO {

    private List<DepartmentVO> departmentVOS;
    private List<PositionsVO> positionsVOS;
    private List<AuthVO> authVOS;

    public List<DepartmentVO> getDepartmentVOS() {
        if (departmentVOS == null) {
            return new ArrayList<>();
        }
        return departmentVOS;
    }

    public void setDepartmentVOS(List<DepartmentVO> departmentVOS) {
        this.departmentVOS = departmentVOS;
    }

    public List<PositionsVO> getPositionsVOS() {
        if (positionsVOS == null) {
            return new ArrayList<>();
        }
        return positionsVOS;
    }

    public void setPositionsVOS(List<PositionsVO> positionsVOS) {
        this.positionsVOS = positionsVOS;
    }

    public List<AuthVO> getAuthVOS() {
        if (authVOS == null) {
            return new ArrayList<>();
        }
        return authVOS;
    }

    public void setAuthVOS(List<AuthVO> authVOS) {
        this.authVOS = authVOS;
    }
}
