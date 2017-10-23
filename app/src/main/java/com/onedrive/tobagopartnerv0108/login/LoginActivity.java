package com.onedrive.tobagopartnerv0108.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.android.volley.Response;
import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseActivity;
import com.onedrive.tobagopartnerv0108.utils.JsonKeys;
import com.onedrive.tobagopartnerv0108.utils.PostStringRequest;
import com.onedrive.tobagopartnerv0108.utils.TobagoConstants;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;


public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private EditText edt_pass, edt_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(com.onedrive.tobagopartnerv0108.R.layout.activity_login);
        edt_phone = (EditText) findViewById(R.id.activity_login_editText_phone);
        edt_pass = (EditText) findViewById(R.id.activity_login_editText_password);

        (findViewById(R.id.activity_login_button_login)).setOnClickListener(this);
        (findViewById(R.id.activity_login_textView_forget_password)).setOnClickListener(this);
        (findViewById(R.id.activity_login_textView_register)).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.activity_login_button_login:
                Log.d(TAG, "onClick: I'm here");
                checkLogin();
                break;
            case R.id.activity_login_textView_register:
                ViewUtils.makeToast(LoginActivity.this, R.string.goto_website);
                break;
            case R.id.activity_login_textView_forget_password:
                startActivity(new Intent(this, ForgetPasswordActivity.class));
                break;
        }
    }

    private void checkLogin() {
        String phone = edt_phone.getText().toString();
        String pass = edt_pass.getText().toString();

        Response.Listener<String> listener = new Response.Listener<String>() {

            public void onResponse(String response) {
                try {
                    JSONObject jo = new JSONObject(response);
                    Log.d(TAG, "onResponse: code = " + jo.getString(JsonKeys.CODE));
                    switch (Integer.parseInt(jo.getString(JsonKeys.CODE))) {
                        case 0:
                            doLogin(jo);
                            break;
                        case 1:
                            // thông tin tài khoản sai
                            ViewUtils.makeToast(LoginActivity.this,R.string.wrong_info);
                            break;
                        case 2:
                            // giá trị gửi lên rỗng
                            ViewUtils.makeToast(LoginActivity.this, R.string.missing_data);
                            break;
                    }
                } catch (JSONException e) {
                    Log.d(TAG, "lỗi JSON parsing: e = " + e);
                }
            }
        };

        application.getQueue().add(new PostStringRequest(
                new String[]{JsonKeys.PHONE, JsonKeys.PASS},
                new String[]{phone, pass},
                TobagoConstants.LINK_LOGIN,
                listener));
    }

    private void doLogin(JSONObject jo) throws JSONException {
        JSONObject jsonManufacturer = jo.getJSONObject(JsonKeys.MANUFACTURER);
        // đăng nhập thành công
        ViewUtils.makeToast(LoginActivity.this,R.string.login_success);
        // lấy tên (cho màn hình welcome) và Id người dùng.
        application.setBranchName(jsonManufacturer.getString(JsonKeys.BRANCH_NAME));
        application.setTaxCode(jsonManufacturer.getString(JsonKeys.TAX_CODE));
        application.setIdCity(jsonManufacturer.getString(JsonKeys.ID_CITY));
        application.setShop(jsonManufacturer.getString(JsonKeys.SHOP));
        application.setLink(jsonManufacturer.getString(JsonKeys.LINK));
        application.setFullName(jsonManufacturer.getString(JsonKeys.FULL_NAME));
        application.setSex(jsonManufacturer.getString(JsonKeys.SEX));
        application.setEmail(jsonManufacturer.getString(JsonKeys.EMAIL));
        application.setFone(jsonManufacturer.getString(JsonKeys.PHONE));
        application.setAddress(jsonManufacturer.getString(JsonKeys.ADDRESS));
        application.setInfo(jsonManufacturer.getString(JsonKeys.INFO));
        application.setOrder(jsonManufacturer.getString(JsonKeys.NUMBER_ORDER));
        application.setLogo(jsonManufacturer.getString(JsonKeys.LOGO));

        // lưu accessToken vào sharedPreferences.
        application.setAccessToken(jsonManufacturer.getString(JsonKeys.ACCESS_TOKEN));
        application.setManufacturerId(jsonManufacturer.getString(JsonKeys.ID));
        application.setUTimeInSeconds(Long.parseLong(jsonManufacturer.getJSONObject(JsonKeys.MODIFIED).getString(JsonKeys.USEC)));

        Log.d(TAG, "doLogin: Manufacturer = " + application.getManufacturerId());
        // todo : go to main activity.
        startActivity(new Intent(LoginActivity.this, WelcomeActivity.class));
    }
}
