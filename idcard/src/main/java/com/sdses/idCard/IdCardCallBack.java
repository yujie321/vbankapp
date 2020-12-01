package com.sdses.idCard;

public interface IdCardCallBack {

    void onSuccess(IdInfo idInfo);

    void onError(String error);

}
