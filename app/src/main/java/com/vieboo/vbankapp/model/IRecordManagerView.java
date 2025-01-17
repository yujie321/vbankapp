package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseListView;
import com.vieboo.vbankapp.data.Record;
import com.vieboo.vbankapp.data.RecordPlan;

import java.util.List;

public interface IRecordManagerView extends IBaseListView {
    void refreshRecordPlan(List<RecordPlan> recordPlans);

    void loadMoreRecordPlan(List<RecordPlan> recordPlans);

    void setRecordList(List<Record> records);

    void setRecordDetail(Record record);
}
