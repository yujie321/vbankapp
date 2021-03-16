package com.vieboo.vbankapp.weight;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.VideoView;

import com.example.toollib.util.DensityUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.vieboo.vbankapp.R;
import com.vieboo.vbankapp.fragment.HomeFragment;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 身份证 dialog
 */
public class VideoViewDialog implements View.OnTouchListener{
    Button btExit;
    private QMUIDialog qmuiDialog;
    private Context mContext;
    private VideoView videoView;
    Uri rtspUrl;

    public static VideoViewDialog getInstance() {
        return new VideoViewDialog();
    }

    public VideoViewDialog initView(final Context mContext) {
        this.mContext = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_video_view);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_video_view, null));
        videoView = qmuiDialog.findViewById(R.id.video_view);
        videoView.setOnTouchListener(this::onTouch);
        btExit = qmuiDialog.findViewById(R.id.btExit);
        btExit.setOnTouchListener(this);
        return this;
    }

    public void dismiss() {
        if (qmuiDialog != null) {
            videoView.stopPlayback();
            videoView.suspend();
            qmuiDialog.dismiss();
        }
    }

    public void showDialog() {
        if (qmuiDialog != null) {
            qmuiDialog.show();
            Window window = qmuiDialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams lp = window.getAttributes();
                lp.alpha = 0.0f;
                lp.dimAmount = 0f;
                window.setAttributes(lp);

                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.getDecorView().setPadding(DensityUtil.dp2px(0), DensityUtil.dp2px(0), DensityUtil.dp2px(0), DensityUtil.dp2px(0));
            }
        }
    }

    public VideoViewDialog setRtspUrl(Uri rtspUrl) {
        videoView.setVideoURI(rtspUrl);
        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                // 设置静音
                mp.setVolume(0, 0);
            }
        });
        videoView.start();
        return this;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.btExit:
                dismiss();
                break;
            case R.id.video_view:
                dismiss();
                break;
        }
        return false;
    }
}
