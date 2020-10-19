package com.example.toollib.manager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.request.RequestOptions;

/**
 * @author Administrator
 * @date 2019/5/11 0011
 */
public class GlideManager {

    /**
     * 加载网络圆形图片
     *
     * @param mContext  context
     * @param url       图片地址
     * @param imageView iv
     * @param defImage  异常占位图
     */
    @SuppressLint("CheckResult")
    public static void loadCircleImage(Context mContext, String url, ImageView imageView, int... defImage) {
        RequestOptions options = new RequestOptions();
        if (defImage != null && defImage.length > 0) {
            options.diskCacheStrategy(DiskCacheStrategy.ALL);
            options.error(defImage[0]);
            options.placeholder(defImage[0]);
        }
        options.transform(new CircleCrop());
        Glide.with(mContext).load(url).apply(options)
                .thumbnail(0.5f).into(imageView);
    }

    /**
     * 加载uri圆形图片
     *
     * @param mContext  context
     * @param uri       uri
     * @param imageView iv
     */
    public static void loadCircleImage(Context mContext, Uri uri, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .transform(new CircleCrop())
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(uri).apply(options)
                .thumbnail(0.5f).into(imageView);
    }

    /**
     * 加载res圆形图片
     *
     * @param mContext  context
     * @param uri       uri
     * @param imageView iv
     */
    public static void loadCircleImage(Context mContext, Integer uri, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                .transform(new CircleCrop())
                //禁止Glide硬盘缓存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(uri).apply(options)
                .thumbnail(0.5f).into(imageView);
    }


    /**
     * 加载网络图片
     *
     * @param mContext  context
     * @param url       url
     * @param imageView iv
     */
    @SuppressLint("CheckResult")
    public static void loadImage(Context mContext, String url, ImageView imageView, int... defImage) {
        RequestOptions options = new RequestOptions();
        if (defImage != null && defImage.length > 0) {
            options.placeholder(defImage[0]);
            options.error(defImage[0]);
        }
        options.dontAnimate();
        options.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(url).apply(options)
                .thumbnail(0.5f).into(imageView);
    }


    /**
     * 加载uri图片
     */
    public static void loadUriImage(Context mContext, Uri uri, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                //禁止Glide硬盘缓存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                .dontAnimate();
        Glide.with(mContext).load(uri).apply(options)
                .thumbnail(0.5f).into(imageView);
    }

    /**
     * 加载uri图片
     */
    public static void loadUriImage(Context mContext, String url, ImageView imageView) {
        RequestOptions options = new RequestOptions()
                //禁止Glide硬盘缓存缓存
                .diskCacheStrategy(DiskCacheStrategy.RESOURCE);
        Glide.with(mContext).load(url).apply(options)
                .thumbnail(0.5f).into(imageView);
    }

}
