package com.example.licenta.smartdoctor.dao;

import com.example.licenta.smartdoctor.models.Pacient_Formular2;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Pacient_Formular2Dao {
    private static final String FORMULAR2_COLLECTION = "formular2";
    private FirebaseFirestore db;

    public Pacient_Formular2Dao(FirebaseFirestore db) {
        this.db = db;
    }

    public Task<DocumentSnapshot> getFormular2(String id) {
        return db.collection(FORMULAR2_COLLECTION).document(id).get();
    }

    public Task<QuerySnapshot> getAll() {
        return db.collection(FORMULAR2_COLLECTION).get();
    }

    public Task<DocumentReference> addFormular2(Pacient_Formular2 formular2) {
        return db.collection(FORMULAR2_COLLECTION).add(formular2);
    }

    public Task<QuerySnapshot> getFormular2PentruPacient(String pacientId) {
        return db.collection(FORMULAR2_COLLECTION).whereEqualTo("id_pacient", pacientId).get();
    }
}
