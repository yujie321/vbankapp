package com.vieboo.vbankapp.data;

import java.util.ArrayList;
import java.util.List;

public class PlayListVo {
    private List<PlayInfo> playList;
    public List<PlayInfo> getData() {
        if (playList == null) {
            return new ArrayList<>();
        }
        return playList;
    }

    public void setData(List<SecureResultVO> data) {
        this.playList = playList;
    }
}

