package com.onedrive.tobagopartnerv0108.infrastructure;


import android.os.Parcel;
import android.os.Parcelable;

import com.onedrive.tobagopartnerv0108.R;

public class Order implements Parcelable{

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel source) {
            return new Order(source);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };
    public static final String PENDING = "0";
    public static final String SUCCESS = "1";
    public static final String FAIL = "2";

    private String orderId, orderTime, orderStatus, orderCode, orderMoney, orderCashback;

    public Order(String orderId, String orderTime, String orderStatus, String orderCode, String orderMoney, String orderCashback) {
        this.orderId = orderId;
        this.orderTime = orderTime;
        this.orderStatus = orderStatus;
        this.orderCode = orderCode;
        this.orderMoney = orderMoney;
        this.orderCashback = orderCashback;
    }

    public String getOrderMoney() {
        return orderMoney;
    }

    public void setOrderMoney(String orderMoney) {
        this.orderMoney = orderMoney;
    }

    public String getOrderCashback() {
        return orderCashback;
    }

    public void setOrderCashback(String orderCashback) {
        this.orderCashback = orderCashback;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public String getOrderStatus() {
        switch (orderStatus) {
            case PENDING:
                return "Đang chờ";
            case SUCCESS:
                return "Thành công";
            case FAIL:
                return "Thất bại";
        }
        return orderStatus;
    }

    public int getStatusColor() {
        switch (orderStatus) {
            case PENDING:
                return R.color.colorPrimary;
            case SUCCESS:
                return R.color.colorGreen;
            case FAIL:
                return R.color.colorRed;
            default:
                throw new IllegalArgumentException("Not found this orderStatus");
        }
    }

    public void setOrderStatus(String status) {
        this.orderStatus = status;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel destination, int flags) {
        destination.writeString(orderId);
        destination.writeString(orderTime);
        destination.writeString(orderStatus);
        destination.writeString(orderCode);
        destination.writeString(orderMoney);
        destination.writeString(orderCashback);
    }

    public Order(Parcel parcel) {
        orderId = parcel.readString();
        orderTime = parcel.readString();
        orderStatus = parcel.readString();
        orderCode = parcel.readString();
        orderMoney = parcel.readString();
        orderCashback = parcel.readString();
    }

    @Override
    public String toString() {
        return "Order{" +
                "orderId='" + orderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", orderStatus='" + orderStatus + '\'' +
                ", orderCode='" + orderCode + '\'' +
                ", orderMoney='" + orderMoney + '\'' +
                ", orderCashback='" + orderCashback + '\'' +
                '}';
    }
}
