package com.joking.selectlibrary.widget;
/*
 * TabController     2017-10-11
 * Copyright (c) 2017 JoKing All right reserved.
 */

import android.support.design.widget.TabLayout;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import com.joking.selectlibrary.R;
import com.joking.selectlibrary.adapter.BaseRecyclerAdapter;

public class TabController {
    private String mTip;
    private TabLayout mTabLayout;
    private RecyclerView mRecyclerView;
    private View mEmptyView;
    private BaseRecyclerAdapter mAdapter;

    public interface OnControlListener {
        /**
         * 获取数据接口
         *
         * @param tabController
         * @param path
         */
        void queryData(TabController tabController, String path);

        /**
         * 最终树节点的信息
         *
         * @param path
         * @param id
         */
        void finishSelect(String path, String id);
    }

    private OnControlListener mOnControlListener;

    public void setOnControlListener(OnControlListener onControlListener) {
        mOnControlListener = onControlListener;
    }

    public TabController(TabLayout tabLayout, RecyclerView recyclerView, View emptyView) {
        mTabLayout = tabLayout;
        mRecyclerView = recyclerView;
        mEmptyView = emptyView;

        mTip = mTabLayout.getContext().getString(R.string.please_select);

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (mTabLayout.getSelectedTabPosition() != mTabLayout.getTabCount() - 1) {
                    removeTab(mTabLayout.getSelectedTabPosition());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    public void recycle() {
        if (mRecyclerView != null) {
            mRecyclerView.setAdapter(null);
        }
        if (mAdapter != null) {
            mAdapter.recycle();
        }

        mTabLayout = null;
        mRecyclerView = null;
        mEmptyView = null;
        mAdapter = null;
    }

    /**
     * 根据path更新tab和获取数据
     *
     * @param path
     */
    public void requestData(String path) {
        if (mOnControlListener != null) {
            String[] str = path.split(",");
            if (mTabLayout.getTabCount() == 0) {
                addTab(mTip);
                for (String s : str) {
                    addTab(s);
                }
            } else {
                addTab(str[str.length - 1]);
            }

            if (mEmptyView != null) {
                mEmptyView.setVisibility(View.VISIBLE);
            }
            mOnControlListener.queryData(this, path);
        }
    }

    /**
     * 已经到树节点
     *
     * @param id
     */
    public void finishSelect(String id) {
        if (mOnControlListener != null) {
            mOnControlListener.finishSelect(getPath(), id);
        }
    }

    /**
     * 获取tab的全路径
     *
     * @return
     */
    public String getPath() {
        return getPath(mTabLayout.getTabCount() - 1);
    }

    /**
     * tab下标0到position的路径
     *
     * @param position
     * @return
     */
    private String getPath(int position) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < position; i++) {
            TabLayout.Tab tab = mTabLayout.getTabAt(i);
            if (tab != null) {
                String text = tab.getText().toString();
                sb.append(text);
                sb.append(",");
            }
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }

    /**
     * 获取到数据后调用
     *
     * @param adapter
     */
    public void setAdapter(BaseRecyclerAdapter adapter) {
        if (mEmptyView != null) {
            mEmptyView.setVisibility(View.INVISIBLE);
        }

        if (mAdapter != null) {
            mAdapter.recycle();
        }
        mAdapter = adapter;
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.scheduleLayoutAnimation();
    }

    /**
     * 添加新tab
     *
     * @param title
     */
    private void addTab(String title) {
        if (TextUtils.isEmpty(title)) {
            return;
        }
        TabLayout.Tab tab = mTabLayout.getTabAt(mTabLayout.getTabCount() - 1);
        if (tab != null) {
            tab.setText(title);
        }
        mTabLayout.addTab(mTabLayout.newTab().setText(mTip), true);
    }

    /**
     * 移除position处的tab
     *
     * @param position
     */
    private void removeTab(int position) {
        int tabCount = mTabLayout.getTabCount();
        if (position < 0 || position > tabCount - 1) {
            return;
        }

        for (int i = tabCount - 1; i >= position; i--) {
            mTabLayout.removeTabAt(i);
        }

        requestData(getPath(position));
    }
}
