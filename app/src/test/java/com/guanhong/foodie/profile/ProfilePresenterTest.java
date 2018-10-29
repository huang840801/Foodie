package com.guanhong.foodie.profile;

import android.content.Context;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class ProfilePresenterTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void start() {

        ProfileContract.View view = mock(ProfileContract.View.class);
        Context context = mock(Context.class);
        ProfilePresenter presenter = new ProfilePresenter(view, context);
        presenter.start();

//        verify(view, times(1))
//                .showPersonalArticleUi(null);
    }
}