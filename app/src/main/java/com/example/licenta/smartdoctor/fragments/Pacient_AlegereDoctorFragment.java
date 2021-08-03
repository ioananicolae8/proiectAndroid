package com.example.licenta.smartdoctor.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.activities.ChatActivity;
import com.example.licenta.smartdoctor.adapters.AlegereDoctorAdapter;
import com.example.licenta.smartdoctor.adapters.GenericAdapter;
import com.example.licenta.smartdoctor.dao.Doctor_UserDao;
import com.example.licenta.smartdoctor.dao.Pacient_UserDao;
import com.example.licenta.smartdoctor.enums.ETipUtilizator;
import com.example.licenta.smartdoctor.models.Doctor_User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Pacient_AlegereDoctorFragment extends Fragment {

    private RecyclerView alegereDoctorRecyclerView;

    private String idUserLogat;
    private Doctor_UserDao doctorUserDao;
    private Pacient_UserDao pacientUserDao;
    private List<Doctor_User> listaDoctori;
    private List<String> iduriDoctori;
    private GenericAdapter alegereDoctorAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idUserLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
        doctorUserDao = new Doctor_UserDao(FirebaseFirestore.getInstance());
        pacientUserDao = new Pacient_UserDao(FirebaseFirestore.getInstance());
        listaDoctori = new ArrayList<>();
        iduriDoctori = new ArrayList<>();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pacient__alegere_doctor, container, false);
        alegereDoctorRecyclerView = view.findViewById(R.id.alegereDoctorRecyclerView);
        populeazaListaDoctori();

        return view;
    }

    private void populeazaListaDoctori() {
        alegereDoctorAdapter = new AlegereDoctorAdapter(this.getContext(), listaDoctori, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final int pozitieSelectata = alegereDoctorRecyclerView.getChildLayoutPosition(v);
                final Doctor_User doctorSelectat = listaDoctori.get(pozitieSelectata);

                AlertDialog.Builder builder = new AlertDialog.Builder(Pacient_AlegereDoctorFragment.this.getContext())
                        .setTitle("Alegere doctor")
                        .setMessage("Sunteti sigur ca vreti sa alegeti ca si doctor pe Dr. " +
                                doctorSelectat.getNume() + " " + doctorSelectat.getPrenume() + "?")
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                pacientUserDao.adaugareDoctor(
                                        idUserLogat,
                                        iduriDoctori.get(pozitieSelectata),
                                        doctorSelectat.getEmail())
                                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                                            @Override
                                            public void onSuccess(Void unused) {
                                                Intent intent = new Intent(Pacient_AlegereDoctorFragment.this.getContext(), ChatActivity.class);
                                                intent.putExtra("idDestinatar", iduriDoctori.get(pozitieSelectata));
                                                intent.putExtra("emailDestinatar", doctorSelectat.getEmail());
                                                intent.putExtra("tipDestinatar", ETipUtilizator.DOCTOR.name());
                                                startActivity(intent);
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(Exception e) {
                                                e.printStackTrace();
                                                Toast.makeText(Pacient_AlegereDoctorFragment.this.getContext(), "Eroare selectare doctor.", Toast.LENGTH_SHORT).show();
                                            }
                                        });
                            }
                        })
                        .setNegativeButton(R.string.no, null);

                builder.show();
            }
        });

        doctorUserDao.getAll()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        listaDoctori.addAll(queryDocumentSnapshots.toObjects(Doctor_User.class));
                        alegereDoctorAdapter.notifyDataSetChanged();

                        for (DocumentSnapshot doc : queryDocumentSnapshots.getDocuments()) {
                            iduriDoctori.add(doc.getId());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Pacient_AlegereDoctorFragment.this.getContext(), "Eroare incarcare lista doctori.", Toast.LENGTH_SHORT).show();
                    }
                });

        alegereDoctorRecyclerView.setAdapter(alegereDoctorAdapter);
        alegereDoctorRecyclerView.setLayoutManager(new LinearLayoutManager(this.getContext()));
    }
}