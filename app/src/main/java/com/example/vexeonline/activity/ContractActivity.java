package com.example.vexeonline.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.vexeonline.R;

public class ContractActivity extends AppCompatActivity {

    private Toolbar toolbarLienhe;
    private LinearLayout lnCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contract);
        iniViews();
        ActionBar();
        evenButton();
    }

    private void evenButton() {
        lnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                phoneCall();
            }
        });
    }

    private void iniViews() {
        toolbarLienhe = findViewById(R.id.toolbarLienhe);
        lnCall = findViewById(R.id.lnCall);

    }

    private void phoneCall() {
        String phone = "0972103797";
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel: " + phone));
        startActivity(intent);

    }

    private void ActionBar() {
        setSupportActionBar(toolbarLienhe);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarLienhe.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
