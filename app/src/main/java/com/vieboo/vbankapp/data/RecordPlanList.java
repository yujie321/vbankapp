package com.vieboo.vbankapp.data;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecordPlanList {
    private Integer total;
    @SerializedName(value = "datas")
    private List<RecordPlan> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<RecordPlan> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<RecordPlan> data) {
        this.data = data;
    }
}
