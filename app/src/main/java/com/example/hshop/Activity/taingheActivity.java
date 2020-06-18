package com.example.hshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hshop.Adapter.taingheAdapter;
import com.example.hshop.R;
import com.example.hshop.model.SanPham;
import com.example.hshop.until.Checkinternet;
import com.example.hshop.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class taingheActivity extends AppCompatActivity {
    Toolbar toolbartn;
    ListView listViewtn;
   com.example.hshop.Adapter.taingheAdapter taingheAdapter;
    ArrayList<SanPham> mangtn;
    int iddt = 0;
    int page = 1;
    View loadmoretn;
    boolean isload = false;
    boolean limittn = false;
    taingheActivity.dtHandler dtHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tainghe);
        init();
        getiddienthoai();
        getdataDienthoai(page);
        actionbar();
        loadmoredienthoai();
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

    private void loadmoredienthoai() {
        listViewtn.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), chitietsanpham.class);
                intent.putExtra("thongtinsanpham", mangtn.get(position));
                startActivity(intent);
            }
        });
        listViewtn.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isload == false && limittn == false) {
                    isload = true;
                    threadDt threadDt = new threadDt();
                    threadDt.start();

                }
            }
        });
    }

    private void getdataDienthoai(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.duongdantn + String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int iddt = 0, giaspdt = 0, dungluongdt = 0, idSpdt = 0;
                String tenspdt = " ", mausacdt = " ", motadt = " ", anhspdt = " ";
                if (response != null && response.length() != 2) {
                    listViewtn.removeFooterView(loadmoretn);
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            iddt = jsonObject.optInt("id");
                            tenspdt = jsonObject.optString("tensanpham");
                            giaspdt = jsonObject.optInt("giasanpham");
                            anhspdt = jsonObject.optString("hinhanhsanpham");
                            motadt = jsonObject.optString("motasanpham");
                            idSpdt = jsonObject.optInt("idsanpham");
                            SanPham sanPhamtn = new SanPham(iddt, tenspdt, giaspdt, anhspdt, motadt, idSpdt);
                            mangtn.add(sanPhamtn);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    limittn = true;
                    listViewtn.removeFooterView(loadmoretn);
                    Checkinternet.show_toat(getApplicationContext(), "Hết dữ liệu");

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String, String> param = new HashMap<String, String>();
                param.put("idsanpham", String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }

    private void actionbar() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbartn.setNavigationIcon(R.drawable.back_icon);
        toolbartn.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private void getiddienthoai() {
        iddt = getIntent().getIntExtra("idloaisanpham", -1);
        Log.d("giatriloaisp", iddt + "");
    }

    private void init() {
        listViewtn = findViewById(R.id.lsttn);
        mangtn = new ArrayList<>();
        taingheAdapter = new taingheAdapter(this, R.layout.lst_tainghe, mangtn);
        listViewtn.setAdapter(taingheAdapter);
        taingheAdapter.notifyDataSetChanged();
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        loadmoretn = inflater.inflate(R.layout.loadmore, null);
        dtHandler = new dtHandler();
        toolbartn = findViewById(R.id.tb_tainghe);
        setSupportActionBar(toolbartn);
    }

    public class dtHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case 0:
                    listViewtn.addFooterView(loadmoretn);
                    break;
                case 1:
                    getdataDienthoai(++page);
                    isload = false;
                    break;

            }
            super.handleMessage(msg);
        }
    }

    public class threadDt extends Thread {
        @Override
        public void run() {
            dtHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = dtHandler.obtainMessage(1);
            dtHandler.sendMessage(message);
            super.run();
        }
    }
}
