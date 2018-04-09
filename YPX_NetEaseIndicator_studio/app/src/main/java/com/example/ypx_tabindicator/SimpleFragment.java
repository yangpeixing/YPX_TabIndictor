package com.example.ypx_tabindicator;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.ypx.tablayout.YPXTabLayout;

public class SimpleFragment extends Fragment implements View.OnClickListener, CompoundButton.OnCheckedChangeListener, SeekBar.OnSeekBarChangeListener {
    public static final String BUNDLE_TITLE = "title";
    View view;
    AppCompatSeekBar sb_press, sb_normal, sb_padding, sb_holePadding;
    TextView tv_pressTextSize, tv_normalTextSize, tv_paddingSize, tv_holePadding;
    LinearLayout ll_tabPressColor, ll_tabNormalColor, ll_tabIndicatorColor, ll_tabBackgroundColor;
    Button bt_style, bt_tabWidth;
    AppCompatCheckBox cb_isShowIndicator, cb_gravity, cb_tabSetting, cb_isShowDivider;
    LinearLayout ll_tabWidth;
    int normalSize = 14;
    YPXTabLayout indicator;
    MainActivity mainActivity;
    private String mTitle = "title";

    public static SimpleFragment newInstance(String title) {
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_TITLE, title);
        SimpleFragment fragment = new SimpleFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if (arguments != null) {
            mTitle = arguments.getString(BUNDLE_TITLE);
        }
        mainActivity = (MainActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mTitle != null && mTitle.equals("全局设置")) {
            if (view == null) {
                view = inflater.inflate(R.layout.a_fragment_vp, container, false);
                sb_press = (AppCompatSeekBar) view.findViewById(R.id.sb_press);
                sb_normal = (AppCompatSeekBar) view.findViewById(R.id.sb_normal);
                sb_padding = (AppCompatSeekBar) view.findViewById(R.id.sb_padding);
                sb_holePadding = (AppCompatSeekBar) view.findViewById(R.id.sb_holePadding);
                tv_normalTextSize = (TextView) view.findViewById(R.id.tv_normalTextSize);
                tv_pressTextSize = (TextView) view.findViewById(R.id.tv_pressTextSize);
                tv_paddingSize = (TextView) view.findViewById(R.id.tv_paddingSize);
                tv_holePadding = (TextView) view.findViewById(R.id.tv_holePadding);
                ll_tabNormalColor = (LinearLayout) view.findViewById(R.id.ll_tabNormalColor);
                ll_tabPressColor = (LinearLayout) view.findViewById(R.id.ll_tabPressColor);
                ll_tabIndicatorColor = (LinearLayout) view.findViewById(R.id.ll_tabIndictorColor);
                ll_tabBackgroundColor = (LinearLayout) view.findViewById(R.id.ll_tabBackgroundColor);
                bt_tabWidth = (Button) view.findViewById(R.id.bt_tabWidth);
                cb_isShowDivider = (AppCompatCheckBox) view.findViewById(R.id.cb_isShowDivider);
                cb_isShowIndicator = (AppCompatCheckBox) view.findViewById(R.id.cb_isShowIndictor);
                cb_gravity = (AppCompatCheckBox) view.findViewById(R.id.cb_gravity);
                cb_tabSetting = (AppCompatCheckBox) view.findViewById(R.id.cb_tabSetting);
                bt_style = (Button) view.findViewById(R.id.bt_style);
                ll_tabWidth = (LinearLayout) view.findViewById(R.id.ll_tabWidth);
                indicator = mainActivity.getIndicator();
                initListener();
                refreshUI();
            }
            return view;
        } else {
            TextView tv = new TextView(getActivity());
            tv.setBackgroundColor(Color.WHITE);
            tv.setText(mTitle);
            tv.setGravity(Gravity.CENTER);
            return tv;
        }
    }

    public void refreshData(String title) {
        mTitle = title;
    }

    public void refreshUI() {
        if (indicator == null || view == null) {
            return;
        }
        int normalSize = indicator.getTabTextSize();
        int pressSize = indicator.getMaxTabTextSize();
        sb_normal.setProgress((normalSize - 10) * 10);
        sb_press.setProgress((pressSize - normalSize) * 10);
        
        cb_isShowIndicator.setChecked(indicator.isShowIndicator());
        ((ImageView) ll_tabNormalColor.getChildAt(0)).setColorFilter(indicator.getTabTextColor());
        ((ImageView) ll_tabPressColor.getChildAt(0)).setColorFilter(indicator.getTabPressColor());
        ((ImageView) ll_tabIndicatorColor.getChildAt(0)).setColorFilter(indicator.getIndicatorColor());
        ((ImageView) ll_tabBackgroundColor.getChildAt(0)).setColorFilter(indicator.getmBackgroundColor());
        mainActivity.setTabBackgroundColor(indicator.getmBackgroundColor());
    }

    public void initListener() {
        ll_tabPressColor.setOnClickListener(this);
        ll_tabNormalColor.setOnClickListener(this);
        ll_tabIndicatorColor.setOnClickListener(this);
        ll_tabBackgroundColor.setOnClickListener(this);
        bt_tabWidth.setOnClickListener(this);
        cb_gravity.setOnCheckedChangeListener(this);
        cb_isShowIndicator.setOnCheckedChangeListener(this);
        cb_tabSetting.setOnCheckedChangeListener(this);
        cb_isShowDivider.setOnCheckedChangeListener(this);
        sb_normal.setOnSeekBarChangeListener(this);
        sb_press.setOnSeekBarChangeListener(this);
        sb_padding.setOnSeekBarChangeListener(this);
        sb_holePadding.setOnSeekBarChangeListener(this);
        bt_style.setOnClickListener(this);
    }

    @Override
    public void onClick(final View v) {
        if (v == bt_style) {
            showStyleDialog();
            return;
        }

        if (v == bt_tabWidth) {
            showTabWidthDialog();
            return;
        }

        new ColorPickerDialog(getActivity(), indicator.getIndicatorColor(), "标题",
                new ColorPickerDialog.OnColorChangedListener() {
                    @Override
                    public void colorChanged(int color) {
                        if (v == ll_tabNormalColor) {
                            indicator.setTabTextColor(color);
                        } else if (v == ll_tabPressColor) {
                            indicator.setTabPressColor(color);
                        } else if (v == ll_tabIndicatorColor) {
                            indicator.setIndicatorColor(color);
                        } else if (v == ll_tabBackgroundColor) {
                            indicator.setmBackgroundColor(color);
                        }
                        refreshUI();
                    }
                }).show();
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (buttonView == cb_isShowIndicator) {
            indicator.setShowIndicator(isChecked);
        } else if (buttonView == cb_gravity) {
            indicator.setTabLayoutGravity(isChecked ? Gravity.CENTER : Gravity.START | Gravity.CENTER);
        } else if (buttonView == cb_tabSetting) {
            mainActivity.hideTabSettingIcon(!isChecked);
        } else if (buttonView == cb_isShowDivider) {
            indicator.setShowTabDivider(isChecked, 0, 0);
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (seekBar == sb_normal) {
            int textSize = 10 + 10 * progress / 100;
            tv_normalTextSize.setText(String.format("%dsp", textSize));
            tv_pressTextSize.setText(String.format("%dsp", textSize));
            sb_press.setProgress(0);
            indicator.setTabMaxTextSize(textSize);
            indicator.setTabTextSize(textSize);
            normalSize = textSize;
        } else if (seekBar == sb_press) {
            int textSize = normalSize + 10 * progress / 100;
            tv_pressTextSize.setText(String.format("%dsp", textSize));
            indicator.setTabMaxTextSize(textSize);
        } else if (seekBar == sb_padding) {
            int padding = 60 * progress / 100;
            tv_paddingSize.setText(String.format("%ddp", padding));
            indicator.setTabPadding(dp(padding), 0, dp(padding), 0);
        } else if (seekBar == sb_holePadding) {
            int margin = 60 * progress / 100;
            tv_holePadding.setText(String.format("%ddp", margin));
            indicator.setTabLeftAndRightMargin(dp(margin), dp(margin));
        }

    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }

    private void selectIndicatorStyle(int checkedId) {
        mainActivity.setStyle(checkedId);
        switch (checkedId) {
            case 0:
                mainActivity.setTabBackgroundColor(Color.WHITE);
                mainActivity.setHs_indicatorHeight(dp(44));
                indicator.setmBackgroundColor(Color.WHITE);
                indicator.setTabPressColor(Color.RED);
                indicator.setIndicatorColor(Color.RED);
                indicator.setTabTextColor(Color.parseColor("#666666"));
                indicator.setDefaultHeight(dp(44));
                indicator.setmBackgroundColor(Color.WHITE);
                indicator.setDrawIndicatorCreator(new YPXTabLayout.DrawIndicatorCreator() {
                    @Override
                    public void drawIndicator(Canvas canvas, int left, int top, int right, int bottom, Paint paint, int raduis) {
                        RectF oval = new RectF(left, bottom - dp(3), right, bottom);
                        canvas.drawRect(oval, paint);
                    }
                });
                break;
            case 1:
                mainActivity.setStyle(1);
                mainActivity.setHs_indicatorHeight(dp(44));
                mainActivity.setTabBackgroundColor(Color.RED);
                indicator.setmBackgroundColor(Color.RED);
                indicator.setTabPressColor(Color.RED);
                indicator.setIndicatorColor(Color.WHITE);
                indicator.setTabTextColor(Color.WHITE);
                indicator.setDefaultHeight(dp(30));
                indicator.setDrawIndicatorCreator(new YPXTabLayout.DrawIndicatorCreator() {
                    @Override
                    public void drawIndicator(Canvas canvas, int left, int top, int right, int bottom, Paint paint, int raduis) {
                        RectF oval2 = new RectF(bottom / 2 + left, top, right - bottom / 2, bottom);
                        canvas.drawCircle(oval2.left, bottom / 2, bottom / 2, paint);
                        canvas.drawRect(oval2, paint);
                        canvas.drawCircle(oval2.right, bottom / 2, bottom / 2, paint);
                    }
                });
                break;
            case 2:
                mainActivity.setHs_indicatorHeight(dp(44));
                indicator.setDefaultHeight(dp(44));
                indicator.setDrawIndicatorCreator(new YPXTabLayout.DrawIndicatorCreator() {
                    @Override
                    public void drawIndicator(Canvas canvas, int left, int top, int right, int bottom, Paint paint, int raduis) {
                        Path mPath = new Path();
                        int mTriangleHeight = (bottom / 3) - dp(5);
                        mPath.moveTo(left / 2 - dp(50), bottom);
                        mPath.lineTo(left / 2 + dp(50), bottom);
                        mPath.lineTo(left / 2, -mTriangleHeight);
                        mPath.close();
                        canvas.save();
                        // 画笔平移到正确的位置
                        canvas.translate(left / 2 + (right - left) / 2, bottom + 1);
                        canvas.drawPath(mPath, paint);
                        canvas.restore();
                    }
                });

                break;
            case 3:
                break;
        }
        refreshUI();
        indicator.refreshCurrentTab();
    }


    private void showStyleDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] items = getResources().getStringArray(R.array.styleArray);
        builder.setSingleChoiceItems(items, mainActivity.getStyle(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                selectIndicatorStyle(which);
                bt_style.setText(items[which]);
                dialog.dismiss();
            }
        });
        builder.show();
    }

    private void showTabWidthDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        final String[] items = getResources().getStringArray(R.array.tabWidthArray);
        builder.setSingleChoiceItems(items, mainActivity.getStyle(), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                bt_tabWidth.setText(items[which]);
                dialog.dismiss();
                if (which == 4) {
                    ll_tabWidth.setVisibility(View.VISIBLE);
                } else {
                    ll_tabWidth.setVisibility(View.GONE);
                }
            }
        });
        builder.show();
    }


    public int dp(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, this.getResources().getDisplayMetrics());
    }
}