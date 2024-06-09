package com.example.qunlsinhvin;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    EditText edtnganh, edttenlop, edthovaten, edtngaysinh, edtdienthoai, edtemail;
    Button btnthem, btnxoa, btncapnhat, btntracuu;
    ListView lv;
    ArrayList<String> mylist;
    ArrayAdapter<String> mydapter;
    SQLiteDatabase mydatabase;
    ImageView btnBack; // Tham chiếu đến ImageView

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtnganh = findViewById(R.id.edtnganh);
        edttenlop = findViewById(R.id.edttenlop);
        edthovaten = findViewById(R.id.edthovaten);
        edtngaysinh = findViewById(R.id.edtngaysinh);
        edtdienthoai = findViewById(R.id.edtdienthoai);
        edtemail = findViewById(R.id.edtemail);
        btnthem = findViewById(R.id.btnthem);
        btncapnhat = findViewById(R.id.btncapnhat);
        btnxoa = findViewById(R.id.btnxoa);
        btntracuu = findViewById(R.id.btntracuu);
        btnBack = findViewById(R.id.btnBack); // Tham chiếu đến ImageView

        lv = findViewById(R.id.lv);
        mylist = new ArrayList<>();
        mydapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mylist);
        lv.setAdapter(mydapter);

        mydatabase = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);
        try {
            String createTableSql = "CREATE TABLE IF NOT EXISTS tbllop (" +
                    "nganh TEXT PRIMARY KEY, " +
                    "tenlop TEXT, " +
                    "hovaten TEXT, " +
                    "ngaysinh TEXT, " +
                    "dienthoai TEXT, " +
                    "email TEXT)";
            mydatabase.execSQL(createTableSql);
        } catch (Exception e) {
            Log.e("Error", "Table đã tồn tại");
        }

        btnthem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nganh = edtnganh.getText().toString();
                    String tenlop = edttenlop.getText().toString();
                    String hovaten = edthovaten.getText().toString();
                    String email = edtemail.getText().toString();
                    String ngaysinh = edtngaysinh.getText().toString();
                    String dienthoai = edtdienthoai.getText().toString();

                    ContentValues myvalue = new ContentValues();
                    myvalue.put("nganh", nganh);
                    myvalue.put("tenlop", tenlop);
                    myvalue.put("hovaten", hovaten);
                    myvalue.put("ngaysinh", ngaysinh);
                    myvalue.put("dienthoai", dienthoai);
                    myvalue.put("email", email);

                    long result = mydatabase.insertWithOnConflict("tbllop", null, myvalue, SQLiteDatabase.CONFLICT_IGNORE);
                    if (result == -1) {
                        Toast.makeText(MainActivity.this, "Không thể thêm!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", "Insert Error: " + e.getMessage());
                }
            }
        });

        btnxoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nganh = edtnganh.getText().toString();
                    int n = mydatabase.delete("tbllop", "nganh = ?", new String[]{nganh});
                    if (n == 0) {
                        Toast.makeText(MainActivity.this, "Không có văn bản nào để xóa", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, n + " Văn bản đã được xóa", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", "Delete Error: " + e.getMessage());
                }
            }
        });

        btncapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    String nganh = edtnganh.getText().toString();
                    String dienthoai = edtdienthoai.getText().toString();
                    ContentValues myvalue = new ContentValues();
                    myvalue.put("dienthoai", dienthoai);

                    int n = mydatabase.update("tbllop", myvalue, "nganh = ?", new String[]{nganh});
                    if (n == 0) {
                        Toast.makeText(MainActivity.this, "Không thể cập nhật!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(MainActivity.this, n + " Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", "Update Error: " + e.getMessage());
                }
            }
        });

        btntracuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    mylist.clear();
                    Cursor c = mydatabase.query("tbllop", null, null, null, null, null, null);
                    if (c.moveToFirst()) {
                        do {
                            String data = c.getString(0) + " - " + c.getString(1) + " - " + c.getString(2) + " - " + c.getString(3) + " - " + c.getString(4) + " - " + c.getString(5);
                            mylist.add(data);
                        } while (c.moveToNext());
                    }
                    c.close();
                    mydapter.notifyDataSetChanged();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Lỗi: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.e("Error", "Query Error: " + e.getMessage());
                }
            }
        });

        // Gán sự kiện click cho btnBack
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện click ở đây
                // Ví dụ: kết thúc hoạt động hiện tại
                finish();
            }
        });
    }
}
