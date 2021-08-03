package com.example.licenta.smartdoctor.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.activities.Pacient_Formular2Activity;
import com.example.licenta.smartdoctor.dao.Pacient_Formular2Dao;
import com.example.licenta.smartdoctor.enums.EMomentulZilei;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class Pacient_HomeFragment extends Fragment {

    private Button btDimineata;
    private Button btPranz;
    private Button btSeara;
    private Button btNoaptea;

    private String idUtilizatorLogat;
    private Pacient_Formular2Dao pacientFormular2Dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pacientFormular2Dao = new Pacient_Formular2Dao(FirebaseFirestore.getInstance());
        idUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_pacient, container, false);

        btDimineata = view.findViewById(R.id.btDimineata);
        btPranz = view.findViewById(R.id.btPranz);
        btSeara = view.findViewById(R.id.btSeara);
        btNoaptea = view.findViewById(R.id.btNoaptea);

        btDimineata.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(Pacient_HomeFragment.this.getContext(), Pacient_Formular2Activity.class);
                intent1.putExtra("momentulZileiSelectat", EMomentulZilei.DIMINEATA.name());
                startActivity(intent1);
            }
        });
        btPranz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent(Pacient_HomeFragment.this.getContext(), Pacient_Formular2Activity.class);
                intent2.putExtra("momentulZileiSelectat", EMomentulZilei.PRANZ.name());
                startActivity(intent2);
            }
        });
        btSeara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent3 = new Intent(Pacient_HomeFragment.this.getContext(), Pacient_Formular2Activity.class);
                intent3.putExtra("momentulZileiSelectat", EMomentulZilei.SEARA.name());
                startActivity(intent3);
            }
        });
        btNoaptea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent4 = new Intent(Pacient_HomeFragment.this.getContext(), Pacient_Formular2Activity.class);
                intent4.putExtra("momentulZileiSelectat", EMomentulZilei.NOAPTE.name());
                startActivity(intent4);
            }
        });

        return view;
    }
}