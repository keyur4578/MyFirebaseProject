package com.karon.myfirebaseproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class UpdateUser extends AppCompatActivity {


    EditText edtUserName,edtMobile,edtEmail,edtAbout;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_update_user);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Enable back button in toolbar
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        String docid = getIntent().getStringExtra("docid");
        String firstname = getIntent().getStringExtra("firstname");
        String email = getIntent().getStringExtra("email");
        String mobilenumber = getIntent().getStringExtra("mobilenumber");
        String aboutme = getIntent().getStringExtra("aboutme");

        btnUpdate = (Button) findViewById(R.id.btnUpdate);
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        edtMobile = (EditText) findViewById(R.id.edtMobile);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtAbout = (EditText) findViewById(R.id.edtAbout);

        edtUserName.setText(firstname);
        edtMobile.setText(mobilenumber);
        edtEmail.setText(email);
        edtAbout.setText(aboutme);

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                String name = edtUserName.getText().toString();
                String mobile = edtMobile.getText().toString();
                String email = edtEmail.getText().toString();
                String about = edtAbout.getText().toString();

                if(name.isEmpty() || mobile.isEmpty() || email.isEmpty() || about.isEmpty())
                {
                    Toast.makeText(UpdateUser.this, "Please enter all values", Toast.LENGTH_SHORT).show();
                    return;
                }

                Map<String,String> users = new HashMap<>();
                users.put("firstname",name);
                users.put("mobilenumber",mobile);
                users.put("email",email);
                users.put("aboutme",about);

                db.collection("users")
                        .document(docid)
                        .set(users)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                                finish();
                                finish();
                                startActivity(new Intent(UpdateUser.this,ViewUsers.class));
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(UpdateUser.this, "Error", Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }
}