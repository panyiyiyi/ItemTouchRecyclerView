package com.itemtouchrecycler.impl;

/**
 * Created by Even on 2017/12/19.
 * Description: itemTouch 接口定义
 */

public interface ItemTouchListener {
    void itemMove(int fromPostion, int toPostion);//拖拽

    void itemRemove(int postion);//删除
}
