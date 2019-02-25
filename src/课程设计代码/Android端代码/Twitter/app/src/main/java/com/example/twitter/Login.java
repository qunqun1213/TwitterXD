package com.example.twitter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Login extends AppCompatActivity {

    EditText password;
    EditText number;
    Button btn_login;
    Info info;
    //public static final String Login = "http://192.168.43.235/twitter/login.jsp" ;
  //  public static final String Login = "http://192.168.0.102:8080/Twitter/login.jsp" ;
   // public static final String Login = "http://169.254.118.48:8080/Twitter/login.jsp" ;
    public static final String Login = "http://192.168.43.228:8080/Twitter/login.jsp" ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.hide();
        }
        password = (EditText) findViewById(R.id.password);
        number = (EditText) findViewById(R.id.number);
        btn_login = (Button)findViewById(R.id.log);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                login();
            }
        });
        ActivityCollector.addActivtiy(this);
    }
    private void login(){
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
                            .url(Login)
                            .post(requestBody)
                            .build();
                    Response response = client.newCall(request).execute();
                    final String data = response.body().string();
                    final Info result = parseJsonObject(data);
                    if(result.get_idnumber() != null){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(Login.this,Homepage.class);
                                intent.putExtra("_idnumber",result.get_idnumber());
                                intent.putExtra("_imghead",result.get_imghead());
                                intent.putExtra("_name",result.get_name());
                                intent.putExtra("fan",result.getFan());
                                intent.putExtra("follow",result.getFollow());
                                startActivity(intent);
                            }
                        });
                    }
                    else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Login.this,"用户名或密码错误",Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }).start();
    }
    private Info parseJsonObject(String jsonStr){
        StringBuilder builder = new StringBuilder();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);// 生成json数组
            for( int i = 0; i < jsonArray.length(); ++i ){
                JSONObject object = jsonArray.getJSONObject(i);// 获取第i个元素
                String _imghead = object.getString("_imghead");
                String _name = object.getString("_name");
                String _idnumber = object.getString("_idnumber");
                String fan = object.getString("fan");
                String follow = object.getString("follow");
                info = new Info();
                info.set_idnumber(_idnumber);
                info.set_name(_name);
                info.set_imghead(_imghead);
                info.setFan(fan);
                info.setFollow(follow);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        return info;
    }

}
