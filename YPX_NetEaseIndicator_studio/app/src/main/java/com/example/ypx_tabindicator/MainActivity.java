package com.example.ypx_tabindicator;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;

import com.ypx.tablayout.YPXTabLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    FragmentPagerAdapter mAdapter;
    private List<SimpleFragment2> mTabContents = new ArrayList<>();
    private ViewPager mViewPager;
    private String[] mDatas, allDatas;
    private YPXTabLayout indicator, indicator2, indicator3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initAdapter();
    }

    private void initView() {
        allDatas = new String[]{"全部", "成交", "有效"};
        mDatas = new String[]{"感谢您", "使用", "YPXTabLayout", "如果您", "觉得", "该指示器", "效果还行", "还请去", "我的GitHub", "点个赞"};
        mViewPager = (ViewPager) findViewById(R.id.id_vp);
        indicator = (YPXTabLayout) findViewById(R.id.indicator);
        indicator2 = (YPXTabLayout) findViewById(R.id.indicator2);
        indicator3 = (YPXTabLayout) findViewById(R.id.indicator3);


        indicator.setTitles(allDatas);
        indicator.setShowTabSizeChange(false);//关闭大小变换
        indicator.setTabTextColor(Color.parseColor("#333333"));//设置未选控制时期中时颜色
        indicator.setTabPressColor(Color.parseColor("#0078D5"));//设置选中后颜色
        indicator.setTabLayoutGravity(Gravity.CENTER);//设置布局方式为居中
        indicator.setTabWidth((ScreenUtils.getScreenWidth(this) - dp(50)) / 3);//设置tab的宽度
        indicator.setIndicatorColor(Color.parseColor("#0078D5"));//设置指示器颜色
        indicator.setTabTextSize(14);//设置字体默认大小
        indicator.setTabMaxTextSize(16);//设置字体默认大小
        //设置下划线
        indicator.setDrawIndicatorCreator(new YPXTabLayout.DrawIndicatorCreator() {
            @Override
            public void drawIndicator(Canvas canvas, int left, int top, int right, int bottom, Paint paint, int raduis) {
                float offset = indicator.getPositionOffset();
                float x = 4 * offset * (1 - offset);

//                int xleft = left;
//                int xright = right;
                RectF oval = new RectF(left + dp(30), bottom - dp(3), right - dp(30), bottom);
                canvas.drawRect(oval, paint);

//                Log.e("drawIndicator", "getPositionOffset: " + offset + "   x=" + x + "   left="
//                        + left + "   totalLeft=" + (left - indicator.getTabWidth() * x));

//                RectF oval2 = new RectF(bottom / 2    + left, top+dp(5), right - bottom / 2, bottom-dp(5));
//                canvas.drawCircle(oval2.left, bottom / 2, (bottom -dp(10))/ 2, paint);
//                canvas.drawRect(oval2, paint);
//                canvas.drawCircle(oval2.right, bottom / 2, (bottom -dp(10))/ 2, paint);
            }
        });

        int[] numbs = new int[]{16, 9, 6};
        for (int i = 0; i < numbs.length; i++) {
            String text = allDatas[i] + " " + numbs[i];
            SpannableStringBuilder builder = new SpannableStringBuilder(text);
            int start = text.indexOf(numbs[i] + "");
            builder.setSpan(new ForegroundColorSpan(Color.parseColor("#F98474")), start,
                    start + (numbs[i] + "").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            builder.setSpan(new AbsoluteSizeSpan(ScreenUtils.sp2px(this, 14)), start,
                    start + (numbs[i] + "").length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            indicator.getTab(i).setText(builder);
        }


        indicator2.setTitles(mDatas);
    }

    private void initAdapter() {
        mTabContents.clear();
        for (String data : mDatas) {
            mTabContents.add(SimpleFragment2.newInstance(data));
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
                    SimpleFragment2 fragment = (SimpleFragment2) super.instantiateItem(container, position);
                    fragment.refreshData(mDatas[position]);
                    return fragment;

                }
            };
            mViewPager.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

        indicator.setViewPager(mViewPager, 0);
        indicator2.setViewPager(mViewPager, 0);
        indicator3.setViewPager(mViewPager, 0);

        indicator2.setOnPageChangeListener(new YPXTabLayout.PageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.e("onPageSelected", "onPageScrolled: " + position + "  " + positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                Log.e("onPageSelected", "onPageSelected: " + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                Log.e("onPageSelected", "onPageScrollStateChanged: " + state);

            }
        });

//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                indicator.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                indicator2.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                indicator3.onPageScrolled(position, positionOffset, positionOffsetPixels);
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                indicator.onPageSelected(position);
//                indicator2.onPageSelected(position);
//                indicator3.onPageSelected(position);
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                indicator.onPageScrollStateChanged(state);
//                indicator2.onPageScrollStateChanged(state);
//                indicator3.onPageScrollStateChanged(state);
//            }
//        });
    }


    public YPXTabLayout getIndicator() {
        return indicator;
    }

    public int dp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, this.getResources().getDisplayMetrics());
    }
}
