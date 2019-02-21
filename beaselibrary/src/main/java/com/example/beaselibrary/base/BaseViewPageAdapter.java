package com.example.beaselibrary.base;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by zhangjie on 2017/7/18.
 * viewPager适配器
 */

public class BaseViewPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;

    public BaseViewPageAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        super.destroyItem(container, position, object);
        if (position <= getCount()) {
            FragmentManager manager = ((Fragment) object).getFragmentManager();
            if (null != manager) {
                FragmentTransaction trans = manager.beginTransaction();
                trans.remove((Fragment) object);
                trans.commitAllowingStateLoss();
            }

        }
    }
}
