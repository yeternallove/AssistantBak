package com.zucc.zwy1317.myassistant.ui.customview.calendar;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;

import com.zucc.zwy1317.myassistant.util.BusProvider;
import com.zucc.zwy1317.myassistant.util.Events;


/**
 * 顶部星期以下，日程列表以上，可以滑动的日历 View
 */
public class WeekListView extends RecyclerView {

    private boolean mUserScrolling = false;

    private boolean mScrolling = false;


    public WeekListView(Context context) {
        super(context);
    }

    public WeekListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WeekListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * Enable snapping behaviour for this recyclerView\
     * 能够让 recyclerView 滚动折叠
     * @param enabled enable or disable the snapping behaviour
     */
    public void setSnapEnabled(boolean enabled) {
        if (enabled) {
            addOnScrollListener(mScrollListener);
        } else {
            removeOnScrollListener(mScrollListener);
        }
    }

    //recyclerView的滚动监听事件
    private OnScrollListener mScrollListener = new OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
        }

        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            final WeeksAdapter weeksAdapter = (WeeksAdapter) getAdapter();
            switch (newState) {
                case SCROLL_STATE_IDLE://停止滚动
                    //如果是用户手势操作滚动完成后
                    if (mUserScrolling) {
                        //平滑的滚动调整
                        scrollToView(getCenterView());
                        //用于延迟UI操作的方法
                        postDelayed(new Runnable(){
                            @Override
                            public void run() {weeksAdapter.setDragging(false);
                            }
                        }, 700); // Wait for recyclerView to settle
                    }
                    mUserScrolling = false;
                    mScrolling = false;
                    break;
                // 如果滚动是由于手指的操作引起的(仅限手指操作，没有任何操作)
                case SCROLL_STATE_DRAGGING://被迫滚动
                    // 发送CalendarScrolledEvent日历滚动事件
                    BusProvider.getInstance().send(new Events.CalendarScrolledEvent());
                    // 不是自己滚的 用户拖动的
                    if (!mScrolling) {
                        mUserScrolling = true;
                    }
                    //刷新数据
                    weeksAdapter.setDragging(true);
                    break;
                case SCROLL_STATE_SETTLING://正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程,即惯性滑动
                    // 若为自动滚动的状态，则设置Text月份的透明度
                    weeksAdapter.setAlphaSet(true);
                    mScrolling = true;
                    break;
            }
        }
    };

    /**
     * 计算得到一个在滑动时距中心点最近的Item
     * @param y 总高度的1/2
     */
    private View getChildClosestToPosition(int y) {
        if (getChildCount() <= 0) {
            return null;
        }

        int closestY = 8888;
        View closestChild = null;

        for (int i = 0; i < getChildCount(); i++) {
            //数值为6，显示的为5个item，但一滑动就会有6个
            View child = getChildAt(i);

            //getX和getY获取到的值为相对于父视图而言的两个左边缘和上边缘的距离
            //得到每个Item中心点的绝对距离,即每个item中心点到父容器的距离
            int yDistance = (int) child.getY() - y;

            if (Math.abs(yDistance) < closestY) {
                closestY = Math.abs(yDistance);
                closestChild = child;
            }
        }
        return closestChild;
    }

    /**
     * 返回一个距中心点最近的Item
     */
    private View getCenterView() {
        return getChildClosestToPosition(getMeasuredHeight() / 2);
    }

    /**
     * 手势操作完，平滑的滚动
     *
     * @param child 距中心点最近的Item
     */
    private void scrollToView(View child) {
        if (child == null) {
            return;
        }
        //停止滚动
        stopScroll();

        int scrollDistance = getScrollDistance(child);

        if (scrollDistance != 0) {
            //smoothScrollBy()平滑的滑动
            smoothScrollBy(0, scrollDistance);
        }
    }

    /**
     * 得到滑动的绝对距离
     * @param child 距中心点最近的Item
     * @return 滚动的距离
     */
    private int getScrollDistance(View child) {
        //getMeasuredHeight()是实际View的大小，
        //与屏幕无关，而getHeight的大小此时则是屏幕的大小。
        //当超出屏幕后,getMeasuredHeight() 等于 getHeight()加上屏幕之外没有显示的大小

        //一个item的高度
        int itemHeight = getChildAt(0).getMeasuredHeight();
        int centerY = getMeasuredHeight() / 2;

        int childCenterY = ((int) child.getY() + (itemHeight / 2));

        return childCenterY - centerY;//item的中心到容器的中心的距离
    }
}
