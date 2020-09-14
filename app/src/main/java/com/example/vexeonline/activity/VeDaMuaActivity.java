package com.example.vexeonline.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.vexeonline.R;

public class VeDaMuaActivity extends AppCompatActivity {

    Toolbar toolbarVeDaMua;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ve_da_mua);
        init();
        ActionBar();
    }

    private void init() {
        toolbarVeDaMua = findViewById(R.id.toolbarVeDaMua);
    }

    private void ActionBar() {
        setSupportActionBar(toolbarVeDaMua);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarVeDaMua.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
