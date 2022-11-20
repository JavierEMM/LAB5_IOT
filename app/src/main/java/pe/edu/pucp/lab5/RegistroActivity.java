package pe.edu.pucp.lab5;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pe.edu.pucp.lab5.dto.Usuario;

public class RegistroActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        EditText nombre = findViewById(R.id.editRegistroNombre);
        EditText apellido = findViewById(R.id.editRegistroApellido);
        EditText correo = findViewById(R.id.editRegistroCorreo);
        EditText contrasena = findViewById(R.id.editRegistroContrasena);
        ((Button) findViewById(R.id.btnRegistrar)).setOnClickListener(view -> {
                Boolean bool = true;
                if(nombre.getText().toString().isEmpty()){
                    nombre.setError("No deje el nombre vacio");
                    bool = false;
                }
                if(apellido.getText().toString().isEmpty()){
                    apellido.setError("No deje el apellido vacio");
                    bool = false;
                }
                if(correo.getText().toString().isEmpty()){
                    correo.setError("No deje el correo vacio");
                    bool = false;
                }
                if(contrasena.getText().toString().isEmpty()){
                    contrasena.setError("No deje la contraseña vacia");
                    bool = false;
                }
                if(bool){
                    Log.d("Hola",correo.getText().toString());
                    Log.d("Hola",contrasena.getText().toString());
                    firebaseAuth.createUserWithEmailAndPassword(correo.getText().toString(),contrasena.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                databaseReference.child("users").push().setValue(new Usuario(nombre.getText().toString(),apellido.getText().toString(),correo.getText().toString())).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task){
                                        if(task.isSuccessful()){
                                            firebaseAuth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
                                                @Override
                                                public void onComplete(@NonNull Task<Void> task) {
                                                    if(task.isSuccessful()){
                                                        Toast.makeText(RegistroActivity.this,"Usuario guardado correctamente",Toast.LENGTH_SHORT).show();
                                                    }else{
                                                        Toast.makeText(RegistroActivity.this,"Error al enviar verificación",Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            });

                                        }else{
                                            Toast.makeText(RegistroActivity.this,"Error al crear usuario",Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(RegistroActivity.this,"Error al crear usuario",Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    Toast.makeText(RegistroActivity.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistroActivity.this, "Complete los parametros", Toast.LENGTH_SHORT).show();
                }
            });
    }
}