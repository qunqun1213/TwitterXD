package com.example.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Other_Homepage extends AppCompatActivity {
    RadioButton guanzhu;
    Button followin;
    ImageView headimage;
    TextView name;
    RadioButton fanm;
    private Activity activity;
    String _idnumber;
    String _name;
    String _imghead;
    String _id;
    String fan;
    String follow;
    String followId;//当前用户的id
    int type;
    /*public static final String web = "http://169.254.118.48:8080" + "/Twitter/get_my_twitter_json.jsp";
    public static final String guanzhuweb = "http://192.168.0.102:8080" + "/Twitter/get_all_twitter_json.jsp";
    public static final String quguan = "http://169.254.118.48:8080" + "/Twitter/Unfollow.jsp";
    public static final String gz = "http://169.254.118.48:8080" + "/Twitter/dofollow.jsp";
*/
    public static final String web = "http://192.168.43.228:8080" + "/Twitter/get_my_twitter_json.jsp";
    public static final String guanzhuweb = "http://192.168.43.228:8080" + "/Twitter/get_all_twitter_json.jsp";
    public static final String quguan = "http://192.168.43.228:8080" + "/Twitter/Unfollow.jsp";
    public static final String gz = "http://192.168.43.228:8080" + "/Twitter/dofollow.jsp";


    private View view;//定义view用来设置fragment的layout
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以Info实体类为对象的数据集合
    private ArrayList<Info> _data = new ArrayList<Info>();
    //自定义recyclerveiw的适配器
    //private CollectRecycleAdapter mCollectRecyclerAdapter;
    private OtherAllAdapter allAdapter;
    //下拉刷新控件
    private SwipeRefreshLayout refreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other__homepage);

        _idnumber =  getIntent().getStringExtra("userId");
        _name = getIntent().getStringExtra("_name");
        _imghead = getIntent().getStringExtra("_imghead");
        fan = getIntent().getStringExtra("fan");
        follow = getIntent().getStringExtra("follow");
        followId = getIntent().getStringExtra("followId");
        type  = getIntent().getIntExtra("type",0);//-1为关注 -2为粉丝
      //  Toast.makeText(Other_Homepage.this,String.valueOf(type) + _idnumber + followId,Toast.LENGTH_SHORT).show();
        guanzhu = (RadioButton) findViewById(R.id.other_follow);
        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Other_Homepage.this, FollowPage.class);
                intent.putExtra("userId",_idnumber);
                startActivity(intent);
            }
        });
        fanm = (RadioButton) findViewById(R.id.other_fan);
        fanm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Other_Homepage.this, FanPage.class);
                intent.putExtra("userId",_idnumber);
                startActivity(intent);
            }
        });
        followin = (Button) findViewById(R.id.followin);
        if(type == -10){
            followin.setVisibility(View.INVISIBLE);
        }
        else if(type == -2){
            followin.setText("关注");
            followin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    guanzhuobject();
                }
            });
        }
        //0为互关 -1 为关注
        else if(type == -1){
            followin.setText("取关");
            followin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    quguanobject();
                }
            });
        }
            //_id = getIntent().getStringExtra("hostId");

        name = (TextView)findViewById(R.id.other_name);
        name.setText(_idnumber);

        headimage = (ImageView)findViewById(R.id.other_headimage);
        File file = new File(_imghead);
        if(file.exists())
            headimage.setImageURI(Uri.parse( _imghead));
        else
            headimage.setImageResource(R.drawable.dft);
      //  Toast.makeText(Other_Homepage.this, _idnumber, Toast.LENGTH_SHORT).show();
        refreshLayout = (SwipeRefreshLayout)findViewById(R.id.othersrl);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新

                //mCollectRecyclerAdapter.notifyDataSetChanged();
                jsonObject();
                refreshLayout.setRefreshing(false);
            }
        });
        jsonObject();
        ActivityCollector.addActivtiy(this);
    }
    private void initRecyclerView() {
        //获取RecyclerView
        mCollectRecyclerView=(RecyclerView)findViewById(R.id.list_ohterhppage);
        //创建adapter
        //  mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), _data);
        allAdapter = new OtherAllAdapter(_data,Other_Homepage.this);
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(allAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(Other_Homepage.this, LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(Other_Homepage.this,DividerItemDecoration.VERTICAL));
        mCollectRecyclerView.setItemAnimator (new DefaultItemAnimator());
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        /*mCollectRecyclerAdapter.setOnItemClickListener(new CollectRecycleAdapter.OnItemClickListener() {
            @Override
            public void OnItemClick(View view, Info data) {
                //此处进行监听事件的业务处理
                //Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();
            }
        });*/
    }

    private void jsonObject(){
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
                            initRecyclerView();
                            allAdapter.notifyDataSetChanged();
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
        if (_data != null)
            _data.clear();
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
                String _name2 = object.getString("_name2");;
                //账号2
                String _idnumber2 = object.getString("_idnumber2");;
                //发言时间2
                String _wordTime2 = object.getString("_wordTime2");;
                //内容2
                String _content2 = object.getString("_content2");;
                Info info = new Info( _imghead,  _name,  _idnumber,  _wordTime,  _content,
                        _ctm,  _tn,  _lk,  _name2,  _idnumber2,  _wordTime2,  _content2);
                info.setType(type);
                info.setPic(object.getString("pic"));
                info.setArticleId(articleId);
                //Toast.makeText(getActivity(),String.valueOf(type),Toast.LENGTH_SHORT).show();
                //Info minfo = new Info(imgId, name, idnumber, wordTime, content, twiid);
                // builder.append(imgId).append(name).append(idnumber).append(wordTime).append(content).append(twiid).append("\n");
                _data.add(info);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //return builder.toString();
    }

    private void guanzhuobject(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("userId",followId)
                            .add("followId",_idnumber)
                            .build();
                    Request request = new Request.Builder()
                            .url(gz)
                            .post(requestBody)
                            .build();
                    // 客户端发起请求
                    Response response = client.newCall(request).execute();
                    // 返回值
                    final String data = response.body().string();
                    // 解析ｊｓｏｎ
                    //final String result =
                    //parseguanzhuobject(data);
//                    显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*不知道为什么一开始一定要set*/
                            initRecyclerView();
                            allAdapter.notifyDataSetChanged();
                            //_tvresult.setText("");
                        }
                    });
                    if(data.trim().equals("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Other_Homepage.this,"已关注",Toast.LENGTH_SHORT).show();
                                type = -1;
                                Intent intent = new Intent(Other_Homepage.this,Other_Homepage.class);
                                intent.putExtra("userId",_idnumber);
                                intent.putExtra("_imghead",_imghead);
                                intent.putExtra("_name",_name);
                                intent.putExtra("fan",fan);
                                intent.putExtra("follow",follow);
                                intent.putExtra("followId",followId);
                                intent.putExtra("type",type);
                                finish();
                                startActivity(intent);
                                followin.setText("取关");
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
    private void quguanobject(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("userId",followId)
                            .add("followId",_idnumber)
                            .build();
                    Request request = new Request.Builder()
                            .url(quguan)
                            .post(requestBody)
                            .build();
                    // 客户端发起请求
                    Response response = client.newCall(request).execute();
                    // 返回值
                    final String data = response.body().string();

//                    显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initRecyclerView();
                            allAdapter.notifyDataSetChanged();
                            //_tvresult.setText("");
                        }
                    });
                    if(data.trim().equals("1")){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(Other_Homepage.this,"已取关",Toast.LENGTH_SHORT).show();
                                type = -2;
                                Intent intent = new Intent(Other_Homepage.this,Other_Homepage.class);
                                intent.putExtra("userId",_idnumber);
                                intent.putExtra("_imghead",_imghead);
                                intent.putExtra("_name",_name);
                                intent.putExtra("fan",fan);
                                intent.putExtra("follow",follow);
                                intent.putExtra("followId",followId);
                                intent.putExtra("type",type);
                                finish();
                                startActivity(intent);
                                followin.setText("关注");
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
    private void parseguanzhuobject(String jsonStr){
        StringBuilder builder = new StringBuilder();
        if (_data != null)
            _data.clear();
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
                String _name2 = object.getString("_name2");;
                //账号2
                String _idnumber2 = object.getString("_idnumber2");;
                //发言时间2
                String _wordTime2 = object.getString("_wordTime2");;
                //内容2
                String _content2 = object.getString("_content2");;
                Info info = new Info( _imghead,  _name,  _idnumber,  _wordTime,  _content,
                        _ctm,  _tn,  _lk,  _name2,  _idnumber2,  _wordTime2,  _content2);
                info.setType(type);
                //Toast.makeText(getActivity(),String.valueOf(type),Toast.LENGTH_SHORT).show();
                //Info minfo = new Info(imgId, name, idnumber, wordTime, content, twiid);
                // builder.append(imgId).append(name).append(idnumber).append(wordTime).append(content).append(twiid).append("\n");
                _data.add(info);
            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //return builder.toString();
    }
}
