package com.enpoints;

import java.util.List;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.enpoints.frutaendpoint.model.Fruta;
 
public class CustomAdapter extends BaseAdapter {
    Context context;
    List<Fruta> rowItems;
 
    public CustomAdapter(Context context, List<Fruta> items) {
        this.context = context;
        this.rowItems = items;
    }
 
    /*private view holder class*/
    private class ViewHolder {
        TextView txtId;
        TextView txtTitle;
        TextView txtDesc;
    }
 
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
 
        LayoutInflater mInflater = (LayoutInflater)
            context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.list_item, null);
            holder = new ViewHolder();
            holder.txtDesc = (TextView) convertView.findViewById(R.id.textView2);
            holder.txtTitle = (TextView) convertView.findViewById(R.id.textView1);
            holder.txtId = (TextView) convertView.findViewById(R.id.textView3);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }
 
        Fruta rowItem = (Fruta) getItem(position);
 
        holder.txtDesc.setText(rowItem.getDescripcion());
        holder.txtTitle.setText(rowItem.getNombre());
        holder.txtId.setText(rowItem.getId());
 
        return convertView;
    }
 
    @Override
    public int getCount() {
        return rowItems.size();
    }
 
    @Override
    public Object getItem(int position) {
        return rowItems.get(position);
    }
 
    @Override
    public long getItemId(int position) {
        return rowItems.indexOf(getItem(position));
    }
}