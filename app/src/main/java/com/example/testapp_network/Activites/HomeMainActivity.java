package com.example.testapp_network.Activites;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.testapp_network.R;
import com.example.testapp_network.Views.PreferenceHelper;
import com.example.testapp_network.databinding.ActivityHomeMainBinding;

public class HomeMainActivity extends AppCompatActivity {

    ActivityHomeMainBinding homeMainBinding;
    PreferenceHelper preferenceHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homeMainBinding = ActivityHomeMainBinding.inflate(getLayoutInflater());
        setContentView(homeMainBinding.getRoot());

        preferenceHelper = PreferenceHelper.getInstans(this);

        homeMainBinding.btnLogOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                preferenceHelper.logout();
                startActivity(new Intent(HomeMainActivity.this,LoginActivity.class));
                finish();

            }
        });
    }
}