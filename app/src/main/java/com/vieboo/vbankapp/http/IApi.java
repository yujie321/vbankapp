package com.vieboo.vbankapp.http;

import com.example.toollib.http.HttpResult;
import com.vieboo.vbankapp.data.AuthVO;
import com.vieboo.vbankapp.data.DepartmentVO;
import com.vieboo.vbankapp.data.InOutVO;
import com.vieboo.vbankapp.data.NoticeListVO;
import com.vieboo.vbankapp.data.PassengerSummeryVo;
import com.vieboo.vbankapp.data.PassengerVO;
import com.vieboo.vbankapp.data.PositionsVO;
import com.vieboo.vbankapp.data.PunchRecordVO;
import com.vieboo.vbankapp.data.RecordVO;
import com.vieboo.vbankapp.data.RegulationsVO;
import com.vieboo.vbankapp.data.SecureRecordVo;
import com.vieboo.vbankapp.data.SecureVO;
import com.vieboo.vbankapp.data.StaticTodaySummeryVo;
import com.vieboo.vbankapp.data.VQDSystemRecordVo;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
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
    Observable<HttpResult<String>> readNotice(@Query("id") int id);

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
     * 获取当日安保运营统计
     */
    @GET("/microservice-pad/rest/secure/todayperiodstatic")
    Observable<HttpResult<List<SecureRecordVo>>> todaySecureStatic();

    //进出管理统计

    /**
     * 查询多个人员进出登记信息
     */
    @GET("/microservice-pad/rest/personinout")
    Observable<HttpResult<InOutVO>> findPersonInOut(@Query("beginTime") String beginTime, @Query("endTime") String endTime,
                                                    @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);


    /**
     * 当日进出记录趋势图
     */
    @GET("microservice-pad/rest/personinout/todayperiodstatic")
    Observable<HttpResult<List<SecureRecordVo>>> todayPersonInoutStatic();

    //网点运营统计
    /**
     * 获取当日客流和VIP到访人数
     */
    @GET("/microservice-pad/rest/passenger/todaysummery")
    Observable<HttpResult<PassengerSummeryVo>> todayPassengerSummery();


    /**
     * 获取近七日客流趋势
     */
    @GET("microservice-pad/rest/passenger/last7dayperiodstatic")
    Observable<HttpResult<List<SecureRecordVo>>> last7dayPeriodStatic();

    /**
     * 获取当日客流统计
     */
    @GET("microservice-pad/rest/passenger/todayperiodstatic")
    Observable<HttpResult<List<SecureRecordVo>>> todayPassengerStatic();

    /**
     * 查询规章制度
     */
    @GET("microservice-pad/rest/findRegulations")
    Observable<HttpResult<List<RegulationsVO>>> findRegulations(@Query("name") String name, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    //人员管控
    /**
     * 添加人员
     */
    @POST("/microservice-pad/rest/addPerson")
    Observable<HttpResult<String>> addPerson(@Body RequestBody body);

    /**
     * 上传人员图片
     */
    @POST("/microservice-pad/rest/file/upload")
    Observable<HttpResult<String>> upload(@Body RequestBody body);

}
