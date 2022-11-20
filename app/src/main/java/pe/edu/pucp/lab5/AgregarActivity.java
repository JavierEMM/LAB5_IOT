package pe.edu.pucp.lab5;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;

import pe.edu.pucp.lab5.Entity.Actividad;

public class AgregarActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    Uri uri = result.getData().getData();
                    StorageReference child = storage.getReference().child(user.getUid()+"/"+uri.hashCode()+".jpg");
                    child.putFile(uri)
                            .addOnSuccessListener(taskSnapshot -> Log.d("msg","archivo subido exitosamente"))
                            .addOnFailureListener(e -> Log.d("msg","error",e.getCause()))
                            .addOnProgressListener(snapshot -> {
                                long bytesTransferidos = snapshot.getBytesTransferred();
                                long bytesTotales = snapshot.getTotalByteCount();
                                double progreso = (100.0 * bytesTransferidos) / bytesTotales;
                                Log.d("msg","Porcentaje de subida: " + Math.round(progreso) + "%");

                            });
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference().child("activities");

        EditText editTextTitulo = findViewById(R.id.editText_titulo);

        EditText editTextDescripcion = findViewById(R.id.editText_descripcion);

        EditText editTextFechaInicio = findViewById(R.id.editTextFechaInicio);
        Button btnInicio = findViewById(R.id.btnFechaInicio);
        btnInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AgregarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String fecha = i2 + "/" + i1 + "/" + i;
                        editTextFechaInicio.setText(fecha);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        });

        EditText editTextHoraInicio = findViewById(R.id.editTextHoraInicio);
        Button btnHoraInicio = findViewById(R.id.btnHoraInicio);
        btnHoraInicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minutos = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AgregarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        editTextHoraInicio.setText(i + ":" + i1);
                    }
                },hora,minutos,false);
                timePickerDialog.show();
            }
        });

        EditText editTextFechaFinal = findViewById(R.id.editTextFechaFinal);
        Button btnFinal = findViewById(R.id.btnFechaFinal);
        btnFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int anio = calendar.get(Calendar.YEAR);
                int mes = calendar.get(Calendar.MONTH);
                int dia = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(AgregarActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        String fecha = i2 + "/" + i1 + "/" + i;
                        editTextFechaFinal.setText(fecha);
                    }
                },anio,mes,dia);
                datePickerDialog.show();
            }
        });

        EditText editTextHoraFinal = findViewById(R.id.editTextHoraFinal);
        Button btnHoraFinal = findViewById(R.id.btnHoraFinal);
        btnHoraFinal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar calendar = Calendar.getInstance();
                int hora = calendar.get(Calendar.HOUR_OF_DAY);
                int minutos = calendar.get(Calendar.MINUTE);
                TimePickerDialog timePickerDialog = new TimePickerDialog(AgregarActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        editTextHoraFinal.setText(i + ":" + i1);
                    }
                },hora,minutos,false);
                timePickerDialog.show();
            }
        });

        Button btnimagen = findViewById(R.id.btnImagen);
        btnimagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intent.setType("image/jpeg");
                launcher.launch(intent);
            }
        });

        StorageReference reference = storage.getReference();
        storageReference = reference.child("imagenes");

        StorageReference photoRef = storageReference.child("photo.jpg");
        ImageView imageView = findViewById(R.id.imageView);
        Glide.with(AgregarActivity.this).load(photoRef).into(imageView);

        Button btnAgregar = findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tituloStr = editTextTitulo.getText().toString();
                String descripcionStr = editTextDescripcion.getText().toString();
                String fechaInicioStr = editTextFechaInicio.getText().toString();
                String horaInicioStr = editTextHoraInicio.getText().toString();
                String fechaFinalStr = editTextFechaFinal.getText().toString();
                String horaFinalStr = editTextHoraFinal.getText().toString();
                Actividad actividad = new Actividad();
                actividad.setTitulo(tituloStr);
                actividad.setDescripcion(descripcionStr);
                actividad.setFechaInicio(fechaInicioStr);
                actividad.setHoraInicio(horaInicioStr);
                actividad.setFechaFin(fechaFinalStr);
                actividad.setHoraFin(horaFinalStr);
                actividad.setFoto("photo.jpg");
                databaseReference.push().setValue(actividad);
                Intent intent = new Intent(AgregarActivity.this,ListarActivity.class);
                startActivity(intent);
            }
        });

        Button btnCancelar = findViewById(R.id.btnCancelar);
        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AgregarActivity.this,ListarActivity.class);
                startActivity(intent);
            }
        });

    }
}