package com.example.proyectoa20202;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class ActLoginAdministrador extends AppCompatActivity {

    private static final String TAG = "";
    EditText tEmailad, tPasswordad;
    Button bLoginad;
    Button bRegisterad;

    private FirebaseAuth mAuth;
    private TextView tvRegisterad;
    private ProgressDialog loadingBar;



    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        // startActivity(new Intent(MainActivity.this, ActDashboard.class));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_login_administrador);

        tEmailad = (EditText)findViewById(R.id.Emailad);
        tPasswordad = (EditText)findViewById(R.id.Passwordad);
        bLoginad = (Button) findViewById(R.id.Loginad);
        bRegisterad = (Button) findViewById(R.id.registerad);
        tvRegisterad = (TextView) findViewById(R.id.tvregisterad);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        bRegisterad.setVisibility(View.INVISIBLE);
        bRegisterad.setEnabled(false);

        tvRegisterad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bLoginad.setVisibility(View.INVISIBLE);
                bRegisterad.setVisibility(View.INVISIBLE);
                tvRegisterad.setText("Registro de cliente");

                bRegisterad.setVisibility(View.VISIBLE);
                bRegisterad.setEnabled(true);

            }
        });

        bLoginad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
            }
        });


        ((Button)findViewById(R.id.registerad)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();

            }
        });


    }

    private void validateUser(){

        String email = tEmailad.getText().toString();
        String password = tPasswordad.getText().toString();





        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(ActLoginAdministrador.this, MapsActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(ActLoginAdministrador.this, "Usuario o contraseña no valido.", Toast.LENGTH_SHORT).show();

                        }


                    }
                });

    }



    private void insertUser() {

        final String email = tEmailad.getText().toString();
        final String password = tPasswordad.getText().toString();

        RegistrarConductor(email,password);


    }

    private void RegistrarConductor(String email, String password){
        if (TextUtils.isEmpty(email)){
            Toast.makeText(ActLoginAdministrador.this, "Por favor ingrese correo", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(ActLoginAdministrador.this, "Por favor ingrese contraseña", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Registrando cliente");
            loadingBar.setMessage("Espere un momento");
            loadingBar.show();

            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(ActLoginAdministrador.this,"Cliente registrado", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                            else {
                                Toast.makeText(ActLoginAdministrador.this,"Registro fallido, por favor vuelvalo a intentar", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }



    }






}