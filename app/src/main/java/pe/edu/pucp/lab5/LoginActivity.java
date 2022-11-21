package pe.edu.pucp.lab5;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract;
import com.firebase.ui.auth.IdpResponse;
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import pe.edu.pucp.lab5.dto.Usuario;

public class LoginActivity extends AppCompatActivity {

    GoogleSignInClient googleSignInClient;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onStart() {
        super.onStart();
        if(firebaseAuth.getCurrentUser() != null){
            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                startActivity(new Intent(LoginActivity.this,ListarActivity.class));
                finish();
            }else{
                firebaseAuth.signOut();
                Toast.makeText(LoginActivity.this,"Por favor, Verifique su correo, Revise spam",Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);

        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth =  FirebaseAuth.getInstance();
        firebaseAuth.setLanguageCode("es-419");
        databaseReference = firebaseDatabase.getReference();
        if(firebaseAuth.getCurrentUser() != null){
            if(firebaseAuth.getCurrentUser().isEmailVerified()){
                startActivity(new Intent(LoginActivity.this,ListarActivity.class));
                finish();
            }else{
                firebaseAuth.signOut();
                Toast.makeText(LoginActivity.this,"Por favor, Verifique su correo, Revise spam",Toast.LENGTH_SHORT).show();
            }

        }else{
            ((Button) findViewById(R.id.btnIrRegistro)).setOnClickListener(view -> {
                startActivity(new Intent(LoginActivity.this,RegistroActivity.class));
            });
            ((Button) findViewById(R.id.btnIngresar)).setOnClickListener(view -> {
                EditText correo = findViewById(R.id.editCorreo);
                EditText contrasena = findViewById(R.id.editContrasena);
                if(correo.getText().toString().trim().isEmpty()){
                    correo.setError("No dejar vacio");
                    correo.requestFocus();
                }else if (contrasena.getText().toString().trim().isEmpty()){
                    contrasena.setError("No dejar vacio");
                    contrasena.requestFocus();
                }else{
                    firebaseAuth.signInWithEmailAndPassword(correo.getText().toString(),contrasena.getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                firebaseAuth.getCurrentUser().reload().addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(firebaseAuth.getCurrentUser().isEmailVerified()){
                                            startActivity(new Intent(LoginActivity.this,ListarActivity.class));
                                            finish();
                                        }else{
                                            firebaseAuth.getCurrentUser().sendEmailVerification();
                                            Toast.makeText(LoginActivity.this, "Se el ha enviado nuevamente la verificación por correo, por favor verifique", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                });
                            }else{
                                Toast.makeText(LoginActivity.this, "Verifique sus datos", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            });
            ((Button) findViewById(R.id.btnGoogle)).setOnClickListener(view -> {
                Intent intent = googleSignInClient.getSignInIntent();
                signInLauncher.launch(intent);
            });
        }
    }
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>(){
        @Override
        public void onActivityResult(ActivityResult result) {
            if(result.getResultCode() == RESULT_OK){
                onSignInResult(result);
            }
        }
    });

    private void onSignInResult(ActivityResult result){
        Task<GoogleSignInAccount> accountTask = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
        try {
            GoogleSignInAccount account = accountTask.getResult();
            firebaseAuthenticationFinal(account);
        }catch (Exception e){
            Log.d("Exception",e.getMessage());
        }
    }

    private void firebaseAuthenticationFinal(GoogleSignInAccount account) {
        AuthCredential authCredential = GoogleAuthProvider.getCredential(account.getIdToken(),null);
        firebaseAuth.signInWithCredential(authCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    if(task.getResult().getAdditionalUserInfo().isNewUser()){
                        databaseReference.child("users").child(firebaseAuth.getCurrentUser().getUid()).setValue(new Usuario(task.getResult().getAdditionalUserInfo().getUsername(),task.getResult().getAdditionalUserInfo().getUsername(),firebaseAuth.getCurrentUser().getEmail())).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(LoginActivity.this, "Se crea una cuenta Google", Toast.LENGTH_SHORT).show();
                                }else {
                                    Toast.makeText(LoginActivity.this, "Error crea una cuenta Google", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

                    }else{
                        Toast.makeText(LoginActivity.this, "Ingresa con google correctamente", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(LoginActivity.this,ListarActivity.class));
                    finish();
                }else{
                    Toast.makeText(LoginActivity.this, "Error utilizando credenciales", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}