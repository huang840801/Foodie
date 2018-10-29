package com.guanhong.foodie.post;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PostPresenterTest {

    private ArrayList mMockList;

    @Before
    public void setUp() throws Exception {

        mMockList = mock(ArrayList.class);
    }

    @Test
    public void uploadImage() {

//        when(mMockList.get(0)).thenReturn("two");

//        System.out.println(mMockList.get(0));

        mMockList.add("one");
        mMockList.clear();

        verify(mMockList).add("one");
        verify(mMockList).clear();
    }


}