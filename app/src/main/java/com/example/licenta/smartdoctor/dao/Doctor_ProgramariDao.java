package com.example.licenta.smartdoctor.dao;

import com.example.licenta.smartdoctor.models.Doctor_Programari;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

public class Doctor_ProgramariDao {
    private static final String PROGRAMARI_COLLECTION = "programari";
    private FirebaseFirestore db;

    public Doctor_ProgramariDao(FirebaseFirestore db) {
        this.db = db;
    }

    public Task<DocumentReference> addProgramare(Doctor_Programari doctor_programari) {
        return db.collection(PROGRAMARI_COLLECTION).add(doctor_programari);
    }

    public Task<QuerySnapshot> getProgramariPentruDoctor(String doctorId) {
        return db.collection(PROGRAMARI_COLLECTION).whereEqualTo("idDoctor", doctorId).get();
    }
}
