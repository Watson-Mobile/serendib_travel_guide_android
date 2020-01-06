package com.watson.serendibtravelguide;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.watson.serendibtravelguide.ui.Register.MainRegisterActivity;
import com.watson.serendibtravelguide.ui.Register.MapTestActivity;
import com.watson.serendibtravelguide.ui.Register.MapsActivity;
import com.watson.serendibtravelguide.ui.Register.RegisterTravellerActivity;
import com.watson.serendibtravelguide.ui.userlogin.LoginActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {
    Button login_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        login_btn = findViewById(R.id.loginpage_btn);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
//        AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
//                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications)
//                .build();
//        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
//        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
//        NavigationUI.setupWithNavController(navView, navController);

    }

    public void goToLoginPage(View view)
    {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void goToRegisterPage(View view)
    {
        Intent intent = new Intent(this,MainRegisterActivity.class);
        startActivity(intent);

    }

    public void goMapPage(View view)
    {
        Intent intent = new Intent(this, MapsActivity.class);
        startActivity(intent);

    }

}
