package com.onedrive.tobagopartnerv0108.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.onedrive.tobagopartnerv0108.activities.BaseAuthenticatedActivity;
import com.onedrive.tobagopartnerv0108.fragments.OrderListFragment;
import com.onedrive.tobagopartnerv0108.utils.TobagoConstants;

public class OrderPagerAdapter extends FragmentStatePagerAdapter {

    private static final int NUM_PAGES = 4;

    private BaseAuthenticatedActivity activity;

    private String pageTitles[] = new String[] {"Tất cả", "Đang chờ", "Thành công", "Thất bại"};

    public OrderPagerAdapter(BaseAuthenticatedActivity activity) {
        super(activity.getSupportFragmentManager());
        this.activity = activity;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return OrderListFragment.newInstance(TobagoConstants.LINK_GET_LIST_ORDER);
            case 1:
                return OrderListFragment.newInstance(TobagoConstants.LINK_GET_LIST_ORDER_WAITING);
            case 2:
                return OrderListFragment.newInstance(TobagoConstants.LINK_GET_LIST_ORDER_SUCCESS);
            case 3:
                return OrderListFragment.newInstance(TobagoConstants.LINK_GET_LIST_ORDER_FAIL);
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return pageTitles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return pageTitles[position];
    }
}
