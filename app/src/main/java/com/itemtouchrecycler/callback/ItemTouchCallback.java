package com.itemtouchrecycler.callback;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.itemtouchrecycler.MainActivity;

/**
 * Created by Even on 2017/12/19.
 * Description:
 */

public class ItemTouchCallback extends ItemTouchHelper.Callback {
    private MainActivity.ItemTouchAdapter mAdapter;

    public ItemTouchCallback(MainActivity.ItemTouchAdapter adapter) {
        super();
        this.mAdapter = adapter;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        int dragFlags;//可以拖动的方向
        int swipeFlags;//滑动的方向
        if (recyclerView.getLayoutManager() instanceof GridLayoutManager) {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;//表示可以拖动的方向
            swipeFlags = 0;
        } else {
            dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;//只能上下拖动
            swipeFlags = 0;
        }
        return makeMovementFlags(dragFlags, swipeFlags);
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        int fromPosition = viewHolder.getAdapterPosition();//获取拖动的Viewholder的position，也就是当前item的position
        int toPosition = target.getAdapterPosition();//获取目标viewholder的position，也就是要拖动到当前位置的position
        mAdapter.itemMove(fromPosition, toPosition);
        return true;
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        mAdapter.clearDragAnimator(recyclerView);
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

    }
}
