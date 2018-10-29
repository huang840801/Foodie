package com.guanhong.foodie.restaurant;

import com.guanhong.foodie.objects.Article;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class RestaurantPresenterTest {

    private Article mMockArticle;

    @Before
    public void setUp() throws Exception {

        mMockArticle = mock(Article.class);
    }

    @Test
    public void openPersonalArticle() {

        mMockArticle.setRestaurantName("restaurant name");
    }
}