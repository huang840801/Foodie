package com.guanhong.foodie.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class NormalArticleItemDecoration extends RecyclerView.ItemDecoration {

    private int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;


    }

    public NormalArticleItemDecoration(int space) {
        this.mSpace = space;
    }
}
