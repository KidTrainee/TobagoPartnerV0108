package com.onedrive.tobagopartnerv0108.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.fragments.ChangeInfoDialogFragment;
import com.onedrive.tobagopartnerv0108.fragments.ChangePasswordDialogFragment;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;
import com.onedrive.tobagopartnerv0108.utils.expandable_layout.ExpandableLinearLayout;


public class UserInfoActivity extends BaseAuthenticatedActivity implements View.OnClickListener {

    ExpandableLinearLayout storeInfo, personalInfo;
    @Override
    protected void onTobagoCreate(Bundle savedInstanceState) {
        shouldShowUpNavigation = true;
        shouldShowActivityLabel = true;
        setContentView(R.layout.activity_user_info);

        personalInfo = (ExpandableLinearLayout) findViewById(R.id.content_user_info_expandableLinearLayout_personal_info);

        (findViewById(R.id.activity_account_info_cardView_change_password)).setOnClickListener(this);
        (findViewById(R.id.activity_account_info_textView_changeInfo)).setOnClickListener(this);
        (findViewById(R.id.activity_user_info_linearLayout_personal_info_title)).setOnClickListener(this);
        (findViewById(R.id.activity_user_info_circleImageView_avatar)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        switch (view.getId()) {
            case R.id.activity_account_info_cardView_change_password:
                ChangePasswordDialogFragment.newInstance().show(fragmentTransaction, "");
                break;
            case R.id.activity_account_info_textView_changeInfo:
                ChangeInfoDialogFragment.newInstance().show(fragmentTransaction, "");
                break;
            case R.id.activity_user_info_linearLayout_personal_info_title:
                personalInfo.toggle();
                break;
            case R.id.activity_user_info_circleImageView_avatar:
                ViewUtils.makeToast(UserInfoActivity.this, R.string.text_change_avatar);
                break;
        }
    }
}
