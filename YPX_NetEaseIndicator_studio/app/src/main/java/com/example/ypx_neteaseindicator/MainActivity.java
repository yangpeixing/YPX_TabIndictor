package com.example.ypx_neteaseindicator;

import java.util.ArrayList;
import java.util.List;

import com.example.ypx_neteaseindicator.NetEaseIndicator.DrawIndicatorCreator;
import com.example.ypx_neteaseindicator.NetEaseIndicator.PageChangeListener;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.Window;

public class MainActivity extends FragmentActivity {

	private List<Fragment> mTabContents = new ArrayList<Fragment>();
	private FragmentPagerAdapter mAdapter;
	private ViewPager mViewPager;
	private String[] mDatas ;

	private NetEaseIndicator mIndicator,mIndicator2;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		initView();
		initDatas();
		mViewPager.setAdapter(mAdapter);
		mIndicator2.setViewPager(mViewPager, 0);
		mIndicator.setViewPager(mViewPager, 0);
		style1();
		style2();
		mIndicator.setOnPageChangeListener(new PageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				mIndicator2.onPageSelected(position);
			}
			
			@Override
			public void onPageScrolled(int position, float positionOffset,
					int positionOffsetPixels) {
				mIndicator2.onPageScrolled(position, positionOffset, positionOffsetPixels);
			}
			
			@Override
			public void onPageScrollStateChanged(int state) {
				mIndicator2.onPageScrollStateChanged(state);
			}
		});
		
	}


	protected void style2() {
		mIndicator2.setTitles(mDatas);
		mIndicator2.setDefaultHeight(dp2px(30));//设置默认高度为30dp
		mIndicator2.setTabPadding(dp2px(10), 0, dp2px(10), dp2px(5));//设置tabPadding左右10dp
		mIndicator2.setBackgroundRadius(dp2px(35));//设置外框半径25dp
		mIndicator2.setShowTabSizeChange(true);//显示字体大小切换效果
		mIndicator2.setShowBackground(false);//不显示背景
		mIndicator2.setShowIndicator(true);//显示指示器
		mIndicator2.setDeuceTabWidth(false);//不平分tab宽度，默认为平分
		mIndicator2.setTabTextSize(14);//设置tab默认字体大小
		mIndicator2.setTabMaxTextSize(18);//设置tab变换字体大小，如果setShowTabSizeChange设置false，则按默认字体大小
		mIndicator2.setTabPressColor(Color.RED);//设置tab选中后的字体颜色
		mIndicator2.setTabTextColor(Color.parseColor("#666666"));//设置未选中时字体颜色
		mIndicator2.setIndicatorColor(Color.RED);//设置指示器颜色为红色
		mIndicator2.setmBackgroundColor(Color.RED);//设置背景颜色为红色，如果setShowBackground为false则无背景
		mIndicator2.setBackgroundLineColor(Color.RED);//设置背景框颜色，如果setShowBackground为false则无背景框颜色
		mIndicator2.setBackgroundStrokeWidth(dp2px(1));//设置背景框宽度\
		//mIndicator2.setTabWidth(ScreenUtils.getScreenWidth(this)/4);//强制tab宽度
		mIndicator2.setDrawIndicatorCreator(new DrawIndicatorCreator() {

			@Override
			public void drawIndicator(Canvas canvas, int left, int top,
					int right, int bottom, Paint paint, int raduis) {
				//设置下滑线条
				RectF oval = new RectF(left, bottom - dp2px(2), right, bottom);
				canvas.drawRect(oval, paint);
			}
		});
	}

	protected void style1() {
		mIndicator.setTitles(new String[]{mDatas[0],mDatas[1],mDatas[2]});
		mIndicator.setDefaultHeight(dp2px(30));
		mIndicator.setTabPadding(dp2px(15), 0, dp2px(15), 0);
		mIndicator.setDeuceTabWidth(false);
		mIndicator.setBackgroundRadius(dp2px(30));
		mIndicator.setBackgroundStrokeWidth(dp2px(1));
	}

	private void initDatas() {
		mDatas = new String[] { "问吧", "热点区", "关注", "问吧吧", "话题话题", "关注", "问吧",
				"话题关注者", "关注", "移动互联网", "话题", "关注", "爱分享", "热点区", "国际新闻", "国内" };
		mTabContents.clear();
		for (String data : mDatas) {
			VpSimpleFragment fragment = VpSimpleFragment.newInstance(data);
			mTabContents.add(fragment);
		}

		mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
			@Override
			public int getCount() {
				return mTabContents.size();
			}

			@Override
			public Fragment getItem(int position) {
				return mTabContents.get(position);
			}
		};
	}

	private void initView() {
		mViewPager = (ViewPager) findViewById(R.id.id_vp);
		mIndicator = (NetEaseIndicator) findViewById(R.id.netEaseIndicator1);
		mIndicator2= (NetEaseIndicator) findViewById(R.id.netEaseIndicator2);
	}

	/**
	 * dpתpx
	 * 
	 * @param dpVal
	 * @return
	 */
	public int dp2px(float dpVal) {
		return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
				dpVal, getResources().getDisplayMetrics());
	}
}
