package qdesign.project.firebase;

import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Firebase {
    private static Firebase single_instance = null;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    public static Firebase getInstance(){
        if (single_instance == null){
            single_instance = new Firebase();
        }
        return single_instance;
    }

    public FirebaseFirestore getDb(){
        return db;
    }
}
