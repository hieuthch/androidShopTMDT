package com.example.hshop.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.hshop.R;
import com.example.hshop.until.Checkinternet;
import com.example.hshop.until.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class khachhangActiity extends AppCompatActivity {
    EditText tenkh, sodienthoaikh, emailkh, diachikh;
    Button btnxacnhan, btntrove;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_khachhang);
        innit();
        eventrove();
        evenxacnhan();
    }

    private void evenxacnhan() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten = tenkh.getText().toString().trim();
                final String sdt = sodienthoaikh.getText().toString().trim();
                final String email = emailkh.getText().toString().trim();
                final String diachi = diachikh.getText().toString().trim();
                if (ten.length() > 0 && sdt.length() > 0 && email.length() > 0 && diachi.length() > 0) {
                    RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.duongdankhachhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang",madonhang);
                            if (Integer.parseInt(madonhang) > 0) {

                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")) {
                                            Checkinternet.show_toat(getApplicationContext(), "Đơn hàng đặt thành công !!!");
                                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            Checkinternet.show_toat(getApplicationContext(), "Dữ liệu bị lỗi !!!");
                                        }

                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i = 0; i < MainActivity.giohangs.size(); i++) {
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang", madonhang);
                                                jsonObject.put("soluong", MainActivity.giohangs.get(i).getSoluongsp());
                                                jsonObject.put("tensanpham", MainActivity.giohangs.get(i).getTensp());
                                                jsonObject.put("giasanpham", MainActivity.giohangs.get(i).getGiasp());
                                                jsonObject.put("masanpham", MainActivity.giohangs.get(i).getIdsp());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String, String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json", jsonArray.toString());

                                        return hashMap;
                                    }
                                };
                                queue.add(request);

                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String, String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang", ten);
                            hashMap.put("sodienthoai", sdt);
                            hashMap.put("email", email);
                            hashMap.put("diachi", diachi);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                } else {
                    Checkinternet.show_toat(getApplicationContext(), "Vui lòng điền đầy đủ thông tin");
                }
            }
        });
    }

    private void eventrove() {
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                ;
            }
        });
    }

    private void innit() {
        tenkh = findViewById(R.id.tenkh);
        sodienthoaikh = findViewById(R.id.sodienthoaikh);
        emailkh = findViewById(R.id.emailkh);
        diachikh = findViewById(R.id.diachikh);
        btnxacnhan = findViewById(R.id.xacnhan);
        btntrove = findViewById(R.id.trove);
    }
}
