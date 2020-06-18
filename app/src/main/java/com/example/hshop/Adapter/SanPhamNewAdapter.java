package com.example.hshop.Adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.toolbox.ImageLoader;
import com.example.hshop.Activity.chitietsanpham;
import com.example.hshop.R;
import com.example.hshop.model.SanPham;
import com.example.hshop.until.Server;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanPhamNewAdapter extends RecyclerView.Adapter<SanPhamNewAdapter.ViewHolder> {
    Context context;
    ArrayList<SanPham> mList;

    public SanPhamNewAdapter(Context context, ArrayList<SanPham> mList) {
        this.context = context;
        this.mList = mList;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.list_sanphamnew, null);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        SanPham sanPham = mList.get(position);
        holder.txttensp.setMaxLines(1);
        holder.txttensp.setEllipsize(TextUtils.TruncateAt.END);
        holder.txttensp.setText(sanPham.getTensanpham());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: " + decimalFormat.format(sanPham.getGiasanpham()) + " Đ");
        String linkanh = Server.duongdananh + sanPham.getHinhanhsanpham();
        Picasso.with(context).load(linkanh).into(holder.imgloaisp);

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView txttensp;
        ImageView imgloaisp;
        TextView txtgiasp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txttensp = itemView.findViewById(R.id.newname);
            imgloaisp = itemView.findViewById(R.id.newimgsp);
            txtgiasp = itemView.findViewById(R.id.newgiasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(context, chitietsanpham.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("thongtinsanpham", mList.get(getPosition()));
                    context.startActivity(intent);

                }
            });
        }
    }
}
