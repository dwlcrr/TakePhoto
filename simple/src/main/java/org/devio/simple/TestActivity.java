package org.devio.simple;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.NestedScrollView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 *
 */
public class TestActivity extends Activity {

    private ObserveScrollView svLeftTitle, svRightTitle, scrollViewLeft, scrollViewRight;
    private boolean isLeft, isRight, isleftTitle, isRightTitle;
    private List<String> leftList = new ArrayList<>();
    private List<String> rightList = new ArrayList<>();
    private List<String> middleList = new ArrayList<>();
    private LinearLayout ll_qiQuanLeft, ll_qiQuanRight, ll_xingQuan;
    private int currentPos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        View contentView = LayoutInflater.from(this).inflate(R.layout.test, null);
        setContentView(contentView);
        addListener();
        addData();
    }

    private void addData() {
        for (int i = 0; i < 16; i++) {
            leftList.add("aaaaa");
        }
        for (int i = 0; i < 16; i++) {
            rightList.add("bbbbb");
        }
        for (int i = 0; i < 16; i++) {
            middleList.add("bbbbb");
        }
        for (int i = 0; i < leftList.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_qiquan_left, null);
            TextView tv_openingPrice = view.findViewById(R.id.tv_openingPrice);
            TextView tv_shSellPrice = view.findViewById(R.id.tv_shSellPrice);
            TextView tv_shBuyPrice = view.findViewById(R.id.tv_shBuyPrice);
            TextView tv_inventory = view.findViewById(R.id.tv_inventory);
            TextView tv_tradingVolume = view.findViewById(R.id.tv_tradingVolume);
            TextView tv_upsAndDowns = view.findViewById(R.id.tv_upsAndDowns);
            TextView tv_latestPrice = view.findViewById(R.id.tv_latestPrice);
            tv_openingPrice.setText(leftList.get(i));
            tv_shSellPrice.setText(leftList.get(i));
            tv_shBuyPrice.setText(leftList.get(i));
            tv_inventory.setText(leftList.get(i));
            tv_tradingVolume.setText(leftList.get(i));
            tv_upsAndDowns.setText(leftList.get(i));
            tv_latestPrice.setText(leftList.get(i));
            ll_qiQuanLeft.addView(view);
        }
        for (int i = 0; i < rightList.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.item_qiquan_right, null);
            TextView tv_openingPrice = view.findViewById(R.id.tv_openingPrice);
            TextView tv_shSellPrice = view.findViewById(R.id.tv_shSellPrice);
            TextView tv_shBuyPrice = view.findViewById(R.id.tv_shBuyPrice);
            TextView tv_inventory = view.findViewById(R.id.tv_inventory);
            TextView tv_tradingVolume = view.findViewById(R.id.tv_tradingVolume);
            TextView tv_upsAndDowns = view.findViewById(R.id.tv_upsAndDowns);
            TextView tv_latestPrice = view.findViewById(R.id.tv_latestPrice);
            tv_openingPrice.setText(rightList.get(i));
            tv_shSellPrice.setText(rightList.get(i));
            tv_shBuyPrice.setText(rightList.get(i));
            tv_inventory.setText(rightList.get(i));
            tv_tradingVolume.setText(rightList.get(i));
            tv_upsAndDowns.setText(rightList.get(i));
            tv_latestPrice.setText(rightList.get(i));
            ll_qiQuanRight.addView(view);
        }
        for (int i = 0; i < middleList.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.simple_item, null);
            TextView textView = view.findViewById(R.id.simple_text);
            textView.setText(middleList.get(i));
            ll_xingQuan.addView(view);
        }
    }

    private void addListener() {
        svLeftTitle = findViewById(R.id.svLeftTitle);
        svRightTitle = findViewById(R.id.svRightTitle);
        scrollViewLeft = findViewById(R.id.scrollViewLeft);
        scrollViewRight = findViewById(R.id.scrollViewRight);
        ll_qiQuanLeft = findViewById(R.id.ll_qiQuanLeft);
        ll_qiQuanRight = findViewById(R.id.ll_qiQuanRight);
        ll_xingQuan = findViewById(R.id.ll_xingQuan);

        svLeftTitle.post(() -> svLeftTitle.fullScroll(View.FOCUS_RIGHT));
        svRightTitle.post(() -> svRightTitle.fullScroll(View.FOCUS_LEFT));
        scrollViewLeft.post(() -> scrollViewLeft.fullScroll(View.FOCUS_RIGHT));
        scrollViewRight.post(() -> scrollViewRight.fullScroll(View.FOCUS_LEFT));

        svLeftTitle.setScrollListener(new ObserveScrollView.ScrollListener() {
            @Override
            public void scrollOritention(HorizontalScrollView scrollView, int x, int y, int oldX, int oldY) {
                if (currentPos != 1) return;
                Log.i("svTag", "svLeftTitle");
                scrollViewLeft.scrollTo(x, y);
                svRightTitle.scrollTo((int) svRightTitle.getX() - x + oldX, (int) svRightTitle.getY() - y + oldY);
                scrollViewRight.scrollTo((int) scrollViewRight.getX() - x + oldX, (int) scrollViewRight.getY() - y + oldY);
            }

            @Override
            public void onScrolled(boolean isScrolled) {
                if (isScrolled) {
                    currentPos = 1;
                    scrollViewLeft.fling(0);
                    svRightTitle.fling(0);
                    scrollViewRight.fling(0);
                }
            }
        });
        svRightTitle.setScrollListener(new ObserveScrollView.ScrollListener() {
            @Override
            public void scrollOritention(HorizontalScrollView scrollView, int x, int y, int oldX, int oldY) {
                if (currentPos != 2) return;
                scrollViewRight.scrollTo(x, y);
                svLeftTitle.scrollTo((int) svLeftTitle.getX() - x + oldX, (int) svLeftTitle.getY() - y + oldY);
                scrollViewLeft.scrollTo((int) scrollViewLeft.getX() - x + oldX, (int) scrollViewLeft.getY() - y + oldY);
            }

            @Override
            public void onScrolled(boolean isScrolled) {
                if (isScrolled) {
                    currentPos = 2;
                    scrollViewRight.fling(0);
                    svLeftTitle.fling(0);
                    scrollViewLeft.fling(0);
                }
            }
        });
        scrollViewLeft.setScrollListener(new ObserveScrollView.ScrollListener() {
            @Override
            public void scrollOritention(HorizontalScrollView scrollView, int x, int y, int oldX, int oldY) {
                if (currentPos != 3) return;
                svLeftTitle.scrollTo(x, y);
                svRightTitle.scrollTo((int) svRightTitle.getX() + oldX - x, (int) svRightTitle.getY() - y + oldY);
                scrollViewRight.scrollTo((int) scrollViewRight.getX() + oldX - x, (int) scrollViewRight.getY() - y + oldY);
            }

            @Override
            public void onScrolled(boolean isScrolled) {
                if (isScrolled) {
                    currentPos = 3;
                    svLeftTitle.fling(0);
                    svRightTitle.fling(0);
                    scrollViewRight.fling(0);
                }
            }
        });
        scrollViewRight.setScrollListener(new ObserveScrollView.ScrollListener() {
            @Override
            public void scrollOritention(HorizontalScrollView scrollView, int x, int y, int oldX, int oldY) {
                if (currentPos != 4) return;
                svRightTitle.scrollTo(x, y);
                svLeftTitle.scrollTo((int) svLeftTitle.getScrollX() + x - oldX, (int) svLeftTitle.getScaleY() + y - oldY);
                scrollViewLeft.scrollTo((int) scrollViewLeft.getScrollX() + x - oldX, (int) scrollViewLeft.getScaleY() + y - oldY);
            }

            @Override
            public void onScrolled(boolean isScrolled) {
                if (isScrolled) {
                    currentPos = 4;
                    svRightTitle.fling(0);
                    svLeftTitle.fling(0);
                    scrollViewLeft.fling(0);
                }
            }
        });

    }
}
