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

public class DienThoaiAdapter extends ArrayAdapter {
    private Context mCtxsp;
    private int mLayoutDienthoai;
    private List<SanPham> mListsp;

    public DienThoaiAdapter(@NonNull Context context, int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
        this.mCtxsp =context;
        this.mListsp=objects;
        this.mLayoutDienthoai =resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        // Kiểm tra lần đầu tiên xem item có null / không
        if(item == null){
            item = LayoutInflater.from(mCtxsp).inflate(mLayoutDienthoai, null);
        }

        // Load từng widget trên layout
        TextView txttendt = item.findViewById(R.id.txt_tendienthoai);
        ImageView imgdienthoai = item.findViewById(R.id.img_dienthoai);
        TextView txtgiadienthoai = item.findViewById(R.id.txt_giadienthoai);
        TextView txtmotadienthoai=item.findViewById(R.id.txt_matadienthoai);



        // Lấy đối tượng tại vị trí position
        SanPham sp = mListsp.get(position);
        // Gán dữ liệu lên View
        txttendt.setText(sp.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgiadienthoai.setText("Giá: "+decimalFormat.format(sp.getGiasanpham())+" Đ");
        String linkanh = Server.duongdananh + sp.getHinhanhsanpham();
        Picasso.with(getContext()).load(linkanh).into(imgdienthoai);
        txtmotadienthoai.setMaxLines(2);
        txtgiadienthoai.setEllipsize(TextUtils.TruncateAt.END);
        txtmotadienthoai.setText(sp.motasanpham);

        return item;
    }
}
