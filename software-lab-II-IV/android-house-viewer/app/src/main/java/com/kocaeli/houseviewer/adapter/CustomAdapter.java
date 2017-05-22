package com.kocaeli.houseviewer.adapter;

import java.util.List;

import com.kocaeli.houseviewer.R;
import com.kocaeli.houseviewer.entity.House;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<House>
{
    private static class ViewHolder
    {
        TextView txtName;
        TextView txtType;
        TextView txtVersion;
        ImageView info;
    }

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

            viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
            viewHolder.txtType = (TextView) convertView.findViewById(R.id.type);
            viewHolder.txtVersion = (TextView) convertView.findViewById(R.id.version_number);
            viewHolder.info = (ImageView) convertView.findViewById(R.id.item_info);

            convertView.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        viewHolder.txtName.setText(house.getDescription());
        viewHolder.txtType.setText(house.getType());
        viewHolder.txtVersion.setText(house.getPrice().toString());

        return convertView;
    }


}