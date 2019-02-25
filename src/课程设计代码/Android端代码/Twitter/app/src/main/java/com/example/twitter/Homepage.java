package com.example.twitter;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.security.acl.Group;
import java.util.ArrayList;
import java.util.List;


public class Homepage extends AppCompatActivity implements View.OnClickListener{
    private Fragment currentFragment=new Fragment();
    private ViewPager viewPager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private MyPagerAdapter adapter;
    private LinearLayout layout;
    private RadioButton rb_hp,rb_bl,rb_fd, rb_msg;
    private RadioGroup mradiogroup;
    private Fragment fragment_hp,fragment_bl,fragment_fd, fragment_msg;
    private FragmentManager manager;
    private boolean isHpFragment = true;//跳转到这个页面的时候先显示的就是hpFragment
    public static final int EXTERNAL_STORAGE_REQ_CODE = 10 ;

    String _idnumber;
    String _name;
    String _imghead;
    String fan;
    String follow;
    /**
     * 按钮的没选中显示的图标
     */
    private int[] unselectedIconIds = { R.drawable.w1,
            R.drawable.w2, R.drawable.w3,
            R.drawable.w4 };
    /**
     * 按钮的选中显示的图标
     */
    private int[] selectedIconIds = { R.drawable.b1,
            R.drawable.b2, R.drawable.b3,
            R.drawable.b4 };
    TextView result;
    android.support.v7.app.ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);


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
        //initEvent();

        adapter = new MyPagerAdapter(getSupportFragmentManager(),fragmentList);
        viewPager.setAdapter(adapter);
        mradiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_hp:
                        isHpFragment = true;
                            //viewPager.setCurrentItem(0);
                        selectPage(0);
                        break;
                    case R.id.rb_fd:
                        isHpFragment = false;
                        //viewPager.setCurrentItem(1);
                        selectPage(1);
                        break;
                    case R.id.rb_bl:
                        isHpFragment = false;
                        //viewPager.setCurrentItem(2);
                        selectPage(2);
                        break;
                    case R.id.rb_msg:
                        isHpFragment = false;
                        //viewPager.setCurrentItem(3);
                        selectPage(3);
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
                        rb_hp.setChecked(true);
                        break;
                    case 1:
                        rb_fd.setChecked(true);
                        break;
                    case 2:
                        rb_bl.setChecked(true);
                        break;
                    case 3:
                        rb_msg.setChecked(true);
                        break;

                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        ActivityCollector.addActivtiy(this);
        _idnumber = getIntent().getStringExtra("_idnumber");
        _name = getIntent().getStringExtra("_name");
        _imghead = getIntent().getStringExtra("_imghead");
        fan = getIntent().getStringExtra("fan");
        follow = getIntent().getStringExtra("follow");
    }
//    private void bindID() {
//        viewPager = (ViewPager)findViewById(R.id.viewpager_a);
//    }
    private void initView(){

        mradiogroup = (RadioGroup) findViewById(R.id.rg);
        viewPager = (ViewPager)findViewById(R.id.viewpager_a);
        MyPagerAdapter tabPageAdapter = new MyPagerAdapter(
                getSupportFragmentManager(), fragmentList);
        rb_hp = (RadioButton) findViewById(R.id.rb_hp);
        rb_fd = (RadioButton) findViewById(R.id.rb_fd);
        rb_bl = (RadioButton) findViewById(R.id.rb_bl);
        rb_msg = (RadioButton) findViewById(R.id.rb_msg);
    }


    /**
     * 初始化监听
     */
    private  void  initEvent(){
//        rb_hp.setOnClickListener(this);
//        rb_fd.setOnClickListener(this);
//        rb_bl.setOnClickListener(this);
//        rb_msg.setOnClickListener(this);
        //mradiogroup.setOnCheckedChangeListener();
    }
    /**
     * 初始化Fragment
     */
    private void initFragment() {
        manager = getSupportFragmentManager();

        fragment_hp = new hpFragment();

        fragment_bl = new bellFragment();

        fragment_fd = new fdFragment();

        fragment_msg = new msgFragment();

        fragmentList.add(fragment_hp);
        fragmentList.add(fragment_fd);
        fragmentList.add(fragment_bl);
        fragmentList.add(fragment_msg);
    }

    /**
     * 展示Fragment
     */
//    private void showFragment(Fragment fragment) {
//        if (currentFragment!=fragment) {
//            FragmentTransaction transaction = manager.beginTransaction();
//            transaction.hide(currentFragment);
//            currentFragment = fragment;
//            if (!fragment.isAdded()) {
//                transaction.add(R.id.layout_main_fragment, fragment).show(fragment).commit();
//            } else {
//                transaction.show(fragment).commit();
//            }
//        }


//        FragmentTransaction transaction = manager.beginTransaction();
//        transaction.replace(R.id.layout_main_fragment,fragment);
//        //transaction.addToBackStack(null);
//        transaction.commit();

//    }
    private void selectPage(int position) {
        // 将所有的tab的icon变成灰色的
        for (int i = 0; i < mradiogroup.getChildCount(); i++) {
            Drawable gray = getResources().getDrawable(unselectedIconIds[i]);
            // 不能少，少了不会显示图片
            gray.setBounds(0, 0, 260,
                    200);
            RadioButton child = (RadioButton)mradiogroup.getChildAt(i);
            child.setCompoundDrawables(null, gray, null, null);
//            child.setTextColor(getResources().getColor(
//                    R.color.dark_gray));
        }
        // 切换页面
        viewPager.setCurrentItem(position);
        // 改变图标
        Drawable yellow = getResources().getDrawable(selectedIconIds[position]);
        yellow.setBounds(0, 0, 260,
                200);
        RadioButton select = (RadioButton) mradiogroup.getChildAt(position);
        select.setCompoundDrawables(null, yellow, null, null);
//        select.setTextColor(getResources().getColor(
//                R.color.));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK ){
            if(isHpFragment == true)
                ActivityCollector.finishAll();
            else{
                isHpFragment = true;
                viewPager.setCurrentItem(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
    @Override
    public void onClick(View v) {
//        switch (v.getId()) {
//            case R.id.rb_hp:
//                isHpFragment = true;
//                showFragment(fragment_hp);
//                break;
//            case R.id.rb_fd:
//                isHpFragment = false;
//                showFragment(fragment_fd);
//                break;
//            case R.id.rb_bl:
//                isHpFragment = false;
//                showFragment(fragment_bl);
//                break;
//            case R.id.rb_msg:
//                isHpFragment = false;
//                showFragment(fragment_msg);
//                break;
//        }
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

}
