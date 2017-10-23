package com.onedrive.tobagopartnerv0108.activities;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.adapters.OrderPagerAdapter;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;


public class SaleHistoryActivity extends BaseAuthenticatedActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onTobagoCreate(Bundle savedInstanceState) {
        shouldShowUpNavigation = true;
        shouldShowActivityLabel = true;
        setContentView(R.layout.activity_sale_history);

        ViewPager viewPager = (ViewPager) findViewById(R.id.content_sale_activity_viewPager);
        OrderPagerAdapter adapter = new OrderPagerAdapter(this);
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.content_sale_activity_sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_sale_history, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.action_search:
                ViewUtils.makeToast(SaleHistoryActivity.this, R.string.action_search);
                return true;
        }
        return false;
    }
}
