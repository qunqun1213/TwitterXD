package com.example.twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Register extends AppCompatActivity {

    EditText password;
    EditText number;
    Button btn_register;
    //public static final String Register = "http://192.168.43.235/twitter/register.jsp" ;
   // public static final String Register = "http://192.168.43.235/twitter/register.jsp" ;
    public static final String Register = "http://192.168.43.228:8080/Twitter/register.jsp" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        password = (EditText) findViewById(R.id.password);
        number = (EditText) findViewById(R.id.number);
        btn_register = (Button)findViewById(R.id.next);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                register();
            }
        });
        ActivityCollector.addActivtiy(this);
    }
    private void register(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    final String id = number.getText().toString();
                    final String pass = password.getText().toString();
                    OkHttpClient client = new OkHttpClient();

                    RequestBody requestBody = new FormBody.Builder()
                            .add("id",id)
                            .add("pass",pass)
                            .build();
                    Request request = new Request.Builder()
                            .url(Register)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    final String data = response.body().string();
                    final String result = data;
                    if(data.trim().equals("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this,"注册成功，即将跳转到登录界面",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(Register.this,Login.class);
                                startActivity(intent);
                            }
                        });
                    }
                    else if(data.trim().equals("2")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this,"密码长度不足8位",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else if(data.trim().equals("3")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this,"该账户ID已被使用",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Register.this,"注册失败",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }

}
