package com.example.licenta.smartdoctor.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.adapters.GenericAdapter;
import com.example.licenta.smartdoctor.adapters.ProgramareAdapter;
import com.example.licenta.smartdoctor.dao.Doctor_ProgramariDao;
import com.example.licenta.smartdoctor.models.Doctor_Programari;
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
import java.util.List;

public class Doctor_ProgramariFragment extends Fragment {

    private String idUserLogat;
    private RecyclerView programariRecyclerView;

    private GenericAdapter adapter;
    private List<Doctor_Programari> programari;
    private Doctor_ProgramariDao programariDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idUserLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
        programari = new ArrayList<>();
        programariDao = new Doctor_ProgramariDao(FirebaseFirestore.getInstance());
        adapter = new ProgramareAdapter(this.getContext(), programari, null);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor__programari, container, false);
        programariRecyclerView = view.findViewById(R.id.programariRecyclerView);
        populeazaListaProgramari();

        return view;
    }

    private void populeazaListaProgramari() {
        programariRecyclerView.setAdapter(adapter);
        programariRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));

        programariDao.getProgramariPentruDoctor(idUserLogat)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        programari.addAll(queryDocumentSnapshots.toObjects(Doctor_Programari.class));

                        // Sortare date descrescator
                        Collections.sort(programari, new Comparator<Doctor_Programari>() {
                                    @Override
                                    public int compare(Doctor_Programari o1, Doctor_Programari o2) {
                                        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                        try {
                                            String data1 = o1.getDataOra();
                                            int indexSfarsitData1 = data1.indexOf(" ");
                                            data1 = data1.substring(0, indexSfarsitData1);

                                            String data2 = o2.getDataOra();
                                            int indexSfarsitData2 = data2.indexOf(" ");
                                            data2 = data2.substring(0, indexSfarsitData2);

                                            Date o1Date = dateFormat.parse(data1);
                                            Date o2Date = dateFormat.parse(data2);
                                            return o2Date.compareTo(o1Date);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }

                                        return -1;
                                    }
                                });

                        adapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}