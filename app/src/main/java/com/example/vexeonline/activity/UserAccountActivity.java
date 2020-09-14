package com.example.vexeonline.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.vexeonline.R;
import com.example.vexeonline.model.Account;

public class UserAccountActivity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbarUserAccount;

    private TextView txtUserName;
    private TextView txtEmail;
    private TextView txtPhone;
    private Button btnLogout;
    private Account account;

    private LinearLayout lnVeDaMua;
    //private LinearLayout lnNhaXe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_account);
        //getData
        Intent intent = getIntent();
        account = new Account();
        account = (Account) intent.getSerializableExtra("login");

        init();
        actionToolbar();
    }


    private void actionToolbar() {
        setSupportActionBar(toolbarUserAccount);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarUserAccount.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }


    private void init() {
        toolbarUserAccount = findViewById(R.id.toolbarUserAccount);
        txtEmail = findViewById(R.id.txtEmail);
        txtUserName = findViewById(R.id.txtUserName);
        txtPhone = findViewById(R.id.txtPhone);
        lnVeDaMua = findViewById(R.id.lnVeDaMua);
        //lnNhaXe = findViewById(R.id.lnNhaXe);
        btnLogout = findViewById(R.id.btnLogout);

        txtUserName.setText(account.getUserName());
        txtEmail.setText(account.getEmail());
        txtPhone.setText(account.getPhone());

        lnVeDaMua.setOnClickListener(this);
        //lnNhaXe.setOnClickListener(this);
        btnLogout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.lnVeDaMua:
                Intent intent = new Intent(getApplicationContext(), VeDaMuaActivity.class);
                startActivity(intent);
                break;

/*
            case R.id.lnNhaXe:
                Intent intent1 = new Intent(getApplicationContext(), NhaXeActivity.class);
                startActivity(intent1);
                break;
*/

            case R.id.btnLogout:
                AlertDialog.Builder builder = new AlertDialog.Builder(UserAccountActivity.this);
                builder.setTitle("Xác Nhận Đăng Xuất");
                builder.setMessage("Bạn Có Chắc Muốn Đăng Xuất?");
                builder.setPositiveButton("có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent2 = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent2);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.show();
                break;
        }

    }
}
