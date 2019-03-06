package com.wisdom.utils;

import android.content.Context;
import android.graphics.Color;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.wisdom.cutpictonine.R;


import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class StrUtils {
    public static final String TAG = StrUtils.class.getSimpleName();
    static DecimalFormat df = new DecimalFormat("#0.00");

    public final static boolean isBlank(String str) {
        return null == str || str.equals("");
    }

    /**
     * 处理空字符串
     *
     * @param str
     * @return String
     */
    public static String doEmpty(String str) {
        return doEmpty(str, "");
    }

    public static String decimalFromart(double value) {
        return df.format(value);
    }

    /**
     * 判断一个字符是否是中文
     *
     * @param c
     * @return
     */
    public static boolean isChinese(char c) {
        return c >= 0x4E00 && c <= 0x9FA5;// 根据字节码判断
    }

    /**
     * 判断一个字符是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        char[] strChar = str.toCharArray();
        Boolean isChiness = false;
        for (char a : strChar) {
            // 根据字节码判断
            isChiness = a >= 0x4E00 && a <= 0x9FA5;
        }
        return isChiness;
    }


    /**
     * 判断一个字符串是否含有中文
     *
     * @param str
     * @return
     */
    public static boolean isHasChinese(String str) {
        if (str == null) return false;
        for (char c : str.toCharArray()) {
            if (isChinese(c)) return true;// 有一个中文字符就返回
        }
        return false;
    }


    /**
     * 处理空字符串
     *
     * @param str
     * @param defaultValue
     * @return String
     */
    public static String doEmpty(String str, String defaultValue) {
        if (str == null || str.equalsIgnoreCase("null")
                || str.trim().equals("")) {
            str = defaultValue;
        } else if (str.startsWith("null")) {
            str = str.substring(4, str.length());
        }
        return str.trim();
    }

    /**
     * 编码
     *
     * @param value
     * @return
     */
    public static String encoder(String value) {
        if (StrUtils.isBlank(value)) {
            return "";
        } else {
            return URLEncoder.encode(value);
        }
    }

    /**
     * 解码
     *
     * @param str
     * @return
     */
    public static String decoder(String str) {

        return URLDecoder.decode(str);
    }

    public static String inputStream2String(InputStream in) throws IOException {
        StringBuffer out = new StringBuffer();
        byte[] b = new byte[4096];
        for (int n; (n = in.read(b)) != -1; ) {
            out.append(new String(b, 0, n));
        }
        return out.toString();
    }

    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_LONG).show();
    }

    public static void showToast(Context context, int strId) {
        Toast.makeText(context, strId, Toast.LENGTH_LONG).show();
    }

    /**
     * 判断字符串文本是否为空
     *
     * @param str 待判断的字符串
     * @return 为空返回true  不为空返回 false
     */
    public static boolean isEmpty(String str) {
        boolean isEmpty = false;
        if ("".equals(str) || str == null) {
            isEmpty = true;
        }
        return isEmpty;
    }


    /**
     * 验证电话号码是否正确的正则方法
     *
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNumberValid(String phoneNum) {
        boolean isvalid = false;
        String expression = "((^(13|15|18|17)[0-9]{9}$)|(^0[1,2]{1}\\d{1}-?\\d{8}$)|(^0[3-9] {1}\\d{2}-?\\d{7,8}$)|(^0[1,2]{1}\\d{1}-?\\d{8}-(\\d{1,4})$)|(^0[3-9]{1}\\d{2}-? \\d{7,8}-(\\d{1,4})$))";
        CharSequence inputStr = phoneNum;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isvalid = true;
        }
        return isvalid;
    }

//    "^[0-9]{0,9}:[0-9]{0,9}$"

    /**
     * 验证积分比值格式的正则方法  1：1
     *
     * @param integeralRatio
     * @return
     */
    public static boolean isIntegeralRatioValid(String integeralRatio) {
        boolean isvalid = false;
        String expression = "^[0-9]{1,9}:[0-9]{1,9}$";
        CharSequence inputStr = integeralRatio;
        Pattern pattern = Pattern.compile(expression);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isvalid = true;
        }
        return isvalid;
    }


    /**
     * 判断一个字符是否是中文
     * @param c
     * @return
     */
//    public static boolean isHasChinese(char c) {
//        return c >= 0x4E00 &&  c <= 0x9FA5;// 根据字节码判断
//    }

    /**
     * 取得文本框的内容
     *
     * @param editText
     * @return
     */
    public static String getEdtTxtContent(EditText editText) {
        return editText.getText().toString().trim();
    }

    /**
     * 判断输入框的内容是否为空
     *
     * @param editText
     * @return editText为空返回true
     */
    public static Boolean isEdtTxtEmpty(EditText editText) {
        Boolean isEmpty = true;
        if (!"".equals(getEdtTxtContent(editText).trim())) {
            isEmpty = false;
        }
        return isEmpty;
    }

    /**
     * 返回字符串的第一个字母
     *
     * @param str
     * @return
     */
    public static String getFirstChar(String str) {
        String strFirst = "";
        if (str.length() > 0) {
            strFirst = String.valueOf(str.charAt(0));
        }
        return strFirst;
    }

    /**
     * 判断str中是否包含str2
     *
     * @param str1
     * @param str2
     * @return
     */
    public static Boolean isStr1ContainStr2(String str1, String str2) {
        if (str1.indexOf(str2) == -1) {
            //不包含
            return true;
        } else {
            //包含
            return false;
        }
    }

    //邮箱验证
    public static boolean isEmail(String strEmail) {
        String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(strEmail);
        Log.e("邮箱验证", m.matches() + "");
        if (m.matches() == true) {
            return false;
        } else {
            return true;
        }
    }



    /**
     * 使TextView指定关键字高亮
     *
     * @param tv       textView控件
     * @param str      textView要设置的文字内容
     * @param keyWords 为资中要变色的关键字
     */
    public static void changeKeywordsColor(TextView tv, String str, String keyWords) {
        SpannableString s = new SpannableString(str);

        Pattern p = Pattern.compile(keyWords);

        Matcher m = p.matcher(s);

        while (m.find()) {
            int start = m.start();
            int end = m.end();
            s.setSpan(new ForegroundColorSpan(Color.BLUE), start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }
        tv.setText(s);
    }

    /**
     * 根据文件路径判断文件是否是图片
     *
     * @param filePath
     * @return 是图片返回true
     */
    public static Boolean isFileTypePic(String filePath) {
        Boolean isPic = false;
        Log.i(TAG, "isFileTypePic: " + filePath);
        String[] name = filePath.split(".");
        if (name.length > 1) {
            String lastName = name[name.length];
            Log.i(TAG, "lastName: " + lastName);
            if (lastName.contains("jpg")
                    || lastName.contains("png")
                    || lastName.contains("bmp")
                    || lastName.contains("gif")
                    || lastName.contains("jpeg")
                    ) {
                isPic = true;
            }
        } else {
            ToastUtil.showToast("下载文件路径出错");
        }
        return isPic;
    }

    /**
     * 将输入的阿拉伯数字转换成中国大写数字
     *
     * @param string
     * @return
     */
    public static String toChinese(String string) {
        String[] s1 = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        String[] s2 = {"十", "百", "千", "万", "十", "百", "千", "亿", "十", "百", "千"};
        String result = "";
        int n = string.length();
        for (int i = 0; i < n; i++) {
            int num = string.charAt(i) - '0';
            if (i != n - 1 && num != 0) {
                result += s1[num] + s2[n - 2 - i];
            } else {
                result += s1[num];
            }
            System.out.println("  " + result);
        }
        return result;
    }
}
