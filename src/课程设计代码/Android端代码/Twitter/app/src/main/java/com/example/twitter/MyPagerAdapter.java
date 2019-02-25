package com.example.twitter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by hasee on 2018/12/18.
 */

public class MyPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> mfragmentList;

    public MyPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.mfragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mfragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mfragmentList.size();
    }
    /**
     * 重写，不让Fragment销毁
     */
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {

    }
}
