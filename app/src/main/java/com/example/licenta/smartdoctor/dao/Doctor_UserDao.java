package com.example.licenta.smartdoctor.dao;

import com.example.licenta.smartdoctor.models.Doctor_User;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class Doctor_UserDao {

    private static final String USER_COLLECTION = "doctors";
    private static final String STYLE_KEY = "style";
    private static final String MANDATORY_KEY = "isMandatory";

    private FirebaseFirestore db;

    public Doctor_UserDao(FirebaseFirestore db) {
        this.db = db;
    }

    public Task<Void> addUserToDb(String id, Doctor_User doctorUser) {
        return db.collection(USER_COLLECTION).document(id).set(doctorUser);
    }

    public Task<DocumentSnapshot> getUserFromDb(String id) {
        return db.collection(USER_COLLECTION).document(id).get();
    }

    public Task<QuerySnapshot> getAll() {
        return db.collection(USER_COLLECTION).get();
    }

    public Task<Void> addUserStyle(String userId, String style) {
        Map<String, Object> data = new HashMap<>();
        data.put(STYLE_KEY, style);

        return db.collection(USER_COLLECTION).document(userId)
                .set(data, SetOptions.merge());
    }

    public Task<Void> grantGeneratorAccess(String userId) {
        Map<String, Object> data = new HashMap<>();
        data.put(MANDATORY_KEY, true);

        return db.collection(USER_COLLECTION).document(userId)
                .set(data, SetOptions.merge());
    }

}
