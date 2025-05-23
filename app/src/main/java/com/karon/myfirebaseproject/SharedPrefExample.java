package com.karon.myfirebaseproject;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.nio.file.Files;

public class SharedPrefExample extends AppCompatActivity {


    EditText edtUserName;
    Button btnAdd,btnView,btnDelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_shared_pref_example);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        edtUserName = (EditText) findViewById(R.id.edtUserName);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnView = (Button) findViewById(R.id.btnView);
        btnDelete = (Button) findViewById(R.id.btnDelete);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtUserName.getText().toString();
                SharedPreferences sp = getSharedPreferences("MyPref",MODE_PRIVATE);
                SharedPreferences.Editor editor = sp.edit();
                editor.putString("username",name);
                editor.apply();

                Toast.makeText(SharedPrefExample.this, "Value Stored!", Toast.LENGTH_SHORT).show();
            }
        });

        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("MyPref",MODE_PRIVATE);
                if(sp.contains("username"))
                {
                    String nm = sp.getString("username","").toString();
                    Toast.makeText(SharedPrefExample.this, "Value  = "+nm, Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SharedPrefExample.this, "Value not available!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sp = getSharedPreferences("MyPref",MODE_PRIVATE);
                if(sp.contains("username"))
                {
                    SharedPreferences.Editor editor = sp.edit();
                    editor.remove("username");
                    editor.apply();
                    Toast.makeText(SharedPrefExample.this, "Value Removed!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(SharedPrefExample.this, "Value not available!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}