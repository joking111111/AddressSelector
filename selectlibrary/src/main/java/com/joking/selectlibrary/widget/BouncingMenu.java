package com.joking.selectlibrary.widget;
/*
 * BouncingMenu     2017-10-11
 * Copyright (c) 2017 JoKing All right reserved.
 */

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.joking.selectlibrary.R;

/**
 * BouncingMenu
 * 控制BouncingView展示与消失动画时机
 */
public class BouncingMenu {
    private ViewGroup mParentVG;
    private View rootView;
    private ImageView mask;
    private BouncingView bouncingView;
    private View headerLayout;
    private RecyclerView recyclerView;
    private TabController tabController;
    private boolean isAlive;

    private BouncingMenu(View view, final String request) {
        mParentVG = findRootParent(view);
        //渲染自己的布局进来
        rootView = LayoutInflater.from(view.getContext()).inflate(R.layout.layout_bouncing_menu, null, false);

        mask = (ImageView) rootView.findViewById(R.id.mask);
        bouncingView = (BouncingView) rootView.findViewById(R.id.bv);
        headerLayout = rootView.findViewById(R.id.headerLayout);
        recyclerView = (RecyclerView) rootView.findViewById(R.id.rv);

        recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        //不建议在此加分割线，因为没有动画效果
        //recyclerView.addItemDecoration(new DividerItemDecoration(view.getContext(), DividerItemDecoration.VERTICAL));

        mask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        rootView.findViewById(R.id.tv_tip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        rootView.findViewById(R.id.iv_cross).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        TabLayout tabLayout = (TabLayout) rootView.findViewById(R.id.tl);
        tabController = new TabController(tabLayout, recyclerView, rootView.findViewById(R.id.emptyView));

        bouncingView.setAnimationListener(new BouncingView.AnimationListener() {
            @Override
            public void showContent() {
                tabController.requestData(request);
                headerLayout.setVisibility(View.VISIBLE);
            }
        });
    }

    public void setOnControlListener(TabController.OnControlListener onControlListener) {
        tabController.setOnControlListener(onControlListener);
    }

    private ViewGroup findRootParent(View view) {
        //目标：找到decorview中的contentview
        do {
            if (view instanceof FrameLayout) {
                if (view.getId() == android.R.id.content) {
                    return (ViewGroup) view;
                }
            }
            if (view != null) {
                ViewParent parent = view.getParent();
                view = parent instanceof View ? (View) parent : null;
            }

        } while (view != null);
        return null;
    }

    public static BouncingMenu make(View view, String request) {
        return new BouncingMenu(view, request);
    }

    public BouncingMenu show() {
        if (isReady()) {
            LayoutParams lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
            mParentVG.addView(rootView, lp);
            //开始动画
            bouncingView.show();

            isAlive = true;

            return this;
        }
        return null;
    }

    public void dismiss() {
        if (isReady()) {
            isAlive = false;
            mask.setVisibility(View.INVISIBLE);

            ObjectAnimator animator = ObjectAnimator.ofFloat(rootView, "translationY", 0, rootView.getHeight());
            animator.setDuration(700);
            animator.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    recycle();
                }
            });
            animator.start();
        }
    }

    private void recycle() {
        if (mParentVG != null && rootView != null) {
            mParentVG.removeView(rootView);
        }
        if (tabController != null) {
            tabController.recycle();
        }

        mParentVG = null;
        rootView = null;
        mask = null;
        bouncingView = null;
        headerLayout = null;
        recyclerView = null;
        tabController = null;
    }

    public boolean isReady() {
        return mParentVG != null
                && rootView != null
                && mask != null
                && bouncingView != null
                && headerLayout != null
                && recyclerView != null
                && tabController != null;
    }

    public boolean isAlive() {
        return isAlive;
    }
}
