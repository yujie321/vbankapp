package com.vieboo.vbankapp.data;

import java.util.ArrayList;
import java.util.List;

public class VQDSystemRecordVo {

    private Integer total;
    private List<VQDResultVO> datas;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<VQDResultVO> getData() {
        if (datas == null) {
            return new ArrayList<>();
        }
        return datas;
    }

    public void setData(List<VQDResultVO> data) {
        this.datas = data;
    }
}
