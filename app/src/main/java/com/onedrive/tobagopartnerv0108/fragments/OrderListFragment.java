package com.onedrive.tobagopartnerv0108.fragments;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseAuthenticatedActivity;
import com.onedrive.tobagopartnerv0108.activities.OrderDetailActivity;
import com.onedrive.tobagopartnerv0108.adapters.OrderAdapter;
import com.onedrive.tobagopartnerv0108.infrastructure.Order;
import com.onedrive.tobagopartnerv0108.infrastructure.TobagoApplication;
import com.onedrive.tobagopartnerv0108.utils.JsonKeys;
import com.onedrive.tobagopartnerv0108.utils.PostStringRequest;
import com.onedrive.tobagopartnerv0108.utils.TobagoConstants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class OrderListFragment extends BaseFragment
        implements OrderAdapter.OnOrderClickListener,
        SwipeRefreshLayout.OnRefreshListener,
        OrderAdapter.OnLoadMoreListener {

    private static final String LIST_ORDER_LINK = "list_order_link";

    protected TobagoApplication application;

    private BaseAuthenticatedActivity activity;
    private OrderAdapter orderAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private String listOrderLink;
    private int PAGE = 1;

    public static OrderListFragment newInstance(String listOrderLink) {

        Bundle args = new Bundle();
        args.putString(LIST_ORDER_LINK, listOrderLink);
        OrderListFragment fragment = new OrderListFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseAuthenticatedActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: I'm here3");
        listOrderLink = getArguments().getString(LIST_ORDER_LINK);
        if (application == null) {
            application = activity.getTobagoApplication();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable final ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_order, container, false);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.fragment_list_order_swipe_refresh);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_list_order_recyclerView);

        LinearLayoutManager layoutManager = new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);

        orderAdapter = new OrderAdapter(activity, this, this, layoutManager);
        recyclerView.setAdapter(orderAdapter);

        swipeRefreshLayout.setOnRefreshListener(this);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getOrderListFromServer(listOrderLink, PAGE);
    }

    @Override
    public void onOrderClicked(Order order) {
        startActivity(new Intent(activity, OrderDetailActivity.class).putExtra(TobagoConstants.EXTRA_ORDER, order));
    }

    @Override
    public void onRefresh() {
        Toast.makeText(activity, "refreshing", Toast.LENGTH_SHORT).show();
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(true);
        orderAdapter.removeAllOrder();
        PAGE = 1;
        getOrderListFromServer(listOrderLink, PAGE);
    }

    @Override
    public void onLoadMore() {
        Log.d(TAG, "onLoadMore: listOrderLink = " + listOrderLink + "\t page = " + PAGE);
        orderAdapter.setProgressingMore(true);
        orderAdapter.setLoadingMore(true);
        getOrderListFromServer(listOrderLink, PAGE);
        PAGE++;
    }

    private void getOrderListFromServer(String link, int page) {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // obtaining list order.
                // turn off progressbar item.
                Log.d(TAG, "onResponse: swipeRefreshLayout.isRefreshing() = " + swipeRefreshLayout.isRefreshing());
                if (swipeRefreshLayout.isRefreshing())
                    swipeRefreshLayout.setRefreshing(false);
                // extract JSON data as an array.
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
                if (orderAdapter.getProgressingMore()) {
                    orderAdapter.setProgressingMore(false);
                } else if (orderAdapter.getLoadingMore()) {
                    orderAdapter.setLoadingMore(false);
                }
            }
        };
        Log.d(TAG, "getOrderListFromServer: application == null " + (application==null));
        Log.d(TAG, "getOrderListFromServer: application.getAccessToken == null " + (application.getAccessToken()==null));
        application.getQueue().add(new PostStringRequest(
                new String[]{JsonKeys.ACCESS_TOKEN, JsonKeys.PAGE},
                new String[]{application.getAccessToken(), String.valueOf(page)},
                link,
                listener));
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
                    return;
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
