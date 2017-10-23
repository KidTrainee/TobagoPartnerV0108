package com.onedrive.tobagopartnerv0108.login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;
import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseActivity;
import com.onedrive.tobagopartnerv0108.utils.JsonKeys;
import com.onedrive.tobagopartnerv0108.utils.PostStringRequest;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class ForgetPasswordActivity extends BaseActivity {

    public static final String EXTRA_PHONE = "forget_pass_activity_extra_phone";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        final EditText edt_phone = (EditText) findViewById(R.id.activity_forget_password_editText_phone);
        Button sendInfo = (Button) findViewById(R.id.activity_forget_password_button_sendInfo);
        sendInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final String phone = edt_phone.getText().toString();
                if (phone.isEmpty()) {
                    setErrorEditText(edt_phone, R.string.error_missing_phone);
                }
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            if (jo.getString(JsonKeys.CODE).equals("0")) {
                                Log.d(TAG, "onResponse: code = " + jo.getString(JsonKeys.CODE));
                                // check mail de lay mat khau moi
                                ViewUtils.makeToast(ForgetPasswordActivity.this, R.string.check_mail_to_get_code);
                                // chuyển về màn nhập code.
                                startActivity(new Intent(ForgetPasswordActivity.this, ConfirmPasswordActivity.class)
                                        .putExtra(ForgetPasswordActivity.EXTRA_PHONE, phone));
                            } else {
                                // loi, yeu cau gui lai, nhap lai sdt hoac lien he tu van
                                ViewUtils.makeToast(ForgetPasswordActivity.this, R.string.error_send_code_pass);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                Log.d(TAG, "onClick: PHONE = " + JsonKeys.PHONE + "\n phone = " + phone);
                application.getQueue().add(new PostStringRequest(
                        new String[]{JsonKeys.PHONE},
                        new String[]{phone},
                        getResources().getString(R.string.linkSendCodePass),
                        listener));
            }
        });
    }
}
