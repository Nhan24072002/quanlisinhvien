package com.example.qunlsinhvin;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

    public class login extends AppCompatActivity {

        EditText edtUsername, edtPassword;
        Button btnLogin;
        TextView signupText;
        SQLiteDatabase mydatabase;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_login);

            edtUsername = findViewById(R.id.username);
            edtPassword = findViewById(R.id.password);
            btnLogin = findViewById(R.id.loginButton);
            signupText = findViewById(R.id.signupText);

            mydatabase = openOrCreateDatabase("StudentDB", MODE_PRIVATE, null);

            btnLogin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String username = edtUsername.getText().toString().trim();
                    String password = edtPassword.getText().toString().trim();

                    if (username.isEmpty() || password.isEmpty()) {
                        Toast.makeText(login.this, "Vui lòng nhập tên tài khoản và mật khẩu", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    Cursor cursor = mydatabase.rawQuery("SELECT * FROM users WHERE username=? AND password=?", new String[]{username, password});
                    if (cursor.getCount() > 0) {
                        Intent intent = new Intent(login.this, page.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(login.this, "Sai tên đăng nhập hoặc mật khẩu", Toast.LENGTH_SHORT).show();
                    }
                    cursor.close();
                }
            });

            signupText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(login.this, SignUp.class);
                    startActivity(intent);
                }
            });
        }
    }
