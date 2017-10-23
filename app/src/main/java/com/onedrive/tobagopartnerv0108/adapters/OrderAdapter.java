package com.onedrive.tobagopartnerv0108.adapters;

import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.onedrive.tobagopartnerv0108.R;
import com.onedrive.tobagopartnerv0108.activities.BaseAuthenticatedActivity;
import com.onedrive.tobagopartnerv0108.infrastructure.Order;
import com.onedrive.tobagopartnerv0108.infrastructure.TobagoApplication;
import com.onedrive.tobagopartnerv0108.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;


public class OrderAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
        implements View.OnClickListener {

    private static final int VIEW_ORDER = 0;
    private static final int VIEW_PROGRESS = 1;
    private static final String TAG = OrderAdapter.class.getSimpleName();
    private final OnLoadMoreListener onLoadMoreListener;

    private ArrayList<Order> orderList;
    private BaseAuthenticatedActivity activity;
    private TobagoApplication application;
    private Long uTimeInSeconds;
    private OnOrderClickListener listener;
    private final LinearLayoutManager linearLayoutManager;

    private boolean isProgressingMore, isLoadingMore, isMoreOrder;

    private int totalOrderCount, lastVisibleOrder;

    public OrderAdapter(BaseAuthenticatedActivity activity, OnLoadMoreListener onLoadMoreListener ,OnOrderClickListener listener,
                        LinearLayoutManager layoutManager) {
        this.activity = activity;
        application = activity.getTobagoApplication();
        uTimeInSeconds = application.getUTimeInSeconds();
        this.listener = listener;
        this.linearLayoutManager = layoutManager;
        this.onLoadMoreListener = onLoadMoreListener;
        this.orderList = new ArrayList<>();
        isLoadingMore = false; isMoreOrder = false;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                totalOrderCount = linearLayoutManager.getItemCount();
                lastVisibleOrder = linearLayoutManager.findLastVisibleItemPosition() + 1;
                // nếu đang không loading và totalOrderCount == lastVisibleOrder và có thêm order để load.
                if ((!isLoadingMore) && (totalOrderCount == lastVisibleOrder) && isMoreOrder) {
                    if (onLoadMoreListener != null) {
                        onLoadMoreListener.onLoadMore();
                    }
                }
            }
        });
    }

    @Override
    public int getItemViewType(int position) {
        return orderList.get(position) != null ? VIEW_ORDER : VIEW_PROGRESS;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater inflater = activity.getLayoutInflater();

        if (viewType == VIEW_ORDER) {
            View view = inflater.inflate(R.layout.list_item, parent, false);
            view.setOnClickListener(this);
            return new OrderViewHolder(view);
        } else {
            return new ProgressViewHolder(inflater.inflate(R.layout.item_progress, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OrderViewHolder) {

            Order order = getOrder(position);
            holder.itemView.setTag(order);

            OrderViewHolder tempHolder = (OrderViewHolder) holder;
            tempHolder.orderCode.setText(order.getOrderCode());
            // order time saved in second.
            tempHolder.orderTime.setText(ViewUtils.convertTimeMillisecToDate(Long.parseLong(order.getOrderTime())*1000, uTimeInSeconds*1000));
            tempHolder.orderStatus.setText(order.getOrderStatus());
            tempHolder.orderStatus.setTextColor(activity.getResources().getColor(order.getStatusColor()));
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size();
    }

    @Override
    public void onClick(View v) {
        if (v.getTag() instanceof Order) {
            Order order = (Order) v.getTag();
            listener.onOrderClicked(order);
        }
    }

    private static class OrderViewHolder extends RecyclerView.ViewHolder {

        private TextView orderCode, orderTime, orderStatus;
        public OrderViewHolder(View itemView) {
            super(itemView);

            orderCode = (TextView) itemView.findViewById(R.id.list_item_orderCode);
            orderTime = (TextView) itemView.findViewById(R.id.list_item_orderTime);
            orderStatus = (TextView) itemView.findViewById(R.id.list_item_orderStatus);
        }
    }

    private static class ProgressViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public ProgressViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar);
        }
    }

    public void addAllOrder(ArrayList<Order> list) {
        orderList.clear();
        orderList.addAll(list);
        notifyDataSetChanged();
    }

    public Order getOrder(int position) {
        return orderList.get(position);
    }

    public void addOrder(Order order) {
        orderList.add(order);
        notifyItemInserted(0);
    }

    public void removeOrder(Order order) {
        orderList.remove(order);
        notifyDataSetChanged();
    }


    public void removeAllOrder() {
        orderList.clear();
        notifyDataSetChanged();
    }

    public void addMoreOrders(List<Order> list) {
        orderList.addAll(list);
        notifyItemRangeChanged(0, orderList.size());
    }

    public boolean getProgressingMore() {
        return isProgressingMore;
    }

    public void setProgressingMore(final boolean isProgressing) {
        this.isProgressingMore = isProgressing;
        if (isProgressing) {
            new Handler().post(new Runnable() {
                @Override
                public void run() {
                    orderList.add(null);
                    notifyItemInserted(orderList.size() - 1);
                }
            });
        } else {
            orderList.remove(orderList.size() - 1);
            notifyItemRemoved(orderList.size());
        }
    }

    public boolean getLoadingMore() {
        return isLoadingMore;
    }

    public void setLoadingMore(boolean isLoadingMore) {
        this.isLoadingMore = isLoadingMore;
    }

    public void setIsMoreOrder(boolean isMoreOrder) {
        this.isMoreOrder = isMoreOrder;
    }

    public interface OnOrderClickListener {
        void onOrderClicked(Order order);
    }

    public interface OnLoadMoreListener {
        void onLoadMore();
    }
}
