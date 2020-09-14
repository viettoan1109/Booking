package com.example.vexeonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.vexeonline.R;
import com.example.vexeonline.model.Account;
import com.example.vexeonline.model.GioHang;
import com.example.vexeonline.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietXeActivity extends AppCompatActivity {

    private Toolbar toolbarChiTiet;
    private ImageView imgChiTiet;
    private TextView txtChiTiet;
    private TextView txtGia;
    private TextView txtMoTaChiTiet;
    private Spinner spinner;
    private Button btnThemGioHang;

    private Account account;

    int id = 0;
    int idLoaiXe = 0;
    int gia = 0;
    String tenXe = "";
    String moTa = "";
    String hinhAnhXe = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_xe);

        Intent intent = getIntent();
        account = new Account();
        account = (Account) intent.getSerializableExtra("login");


        init();
        actionToolbar();
        getInforMation();
        catchevenSpinner();
        evenButton();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnGioHang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
                break;

            /*case R.id.mnLogin:
                Intent intent1 = new Intent(getApplicationContext(), UserAccountActivity.class);
                intent1.putExtra("login", account);
                startActivity(intent1);
                break;*/

        }
        return super.onOptionsItemSelected(item);
    }

    private void evenButton() {
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.gioHangs.size() > 0) {

                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exit = false;

                    for (int i = 0; i < MainActivity.gioHangs.size(); i++) {
                        if (MainActivity.gioHangs.get(i).idSP == id) {
                            MainActivity.gioHangs.get(i).setSoLuong(MainActivity.gioHangs.get(i).getSoLuong() + sl);
                            if (MainActivity.gioHangs.get(i).getSoLuong() >= 15) {
                                MainActivity.gioHangs.get(i).setSoLuong(15);
                            }
                            MainActivity.gioHangs.get(i).setGiaSanPham(gia * MainActivity.gioHangs.get(i).getSoLuong());
                            exit = true;
                        }
                    }
                    if (exit == false) {
                        int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long giaMoi = soLuong * gia;

                        MainActivity.gioHangs.add(new GioHang(id, tenXe, giaMoi, hinhAnhXe, soLuong));

                    }

                } else {
                    int soLuong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long giaMoi = soLuong * gia;

                    MainActivity.gioHangs.add(new GioHang(id, tenXe, giaMoi, hinhAnhXe, soLuong));

                }

                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                intent.putExtra("login", account);
                startActivity(intent);
            }
        });
    }

    private void catchevenSpinner() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
        ArrayAdapter<Integer> arrayAdapter = new ArrayAdapter<Integer>(this, android.R.layout.simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void getInforMation() {
        Sanpham sanpham = (Sanpham) getIntent().getSerializableExtra("thongTinXe");

        id = sanpham.getId();
        tenXe = sanpham.getTenSanPham();
        gia = sanpham.getGiaSanPham();
        hinhAnhXe = sanpham.getHinhAnhSanPham();
        moTa = sanpham.getMoTaSanPham();
        idLoaiXe = sanpham.getIdLoaiSanPham();

        txtChiTiet.setText(tenXe);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtGia.setText("Giá : " + decimalFormat.format(gia) + " VNĐ");
        txtMoTaChiTiet.setText(moTa);

        Picasso.get().load(hinhAnhXe)
                .placeholder(R.drawable.noimg)
                .error(R.drawable.error)
                .into(imgChiTiet);

    }

    private void actionToolbar() {
        setSupportActionBar(toolbarChiTiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChiTiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        toolbarChiTiet = findViewById(R.id.toolbarChiTiet);
        imgChiTiet = findViewById(R.id.imgChiTiet);
        txtChiTiet = findViewById(R.id.txtChiTiet);
        txtMoTaChiTiet = findViewById(R.id.txtMoTaChiTiet);
        txtGia = findViewById(R.id.txtGia);
        spinner = findViewById(R.id.spinner);
        btnThemGioHang = findViewById(R.id.btnThemGioHang);
    }
}
