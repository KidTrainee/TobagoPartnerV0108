package com.onedrive.tobagopartnerv0108.infrastructure;

import android.os.Parcel;
import android.os.Parcelable;

public class Product implements Parcelable{

    private String productName, productNumber, total, percentCashback, tobagoPercentCashback;

    public Product(String productName,  String productNumber, String productTotal, String percentCashback, String tobagoPercentCashback) {
        this.productName = productName;
        this.total = productTotal;
        this.productNumber = productNumber;
        this.percentCashback = percentCashback;
        this.tobagoPercentCashback = tobagoPercentCashback;
    }


    protected Product(Parcel in) {
        productName = in.readString();
        productNumber = in.readString();
        total = in.readString();
        percentCashback = in.readString();
        tobagoPercentCashback = in.readString();
    }

    public static final Creator<Product> CREATOR = new Creator<Product>() {
        @Override
        public Product createFromParcel(Parcel in) {
            return new Product(in);
        }

        @Override
        public Product[] newArray(int size) {
            return new Product[size];
        }
    };

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getProductNumber() {
        return productNumber;
    }

    public void setProductNumber(String productNumber) {
        this.productNumber = productNumber;
    }

    public String getPercentCashback() {
        return percentCashback;
    }

    public void setPercentCashback(String percentCashback) {
        this.percentCashback = percentCashback;
    }

    public String getTobagoPercentCashback() {
        return tobagoPercentCashback;
    }

    public void setTobagoPercentCashback(String tobagoPercentCashback) {
        this.tobagoPercentCashback = tobagoPercentCashback;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(productName);
        dest.writeString(productNumber);
        dest.writeString(total);
        dest.writeString(percentCashback);
        dest.writeString(tobagoPercentCashback);
    }
}
