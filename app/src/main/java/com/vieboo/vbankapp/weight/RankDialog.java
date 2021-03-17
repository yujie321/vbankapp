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

public class RankDialog implements View.OnTouchListener{
    private QMUIDialog qmuiDialog;
    private Context mContext;
    public static RankDialog getInstance() {
        return new RankDialog();
    }

    public RankDialog initView(final Context mContext) {
        this.mContext = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_rank);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_rank, null));
        return this;
    }

    public void dismiss() {
        if (qmuiDialog != null) {
            qmuiDialog.dismiss();
        }
    }

    public void showDialog() {
        if (qmuiDialog != null) {
            qmuiDialog.show();
            Window window = qmuiDialog.getWindow();
            if (window != null) {
                WindowManager.LayoutParams lp = window.getAttributes();
//                lp.alpha = 0.0f;
//                lp.dimAmount = 0f;
//                window.setAttributes(lp);

                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.getDecorView().setPadding(DensityUtil.dp2px(0), DensityUtil.dp2px(0), DensityUtil.dp2px(0), DensityUtil.dp2px(0));
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch (v.getId()){
            case R.id.video_view:
                dismiss();
                break;
        }
        return false;
    }
}
