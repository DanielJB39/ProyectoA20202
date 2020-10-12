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

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";
    EditText tEmail, tPassword;
    Button bLogin;
    Button bRegister;

    private FirebaseAuth mAuth;
    private TextView tvRegister;
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
        setContentView(R.layout.activity_main);

        tEmail = (EditText)findViewById(R.id.Email);
        tPassword = (EditText)findViewById(R.id.Password);
        bLogin = (Button) findViewById(R.id.Login);
        bRegister = (Button) findViewById(R.id.register);
        tvRegister = (TextView) findViewById(R.id.tvregister);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);

        bRegister.setVisibility(View.INVISIBLE);
        bRegister.setEnabled(false);

        tvRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bLogin.setVisibility(View.INVISIBLE);
                bRegister.setVisibility(View.INVISIBLE);
                tvRegister.setText("Registro de cliente");

                bRegister.setVisibility(View.VISIBLE);
                bRegister.setEnabled(true);

            }
        });




        bLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validateUser();
            }
        });

        ((Button)findViewById(R.id.register)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertUser();

            }
        });


    }

    private void validateUser(){

        String email = tEmail.getText().toString();
        String password = tPassword.getText().toString();

        IngresarCliente(email, password);


    }

    private void IngresarCliente(String email, String password){

        if (TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this, "Por favor ingrese correo...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this, "Por favor ingrese contraseña...", Toast.LENGTH_SHORT).show();
        }
        else {
            loadingBar.setTitle("Ingresando cliente");
            loadingBar.setMessage("Espere un momento");
            loadingBar.show();

            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Ingreso exitoso", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                            else {
                                Toast.makeText(MainActivity.this,"Ingreso fallido, por favor vuelvalo a intentar...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }

    }



    private void insertUser() {

        final String email = tEmail.getText().toString();
        final String password = tPassword.getText().toString();

        RegistrarCliente(email,password);


    }

    private void RegistrarCliente(String email, String password){
        if (TextUtils.isEmpty(email)){
            Toast.makeText(MainActivity.this, "Por favor ingrese correo...", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(password)){
            Toast.makeText(MainActivity.this, "Por favor ingrese contraseña...", Toast.LENGTH_SHORT).show();
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
                                Toast.makeText(MainActivity.this,"Cliente registrado...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                            else {
                                Toast.makeText(MainActivity.this,"Registro fallido, por favor vuelvalo a intentar...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }



    }

}