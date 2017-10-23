package com.onedrive.tobagopartnerv0108.fragments;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseAuthenticatedActivity;
import com.onedrive.tobagopartnerv0108.adapters.ProductAdapter;
import com.onedrive.tobagopartnerv0108.infrastructure.Product;

import java.util.ArrayList;

public class ProductDetailsDialogFragment extends BaseDialogFragment {

    private static final String PRODUCT_ARRAY_LIST = "product_array_list";
    private BaseAuthenticatedActivity activity;
    private ArrayList<Product> productList;

    public static ProductDetailsDialogFragment newInstance(ArrayList<Product> productList) {

        Bundle args = new Bundle();
        args.putParcelableArrayList(PRODUCT_ARRAY_LIST, productList);
        ProductDetailsDialogFragment fragment = new ProductDetailsDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.activity = (BaseAuthenticatedActivity) activity;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        productList = getArguments().getParcelableArrayList(PRODUCT_ARRAY_LIST);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        getDialog().setTitle(activity.getResources().getString(R.string.product_list));

        View view = inflater.inflate(R.layout.fragment_dialog_product_details, container, false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.fragment_dialog_product_details_recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        ProductAdapter adapter = new ProductAdapter(activity.getLayoutInflater(), productList);
        recyclerView.setAdapter(adapter);
        // done clicked.
        (view.findViewById(R.id.fragment_dialog_product_details_textView_done)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return view;
    }

}
