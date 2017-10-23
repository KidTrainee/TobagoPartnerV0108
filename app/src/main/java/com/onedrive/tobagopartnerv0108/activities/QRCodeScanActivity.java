package com.onedrive.tobagopartnerv0108.activities;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.onedrive.tobagopartnerv0108.R;
import com.google.zxing.Result;
import com.onedrive.tobagopartnerv0108.utils.PermissionRequest;
import com.onedrive.tobagopartnerv0108.utils.TobagoConstants;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class QRCodeScanActivity extends BaseAuthenticatedActivity {
    private static final int SCAN_REQUEST_CODE = 0;
    private ZXingScannerView mScannerView;
    private TextView text;

    @Override
    protected void onTobagoCreate(Bundle savedInstanceState) {
        shouldShowUpNavigation = true;
        shouldShowActivityLabel = true;
        setContentView(R.layout.activity_qr_code_scan);

        requestCameraPermission();

        mScannerView = (ZXingScannerView) findViewById(R.id.fragment_qr_code_zxing_scanner_view);
        mScannerView.setResultHandler(new ZXingScannerView.ResultHandler() {
            @Override
            public void handleResult(Result result) {
                mScannerView.stopCamera();
                Log.d(TAG, "handleResult: result.getText() = " + result.getText());
                startActivity(new Intent(QRCodeScanActivity.this, OrderDetailActivity.class).putExtra(TobagoConstants.EXTRA_QR_RESULT, result.getText()));
            }
        });
        startScan();
    }

    private void requestCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PermissionRequest.with(this)
                    .permissions(Manifest.permission.CAMERA)
                    .rationale(R.string.camera_permission_rationale)
                    .submit();
        }
    }

    private void startScan() {
        mScannerView.startCamera();
     }

    @Override
    protected void onResume() {
        super.onResume();
        mScannerView.startCamera();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mScannerView.stopCamera();
    }
}
