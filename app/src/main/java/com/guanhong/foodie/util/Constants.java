package com.guanhong.foodie.util;

public class Constants {

    public static final String TAG = "Huang";
    public static final String CODING_TIME = "codingTime";

    // RecyclerView Adapter
    public static final int VIEWTYPE_NORMAL = 1;
    public static final int VIEWTYPE_LOADING = 2;
    public static final int VIEWTYPE_RESTAURANT_MAIN = 3;
    public static final int VIEWTYPE_RESTAURANT_COMMENT = 4;

    // Request Code
    public static final int SINGLE_PICKER = 21;
    public static final int MULTIPLE_PICKER = 22;
    public static final int REQUEST_WRITE_STORAGE_REQUEST_CODE = 102;
    public static final int REQUEST_FINE_LOCATION_PERMISSION = 103;
    public static final int CHILD_MAP_REQUEST_CODE = 87;

    //latlng save digits
    public static final int LATLNG_SAVE_DIGITS = 8;

    //Image type
    public static final String IMAGE_TYPE = "image/*";

    //Image color
    public static final int COLOR = 0xff424242;

    //Bitmap key
    public static final String BITMAP_ROUND_CORNER_KEY = "roundCorner";
    public static final String BITMAP_CIRCLE_KEY = "circle";


    //Table key
    public static final String ARTICLE = "article";
    public static final String COMMENT = "comment";
    public static final String LIKE = "like";
    public static final String RESTAURANT = "restaurant";
    public static final String USER = "user";

    //Map select result
    public static final String ADDRESS = "address";
    public static final String LAT = "lat";
    public static final String LNG = "lng";

    //SharedPreferences
    public static final String USER_DATA = "userData";
    public static final String USER_ID = "userId";

    //Table key : user
    public static final String EMAIL = "email";
    public static final String ID = "id";
    public static final String IMAGE = "image";
    public static final String NAME = "name";

    //Table key : restaurant
    public static final String AUTHOR = "author";
    public static final String CONTENT = "content";
    public static final String CREATEDTIME = "createdTime";
    public static final String MENUS = "menus";
    public static final String DISHNAME = "dishName";
    public static final String DISHPRICE = "dishPrice";
    public static final String LATLNG = "latLng";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String LAT_LNG = "lat_lng";
    public static final String LOCATION = "location";
    public static final String PICTURES = "pictures";
    public static final String RESTAURANT_NAME = "restaurantName";
    public static final String STARCOUNT = "starCount";


    //Table key : like
    public static final String RESTAURANT_LOCATION = "restaurantLocation";
    public static final String RESTAURANT_PICTURES = "restaurantPictures";


    //Date format
    public static final String DATE_FORMAT = "yyyy 年 MM月dd日 HH:mm";

    //Typaface
    public static final String TYPAFACE = "fonts/GenJyuuGothicX-Bold.ttf";
}
