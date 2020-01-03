package com.watson.serendibtravelguide.ui.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.watson.serendibtravelguide.R;

public class MainRegisterActivity extends AppCompatActivity {

    Button btn_traveller;
    Button btn_local_guide;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
        btn_traveller = (Button) findViewById(R.id.RegisterTraveller);
        btn_traveller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainRegisterActivity.this, RegisterTravellerActivity.class);
                startActivity(intent);
            }
        });

        btn_local_guide = (Button)findViewById(R.id.RegisterLocalGuide);
        btn_local_guide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainRegisterActivity.this, RegisterLocalGuideActivity.class);
                startActivity(intent);
            }
        });

    }

}

