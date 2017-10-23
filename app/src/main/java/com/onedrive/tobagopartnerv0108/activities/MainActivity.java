package com.onedrive.tobagopartnerv0108.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.util.Log;
import android.view.Menu;
import android.view.View;

import com.android.volley.Response;
import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.adapters.OrderAdapter;
import com.onedrive.tobagopartnerv0108.adapters.SlidingImageAdapter;
import com.onedrive.tobagopartnerv0108.infrastructure.Order;
import com.onedrive.tobagopartnerv0108.utils.JsonKeys;
import com.onedrive.tobagopartnerv0108.utils.PostStringRequest;
import com.onedrive.tobagopartnerv0108.utils.TobagoConstants;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends BaseAuthenticatedActivity
        implements  NavigationView.OnNavigationItemSelectedListener,
        OrderAdapter.OnOrderClickListener,
                    View.OnClickListener {

    private static final int FIRST_PAGE = 1;
    private int currentImage = 0;

    ArrayList<Order> orderList;
    private OrderAdapter orderAdapter;

    @Override
    protected void onTobagoCreate(Bundle savedInstanceState) {
        shouldShowActivityLabel = false;
        shouldShowActivityLogo = true;
        setContentView(R.layout.activity_main);
        setupBanner();
        orderList = new ArrayList<>();
        setupRecyclerView();

        (findViewById(R.id.include_toolbar_main_account)).setOnClickListener(this);
        (findViewById(R.id.include_toolbar_main_qr_code_scan)).setOnClickListener(this);
        (findViewById(R.id.include_toolbar_main_enterCode)).setOnClickListener(this);
        (findViewById(R.id.content_main_textView_sale_history)).setOnClickListener(this);
    }

    private void setupBanner() {
        final ViewPager bannerViewPager = (ViewPager) findViewById(R.id.content_main_viewPager);
        final ArrayList<String> bannerImageList = application.getBannerImages();
        bannerViewPager.setAdapter(new SlidingImageAdapter(this, bannerImageList));

        final Handler handler = new Handler();

        final Runnable slideImageRunnable = new Runnable() {
            @Override
            public void run() {
                if (currentImage == bannerImageList.size())
                    currentImage = 0;
                    bannerViewPager.setCurrentItem(currentImage, true);
                currentImage++;
            }
        };
        Timer slideTimer = new Timer();
        slideTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(slideImageRunnable);
            }
        }, 5000, 5000);
    }

    private void setupRecyclerView() {

        RecyclerView recentOrderRecyclerView = (RecyclerView) findViewById(R.id.recyclerView_recentOrder);

        // layout
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recentOrderRecyclerView.setLayoutManager(layoutManager);

        orderAdapter = new OrderAdapter(this, null, this, layoutManager);
        recentOrderRecyclerView.setAdapter(orderAdapter);
        // data and adapter.
//        ArrayList<Order> orderList = getOrderListFromFakeData();
        getOrderListFromServer(FIRST_PAGE);

        ViewUtils.applySnapHelper(recentOrderRecyclerView);
    }

    private void getOrderListFromServer(int page) {

        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                orderAdapter.removeAllOrder();
                swipeRefreshLayout.setRefreshing(false);

                try {
                    JSONArray jsonListOrder = new JSONArray(response);
                    if (jsonListOrder.length()<15) {
                        orderAdapter.setIsMoreOrder(false);
                    } else {
                        orderAdapter.setIsMoreOrder(true);
                    }
                    readJsonDataFromArray(jsonListOrder);
                } catch (JSONException e) {
                    // try extract json data as an object
                    Log.e(TAG, "onResponse: lỗi khi đọc json data từ response as an array.\n", e);
                    try {
                        JSONObject jo = new JSONObject(response);
                        readJsonDataFromObject(jo);
                    } catch (JSONException e2) {
                        Log.e(TAG, "onResponse: lỗi khi đọc json data từ response as an object.\n " + response, e2);
                    }
                }
            }
        };
        Log.d(TAG, "Manufacturer = " + application.getManufacturerId()
        + "\n page = " + page);
        application.getQueue().add(new PostStringRequest(
                new String[] {JsonKeys.ACCESS_TOKEN, JsonKeys.PAGE},
                new String[] {application.getAccessToken(), String.valueOf(page)},
                TobagoConstants.LINK_GET_LIST_ORDER,
                listener));
    }


//    private ArrayList<Order> getOrderListFromFakeData() {
//        ArrayList<Order> orderList = DataUtils.createFakeOrder(3, Order.PENDING);
//        orderList.addAll(DataUtils.createFakeOrder(3, Order.SUCCESS));
//        orderList.addAll(DataUtils.createFakeOrder(3, Order.FAIL));
//        return orderList;
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onOrderClicked(Order order) {
        startActivity(new Intent(this, OrderDetailActivity.class)
                .putExtra(TobagoConstants.EXTRA_ORDER, order));
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.include_toolbar_main_account:
                startActivity(new Intent(this, AccountActivity.class));
                break;
            case R.id.include_toolbar_main_qr_code_scan:
                startActivity(new Intent(this, QRCodeScanActivity.class));
                break;
            case R.id.include_toolbar_main_enterCode:
                startActivity(new Intent(this, CreateQrCodeActivity.class));
                break;
            case R.id.content_main_textView_sale_history:
                startActivity(new Intent(this, SaleHistoryActivity.class));
                break;
        }
    }

    @Override
    public void onRefresh() {
        super.onRefresh();
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setRefreshing(true);
            getOrderListFromServer(FIRST_PAGE);
        }
    }


    private void readJsonDataFromObject(JSONObject jo) throws JSONException {
        if (jo.has(JsonKeys.CODE)) {
            String code = jo.getString(JsonKeys.CODE);
            switch (Integer.parseInt(code)) {
                case 1:
                    Log.e(TAG, "lỗi khi đọc json data từ response. code = " + code);
                    // error: cập nhật thất bại

                    return;
                case -1:
                    Log.e(TAG, "lỗi khi đọc json data từ response. code = " + code);
                    // error: accessToken.
                    application.doLogout();
                    break;
            }
        }
    }

    private void readJsonDataFromArray(JSONArray jsonArray) throws JSONException {
        ArrayList<Order> orderList = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {
            String orderStatus, orderTime, orderCode, orderId, orderTotal, orderCashback;

            JSONObject jsonOrder = jsonArray.getJSONObject(i).getJSONObject(JsonKeys.ORDER);
            orderStatus = jsonOrder.getString(JsonKeys.SHOW);
            orderTime = jsonOrder.getString(JsonKeys.TIME);
            orderCode = jsonOrder.getString(JsonKeys.ORDER_CODE);
            orderTotal = jsonOrder.getString(JsonKeys.TOTAL_COST);
            orderCashback = jsonOrder.getString(JsonKeys.CASHBACK);
            orderId = jsonOrder.getString(JsonKeys.ID);
            Order order = new Order(orderId, orderTime, orderStatus, orderCode, orderTotal, orderCashback);
            Log.d(TAG, "readJsonDataFromArray: order = " + order.toString());
            orderList.add(order);
        }
        orderAdapter.addMoreOrders(orderList);
    }
}
