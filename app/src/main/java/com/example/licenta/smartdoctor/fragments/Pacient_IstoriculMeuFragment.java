package com.example.licenta.smartdoctor.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.activities.Pacient_IstoricSelectieMomentZiActivity;
import com.example.licenta.smartdoctor.dao.Pacient_Formular2Dao;
import com.example.licenta.smartdoctor.models.Pacient_Formular2;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Pacient_IstoriculMeuFragment extends Fragment implements AdapterView.OnItemClickListener {

    private ListView istoriculMeuListView;

    private Pacient_Formular2Dao formular2Dao;
    private List<String> istoriculMeuListItems;
    private List<Pacient_Formular2> formulare;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        formular2Dao = new Pacient_Formular2Dao(FirebaseFirestore.getInstance());
        istoriculMeuListItems = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_istoricul_meu_pacient, container, false);
        istoriculMeuListView = view.findViewById(R.id.istoriculMeuListView);
        istoriculMeuListView.setOnItemClickListener(this);
        populeazaIstoriculMeu();

        return view;
    }

    private void populeazaIstoriculMeu() {
        final ArrayAdapter<String> istoriculMeuAdapter = new ArrayAdapter<>(
                this.getContext(),
                android.R.layout.simple_list_item_1,
                istoriculMeuListItems
        );
        istoriculMeuListView.setAdapter(istoriculMeuAdapter);

        String pacientId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        formular2Dao.getFormular2PentruPacient(pacientId)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        formulare = queryDocumentSnapshots.toObjects(Pacient_Formular2.class);
                        for (Pacient_Formular2 formular2: formulare) {
                            String data = formular2.getData_ora();
                            int indexSfarsitData = data.indexOf(" ");
                            data = data.substring(0, indexSfarsitData);
                            istoriculMeuListItems.add(data);
                        }

                        // Stergere duplicate
                        Set<String> dateUnice = new HashSet<>(istoriculMeuListItems);
                        istoriculMeuListItems.clear();
                        istoriculMeuListItems.addAll(dateUnice);

                        // Sortare date descrescator
                        Collections.sort(istoriculMeuListItems, new Comparator<String>() {
                            @Override
                            public int compare(String o1, String o2) {
                                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    Date o1Date = dateFormat.parse(o1);
                                    Date o2Date = dateFormat.parse(o2);
                                    return o2Date.compareTo(o1Date);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                return -1;
                            }
                        });

                        // Afisare noi date in lista
                        istoriculMeuAdapter.notifyDataSetChanged();
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
        String dataSelectata = istoriculMeuListItems.get(position);
        Intent intent = new Intent(this.getContext(), Pacient_IstoricSelectieMomentZiActivity.class);
        intent.putExtra("data_selectata", dataSelectata);
        startActivity(intent);
    }
}