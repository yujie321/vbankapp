package com.sdses.idCard;

import android.graphics.Bitmap;

import java.nio.ByteBuffer;

/**
 * 创建时间： 2017/5/2 10:13
 * 编写人：
 * 功能描述：
 */
public class Bitmap2RGB {

    public static byte[] getPixelsRGB(Bitmap image) {
        // calculate how many bytes our image consists of
        int bytes = image.getByteCount();

        ByteBuffer buffer = ByteBuffer.allocate(bytes); // Create a new buffer
        image.copyPixelsToBuffer(buffer); // Move the byte data to the buffer

        byte[] temp = buffer.array(); // Get the underlying array containing the data.

        byte[] pixels = new byte[(temp.length / 4) * 3]; // Allocate for BGR

        for (int i = 0; i < temp.length / 4; i++) {

            pixels[i * 3] = temp[i * 4];        //R
            pixels[i * 3 + 1] = temp[i * 4 + 1];    //G
            pixels[i * 3 + 2] = temp[i * 4 + 2];       //B

        }

        return pixels;
    }

    public static byte[] bitmap2Rgb(Bitmap bmp) {

        //TODO 目前30毫秒，
        int bytes = bmp.getByteCount();
        ByteBuffer buf = ByteBuffer.allocate(bytes);
        bmp.copyPixelsToBuffer(buf);
        byte[] rgba  = buf.array();

        byte[] pixels = new byte[(rgba .length/4) * 3];


        int count = rgba.length / 4;

        //Bitmap像素点的色彩通道排列顺序是RGBA
        for (int i = 0; i < count; i++) {

            pixels[i * 3] = rgba[i * 4];        //R
            pixels[i * 3 + 1] = rgba[i * 4 + 1];    //G
            pixels[i * 3 + 2] = rgba[i * 4 + 2];       //B

        }

        return pixels;

//        int w = bmp.getWidth();
//        int h = bmp.getHeight();
//
//        byte[] pixels = new byte[w * h * 3]; // Allocate for RGB
//
//        int k = 0;
//
//        for (int x = 0; x < h; x++) {
//            for (int y = 0; y < w; y++) {
//                int color = bmp.getPixel(y, x);
//                int r = Color.red(color);
//                int g = Color.green(color);
//                int b = Color.blue(color);
//                pixels[k * 3] = (byte) r;
//                pixels[k * 3 + 1] = (byte) g;
//                pixels[k * 3 + 2] = (byte) b;
//                k++;
//            }
//        }
//
//        return pixels;
    }


//    public static Bitmap rgb2Bitmap(byte[] data, int width, int height) {
//        int[] colors = new int[data.length / 3];
//        OpenFace.getInstance().cvtRGB2ARGB(data, colors, data.length);
//        Bitmap bmp = Bitmap.createBitmap(colors, width, height, Bitmap.Config.RGB_565);
//        return bmp;
//    }


    /**
     * @方法描述 将RGB字节数组转换成Bitmap，
     */
    static public Bitmap rgb2Bitmap(byte[] data, int width, int height) {
        int[] colors = convertByteToColor(data);    //取RGB值转换为int数组
        if (colors == null) {
            return null;
        }

        Bitmap bmp = Bitmap.createBitmap(colors, 0, width, width, height,
                Bitmap.Config.ARGB_8888);
        return bmp;
    }


    // 将一个byte数转成int
    // 实现这个函数的目的是为了将byte数当成无符号的变量去转化成int
    public static int convertByteToInt(byte data) {

        int heightBit = (int) ((data >> 4) & 0x0F);
        int lowBit = (int) (0x0F & data);
        return heightBit * 16 + lowBit;
    }


    // 将纯RGB数据数组转化成int像素数组
    public static int[] convertByteToColor(byte[] data) {
        int size = data.length;
        if (size == 0) {
            return null;
        }

        int arg = 0;
        if (size % 3 != 0) {
            arg = 1;
        }

        // 一般RGB字节数组的长度应该是3的倍数，
        // 不排除有特殊情况，多余的RGB数据用黑色0XFF000000填充
        int[] color = new int[size / 3 + arg];
        int red, green, blue;
        int colorLen = color.length;
        if (arg == 0) {
            for (int i = 0; i < colorLen; ++i) {
                red = convertByteToInt(data[i * 3]);
                green = convertByteToInt(data[i * 3 + 1]);
                blue = convertByteToInt(data[i * 3 + 2]);

                // 获取RGB分量值通过按位或生成int的像素值
                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }
        } else {
            for (int i = 0; i < colorLen - 1; ++i) {
                red = convertByteToInt(data[i * 3]);
                green = convertByteToInt(data[i * 3 + 1]);
                blue = convertByteToInt(data[i * 3 + 2]);
                color[i] = (red << 16) | (green << 8) | blue | 0xFF000000;
            }

            color[colorLen - 1] = 0xFF000000;
        }

        return color;
    }
}
