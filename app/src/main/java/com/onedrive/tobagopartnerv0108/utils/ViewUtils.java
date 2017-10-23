package com.onedrive.tobagopartnerv0108.utils;

import android.content.Context;
import android.support.annotation.StringRes;
import android.support.v7.widget.LinearSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SnapHelper;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ViewUtils {

    public static void applySnapHelper(RecyclerView recyclerView) {
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(recyclerView);
    }

    public static void makeToast(Context context, @StringRes int message) {
        Toast.makeText(context, context.getResources().getString(message), Toast.LENGTH_SHORT).show();
    }

    public static String convertTimeMillisecToDate(Long timeInMillisec, Long uTimeInMillisec) {
        Date orderDate = new Date(timeInMillisec + uTimeInMillisec);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return df.format(orderDate);
    }

    public static void checkValidation(Context context, EditText[] edts, @StringRes int[] errors) {
        for (int i = 0; i < edts.length; i++) {
            EditText edt = edts[i];
            if (edt.getText().toString().isEmpty()) {
                edt.setError(context.getResources().getString(errors[i]));
            }
        }
    }
}
