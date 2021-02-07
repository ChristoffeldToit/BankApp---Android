package com.example.sisonkebankapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {


    //Context
    private Context context;
    //Integer array.
    private int digit[] = new int[] {};

    //Constructor which has two parameters.
    //We will initialize our objects for context and integer array using these parameters.
    public ListAdapter (Context context, int digit[]) {

        this.context = context;
        this.digit = digit;
    }

    @Override
    public int getViewTypeCount()
    {
        return getCount();
    }
    @Override
    public int getItemViewType(int position)
    {

        return position;
    }

    @Override
    public int getCount()
    {
        return digit.length;
    }

    @Override
    public Object getItem(int position)
    {
        return digit[position];
    }

    @Override
    public long getItemId(int position)
    {
        return 0;
    }

    /*
        Using this method, compiler will first inflate the layout file listview_item.xml
        Now every children of listview will get the user interface developed by the  listview_item.xml file.
        After this, compiler will set the value of the textview.
        Compiler will use integer array to populate the values in the textview.
     */

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_item, null, true);

            holder.tvProduct = (TextView) convertView.findViewById(R.id.tv);

            convertView.setTag(holder);
        }else {
            // the getTag returns the viewHolder object set as a tag to the view
            holder = (ViewHolder)convertView.getTag();
        }

        holder.tvProduct.setText("Item Number : "+ digit[position]);

        return convertView;
    }

    private class ViewHolder {

        protected TextView tvProduct;

    }

}