package com.wisdom.cutpictonine

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.wisdom.base.BaseActivity
import com.wisdom.cutpictonine.MainActivity.Companion.REQUEST_CODE_CUT
import com.wisdom.cutpictonine.MainActivity.Companion.tempFile
import com.wisdom.cutpictonine.slicer.*
import java.io.FileInputStream
import java.util.*

/**
 * @ProjectName project： CutPicToNine
 * @class package：com.wisdom.cutpictonine
 * @class describe：选择完图片的页面
 * @author HanXueFeng
 * @time 2019/3/6 9:44
 * @change
 */
class ChoosePicActivity : BaseActivity() {

    private val ninePicImageViews = ArrayList<ImageView>()
    private val towPickImageViews = ArrayList<ImageView>()
    private val threePickImageViews = ArrayList<ImageView>()
    private val fourPickImageViews = ArrayList<ImageView>()
    private val sixPickImageViews = ArrayList<ImageView>()
    private var currentImageViewList = ninePicImageViews
    private val ninePicBitmapSlicer = NinePicBitmapSlicer()
    private val towPicBitmapSlicer = TowPicBitmapSlicer()
    private val threePicBitmapSlicer = ThreePicBitmapSlicer()
    private val fourPicBitmapSlicer = FourPicBitmapSlicer()
    private val sixPicBitmapSlicer = SixPicBitmapSlicer()
    private var bitmapSlicer = ninePicBitmapSlicer
    private var progressView: View? = null
    private var lastDesBitmaps: List<Bitmap>? = null
    private val bitmapSliceListener = object : BitmapSlicer.BitmapSliceListener {
        override fun onSliceSuccess(srcBitmap: Bitmap, desBitmaps: List<Bitmap>) {
            srcBitmap.recycle()
            bitmapSlicer.setSrcBitmap(null)
            for (imageView in ninePicImageViews) {
                imageView.setImageBitmap(null)
                imageView.visibility = View.GONE
            }
            if (lastDesBitmaps != null) {
                for (lastDesBitmap in lastDesBitmaps!!) {
                    lastDesBitmap.recycle()
                }
            }
            lastDesBitmaps = null
            for (i in currentImageViewList.indices) {
                currentImageViewList[i].setImageBitmap(desBitmaps[i])
                currentImageViewList[i].visibility = View.VISIBLE
            }
            lastDesBitmaps = desBitmaps
            progressView!!.visibility = View.GONE
//            resultView.setVisibility(View.GONE)
        }

        override fun onSliceFailed() {
            Toast.makeText(this@ChoosePicActivity, "切片失败", Toast.LENGTH_SHORT).show()
            progressView!!.visibility = View.GONE
//            resultView.setVisibility(View.GONE)
        }
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_choose_pic)
    }

    override fun initViews() {
        initImageViews()
        bitmapSlicer = ninePicBitmapSlicer
        currentImageViewList = ninePicImageViews
        progressView = findViewById(R.id.layout_progress)
        val uri = intent.data
        var h = 0
        var w = 0
        try {
            val bitmap = BitmapFactory.decodeStream(contentResolver.openInputStream(uri))
            h = bitmap.height
            w = bitmap.width
            bitmap.recycle()
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(this, "无法读取图片", Toast.LENGTH_SHORT).show()
            return
        }

        val intent = Intent("com.android.camera.action.CROP")
        intent.setDataAndType(uri, "image/*")
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true")
        //该参数可以不设定用来规定裁剪区的宽高比
        intent.putExtra("aspectX", bitmapSlicer.aspectX)
        intent.putExtra("aspectY", bitmapSlicer.aspectY)
        //该参数设定为你的imageView的大小
        intent.putExtra("outputX", bitmapSlicer.calculateOutputX(w, h))
        intent.putExtra("outputY", bitmapSlicer.calculateOutputY(w, h))
        //是否返回bitmap对象
        intent.putExtra("return-data", false)
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile))
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString())
        intent.putExtra("noFaceDetection", true)
        startActivityForResult(intent, REQUEST_CODE_CUT)

    }

    private fun initImageViews() {
        ninePicImageViews.add(findViewById(R.id.iv_image1))
        ninePicImageViews.add(findViewById(R.id.iv_image2))
        ninePicImageViews.add(findViewById(R.id.iv_image3))
        ninePicImageViews.add(findViewById(R.id.iv_image4))
        ninePicImageViews.add(findViewById(R.id.iv_image5))
        ninePicImageViews.add(findViewById(R.id.iv_image6))
        ninePicImageViews.add(findViewById(R.id.iv_image7))
        ninePicImageViews.add(findViewById(R.id.iv_image8))
        ninePicImageViews.add(findViewById(R.id.iv_image9))

        towPickImageViews.add(ninePicImageViews[0])
        towPickImageViews.add(ninePicImageViews[1])

        threePickImageViews.add(ninePicImageViews[0])
        threePickImageViews.add(ninePicImageViews[1])
        threePickImageViews.add(ninePicImageViews[2])

        fourPickImageViews.add(ninePicImageViews[0])
        fourPickImageViews.add(ninePicImageViews[1])
        fourPickImageViews.add(ninePicImageViews[3])
        fourPickImageViews.add(ninePicImageViews[4])

        sixPickImageViews.add(ninePicImageViews[0])
        sixPickImageViews.add(ninePicImageViews[1])
        sixPickImageViews.add(ninePicImageViews[2])
        sixPickImageViews.add(ninePicImageViews[3])
        sixPickImageViews.add(ninePicImageViews[4])
        sixPickImageViews.add(ninePicImageViews[5])
    }

    /**
     *  @describe 页面回调方法
     *  @return
     *  @author HanXueFeng
     *  @time 2019/3/6  11:08
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_CUT -> {
                    try {
                        val inputStream = FileInputStream(tempFile)
                        val bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()
                        tempFile.delete()
                        bitmapSlicer.setSrcBitmap(bitmap)
                            .registerListener(bitmapSliceListener)
                            .slice()
                        progressView!!.visibility = View.VISIBLE
                    } catch (e: Exception) {
                        e.printStackTrace()
                        Toast.makeText(this, "无法读取图片", Toast.LENGTH_SHORT).show()
                        progressView!!.visibility = View.GONE
                        return
                    }

                }
            }
        }
    }
}