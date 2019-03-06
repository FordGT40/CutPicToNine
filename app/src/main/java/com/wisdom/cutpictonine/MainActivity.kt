package com.wisdom.cutpictonine

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.wisdom.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.head_title_bar.*
import org.jetbrains.anko.toast
import java.io.File

class MainActivity : BaseActivity(), View.OnClickListener {

    private val baseDir = Environment.getExternalStorageDirectory().toString() + "/wisdom/MicroMsg"
    private val tempFile = File(baseDir, "crop_temp")

    companion object {
        val baseDir = Environment.getExternalStorageDirectory().toString() + "/wisdom/MicroMsg"
        val tempFile = File(baseDir, "crop_temp")
        const val REQUEST_CODE_PICK = 0
        const val REQUEST_CODE_CUT = 1
        const val REQUEST_PERMISSION = 2
    }

    override fun onClick(v: View?) {
        when {
            v!!.id == R.id.btn_choose_pic -> {
                //选择图片点击事件
                val intent = Intent(Intent.ACTION_PICK)
                intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*")
                startActivityForResult(intent, REQUEST_CODE_PICK)
            }
        }
    }

    override fun setlayoutIds() {
        setContentView(R.layout.activity_main)
    }

    override fun initViews() {
        head_back_iv.visibility = View.GONE
        setTitle(R.string.main_title)
        val options = RequestOptions().diskCacheStrategy(DiskCacheStrategy.RESOURCE)
        Glide.with(this).load(R.drawable.eg).apply(options).into(iv_inside)
        btn_choose_pic.setOnClickListener(this)
        val baseDirFile = File(baseDir)
        if (!baseDirFile.exists()) {
            baseDirFile.mkdirs()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //判断是否已经赋予权限
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                //如果应用之前请求过此权限但用户拒绝了请求，此方法将返回 true。
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    )
                ) {//这里可以写个对话框之类的项向用户解释为什么要申请权限，并在对话框的确认键后续再次申请权限
                    toast(R.string.get_permission)
                }
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION
                )

            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when {
                requestCode == REQUEST_CODE_PICK -> {
                    //将数据带到下一页
                    var intent = Intent(this@MainActivity, ChoosePicActivity::class.java)
                    intent.data = data!!.data
                    startActivity(intent)
                }
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_PERMISSION) {
            for (i in permissions.indices) {
                if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                    toast("权限申请失败")
                    finish()
                    break
                }
            }
        }
    }
}
