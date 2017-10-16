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
import com.joking.selectlibrary.model.GeoFence;
import com.joking.selectlibrary.widget.TabController;

import java.util.ArrayList;
import java.util.List;

public class GeoFenceAdapter extends BaseRecyclerAdapter<GeoFence, GeoFenceAdapter.GeoFenceViewHolder> {

    private TabController mController;

    public GeoFenceAdapter(TabController controller) {
        mController = controller;
    }

    @Override
    public void recycle() {
        super.recycle();
        mController = null;
    }

    @Override
    public GeoFenceViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_geofence, parent, false);
        return new GeoFenceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GeoFenceViewHolder holder, int position) {
        final GeoFence geoFence = mList.get(position);
        holder.content.setText(geoFence.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(geoFence.getId())) {
                    //还没到叶子节点
                    String path;
                    if (TextUtils.isEmpty(mController.getPath())) {
                        path = geoFence.getName();
                    } else {
                        path = mController.getPath() + "," + geoFence.getName();
                    }
                    mController.requestData(path);
                } else {
                    //通知controller结束选择
                    mController.finishSelect(geoFence.getId());
                }
            }
        });
    }

    static class GeoFenceViewHolder extends RecyclerView.ViewHolder {
        private TextView content;

        public GeoFenceViewHolder(View itemView) {
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
    public static GeoFenceAdapter newInstance(TabController controller, String content, boolean isGeoFence) {
        List<GeoFence> list = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            GeoFence geoFence = new GeoFence();
            geoFence.setName(content + i);
            if (isGeoFence) {
                geoFence.setId(String.valueOf(i));
            }
            list.add(geoFence);
        }

        GeoFenceAdapter adapter = new GeoFenceAdapter(controller);
        adapter.addAll(list);
        return adapter;
    }
}
