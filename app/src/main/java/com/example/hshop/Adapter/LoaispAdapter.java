package com.example.hshop.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hshop.Activity.MainActivity;
import com.example.hshop.R;
import com.example.hshop.model.Loaisp;
import com.example.hshop.model.SanPham;
import com.example.hshop.until.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class LoaispAdapter extends ArrayAdapter<Loaisp> {
    private Context mCtxsp;
    private int mLayoutItemsp;
    private List<Loaisp> mListsp;

    public LoaispAdapter(Context context, int resource, List<Loaisp> objects) {
        super(context, resource, objects);
        this.mCtxsp = context;
        this.mListsp = objects;
        this.mLayoutItemsp = resource;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        // Kiểm tra lần đầu tiên xem item có null / không
        if (item == null) {
            item = LayoutInflater.from(mCtxsp).inflate(mLayoutItemsp, null);
        }

        // Load từng widget trên layout
        TextView txtloaisp = item.findViewById(R.id.txtmenu);
        ImageView imgloaisp = item.findViewById(R.id.imgmenu);
        // Lấy đối tượng tại vị trí position
        Loaisp lsp = mListsp.get(position);
        // Gán dữ liệu lên View
        txtloaisp.setText(lsp.getTenLoaiSp());
        String linkanh = Server.duongdananh + lsp.getImgLoaiSp();
        Picasso.with(getContext()).load(linkanh).into(imgloaisp);
        return item;
    }
}

