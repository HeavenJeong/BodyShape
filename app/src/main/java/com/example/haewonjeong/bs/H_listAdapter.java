package com.example.haewonjeong.bs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by samsung on 2015-08-24.
 */
public class H_listAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private ArrayList<H_ListItem> data;
    private int layout;

    public H_listAdapter(Context context, int layout, ArrayList<H_ListItem> data)
    {
        this.inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.data = data;
        this.layout = layout;
    }
    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position).getName();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView == null)
        {
            convertView = inflater.inflate(layout, parent ,false);
        }

        H_ListItem listviewitem = data.get(position);

        ImageView icon = (ImageView)convertView.findViewById(R.id.h_listimage);
        icon.setImageResource(listviewitem.getIcon());

        TextView name = (TextView)convertView.findViewById(R.id.h_listname);
        name.setText(listviewitem.getName());

        return convertView;

    }
}
