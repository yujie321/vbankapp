package com.vieboo.vbankapp.http;

import com.example.toollib.http.HttpResult;
import com.vieboo.vbankapp.data.NoticeListVO;
import com.vieboo.vbankapp.data.PassengerVO;
import com.vieboo.vbankapp.data.StaticTodaySummeryVo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface IApi {


    /**
     * 获取当日总览
     * @return observable
     */
    @GET("/microservice-pad/rest/static/todaysummary")
    Observable<HttpResult<StaticTodaySummeryVo>> toDaySummary();


    /**
     * 当日客流及安保运营统计情况
     * @return Observable
     */
    @GET("/microservice-result/rest/face/passenger")
    Observable<HttpResult<PassengerVO>> passenger();

    /**
     * 查询统计
     * 通知通告
     */
    @GET("/microservice-pad/rest/findNotice")
    Observable<HttpResult<List<NoticeListVO>>> getNoticeList();
}
