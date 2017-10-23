package com.onedrive.tobagopartnerv0108.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseActivity;
import com.onedrive.tobagopartnerv0108.utils.JsonKeys;
import com.onedrive.tobagopartnerv0108.utils.PostStringRequest;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ConfirmPasswordActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_confirmCode;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_password);

        (findViewById(R.id.activity_confirm_password_button_send)).setOnClickListener(this);
        (findViewById(R.id.activity_confirm_password_textView_reSend)).setOnClickListener(this);

        edt_confirmCode = (EditText) findViewById(R.id.activity_confirm_password_edt_confirmCode);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.activity_confirm_password_button_send:
                doSendPass();
                break;
            case R.id.activity_confirm_password_textView_reSend:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
        finish();
    }

    private void doSendPass() {
        String code = edt_confirmCode.getText().toString();
        if (code.isEmpty()) {
            setErrorEditText(edt_confirmCode, R.string.missing_confirm_code);
        } else {
            Response.Listener<String> listener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jo = new JSONObject(response);
                        if (jo.getString(JsonKeys.CODE).equals("0")) {
                            // check mail de lay lai pass
                            ViewUtils.makeToast(ConfirmPasswordActivity.this, R.string.send_pass_success);
                            startActivity(new Intent(ConfirmPasswordActivity.this, LoginActivity.class));
                            finish();
                        } else {
                            // loi xac nhan tai khoan
                            ViewUtils.makeToast(ConfirmPasswordActivity.this, R.string.error_send_pass);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            };
            application.getQueue().add(new PostStringRequest(
                    new String[]{JsonKeys.PHONE, JsonKeys.CODE_FORGET_PASS},
                    new String[]{getIntent().getStringExtra(ForgetPasswordActivity.EXTRA_PHONE), code},
                    getResources().getString(R.string.linkSendPass),
                    listener));
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
