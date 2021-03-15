package com.sdses.idCard;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;

import com.sdses.bean.ID2Data;
import com.sdt.Common;
import com.sdt.Sdtapi;

public class IdCardHelper {

    private static final String TAG = "IdCardHelper";

    /**
     * 连续读卡线程
     */
    private Thread mContinueReadCardThread;

    /**
     * 连续读卡线程关闭
     */
    private boolean mThreadIsRunning = false;

    /**
     * 连续读卡间隔，单位毫秒
     */
    private static final int m_nReadCardDelay = 2500;

    /**
     * 连续读卡计数
     */
    private int m_nReadCardCount = 0;

    /**
     * usb设备拔出消息号
     */
    private static final int MSG_USBDEV_DETACHED = 0X11;
    /**
     * usb设备未授权
     */
    private static final int MSG_USBDEV_NOAUTHORIZE = 0X12;
    /**
     * 读卡成功消息号
     */
    private static final int MSG_READCARD_OK = 0X31;

    private static final int MSG_API_ISNULL = 0X41;
    private static final int MSG_GETSAM_ISOK = 0X42;
    /**
     * 读卡设备usb连接状态
     */
    private boolean m_bSAMUSBState = false;

    private Common common;
    /**
     * 读卡必需
     */
    private Sdtapi sdtapi;

    private IdCardCallBack idCardCallBack;

    private IdCardHelper() {
    }

    public static IdCardHelper getInstance() {
        return IdCardHelper.IdCardHelperHolder.idCardHelper;
    }

    private static class IdCardHelperHolder {
        private static IdCardHelper idCardHelper = new IdCardHelper();
    }

