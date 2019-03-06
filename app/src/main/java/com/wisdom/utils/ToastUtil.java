package com.wisdom.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by daixun on 15/8/29.
 */
public class ToastUtil {
    private static Context mContext;

    public static void init(Context context){
        mContext=context;
    }
    public static void showToast(String msg){

        Toast.makeText(mContext,msg+"", Toast.LENGTH_SHORT).show();
    }
    public static void showToast(Context context,String msg){

        Toast.makeText(context,msg+"", Toast.LENGTH_SHORT).show();
    }
    public static void showToast(int msgRes){
        showToast(mContext.getString(msgRes));
    }

}
