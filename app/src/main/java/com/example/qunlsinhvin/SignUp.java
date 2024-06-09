package com.example.qunlsinhvin;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SignUp extends AppCompatActivity {

    SQLiteDatabase mydatabase;
    EditText edtUsername, edtPassword;
    Button btnRegister;
    TextView tvLoginNow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        edtUsername = findViewById(R.id.edtUsername);
        edtPassword = findViewById(R.id.edtPassword);
        btnRegister = findViewById(R.id.btnRegister);
        tvLoginNow = findViewById(R.id.tvLoginNow);

        mydatabase = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);
        try {
            String createTableSql = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "username TEXT, " +
                    "password TEXT)";
            mydatabase.execSQL(createTableSql);
            Log.d("Database", "Table created successfully");
        } catch (Exception e) {
            Log.e("Error", "Table already exists or creation failed", e);
        }

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(SignUp.this, "Please enter username and password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Cursor cursor = mydatabase.rawQuery("SELECT * FROM users WHERE username=?", new String[]{username});
                if (cursor.getCount() > 0) {
                    Toast.makeText(SignUp.this, "Username already exists", Toast.LENGTH_SHORT).show();
                    return;
                }

                ContentValues myvalue = new ContentValues();
                myvalue.put("username", username);
                myvalue.put("password", password);

                long result = mydatabase.insert("users", null, myvalue);
                if (result != -1) {
                    Intent intent = new Intent(SignUp.this, login.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(SignUp.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Database", "Insertion failed");
                    Toast.makeText(SignUp.this, "Registration failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        tvLoginNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUp.this, login.class);
                startActivity(intent);
            }
        });
    }
}
