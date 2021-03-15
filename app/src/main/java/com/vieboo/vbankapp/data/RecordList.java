package com.vieboo.vbankapp.data;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class RecordList {
    private Integer total;
    @SerializedName(value = "datas")
    private List<Record> data;

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public List<Record> getData() {
        if (data == null) {
            return new ArrayList<>();
        }
        return data;
    }

    public void setData(List<Record> data) {
        this.data = data;
    }
}
