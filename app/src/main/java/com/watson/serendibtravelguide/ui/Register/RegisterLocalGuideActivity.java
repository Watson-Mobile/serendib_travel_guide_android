package com.watson.serendibtravelguide.ui.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.watson.serendibtravelguide.R;

public class RegisterLocalGuideActivity extends AppCompatActivity {

    Button btn_nextPage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_local_guide);
        btn_nextPage = (Button)findViewById(R.id.Next);
        btn_nextPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterLocalGuideActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });
    }




}
