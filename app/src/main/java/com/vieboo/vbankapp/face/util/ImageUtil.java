package com.vieboo.vbankapp.face.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;

import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 图片转化工具类
 */
public class ImageUtil {


    /**
     *  将图片内容解析成字节数组
     * @param inStream
     * @return byte[]
     * @throws Exception
     */
    public static byte[] readStream(InputStream inStream) throws Exception {
        byte[] buffer = new byte[1024];
        int len = -1;
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        byte[] data = outStream.toByteArray();
        outStream.close();
        inStream.close();
        return data;

    }
    /**
     *   将字节数组转换为ImageView可调用的Bitmap对象
     * @param bytes
     * @param opts
     * @return Bitmap
     */
    public static Bitmap getPicFromBytes(byte[] bytes,
                                         BitmapFactory.Options opts) {
        if (bytes != null)
            if (opts != null)
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length,
                        opts);
            else
                return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        return null;
    }
    /**
     *  图片缩放
     * @param bitmap 对象
     * @param w 要缩放的宽度
     * @param h 要缩放的高度
     * @return newBmp 新 Bitmap对象
     */
    public static Bitmap zoomBitmap(Bitmap bitmap, int w, int h){
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) w / width);
        float scaleHeight = ((float) h / height);
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap newBmp = Bitmap.createBitmap(bitmap, 0, 0, width, height,
                matrix, true);
        return newBmp;
    }

    /**
     *  把Bitmap转Byte
     * @Author HEH
     * @EditTime 2010-07-19 上午11:45:56
     */
    public static byte[] Bitmap2Bytes(Bitmap bm){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
    /**
     *  把字节数组保存为一个文件
     * @Author HEH
     * @EditTime 2010-07-19 上午11:45:56
     */
    public static File getFileFromBytes(byte[] b, String outputFile) {
        BufferedOutputStream stream = null;
        File file = null;
        try {
            file = new File(outputFile);
            FileOutputStream fstream = new FileOutputStream(file);
            stream = new BufferedOutputStream(fstream);
            stream.write(b);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        }
        return file;
    }

    public static Bitmap byteToBitmap(byte[] imgByte) {
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            //  Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }


    public static Bitmap getURLimage(String url) {
        Bitmap bmp = null;
        try {
            URL myurl = new URL(url);
            // 获得连接
            HttpURLConnection conn = (HttpURLConnection) myurl.openConnection();
            conn.setConnectTimeout(6000);//设置超时
            conn.setDoInput(true);
            conn.setUseCaches(false);//不缓存
            conn.connect();
            InputStream is = conn.getInputStream();//获得图片的数据流
            bmp = BitmapFactory.decodeStream(is);
            is.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bmp;
    }

    /**
     *  图片裁剪，并把裁剪完的新图片保存 。
     *
     * @param
     *           <strong> 源图片数据流byte[]</strong>
     *            <strong>裁剪图片存放完整的路径，如：D:\\img_200×200.jpg</strong>
     * @param x
     *            剪切点x坐标
     * @param y
     *            剪切点y坐标
     * @param width
     *            剪切宽度
     * @param height
     *            剪切高度
     * @return 返回裁剪后的图片名称
     */
    public static Bitmap  cutImageBitmap(byte[] srcImageData, int srcWidth, int srcHeight, int x, int y, int width, int height) {
        try {
            //读取原图
            //  Bitmap bitmap = BitmapFactory.decodeByteArray(srcImageData,0,srcImageData.length);
            YuvImage yuvimage=new YuvImage(srcImageData, ImageFormat.NV21, srcWidth,srcHeight, null); //20、20分别是图的宽度与高度
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            yuvimage.compressToJpeg(new Rect(0, 0,srcWidth, srcHeight), 100, baos);//80--JPG图片的质量[0-100],100最高
            byte[] jdata = baos.toByteArray();
            Bitmap bitmap= BitmapFactory.decodeByteArray(jdata, 0, jdata.length);
            //Bitmap bitmap=byteToBitmap(srcImageData);
            Bitmap subBitmap = Bitmap.createBitmap(bitmap, x, y, width,height, null, false);
          return subBitmap;

        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return null;


    }
}
