package com.example.licenta.smartdoctor.dao;

import com.example.licenta.smartdoctor.models.Pacient_Formular1;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Pacient_Formular1Dao {
    private static final String FORMULAR1_COLLECTION = "formular1";
    private FirebaseFirestore db;

    public Pacient_Formular1Dao(FirebaseFirestore db) {
        this.db = db;
    }

    public Task<Void> adaugareFormular1(String idUtilizator, Pacient_Formular1 pacientFormular1) {
        return db.collection(FORMULAR1_COLLECTION).document(idUtilizator).set(pacientFormular1);
    }

    public Task<DocumentSnapshot> getFormular1(String idUtilizator) {
        return db.collection(FORMULAR1_COLLECTION).document(idUtilizator).get();
    }
}
