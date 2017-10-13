package com.joking.selectlibrary.adapter;
/*
 * NonGeoFenceAdapter     2017-10-11
 * Copyright (c) 2017 JoKing All right reserved.
 */

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.joking.selectlibrary.R;
import com.joking.selectlibrary.widget.TabController;

import java.util.ArrayList;
import java.util.List;

public class NonGeoFenceAdapter extends BaseRecyclerAdapter<NonGeoFence, NonGeoFenceAdapter.NonGeoFenceViewHolder> {

    private TabController mController;

    public NonGeoFenceAdapter(TabController controller) {
        mController = controller;
    }

    @Override
    public void recycle() {
        super.recycle();
        mController = null;
    }

    @Override
    public NonGeoFenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_non_geofence, parent, false);
        return new NonGeoFenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NonGeoFenceViewHolder holder, int position) {
        final NonGeoFence nonGeoFence = mList.get(position);
        holder.content.setText(nonGeoFence.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String path;
                if (TextUtils.isEmpty(mController.getPath())) {
                    path = nonGeoFence.getName();
                } else {
                    path = mController.getPath() + "," + nonGeoFence.getName();
                }
                mController.requestData(path);
            }
        });
    }

    static class NonGeoFenceViewHolder extends RecyclerView.ViewHolder {
        private TextView content;

        public NonGeoFenceViewHolder(View itemView) {
            super(itemView);
            content = (TextView) itemView.findViewById(R.id.content);
        }
    }

    /**
     * 本地测试用
     *
     * @param controller
     * @param content
     * @return
     */
    public static NonGeoFenceAdapter newInstance(TabController controller, String content) {
        List<NonGeoFence> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            NonGeoFence nonGeoFence = new NonGeoFence();
            nonGeoFence.setName(content + i);
            list.add(nonGeoFence);
        }

        NonGeoFenceAdapter adapter = new NonGeoFenceAdapter(controller);
        adapter.addAll(list);
        return adapter;
    }
}
