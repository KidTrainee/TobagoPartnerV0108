package com.onedrive.tobagopartnerv0108.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.volley.Response;
import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseAuthenticatedActivity;
import com.onedrive.tobagopartnerv0108.utils.JsonKeys;
import com.onedrive.tobagopartnerv0108.utils.PostStringRequest;
import com.onedrive.tobagopartnerv0108.utils.TobagoConstants;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePasswordDialogFragment extends BaseDialogFragment {

    private BaseAuthenticatedActivity activity;

    public static ChangePasswordDialogFragment newInstance() {

        Bundle args = new Bundle();

        ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseAuthenticatedActivity) activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_dialog_change_password, container, false);
        final TextInputLayout oldPassLayout = (TextInputLayout) view.findViewById(R.id.fragment_dialog_change_password_textInputLayout_oldPassword);
        final TextInputLayout newPassLayout = (TextInputLayout) view.findViewById(R.id.fragment_dialog_change_password_textInputLayout_newPassword);
        final TextInputLayout confirmNewPassLayout = (TextInputLayout) view.findViewById(R.id.fragment_dialog_change_password_textInputLayout_confirm_newPassword);

        Button doneBtn = (Button) view.findViewById(R.id.fragment_dialog_change_password_button_done);
        Button cancelBtn = (Button) view.findViewById(R.id.fragment_dialog_change_password_button_cancel);
        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String oldPass = oldPassLayout.getEditText().getText().toString();
                String newPass = newPassLayout.getEditText().getText().toString();
                String confirmNewPass = confirmNewPassLayout.getEditText().getText().toString();
                // check input validation.
                if (oldPass.isEmpty()) {
                    oldPassLayout.setError(activity.getResources().getString(R.string.error_missing_oldPass));
                } else if (newPass.isEmpty()) {
                    newPassLayout.setError(activity.getResources().getString(R.string.error_missing_newPass));
                } else if (!confirmNewPass.equals(newPass)) {
                    confirmNewPassLayout.setError(activity.getResources().getString(R.string.error_password_not_match));
                } else if (newPass.length()<=6) {
                    newPassLayout.setError(activity.getResources().getString(R.string.error_short_password));
                }
                // finish check input validation.
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jo = new JSONObject(response);
                            int code = Integer.parseInt(jo.getString(JsonKeys.CODE));
                            switch (code) {
                                case -1:
                                    // data send error.
                                    Log.d(TAG, "onResponse: data send error. code = " + code);
                                    break;
                                case 0:
                                    // success
                                    doChangePassword();
                                    break;
                                case 1:
                                    // error accessToken
                                    Log.d(TAG, "onResponse: error accessToken. code = " + code);
                                    ViewUtils.makeToast(activity, R.string.invalid_access_token);
                                    activity.getTobagoApplication().doLogout();
                                    break;
                                case 2:
                                    // error password and password again
                                    Log.d(TAG, "onResponse: error password and password again. code = " + code);
                                    ViewUtils.makeToast(activity, R.string.error_password_not_match);
                                    break;
                                case 3:
                                    // error passOld
                                    Log.d(TAG, "onResponse: error old pass. code = " + code);
                                    ViewUtils.makeToast(activity, R.string.error_old_pass_not_match);
                                    break;
                                default:
                                    Log.d(TAG, "onResponse - change password - not support");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                activity.getTobagoApplication().getQueue().add(new PostStringRequest(
                        new String[] {JsonKeys.ACCESS_TOKEN, JsonKeys.PASS_OLD, JsonKeys.PASSWORD, JsonKeys.PASSWORD_AGAIN},
                        new String[] {activity.getTobagoApplication().getAccessToken(), oldPass, newPass, confirmNewPass},
                        TobagoConstants.LINK_CHANGE_PASS,
                        listener));

            }
        });
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        getDialog().setTitle(getContext().getResources().getString(R.string.change_password));
        return view;
    }

    private void doChangePassword() {
        ViewUtils.makeToast(activity, R.string.change_password_success);
        activity.getTobagoApplication().doLogout();
        activity.finish();
    }
}
