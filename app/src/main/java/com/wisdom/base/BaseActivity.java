package com.wisdom.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import com.wisdom.base.weakreferences.WeakReferenceActivity;
import com.wisdom.cutpictonine.R;
import com.wisdom.utils.NetUtils;
import com.wisdom.utils.StrUtils;


public abstract class BaseActivity extends FragmentActivity {

    protected Context context;
    protected TextView title;
    protected ImageView backIv;
    protected TextView right;
    protected ImageView rightIv;
    private WeakReferenceActivity mActivity;


    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        setlayoutIds();
        context = this;
//        ButterKnife.bind(this);
        mActivity = new WeakReferenceActivity(this);

        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ActivityManager.getActivityManagerInstance().addActivity(this);
        initHeadView();
        initViews();
    }


    public void initHeadView() {
        title = findViewById(R.id.comm_head_title);
        backIv = findViewById(R.id.head_back_iv);
        right = findViewById(R.id.head_right);
        rightIv = findViewById(R.id.head_right_iv);
        if (backIv != null)
            backIv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
    }


    public void setTitle(int resId) {
        if (title != null)
            title.setText(resId);
    }

    public void setRight(int resId) {
        if (right != null) {
            right.setText(resId);
            right.setVisibility(View.VISIBLE);
        }
    }

    public void setTitle(String resId) {
        if (title != null)
            title.setText(resId);
    }

    public void setRightIcon(int resId) {
        if (null != rightIv) {
            rightIv.setImageResource(resId);
            rightIv.setVisibility(View.VISIBLE);
        }
    }

    public void showRight() {
        if (null != right)
            right.setVisibility(View.VISIBLE);
    }

    public void hideRight() {
        if (null != right)
            right.setVisibility(View.GONE);
    }

    public void hideBackIv() {
        if (null != backIv)
            backIv.setVisibility(View.GONE);
    }

    public abstract void setlayoutIds();

    public abstract void initViews();


    public boolean NetAvailable() {
        if (!NetUtils.isCheckNetAvailable(context)) {
            StrUtils.showToast(context, R.string.net_wrok_unconnetion_text);//设置相关的文字
            return false;
        }
        return true;
    }

    public void startActivity(Class<?> cls) {
        startActivity(cls, null);
    }

    public void startActivity(Class<?> cls, Bundle bundle) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    public void startActivityForResult(Class<?> cls, int requestCode) {
        startActivityForResult(cls, null, requestCode);
    }

    public void startActivityForResult(Class<?> cls, Bundle bundle, int requestCode) {
        Intent intent = new Intent(this, cls);
        intent.putExtras(bundle);
        startActivityForResult(intent, requestCode);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
