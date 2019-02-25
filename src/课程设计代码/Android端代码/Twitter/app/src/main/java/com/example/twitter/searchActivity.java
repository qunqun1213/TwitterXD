package com.example.twitter;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class searchActivity extends AppCompatActivity implements View.OnClickListener {


    public static final String web = "http://192.168.43.228:8080" + "/Twitter/addhistory.jsp";
    private Fragment currentFragment=new Fragment();
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private MyPagerAdapter adapter;
    private LinearLayout layout;
    private RadioButton rb_rm,rb_yh;
    private RadioGroup mradiogroup;
    private Fragment fragment_rm,fragment_yh;
    private FragmentManager manager;
    private boolean isHpFragment = true;//跳转到这个页面的时候先显示的就是hpFragment
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;

    private SearchView mSearchView;

    String _idnumber;
    String _name;
    String _imghead;
    String fan;
    String follow;
    String searchText;

    /**
     * 按钮的没选中显示的图标
     */
    private int[] unselectedIconIds = { R.drawable.rmunclick,R.drawable.yhunclick};
    /**
     * 按钮的选中显示的图标
     */
    private int[] selectedIconIds = { R.drawable.rmclick,R.drawable.yhclick };
    TextView result;
    android.support.v7.app.ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_second_list);

        _idnumber = getIntent().getStringExtra("userId");
        searchText = getIntent().getStringExtra("searchText");
        actionBar = getSupportActionBar();
        int permission = ActivityCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED ) {
            // 请求权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    EXTERNAL_STORAGE_REQ_CODE);
        }
//        bindID();
        initFragment();
        initView();
        addHistory();

        mSearchView = (SearchView) findViewById(R.id.searchview_result);
        mSearchView.setSubmitButtonEnabled(true);
        mSearchView.setIconified(false);

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {//点击提交按钮时
                Toast.makeText(searchActivity.this, "Submit---提交", Toast.LENGTH_SHORT).show();

                searchText = query;
                finish();
                Intent intent = new Intent(searchActivity.this, searchActivity.class);
             //   Toast.makeText(searchActivity.this,"sear"+searchText,Toast.LENGTH_SHORT).show();
                intent.putExtra("searchText", searchText);
                intent.putExtra("userId",_idnumber);
                startActivity(intent);
                searchActivity.this.overridePendingTransition(0,0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {//搜索框内容变化时

                return true;
            }
        });


        adapter = new MyPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        mradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_rm:
                    //    isHpFragment = true;
                        selectPage(0);
                        break;
                    case R.id.rb_yh:
                    //    isHpFragment = false;
                        //viewPager.setCurrentItem(1);
                        selectPage(1);
                        break;
                }
            }
        });
        //滑动也能切换button的图片
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }
            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        rb_rm.setChecked(true);
                        break;
                    case 1:
                        rb_yh.setChecked(true);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

    }

    @Override
    protected void onPause() {
        overridePendingTransition(0,0);
        super.onPause();
    }

    private void initView(){

        mradiogroup = (RadioGroup) findViewById(R.id.search_rg);
        viewPager = (ViewPager)findViewById(R.id.viewpager_s);
        MyPagerAdapter tabPageAdapter = new MyPagerAdapter(
                getSupportFragmentManager(), fragmentList);
        rb_rm = (RadioButton) findViewById(R.id.rb_rm);
        rb_yh = (RadioButton) findViewById(R.id.rb_yh);

    }
    /**
     * 初始化监听
     */
    private  void  initEvent(){

    }
    /**
     * 初始化Fragment
     */
    private void initFragment() {
        manager = getSupportFragmentManager();

        fragment_rm = new rmFragment();

        fragment_yh = new yhFragment();

        fragmentList.add(fragment_rm);
        fragmentList.add(fragment_yh);
    }


    private void selectPage(int position) {
        // 将所有的tab的icon变成灰色的
        for (int i = 0; i < mradiogroup.getChildCount(); i++) {
            Drawable gray = getResources().getDrawable(unselectedIconIds[i]);
            // 不能少，少了不会显示图片
            gray.setBounds(0, 0, 540,
                    120);
            RadioButton child = (RadioButton)mradiogroup.getChildAt(i);
            child.setCompoundDrawables(null, gray, null, null);
//            child.setTextColor(getResources().getColor(
//                    R.color.dark_gray));
        }
        // 切换页面
        viewPager.setCurrentItem(position);
        // 改变图标
        Drawable yellow = getResources().getDrawable(selectedIconIds[position]);
        yellow.setBounds(0, 0, 540,
                120);
        RadioButton select = (RadioButton) mradiogroup.getChildAt(position);
        select.setCompoundDrawables(null, yellow, null, null);
//        select.setTextColor(getResources().getColor(
//                R.color.));
    }

    @Override
    public void onClick(View v) {

    }

    //传递数据 userId

    public String get_idnumber() {
        return _idnumber;
    }

    public String get_name() {
        return _name;
    }

    public String get_headimg() {
        return _imghead;
    }

    public String getFan() {
        return fan;
    }

    public String getFollow() {
        return follow;
    }

    public String getSearchText() { return searchText; }

    private void addHistory(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // 声明客户端
                    OkHttpClient client = new OkHttpClient();
                    RequestBody requestBody = new FormBody.Builder()
                            .add("searchText",searchText)
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

                }
                catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        }).start();
    }

}
