package com.example.licenta.smartdoctor.dao;

import com.example.licenta.smartdoctor.models.Pacient_User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Pacient_UserDao {

    private static final String USER_COLLECTION = "users";
    private static final String DOCTOR_ID_KEY = "doctorId";
    private static final String DOCTOR_EMAIL_KEY = "doctorEmail";

    private FirebaseFirestore db;

    public Pacient_UserDao(FirebaseFirestore db) {
        this.db = db;
    }

    public Task<Void> addUserToDb(String id, Pacient_User pacientUser) {
        return db.collection(USER_COLLECTION).document(id).set(pacientUser);
    }

    public Task<DocumentSnapshot> getUserFromDb(String id) {
        return db.collection(USER_COLLECTION).document(id).get();
    }

    public Task<Void> adaugareDoctor(String userId, String doctorId, String doctorEmail) {
        Map<String, Object> data = new HashMap<>();
        data.put(DOCTOR_ID_KEY, doctorId);
        data.put(DOCTOR_EMAIL_KEY, doctorEmail);

        return db.collection(USER_COLLECTION).document(userId)
                .set(data, SetOptions.merge());
    }
}
