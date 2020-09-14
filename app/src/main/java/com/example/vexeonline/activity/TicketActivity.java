package com.example.vexeonline.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.vexeonline.R;
import com.example.vexeonline.adapter.XeGiuongNamAdapter;
import com.example.vexeonline.model.Sanpham;

import java.util.ArrayList;

public class TicketActivity extends AppCompatActivity {

    private Toolbar toolbarXeGiuongNam;
    private ListView lvXeGiuongNam;

    private XeGiuongNamAdapter xeGiuongNamAdapter;
    private ArrayList<Sanpham> sanphams;

    int idXe = 0;
    int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ticket);
        

        actionToolbar();

    }

    private void actionToolbar() {
        setSupportActionBar(toolbarXeGiuongNam);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarXeGiuongNam.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void getID() {
        idXe = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giaTriLoaiSP", idXe + "");

    }


    private void init() {
        toolbarXeGiuongNam = findViewById(R.id.toolbarXeGiuongNam);
        lvXeGiuongNam = findViewById(R.id.lvXeGiuongNam);

        sanphams = new ArrayList<>();
        xeGiuongNamAdapter = new XeGiuongNamAdapter(getApplicationContext(), sanphams);
        lvXeGiuongNam.setAdapter(xeGiuongNamAdapter);

    }

}
