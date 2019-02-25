package com.example.twitter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeImageActivity extends AppCompatActivity {
    Button main_btn;
    ImageView main_icon;
    Button queren;
    PhotoPopUpWindow mPhotoPopupWindow;
    private static final int REQUEST_IMAGE_GET = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_SMALL_IMAGE_CUTTING = 2;
    private static final int REQUEST_BIG_IMAGE_CUTTING = 3;
    private static final String IMAGE_FILE_NAME = "icon.jpg";
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE" };
    File file;
    String _idnumber;
    String path;
    int type = 0;
    //public static final String webUpdate = "http://169.254.118.48:8080/updateheadimage.jsp";
    public static final String webUpdate = "http://192.168.43.228:8080/Twitter/updateheadimage.jsp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_image);
        _idnumber =  getIntent().getStringExtra("userId");
        path = getIntent().getStringExtra("path");
      //  Toast.makeText(ChangeImageActivity.this,_idnumber + path,Toast.LENGTH_SHORT).show();
        verifyStoragePermissions(ChangeImageActivity.this);
        main_icon = (ImageView) findViewById(R.id.main_icon);
        main_btn = (Button) findViewById(R.id.main_btn);
        main_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoPopupWindow = new PhotoPopUpWindow(ChangeImageActivity.this, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 进入相册选择
                        mPhotoPopupWindow.dismiss();
                        Intent intent = new Intent(Intent.ACTION_PICK);
                        intent.setType("image/*");
                        // 判断系统中是否有处理该 Intent 的 Activity
                        if (intent.resolveActivity(getPackageManager()) != null) {
                            startActivityForResult(intent, REQUEST_IMAGE_GET);
                        } else {
                            Toast.makeText(ChangeImageActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 拍照

                    }
                });
                View rootView = LayoutInflater.from(ChangeImageActivity.this).inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        queren = (Button) findViewById(R.id.queren);

        queren.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type == 0){
                    Toast.makeText(ChangeImageActivity.this,"未选择图片",Toast.LENGTH_SHORT).show();
                }
                else {
                    update();
                    Intent intent = new Intent(ChangeImageActivity.this, Login.class);
                    startActivity(intent);
                }

            }
        });
    }
    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 回调成功
        if (resultCode == RESULT_OK) {
            switch (requestCode) {

                // 小图切割
                case REQUEST_SMALL_IMAGE_CUTTING:
                    if (data != null) {
                        setPicToView(data);
                    }
                    break;

                // 相册选取
                case REQUEST_IMAGE_GET:
                    try {
                        startSmallPhotoZoom(data.getData());
                    } catch (NullPointerException e) {
                        e.printStackTrace();
                    }
                    break;

                //......
            }
        }
    }
    /**
     * 小图模式切割图片
     * 此方式直接返回截图后的 bitmap，由于内存的限制，返回的图片会比较小
     */
    public void startSmallPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300); // 输出图片大小
        intent.putExtra("outputY", 300);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, REQUEST_SMALL_IMAGE_CUTTING);
    }
    /**
     * 大图模式切割图片
     * 直接创建一个文件将切割后的图片写入
     */
    public void startBigPhotoZoom(Uri uri) {
        // 创建大图文件夹
        Uri imageUri = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //String storage = Environment.getExternalStorageDirectory().getPath();
            File dirFile = new File("/sdcard/bigIcon");
            if (!dirFile.exists()) {
                if (!dirFile.mkdirs()) {
                    Log.e("TAG", "文件夹创建失败");
                } else {
                    Log.e("TAG", "文件夹创建成功");
                }
            }
            File file = new File(dirFile, System.currentTimeMillis() + ".jpg");
            imageUri = Uri.fromFile(file);
        }
        // 开始切割
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1); // 裁剪框比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 600); // 输出图片大小
        intent.putExtra("outputY", 600);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", false); // 不直接返回数据
        intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri); // 返回一个文件
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        startActivityForResult(intent, REQUEST_BIG_IMAGE_CUTTING);
    }
    /**
     * 小图模式中，保存图片后，设置到视图中
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data"); // 直接获得内存中保存的 bitmap
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File("/sdcard/smallIcon");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                        Toast.makeText(ChangeImageActivity.this,"文件夹创建失败",Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                        Toast.makeText(ChangeImageActivity.this,"文件夹创建成功",Toast.LENGTH_SHORT).show();
                    }
                }
                file = new File(dirFile, System.currentTimeMillis() + ".jpg");
             //   Toast.makeText(ChangeImageActivity.this,_idnumber +"文件路径"+file.toString(),Toast.LENGTH_SHORT).show();
                path = file.toString();
                type = 1;
                // 保存图片
                FileOutputStream outputStream = null;
                try {
                    outputStream = new FileOutputStream(file);
                    photo.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
                    outputStream.flush();
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            // 在视图中显示图片
            main_icon.setImageBitmap(photo);
        }
    }
    // 修改
    private void update(){
        //Toast.makeText(ChangeImageActivity.this,"调用" + file.toString() + _idnumber,Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();
                    // 上传参数
                    RequestBody requestBody = new FormBody.Builder()
                            .add("headimage", path)
                            .add("userId", _idnumber) // 新增参数
                            .build();
                    // 声明请求
                    Request request = new Request.Builder()
                            .url(webUpdate)
                            .post(requestBody) //post
                            .build();
                    // 客户端发起请求
                    Response response = client.newCall(request).execute();
                    // 返回值
                    final String data = response.body().string();

                    final String result = data;
              //      Toast.makeText(ChangeImageActivity.this,"调用" + path+ _idnumber,Toast.LENGTH_SHORT).show();
                    if( data.trim().equals("1")) {
                        // 解析ＸＭＬ
                        // final String result = parseXml(data);
                        // 显示
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(ChangeImageActivity.this, "成功", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangeImageActivity.this, "失败", Toast.LENGTH_SHORT).show();


                            }
                        });
                    }
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }
}
