package com.example.twitter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UserArticle extends AppCompatActivity {

   // public static final String web = "http://192.168.0.102:8080" + "/Twitter/getcmt.jsp";
  //  public static final String web = "http://169.254.118.48:8080" + "/Twitter/getcmt.jsp";
    public static final String web = "http://192.168.43.228:8080" + "/Twitter/getcmt.jsp";
    public RecyclerView mView;
    private ArrayList<Info> UserA = new ArrayList<Info>();
    //自定义recyclerveiw的适配器
    private CmtAdapter mAdapter;
    TextView _tvresult;
    private View view;
    int articleId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_article);
     //   init();
        articleId = getIntent().getIntExtra("articleId",0);
     //   Toast.makeText(UserArticle.this,"11111",Toast.LENGTH_SHORT).show();
        jsonObject();
        ActivityCollector.addActivtiy(this);
    }

    private void initR(){
        mView = (RecyclerView) findViewById(R.id.list_cmt);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mView.setLayoutManager(linearLayoutManager);
        mAdapter = new CmtAdapter(UserA);
        mView.setAdapter(mAdapter);
    }

   // String _imghead, String _name, String _idnumber, String _wordTime, String _content
    /*
    private void init(){
        Info info1 = new Info("tx3.jpg", "juju", "123", "2018-12-20", "lalalalalalauwiksg");
        UserA.add(info1);
    }*/

    private void jsonObject(){
    //    Toast.makeText(UserArticle.this,String.valueOf(articleId),Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("articleId",String.valueOf(articleId) )
                            .build();
                    // 声明请求
                    Request request = new Request.Builder()
                            .url(web)
                            .post(requestBody)
                            .build();
                    // 客户端发起请求
                    Response response = client.newCall(request).execute();
                    // 返回值
                    final String data = response.body().string();
                    // 解析ｊｓｏｎ
                    //final String result =
                    parseJsonObject(data);
//                    显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*不知道为什么一开始一定要set*/
                            initR();
                            mAdapter.notifyDataSetChanged();
                            //_tvresult.setText("");
                        }
                    });
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    private void parseJsonObject(String jsonStr){
        StringBuilder builder = new StringBuilder();
        if (UserA != null)
            UserA.clear();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);// 生成json数组

            for( int i = 0; i < jsonArray.length(); ++i ){
                JSONObject object = jsonArray.getJSONObject(i);// 获取第i个元素

                int articleId = object.getInt("articleId");
                String _imghead = object.getString("_imghead");
                String _name = object.getString("_name");
                String _idnumber = object.getString("_idnumber");
                String _content = object.getString("_content");
                String _wordTime = object.getString("_wordTime");
                String _ctm =object.getString("_ctm");
                String _tn = object.getString("_tn");
                String _lk = object.getString("_lk");
                //类型   是否是转发的 0是原创 不是0则为转发的推文的id
                int type = object.getInt("type");
                //名字2
                String _name2 = object.getString("_name2");
                //账号2
                String _idnumber2 = object.getString("_idnumber2");
                //发言时间2
                String _wordTime2 = object.getString("_wordTime2");
                //内容2
                String _content2 = object.getString("_content2");
                //Info info = new Info( _idnumber,  _name,  _imghead,  _wordTime,  _content);
                Info info = new Info( _imghead,  _name,  _idnumber,  _wordTime,  _content,
                        _ctm,  _tn,  _lk,  _name2,  _idnumber2,  _wordTime2,  _content2);
                info.setType(type);
                info.setArticleId(articleId);
                info.setPic(object.getString("pic"));
                UserA.add(info);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //return builder.toString();
    }

}
