package com.joking.selectlibrary.adapter;
/*
 * BaseRecyclerAdapter     2017-08-28
 * Copyright (c) 2017 JoKing All right reserved.
 */

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T, V extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<V> {

    protected List<T> mList = new ArrayList<>();

    /**
     * 是否做重复性检查
     *
     * @return boolean
     */
    public boolean checkDuplicate() {
        return false;
    }

    public List<T> getList() {
        return mList;
    }

    public void add(T item) {
        if (checkDuplicate() && mList.contains(item)) {
            return;
        }
        mList.add(item);
        notifyItemInserted(mList.size() - 1);// size已经加一
    }

    public void addAll(List<T> list) {
        if (checkDuplicate()) {
            list.removeAll(mList);
        }
        int size = mList.size();
        mList.addAll(list);
        notifyItemRangeInserted(size, list.size());
    }

    public void remove(int position) {
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size() - position);// size已经减一
    }

    public void clear() {
        mList.clear();
        notifyDataSetChanged();
    }

    public void recycle() {
        mList.clear();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
}