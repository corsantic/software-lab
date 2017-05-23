package com.kocaeli.houseviewer.adapter;

import java.util.List;

import com.kocaeli.houseviewer.R;
import com.kocaeli.houseviewer.entity.House;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<House>
{
    public CustomAdapter(List<House> data, Context context)
    {
        super(context, R.layout.row_item, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        House house = getItem(position);

        ViewHolder viewHolder;

        if (convertView == null)
        {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.row_item, parent, false);

            viewHolder.description = (TextView) convertView.findViewById(R.id.name);
            viewHolder.type = (TextView) convertView.findViewById(R.id.type);
            viewHolder.price = (TextView) convertView.findViewById(R.id.price);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.description.setText(house.getDescription());
        viewHolder.type.setText(house.getType());
        viewHolder.price.setText(house.getPrice().toString());

        return convertView;
    }

    public static class ViewHolder
    {
        TextView description;
        TextView type;
        TextView price;
    }


}