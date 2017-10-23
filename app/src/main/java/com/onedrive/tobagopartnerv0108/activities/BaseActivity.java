package com.onedrive.tobagopartnerv0108.activities;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.infrastructure.TobagoApplication;
import com.onedrive.tobagopartnerv0108.login.LoginActivity;

public abstract class BaseActivity extends LogcatActivity {

    protected String TAG = "MyTest--" + getClass().getSimpleName();

    protected TobagoApplication application;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);

        if (!checkInternetConnection()) {

        }
        if (application == null) {
            application = (TobagoApplication) getApplication();
        }
    }

    public TobagoApplication getTobagoApplication() {
        if (application == null) {
            application = (TobagoApplication) getApplication();
        }
        return application;
    }

    // ViewUtils

    protected void setErrorEditText(EditText edt, @StringRes int string) {
        edt.setError(getResources().getString(string));
    }
    public boolean checkInternetConnection() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager.getActiveNetworkInfo() == null
                || !connectivityManager.getActiveNetworkInfo().isAvailable()
                || !connectivityManager.getActiveNetworkInfo().isConnected()) {
            new AlertDialog.Builder(this)
                    .setMessage(getResources().getString(R.string.check_wifi_request))
                    .setPositiveButton(getResources().getString(R.string.agree), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                            finish();
                        }
                    })
                    .setNegativeButton(getResources().getString(R.string.exit), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startActivity(new Intent(BaseActivity.this, LoginActivity.class));
                        }
                    })
                    .show();
            return false;
        } else {
            return true;
        }
    }
}
