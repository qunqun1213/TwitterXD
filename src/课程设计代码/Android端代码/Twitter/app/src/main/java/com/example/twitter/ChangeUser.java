package com.example.twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChangeUser extends AppCompatActivity {
   // public static final String Web = "http://169.254.118.48:8080/Twitter/updatename.jsp";
    public static final String Web = "http://192.168.43.228:8080/Twitter/updatename.jsp";
    TextView origin;
    EditText now;
    Button change;
    String originname;
    String _idnumber;
    String now_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_edit_name);
        origin = (TextView) findViewById(R.id.origin_name);
        originname = getIntent().getStringExtra("_name");
        origin.setText(originname);
        now = (EditText)findViewById(R.id.now_name);

        _idnumber = getIntent().getStringExtra("userId");
        change = (Button) findViewById(R.id.edit_name);
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jsonobject();
                now_name = now.getText().toString();
                Intent intent = new Intent();
                intent.putExtra("newname", now_name);
                setResult(111, intent);
                finish();
            }
        });
    }
    public void jsonobject(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端

                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("_name", now_name)
                            .add("userId",_idnumber)
                            .build();
                    // 声明请求
                    Request request = new Request.Builder()
                            .url(Web)
                            .post(requestBody)
                            .build();
                    // 客户端发起请求
                    Response response = client.newCall(request).execute();
                    // 返回值
                    final String data = response.body().string();
                    // 解析ｊｓｏｎ
                    final String result = data;

                    if( data.trim().equals("1")) {
                        // 解析ＸＭＬ
                        // final String result = parseXml(data);
                        // 显示
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {

                                Toast.makeText(ChangeUser.this, "成功", Toast.LENGTH_SHORT).show();

                            }
                        });

                    }
                    else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(ChangeUser.this, "失败", Toast.LENGTH_SHORT).show();


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
