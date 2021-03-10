package com.vieboo.vbankapp.http;

import com.example.toollib.http.HttpResult;
import com.vieboo.vbankapp.data.AuthVO;
import com.vieboo.vbankapp.data.DepartmentVO;
import com.vieboo.vbankapp.data.DeviceStatusVo;
import com.vieboo.vbankapp.data.EscortInfo;
import com.vieboo.vbankapp.data.InOutVO;
import com.vieboo.vbankapp.data.NoticeListVO;
import com.vieboo.vbankapp.data.PadInfoVo;
import com.vieboo.vbankapp.data.PassengerSummeryVo;
import com.vieboo.vbankapp.data.PassengerVO;
import com.vieboo.vbankapp.data.PlayListVo;
import com.vieboo.vbankapp.data.PositionsVO;
import com.vieboo.vbankapp.data.PunchRecordVO;
import com.vieboo.vbankapp.data.RecordPlan;
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
    @GET("rest/static/todaysummary")
    Observable<HttpResult<StaticTodaySummeryVo>> toDaySummary();


    /**
     * 当日客流及安保运营统计情况
     *
     * @return Observable
     */
    @GET("rest/static/passenger")
    Observable<HttpResult<PassengerVO>> passenger();

    /**
     * 查询统计
     * 通知通告
     */
    @GET("rest/findNotice")
    Observable<HttpResult<List<NoticeListVO>>> getNoticeList();

    /**
     * 阅读通知公告
     */
    @GET("rest/readNotice")
    Observable<HttpResult<String>> readNotice(@Query("id") int id);

    /**
     * 查询员工
     */
    @GET("rest/findPerson")
    Observable<HttpResult<List<PunchRecordVO>>> findPerson(@Query("type") String type, @Query("pageNum") int pageNum, @Query("size") int size);


    /**
     * 查询员工今日打卡记录
     */
    @GET("rest/findPunchRecord")
    Observable<HttpResult<List<RecordVO>>> findPunchRecord();


    /**
     * 上班打卡
     */
    @POST("rest/clockIn")
    Observable<HttpResult<String>> clockIn(@Query("personHead") String personHead, @Query("id") String id);

    /**
     * 下班打卡
     */
    @POST("rest/clockOut")
    Observable<HttpResult<String>> clockOut(@Query("personHead") String personHead, @Query("id") String id);

    /**
     * 部门查询
     */
    @GET("rest/findDepartment")
    Observable<HttpResult<List<DepartmentVO>>> findDepartment();

    /**
     * 职务查询
     */
    @GET("rest/findPositions")
    Observable<HttpResult<List<PositionsVO>>> findPositions();

    /**
     * 权限查询
     */
    @GET("rest/findAuth")
    Observable<HttpResult<List<AuthVO>>> findAuth();

    //设备状态查询
    /**
     * 查询VQD记录
     */
    @GET("rest/vqd")
    Observable<HttpResult<VQDSystemRecordVo>> findVQD(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 查询设备在线状态
     */
    @GET("rest/vqd/static")
    Observable<HttpResult<DeviceStatusVo>> getVqdStatic();

    /**
     * 查询记录
     */
    @GET("rest/secure")
    Observable<HttpResult<SecureVO>> findSecure(@Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    /**
     * 获取当日安保运营统计
     */
    @GET("rest/secure/todayperiodstatic")
    Observable<HttpResult<List<SecureRecordVo>>> todaySecureStatic();

    //进出管理统计

    /**
     * 查询多个人员进出登记信息
     */
    @GET("rest/personinout")
    Observable<HttpResult<InOutVO>> findPersonInOut(@Query("beginTime") String beginTime, @Query("endTime") String endTime,
                                                    @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);


    /**
     * 当日进出记录趋势图
     */
    @GET("rest/personinout/todayperiodstatic")
    Observable<HttpResult<List<SecureRecordVo>>> todayPersonInoutStatic();

    //网点运营统计
    /**
     * 获取当日客流和VIP到访人数
     */
    @GET("rest/passenger/todaysummery")
    Observable<HttpResult<PassengerSummeryVo>> todayPassengerSummery();


    /**
     * 获取近七日客流趋势
     */
    @GET("rest/passenger/last7dayperiodstatic")
    Observable<HttpResult<List<SecureRecordVo>>> last7dayPeriodStatic();

    /**
     * 获取普通客户当日客流
     */
    @GET("rest/passenger/todayperiodstatic")
    Observable<HttpResult<List<SecureRecordVo>>> todayPassengerStatic();

    /**
     * 获取VIP客户当日客流
     */
    @GET("rest/passenger/todayperiodstatic/vip")
    Observable<HttpResult<List<SecureRecordVo>>> todayVipPassengerStatic();

    /**
     * 查询规章制度
     */
    @GET("rest/findRegulations")
    Observable<HttpResult<List<RegulationsVO>>> findRegulations(@Query("name") String name, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);

    //人员管控
    /**
     * 添加人员
     */
    @POST("rest/addPerson")
    Observable<HttpResult<String>> addPerson(@Body RequestBody body);

    /**
     * 上传人员图片
     */
    @POST("rest/file/upload")
    Observable<HttpResult<String>> upload(@Body RequestBody body);

    /**
     * 获取pad信息
     */
    @GET("rest/padinfo/config")
    Observable<HttpResult<PadInfoVo>> getPadInfo(@Query("ip") String ip);

    /**
     * 获取pad播放列表
     */
    @GET("rest/padinfo/playinfo")
    Observable<HttpResult<PlayListVo>> getPlayInfo(@Query("ip") String ip, @Query("isSub") boolean bSub);

    /**
     * 查询押运信息
     * @param date 日期
     * @return HttpResult
     */
    @GET("rest/escort")
    Observable<HttpResult<List<EscortInfo>>> getEscort(@Query("date") String date);
    /*
    * 获取录像计划列表
     */
    @GET("rest/record/plan")
    Observable<HttpResult<List<RecordPlan>>> getRecordPlans( @Query("key") String name, @Query("pageNum") int pageNum, @Query("pageSize") int pageSize);
}
