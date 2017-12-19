package com.itemtouchrecycler;

import android.app.Service;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.TextView;

import com.itemtouchrecycler.callback.ItemTouchCallback;
import com.itemtouchrecycler.impl.ItemTouchListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private List<String> dataList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        initData();
        initView();
    }

    private void initView() {
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        ItemTouchAdapter mAdapter = new ItemTouchAdapter();
        recyclerView.setAdapter(mAdapter);

        ItemTouchCallback callback = new ItemTouchCallback(mAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(recyclerView);


    }

    /**
     * 初始化数据
     */
    private void initData() {
        for (int i = 0; i < 16; i++) {
            dataList.add("Item " + i);
        }
    }

    public class ItemTouchAdapter extends RecyclerView.Adapter<ItemTouchAdapter.ItemTouchViewHolder> implements ItemTouchListener {
        @Override
        public ItemTouchViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
            View itemView = getLayoutInflater().inflate(R.layout.item_default, parent, false);
            final ItemTouchViewHolder itemTouchViewHolder = new ItemTouchViewHolder(itemView);//处理长按动画
            itemTouchViewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    RecyclerView recyclerView = ((RecyclerView) parent);
                    startDragAnimator(recyclerView, itemTouchViewHolder);
                    //获取系统震动服务
                    Vibrator vib = (Vibrator) MainActivity.this.getSystemService(Service.VIBRATOR_SERVICE);
                    //震动70毫秒
                    vib.vibrate(50);
                    return true;
                }
            });


            return itemTouchViewHolder;
        }

        @Override
        public void onBindViewHolder(ItemTouchViewHolder holder, final int position) {
            holder.textView.setText(dataList.get(position));

        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }

        /*拖拽*/
        @Override
        public void itemMove(int fromPostion, int toPostion) {
//            String remove = dataList.remove(fromPostion);
//            dataList.add(toPostion, remove);
//            notifyItemMoved(fromPostion, toPostion);

            if (fromPostion < toPostion) {
                for (int i = fromPostion; i < toPostion; i++) {
                    Collections.swap(dataList, i, i + 1);
                    notifyItemMoved(i, i + 1);
                }
            } else {
                for (int i = fromPostion; i > toPostion; i--) {
                    Collections.swap(dataList, i, i - 1);
                    notifyItemMoved(i, i - 1);
                }
            }

        }

        /**
         * 开启拖拽模式
         *
         * @param parent
         * @param itemTouchViewHolder
         */
        private void startDragAnimator(RecyclerView parent, ItemTouchViewHolder itemTouchViewHolder) {
            int visibleChildCount = parent.getChildCount();
            for (int i = 0; i < visibleChildCount; i++) {
                View view = parent.getChildAt(i);
                if (recyclerView.getChildViewHolder(view).equals(itemTouchViewHolder)) {
                    continue;
                }
                //从左向右
                Animation rotateAnim = new RotateAnimation(-3, 3, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                rotateAnim.setDuration(300);
                rotateAnim.setRepeatMode(Animation.REVERSE);
                rotateAnim.setRepeatCount(Animation.INFINITE);
                rotateAnim.start();
                view.setAnimation(rotateAnim);
            }
        }

        /**
         * 清理动画
         */
        public void clearDragAnimator(RecyclerView parent) {
            int visibleChildCount = parent.getChildCount();
            for (int i = 0; i < visibleChildCount; i++) {
                View view = parent.getChildAt(i);
                view.clearAnimation();
            }
        }


        /*删除*/
        @Override
        public void itemRemove(int postion) {

        }

        class ItemTouchViewHolder extends RecyclerView.ViewHolder {
            private TextView textView;

            public ItemTouchViewHolder(View itemView) {
                super(itemView);
                textView = (TextView) itemView.findViewById(R.id.item_default_text);
            }
        }
    }

}
