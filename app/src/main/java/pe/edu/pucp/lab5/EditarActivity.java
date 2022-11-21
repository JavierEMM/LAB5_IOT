package pe.edu.pucp.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class EditarActivity extends AppCompatActivity {

    EditText editText_titulo,editText_descripcion,editText_fechainicio,editText_horainicio,editText_fechafinal,editText_horafinal;
    String titulo,descripcion,fechainicio,horainicio,fechafinal,horafinal;
    Button btn_editar;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference reference;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar);

        reference = FirebaseDatabase.getInstance().getReference("users");

        editText_titulo = findViewById(R.id.editText_editartitulo);
        editText_descripcion = findViewById(R.id.editText_editardescripcion);
        editText_fechainicio = findViewById(R.id.editText_editarfechainicio);
        editText_horainicio = findViewById(R.id.editText_editarhorainicio);
        editText_fechafinal = findViewById(R.id.editText_editarfechafinal);
        editText_horafinal = findViewById(R.id.editText_editarhorafinal);


        btn_editar = findViewById(R.id.btn_editar);

        showAllEventData();





    }

    public void showAllEventData(){
        Intent intent=getIntent();
        titulo = intent.getStringExtra("titulo1");
        descripcion = intent.getStringExtra("descripcion1");
        fechainicio = intent.getStringExtra("fechainicio1");
        horainicio = intent.getStringExtra("horainicio1");
        fechafinal = intent.getStringExtra("fechafinal1");
        horafinal = intent.getStringExtra("horafinal1");

        editText_titulo.setText(titulo);
        editText_descripcion.setText(descripcion);
        editText_fechainicio.setText(fechainicio);
        editText_horainicio.setText(horainicio);
        editText_fechafinal.setText(fechafinal);
        editText_horafinal.setText(horafinal);



    }

    public void update(View view){

        if(titulochange() || descripcionchange() || fechainiciochanged() || horainiciochange() || fechafinalchanged() || horafinalchange() ){
            Toast.makeText(this,"la data se ha actualizado",Toast.LENGTH_LONG).show();
        }



    }
    private boolean horafinalchange() {
        if(!horafinal.equals(editText_horafinal.getText().toString())){

            reference.child(horafinal).child("horaFin").setValue(editText_horafinal.getText().toString());
            horafinal = editText_horafinal.getText().toString();
            return true;

        }else{
            return false;
        }
    }

    private boolean fechafinalchanged() {
        if(!fechafinal.equals(editText_fechafinal.getText().toString())){

            reference.child(fechafinal).child("fechaFin").setValue(editText_fechafinal.getText().toString());
            fechafinal = editText_fechafinal.getText().toString();
            return true;

        }else{
            return false;
        }
    }

    private boolean horainiciochange() {
        if(!horainicio.equals(editText_horainicio.getText().toString())){

            reference.child(horainicio).child("horaInicio").setValue(editText_horainicio.getText().toString());
            horainicio = editText_horainicio.getText().toString();
            return true;

        }else{
            return false;
        }
    }

    private boolean fechainiciochanged() {
        if(!fechainicio.equals(editText_fechainicio.getText().toString())){

            reference.child(fechainicio).child("fechaInicio").setValue(editText_fechainicio.getText().toString());
            fechainicio = editText_fechainicio.getText().toString();
            return true;

        }else{
            return false;
        }
    }




    private boolean descripcionchange() {
        if(!descripcion.equals(editText_descripcion.getText().toString())){

            reference.child(descripcion).child("descripcion").setValue(editText_descripcion.getText().toString());
            descripcion = editText_descripcion.getText().toString();
            return true;

        }else{
            return false;
        }
    }

    private boolean titulochange() {
        if(!titulo.equals(editText_titulo.getText().toString())){

            reference.child(titulo).child("titulo").setValue(editText_titulo.getText().toString());
            titulo = editText_titulo.getText().toString();
            return true;

        }else{
            return false;
        }

    }

    public void cancelar (View view){
        Intent intent = new Intent (EditarActivity.this, AgregarActivity.class);
        startActivity(intent);

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