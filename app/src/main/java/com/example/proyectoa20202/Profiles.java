package com.example.proyectoa20202;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Profiles extends AppCompatActivity {

    private Button btnCliente;
    private Button btnConductor;
    private Button btnAdministrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profiles);

        btnCliente = (Button) findViewById(R.id.Cliente);
        btnConductor = (Button) findViewById(R.id.Conductor);
        btnAdministrador = (Button) findViewById(R.id.Administrador);


        btnCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MainActivity = new Intent(Profiles.this, com.example.proyectoa20202.MainActivity.class);
                startActivity(MainActivity);
            }
        });


       btnConductor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActLoginDriver = new Intent(Profiles.this, ActLoginDriver.class);
                startActivity(ActLoginDriver);
            }
        });

        btnAdministrador.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent ActLoginAdmin = new Intent(Profiles.this, ActLoginAdministrador.class);
                startActivity(ActLoginAdmin);
            }
        });




    }
}