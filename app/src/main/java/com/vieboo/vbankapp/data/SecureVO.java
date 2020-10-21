package com.vieboo.vbankapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class SecureVO {

    private Integer total;
    @SerializedName(value = "datas")
    private List<SecureResultVO> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<SecureResultVO> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<SecureResultVO> data) {
        this.data = data;
    }
}
