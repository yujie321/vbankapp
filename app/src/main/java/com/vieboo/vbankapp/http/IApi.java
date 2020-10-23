package com.vieboo.vbankapp.http;

import com.example.toollib.http.HttpResult;
import com.vieboo.vbankapp.data.AuthVO;
import com.vieboo.vbankapp.data.DepartmentVO;
import com.vieboo.vbankapp.data.InOutVO;
import com.vieboo.vbankapp.data.NoticeListVO;
import com.vieboo.vbankapp.data.PassengerVO;
import com.vieboo.vbankapp.data.PositionsVO;
import com.vieboo.vbankapp.data.PunchRecordVO;
import com.vieboo.vbankapp.data.RecordVO;
import com.vieboo.vbankapp.data.SecureVO;
import com.vieboo.vbankapp.data.StaticTodaySummeryVo;
import com.vieboo.vbankapp.data.VQDResultVO;
import com.vieboo.vbankapp.data.VQDSystemRecordVo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface IApi {


    /**
     * 获取当日总览
     *
     * @return observable
     */
    @GET("/microservice-pad/rest/static/todaysummary")
    Observable<HttpResult<StaticTodaySummeryVo>> toDaySummary();


    /**
     * 当日客流及安保运营统计情况
     *
     * @return Observable
     */
    @GET("microservice-pad/rest/static/passenger")
    Observable<HttpResult<PassengerVO>> passenger();

    /**
     * 查询统计
     * 通知通告
     */
    @GET("/microservice-pad/rest/findNotice")
    Observable<HttpResult<List<NoticeListVO>>> getNoticeList();

    /**
     * 阅读通知公告
     */
    @GET("/microservice-pad/rest/readNotice")
    Observable<HttpResult<String>> readNotice();

    /**
     * 查询员工
     */
    @GET("/microservice-pad/rest/findPerson")
    Observable<HttpResult<List<PunchRecordVO>>> findPerson(@Query("pageNum") int pageNum, @Query("size") int size);


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

    /**
     * 查询员工
     */
    @GET("/microservice-pad/rest/vqd")
    Observable<HttpResult<VQDSystemRecordVo>> findVQD(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 查询记录
     */
    @GET("/microservice-pad/rest/secure")
    Observable<HttpResult<SecureVO>> findSecure(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 查询多个人员进出登记信息
     */
    @GET("/microservice-pad/rest/personinout")
    Observable<HttpResult<InOutVO>> findPersonInOut(@Query("beginTime") String beginTime, @Query("endTime") String endTime,
                                                    @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
}
