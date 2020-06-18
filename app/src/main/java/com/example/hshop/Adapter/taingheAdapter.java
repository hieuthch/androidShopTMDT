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

public class taingheAdapter extends ArrayAdapter {
    private Context mCtxsp;
    private int mLayouttainghe;
    private List<SanPham> mListsp;

    public taingheAdapter(@NonNull Context context, int resource, @NonNull List<SanPham> objects) {
        super(context, resource, objects);
        this.mCtxsp =context;
        this.mListsp=objects;
        this.mLayouttainghe =resource;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View item = convertView;
        // Kiểm tra lần đầu tiên xem item có null / không
        if(item == null){
            item = LayoutInflater.from(mCtxsp).inflate(mLayouttainghe, null);
        }

        // Load từng widget trên layout
        TextView txttentn = item.findViewById(R.id.txt_tentn);
        ImageView imgtn = item.findViewById(R.id.img_tn);
        TextView txtgiatn = item.findViewById(R.id.txt_giatn);
        TextView txtmotatn=item.findViewById(R.id.txt_motatn);



        // Lấy đối tượng tại vị trí position
        SanPham sp = mListsp.get(position);
        // Gán dữ liệu lên View
        txttentn.setText(sp.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgiatn.setText("Giá: "+decimalFormat.format(sp.getGiasanpham())+" Đ");
        String linkanh = Server.duongdananh + sp.getHinhanhsanpham();
        Picasso.with(getContext()).load(linkanh).into(imgtn);
        txtmotatn.setMaxLines(2);
        txtmotatn.setEllipsize(TextUtils.TruncateAt.END);
        txtmotatn.setText(sp.motasanpham);

        return item;
    }
}
