package com.example.hshop.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.hshop.Activity.GiohangActivity;
import com.example.hshop.Activity.MainActivity;
import com.example.hshop.R;
import com.example.hshop.model.Giohang;
import com.example.hshop.model.SanPham;
import com.example.hshop.until.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class GiohangAdapter extends ArrayAdapter {
    private Context mCtxgh;
    private int mLayoutgiohang;
    private List<Giohang> mListgh;

    public GiohangAdapter(@NonNull Context context, int resource, @NonNull List objects) {
        super(context, resource, objects);
        this.mCtxgh = context;
        this.mListgh = objects;
        this.mLayoutgiohang = resource;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View item = convertView;
        // Kiểm tra lần đầu tiên xem item có null / không
        if (item == null) {
            item = LayoutInflater.from(mCtxgh).inflate(mLayoutgiohang, null);
            // Load từng widget trên layout
            TextView txtspgh = item.findViewById(R.id.txttenspgh);
            ImageView imghagh = item.findViewById(R.id.imggiohang);
            TextView txtgspgh = item.findViewById(R.id.txtgiaspgh);
            Button btnslgh = item.findViewById(R.id.btnslspgh);
            Button btntru = item.findViewById(R.id.btngiohangtru);
            Button btncong = item.findViewById(R.id.btngiohangcong);
            item.setTag(convertView);

        } else {
            convertView = (View) item.getTag();

        }

        TextView txtspgh = item.findViewById(R.id.txttenspgh);
        ImageView imghagh = item.findViewById(R.id.imggiohang);
        final TextView txtgspgh = item.findViewById(R.id.txtgiaspgh);
        final Button btnslgh = item.findViewById(R.id.btnslspgh);
        final Button btntru = item.findViewById(R.id.btngiohangtru);
        Button btncong = item.findViewById(R.id.btngiohangcong);

        // Lấy đối tượng tại vị trí position
        Giohang giohang = mListgh.get(position);
        // Gán dữ liệu lên View
        txtspgh.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        txtgspgh.setText("Giá: " + decimalFormat.format(giohang.getGiasp()) + " Đ");
        String linkanh = Server.duongdananh + giohang.getHinhanhsp();
        Picasso.with(getContext()).load(linkanh).into(imghagh);
        btnslgh.setText(giohang.getSoluongsp() + "");
        final int sl = Integer.parseInt(btnslgh.getText().toString());
        int slbd = Integer.parseInt(btnslgh.getText().toString());
        if (slbd <=1){
            btntru.setVisibility(View.INVISIBLE);
        }

        btncong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoinhat = Integer.parseInt(btnslgh.getText().toString()) + 1;
                int slht = MainActivity.giohangs.get(position).getSoluongsp();
                long giaht = MainActivity.giohangs.get(position).getGiasp();
                MainActivity.giohangs.get(position).setSoluongsp(slmoinhat);
                long giamn = (giaht * slmoinhat) / slht;
                MainActivity.giohangs.get(position).setGiasp(giamn);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtgspgh.setText("Giá: " + decimalFormat.format(giamn) + " Đ");
                GiohangActivity.Evengiohang();
                if (slmoinhat >= 1) {
                   btntru.setVisibility(View.VISIBLE);
                }

                btnslgh.setText(String.valueOf(slmoinhat));


            }
        });
        btntru.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int slmoinhat = Integer.parseInt(btnslgh.getText().toString()) - 1;
                int slht = MainActivity.giohangs.get(position).getSoluongsp();
                long giaht = MainActivity.giohangs.get(position).getGiasp();
                MainActivity.giohangs.get(position).setSoluongsp(slmoinhat);
                long giamn = (giaht * slmoinhat) / slht;
                MainActivity.giohangs.get(position).setGiasp(giamn);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                txtgspgh.setText("Giá: " + decimalFormat.format(giamn) + " Đ");
                GiohangActivity.Evengiohang();
                if (slmoinhat <= 1) {
                    btntru.setVisibility(View.INVISIBLE);
                }
                btnslgh.setText(String.valueOf(slmoinhat));
            }
        });


        return item;
    }
}
