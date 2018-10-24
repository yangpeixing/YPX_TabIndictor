package com.ypx.tablayout;

import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;

/**
 * 作者：yangpeixing on 2018/6/28 15:11
 * 功能：
 * 产权：南京婚尚信息技术
 */
public class YPXTabManager {
    public static final int Left = -1;
    public static final int Center = -2;

    public static final int Wrap = -1;
    public static final int Longest = -2;
    public static final int ScreenWidth_fit = -3;


    private int childGravity = Left;
    private int tabWidth = Wrap;

    private HorizontalScrollView horizontalScrollView;
    private YPXTabLayout layout;
    private String[] titles;

    private YPXTabManager(YPXTabLayout layout) {
        this.layout = layout;
        horizontalScrollView = new HorizontalScrollView(layout.getContext());
        horizontalScrollView.setOverScrollMode(View.OVER_SCROLL_NEVER);
        horizontalScrollView.setHorizontalScrollBarEnabled(false);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        horizontalScrollView.setLayoutParams(params);
        if (layout.getParent() != null) {
            ViewGroup parent = (ViewGroup) layout.getParent();
            parent.removeView(layout);
            parent.addView(horizontalScrollView);
            horizontalScrollView.addView(layout);
        }
    }

    public static YPXTabManager create(YPXTabLayout layout) {
        return new YPXTabManager(layout);
    }

    public YPXTabManager withTitles(String[] titles) {
        this.titles = titles;
        layout.setTitles(titles);
        return this;
    }

    public YPXTabManager withTabWidth(int tabWidth) {
        this.tabWidth = tabWidth;
        if (tabWidth == Wrap) {
            layout.setDeuceTabWidth(false);
        } else if (tabWidth == Longest) {
            layout.setDeuceTabWidth(true);
        } else if (tabWidth == ScreenWidth_fit) {
            if (ViewCompat.canScrollHorizontally(horizontalScrollView, 1)) {
                return this;
            }
            layout.post(new Runnable() {
                @Override
                public void run() {
                    layout.setTabWidth(horizontalScrollView.getWidth() / titles.length);
                }
            });

        } else {
            layout.setTabWidth(tabWidth);
        }
        return this;
    }

    public YPXTabManager withMargin(int left,int top,int right,int bottom) {

        return this;
    }

    public void build(){

    }


}
