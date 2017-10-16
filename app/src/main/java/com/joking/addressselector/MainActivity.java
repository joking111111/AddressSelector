package com.joking.addressselector;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.joking.selectlibrary.adapter.BaseRecyclerAdapter;
import com.joking.selectlibrary.adapter.GeoFenceAdapter;
import com.joking.selectlibrary.widget.BouncingMenu;
import com.joking.selectlibrary.widget.TabController;


public class MainActivity extends AppCompatActivity {

    private BouncingMenu menu;
    private TabLayout mTabLayout;
    private TabController mTabController;
    private RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTabLayout = (TabLayout) findViewById(R.id.tabLayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        mTabController = new TabController(mTabLayout, mRecyclerView, null);
        mTabController.setOnControlListener(new TabController.OnControlListener() {
            @Override
            public void queryData(TabController tabController, String path) {
                BaseRecyclerAdapter adapter;
                if (TextUtils.isEmpty(path)) {
                    adapter = GeoFenceAdapter.newInstance(tabController, "中国", false);
                } else {
                    switch (path.split(",").length) {
                        case 1:
                            adapter = GeoFenceAdapter.newInstance(tabController, "北京", false);
                            break;
                        case 2:
                            adapter = GeoFenceAdapter.newInstance(tabController, "海淀区", false);
                            break;
                        default:
                            adapter = GeoFenceAdapter.newInstance(tabController, "海南", true);
                            break;
                    }
                }
                Log.d("ssss", "path: " + path);
                tabController.setAdapter(adapter);
            }

            @Override
            public void finishSelect(String path, String id) {
                Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
                Log.d("ssss", "finishSelect: " + path);
            }
        });

        mTabController.requestData("中国,北京,海淀区");
    }

    public void popUpMenu(View view) {
        menu = BouncingMenu.make(view, "中国,北京,海淀区").show();
        menu.setOnControlListener(new TabController.OnControlListener() {
            @Override
            public void queryData(TabController tabController, String path) {
                BaseRecyclerAdapter adapter;
                if (TextUtils.isEmpty(path)) {
                    adapter = GeoFenceAdapter.newInstance(tabController, "中国", false);
                } else {
                    switch (path.split(",").length) {
                        case 1:
                            adapter = GeoFenceAdapter.newInstance(tabController, "北京", false);
                            break;
                        case 2:
                            adapter = GeoFenceAdapter.newInstance(tabController, "海淀区", false);
                            break;
                        default:
                            adapter = GeoFenceAdapter.newInstance(tabController, "海南", true);
                            break;
                    }
                }
                Log.d("ssss", "path: " + path);
                tabController.setAdapter(adapter);
            }

            @Override
            public void finishSelect(String path, String id) {
                Toast.makeText(MainActivity.this, path, Toast.LENGTH_SHORT).show();
                Log.d("ssss", "finishSelect: " + path);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if (menu != null && menu.isAlive()) {
            menu.dismiss();
            return;
        }
        super.onBackPressed();
    }
}
