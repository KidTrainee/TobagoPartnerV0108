package com.onedrive.tobagopartnerv0108.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseAuthenticatedActivity;
import com.squareup.picasso.Picasso;

public class QRCodeDialogFragment extends BaseDialogFragment {

    private static final String QR_CODE_URL_KEY = "qr_code_url_key";
    private String qrCodeUrl;
    private BaseAuthenticatedActivity activity;

    public static QRCodeDialogFragment newInstance(String qrCodeUrl) {

        Bundle args = new Bundle();
        args.putString(QR_CODE_URL_KEY, qrCodeUrl);
        QRCodeDialogFragment fragment = new QRCodeDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseAuthenticatedActivity) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.qrCodeUrl = getArguments().getString(QR_CODE_URL_KEY);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        getDialog().setTitle(activity.getResources().getString(R.string.qr_code_dialog_title));

        View view = inflater.inflate(R.layout.fragment_dialog_qr_code_image, container, false);

        // put qrCode image into ImageView
        Picasso.with(activity).load(qrCodeUrl).into((ImageView) view.findViewById(R.id.fragment_dialog_qr_code_image));

        return view;
    }
}
