package com.onedrive.tobagopartnerv0108.infrastructure;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Binh on 25-Aug-17.
 */

public class Settings {

    public static final String GRID_LAYOUT = "GridLayout";
    public static final String LINEAR_LAYOUT = "LinearLayout";
    public static final String ORDER_BY_DATE = "order_by_date";
    public static final String ORDER_BY_PRICE = "order_by_price";
    public static final String ORDER_BY_NAME = "order_by_name";
    public static final String FILTER_BY_DATE = "filter_by_date";
    public static final String FILTER_BY_PRICE = "filter_by_price";
    public static final String FILTER_DEFAULT = "filter_default";

    private static final String SETTINGS_PREFERENCES = "settings_preferences";
    private static final String LIST_STYLE_SETTING_PREF = "list_style_setting_pref";
    private static final String ORDER_SETTING_PREF = "order_setting_pref";
    private static final String FILTER_SETTING_PREF = "filter_setting_pref";

    private final Context context;
    private final SharedPreferences preferences;

    private String listStyle;
    private String orderBy;
    private String filter;

    public Settings(Context context) {
        this.context = context;

        preferences = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
        listStyle = preferences.getString(LIST_STYLE_SETTING_PREF, GRID_LAYOUT);
        orderBy = preferences.getString(ORDER_SETTING_PREF, ORDER_BY_DATE);
        filter = preferences.getString(FILTER_SETTING_PREF, FILTER_DEFAULT);
    }

    public String getListStyle() {
        return listStyle;
    }

    public String getOrderBy() {
        return orderBy;
    }

    public String getFilter() {
        return filter;
    }

    public void setFilter(String filter) {
        this.filter = filter;
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putString(LIST_STYLE_SETTING_PREF, filter);
        editor.apply();
    }

    public void setListStyle(String listStyle) {
        this.listStyle = listStyle;
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putString(LIST_STYLE_SETTING_PREF, listStyle);
        editor.apply();
    }

    public void setOrderBy(String orderBy) {
        this.orderBy = orderBy;
        SharedPreferences.Editor editor  = preferences.edit();
        editor.putString(LIST_STYLE_SETTING_PREF, orderBy);
        editor.apply();
    }
}
