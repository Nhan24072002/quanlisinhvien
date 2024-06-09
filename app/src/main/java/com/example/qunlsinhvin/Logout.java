package com.example.qunlsinhvin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class Logout extends AppCompatActivity {
    CardView appCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);

        Button btnLoginAgain = findViewById(R.id.btnLoginAgain);

        btnLoginAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Chuyển về màn hình đăng nhập
                Intent intent = new Intent(Logout.this, login.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
