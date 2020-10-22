package com.sdses.idCard;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbEndpoint;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;

import com.blankj.utilcode.util.Utils;
import com.sdses.bean.ID2PicNoLic;
import com.zkteco.android.IDReader.WLTService;
import com.zkteco.android.biometric.core.utils.LogHelper;
import com.zkteco.android.biometric.module.idcard.meta.IDCardInfo;
import com.zkteco.android.biometric.module.idcard.meta.IDCardReaderRetData;
import com.zkteco.android.biometric.module.idcard.meta.IDCardReaderSendData;
import com.zkteco.android.biometric.module.idcard.meta.IDPRPCardInfo;

public class IdCardHelper {
    private UsbManager mUsbManager;
    private UsbDevice usbDevice;
    private UsbDeviceConnection usbDeviceConnection;
    private UsbEndpoint inEndpoint;
    private UsbEndpoint outEndpoint;
    private final int vid = 1024;    //ID180 CH341 VID
    private final int pid = 50010;     //ID180 CH341 PID

    private IdCardHelper() {
    }

    public static IdCardHelper getInstance() {
        return IdCardHelper.IdCardHelperHolder.idCardHelper;
    }

    private static class IdCardHelperHolder {
        private static IdCardHelper idCardHelper = new IdCardHelper();
    }

    public void requestPermission(UsbDevice usbDevice) {
        String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
        PendingIntent mPermissionIntent = PendingIntent.getBroadcast(Utils.getApp(), 0, new Intent(ACTION_USB_PERMISSION), 0);
        if (!mUsbManager.hasPermission(usbDevice)) {
            mUsbManager.requestPermission(usbDevice, mPermissionIntent);
        }
    }

    public void open() {
        mUsbManager = (UsbManager) Utils.getApp().getSystemService(Context.USB_SERVICE);

        if (mUsbManager != null) {
            for (final UsbDevice usbDevice : mUsbManager.getDeviceList().values()) {
                if (usbDevice.getProductId() == pid && usbDevice.getVendorId() == vid) {
                    requestPermission(usbDevice);
                    requestPermission(usbDevice);
                    this.usbDevice = usbDevice;
                    break;
                }
            }
        }

        if (usbDevice == null) {
            return;
        }

        usbDeviceConnection = mUsbManager.openDevice(usbDevice);

        UsbInterface usbInterface = usbDevice.getInterface(0);

        for (int i = 0; i < usbInterface.getEndpointCount(); ++i) {
            UsbEndpoint endpoint = usbInterface.getEndpoint(i);
            if (endpoint.getAttributes() == 2) {
                if (endpoint.getDirection() == 128) {
                    inEndpoint = endpoint;

                } else if (endpoint.getDirection() == 0) {
                    outEndpoint = endpoint;
                }
            }
        }

        usbDeviceConnection.claimInterface(usbInterface, true);
    }

    public void close() {
        if (usbDeviceConnection != null)
            usbDeviceConnection.close();
        mUsbManager = null;
        usbDevice = null;
        inEndpoint = null;
        outEndpoint = null;
    }


    public IdInfo getIdInfo() {
        if (usbDevice == null) {
            return null;
        }

        if (findCard() && selectCard()) {
            return read();
        }

        return null;
    }

    private boolean findCard() {
        boolean ret;
        IDCardReaderSendData idCardReaderSendData = new IDCardReaderSendData();
        IDCardReaderRetData idCardReaderRetData = new IDCardReaderRetData();
        idCardReaderSendData.setCmd((byte) 32);
        idCardReaderSendData.setPara((byte) 1);
        this.controlDevice(idCardReaderSendData, idCardReaderRetData, 2000);

        if (idCardReaderRetData.getSw3() != -97) {
            ret = false;
        } else {
            ret = true;
        }

        return ret;
    }

    private boolean selectCard() {
        boolean ret;

        IDCardReaderSendData idCardReaderSendData = new IDCardReaderSendData();
        IDCardReaderRetData idCardReaderRetData = new IDCardReaderRetData();
        idCardReaderSendData.setCmd((byte) 32);
        idCardReaderSendData.setPara((byte) 2);
        this.controlDevice(idCardReaderSendData, idCardReaderRetData, 2000);
        if (idCardReaderRetData.getSw3() != -112) {
            ret = false;
        } else {
            ret = true;
        }

        return ret;
    }

