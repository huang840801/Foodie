package com.guanhong.foodie.util;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class ProfileArticleItemDecoration extends RecyclerView.ItemDecoration {

    int mSpace;

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        outRect.left = mSpace;
        outRect.right = mSpace;
        outRect.bottom = mSpace;


    }

    public ProfileArticleItemDecoration(int space) {
        this.mSpace = space;
    }
}
