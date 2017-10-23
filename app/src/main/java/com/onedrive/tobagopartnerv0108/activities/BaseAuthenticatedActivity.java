package com.onedrive.tobagopartnerv0108.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NavUtils;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.login.LoginActivity;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


public abstract class BaseAuthenticatedActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener,
                                SwipeRefreshLayout.OnRefreshListener{

    protected boolean shouldShowUpNavigation = false;
    protected boolean shouldShowActivityLabel = false;
    protected boolean shouldShowActivityLogo = false;

    protected SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected final void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            Log.d(TAG, "onCreate: accessToken = " + application.getAccessToken());
            Log.d(TAG, "onCreate: manufacturerId = " + application.getManufacturerId());
            if (application.getAccessToken().isEmpty()) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
            }

            onTobagoCreate(savedInstanceState);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(R.layout.activity_base_authenticated);

        ViewGroup container = (ViewGroup) findViewById(R.id.frame_container);
        LayoutInflater.from(this).inflate(layoutResID, container, true);

        // setup content
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImageView activityLogo = (ImageView) toolbar.findViewById(R.id.include_toolbar_imageView_activityLogo);
        TextView activityTitle = (TextView) toolbar.findViewById(R.id.include_toolbar_textView_activityTitle);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (actionBar != null) {
            actionBar.setDisplayShowTitleEnabled(false);
            if (shouldShowUpNavigation) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            } else {
                actionBar.setDisplayHomeAsUpEnabled(false);
                ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
                drawer.setDrawerListener(toggle);
                toggle.syncState();
            }

            if (shouldShowActivityLabel) {
                activityTitle.setVisibility(View.VISIBLE);
                activityTitle.setText(this.getTitle());
            } else {
                activityTitle.setVisibility(View.GONE);
            }

            if (shouldShowActivityLogo) {
                activityLogo.setVisibility(View.VISIBLE);
            } else {
                activityLogo.setVisibility(View.GONE);
            }
        }

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        if (swipeRefreshLayout != null) {
            swipeRefreshLayout.setOnRefreshListener(this);
        }
        View header = navigationView.getHeaderView(0);
        CircleImageView circleImageView = (CircleImageView) header.findViewById(R.id.nav_drawer_imageView_manufacturer_image);
        TextView manufacturerName = (TextView) header.findViewById(R.id.nav_drawer_textView_manufacturer_name);
        TextView manufacturerWebsite = (TextView) header.findViewById(R.id.nav_drawer_textView_manufacturer_website);
        Log.d(TAG, "setContentView: circleImageView == null: " + (circleImageView==null));

        Picasso.with(this).load(application.getManufacturerImage())
                .into(circleImageView);
        manufacturerName.setText(application.getBranchName());
        manufacturerWebsite.setText(application.getLink());
    }

    protected abstract void onTobagoCreate(Bundle savedInstanceState);

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
            case R.id.action_navigating_website:
                ViewUtils.makeToast(BaseAuthenticatedActivity.this, R.string.goto_website);
                return true;
            case R.id.action_refresh:
                if (swipeRefreshLayout != null)
                    swipeRefreshLayout.setRefreshing(true);
                onRefresh();
                return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Class cls;
        if (id == R.id.nav_account_info) {
            cls = AccountActivity.class;
        } else if (id == R.id.nav_qr_code_scan) {
            cls = QRCodeScanActivity.class;
        } else if (id == R.id.nav_enter_code) {
            cls = CreateQrCodeActivity.class;
        } else if (id == R.id.nav_sale_history) {
            cls = SaleHistoryActivity.class;
        } else if (id == R.id.nav_log_out) {
            checkLogout();
            cls = LoginActivity.class;
        } else {
            cls = MainActivity.class;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        if (this.getClass() == cls) {
            return true;
        } else {
            startActivity(new Intent(this, cls));
        }
        return true;
    }

    protected void checkLogout(){
        application.doLogout();
    }

    @Override
    public void onRefresh() {
        ViewUtils.makeToast(BaseAuthenticatedActivity.this, R.string.action_refresh_data);
        if (swipeRefreshLayout != null)
            swipeRefreshLayout.setRefreshing(false);
    }


}
