package com.example.vexeonline.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vexeonline.R;
import com.example.vexeonline.model.LoaiVe;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

// Custom từng dòng listview trên thanh công cụw

public class LoaiVeAdapter extends BaseAdapter {
    private ArrayList<LoaiVe> arrayListLoaive;
    private Context context;

    public LoaiVeAdapter(ArrayList<LoaiVe> arrayListLoaive, Context context) {
        this.arrayListLoaive = arrayListLoaive;
        this.context = context;
    }

    public class ViewHolder {
        TextView txtTenloaisp;
        ImageView imgLoaisp;
    }

    @Override
    public int getCount() {
        return arrayListLoaive.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListLoaive.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dong_listview_loaive, viewGroup, false);
            viewHolder.txtTenloaisp = (TextView) view.findViewById(R.id.textviewLoaive);
            viewHolder.imgLoaisp = (ImageView) view.findViewById(R.id.imageviewLoaive);
            view.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) view.getTag();
        }
        LoaiVe loaiVe = (LoaiVe) getItem(position);
        viewHolder.txtTenloaisp.setText(loaiVe.getTenloaisp());
        Picasso.get().load(loaiVe.getHinhanhsp()).placeholder(R.drawable.noimg).error(R.drawable.error).into(viewHolder.imgLoaisp);
        return view;

       /* LayoutInflater layoutInflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view=layoutInflater.inflate(layout,null);
        TextView tvTenloaisp=view.findViewById(R.id.textviewLoaive);
        ImageView imgloaisp=view.findViewById(R.id.imageviewLoaive);
        tvTenloaisp.setText(arrayListLoaive.get(position).getTenloaisp());
        Picasso.get().load(arrayListLoaive.get(position).getHinhanhsp()).centerCrop().resize(150,150).into(imgloaisp);
        return view;*/
    }
}
