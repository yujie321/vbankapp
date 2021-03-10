package com.vieboo.vbankapp.model;

import com.example.toollib.data.base.IBaseListView;
import com.vieboo.vbankapp.data.RecordPlan;

import java.util.List;

public interface IRecordManagerView extends IBaseListView {
    void refreshRecordPlan(List<RecordPlan> recordPlans);

    void loadMoreRecordPlan(List<RecordPlan> recordPlans);
}
