package com.example.vexeonline.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.vexeonline.R;
import com.example.vexeonline.adapter.LoaiVeAdapter;
import com.example.vexeonline.adapter.SanPhamAdapter;
import com.example.vexeonline.model.Account;
import com.example.vexeonline.model.GioHang;
import com.example.vexeonline.model.LoaiVe;
import com.example.vexeonline.model.Sanpham;
import com.example.vexeonline.ulti.CheckInternet;
import com.example.vexeonline.ulti.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<GioHang> gioHangs;

    private Toolbar toolbar;
    private ViewFlipper viewFlipper;
    private RecyclerView recyclerView;
    private NavigationView navigationView;
    private ListView listView;
    private DrawerLayout drawerLayout;
    private ArrayList<LoaiVe> arraylistLoaive;
    private LoaiVeAdapter loaiVeAdapter;
    private ArrayList<Sanpham> arraylistSanpham;
    private SanPhamAdapter sanPhamAdapter;
    int id = 0;
    private String tenLoaive = "";
    private String hinhLoaive = "";

    private Account account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        account = new Account();
        account = (Account) intent.getSerializableExtra("login");
        iniViews();

        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
            ActionBar();
            ActionViewFlipper();
            GetDuLieuLoaiVe();
            GetDuLieuSPMoiNhat();
            CatchOnItemListView();
        } else {
            CheckInternet.ShowToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet!!!");
            finish();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.mnGioHang:
                Intent intent = new Intent(getApplicationContext(), GioHangActivity.class);
                startActivity(intent);
                break;
            case R.id.mnLogin:
                Intent intent1 = new Intent(getApplicationContext(), UserAccountActivity.class);
                intent1.putExtra("login", account);
                startActivity(intent1);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        // Gắn địa chỉ vào imageview
        mangquangcao.add("https://pigllet.000webhostapp.com/Datvexe/qc1.png");
        mangquangcao.add("https://pigllet.000webhostapp.com/Datvexe/qc2.png");
        mangquangcao.add("https://pigllet.000webhostapp.com/Datvexe/qc3.png");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_out);
        viewFlipper.setInAnimation(animation_slide_in);
    }

    private void ActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size); // Set Icon
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

    }

    private void GetDuLieuLoaiVe() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlLoaive, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenLoaive = jsonObject.getString("tenLoaisp");
                            hinhLoaive = jsonObject.getString("hinhanhLoaisp");
                            arraylistLoaive.add(new LoaiVe(id, tenLoaive, hinhLoaive));
                            loaiVeAdapter.notifyDataSetChanged();  // Update
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    arraylistLoaive.add(3, new LoaiVe(0, "Liên Hệ", "https://pigllet.000webhostapp.com/Datvexe/phone.png"));
                    arraylistLoaive.add(4, new LoaiVe(0, "Thông Tin", "https://pigllet.000webhostapp.com/Datvexe/support.png"));
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckInternet.ShowToast(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void GetDuLieuSPMoiNhat() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlSanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (requestQueue != null) {
                    int id = 0;
                    String tensanpham = "";
                    int giasanpham = 0;
                    String hinhanhsanpham = "";
                    String motasanpham = "";
                    int idsanpham = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tensanpham = jsonObject.getString("tensp");
                            giasanpham = jsonObject.getInt("giasp");
                            hinhanhsanpham = jsonObject.getString("hinhanhsp");
                            motasanpham = jsonObject.getString("motasp");
                            idsanpham = jsonObject.getInt("idsanpham");
                            arraylistSanpham.add(new Sanpham(id, tensanpham, giasanpham, hinhanhsanpham, motasanpham, idsanpham));
                            sanPhamAdapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                CheckInternet.ShowToast(getApplicationContext(), error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void CatchOnItemListView() {
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            CheckInternet.ShowToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, XeGiuongNamAvtivity.class);
                            intent.putExtra("idloaisanpham",arraylistLoaive.get(position).getId());
                            intent.putExtra("login", account);
                            startActivity(intent);
                        } else {
                            CheckInternet.ShowToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, LimousineActivity.class);
                            intent.putExtra("idloaisanpham",arraylistLoaive.get(position).getId());
                            intent.putExtra("login", account);
                            startActivity(intent);
                        } else {
                            CheckInternet.ShowToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, ContractActivity.class);
                            startActivity(intent);
                        } else {
                            CheckInternet.ShowToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (CheckInternet.haveNetworkConnection(getApplicationContext())) {
                            Intent intent = new Intent(MainActivity.this, InformationActivity.class);
                            startActivity(intent);
                        } else {
                            CheckInternet.ShowToast(getApplicationContext(), "Bạn hãy kiểm tra lại kết nối Internet");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;

                }
            }
        });
    }

    private void iniViews() {
        toolbar = findViewById(R.id.toolbar);
        viewFlipper = findViewById(R.id.viewFlipper);
        recyclerView = findViewById(R.id.recycleView);
        navigationView = findViewById(R.id.navigationView);
        listView = findViewById(R.id.listView);
        drawerLayout = findViewById(R.id.drawerLayout);
        ///////////////////////////////////////////////////////////////////////////////////////////
        arraylistLoaive = new ArrayList<>();
        arraylistLoaive.add(new LoaiVe(0, "Trang chủ", "https://pigllet.000webhostapp.com/Datvexe/home.png"));
        loaiVeAdapter = new LoaiVeAdapter(arraylistLoaive, getApplicationContext());
        listView.setAdapter(loaiVeAdapter);

        ///////////////////////////////////////////////////////////////////////////////////////////
        arraylistSanpham = new ArrayList<>();
        sanPhamAdapter = new SanPhamAdapter(getApplicationContext(), arraylistSanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerView.setAdapter(sanPhamAdapter);

        if (gioHangs != null){

        } else {
            gioHangs = new ArrayList<>();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


}
