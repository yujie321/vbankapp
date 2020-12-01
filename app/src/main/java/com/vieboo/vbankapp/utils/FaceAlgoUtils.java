package com.vieboo.vbankapp.utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.arcsoft.face.FaceFeature;
import com.arcsoft.face.FaceInfo;
import com.arcsoft.imageutil.ArcSoftImageUtil;
import com.blankj.utilcode.util.FileUtils;
import com.vieboo.vbankapp.service.FaceServer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class FaceAlgoUtils {

    // 算法所需的动态库文件
    public static final String[] LIBRARIES = new String[]{
            // 人脸相关
            "libarcsoft_face_engine.so",
            "libarcsoft_face.so",
            // 图像库相关
            "libarcsoft_image_util.so",
    };

    /**
     * 检查能否找到动态链接库，如果找不到，请修改工程配置
     *
     * @return 动态库是否存在
     */
    public static boolean checkSoFile(Context context) {

        File dir = new File(context.getApplicationInfo().nativeLibraryDir);
        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            return false;
        }
        List<String> libraryNameList = new ArrayList<>();
        for (File file : files) {
            libraryNameList.add(file.getName());
        }
        boolean exists = true;
        for (String library : LIBRARIES) {
            exists &= libraryNameList.contains(library);
        }
        return exists;
    }


    /**
     * 根据图片Bitmap提取特征
     *
     * @param originalBitmap
     * @return
     */
    public static FaceFeature getImageFeature(Bitmap originalBitmap) {
        int imgWidth = originalBitmap.getWidth();
        int imgHeight = originalBitmap.getHeight();
        Bitmap bitmap = null;
        //静态图片检测,图像宽高要求是 宽度是4的倍数，高度是2的位数
        // 对静态图片进行检测。若图像数据宽高不符合要求，可使用ArcSoftImageUtil中的
        //getAlignedBitmap函数获取对齐后的图像。
        if (imgWidth % 4 == 0 && imgHeight % 4 == 0) {
            //宽度是4的倍数。高度是2的倍数
            bitmap = originalBitmap;
        } else {
            bitmap = ArcSoftImageUtil.getAlignedBitmap(originalBitmap, true);
        }
        //2-提取特征
        byte[] nv21 = FaceServer.getInstance().bitmapToNV21Data(bitmap);
        if (nv21 != null) {
            int newImgWidth = bitmap.getWidth();
            int newImgHeight = bitmap.getHeight();
            //检测人脸
            List<FaceInfo> faceInfos = FaceServer.getInstance().getFaceInfo(nv21, newImgWidth, newImgHeight);
            if (faceInfos != null && faceInfos.size() > 0) {
                //提取特征
                FaceFeature faceFeature = FaceServer.getInstance().getFaceFeature(nv21, newImgWidth, newImgHeight, faceInfos.get(0));
                if (faceFeature != null) {
                    return faceFeature;
                }
            }
        }
        return null;
    }


    /**
     * 复制授权码到SD卡
     */
    public static boolean copyLicenseCodeToCard(Context context) {
        return FileUtils.copyFile(context.getFilesDir().getAbsoluteFile() + File.separator + "ArcFacePro32.dat", Constants.LICENSE_PATH);
    }

    /**
     * 复制授权码到SD卡
     */
    public static boolean copyLicenseCodeToActiveFile(Context context) {
        return FileUtils.copyFile(Constants.LICENSE_PATH, context.getFilesDir().getAbsoluteFile() + File.separator + "ArcFacePro32.dat");
    }
}
