package com.example.kakaroto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPassword;
    Button buttonLogin, buttonRegister;
    TextView textViewError;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new DatabaseHelper(this);

        editTextEmail = findViewById(R.id.editText_email);
        editTextPassword = findViewById(R.id.editText_password);
        buttonLogin = findViewById(R.id.button_login);
        buttonRegister = findViewById(R.id.button_register);
        textViewError = findViewById(R.id.textView_error);

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                if (db.checkUser(email, password)) {
                    // Usuario autenticado, puedes abrir la nueva actividad aquí
                    textViewError.setVisibility(View.GONE);
                    // Abrir la actividad CRUD (HomeActivity)
                    goToHomeActivity();
                    Toast.makeText(MainActivity.this, "Sesión iniciada", Toast.LENGTH_SHORT).show();

                } else {
                    // Mostrar mensaje de error
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("Credenciales incorrectas");
                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextEmail.getText().toString();
                String password = editTextPassword.getText().toString();

                long result = db.addUser(email, password);
                if (result > 0) {
                    // Usuario registrado exitosamente
                    textViewError.setVisibility(View.GONE);
                    // Mostrar mensaje de éxito o abrir la actividad CRUD (HomeActivity)
                    goToHomeActivity();
                    Toast.makeText(MainActivity.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                } else {
                    // Error al registrar el usuario
                    textViewError.setVisibility(View.VISIBLE);
                    textViewError.setText("Error al registrar el usuario");
                }
            }
        });
    }

    // Método para iniciar la actividad CRUD (HomeActivity)
    private void goToHomeActivity() {
        Intent intent = new Intent(MainActivity.this, HomeActivity.class);
        startActivity(intent);
    }
}
