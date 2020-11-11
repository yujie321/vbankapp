package com.vieboo.vbankapp.weight;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.toollib.manager.GlideManager;
import com.example.toollib.util.DensityUtil;
import com.qmuiteam.qmui.widget.dialog.QMUIDialog;
import com.vieboo.vbankapp.R;

/**
 * 身份证 dialog
 */
public class IdCardDialog {

    private QMUIDialog qmuiDialog;
    private Context mContext;

    private TextView tvIdCardName, tvIdCardSex, tvIdCardNation, tvIdCardBirth, tvIdCardAddress, tvIdCardNumber;
    private ImageView ivIdCardHead;

    public static IdCardDialog getInstance() {
        return new IdCardDialog();
    }

    public IdCardDialog initView(final Context mContext) {
        this.mContext = mContext;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        QMUIDialog.CustomDialogBuilder dialogBuilder = new QMUIDialog.CustomDialogBuilder(mContext);
        dialogBuilder.setLayout(R.layout.dialog_id_card);
        qmuiDialog = dialogBuilder.create();
        qmuiDialog.setContentView(inflater.inflate(R.layout.dialog_id_card, null));
        tvIdCardName = qmuiDialog.findViewById(R.id.tvIdCardName);
        tvIdCardSex = qmuiDialog.findViewById(R.id.tvIdCardSex);
        tvIdCardNation = qmuiDialog.findViewById(R.id.tvIdCardNation);
        tvIdCardBirth = qmuiDialog.findViewById(R.id.tvIdCardBirth);
        tvIdCardAddress = qmuiDialog.findViewById(R.id.tvIdCardAddress);
        tvIdCardNumber = qmuiDialog.findViewById(R.id.tvIdCardNumber);
        ivIdCardHead = qmuiDialog.findViewById(R.id.ivIdCardHead);
        return this;
    }

    public IdCardDialog setIdCardName(String name) {
        tvIdCardName.setText(name);
        return this;
    }

    public IdCardDialog setIdCardSex(String sex) {
        tvIdCardSex.setText(sex);
        return this;
    }

    public IdCardDialog setIdCardNation(String nation) {
        tvIdCardNation.setText(nation);
        return this;
    }

    public IdCardDialog setIdCardBirth(String year , String month , String day) {
        SpannableString spannableString = new SpannableString(year + "  " + "年" + " " +
                month + "  " + "月" + " " +
                day + "  " + "日");
        tvIdCardBirth.setText(spannableString);
        return this;
    }

    public IdCardDialog setIdCardBirth(String birthDay) {
        tvIdCardBirth.setText(birthDay);
        return this;
    }

    public IdCardDialog setIdCardAddress(String address) {
        tvIdCardAddress.setText(address);
        return this;
    }

    public IdCardDialog setIdCardNumber(String idCardNumber) {
        tvIdCardNumber.setText(idCardNumber);
        return this;
    }

    public IdCardDialog setIdCardHead(String idCardHeadUrl) {
        GlideManager.loadImage(mContext, idCardHeadUrl, ivIdCardHead);
        return this;
    }

    public IdCardDialog setIdCardHead(Bitmap bitmap) {
        ivIdCardHead.setImageBitmap(bitmap);
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
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                window.getDecorView().setPadding(DensityUtil.dp2px(70), DensityUtil.dp2px(215), DensityUtil.dp2px(70), 0);
            }
        }
    }
}
