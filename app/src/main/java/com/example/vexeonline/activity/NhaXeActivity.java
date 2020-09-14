package com.example.vexeonline.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.vexeonline.R;

public class NhaXeActivity extends AppCompatActivity {

    Toolbar toolbarNhaxe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nha_xe);
        init();
        ActionBar();
    }

    private void init() {
        toolbarNhaxe = findViewById(R.id.toolbarNhaXe);
    }

    private void ActionBar() {
        setSupportActionBar(toolbarNhaxe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarNhaxe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
