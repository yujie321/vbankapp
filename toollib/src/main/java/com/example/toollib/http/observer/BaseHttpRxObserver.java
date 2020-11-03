package com.example.toollib.http.observer;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.view.Window;

import com.example.toollib.http.HttpResult;
import com.example.toollib.http.exception.ApiException;
import com.example.toollib.http.exception.HttpError;
import com.example.toollib.util.Log;
import com.example.toollib.util.LoginInterceptor;
import com.example.toollib.util.ToastUtil;
import com.qmuiteam.qmui.widget.dialog.QMUITipDialog;

import java.lang.ref.WeakReference;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * @author Administrator
 * @date 2019/6/25 0025
 */
public abstract class BaseHttpRxObserver<T> implements Observer<HttpResult<T>>, DialogInterface.OnDismissListener {

    /**
     * 进度条提示文字 默认不显示
     */
    private WeakReference<String> loadingTip;
    private WeakReference<Context> mContext;
    private Disposable disposable;
    private QMUITipDialog tipLoading;

    public BaseHttpRxObserver() {
    }

    public BaseHttpRxObserver(String loadingTip, Context mContext) {
        this.loadingTip = new WeakReference<>(loadingTip);
        this.mContext = new WeakReference<>(mContext);
    }

    public BaseHttpRxObserver(Context mContext) {
        this.mContext = new WeakReference<>(mContext);
    }

    @Override
    public void onSubscribe(Disposable d) {
        if (mContext != null) {
            Log.e("线程 = " + Thread.currentThread().getName());
            ((Activity) mContext.get()).runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tipLoading = new QMUITipDialog.Builder(mContext.get())
                            .setIconType(QMUITipDialog.Builder.ICON_TYPE_LOADING)
                            .setTipWord(loadingTip != null ? loadingTip.get() : "")
                            .create();
                    Window window = tipLoading.getWindow();
                    if (window != null) {
                        window.setGravity(Gravity.CENTER);
                    }
                    tipLoading.setOnDismissListener(BaseHttpRxObserver.this);
                    tipLoading.show();
                }
            });
        }
        this.disposable = d;
        onStarts(d);
    }

    @Override
    public void onNext(HttpResult<T> httpResult) {
        if (tipLoading != null && tipLoading.isShowing()) {
            tipLoading.dismiss();
        }
        T data = httpResult.getData();
        onSuccess(data);
    }

    @Override
    public void onError(Throwable e) {
        //请求失败
        if (tipLoading != null && tipLoading.isShowing()) {
            tipLoading.dismiss();
        }
        if (e instanceof ApiException) {
            onError((ApiException) e);
        } else {
            onError(new ApiException(e, HttpError.UNKNOWN.getCode()));
        }
    }

    @Override
    public void onComplete() {

    }

    /**
     * 开始网络请求
     *
     * @param d Disposable
     */
    public void onStarts(Disposable d) {

    }

    /**
     * 错误/异常回调
     *
     * @param apiException 错误信息
     */
    public void onError(ApiException apiException) {
        boolean flag = LoginInterceptor.tokenReLogin(apiException);
        if (!flag) {
            ToastUtil.showShort(apiException.getMsg());
        }
    }


    /**
     * 请求结果
     *
     * @param response T
     */
    protected abstract void onSuccess(T response);


    @Override
    public void onDismiss(DialogInterface dialog) {
        httpCancel();
    }

    private void httpCancel() {
        mContext = null;
        loadingTip = null;
        if (disposable != null) {
            disposable.dispose();
        }
        if (tipLoading != null) {
            tipLoading.cancel();
            tipLoading = null;
        }
    }
}
