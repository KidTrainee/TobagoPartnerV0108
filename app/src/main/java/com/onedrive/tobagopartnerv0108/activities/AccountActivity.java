package com.onedrive.tobagopartnerv0108.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;

public class AccountActivity extends BaseAuthenticatedActivity implements View.OnClickListener {

    private ImageView avatar;

    @Override
    protected void onTobagoCreate(Bundle savedInstanceState) {
        shouldShowUpNavigation = true;
        shouldShowActivityLabel = true;
        setContentView(R.layout.activity_account);

        avatar = (ImageView) findViewById(R.id.content_account_info_circleImageView_avatar);
        avatar.setOnClickListener(this);

        (findViewById(R.id.content_account_cardView_userInfo)).setOnClickListener(this);
        (findViewById(R.id.content_account_cardView_contact)).setOnClickListener(this);
        (findViewById(R.id.content_account_cardView_help)).setOnClickListener(this);
        (findViewById(R.id.content_account_cardView_logout)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        if (view.equals(avatar)) {
            ViewUtils.makeToast(AccountActivity.this, R.string.text_change_avatar);
        }

        switch (view.getId()) {
            case R.id.content_account_cardView_userInfo:
                startActivity(new Intent(this, UserInfoActivity.class));
                break;
            case R.id.content_account_cardView_contact:
                ViewUtils.makeToast(AccountActivity.this, R.string.action_call);
                break;
            case R.id.content_account_cardView_help:
                ViewUtils.makeToast(AccountActivity.this, R.string.contact_consultant);
                break;
            case R.id.content_account_cardView_logout:
                application.doLogout();
                finish();
                break;
        }
    }
}
