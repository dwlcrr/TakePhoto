package org.devio.simple;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

public class ObserveScrollView extends HorizontalScrollView {

    private ScrollListener mListener;

    public interface ScrollListener {//声明接口，用于传递数据

        void scrollOritention(HorizontalScrollView scrollView, int x, int y, int oldX, int oldY);

        void onScrolled(boolean isScrolled);
    }

    public ObserveScrollView(Context context, AttributeSet attrs,
                             int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
    }

    public ObserveScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
    }

    public ObserveScrollView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        // TODO Auto-generated method stub
        super.onScrollChanged(l, t, oldl, oldt);
        if (mListener != null) {
            mListener.scrollOritention(this, l, t, oldl, oldt);
        }
    }
    //工作上 努力 努力 努力。
    public void setScrollListener(ScrollListener l) {
        this.mListener = l;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                if (mListener != null) {
                    mListener.onScrolled(true);
                }
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
                if (mListener != null) {
                    mListener.onScrolled(false);
                }
                break;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void fling(int x){
        super.fling(x);
    }

    @Override
    public void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(true, l, t, r, b);
        int tag = Integer.parseInt((String) this.getTag());
        if (tag % 2 == 1) {
            fullScroll(FOCUS_RIGHT);
        }
    }

}
