package com.google.privatesharingapp.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.google.privatesharingapp.R;
import com.google.privatesharingapp.bean.DemoInfo;

/**
 * Demo List Adapter
 */

public class DemoListAdapter extends BaseAdapter {

    private DemoInfo[] demos;
    private Context mContext;

    public DemoListAdapter(Context context, DemoInfo[] demos) {  // Constructor
        super();
        this.demos = demos;
        this.mContext = context;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent) {
        // If convertView is null, inflate a new view from the demo_info_item layout file using LayoutInflater
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.demo_info_item, null);
        }
        // Get the TextView elements from the view
        TextView title = (TextView) convertView.findViewById(R.id.title);
        TextView desc = (TextView) convertView.findViewById(R.id.desc);
        // Set the text content for the TextView elements
        title.setText(demos[index].title);
        desc.setText(demos[index].desc);
        return convertView;
    }

    @Override
    public int getCount() {
        return demos.length;
    }  // Return the number of items in the list

    @Override
    public Object getItem(int index) { return demos[index]; }  // Return the data item at the specified position

    @Override
    public long getItemId(int id) { return id; }  // Return the ID of the data item at the specified position
}
