package com.example.licenta.smartdoctor.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.enums.ETipUtilizator;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;
import com.example.licenta.smartdoctor.utils.common.PermissionHelper;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    public static ETipUtilizator tipUtilizatorLogat;

    private SharedPreferences sharedPreferences;

    private static final int ALL_PERMISSIONS = 1;
    private static final String[] PERMISSIONS = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.RECORD_AUDIO
    };

    private Button btPacient;
    private Button btDoctor;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pacient);

        auth = FirebaseAuth.getInstance();

        btPacient = findViewById(R.id.btPacient);
        btPacient.setOnClickListener(this);

        btDoctor = findViewById(R.id.btDoctor);
        btDoctor.setOnClickListener(this);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String valoareTipUtilizatorLogat = sharedPreferences.getString("tipUtilizatorLogat", null);

        if (valoareTipUtilizatorLogat != null) {
            tipUtilizatorLogat = ETipUtilizator.valueOf(valoareTipUtilizatorLogat);
        }

        ActivityCompat.requestPermissions(this, PERMISSIONS, ALL_PERMISSIONS);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        if (!PermissionHelper.hasPermissions(this, PERMISSIONS)) {
            ActivityCompat.requestPermissions(this, PERMISSIONS, ALL_PERMISSIONS);
        } else {
            switch (v.getId()) {
                case R.id.btPacient:
                    if (auth.getCurrentUser() != null && tipUtilizatorLogat == ETipUtilizator.PACIENT) {
                        Intent intent = new Intent(MainActivity.this, Pacient_MenuActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, Pacient_LogInActivity.class);
                        startActivity(intent);
                    }
                    break;

                case R.id.btDoctor:
                    if (auth.getCurrentUser() != null && tipUtilizatorLogat == ETipUtilizator.DOCTOR) {
                        Intent intent = new Intent(MainActivity.this, Doctor_MenuActivity.class);
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(MainActivity.this, Doctor_LogInActivity.class);
                        startActivity(intent);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(this);
    }
}