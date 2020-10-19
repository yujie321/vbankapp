package com.example.toollib.weight;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Administrator
 * @date 2019/5/8 0008
 *
 * 监听 输入框 是否有值 有值时btn 能点击
 */
public class BtnToEditListenerUtils {

    private List<EditText> editTextList = new ArrayList<>();

    private Button btn;

    public static BtnToEditListenerUtils getInstance() {
        return new BtnToEditListenerUtils();
    }

    public BtnToEditListenerUtils setBtn(Button btn) {
        this.btn = btn;
        btn.setEnabled(false);
        return this;
    }

    public BtnToEditListenerUtils addEditView(EditText editText) {
        editTextList.add(editText);
        return this;
    }

    public void build() {
        setWatcher();
    }

    private void setWatcher() {
        for (int i = 0; i < editTextList.size(); i++) {
            editTextList.get(i).addTextChangedListener(textWatcher);
        }

    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            isClickable();
        }

        @Override
        public void afterTextChanged(Editable s) {
            isClickable();
        }
    };

    private void isClickable(){
        for (EditText text : editTextList) {
            String s = text.getText().toString();
            if (TextUtils.isEmpty(s)) {
                btn.setEnabled(false);
                return;
            }else {
                btn.setEnabled(true);
            }
        }
    }

}
