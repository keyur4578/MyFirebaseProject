package com.karon.myfirebaseproject;

import android.app.ComponentCaller;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.karon.myfirebaseproject.adapters.UserAdapter;
import com.karon.myfirebaseproject.models.User;

import java.util.ArrayList;

public class ViewUsers extends AppCompatActivity implements UserAdapter.OnUserClickListner {

    ListView mylistview;
    ArrayList<User> data;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_view_users);
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
        mylistview = (ListView) findViewById(R.id.mylistview);
        loadData();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data, @NonNull ComponentCaller caller) {
        super.onActivityResult(requestCode, resultCode, data, caller);
        if (requestCode == 123 && resultCode == RESULT_OK) {
            loadData();
        }
    }

    void loadData()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        data = new ArrayList<>();
        db.collection("users")
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {

                    for(DocumentSnapshot document : queryDocumentSnapshots)
                    {
                        String id = document.getId().toString();
                        String firstname = document.getString("firstname");
                        String email = document.getString("email");
                        String mobilenumber = document.getString("mobilenumber");
                        String aboutme = document.getString("aboutme");

                        User obj = new User();
                        obj.setFirstname(firstname);
                        obj.setEmail(email);
                        obj.setMobilenumber(mobilenumber);
                        obj.setAboutme(aboutme);
                        obj.setDocid(id);

                        data.add(obj);


                    }

                    UserAdapter adapter = new UserAdapter(ViewUsers.this,data,this);
                    mylistview.setAdapter(adapter);

                })
                .addOnFailureListener(e->{
                    Toast.makeText(this, "Data Not Available", Toast.LENGTH_SHORT).show();
                });

    }

    @Override
    public void onDeleteButtonClick(User obj) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users")
                .document(obj.getDocid().toString())
                .delete()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        loadData();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ViewUsers.this, "Delete Error", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onEditButtonClick(User obj) {
        Intent intent = new Intent(ViewUsers.this, UpdateUser.class);
        intent.putExtra("docid",obj.getDocid().toString());
        intent.putExtra("firstname",obj.getFirstname().toString());
        intent.putExtra("email",obj.getEmail().toString());
        intent.putExtra("mobilenumber",obj.getMobilenumber().toString());
        intent.putExtra("aboutme",obj.getAboutme().toString());
        startActivityForResult(intent,123);
    }


}