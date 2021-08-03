package com.example.licenta.smartdoctor.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.dao.Pacient_Formular1Dao;
import com.example.licenta.smartdoctor.models.Pacient_Formular1;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pacient_JurnalulMeuFragment extends Fragment {

    private TextView tipDiabet;
    private TextView terapieDiabet;
    private TextView medicamente;
    private TextView glucometru;
    private TextView senzor;
    private TextView alergii;


    private String idUtilizatorLogat;
    private Pacient_Formular1Dao pacientFormular1Dao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pacientFormular1Dao = new Pacient_Formular1Dao(FirebaseFirestore.getInstance());
        idUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_jurnalul_meu_pacient, container, false);

        tipDiabet = view.findViewById(R.id.tipDiabet);
        terapieDiabet = view.findViewById(R.id.terapieDiabet);
        medicamente = view.findViewById(R.id.medicamente);
        glucometru = view.findViewById(R.id.glucometru);
        senzor = view.findViewById(R.id.senzor);
        alergii = view.findViewById(R.id.alergii);
        afiseazaFormular1();

        return view;
    }

    private void afiseazaFormular1() {
        pacientFormular1Dao.getFormular1(idUtilizatorLogat)
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            Pacient_Formular1 pacientFormular1 = task.getResult().toObject(Pacient_Formular1.class);
                            tipDiabet.setText(pacientFormular1.getTipul_diabetului());
                            terapieDiabet.setText(pacientFormular1.getTerapie_de_diabet());
                            medicamente.setText(pacientFormular1.getMedicamente());
                            glucometru.setText(pacientFormular1.getTip_glucometru());
                            senzor.setText(pacientFormular1.getTip_senzor());
                            alergii.setText(pacientFormular1.getAlergii());
                        }
                    }
                });
    }
}