package com.example.phoneauthentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class AddDetails extends AppCompatActivity {

    EditText firstName, lastName, emailAddress;
    Button saveBtn;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_details);

        firstName = findViewById(R.id.firstName);
        lastName = findViewById(R.id.lastName);
        emailAddress = findViewById(R.id.emailAddress);
        saveBtn = findViewById(R.id.saveBtn);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        final DocumentReference docRef = fStore.collection("users").document(userID);

        saveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String first = firstName.getText().toString();
                String last = lastName.getText().toString();
                String email = emailAddress.getText().toString();

                if (!first.isEmpty() && !last.isEmpty() && !email.isEmpty()) {

                    Map<String, Object> user = new HashMap<>();
                    user.put("firstName", first);
                    user.put("lastName", last);
                    user.put("emailAddress", email);

                    docRef.set(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                               if(task.isSuccessful()){
                                   startActivity(new Intent(getApplicationContext(), MainActivity.class));
                                   finish();
                               }else {
                                   Toast.makeText(AddDetails.this, "Data is not Inserted.", Toast.LENGTH_SHORT).show();
                               }
                        }
                    });

                } else {
                    Toast.makeText(AddDetails.this, "All Fields are Required.", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
    }
}