    public IdCardHelper init(Activity activity) {
        common = new Common();
        try {
            sdtapi = new Sdtapi(activity);
            m_bSAMUSBState = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        new Thread(new TestBussiness()).start();

        //意图过滤器
        IntentFilter filter = new IntentFilter();
        //USB设备拔出
        filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
        //自定义的USB设备请求授权
        filter.addAction(common.ACTION_USB_PERMISSION);
        activity.registerReceiver(mUsbReceiver, filter);
        return this;
    }


    public void setIdCardCallBack(IdCardCallBack idCardCallBack) {
        this.idCardCallBack = idCardCallBack;
    }

    /**
     * 开启读卡
     */
    public IdCardHelper openContinueReadCard() {
        if (mContinueReadCardThread == null) {
            mContinueReadCardThread = new Thread(new ContinueReadCardThread());
        }
        mContinueReadCardThread.start();
        return this;
    }

    public void closeContinueReadCard() {
        mThreadIsRunning = false;
        if (mContinueReadCardThread != null) {
            mContinueReadCardThread = null;
        }
        idCardCallBack = null;
    }


    /**
     * 连续读卡线程
     *
     * @author Think
     */
    private class ContinueReadCardThread implements Runnable {

        private Message msg;// 用来发消息

        @Override
        public void run() {
            // TODO Auto-generated method stub
            mThreadIsRunning = true; //线程可运行，false时会退出循环
            m_nReadCardCount = 0;    //读卡次数计数
            while (mThreadIsRunning) {
                Log.w(TAG, "读卡线程开启一次读卡过程");
                byte[] data = new byte[1280];
                if (readCard(data)) {
                    m_nReadCardCount++;
                    ID2Data _id2data = new ID2Data();
                    _id2data.clearID2DataRAW();
                    _id2data.decode_debug(data);
                    _id2data.rePackage();
                    msg = MyHandler.obtainMessage(MSG_READCARD_OK, _id2data);
                    MyHandler.sendMessage(msg);
                    SystemClock.sleep(m_nReadCardDelay);
                } else {
                    Log.w(TAG, "读卡线程未读到卡延时开始");
                    SystemClock.sleep(300); //未读到卡时，间隔要小一些
                    Log.w(TAG, "读卡线程未读到卡延时结束");
                }
            }
        }
    }

    /**
     * 读卡
     *
     * @param data 1280字节 byte 数组
     * @return
     */
    public boolean readCard(byte[] data) {
        if (data == null || data.length < 1280) {
            return false;
        }
        if (!m_bSAMUSBState) {
            return false;
        }
        int nRet = 0;
        int[] txtDataLen = new int[1];
        int[] photoDataLen = new int[1];
        byte[] txtData = new byte[256];
        byte[] photoData = new byte[1024];


        try {
            sdtapi.SDT_StartFindIDCard();//寻卡
            sdtapi.SDT_SelectIDCard();//选卡
            nRet = sdtapi.SDT_ReadBaseMsg(txtData, txtDataLen, photoData, photoDataLen);
            if (nRet == 0x90) {
                System.arraycopy(txtData, 0, data, 0, 256);
                System.arraycopy(photoData, 0, data, 256, 1024);
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public class TestBussiness implements Runnable {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            try {
                Sdtapi api = sdtapi;
                if (api == null) {
                    MyHandler.sendEmptyMessage(MSG_API_ISNULL);
                }
                byte[] sam = new byte[128];

                api.SDT_GetSAMID(sam);
                if (sam[0] != 0x0) {
                    Message msg = new Message();
                    msg.what = MSG_GETSAM_ISOK;
                    Bundle bundle = new Bundle();
                    bundle.putString("sam", new String(sam));
                    msg.setData(bundle);
                    MyHandler.sendMessage(msg);
                }
                Log.w(TAG, new String(sam));
                if (idCardCallBack != null) {
                    idCardCallBack.onError(new String(sam));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } //读卡必需
        }
    }

    @SuppressLint("HandlerLeak")
    private Handler MyHandler = new Handler() {

        public void handleMessage(Message msg) {
            if (msg.what == MSG_USBDEV_DETACHED) { //
                mThreadIsRunning = false;
                if (idCardCallBack != null) {
                    idCardCallBack.onError("USB设备异常或无连接，应用程序即将关闭");
                }
            } else if (msg.what == MSG_USBDEV_NOAUTHORIZE) {
                //usb设备已经重新授权，应用重新启动
                if (idCardCallBack != null) {
                    //idCardCallBack.onError("usb设备已经重新授权，应用重新启动");
                }
            } else if (msg.what == MSG_READCARD_OK) {
                ID2Data _id2data = (ID2Data) msg.obj;
                String strTemp = _id2data.getmID2Txt().getmName() + "\n" +
                        _id2data.getmID2Txt().getmGender() + "\n" +
                        _id2data.getmID2Txt().getmNational() + "\n" +
                        _id2data.getmID2Txt().getmBirthYear() + "年" +
                        _id2data.getmID2Txt().getmBirthMonth() + "月" +
                        _id2data.getmID2Txt().getmBirthDay() + "日" + "\n" +
                        _id2data.getmID2Txt().getmID2Num() + "\n" +
                        _id2data.getmID2Txt().getmAddress() + "\n" +
                        _id2data.getmID2Txt().getmIssue() + "\n" +
                        _id2data.getmID2Txt().getmBegin() + "-" +
                        _id2data.getmID2Txt().getmEnd();
                strTemp += "\n 读卡次数:" + m_nReadCardCount;
                Log.e(TAG, "身份信息:" + strTemp);

                Bitmap bm = BitmapFactory.decodeByteArray(_id2data.getmID2Pic().getHeadFromCard(), 0, 38862);
                if (idCardCallBack != null) {
                    IdInfo idInfo = new IdInfo();
                    idInfo.setName(_id2data.getmID2Txt().getmName());
                    idInfo.setIdCardNum(_id2data.getmID2Txt().getmID2Num());
                    idInfo.setSex(_id2data.getmID2Txt().getmGender());
                    idInfo.setNational(_id2data.getmID2Txt().getmNational());
                    String birthDay = _id2data.getmID2Txt().getmBirthYear() + "年" +
                            _id2data.getmID2Txt().getmBirthMonth() + "月" +
                            _id2data.getmID2Txt().getmBirthDay() + "日";
                    idInfo.setBirthday(birthDay);
                    idInfo.setAddress(_id2data.getmID2Txt().getmAddress());
                    idInfo.setIssue(_id2data.getmID2Txt().getmIssue());
                    idInfo.setIndate(_id2data.getmID2Txt().getmBegin() + "-" + _id2data.getmID2Txt().getmEnd());
                    idInfo.setPhotoBmp(bm);
                    idCardCallBack.onSuccess(idInfo);
                }
            } else if (msg.what == MSG_API_ISNULL) {
                if (idCardCallBack != null) {
                    idCardCallBack.onError("api is null");
                }
            } else if (msg.what == MSG_GETSAM_ISOK) {
                String saminfo = msg.getData().getString("sam");
                //m_tvInfo.setText("获取sam模块号成功 "+saminfo);
            }

        }
    };

    private final BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            //
            if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                UsbDevice device = intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                String deviceName = device.getDeviceName();
                if (device != null && device.equals(deviceName)) {

                    Message msg = new Message();
                    msg.what = MSG_USBDEV_DETACHED;
                    msg.obj = "USB设备拔出，应用程序即将关闭。";
                    MyHandler.sendMessage(msg);
                }
            } else if (common.ACTION_USB_PERMISSION.equals(action)) {//USB设备未授权，从SDTAPI中发出的广播
                Message msg = new Message();
                msg.what = MSG_USBDEV_NOAUTHORIZE;
                msg.obj = "USB设备无权限";
                MyHandler.sendMessage(msg);
            }
        }
    };

}
