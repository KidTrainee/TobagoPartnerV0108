package com.onedrive.tobagopartnerv0108.utils;

import java.util.ArrayList;

public class DataUtils {
    public static final String FAKE_URL = "https://store.storeimages.cdn-apple.com/4974/as-images.apple.com/is/image/AppleInc/aos/published/images/H/KH/HKHC2/HKHC2?wid=445&hei=445&fmt=jpeg&qlt=95&op_sharpen=0&resMode=bicub&op_usm=0.5,0.5,0,0&iccEmbed=0&layer=comp&.v=1474481298618";

//    public static ArrayList<Order> createFakeOrder(int number, String status) {
//        ArrayList<Order> orderArrayList = new ArrayList<>();
//        for (int i = 0 ; i < number; i++) {
//            Order order = new Order("NH-88" + i, "08/08/1988 - 08:08:08", status, "ABC-12"+i, "Default", "888", "88");
//            orderArrayList.add(order);
//        }
//        return orderArrayList;
//    }

//    public static ArrayList<Product> createFakeProductList() {
//        ArrayList<Product> fakeProductList = new ArrayList<>();
//        for (int i = 0; i < 20; i ++) {
//            Product product = new Product("Áo sơ mi " + i, "08", "888.888", "");
//            fakeProductList.add(product);
//        }
//        return fakeProductList;
//    }


    public static ArrayList<String> createFakeBannerImages() {
        ArrayList<String> bannerImages = new ArrayList<>();
        bannerImages.add("https://cdn.pixabay.com/photo/2015/12/13/09/42/banner-1090835_960_720.jpg");
        bannerImages.add("https://cdn.pixabay.com/photo/2017/03/19/12/42/banner-2156395_960_720.jpg");
        bannerImages.add("http://www.1800printing.com/images/mastertemplates/6565/23_97409_1.jpg");
        return bannerImages;
    }
}
