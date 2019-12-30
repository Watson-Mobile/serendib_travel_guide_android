package com.watson.serendibtravelguide.ui.Register;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import com.watson.serendibtravelguide.R;

public class MainRegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_register);
    }

    public void goToTravellerRegisterPage(){
        Intent intent = new Intent(this, RegisterTravellerActivity.class);
        startActivity(intent);
    }

    public void goToLocalGuideRegisterPage(){
        Intent intent = new Intent(this, RegisterLocalGuideActivity.class);
        startActivity(intent);
    }
}
