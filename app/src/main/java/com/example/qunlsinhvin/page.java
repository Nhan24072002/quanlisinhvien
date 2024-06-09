package com.example.qunlsinhvin;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class page extends AppCompatActivity {
    CardView appCard2;
    LinearLayout myLinearLayout2;
    LinearLayout logOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page);


        appCard2 = findViewById(R.id.appCard);
        myLinearLayout2 = findViewById(R.id.myLinearLayout);
        logOut = findViewById(R.id.logOut);
        myLinearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện click cho LinearLayout
                Intent intent = new Intent(page.this, MainActivity.class);
                startActivity(intent);
            }
        });

        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Xử lý sự kiện click cho LinearLayout
                Intent intent = new Intent(page.this, login.class);
                startActivity(intent);
            }
        });
    }
}