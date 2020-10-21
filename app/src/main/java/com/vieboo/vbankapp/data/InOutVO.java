package com.vieboo.vbankapp.data;


import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class InOutVO {
    private Integer total;
    @SerializedName(value = "datas")
    private List<PersonInOutVO> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<PersonInOutVO> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<PersonInOutVO> data) {
        this.data = data;
    }
}
