package com.onedrive.tobagopartnerv0108.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Response;
import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.fragments.ProductDetailsDialogFragment;
import com.onedrive.tobagopartnerv0108.fragments.QRCodeDialogFragment;
import com.onedrive.tobagopartnerv0108.infrastructure.Order;
import com.onedrive.tobagopartnerv0108.infrastructure.Product;
import com.onedrive.tobagopartnerv0108.utils.JsonKeys;
import com.onedrive.tobagopartnerv0108.utils.PostStringRequest;
import com.onedrive.tobagopartnerv0108.utils.TobagoConstants;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class OrderDetailActivity extends BaseAuthenticatedActivity implements View.OnClickListener {

    private Order order;
    private ArrayList<Product> productList;

    @Override
    protected void onTobagoCreate(Bundle savedInstanceState) {

//        shouldShowUpNavigation = true;
//        shouldShowActivityLabel = true;
//        setContentView(R.layout.activity_order_details);
//
//        productList = new ArrayList<>();
//        order = getIntent().getParcelableExtra(TobagoConstants.EXTRA_ORDER);
//        getOrderDetails();
//
//
//        ((TextView) findViewById(R.id.activity_order_details_textView_order_code)).setText(order.getOrderCode());
//        // uTimeInSeoncds là chênh lệch múi giờ.
//        ((TextView) findViewById(R.id.activity_order_details_textView_order_time))
//                .setText(ViewUtils.convertTimeMillisecToDate(Long.parseLong(order.getOrderTime()),application.getUTimeInSeconds()));
//        ((TextView) findViewById(R.id.activity_order_details_textView_order_total)).setText(order.getOrderMoney());
//        ((TextView) findViewById(R.id.activity_order_details_textView_order_cashback_tobago)).setText(order.getOrderCashback());
//        ((TextView) findViewById(R.id.activity_order_details_textView_order_status)).setText(order.getOrderStatus());
//        ((TextView) findViewById(R.id.activity_order_details_textView_order_status)).setTextColor(order.getStatusColor());
//        Log.d(TAG, "onTobagoCreate: order.getStatusColor() = " + order.getStatusColor());
//
//        (findViewById(R.id.activity_order_details_linearLayout_product_details)).setOnClickListener(this);
//        (findViewById(R.id.activity_order_details_linearLayout_qr_code)).setOnClickListener(this);
    }

    private void getOrderDetails() {
        Response.Listener<String> listener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    int code = Integer.parseInt(jo.getString(JsonKeys.CODE));
                    Log.e(TAG, "onResponse: code = " + code);
                    switch (code) {
                        case 0:
                            // success
                            extractData(jo);
                            break;
                        case -1:
                            // error codeOrder
                            ViewUtils.makeToast(OrderDetailActivity.this, R.string.error_code_order);
                            Log.e(TAG, "onResponse: error codeOrder");
                            break;
                        case 2:
                            // error accessToken
                            Log.e(TAG, "onResponse: error accessToken");
                            ViewUtils.makeToast(OrderDetailActivity.this, R.string.do_logout);
                            application.doLogout();
                            break;
                        default:
                            ViewUtils.makeToast(OrderDetailActivity.this, R.string.not_support);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        application.getQueue().add(new PostStringRequest(
                new String[] {JsonKeys.ORDER_CODE, JsonKeys.ACCESS_TOKEN},
                new String[] {order.getOrderCode(), application.getAccessToken()},
                TobagoConstants.LINK_GET_ORDER,
                listener));
    }

    private void extractData(JSONObject jo) throws JSONException{
        JSONArray listProduct = jo.getJSONArray(JsonKeys.LIST_PRODUCT);
        ((TextView) findViewById(R.id.activity_order_details_textView_customer_name)).setText(jo.getString(JsonKeys.NAME_USER_ONL));
        ((TextView) findViewById(R.id.activity_order_details_textView_customer_id)).setText(jo.getString(JsonKeys.ID_USER_ONL));
        for (int i = 0; i < listProduct.length(); i++) {
            JSONObject product = listProduct.getJSONObject(i);
            productList.add(new Product(
                    product.getString(JsonKeys.PRODUCT_NAME),
                    product.getString(JsonKeys.PRODUCT_NUMBER),
                    product.getString(JsonKeys.TOTAL),
                    product.getString(JsonKeys.PERCENT_CASHBACK),
                    product.getString(JsonKeys.PERCENT_CASHBACK_TOBAGO)));
        }
    }

    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (v.getId()) {
            case R.id.activity_order_details_linearLayout_product_details:
                ProductDetailsDialogFragment dialog = ProductDetailsDialogFragment.newInstance(productList);
                dialog.show(fragmentTransaction, TAG);
                break;
            case R.id.activity_order_details_linearLayout_qr_code:
                QRCodeDialogFragment qrCodeDialogFragment = QRCodeDialogFragment.newInstance("https://www.qrstuff.com/images/sample.png");
                qrCodeDialogFragment.show(fragmentTransaction, TAG);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
