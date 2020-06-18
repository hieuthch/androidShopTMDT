package com.example.hshop.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hshop.R;
import com.example.hshop.model.SanPham;
import com.example.hshop.until.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class LapTopAdapter extends ArrayAdapter {
    private Context mCtxsp;
    private int mLayoutLapTop;
    private List<SanPham> mListsp;

    public LapTopAdapter(@NonNull Context context, int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
        this.mCtxsp =context;
        this.mListsp=objects;
        this.mLayoutLapTop =resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        // Kiểm tra lần đầu tiên xem item có null / không
        if(item == null){
            item = LayoutInflater.from(mCtxsp).inflate(mLayoutLapTop, null);
        }

        // Load từng widget trên layout
        TextView txttenlt = item.findViewById(R.id.txt_tenlaptop);
        ImageView imglaptop = item.findViewById(R.id.img_laptop);
        TextView txtgialaptop = item.findViewById(R.id.txt_gialaptop);
        TextView txtmotalaptop=item.findViewById(R.id.txt_motalaptop);



        // Lấy đối tượng tại vị trí position
        SanPham sp = mListsp.get(position);
        // Gán dữ liệu lên View
        txttenlt.setText(sp.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgialaptop.setText("Giá: "+decimalFormat.format(sp.getGiasanpham())+" Đ");
        String linkanh = Server.duongdananh + sp.getHinhanhsanpham();
        Picasso.with(getContext()).load(linkanh).into(imglaptop);
        txtmotalaptop.setMaxLines(2);
        txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        txtmotalaptop.setText(sp.motasanpham);

        return item;
    }
}