package com.example.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by hasee on 2018/12/1.
 */

public class hpFragment extends Fragment {
    //public static final String web = "http://192.168.43.235" + "/twitter/get_all_twitter_json.jsp";

  //  public static final String web = "http://192.168.0.102:8080" + "/Twitter/get_all_twitter_json.jsp";
    //public static final String web = "http://169.254.118.48:8080" + "/Twitter/get_all_twitter_json.jsp";
    //public static final String web = "http://169.254.118.48:8080" + "/Twitter/get_all_twitter_json.jsp";
    public static final String web = "http://192.168.43.228:8080" + "/Twitter/get_all_twitter_json.jsp";
    private View view;//定义view用来设置fragment的layout
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以Info实体类为对象的数据集合
    private ArrayList<Info> _data = new ArrayList<Info>();
    //自定义recyclerveiw的适配器
    //private CollectRecycleAdapter mCollectRecyclerAdapter;
    private AllAdapter allAdapter;
    TextView _tvresult;

    FloatingActionButton addTwitter;
    String _idnumber;
    String _name;
    String _imghead;
    String fan;
    String follow;
    private Activity activity;

    //下拉刷新控件
    private SwipeRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.hp_fragment,container,false);
        //_tvresult = (TextView)getActivity().findViewById(R.id._result);
        //initData();

        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.hpsrl);
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
        addTwitter = (FloatingActionButton)view.findViewById(R.id.fab);
        addTwitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(),AddTwitterActivity.class);
                intent.putExtra("_idnumber",_idnumber);
                intent.putExtra("_imghead",_imghead);
                intent.putExtra("_name",_name);
                intent.putExtra("fan",fan);
                intent.putExtra("follow",follow);

                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        activity = (Activity)context;
        _idnumber = ((Homepage)activity).get_idnumber();
        _name = ((Homepage)activity).get_name();
        _imghead = ((Homepage)activity).get_headimg();
        fan = ((Homepage)activity).getFan();
        follow = ((Homepage)activity).getFollow();

    }

    /**
     * TODO 对recycleview进行配置
     */
    private void initRecyclerView() {
        //获取RecyclerView
        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.list_info);
        //创建adapter
      //  mCollectRecyclerAdapter = new CollectRecycleAdapter(getActivity(), _data);
        allAdapter = new AllAdapter(_data,getActivity());
        //给RecyclerView设置adapter
        mCollectRecyclerView.setAdapter(allAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mCollectRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mCollectRecyclerView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
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
                    getActivity().runOnUiThread(new Runnable() {
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
                String _name2 = object.getString("_name2");
                //账号2
                String _idnumber2 = object.getString("_idnumber2");
                //发言时间2
                String _wordTime2 = object.getString("_wordTime2");
                //内容2
                String _content2 = object.getString("_content2");
                Info info = new Info( _imghead,  _name,  _idnumber,  _wordTime,  _content,
                        _ctm,  _tn,  _lk,  _name2,  _idnumber2,  _wordTime2,  _content2);
                info.setType(type);
                info.setArticleId(articleId);
                info.setFan(fan);
                info.setFollow(follow);
                info.setPic(object.getString("pic"));
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
