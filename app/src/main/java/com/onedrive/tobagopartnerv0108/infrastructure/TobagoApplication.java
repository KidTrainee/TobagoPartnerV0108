package com.onedrive.tobagopartnerv0108.infrastructure;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.onedrive.tobagopartnerv0108.login.LoginActivity;
import com.onedrive.tobagopartnerv0108.utils.DataUtils;
import com.squareup.otto.Bus;

import java.util.ArrayList;

public class TobagoApplication extends Application {
    public static final String TAG = TobagoApplication.class.getSimpleName();

    private static final String TOBAGO_PREFERENCES = "tobago_preferences";
    
    private static final String PREFS_MANUFACTURER_BRANCH_NAME = TOBAGO_PREFERENCES + "prefs_branch_name";
    private static final String PREFS_MANUFACTURER_TAX_CODE = TOBAGO_PREFERENCES + "prefs_manufacturer_taxCode";
    private static final String PREFS_MANUFACTURER_ID_CITY = TOBAGO_PREFERENCES + "prefs_manufacturer_id_city";
    private static final String PREFS_MANUFACTURER_SHOP = TOBAGO_PREFERENCES + "prefs_manufacturer_shop";
    private static final String PREFS_MANUFACTURER_LINK = TOBAGO_PREFERENCES + "prefs_manufacturer_link";
    private static final String PREFS_MANUFACTURER_FULL_NAME = TOBAGO_PREFERENCES + "prefs_manufacturer_fullName";
    private static final String PREFS_MANUFACTURER_SEX = TOBAGO_PREFERENCES + "prefs_manufacturer_sex";
    private static final String PREFS_MANUFACTURER_EMAIL = TOBAGO_PREFERENCES + "prefs_manufacturer_email";
    private static final String PREFS_MANUFACTURER_PHONE = TOBAGO_PREFERENCES + "prefs_manufacturer_phone";
    private static final String PREFS_MANUFACTURER_ADDRESS = TOBAGO_PREFERENCES + "prefs_manufacturer_address";
    private static final String PREFS_MANUFACTURER_INFO = TOBAGO_PREFERENCES + "prefs_manufacturer_info";
    private static final String PREFS_MANUFACTURER_ORDER = TOBAGO_PREFERENCES + "prefs_manufacturer_order";
    private static final String PREFS_MANUFACTURER_LOGO = TOBAGO_PREFERENCES + "prefs_manufacturer_logo";
    private static final String PREFS_ACCESS_TOKEN = TOBAGO_PREFERENCES + "prefs_access_token";
    private static final String PREFS_MANUFACTURER_ID = TOBAGO_PREFERENCES + "prefs_manufacturer_id";
    private static final String PREFS_U_TIME_IN_SECONDS = TOBAGO_PREFERENCES + "prefs_uTime_in_seconds";
    // TODO: 02-Oct-17 default values of these preferences.
    private static final String DEFAULT_TOBAGO_LOGO = "http://images.all-free-download.com/images/graphiclarge/harry_potter_icon_6825007.jpg";
    private static final String DEFAULT_VALUES = "tobago";

    private SharedPreferences preferences;
    
    private String branchName, taxCode, idCity, shop, link, fullName, sex,
            email, fone, address, info, order, logo;

    private Settings settings;

    private Bus bus;

    private RequestQueue queue;

    public TobagoApplication() {
        bus = new Bus();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        preferences = getSharedPreferences(TOBAGO_PREFERENCES, Context.MODE_PRIVATE);
        settings = new Settings(this);
        if (queue == null) {
            queue = Volley.newRequestQueue(this);
        }
    }

    public Bus getBus() {
        return bus;
    }

    public Settings getSettings() {
        return settings;
    }

    public ArrayList<String> getBannerImages() {
        return DataUtils.createFakeBannerImages();
    }

    public void doLogout() {
        setAccessToken("");
        setManufacturerId("");
        // trở về màn hình login.
        Intent intent = new Intent(TobagoApplication.this, LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    public RequestQueue getQueue() {
        if (queue == null) {
            queue = Volley.newRequestQueue(this);
        }
        return queue;
    }

    // getters and setters of manufacturer info.
    public String getAccessToken() {
        getPreferences();
        return preferences.getString(PREFS_ACCESS_TOKEN, DEFAULT_VALUES);
    }

    public void setAccessToken(String accessToken) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    public void setManufacturerId(String manufacturerId) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_ID, manufacturerId);
        editor.commit();
    }

    public String getManufacturerId() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_ID, DEFAULT_VALUES);
    }

    private void getPreferences() {
        if (preferences==null) {
            preferences = getSharedPreferences(TOBAGO_PREFERENCES, Context.MODE_PRIVATE);
        }
    }

    public String getBranchName() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_BRANCH_NAME, DEFAULT_VALUES);
    }

    public void setBranchName(String branchName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_BRANCH_NAME, branchName);
        editor.commit();
    }

    public Long getUTimeInSeconds() {
        getPreferences();
        return preferences.getLong(PREFS_U_TIME_IN_SECONDS, 0);
    }
    public void setUTimeInSeconds(Long uTimeInSeconds) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(PREFS_U_TIME_IN_SECONDS, uTimeInSeconds);
        editor.commit();
    }

    public String getManufacturerImage() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_LOGO, DEFAULT_TOBAGO_LOGO);
    }

    public String getTaxCode() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_TAX_CODE, DEFAULT_VALUES);
    }

    public void setTaxCode(String taxCode) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_TAX_CODE, taxCode);
        editor.commit();
    }

    public String getIdCity() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_ID_CITY, DEFAULT_VALUES);
    }

    public void setIdCity(String idCity) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_ID_CITY, idCity);
        editor.commit();
    }

    public String getShop() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_SHOP, DEFAULT_VALUES);
    }

    public void setShop(String shop) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_SHOP, shop);
        editor.commit();
    }

    public String getLink() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_LINK, DEFAULT_VALUES);
    }

    public void setLink(String link) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_LINK, link);
        editor.commit();
    }

    public String getFullName() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_FULL_NAME, DEFAULT_VALUES);
    }

    public void setFullName(String fullName) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_FULL_NAME, fullName);
        editor.commit();
    }

    public String getSex() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_SEX, DEFAULT_VALUES);
    }

    public void setSex(String sex) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_SEX, sex);
        editor.commit();
    }

    public String getEmail() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_EMAIL, DEFAULT_VALUES);
    }

    public void setEmail(String email) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_EMAIL, email);
        editor.commit();
    }

    public String getFone() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_PHONE, DEFAULT_VALUES);
    }

    public void setFone(String fone) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_PHONE, fone);
        editor.commit();
    }

    public String getAddress() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_ADDRESS, DEFAULT_VALUES);
    }

    public void setAddress(String address) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_ADDRESS, address);
        editor.commit();
    }

    public String getInfo() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_INFO, DEFAULT_VALUES);
    }

    public void setInfo(String info) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_INFO, info);
        editor.commit();
    }

    public String getOrder() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_ORDER, DEFAULT_VALUES);
    }

    public void setOrder(String order) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_ORDER, order);
        editor.commit();
    }

    public String getLogo() {
        getPreferences();
        return preferences.getString(PREFS_MANUFACTURER_LOGO, DEFAULT_VALUES);
    }

    public void setLogo(String logo) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(PREFS_MANUFACTURER_LOGO, logo);
        editor.commit();
    }
}
