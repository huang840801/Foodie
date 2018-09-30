package com.guanhong.foodie.objects;

public class Menu {

    private String mDishName;
    private String mDishPrice;

    public Menu(){
        mDishName = "";
        mDishPrice = "";
    }

    public String getDishName() {
        return mDishName;
    }

    public void setDishName(String dishName) {
        mDishName = dishName;
    }

    public String getDishPrice() {
        return mDishPrice;
    }

    public void setDishPrice(String dishPrice) {
        mDishPrice = dishPrice;
    }
}
