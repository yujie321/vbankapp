package com.vieboo.vbankapp.http;

import com.example.toollib.http.HttpResult;
import com.vieboo.vbankapp.data.AuthVO;
import com.vieboo.vbankapp.data.DepartmentVO;
import com.vieboo.vbankapp.data.NoticeListVO;
import com.vieboo.vbankapp.data.PassengerVO;
import com.vieboo.vbankapp.data.PositionsVO;
import com.vieboo.vbankapp.data.PunchRecordVO;
import com.vieboo.vbankapp.data.RecordVO;
import com.vieboo.vbankapp.data.StaticTodaySummeryVo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

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

    /**
     * 查询员工
     */
    @GET("/microservice-pad/rest/findPerson")
    Observable<HttpResult<List<PunchRecordVO>>> findPerson(@Query("pageNum") int pageNum ,@Query("size") int size);


    /**
     * 查询员工今日打卡记录
     */
    @GET("/microservice-pad/rest/findPunchRecord")
    Observable<HttpResult<List<RecordVO>>> findPunchRecord();


    /**
     * 上班打卡
     */
    @GET("/microservice-pad/rest/clockIn")
    Observable<HttpResult<String>> clockIn(@Query("id") String id);

    /**
     * 下班打卡
     */
    @GET("/microservice-pad/rest/clockOut")
    Observable<HttpResult<String>> clockOut(@Query("id") String id);

    /**
     * 部门查询
     */
    @GET("/microservice-pad/rest/findDepartment")
    Observable<HttpResult<List<DepartmentVO>>> findDepartment();

    /**
     * 职务查询
     */
    @GET("/microservice-pad/rest/findPositions")
    Observable<HttpResult<List<PositionsVO>>> findPositions();

    /**
     * 权限查询
     */
    @GET("/microservice-pad/rest/findAuth")
    Observable<HttpResult<List<AuthVO>>> findAuth();

}
