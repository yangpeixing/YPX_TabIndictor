package com.ypx.tablayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

/**
 * 作者：yangpeixing on 2018/7/3 14:55
 * 功能：
 * 产权：南京婚尚信息技术
 */
public class YPXScrollableTabLayout extends HorizontalScrollView {
    YPXTabLayout tabLayout;
    public YPXScrollableTabLayout(Context context) {
        super(context);
    }

    public YPXScrollableTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YPXScrollableTabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private void initView(){
        setOverScrollMode(View.OVER_SCROLL_NEVER);
        setHorizontalScrollBarEnabled(false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(params);
        tabLayout=new YPXTabLayout(getContext());
        addView(tabLayout,new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

    public void setTitles(String[] titles) {
       tabLayout.setTitles(titles);
    }

}
