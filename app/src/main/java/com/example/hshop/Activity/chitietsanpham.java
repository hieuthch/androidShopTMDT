package com.example.hshop.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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


import com.example.hshop.R;
import com.example.hshop.model.Giohang;
import com.example.hshop.model.SanPham;
import com.example.hshop.until.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class chitietsanpham extends AppCompatActivity {
    Toolbar toolbarctsp;
    ImageView imgctsp;
    TextView txtten, txtgia, txtctsp;
    Spinner spinner;
    Button btnmua;
    int id = 0, giasp = 0, idSp = 0;
    String tensp = " ", mota = " ", anhsp = " ";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitietsanpham);
        init();
        actionbar();
        getchitietsp();
        spinnersl();
        themgiohang();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void themgiohang() {
        btnmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.giohangs.size() > 0) {
                    int sl = Integer.parseInt(spinner.getSelectedItem().toString());
                    boolean exits = false;
                    for (int i = 0; i < MainActivity.giohangs.size(); i++) {
                        if (MainActivity.giohangs.get(i).getIdsp() == id) {
                            MainActivity.giohangs.get(i).setSoluongsp(MainActivity.giohangs.get(i).getSoluongsp() + sl);
                            if (MainActivity.giohangs.get(i).getSoluongsp() >= 10) {
                                MainActivity.giohangs.get(i).setSoluongsp(10);
                            }
                            MainActivity.giohangs.get(i).setGiasp(giasp * MainActivity.giohangs.get(i).getSoluongsp());
                            exits = true;
                        }
                    }
                    if (exits == false) {
                        int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                        long Giamoi = soluong * giasp;
                        MainActivity.giohangs.add(new Giohang(id, soluong, tensp, anhsp, Giamoi));
                    }

                } else {
                    int soluong = Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi = soluong * giasp;
                    MainActivity.giohangs.add(new Giohang(id, soluong, tensp, anhsp, Giamoi));
                }
                Intent intent  = new Intent(getApplicationContext(), GiohangActivity.class);
                startActivity(intent);
            }
        });
    }

    private void spinnersl() {
        Integer[] soluong = new Integer[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<Integer>(this, R.layout.support_simple_spinner_dropdown_item, soluong);
        spinner.setAdapter(integerArrayAdapter);
    }

    private void getchitietsp() {

        SanPham sanPham = (SanPham) getIntent().getSerializableExtra("thongtinsanpham");
        id = sanPham.getId();
        giasp = sanPham.getGiasanpham();
        idSp = sanPham.getIdsanpham();
        tensp = sanPham.getTensanpham();
        mota = sanPham.getMotasanpham();
        anhsp = sanPham.getHinhanhsanpham();
        txtten.setText(tensp);
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgia.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) + " Đ");
        txtctsp.setText(mota);
        String linkanh = Server.duongdananh + sanPham.getHinhanhsanpham();
        Picasso.with(getApplicationContext()).load(linkanh).into(imgctsp);
    }

    private void actionbar() {
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarctsp.setNavigationIcon(R.drawable.back_icon);
        toolbarctsp.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    private void init() {
        toolbarctsp = findViewById(R.id.tb_chitietsp);
        setSupportActionBar(toolbarctsp);
        imgctsp = findViewById(R.id.imgchitietsp);
        txtten = findViewById(R.id.txttenchitietsp);
        txtgia = findViewById(R.id.txtgiachitietsp);
        txtctsp = findViewById(R.id.txtchitietttsp);
        spinner = findViewById(R.id.spinner);
        btnmua = findViewById(R.id.btnthemgiohang);

    }
}
