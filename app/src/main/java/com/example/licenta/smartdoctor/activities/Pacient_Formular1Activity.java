package com.example.licenta.smartdoctor.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.dao.Pacient_Formular1Dao;
import com.example.licenta.smartdoctor.enums.EMedicamente;
import com.example.licenta.smartdoctor.enums.ETerapieDeDiabet;
import com.example.licenta.smartdoctor.enums.ETipGlucometru;
import com.example.licenta.smartdoctor.enums.ETipSenzor;
import com.example.licenta.smartdoctor.enums.ETipulDiabetului;
import com.example.licenta.smartdoctor.fragments.Pacient_JurnalulMeuFragment;
import com.example.licenta.smartdoctor.models.Pacient_Formular1;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pacient_Formular1Activity extends AppCompatActivity {

    private Spinner spTipDiabet;
    private Spinner spTerapieDiabet;
    private Spinner spMedicamente;
    private Spinner spGlucometru;
    private Spinner spSenzor;
    private EditText etAlergii;
    private Button btFormular1;

    private String idUtilizatorLogat;
    private Pacient_Formular1Dao pacientFormular1Dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formular1_pacient);

        spTipDiabet=findViewById(R.id.spTipDiabet);
        spTerapieDiabet=findViewById(R.id.spTerapieDiabet);
        spMedicamente=findViewById(R.id.spMedicamente);
        spGlucometru=findViewById(R.id.spGlucometru);
        spSenzor=findViewById(R.id.spSenzor);
        etAlergii=findViewById(R.id.etAlergii);
        btFormular1=findViewById(R.id.btnFormular1);
        btFormular1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getApplicationContext(), Pacient_JurnalulMeuFragment.class);
                startActivity(intent);
            }
        });


        populeazaSpinnere();

        idUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
        pacientFormular1Dao = new Pacient_Formular1Dao(FirebaseFirestore.getInstance());

        btFormular1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tipDiabet = spTipDiabet.getSelectedItem().toString();
                String terapieDiabet = spTerapieDiabet.getSelectedItem().toString();
                String medicamente = spMedicamente.getSelectedItem().toString();
                String glucometru = spGlucometru.getSelectedItem().toString();
                String senzor = spSenzor.getSelectedItem().toString();
                String alergii = etAlergii.getText().toString();

                Pacient_Formular1 pacientFormular1 = new Pacient_Formular1();
                pacientFormular1.setTipul_diabetului(tipDiabet);
                pacientFormular1.setTerapie_de_diabet(terapieDiabet);
                pacientFormular1.setMedicamente(medicamente);
                pacientFormular1.setTip_glucometru(glucometru);
                pacientFormular1.setTip_senzor(senzor);
                pacientFormular1.setAlergii(alergii);

                pacientFormular1Dao.adaugareFormular1(idUtilizatorLogat, pacientFormular1)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                // mesaj succes
                                Intent intent = new Intent(Pacient_Formular1Activity.this, Pacient_MenuActivity.class);
                                startActivity(intent);
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                // Mesaj eroare
                            }
                        });
            }
        });
    }

    private void populeazaSpinnere() {
        // Tip diabet
        String[] spTipDiabetOptiuni = new String[ETipulDiabetului.values().length];

        for (int i = 0; i < ETipulDiabetului.values().length; i++) {
            spTipDiabetOptiuni[i] = ETipulDiabetului.values()[i].getName();
        }

        ArrayAdapter<String> spTipDiabetAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                spTipDiabetOptiuni
        );

        spTipDiabet.setAdapter(spTipDiabetAdapter);

        // Terapie diabet
        String[] spTerapieDiabetOptiuni = new String[ETerapieDeDiabet.values().length];

        for (int i = 0; i < ETerapieDeDiabet.values().length; i++) {
            spTerapieDiabetOptiuni[i] = ETerapieDeDiabet.values()[i].getName();
        }

        ArrayAdapter<String> spTerapieDiabetAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                spTerapieDiabetOptiuni
        );

        spTerapieDiabet.setAdapter(spTerapieDiabetAdapter);

        // Medicamente
        String[] spMedicamenteOptiuni = new String[EMedicamente.values().length];

        for (int i = 0; i < EMedicamente.values().length; i++) {
            spMedicamenteOptiuni[i] = EMedicamente.values()[i].name();
        }

        ArrayAdapter<String> spMedicamenteAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                spMedicamenteOptiuni
        );

        spMedicamente.setAdapter(spMedicamenteAdapter);


        // Tip Glucometru
        String[] spGlucometruOptiuni = new String[ETipGlucometru.values().length];

        for (int i = 0; i < ETipGlucometru.values().length; i++) {
            spGlucometruOptiuni[i] = ETipGlucometru.values()[i].getName();
        }

        ArrayAdapter<String> spGlucometruAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                spGlucometruOptiuni
        );

        spGlucometru.setAdapter(spGlucometruAdapter);


        // Tip Senzor
        String[] spSenzorOptiuni = new String[ETipSenzor.values().length];

        for (int i = 0; i < ETipSenzor.values().length; i++) {
            spSenzorOptiuni[i] = ETipSenzor.values()[i].getName();
        }

        ArrayAdapter<String> spSenzorAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                spSenzorOptiuni
        );

        spSenzor.setAdapter(spSenzorAdapter);

    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(this);
    }
}