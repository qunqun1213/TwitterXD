package com.example.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

/**
 * Created by hasee on 2018/12/1.
 */

public class msgFragment extends Fragment {
    public static final String web = "http://192.168.43.228:8080" + "/Twitter/get_my_twitter_json.jsp";

    private View view;//定义view用来设置fragment的layout
    public RecyclerView mCollectRecyclerView;//定义RecyclerView
    //定义以Info实体类为对象的数据集合
    private ArrayList<Info> _data = new ArrayList<Info>();
    //自定义recyclerveiw的适配器
    //private CollectRecycleAdapter mCollectRecyclerAdapter;
    private AllAdapter allAdapter;

    RadioButton guanzhu;
    ImageView headimagem;
    TextView name;
    RadioButton mfan;
    private Activity activity;
    String _idnumber;
    String _name;
    String _imghead;
    String fan;
    String follow;
    String newname;
    //下拉刷新控件
    private SwipeRefreshLayout refreshLayout;
    @Nullable
    @Override
    public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.msg_fragment,container,false);
        guanzhu = (RadioButton) view.findViewById(R.id.follow);
        guanzhu.setText("关注 ");
        guanzhu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FollowPage.class);
                intent.putExtra("userId",_idnumber);
                startActivity(intent);
            }
        });
        headimagem = (ImageView) view.findViewById(R.id.headimagem);
        File file = new File(_imghead);
        if(file.exists())
            headimagem.setImageURI(Uri.parse(_imghead));
        else
            headimagem.setImageResource(R.drawable.dft);
        name = (TextView) view.findViewById(R.id.namem);
        name.setText(_idnumber);
        name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeUser.class);
                intent.putExtra("_name",_name);
                intent.putExtra("userId",_idnumber);
                /*
                 * 带返回值的跳转方法，参数1：intent意图， 第二个参数请求码，是一个requestCode值，如果有多个按钮都要启动Activity，
                 * 则requestCode标志着每个按钮所启动的Activity
                 */
                startActivityForResult(intent, 222);
            }
        });
        headimagem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChangeImageActivity.class);
                intent.putExtra("userId",_idnumber);
                intent.putExtra("path",_imghead);
                startActivityForResult(intent, 222);
            }
        });
        mfan = (RadioButton) view.findViewById(R.id.fan);
        mfan.setText("粉丝 ");
        mfan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), FanPage.class);
                intent.putExtra("userId",_idnumber);
                startActivity(intent);
            }
        });
        refreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.msgsrl);
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
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // 判断请求码和返回码是不是正确的，这两个码都是我们自己设置的
        if (requestCode == 222 && resultCode == 111) {
            _name = data.getStringExtra("newname");// 拿到返回过来的输入的账号
            //name.setText(newname);

        }
        if (requestCode == 222 && resultCode == 222) {
            String imgid = data.getStringExtra("_imgId");// 拿到返回过来的输入的账号
            //headimagem.setImageURI(Uri.parse(imgid));

        }
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
    private void initRecyclerView() {
        //获取RecyclerView
        mCollectRecyclerView=(RecyclerView)view.findViewById(R.id.list_hppage);
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
}
