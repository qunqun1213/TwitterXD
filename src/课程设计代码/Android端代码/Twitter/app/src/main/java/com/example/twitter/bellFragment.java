package com.example.twitter;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
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

/**
 * Created by hasee on 2018/12/1.
 */

public class bellFragment extends Fragment {
    public static final String Web = "http://192.168.43.228:8080/Twitter/get_recent_twitter.jsp";
    private View mview;//定义view用来设置fragment的layout
    public RecyclerView mRecycleView;//定义RecyclerView
    //定义以Tongzhi实体类为对象的数据集合
    private ArrayList<Info> mdata = new ArrayList<Info>();
    //自定义recyclerveiw的适配器
    private TongzhiAdapter mTongzhiAdapter;
    TextView _tvresultt;
    private Activity activity;
    String _idnumber;
    String _name;
    String _imghead;
    String fan;
    String follow;

    //下拉刷新控件
    private SwipeRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mview = inflater.inflate(R.layout.bell_fragment,container,false);
        //_tvresultt = (TextView)getActivity().findViewById(R.id._result);
        //对recycleview进行配置
        //initRecycleView();
        refreshLayout = (SwipeRefreshLayout)mview.findViewById(R.id.blsrl);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                //数据重新加载完成后，提示数据发生改变，并且设置现在不在刷新

                //mCollectRecyclerAdapter.notifyDataSetChanged();
                blObject();
                refreshLayout.setRefreshing(false);
            }
        });
        //模拟数据
        blObject();
        return mview;
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

    private void initRecycleView() {
        //获取RecyclerView
        mRecycleView=(RecyclerView)mview.findViewById(R.id.list_tongzhi);
        //创建adapter
        mTongzhiAdapter = new TongzhiAdapter(getActivity(), mdata);
        //给RecyclerView设置adapter
        mRecycleView.setAdapter(mTongzhiAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局
        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mRecycleView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mRecycleView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mRecycleView.setItemAnimator (new DefaultItemAnimator());
        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        mTongzhiAdapter.setOnITemClickListener(new TongzhiAdapter.OnITemClickListener() {
            @Override
            public void OnItemClick(View view, Info data) {
                //此处进行监听事件的业务处理
                //Toast.makeText(getActivity(),"我是item",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void blObject(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("_idnumber",_idnumber)
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
                    parseblObject(data);
//                    显示
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            initRecycleView();
                            mTongzhiAdapter.notifyDataSetChanged();
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

    private void parseblObject(String jsonStr){
        //StringBuilder builder = new StringBuilder();
        if (mdata != null)
            mdata.clear();
        try {
            JSONArray blArray = new JSONArray(jsonStr);// 生成json数组
            for( int i = 0; i < blArray.length(); ++i ){
                JSONObject object = blArray.getJSONObject(i);// 获取第i个元素

                String _imghead = object.getString("_imghead");
                String _name = object.getString("_name");
                String _content = object.getString("_content");
                Info info = new Info();
                info.set_imghead(_imghead);
                info.set_name(_name);
                info.set_content(_content);
                info.setArticleId(object.getInt("articleId"));
              //  info.setPic(object.getString("pic"));
                //builder.append(imgId).append(name).append(jinti).append(content).append(twiid).append("\n");
                mdata.add(info);
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //return builder.toString();
    }
}
