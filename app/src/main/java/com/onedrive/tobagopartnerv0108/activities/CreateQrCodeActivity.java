package com.onedrive.tobagopartnerv0108.activities;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;

import org.json.JSONObject;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.onedrive.tobagopartnerv0108.R;

import org.json.JSONException;

public class CreateQrCodeActivity extends BaseAuthenticatedActivity implements View.OnClickListener {

    private Button sendBtn;
    private TextInputLayout moneyAmountTextInputLayout;
    private ImageView qrImage;

    @Override
    protected void onTobagoCreate(Bundle savedInstanceState) {

        shouldShowUpNavigation = true;
        shouldShowActivityLabel = true;

        setContentView(R.layout.activity_create_qr_code);

        moneyAmountTextInputLayout = (TextInputLayout) findViewById(R.id.activity_create_code_textInputLayout_moneyAmount);
        qrImage = (ImageView) findViewById(R.id.activity_create_code_iv_qr_image);
        sendBtn = (Button) findViewById(R.id.content_enter_user_id_button_send);

        sendBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.equals(sendBtn)) {
            if (moneyAmountTextInputLayout.getEditText().getText().toString().isEmpty()) {
                moneyAmountTextInputLayout.setError(getResources().getString(R.string.error_missing_money_amount));
            } else {
                doCreateQrCode();
            }
        }
    }

    private void doCreateQrCode() {

        try {
            JSONObject jo = new JSONObject();
            jo.put("name", application.getBranchName());
            jo.put("id",application.getManufacturerId());
            jo.put("money", moneyAmountTextInputLayout.getEditText().getText().toString());

            qrImage.setImageBitmap(setIMG(jo.toString()));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void hideKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    private Bitmap setIMG(String cont) {
        BitMatrix bitMatrix = null;
        Bitmap bmp = null;
        QRCodeWriter writer = new QRCodeWriter();
        try {
            bitMatrix = writer.encode(cont, BarcodeFormat.QR_CODE, 250, 250);
            int width = bitMatrix.getWidth();
            int height = bitMatrix.getHeight();
            bmp = Bitmap.createBitmap(width, height, Bitmap.Config.RGB_565);
            for (int x = 0; x < width; x++) {
                for (int y = 0; y < height; y++) {
                    bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
                }
            }

        } catch (WriterException e) {
            e.printStackTrace();
        }
        return bmp;
    }

}
