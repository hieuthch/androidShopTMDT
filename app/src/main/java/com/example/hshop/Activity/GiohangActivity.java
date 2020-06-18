package com.example.hshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.example.hshop.Adapter.GiohangAdapter;
import com.example.hshop.R;
import com.example.hshop.model.Giohang;
import com.example.hshop.until.Checkinternet;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GiohangActivity extends AppCompatActivity {
    ListView listViewgh;
    TextView thongbao;
    static TextView Tongtien;
    Button btnthanhtoan, btnmuatiep, btngiohangcong, btngiohangtru,btnslgh;
    GiohangAdapter giohangAdapter;
    Toolbar toolbargh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giohang);
        init();
        actiontb();
        checkData();
        Evengiohang();
        evenbtn();
        xoasp();

    }

    private void xoasp() {
        listViewgh.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(GiohangActivity.this);
                builder.setTitle("Xác nhận xóa sản phẩm");
                builder.setMessage("Bạn có chắc chắn muốn xóa sản phẩm này không ? ");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        MainActivity.giohangs.remove(position);
                        giohangAdapter.notifyDataSetChanged();
                        Evengiohang();
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        giohangAdapter.notifyDataSetChanged();
                        Evengiohang();
                    }
                });
                builder.show();
                return true;
            }
        });

    }

    private void evenbtn() {
        btnmuatiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GiohangActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.giohangs.size() > 0) {
                    Intent intent = new Intent(getApplicationContext(),khachhangActiity.class);
                    startActivity(intent );

                }
                else {
                    Checkinternet.show_toat(getApplicationContext(),"Giỏ hàng trống, vui lòng chọn sản phẩm cần mua");
                }
            }
        });
    }

    public static void Evengiohang() {
        long tongtien = 0;
        for (int i = 0; i < MainActivity.giohangs.size(); i++) {
            tongtien += MainActivity.giohangs.get(i).getGiasp();
            DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
            Tongtien.setText(decimalFormat.format(tongtien) + " Đ");
        }
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

    private void checkData() {
        if (MainActivity.giohangs.size() <= 0) {
            giohangAdapter.notifyDataSetChanged();
            thongbao.setVisibility(View.VISIBLE);
            listViewgh.setVisibility(View.VISIBLE);
        } else {
            giohangAdapter.notifyDataSetChanged();
            thongbao.setVisibility(View.INVISIBLE);
            listViewgh.setVisibility(View.VISIBLE);
        }
    }

    private void actiontb() {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargh.setNavigationIcon(R.drawable.back_icon);
        toolbargh.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void init() {
        listViewgh = findViewById(R.id.lstgiohang);
        thongbao = findViewById(R.id.txttbgiohang);
        Tongtien = findViewById(R.id.txttongtien);
        btnthanhtoan = findViewById(R.id.btnthanhtoangh);
        btnmuatiep = findViewById(R.id.btnttmuahang);
        giohangAdapter = new GiohangAdapter(this, R.layout.lst_giohang, MainActivity.giohangs);
        listViewgh.setAdapter(giohangAdapter);
        giohangAdapter.notifyDataSetChanged();
        toolbargh = findViewById(R.id.tb_giohang);
        setSupportActionBar(toolbargh);

    }
}