    private IdInfo read() {
        IDCardInfo idCardInfo = new IDCardInfo();
        IDPRPCardInfo idprpCardInfo = new IDPRPCardInfo();
        boolean ret = this.readCard(0, idCardInfo, idprpCardInfo);

        Bitmap photoBmp = null;
        if (ret) {
            if (idCardInfo.getPhotolength() > 0) {
                ID2PicNoLic id2PicNoLic = new ID2PicNoLic();
                id2PicNoLic.decodeNohLic(idCardInfo.getPhoto());
                byte[] photo = id2PicNoLic.getHeadFromCard();
                photoBmp = BitmapFactory.decodeByteArray(photo, 0, photo.length);
            }
        }

        IdInfo idInfo = new IdInfo();
        if (photoBmp != null) {
            idInfo.setPhotoBmp(photoBmp);
            idInfo.setWidth(WLTService.imgWidth);
            idInfo.setHeight(WLTService.imgHeight);
        }

        idInfo.setName(idCardInfo.getName());
        idInfo.setSex(idCardInfo.getSex());
        idInfo.setAddress(idCardInfo.getAddress());
        idInfo.setNational(idCardInfo.getNation());
        idInfo.setBirthday(idCardInfo.getBirth());
        idInfo.setIdCardNum(idCardInfo.getId());
        idInfo.setIssue(idCardInfo.getDepart());
        idInfo.setIndate(idCardInfo.getValidityTime());
        return idInfo;
    }

    private boolean readCard(int action, IDCardInfo idCardInfo, IDPRPCardInfo idprpCardInfo) {
        long nTickSet = System.currentTimeMillis();
        int nRet = 0;

        while (System.currentTimeMillis() - nTickSet < 500L) {
            nRet = this.readCard_Imp(action, idCardInfo, idprpCardInfo);
            if (nRet > 0) {
                break;
            }
        }

        if (1 == nRet) {
            return true;
        } else {
            return false;
        }
    }

    private int readCard_Imp(int action, IDCardInfo idCardInfo, IDPRPCardInfo idprpCardInfo) {
        byte ret = 0;


        IDCardReaderSendData idCardReaderSendData = new IDCardReaderSendData();
        IDCardReaderRetData idCardReaderRetData = new IDCardReaderRetData();
        idCardReaderSendData.setCmd((byte) 48);
        if (action == 0) {
            idCardReaderSendData.setPara((byte) 1);
        } else {
            if (action != 1) {
                return ret;
            }

            idCardReaderSendData.setPara((byte) 16);
        }

        this.controlDevice(idCardReaderSendData, idCardReaderRetData, 2000);
        if (idCardReaderRetData.getSw3() != -112) {
            return ret;
        }

        int length = idCardReaderRetData.getDataLen();
        byte[] cardInfo = new byte[length];
        idCardReaderRetData.getData(cardInfo);
        int licType;
        if (action == 0) {
            licType = IDPRPCardInfo.CheckType(cardInfo, length);
            if (licType == 2) {
                idprpCardInfo.unPacket(cardInfo, length);
                ret = 2;
            } else if (licType == 1) {
                idCardInfo.unPacket(cardInfo, length);
                ret = 1;
            } else {
                ret = 0;
            }
        } else if (action == 1) {
            licType = IDPRPCardInfo.CheckTypeEx(cardInfo, length);
            if (licType == 2) {
                idprpCardInfo.unPacketExt(cardInfo, length);
                ret = 2;
            } else if (licType == 1) {
                idCardInfo.unPacketExt(cardInfo, length);
                ret = 1;
            } else {
                ret = 0;
            }
        }

        return ret;
    }


    private void controlDevice(IDCardReaderSendData idCardReaderSendData, IDCardReaderRetData idCardReaderRetData, int timeout) {
        long t1 = System.currentTimeMillis();

        byte[] buf = new byte[4096];
        this.read(buf, 4096, 5);

        int length = idCardReaderSendData.getPacketLen();
        byte[] inbuf = new byte[length];
        byte[] outbuf = new byte[4096];
        if (idCardReaderSendData.packet(inbuf)) {
            this.write(inbuf, length, timeout);
            if (this.read(outbuf, 4096, timeout) == 0) {

            } else {
                idCardReaderRetData.unPacket(outbuf);
            }
        }

        long t2 = System.currentTimeMillis();
        LogHelper.d("controlDevice:" + (t2 - t1));
    }

    private int read(byte[] buffer, int length, int timeout) {
        if (!this.isValidBufferLength(buffer, length)) {
            return 0;
        } else {
            long tickStart = System.currentTimeMillis();

            int ret;
            while ((ret = this.usbDeviceConnection.bulkTransfer(this.inEndpoint, buffer, length, timeout)) <= 0 && System.currentTimeMillis() - tickStart < (long) timeout) {
                try {
                    Thread.sleep(10L);
                } catch (InterruptedException var9) {
                    var9.printStackTrace();
                }
            }

            if (ret == -1) {

                return 0;
            } else if (ret <= 0) {

                return 0;
            } else {
                return ret;
            }
        }

    }


    private void write(byte[] buffer, int length, int timeout) {
        if (this.isValidBufferLength(buffer, length)) {
            this.usbDeviceConnection.bulkTransfer(this.outEndpoint, buffer, length, timeout);
        }
    }


    private boolean isValidBufferLength(byte[] buffer, int length) {
        if (null != buffer && buffer.length < length) {
            return false;
        } else {
            return true;
        }
    }
}
