package com.example.twitter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

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

public class fdFragment extends Fragment {
    public static final String web = "http://192.168.43.228:8080" + "/Twitter/gethot.jsp";

    private android.support.v7.widget.SearchView mSearchView;
    //   private SearchView mSearchView;
    private ListView mListView;
  //  private ArrayAdapter<String> mAdapter;
  private ArrayList<Info> _data = new ArrayList<Info>();
    private Toolbar mToolbar;
    private Menu menu;
    //定义RecyclerView
    public RecyclerView mView;
    private ArrayList<Info> fdata = new ArrayList<Info>();
    //自定义recyclerveiw的适配器
  //  private TopAdapter mAdapter;
    private fdAdapter fAdapter;
    TextView _tvResult;
    String _idnumber;
    String _name;
    String _imghead;
    String fan;
    String follow;
    private Activity activity;

    private View fview;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fview = inflater.inflate(R.layout.search_toolbar,container,false);

        initRview();;

        //RecyclerView中没有item的监听事件，需要自己在适配器中写一个监听事件的接口。参数根据自定义
        jsonObject();//初始化
      //  recCircle = new RecCircle(getContext());

        return fview;
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        //在fragment中使用oncreateOptionsMenu时需要在onCrateView中添加此方法，否则不会调用
        setHasOptionsMenu(true);

        AppCompatActivity appCompatActivity= (AppCompatActivity) getActivity();
        mToolbar = (Toolbar) appCompatActivity.findViewById(R.id.toolbar);

        //设置Toolbar
        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        super.onActivityCreated(savedInstanceState);
    }

    private void initRview(){
        mView = (RecyclerView) fview.findViewById(R.id.list_sousuo);
        //recCircle = (RecCircle)fview.findViewById(R.id.top_image);

//        recCircle.setType(RecCircle.TYPE_ROUND);
  //      recCircle.setRoundRadius(6);
        fAdapter = new fdAdapter(_data,getActivity());
        //给RecyclerView设置adapter
        mView.setAdapter(fAdapter);
        //设置layoutManager,可以设置显示效果，是线性布局、grid布局，还是瀑布流布局

        //参数是：上下文、列表方向（横向还是纵向）、是否倒叙
        mView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        //设置item的分割线
        mView.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        mView.setItemAnimator (new DefaultItemAnimator());

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
//        menu.clear();
//        if (getChildFragmentManager().getBackStackEntryCount() == 0) {
//            inflater.inflate(R.menu.main, menu);
//        }
        inflater.inflate(R.menu.main, menu);
        MenuItem searchItem = menu.findItem(R.id.search_button);
        super.onCreateOptionsMenu(menu, inflater);
    //    return true;
    }
    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()) {
            case R.id.search_button:
                //   Intent intent = new Intent(MainActivity.this, Click.class);
                Intent intent = new Intent(getActivity(), Click.class);
                intent.putExtra("_userId", _idnumber);
                startActivity(intent);
                //  Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }



    private void jsonObject(){
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
                            initRview();
                            fAdapter.notifyDataSetChanged();
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
