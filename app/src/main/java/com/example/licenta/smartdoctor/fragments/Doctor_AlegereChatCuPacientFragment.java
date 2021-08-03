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
import android.widget.Toast;

import com.example.licenta.R;
import com.example.licenta.smartdoctor.activities.ChatActivity;
import com.example.licenta.smartdoctor.dao.MesajChatDao;
import com.example.licenta.smartdoctor.enums.ETipUtilizator;
import com.example.licenta.smartdoctor.models.MesajChat;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Doctor_AlegereChatCuPacientFragment extends Fragment {

    private ListView alegereChatCuPacientListView;

    private String idDoctorLogat;
    private List<String> convorbiriCuPacienti;
    private List<MesajChat> mesaje;
    private ArrayAdapter<String> listAdapter;

    private MesajChatDao mesajChatDao;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idDoctorLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
        convorbiriCuPacienti = new ArrayList<>();
        mesajChatDao = new MesajChatDao(FirebaseFirestore.getInstance());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_doctor__alegere_chat_cu_pacient, container, false);
        alegereChatCuPacientListView = view.findViewById(R.id.alegereChatCuPacientListView);
        populeazaListaChatCuPacienti();

        return view;
    }

    private void populeazaListaChatCuPacienti() {
        listAdapter = new ArrayAdapter<>(this.getContext(), android.R.layout.simple_list_item_1, convorbiriCuPacienti);
        alegereChatCuPacientListView.setAdapter(listAdapter);

        // Selectie pacient pentru chat
        alegereChatCuPacientListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String emailPacientConversatieSelectat = convorbiriCuPacienti.get(position);
                String idPacientConversatieSelectat = null;

                for (MesajChat mesajChat: mesaje) {
                    if (mesajChat.getPacientEmail().equals(emailPacientConversatieSelectat)) {
                        idPacientConversatieSelectat = mesajChat.getPacientId();
                        break;
                    }
                }

                Intent intent = new Intent(Doctor_AlegereChatCuPacientFragment.this.getContext(), ChatActivity.class);
                intent.putExtra("idDestinatar", idPacientConversatieSelectat);
                intent.putExtra("emailDestinatar", emailPacientConversatieSelectat);
                intent.putExtra("tipDestinatar", ETipUtilizator.PACIENT.name());
                startActivity(intent);
            }
        });

        mesajChatDao.getMesajePentruDoctor(idDoctorLogat)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        mesaje = queryDocumentSnapshots.toObjects(MesajChat.class);

                        convorbiriCuPacienti.clear();
                        for (MesajChat mesajChat : mesaje) {
                            if (!convorbiriCuPacienti.contains(mesajChat.getPacientEmail())) {
                                convorbiriCuPacienti.add(mesajChat.getPacientEmail());
                            }
                        }

                        listAdapter.notifyDataSetChanged();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        e.printStackTrace();
                        Toast.makeText(Doctor_AlegereChatCuPacientFragment.this.getContext(), "Eroare incarcare conversatii", Toast.LENGTH_SHORT).show();
                    }
                });

        mesajChatDao.getMesajePentruDoctorQuery(idDoctorLogat)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                        if (error != null) {
                            error.printStackTrace();
                            Toast.makeText(Doctor_AlegereChatCuPacientFragment.this.getContext(), "Eroare incarcare conversatii", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        mesaje = value.toObjects(MesajChat.class);

                        convorbiriCuPacienti.clear();
                        for (MesajChat mesajChat : mesaje) {
                            if (!convorbiriCuPacienti.contains(mesajChat.getPacientEmail())) {
                                convorbiriCuPacienti.add(mesajChat.getPacientEmail());
                            }
                        }

                        listAdapter.notifyDataSetChanged();
                    }
                });
    }
}