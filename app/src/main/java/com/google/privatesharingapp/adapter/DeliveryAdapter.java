package com.google.privatesharingapp.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.privatesharingapp.MyOrderActivity;
import com.google.privatesharingapp.NewDeliveryActivity;
import com.google.privatesharingapp.R;
import com.google.privatesharingapp.bean.DeliveryBean;
import com.google.privatesharingapp.bean.TruckBean;
import com.kongzue.dialogx.dialogs.MessageDialog;
import com.kongzue.dialogx.interfaces.OnDialogButtonClickListener;

import java.util.ArrayList;
import java.util.List;

public class DeliveryAdapter extends RecyclerView.Adapter<DeliveryAdapter.ViewHolder> implements Filterable {
    private Context context;
    private List<DeliveryBean> data = new ArrayList<>();
    private List<DeliveryBean> mFilterList = new ArrayList<>();

    public DeliveryAdapter(Context context, List<DeliveryBean> data) {  // Constructor to initialize the adapter
        this.data = data;
        this.mFilterList = data;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {  // Creates a new ViewHolder instance for the RecyclerView items
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_truck, parent, false);

        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        // Get the DeliveryBean object at the specified position from the filtered list
        DeliveryBean deliveryBean = mFilterList.get(position);

        // Set the truck information and image in the ViewHolder
        holder.truckInfo.setText(deliveryBean.getName());
        holder.truckNum.setText(deliveryBean.getDateTime());
        holder.truckImage.setImageResource(R.drawable.truck_blue);

        // Set click event for each Item
        holder.truckShare.setOnClickListener(v -> {
            Intent intent = new Intent(context, MyOrderActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("delivery", deliveryBean);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    // Returns the total number of items in the RecyclerView
    public int getItemCount() {
        if (mFilterList == null) {
            return 0;
        }
        return mFilterList.size();
    }

    @Override
    // Implements the Filterable interface to provide filtering functionality
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    // If there is no filter query, use the original data
                    mFilterList = data;
                } else {
                    // If there is a filter query, create a new filtered list
                    List<DeliveryBean> filteredList = new ArrayList<>();
                    for (DeliveryBean infoBean : data) {
                        //Add matching rule as require
                        if (infoBean.getName().contains(charString)) {
                            filteredList.add(infoBean);
                        }
                    }

                    mFilterList = filteredList;
                }

                // Create a FilterResults object and assign the filtered list to it
                FilterResults filterResults = new FilterResults();
                filterResults.values = mFilterList;
                return filterResults;
            }

            @SuppressLint("NotifyDataSetChanged")
            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                mFilterList = (ArrayList<DeliveryBean>) filterResults.values;
                //Refresh data
                notifyDataSetChanged();
            }
        };
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        private final ImageView truckImage, truckShare;
        public final TextView truckNum, truckInfo;

        public TruckBean infoBean;

        // Constructor to initialize the views in the ViewHolder
        public ViewHolder(View view) {
            super(view);
            this.mView = view;
            this.truckImage = itemView.findViewById(R.id.truckImage);
            this.truckShare = itemView.findViewById(R.id.truckShare);
            this.truckNum = itemView.findViewById(R.id.truckNum);
            this.truckInfo = itemView.findViewById(R.id.truckInfo);
        }
    }
}