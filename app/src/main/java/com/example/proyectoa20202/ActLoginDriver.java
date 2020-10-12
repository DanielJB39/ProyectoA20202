package com.example.proyectoa20202;

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


public class ActLoginDriver extends AppCompatActivity {

    private static final String TAG = "";
    EditText tEmailco, tPasswordco;
    Button bLoginco;
    Button bRegisterco;

    private FirebaseAuth mAuth;
    private TextView tvRegisterco;
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
        setContentView(R.layout.activity_act_login_driver);

        tEmailco = (EditText)findViewById(R.id.Emailco);
        tPasswordco = (EditText)findViewById(R.id.Passwordco);
        bLoginco = (Button) findViewById(R.id.Loginco);
        bRegisterco = (Button) findViewById(R.id.registerco);
        tvRegisterco = (TextView) findViewById(R.id.tvregisterco);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        bRegisterco.setVisibility(View.INVISIBLE);
        bRegisterco.setEnabled(false);

        tvRegisterco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bLoginco.setVisibility(View.INVISIBLE);
                bRegisterco.setVisibility(View.INVISIBLE);
                tvRegisterco.setText("Registro de cliente");

                bRegisterco.setVisibility(View.VISIBLE);
                bRegisterco.setEnabled(true);

            }
        });

        bLoginco.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
            }
        });


        ((Button)findViewById(R.id.registerco)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();

            }
        });

    }





    private void validateUser(){

        String email = tEmailco.getText().toString();
        String password = tPasswordco.getText().toString();





        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            startActivity(new Intent(ActLoginDriver.this, ActDashboard.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(ActLoginDriver.this, "Usuario o contraseña no valido.", Toast.LENGTH_SHORT).show();

                        }


                    }
                });

    }



    private void insertUser() {

        final String email = tEmailco.getText().toString();
        final String password = tPasswordco.getText().toString();

        RegistrarConductor(email,password);


    }

    private void RegistrarConductor(String email, String password){
        if (TextUtils.isEmpty(email)){
            Toast.makeText(ActLoginDriver.this, "Por favor ingrese correo", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(ActLoginDriver.this, "Por favor ingrese contraseña", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(ActLoginDriver.this,"Cliente registrado", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                            else {
                                Toast.makeText(ActLoginDriver.this,"Registro fallido, por favor vuelvalo a intentar", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }



    }


}