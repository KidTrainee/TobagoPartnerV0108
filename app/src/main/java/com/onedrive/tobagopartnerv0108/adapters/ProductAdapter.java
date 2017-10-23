package com.onedrive.tobagopartnerv0108.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.infrastructure.Product;

import java.util.ArrayList;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ViewHolder>{

    private ArrayList<Product> productList;
    private LayoutInflater inflater;

    public ProductAdapter(LayoutInflater inflater, ArrayList<Product> productList) {
        this.productList = productList;
        this.inflater = inflater;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.item_list_product, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Product product = getProduct(position);

        holder.productName.setText(product.getProductName());
        holder.productNumber.setText(product.getProductNumber());
        holder.productPrice.setText(product.getTotal());
        holder.productCashbackCustomer.setText(product.getPercentCashback());
        holder.productCashbackTobago.setText(product.getTobagoPercentCashback());
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        public TextView productName, productNumber, productPrice, productCashbackCustomer, productCashbackTobago;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = (TextView) itemView.findViewById(R.id.item_list_product_productName);
            productNumber = (TextView) itemView.findViewById(R.id.item_list_product_productNumber);
            productPrice = (TextView) itemView.findViewById(R.id.item_list_product_productTotal);
            productCashbackCustomer = (TextView) itemView.findViewById(R.id.item_list_product_productCashBackCustomer);
            productCashbackTobago = (TextView) itemView.findViewById(R.id.item_list_product_productCashBackTobago);
        }
    }

    private Product getProduct(int position) {
        return productList.get(position);
    }

    public void addProduct(Product product) {
        productList.add(product);
        notifyDataSetChanged();
    }

    public void removeProduct(Product product) {
        productList.remove(product);
        notifyDataSetChanged();
    }

    public void addAll(ArrayList<Product> products) {
        productList.addAll(products);
        notifyDataSetChanged();
    }

    public void removeAll() {
        productList.clear();
        notifyDataSetChanged();
    }

}
