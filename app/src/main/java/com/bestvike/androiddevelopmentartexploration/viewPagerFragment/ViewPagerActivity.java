package com.bestvike.androiddevelopmentartexploration.viewPagerFragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.bestvike.androiddevelopmentartexploration.R;
import com.example.beaselibrary.base.BaseActivity;
import com.example.beaselibrary.base.BaseViewPageAdapter;
import com.example.beaselibrary.util.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * ViewPager的简单实用
 */
public class ViewPagerActivity extends BaseActivity {
    @Override
    protected void destroyPresenter() {

    }

    private ViewPager viewPager;
    private List<Fragment> listFragment ;//存放Fragment列表
    private FragmentManager fm;
    private TextView tvFragment1,tvFragment2,tvFragment3;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewpager);
        findView();
        initFragment();//初始化Fragment
        initViewPager();//初始化viewPager
        changeTitleSts(0);//设置加载第几个页面
    }

    private void findView(){
        tvFragment1 = findViewById(R.id.tvFragment1);
        tvFragment1.setOnClickListener(this);
        tvFragment2 = findViewById(R.id.tvFragment2);
        tvFragment2.setOnClickListener(this);
        tvFragment3 = findViewById(R.id.tvFragment3);
        tvFragment3.setOnClickListener(this);
        viewPager = findViewById(R.id.viewPager);
    }

    private void initFragment(){
        if(null == listFragment){
            listFragment = new ArrayList<>();
        }
        listFragment.add(new FragmentOne());
        listFragment.add(new FragmentTwo());
        listFragment.add(new FragmentThree());
        fm = getSupportFragmentManager();
    }

    private void initViewPager(){
        viewPager.setAdapter(new BaseViewPageAdapter(fm,listFragment));
        viewPager.setOffscreenPageLimit(1);//预加载页数
        viewPager.setCurrentItem(0);//默认加载显示页面
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            /**
             * 页面正在滑动
             * @param position   当前显示的页面
             * @param positionOffset
             * @param positionOffsetPixels
             */
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Logger.e("viewPager","onPageScrolled"+",position:"+position+",positionOffset:"+positionOffset+",positionOffsetPixels:"+positionOffsetPixels);
            }

            /**
             * 滑动完成后调用
             * @param position  当前页面的标识
             */
            @Override
            public void onPageSelected(int position) {
                Logger.e("viewPager","onPageSelected,position:"+position);
                changeTitleSts(position);
            }

            /**
             * @param state ==1的时候表示正在滑动,==2的时候表示滑动完毕了,==0的时候表示什么都没做，就是停在那
             */
            @Override
            public void onPageScrollStateChanged(int state) {
                Logger.e("viewPager","onPageScrollStateChanged,state:"+state);
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.tvFragment1:
                viewPager.setCurrentItem(0);
                break;
            case R.id.tvFragment2:
                viewPager.setCurrentItem(1);
                break;
            case R.id.tvFragment3:
                viewPager.setCurrentItem(2);
                break;
        }
    }

    /**
     *改变顶部导航栏状态
     */
    private void changeTitleSts(int position){
        switch (position){
            case 0:
                tvFragment1.setTextColor(Color.parseColor("#f15a4a"));
                tvFragment2.setTextColor(Color.parseColor("#666666"));
                tvFragment3.setTextColor(Color.parseColor("#666666"));
                break;
            case 1:
                tvFragment1.setTextColor(Color.parseColor("#666666"));
                tvFragment2.setTextColor(Color.parseColor("#f15a4a"));
                tvFragment3.setTextColor(Color.parseColor("#666666"));
                break;
            case 2:
                tvFragment1.setTextColor(Color.parseColor("#666666"));
                tvFragment2.setTextColor(Color.parseColor("#666666"));
                tvFragment3.setTextColor(Color.parseColor("#f15a4a"));
                break;
        }
    }
}
