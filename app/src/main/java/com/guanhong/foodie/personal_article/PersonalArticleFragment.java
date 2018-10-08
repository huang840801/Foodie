package com.guanhong.foodie.personal_article;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.guanhong.foodie.R;

import static com.google.common.base.Preconditions.checkNotNull;

public class PersonalArticleFragment extends Fragment implements PersonalArticleContract.View {

    private PersonalArticleContract.Presenter mPresenter;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    public PersonalArticleFragment(){}

    public static PersonalArticleFragment newInstance(){
        return new PersonalArticleFragment();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fragment_personal_article, container, false);
        return view;
    }

    @Override
    public void setPresenter(PersonalArticleContract.Presenter presenter) {
        mPresenter = checkNotNull(presenter);
        mPresenter.start();
    }
}
