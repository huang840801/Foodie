package com.guanhong.foodie.search;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

public class SearchPresenterTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testMvp() {

        SearchContract.View view = mock(SearchContract.View.class);

        SearchPresenter presenter = new SearchPresenter(view);
//        presenter.start();

        verify(view, times(1))
                .transToRestaurant(null);
    }
}