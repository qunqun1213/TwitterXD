package com.example.twitter;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Click extends AppCompatActivity {

    public static final String web = "http://192.168.43.228:8080" + "/Twitter/gethistory.jsp";

    private SearchView mSearchView;

    private ListView mView;
    private ArrayAdapter<String> mAdapter;
 //   private String [] data = {"Java","kotlin","C","C++","C#","Python","PHP","JavaScript"};
//    private Toolbar mToolbar;

    private ArrayList<String> mlist = new ArrayList<String>();

    TextView _tvresult;

    String userId;

    String [] _data = new String[5];

    String searchText = "hello";


    View view;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_first_list);
        userId = getIntent().getStringExtra("_userId");
        mView = (ListView) findViewById(R.id.list_sousuo);

        mSearchView = (SearchView) findViewById(R.id.search_edit_frame);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setIconified(false);

        jsonObject();
       // Toast.makeText(Click.this,"11111",Toast.LENGTH_SHORT).show();
        //搜索图标按钮的点击事件
      mSearchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Click.this, "打开搜索框", Toast.LENGTH_SHORT).show();
            }
        });


        //搜索框内容变化监听
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//点击提交按钮时
                Toast.makeText(Click.this, "Submit---提交", Toast.LENGTH_SHORT).show();

                searchText = query;

                Intent intent = new Intent(Click.this, searchActivity.class);
                //Toast.makeText(Click.this,"click"+searchText,Toast.LENGTH_SHORT).show();
                intent.putExtra("searchText", searchText);
                intent.putExtra("userId",userId);
                startActivity(intent);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//搜索框内容变化时
                if (!TextUtils.isEmpty(newText)) {
//              mListView.setFilterText(newText);
                    mAdapter.getFilter().filter(newText);
                }
                else {
                    mView.clearTextFilter();
                    mAdapter = new ArrayAdapter<>(Click.this, android.R.layout.simple_list_item_1, mlist);
                   // mAdapter = new ArrayAdapter<>(Click.this, android.R.layout.simple_list_item_1, data);
                    mView.setAdapter(mAdapter);
                    //mAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

        //搜索框展开时点击叉叉按钮关闭搜索框的点击事件
        mSearchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                Toast.makeText(Click.this, "关闭搜索框", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        ActivityCollector.addActivtiy(this);
    }



    private void initR(){

        mAdapter = new ArrayAdapter<>(Click.this, android.R.layout.simple_list_item_1, mlist);

      //  mAdapter = new ArrayAdapter<>(mlist);
        mView.setAdapter(mAdapter);
        mView.setTextFilterEnabled(true);
        mView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String content = mlist.get(position);
                //Toast.makeText(Click.this,content,Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Click.this, searchActivity.class);
                //Toast.makeText(Click.this,"click"+searchText+content,Toast.LENGTH_SHORT).show();
                intent.putExtra("searchText", content);
                intent.putExtra("userId",userId);
                startActivity(intent);
            }
        });

    }

    // String _imghead, String _name, String _idnumber, String _wordTime, String _content
    /*
    private void init(){
        Info info1 = new Info("tx3.jpg", "juju", "123", "2018-12-20", "lalalalalalauwiksg");
        UserA.add(info1);
    }*/

    private void jsonObject(){
           // Toast.makeText(Click.this,userId,Toast.LENGTH_SHORT).show();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("userId",userId)
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
         //           Toast.makeText(Click.this,"aaa",Toast.LENGTH_SHORT).show();
//                    显示
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*不知道为什么一开始一定要set*/
                            //Toast.makeText(Click.this,data,Toast.LENGTH_SHORT).show();
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
        if (mlist != null)
            mlist.clear();
        try {
            JSONArray jsonArray = new JSONArray(jsonStr);// 生成json数组

            for( int i = 0; i < jsonArray.length(); ++i ){
                JSONObject object = jsonArray.getJSONObject(i);// 获取第i个元素
            //    String userId = object.getString("_userId");
                // int _num = object.getInt("_num");
                String _content = object.getString("_content");
                _data[i] = _content;
                mlist.add(_data[i]);

            }

        }
        catch (Exception ex){
            ex.printStackTrace();
        }
        //return builder.toString();
    }



}
