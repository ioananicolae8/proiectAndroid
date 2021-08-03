package com.example.licenta.smartdoctor.dao;
import com.example.licenta.smartdoctor.models.MesajChat;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

public class MesajChatDao {
    private static final String MESAJ_CHAT_COLLECTION = "chat";
    private static final String KEY_PACIENT = "pacientId";
    private static final String KEY_DOCTOR = "doctorId";

    private FirebaseFirestore db;

    public MesajChatDao(FirebaseFirestore db) {
        this.db = db;
    }

    public Query getMesajePentruPacientQuery(String pacientId) {
        return db.collection(MESAJ_CHAT_COLLECTION)
                .whereEqualTo(KEY_PACIENT, pacientId);
    }

    public Query getMesajePentruDoctorQuery(String doctorId) {
        return db.collection(MESAJ_CHAT_COLLECTION)
                .whereEqualTo(KEY_DOCTOR, doctorId);
    }

    public Task<QuerySnapshot> getMesajePentruPacient(String pacientId) {
        return db.collection(MESAJ_CHAT_COLLECTION)
                .whereEqualTo(KEY_PACIENT, pacientId).get();
    }

    public Task<QuerySnapshot> getMesajePentruDoctor(String doctorId) {
        return db.collection(MESAJ_CHAT_COLLECTION)
                .whereEqualTo(KEY_DOCTOR, doctorId).get();
    }

    public Task<DocumentReference> adaugareMesaj(MesajChat mesajChat) {
        return db.collection(MESAJ_CHAT_COLLECTION).add(mesajChat);
    }
}
