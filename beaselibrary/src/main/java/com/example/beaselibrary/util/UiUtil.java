package com.example.beaselibrary.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

/**
 * 对页面操作所有的封装使用
 */
public class UiUtil {

    /**
     * 监听输入金额控件，可以输入0，只能输入小输掉后两位
     */
    public static void getTextWatcherZero(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence s, int i, int i1, int i2) {
                String str = editText.getText().toString();
                if (str.length() != 1) {
                    try {
                        String string = str.substring(0, 1);
                        if ("0".equals(string)) {
                            editText.setText("0");
                        }
                    } catch (Exception e) {

                    }
                }
                if (str.length() == 1) {
                    if (".".equals(s.toString())) {
                        editText.setText("");
                        return;
                    }
                }
                try {
                    counter = 0;
                    if (countStr(str, ".") > 1) {
                        editText.setText(str.substring(0, str.length() - 1));
                        editText.setSelection(editText.getText().length());//光标放在文本最后面
                    }
                    //限制只能输入小数点后2位
                    if (str.contains(".")) {
                        String str1 = str.substring(str.lastIndexOf(".") + 1);
                        if (str1.length() > 2) {
                            editText.setText(str.substring(0, str.lastIndexOf(".") + 3));
                        }
                        editText.setSelection(editText.getText().length());//光标放在文本最后面
                    }
                } catch (Exception e) {
                    Logger.e("--", e.toString());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private static int counter = 0;
    private static int countStr(String str1, String str2) {
        if (str1.indexOf(str2) == -1) {
            return 0;
        } else if (str1.indexOf(str2) != -1) {
            counter++;
            countStr(str1.substring(str1.indexOf(str2) +
                    str2.length()), str2);
            return counter;
        }
        return 0;
    }
}
