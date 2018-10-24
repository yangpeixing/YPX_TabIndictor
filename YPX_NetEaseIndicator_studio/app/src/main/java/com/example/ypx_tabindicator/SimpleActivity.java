package com.example.ypx_tabindicator;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nineoldandroids.view.ViewHelper;
import com.ypx.tablayout.YPXTabLayout;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 作者：yangpeixing on 2018/7/4 17:02
 * 功能：
 * 产权：南京婚尚信息技术
 */
public class SimpleActivity extends AppCompatActivity {
    FragmentPagerAdapter mAdapter;
    List<String> tabList;
    List<String> selectList;
    private List<SimpleFragment> mTabContents = new ArrayList<>();
    private ViewPager mViewPager;
    private RelativeLayout rl_indicator;
    private String[] mDatas, allDatas;
    private YPXTabLayout indicator;
    private ImageView iv_add;
    private ImageButton ibt_add;
    private int style = 0;
    private GridLayout grd_tabSetting;
    private View v_mask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple);
        initView();
        initAdapter();
        initListener();
        setTabData();
    }

    private void initView() {
        allDatas = new String[]{"全局设置", "两字", "三个字", "四个字了", "五个字行么", "感谢您",
                "使用本Demo", "学习", "该指示器控件", "如果你", "觉得", "效果很赞", "还请去", "Github上", "点个赞"};

        mDatas = allDatas;
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        rl_indicator = (RelativeLayout) findViewById(R.id.rl_indicator);
        indicator = (YPXTabLayout) findViewById(R.id.indicator);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        grd_tabSetting = (GridLayout) findViewById(R.id.grd_tabSetting);
        v_mask = findViewById(R.id.v_mask);
        ibt_add = (ImageButton) findViewById(R.id.ibt_add);
    }

    private void initListener() {
        ibt_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (grd_tabSetting.getVisibility() == View.VISIBLE) {
                    grd_tabSetting.setVisibility(View.GONE);
                    v_mask.setVisibility(View.GONE);
                    grd_tabSetting.setAnimation(AnimationUtils.loadAnimation(SimpleActivity.this, R.anim.dd_menu_out));
                    ViewHelper.setRotation(iv_add, 0);
                } else {
                    v_mask.setVisibility(View.VISIBLE);
                    grd_tabSetting.setVisibility(View.VISIBLE);
                    grd_tabSetting.setAnimation(AnimationUtils.loadAnimation(SimpleActivity.this, R.anim.dd_menu_in));
                    ViewHelper.setRotation(iv_add, 180);
                }
            }
        });

        v_mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ibt_add.performClick();
            }
        });
    }

    private void initAdapter() {
        mTabContents.clear();
        for (String data : mDatas) {
            mTabContents.add(SimpleFragment.newInstance(data));
        }
        if (mAdapter == null) {
            mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
                @Override
                public int getCount() {
                    return mTabContents.size();
                }

                @Override
                public Fragment getItem(int position) {
                    return mTabContents.get(position);
                }

                @Override
                public int getItemPosition(Object object) {
                    return PagerAdapter.POSITION_NONE;
                }

                @Override
                public Object instantiateItem(ViewGroup container, int position) {
                    SimpleFragment fragment = (SimpleFragment) super.instantiateItem(container, position);
                    fragment.refreshData(mDatas[position]);
                    return fragment;

                }
            };
            mViewPager.setAdapter(mAdapter);
            indicator.setViewPager(mViewPager, 0);
        } else {
            mAdapter.notifyDataSetChanged();
        }
          indicator.setTitles(mDatas);
    }

    public void setHs_indicatorHeight(int height) {
//        ViewGroup.LayoutParams params = hs_indicator.getLayoutParams();
//        params.height = height;
//        hs_indicator.setLayoutParams(params);
    }

    public void setmDatas(String[] mDatas) {
        this.mDatas = mDatas;
        initAdapter();
    }


    public void hideTabSettingIcon(boolean isHide) {
        if (isHide) {
            iv_add.setVisibility(View.GONE);
            ibt_add.setVisibility(View.GONE);
        } else {
            iv_add.setVisibility(View.VISIBLE);
            ibt_add.setVisibility(View.VISIBLE);
        }
    }

    public void setTabBackgroundColor(int color) {
        rl_indicator.setBackgroundColor(color);
        iv_add.setColorFilter(indicator.getTabTextColor());
    }

    public YPXTabLayout getIndicator() {
        return indicator;
    }

    public int getStyle() {
        return style;
    }

    public void setStyle(int style) {
        this.style = style;
    }

    private void setTabData() {
        tabList = new ArrayList<>();
        selectList = new ArrayList<>();
        selectList.add(mDatas[0]);
        selectList.add(mDatas[1]);
        selectList.add(mDatas[2]);
        Collections.addAll(tabList, allDatas);
        for (final String s : tabList) {
            final TextView textView = new TextView(this);
            textView.setText(s);
            textView.setPadding(dp(10), dp(10), dp(10), dp(10));
            textView.setCompoundDrawablePadding(dp(3));
            textView.setGravity(Gravity.CENTER);
            if (s.length() > 6) {
                textView.setTextSize(10);
            } else if (s.length() > 4) {
                textView.setTextSize(12);
            } else {
                textView.setTextSize(14);
            }
            if (selectList.contains(s)) {
                textView.setSelected(true);
                textView.setBackground(getResources().getDrawable(R.drawable.a_border_gray_line2));
                textView.setTextColor(Color.RED);
            } else {
                textView.setSelected(false);
                textView.setTextColor(Color.parseColor("#666666"));
                textView.setBackground(getResources().getDrawable(R.drawable.a_border_gray_line));
            }

            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (s.equals(mDatas[0])) {
                        Toast.makeText(SimpleActivity.this, "该Tab不可操作!", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (textView.isSelected()) {
                        textView.setTextColor(Color.parseColor("#666666"));
                        textView.setBackground(getResources().getDrawable(R.drawable.a_border_gray_line));
                        selectList.remove(s);
                    } else {
                        textView.setBackground(getResources().getDrawable(R.drawable.a_border_gray_line2));
                        textView.setTextColor(Color.RED);
                        selectList.add(s);
                    }
                    textView.setSelected(!textView.isSelected());
                    setmDatas(selectList.toArray(new String[selectList.size()]));
                }
            });
            ViewGroup.MarginLayoutParams params = new ViewGroup.MarginLayoutParams((ScreenUtils.getScreenWidth(this) - dp(50)) / 3, dp(40));
            params.bottomMargin = dp(10);
            params.rightMargin = dp(10);
            textView.setLayoutParams(params);
            grd_tabSetting.addView(textView);
        }
    }

    public int dp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, this.getResources().getDisplayMetrics());
    }
}
