package com.example.vexeonline.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vexeonline.R;
import com.example.vexeonline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class XeGiuongNamAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Sanpham> arraySanpham;

    public XeGiuongNamAdapter(Context context, ArrayList<Sanpham> arraySanpham) {
        this.context = context;
        this.arraySanpham = arraySanpham;
    }

    public class ViewHolder {
        public TextView txtXeGiuongNam;
        public TextView txtGiaVe;
        public TextView txtMoTa;
        public ImageView imgXeGiuongNam;
    }


    @Override
    public int getCount() {
        return arraySanpham.size();
    }

    @Override
    public Object getItem(int position) {
        return arraySanpham.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (view == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            view = inflater.inflate(R.layout.item_xe_giuong_nam, null);
            viewHolder.imgXeGiuongNam = view.findViewById(R.id.imgXeGiuongNam);
            viewHolder.txtXeGiuongNam = view.findViewById(R.id.txtXeGiuongNam);
            viewHolder.txtGiaVe = view.findViewById(R.id.txtGiaVe);
            viewHolder.txtMoTa = view.findViewById(R.id.txtMoTa);

            view.setTag(viewHolder);

        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txtXeGiuongNam.setText(sanpham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtGiaVe.setText("Giá : " + decimalFormat.format(sanpham.getGiaSanPham()) + " VNĐ");
        viewHolder.txtMoTa.setMaxLines(2);
        viewHolder.txtMoTa.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtMoTa.setText(sanpham.getMoTaSanPham());

        Picasso.get().load(sanpham.getHinhAnhSanPham())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(viewHolder.imgXeGiuongNam);

        return view;

    }
}
