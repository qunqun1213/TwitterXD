package com.example.twitter;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AddTwitterActivity extends AppCompatActivity {
    TextView toolbarName;
    EditText content;
    Button send;
    Button cancel;
    Button pic;
    com.nex3z.flowlayout.FlowLayout mFlowLayout ;
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
    String path = "noPic";
    String _idnumber;
    String _name;
    String _imghead;
    String fan;
    String follow;

    String text;
    String time;
    int type = 0;

    //final String sendTwitter = "http://192.168.0.102:8080"+"/Twitter/addTwitter.jsp";
    //final String sendTwitter = "http://169.254.118.48:8080"+"/Twitter/addTwitter.jsp";
    final String sendTwitter = "http://192.168.43.228:8080"+"/Twitter/addTwitter.jsp";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_edit);

        _idnumber = getIntent().getStringExtra("_idnumber");
        _name = getIntent().getStringExtra("_name");
        _imghead = getIntent().getStringExtra("_imghead");
        fan = getIntent().getStringExtra("fan");
        follow = getIntent().getStringExtra("follow");
        verifyStoragePermissions(AddTwitterActivity.this);
        mFlowLayout = (com.nex3z.flowlayout.FlowLayout) findViewById(R.id.flow_pic);
        content = (EditText)findViewById(R.id.content_send);
        send = (Button)findViewById(R.id.send);
        cancel = (Button)findViewById(R.id.cancel);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//取消发送推文
                Intent intent = new Intent(AddTwitterActivity.this,Homepage.class);
                intent.putExtra("_idnumber",_idnumber);
                intent.putExtra("_imghead",_imghead);
                intent.putExtra("_name",_name);
                intent.putExtra("fan",fan);
                intent.putExtra("follow",follow);
                startActivity(intent);
            }
        });
        pic = (Button)findViewById(R.id.btn_pic);
        pic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPhotoPopupWindow = new PhotoPopUpWindow(AddTwitterActivity.this, new View.OnClickListener() {
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
                            Toast.makeText(AddTwitterActivity.this, "未找到图片查看器", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 拍照

                    }
                });
                View rootView = LayoutInflater.from(AddTwitterActivity.this).inflate(R.layout.activity_main, null);
                mPhotoPopupWindow.showAtLocation(rootView,
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
            }
        });

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTwitter();
            }
        });

        toolbarName = (TextView)findViewById(R.id.toolbar_name);
        toolbarName.setText(_name);
        ActivityCollector.addActivtiy(this);
    }
    private void addTwitter(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    text = content.getText().toString();

                    SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
                    Date date = new Date(System.currentTimeMillis());
                    time = format.format(date);
                    OkHttpClient client = new OkHttpClient();
                    final  String data;
                    RequestBody requestBody = new FormBody.Builder()
                            .add("id",_idnumber)
                            .add("text",text)
                            .add("time",time)
                            .add("name",_name)
                            .add("img_id",_imghead)
                            .add("path",path)
                            .add("type",String.valueOf(type))
                            .build();
                    Request request = new Request.Builder()
                            .url(sendTwitter)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    data = response.body().string();
                    if(data.trim().equals("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddTwitterActivity.this,"成功",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(AddTwitterActivity.this,Homepage.class);
                                intent.putExtra("_idnumber",_idnumber);
                                intent.putExtra("_imghead",_imghead);
                                intent.putExtra("_name",_name);
                                intent.putExtra("fan",fan);
                                intent.putExtra("follow",follow);
                                startActivity(intent);
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(AddTwitterActivity.this,"发表失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
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
     * 小图模式中，保存图片后，设置到视图中
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap photo = extras.getParcelable("data"); // 直接获得内存中保存的 bitmap
            // 创建 smallIcon 文件夹
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                String storage = Environment.getExternalStorageDirectory().getPath();
                File dirFile = new File("/sdcard/twitterPic");
                if (!dirFile.exists()) {
                    if (!dirFile.mkdirs()) {
                        Log.e("TAG", "文件夹创建失败");
                        Toast.makeText(AddTwitterActivity.this,"文件夹创建失败",Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("TAG", "文件夹创建成功");
                        Toast.makeText(AddTwitterActivity.this,"文件夹创建成功",Toast.LENGTH_SHORT).show();
                    }
                }
                file = new File(dirFile, System.currentTimeMillis() + ".jpg");
               // Toast.makeText(AddTwitterActivity.this,_idnumber +"文件路径"+file.toString(),Toast.LENGTH_SHORT).show();
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
            // 显示图片
          //  pic.setImageURI(Uri.parse(path));
            //main_icon.setImageBitmap(photo);
            ImageView imageView = new ImageView(this);
            imageView.setImageURI(Uri.parse(path));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(400,
                    400);//两个400分别为添加图片的大小
            imageView.setLayoutParams(params);

            mFlowLayout.addView(imageView);
        }
    }

}
