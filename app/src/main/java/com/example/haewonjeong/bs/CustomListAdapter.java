package com.example.haewonjeong.bs;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import java.util.List;

public class CustomListAdapter extends ArrayAdapter<CustomListInfo> {
    private List<CustomListInfo> mItems;
    private int mResourceId;
    private Context mContext;

    public CustomListAdapter(Context context, int textViewResourceId, List<CustomListInfo> objects)
    {
        super(context, textViewResourceId, objects);

        this.mContext = context;
        this.mItems = objects;
        this.mResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        CustomListItemView mItemView;
        if(convertView == null)
        {
            mItemView = new CustomListItemView(mContext,null);
        }
        else
        {
            mItemView = (CustomListItemView)convertView;
        }
        CustomListInfo msg = mItems.get(position);
        mItemView.setMessage(msg);
        convertView = mItemView;

        return convertView;

    }

}
