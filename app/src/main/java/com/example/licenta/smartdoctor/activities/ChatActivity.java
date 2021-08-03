package com.example.licenta.smartdoctor.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.licenta.R;
import com.example.licenta.smartdoctor.adapters.GenericAdapter;
import com.example.licenta.smartdoctor.adapters.MesajChatAdapter;
import com.example.licenta.smartdoctor.dao.Doctor_UserDao;
import com.example.licenta.smartdoctor.dao.MesajChatDao;
import com.example.licenta.smartdoctor.dao.Pacient_UserDao;
import com.example.licenta.smartdoctor.enums.ETipUtilizator;
import com.example.licenta.smartdoctor.models.Doctor_User;
import com.example.licenta.smartdoctor.models.MesajChat;
import com.example.licenta.smartdoctor.models.Pacient_User;
import com.example.licenta.smartdoctor.utils.common.AuthenticationHelper;
import com.example.licenta.smartdoctor.utils.common.ConnectionHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView fragmentChatRecyclerView;
    private EditText fragmentChatMesajEditText;
    private Button fragmentChatTrimitereButton;

    private ImageView ivChatAvatar;
    private TextView tvChatDetalii;

    private GenericAdapter mesajChatAdapter;
    private List<MesajChat> mesaje;
    private MesajChatDao mesajChatDao;

    private String idUtilizatorLogat;
    private String emailUtilizatorLogat;
    private String idDestinatar;
    private String emailDestinatar;
    private ETipUtilizator tipDestinatar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        fragmentChatRecyclerView = findViewById(R.id.fragmentChatRecyclerView);
        fragmentChatMesajEditText = findViewById(R.id.fragmentChatMesajEditText);
        fragmentChatTrimitereButton = findViewById(R.id.fragmentChatTrimitereButton);
        fragmentChatTrimitereButton.setOnClickListener(this);

        ivChatAvatar = findViewById(R.id.ivChatAvatar);
        tvChatDetalii = findViewById(R.id.tvChatDetalii);

        mesaje = new ArrayList<>();
        mesajChatDao = new MesajChatDao(FirebaseFirestore.getInstance());

        idUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getUid();
        emailUtilizatorLogat = FirebaseAuth.getInstance().getCurrentUser().getEmail();

        idDestinatar = getIntent().getStringExtra("idDestinatar");
        emailDestinatar = getIntent().getStringExtra("emailDestinatar");
        tipDestinatar = ETipUtilizator.valueOf(getIntent().getStringExtra("tipDestinatar"));

        setAvatarDestinatar();
        setDetaliiDestinatar();
        populeazaChat();
    }

    @Override
    public void onBackPressed() {
        if (tipDestinatar == ETipUtilizator.DOCTOR) {
            Intent intent = new Intent(this, Pacient_MenuActivity.class);
            startActivity(intent);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.fragmentChatTrimitereButton) {
            MesajChat mesajChat;
            String dataOraMesaj = new SimpleDateFormat(MesajChat.FORMAT_DATA).format(new Date());

            if (tipDestinatar.equals(ETipUtilizator.DOCTOR)) {
                // Mesaj de la pacient catre doctor
                mesajChat = new MesajChat(
                        idUtilizatorLogat,
                        emailUtilizatorLogat,
                        idDestinatar,
                        emailDestinatar,
                        fragmentChatMesajEditText.getText().toString(),
                        dataOraMesaj,
                        emailUtilizatorLogat
                );
            } else {
                // Mesaj de la doctor catre pacient
                mesajChat = new MesajChat(
                        idDestinatar,
                        emailDestinatar,
                        idUtilizatorLogat,
                        emailUtilizatorLogat,
                        fragmentChatMesajEditText.getText().toString(),
                        dataOraMesaj,
                        emailUtilizatorLogat
                );
            }

            mesajChatDao.adaugareMesaj(mesajChat)
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(Exception e) {
                            Toast.makeText(ChatActivity.this, "Mesajul nu a fost trimis!", Toast.LENGTH_SHORT).show();
                        }
                    });

            fragmentChatMesajEditText.setText("");
            mesajChatAdapter.notifyDataSetChanged();
            fragmentChatRecyclerView.scrollToPosition(mesaje.size() - 1);
        }
    }

    private void populeazaChat() {
        mesajChatAdapter = new MesajChatAdapter<>(
                this,
                mesaje,
                null,
                emailUtilizatorLogat
        );

        fragmentChatRecyclerView.setAdapter(mesajChatAdapter);
        fragmentChatRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        String pacientId = tipDestinatar.equals(ETipUtilizator.DOCTOR) ? idUtilizatorLogat : idDestinatar;

        // Populare mesaje
        mesajChatDao.getMesajePentruPacient(pacientId)
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        mesaje.clear();
                        mesaje.addAll(queryDocumentSnapshots.toObjects(MesajChat.class));
                        Collections.sort(mesaje, new Comparator<MesajChat>() {
                            @Override
                            public int compare(MesajChat o1, MesajChat o2) {
                                try {
                                    Date dataO1 = new SimpleDateFormat(MesajChat.FORMAT_DATA).parse(o1.getDataOraTrimitere());
                                    Date dataO2 = new SimpleDateFormat(MesajChat.FORMAT_DATA).parse(o2.getDataOraTrimitere());

                                    return dataO1.compareTo(dataO2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                return 0;
                            }
                        });

                        mesajChatAdapter.notifyDataSetChanged();
                        fragmentChatRecyclerView.scrollToPosition(mesaje.size() - 1);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(Exception e) {
                        Toast.makeText(ChatActivity.this, "Mesajele nu pot fi incarcate!", Toast.LENGTH_SHORT).show();
                    }
                });

        // Repopulare automata in caz ca apar mesaje noi
        mesajChatDao.getMesajePentruPacientQuery(pacientId)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(QuerySnapshot value, FirebaseFirestoreException error) {
                        if (error != null) {
                            error.printStackTrace();
                            return;
                        }

                        mesaje.clear();
                        mesaje.addAll(value.toObjects(MesajChat.class));
                        Collections.sort(mesaje, new Comparator<MesajChat>() {
                            @Override
                            public int compare(MesajChat o1, MesajChat o2) {
                                try {
                                    Date dataO1 = new SimpleDateFormat(MesajChat.FORMAT_DATA).parse(o1.getDataOraTrimitere());
                                    Date dataO2 = new SimpleDateFormat(MesajChat.FORMAT_DATA).parse(o2.getDataOraTrimitere());

                                    return dataO1.compareTo(dataO2);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }

                                return 0;
                            }
                        });

                        mesajChatAdapter.notifyDataSetChanged();
                        fragmentChatRecyclerView.scrollToPosition(mesaje.size() - 1);
                    }
                });
    }

    private void setAvatarDestinatar() {
        StorageReference reference = FirebaseStorage
                .getInstance()
                .getReference()
                .child(AuthenticationHelper.getUserAvatarPath(
                        ChatActivity.this,
                        idDestinatar)
                );

        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        String imgUrl = uri.toString();

                        // Loading the image into the navigation drawer
                        Glide.with(ChatActivity.this)
                                .load(imgUrl)
                                .into(ivChatAvatar);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Glide.with(ChatActivity.this)
                                .load(AuthenticationHelper.getDefaultAvatarUrl())
                                .into(ivChatAvatar);
                    }
                });
    }

    private void setDetaliiDestinatar() {
        if (tipDestinatar.equals(ETipUtilizator.PACIENT)) {
            Pacient_UserDao pacientUserDAO = new Pacient_UserDao(FirebaseFirestore.getInstance());

            pacientUserDAO.getUserFromDb(idDestinatar)
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Pacient_User pacientUser = documentSnapshot.toObject(Pacient_User.class);
                            tvChatDetalii.setText(pacientUser.getNume() + " " + pacientUser.getPrenume());
                        }
                    });
        } else {
            Doctor_UserDao doctorUserDao = new Doctor_UserDao(FirebaseFirestore.getInstance());
            doctorUserDao.getUserFromDb(idDestinatar)
                    .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                        @Override
                        public void onSuccess(DocumentSnapshot documentSnapshot) {
                            Doctor_User doctorUser = documentSnapshot.toObject(Doctor_User.class);
                            tvChatDetalii.setText(doctorUser.getNume() + " " + doctorUser.getPrenume());
                        }
                    });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConnectionHelper.hasConnection(this);
    }
}