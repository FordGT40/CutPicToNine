package com.wisdom.utils;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import java.io.File;
import java.util.ArrayList;

/**
 * @author HanXueFeng
 * @ProjectName project： CutPicToNine
 * @class package：com.wisdom.utils
 * @class describe：分享相关流程工具类
 * @time 2019/3/7 13:32
 * @change
 */
public class ShareHelper {


    /**
     * 分享到微信
     *
     * @param context
     * @param progressView 进度条布局
     * @param v            被点击的按钮布局文件view
     */
    public static void shareSlicesWeChat(final Context context, final View progressView, View v) {
        if (v.getTag() != null) {
            final ArrayList<File> slices = (ArrayList<File>) v.getTag();
            final ArrayList<Uri> sliceUris = new ArrayList<>();
            Observable.fromArray(slices.toArray(new File[]{}))
                    .map(new Function<File, Uri>() {
                        @Override
                        public Uri apply(File file) throws Exception {
                            Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                                    , new String[]{MediaStore.Images.ImageColumns._ID, MediaStore.Images.ImageColumns.DATA}
                                    , MediaStore.Images.ImageColumns.DATA + "=?"
                                    , new String[]{file.getAbsolutePath()}
                                    , null);
                            if (cursor != null) {
                                if (cursor.getCount() != 0) {
                                    cursor.moveToFirst();
                                    int id = cursor.getInt(cursor.getColumnIndex(MediaStore.Images.ImageColumns._ID));
                                    String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA));
                                    cursor.close();
                                    Log.d("xsm-read-media-database", "id = " + id + ", path = " + path);
                                    Log.i("xsm-read-media-database", "uri = " + Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString() + "/" + id));
                                    return Uri.parse(MediaStore.Images.Media.EXTERNAL_CONTENT_URI.toString() + "/" + id);

                                } else {
                                    cursor.close();
                                    Log.w("xsm-read-media-database", "cursor is empty");
                                    return null;
                                }
                            } else {
                                Log.e("xsm-read-media-database", "cursor is null");
                                return null;
                            }


//                            final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
//                            Uri fileUri;
//                            if (isKitKat) {
//                                fileUri = FileProvider.getUriForFile(MainActivity.this, " com.samon.wechatimageslicer.myprovider", file);
//                            } else {
//                                fileUri = Uri.fromFile(file);
//                            }
//                            Log.i("xsm-collect-slice-uri", "applyURI: " + fileUri);
//                            return fileUri;


                        }
                    }).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<Uri>() {
                        @Override
                        public void onSubscribe(Disposable d) {
                            progressView.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onNext(Uri uri) {
                            Log.d("xsm-collect-slice-uri", uri.toString());
                            sliceUris.add(uri);
                        }

                        @Override
                        public void onError(Throwable e) {
                            progressView.setVisibility(View.GONE);
                        }

                        @Override
                        public void onComplete() {
                            Log.d("xsm-start-wechat", "start wechat with " + slices.size() + " pictures.");
                            progressView.setVisibility(View.GONE);
                            Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//                            分享到朋友圈（微信7.0以上，官方暂时不支持多图分享了）
//                            https://blog.csdn.net/okg0111/article/details/86498186
                            ComponentName comp = new ComponentName("com.tencent.mm",
                                    "com.tencent.mm.ui.tools.ShareToTimeLineUI");
//                            分享给朋友
//                            ComponentName comp = new ComponentName("com.tencent.mm",
//                                    "com.tencent.mm.ui.tools.ShareImgUI");

                            intent.setComponent(comp);
                            intent.setType("image/*");
                            intent.putExtra("Kdescription", "");
                            intent.putExtra(Intent.EXTRA_STREAM, sliceUris);
                            for (int i = 0; i < sliceUris.size(); i++) {
                                Log.i("slice", "sliceUri: " + sliceUris.get(i));
                            }


                            context.startActivity(intent);
                        }
                    });

        }
    }

}
