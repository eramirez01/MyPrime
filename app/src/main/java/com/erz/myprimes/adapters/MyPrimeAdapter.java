package com.erz.myprimes.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.erz.myprimes.R;

public class MyPrimeAdapter extends BaseAdapter {

    Context context;
    int[] data;

    public MyPrimeAdapter(Context context, int size) {
        this.context = context;
        this.data = new int[size];
    }

    public static class ViewHolder {
        public TextView name;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        if (view == null) {
            view = ((Activity)context).getLayoutInflater().inflate(R.layout.block, viewGroup, false);
            viewHolder = new ViewHolder();
            viewHolder.name = (TextView) view.findViewById(R.id.name);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int item = data[i];
        if (item == 0) {
            viewHolder.name.setTextColor(Color.BLACK);
            viewHolder.name.setBackgroundResource(R.drawable.dialog_bg);
        } else {
            viewHolder.name.setTextColor(Color.WHITE);
            viewHolder.name.setBackgroundResource(item);
        }

        viewHolder.name.setText((i + 1) + "");
        return view;
    }

    public void setData(int size) {
        try {
            this.data = new int[size];
        } catch (OutOfMemoryError e) {
            System.gc();
            this.data = new int[0];
            Toast.makeText(context, "Out of memory, try again", Toast.LENGTH_SHORT).show();
        }
        notifyDataSetChanged();
    }

    public void setItemColor(int i, int color) {
        if (i < data.length) {
            data[i] = color;
        }
    }
}
