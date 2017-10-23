package com.onedrive.tobagopartnerv0108.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseActivity;
import com.onedrive.tobagopartnerv0108.activities.MainActivity;

public class WelcomeActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        TextView textView = (TextView) findViewById(R.id.activity_welcome_textView_welcome);

        // Chao mung ... den voi ung dung Tobago cho doi tac
        textView.setText("Chào mừng " + application.getBranchName().toUpperCase() +
                " đến với \n ứng dụng Tobago dành cho đối tác");

        // TODO: 13-Sep-17 load trước thong tin don hang, load anh banner ở đây rồi save vào data
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(WelcomeActivity.this, MainActivity.class));
                finish();
            }
        }, 3000);
    }
}
