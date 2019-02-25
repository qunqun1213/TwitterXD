package com.example.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class FollowPage extends AppCompatActivity {
   // public static final String Web = "http://169.254.118.48:8080/Twitter/get_follow.jsp";
    public static final String Web = "http://192.168.43.228:8080/Twitter/get_follow.jsp";
    public RecyclerView mRecycleView;//定义RecyclerView
    //定义以Tongzhi实体类为对象的数据集合
    private ArrayList<Info> mdata = new ArrayList<Info>();
    //自定义recyclerveiw的适配器
    private Follow_in_Adapter follow_in_adapter;
    private Activity activity;
    String _idnumber;
    String _name;
    String _imghead;
    String fan;
    String follow;
    Info info;
    Info result;
    //下拉刷新控件
    private SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_follow_page);
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.followsrl);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新

                //mCollectRecyclerAdapter.notifyDataSetChanged();
                followobject();
                refreshLayout.setRefreshing(false);
            }
        });
        followobject();
        ActivityCollector.addActivtiy(this);
       _idnumber =  getIntent().getStringExtra("userId");
        ActivityCollector.addActivtiy(this);
    }

    /**
     * TODO 对recycleview进行配置
     */

    private void initRecycleView() {
        //获取RecyclerView
        mRecycleView=(RecyclerView)findViewById(R.id.list_followin);
        //创建adapter
        follow_in_adapter = new Follow_in_Adapter(FollowPage.this, mdata);
        //给RecyclerView设置adapter
        mRecycleView.setAdapter(follow_in_adapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mRecycleView.setLayoutManager(new LinearLayoutManager(FollowPage.this, LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mRecycleView.addItemDecoration(new DividerItemDecoration(FollowPage.this,DividerItemDecoration.VERTICAL));
        mRecycleView.setItemAnimator (new DefaultItemAnimator());
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        follow_in_adapter.setOnITemClickListener(new Follow_in_Adapter.OnITemClickListener() {
            @Override
            public void OnItemClick(View view, Info data) {
                //此处进行监听事件的业务处理
                //Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();

            }
        });
    }
    public void followobject(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
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
                    //final String resultt =
                    result = parsefollowObject(data);
//                    显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initRecycleView();
                            follow_in_adapter.notifyDataSetChanged();

                            //_tvresultt.setText("");
                        }
                    });
                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }
    private Info parsefollowObject(String jsonStr){
        //StringBuilder builder = new StringBuilder();
        if (mdata != null)
            mdata.clear();
        try {
            JSONArray blArray = new JSONArray(jsonStr);// 生成json数组
            for( int i = 0; i < blArray.length(); ++i ){
                JSONObject object = blArray.getJSONObject(i);// 获取第i个元素
                info = new Info();
                info.set_imghead(object.getString("_imghead"));
                info.set_name(object.getString("_name"));
                info.set_idnumber(object.getString("_idnumber"));
                info.setFan(object.getString("fan"));
                info.setFollow(object.getString("follow"));
                info.setType(object.getInt("type"));
                //builder.append(imgId).append(name).append(jinti).append(content).append(twiid).append("\n");
                mdata.add(info);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //return builder.toString();
        return info;
    }
}
