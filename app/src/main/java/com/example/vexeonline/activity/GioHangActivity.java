package com.example.vexeonline.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.vexeonline.R;
import com.example.vexeonline.adapter.GioHangAdapter;
import com.example.vexeonline.model.Account;
import com.example.vexeonline.ulti.CheckInternet;

import java.text.DecimalFormat;

public class GioHangActivity extends AppCompatActivity {

    private Toolbar toolbarGioHang;
    private ListView lvGioHang;
    private TextView txtThongBao;
    static private TextView txtTongTien;
    private Button btnThanhToan;
    private Button btnTiepTucMuaHang;

    private GioHangAdapter gioHangAdapter;
    private Account account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        init();
        Intent intent = getIntent();
        account = new Account();
        account = (Account) intent.getSerializableExtra("login");

        actionToolbar();
        checkData();
        evenUltil();
        catchOnItemListView();
        evenButton();
    }

    private void evenButton() {
        btnTiepTucMuaHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("login", account);
                startActivity(intent);
            }
        });

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.gioHangs.size() > 0){
                    Intent intent = new Intent(getApplicationContext(), InforUserActivity.class);
                    intent.putExtra("login", account);
                    startActivity(intent);
                } else {
                    CheckInternet.ShowToast(getApplicationContext(), "Giỏ Hàng Của Bạn Chưa Có Sản Phẩm");
                }
            }
        });
    }

    private void catchOnItemListView() {
        lvGioHang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GioHangActivity.this);
                builder.setTitle("Xác Nhận Hủy Vé");
                builder.setMessage("Bạn Có Chắc Muốn Hủy?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.gioHangs.size() <= 0) {
                            txtThongBao.setVisibility(View.VISIBLE);
                        } else {
                            MainActivity.gioHangs.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            evenUltil();
                            if (MainActivity.gioHangs.size() <= 0) {
                                txtThongBao.setVisibility(View.VISIBLE);
                            } else {
                                txtThongBao.setVisibility(View.INVISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                evenUltil();
                            }
                        }
                    }

                });

                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        evenUltil();
                    }
                });

                builder.show();

                return false;
            }
        });



    }

    public static void evenUltil() {
        long tongTien = 0;
        for (int i = 0; i < MainActivity.gioHangs.size(); i++) {
            tongTien += MainActivity.gioHangs.get(i).getGiaSanPham();
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            txtTongTien.setText(decimalFormat.format(tongTien) + " VNĐ");

        }
    }

    private void checkData() {
        if (MainActivity.gioHangs.size() <= 0) {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.VISIBLE);
            lvGioHang.setVisibility(View.INVISIBLE);
        } else {
            gioHangAdapter.notifyDataSetChanged();
            txtThongBao.setVisibility(View.INVISIBLE);
            lvGioHang.setVisibility(View.VISIBLE);
        }
    }

    private void actionToolbar() {
        setSupportActionBar(toolbarGioHang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarGioHang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void init() {
        lvGioHang = findViewById(R.id.lvGioHang);
        txtThongBao = findViewById(R.id.txtThongBao);
        txtTongTien = findViewById(R.id.txtTongTien);
        btnThanhToan = findViewById(R.id.btnThanhToan);
        btnTiepTucMuaHang = findViewById(R.id.btnTiepTucMuaHang);
        toolbarGioHang = findViewById(R.id.toolbarGioHang);

        gioHangAdapter = new GioHangAdapter(GioHangActivity.this, MainActivity.gioHangs);
        lvGioHang.setAdapter(gioHangAdapter);
    }
}
