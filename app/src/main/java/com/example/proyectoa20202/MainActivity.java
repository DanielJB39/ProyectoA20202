package com.example.proyectoa20202;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "";
    EditText tEmail, tPassword;
    Button bLogin;
    Button bRegister;

    private FirebaseAuth mAuth;
    private TextView tvRegister;
    private ProgressDialog loadingBar;
    SharedPreferences mPref;
    DatabaseReference mDatabase;


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

        tEmail = (EditText)findViewById(R.id.Email );
        tPassword = (EditText)findViewById(R.id.Password);
        bLogin = (Button) findViewById(R.id.Login);
        bRegister = (Button) findViewById(R.id.register);
        tvRegister = (TextView) findViewById(R.id.tvregister);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new ProgressDialog(this);
        mDatabase = FirebaseDatabase.getInstance().getReference();

        bRegister.setVisibility(View.INVISIBLE);
        bRegister.setEnabled(false);
        mPref = getApplicationContext().getSharedPreferences("typeUser", MODE_PRIVATE);
        String selectUser = mPref.getString("user", "");
        Toast.makeText(this, "Selecciono:..." + selectUser, Toast.LENGTH_SHORT).show();



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

    private void RegistrarCliente(final String email, String password){
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
                                saveUser(email);
                            }

                            else {
                                Toast.makeText(MainActivity.this,"Registro fallido, por favor vuelvalo a intentar...", Toast.LENGTH_SHORT).show();
                                loadingBar.dismiss();
                            }

                        }
                    });
        }



    }


    void saveUser(String email){
        String selectUser = mPref.getString("user", "");

        User user = new User();
        user.setEmail(email);


        if (selectUser.equals("driver")){
            mDatabase.child("Users").child("Drivers").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
        else if (selectUser.equals("client")){
            mDatabase.child("Users").child("Clients").push().setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(MainActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Registro fallido", Toast.LENGTH_SHORT).show();
                    }

                }
            });

        }


    }

}