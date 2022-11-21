package pe.edu.pucp.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarActivity extends AppCompatActivity {

    EditText editText_titulo,editText_descripcion,editText_fechainicio,editText_horainicio,editText_fechafinal,editText_horafinal;
    Button btn_editar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        editText_titulo = findViewById(R.id.editText_editartitulo);
        editText_descripcion = findViewById(R.id.editText_editardescripcion);
        editText_fechainicio = findViewById(R.id.editText_editarfechainicio);
        editText_horainicio = findViewById(R.id.editText_editarhorainicio);
        editText_fechafinal = findViewById(R.id.editText_editarfechafinal);
        editText_horafinal = findViewById(R.id.editText_editarhorafinal);


        btn_editar = findViewById(R.id.btn_editar);

        btn_editar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //updateEvento();
            }
        });



    }

    /*@Override
    protected void onStart() {
        super.onStart();



        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String currentid = user.getUid();


        documentReference.collection("user").document(currentid);
        documentReference.get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()){

        }
    }*/

    /*

    private void updateEvento() {

        String titulo = editText_titulo.getText().toString();
        String descripcion = editText_descripcion.getText().toString();
        String fechainicio = editText_fechainicio.getText().toString();
        String horainicio = editText_horainicio.getText().toString();
        String fechafinal = editText_fechafinal.getText().toString();
        String horafinal = editText_horafinal.getText().toString();

        final Docu


        db.runTransaction(new Transaction.Function<Void>() {
                    @Override
                    public Void apply(Transaction transaction) throws FirebaseFirestoreException {
                        DocumentSnapshot snapshot = transaction.get(sfDocRef);

                        // Note: this could be done without a transaction
                        //       by updating the population using FieldValue.increment()
                        double newPopulation = snapshot.getDouble("population") + 1;
                        transaction.update(sfDocRef, "population", newPopulation);

                        // Success
                        return null;
                    }
                }).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "Transaction success!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Transaction failure.", e);
                    }
                });


    }
    */
}