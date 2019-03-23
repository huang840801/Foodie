package com.guanhong.foodie.recommend;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class RecommendPresenterTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testMvp() {
        RecommendContract.View view = mock(RecommendContract.View.class);

//        RecommendPresenter presenter = new RecommendPresenter(view);
//        presenter.start();

        verify(view, times(1))
                .showAllRestaurantList(null);
    }
}