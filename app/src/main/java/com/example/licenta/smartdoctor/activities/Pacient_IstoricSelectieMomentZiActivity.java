package com.example.licenta.smartdoctor.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.dao.Pacient_Formular2Dao;
import com.example.licenta.smartdoctor.models.Pacient_Formular2;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Pacient_IstoricSelectieMomentZiActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private ListView pacientIstoricSelectieMomentZiListView;

    private Pacient_Formular2Dao formular2Dao;
    private List<String> momentZiOraListItems;
    private List<Pacient_Formular2> formulare;
    
    private String dataSelectata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pacient_istoric_selectie_moment_zi);

        pacientIstoricSelectieMomentZiListView = findViewById(R.id.pacientIstoricSelectieMomentZiListView);
        pacientIstoricSelectieMomentZiListView.setOnItemClickListener(this);
        
        dataSelectata = getIntent().getStringExtra("data_selectata");

        formular2Dao = new Pacient_Formular2Dao(FirebaseFirestore.getInstance());
        momentZiOraListItems = new ArrayList<>();
        
        populeazaListaMomentZi();
    }

    private void populeazaListaMomentZi() {
        final ArrayAdapter<String> momentZiAdapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_list_item_1,
                momentZiOraListItems
        );
        pacientIstoricSelectieMomentZiListView.setAdapter(momentZiAdapter);

        String pacientId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        formular2Dao.getFormular2PentruPacient(pacientId)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        formulare = queryDocumentSnapshots.toObjects(Pacient_Formular2.class);
                        for (Pacient_Formular2 formular2: formulare) {
                            if (formular2.getData_ora().contains(dataSelectata)) {
                                String ora = formular2.getData_ora();
                                int indexOra = ora.indexOf(" ");
                                ora = ora.substring(indexOra + 1);
                                momentZiOraListItems.add(formular2.getMomentulZilei() + " " + ora);
                            }
                        }

                        // Afisare noi date in lista
                        momentZiAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        // Mesaj eroare
                    }
                });
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String elementSelectat = momentZiOraListItems.get(position);
        int indexSeparator = elementSelectat.indexOf(" ");
        String momentZiSelectat = elementSelectat.substring(0, indexSeparator);
        String oraSelectata = elementSelectat.substring(indexSeparator + 1);

        Pacient_Formular2 formularSelectat = null;
        for (Pacient_Formular2 formular: formulare) {
            if (formular.getData_ora().contains(oraSelectata) && formular.getMomentulZilei().equals(momentZiSelectat)) {
                formularSelectat = formular;
                break;
            }
        }

        Intent intent = new Intent(this, Pacient_VizualizareIstoric.class);
        intent.putExtra("formular_selectat", formularSelectat);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(this);
    }
}