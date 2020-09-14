package com.example.vexeonline.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.vexeonline.R;
import com.example.vexeonline.activity.ChiTietXeActivity;
import com.example.vexeonline.model.Account;
import com.example.vexeonline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ItemHolder> {
    private Context context;
    private ArrayList<Sanpham> arraySanpham;

    public SanPhamAdapter(Context context, ArrayList<Sanpham> arraySanpham) {
        this.context = context;
        this.arraySanpham = arraySanpham;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat, null);
        ItemHolder itemHolder = new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham = arraySanpham.get(position);
        holder.txtTensanpham.setText(sanpham.getTenSanPham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtGiasanpham.setText("Giá: " + decimalFormat.format(sanpham.getGiaSanPham()) +" VNĐ");    // Custom lại giá vé
        Picasso.get().load(sanpham.getHinhAnhSanPham())
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(holder.imageviewSanpham);
    }

    @Override
    public int getItemCount() {
        return arraySanpham.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder {

        private Account account;

        public ImageView imageviewSanpham;
        public TextView txtTensanpham, txtGiasanpham;

        public ItemHolder(@NonNull View itemView) {
            super(itemView);
            imageviewSanpham = itemView.findViewById(R.id.imageviewSanpham);
            txtTensanpham = itemView.findViewById(R.id.txtTensanpham);
            txtGiasanpham = itemView.findViewById(R.id.txtGiasanpham);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, ChiTietXeActivity.class);
                    intent.putExtra("thongTinXe", arraySanpham.get(getPosition()));
                    intent.putExtra("login", account);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }

    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public ArrayList<Sanpham> getArraySanpham() {
        return arraySanpham;
    }

    public void setArraySanpham(ArrayList<Sanpham> arraySanpham) {
        this.arraySanpham = arraySanpham;
    }

}
