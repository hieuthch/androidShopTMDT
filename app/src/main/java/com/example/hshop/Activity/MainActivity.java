package com.example.hshop.Activity;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;
import android.widget.ViewFlipper;
import com.android.volley.NetworkError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.hshop.Adapter.LoaispAdapter;
import com.example.hshop.Adapter.SanPhamNewAdapter;
import com.example.hshop.R;
import com.example.hshop.model.Giohang;
import com.example.hshop.model.Loaisp;
import com.example.hshop.model.SanPham;
import com.example.hshop.until.Checkinternet;
import com.example.hshop.until.Server;
import com.google.android.material.navigation.NavigationView;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.net.ssl.HttpsURLConnection;

public class MainActivity extends AppCompatActivity {
    ViewFlipper viewflqc;
    Toolbar toolbar;
    RecyclerView recyclerViewspnew;
    ListView lstmenu;
    NavigationView navigationhome;
    DrawerLayout drhome;
    ArrayList<SanPham> lstSanPham;
    SanPhamNewAdapter sanPhamNewAdapter;
    ArrayList<Loaisp> lstLoaiSp;
    LoaispAdapter loaispAdapter;
    public static ArrayList<Giohang> giohangs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        if (Checkinternet.haveNetworkConnection(getApplicationContext())) {
            actionbar();
            getspnew();
            getLoaiSp();
            actionViewFl();
            loadsanphamnew();
            getitemmenu();
            loadmenu();
        } else {
            Checkinternet.show_toat(getApplicationContext(), "Kiểm tra lại kết nối internet");
            finish();
        }
    }

    private void getitemmenu() {
        ListView listView = findViewById(R.id.lstmenuhome);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Intent intent = new Intent(MainActivity.this, MainActivity.class);
                        startActivity(intent);
                        drhome.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        Intent intentdt = new Intent(MainActivity.this, dienthoaiActivity.class);
                        intentdt.putExtra("idloaisanpham", lstLoaiSp.get(position).getId());
                        startActivity(intentdt);
                        drhome.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        Intent intentlt = new Intent(MainActivity.this, laptopActivity.class);
                        intentlt.putExtra("idloaisanpham", lstLoaiSp.get(position).getId());
                        drhome.closeDrawer(GravityCompat.START);
                        startActivity(intentlt);
                        drhome.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        Intent intenttn = new Intent(MainActivity.this, taingheActivity.class);
                        intenttn.putExtra("idloaisanpham", lstLoaiSp.get(position).getId());
                        startActivity(intenttn);
                        drhome.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        Intent intentlh = new Intent(MainActivity.this, lienheActivity.class);
                        startActivity(intentlh);
                        drhome.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void getLoaiSp() {
        RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int id = 0;
                    String loaiSp = "";
                    String imgLoaiSp = "";
                    final int number = response.length();
                    for (int i = 0; i < number; i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.optInt("id");
                            loaiSp = jsonObject.optString("tenloaisanpham");
                            imgLoaiSp = jsonObject.optString("hinhloaisanpham");
                            lstLoaiSp.add(new Loaisp(id, loaiSp, imgLoaiSp));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    lstLoaiSp.add(new Loaisp(0, "Liên Hệ", "images/contacts-icon.png"));
                    Log.e("loadmenu: ", imgLoaiSp);
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RetryPolicy policy = new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        jsonArrayRequest.setRetryPolicy(policy);
        requestQueue.add(jsonArrayRequest);

    }

    private void getspnew() {
        RequestQueue queue = Volley.newRequestQueue(this);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Server.duongdanspnew, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int idspnew = 0, giasp = 0, dungluong = 0, idSp = 0;
                    String tensp = " ", mausac = " ", mota = " ", anhspnew = " ";
                    final int number = response.length();
                    for (int i = 0; i < number; i++) {
                        try {

                            JSONObject jsonObject = response.getJSONObject(i);
                            idSp = jsonObject.optInt("id");
                            tensp = jsonObject.optString("tensanpham");
                            giasp = jsonObject.optInt("giasanpham");
                            anhspnew = jsonObject.optString("hinhanhsanpham");
                            mota = jsonObject.optString("motasanpham");
                            idspnew = jsonObject.optInt("idsanpham");
                            SanPham sanPham = new SanPham(idSp, tensp, giasp, anhspnew, mota, idspnew);
                            lstSanPham.add(sanPham);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
                , new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_LONG).show();
            }
        });
        RetryPolicy policy = new DefaultRetryPolicy(500000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        arrayRequest.setRetryPolicy(policy);

        queue.add(arrayRequest);


    }

    private void loadmenu() {
        ListView listmenu = findViewById(R.id.lstmenuhome);
        lstLoaiSp = new ArrayList<>();
        lstLoaiSp.add(new Loaisp(0, "Trang chủ", "images/Home-icon.png"));
        LoaispAdapter loaispAdapter = new LoaispAdapter(this, R.layout.menu_loaisp, lstLoaiSp);
        listmenu.setAdapter(loaispAdapter);
        loaispAdapter.notifyDataSetChanged();

    }

    public void loadsanphamnew() {
        lstSanPham = new ArrayList<>();
        SanPhamNewAdapter sanPhamNewAdapter = new SanPhamNewAdapter(getApplicationContext(), lstSanPham);
        recyclerViewspnew.setAdapter(sanPhamNewAdapter);
        sanPhamNewAdapter.notifyDataSetChanged();

    }

    private void actionbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                drhome.openDrawer(GravityCompat.START);
            }
        });

    }

    private void actionViewFl() {
        ArrayList<String> flqc = new ArrayList<>();
        flqc.add("http://cachsudung.com/wp-content/uploads/2020/04/1.png");
        flqc.add("http://cachsudung.com/wp-content/uploads/2020/04/2.png");
        flqc.add("http://cachsudung.com/wp-content/uploads/2020/04/3.png");
        for (int i = 0; i < flqc.size(); i++) {
            ImageView imgqc = new ImageView(getApplicationContext());
            Picasso.with(getApplicationContext()).load(flqc.get(i)).into(imgqc);
            imgqc.setScaleType(ImageView.ScaleType.FIT_XY);
            viewflqc.addView(imgqc);
        }
        viewflqc.setFlipInterval(5000);
        viewflqc.setAutoStart(true);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_out);
        Animation animation_righttin = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slider_rightin);
        viewflqc.setInAnimation(animation_righttin);
        viewflqc.setOutAnimation(animation_out);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public void init() {
        viewflqc = findViewById(R.id.viewflqc);
        toolbar = findViewById(R.id.toolbarmenu);
        navigationhome = findViewById(R.id.menunavi);
        drhome = findViewById(R.id.drhome);
        lstSanPham = new ArrayList<>();
        recyclerViewspnew = findViewById(R.id.recyclerhome);
        sanPhamNewAdapter = new SanPhamNewAdapter(getApplicationContext(), lstSanPham);
        recyclerViewspnew.setHasFixedSize(true);
        recyclerViewspnew.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));
        recyclerViewspnew.setHasFixedSize(true);
        recyclerViewspnew.setAdapter(sanPhamNewAdapter);
        setSupportActionBar(toolbar);
        if (giohangs != null) {
        } else {
            giohangs = new ArrayList<>();
        }
    }
}
