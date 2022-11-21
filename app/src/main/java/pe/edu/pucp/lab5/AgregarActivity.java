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
    Uri uri;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    FirebaseStorage storage = FirebaseStorage.getInstance();
    StorageReference storageReference = storage.getReference();
    ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if (result.getResultCode() == RESULT_OK){
                    uri = result.getData().getData();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

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
                        String fecha = i2 + "/" + (i1+1) + "/" + i;
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
                        String fecha = i2 + "/" + (i1+1) + "/" + i;
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

                String[] fechaInicioS = fechaInicioStr.split("/");
                String[] fechaFinalS = fechaFinalStr.split("/");
                String[] horaInicioS = horaInicioStr.split(":");
                String[] horaFinalS = horaFinalStr.split(":");
                if(uri != null){
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
                if(tituloStr.trim().isEmpty()){
                    editTextTitulo.setError("No puede ser vacio");
                    editTextTitulo.requestFocus();
                }else if(descripcionStr.trim().isEmpty()){
                    editTextDescripcion.setError("No puede ser vacío");
                    editTextDescripcion.requestFocus();
                }else if(fechaInicioStr.trim().isEmpty()){
                    editTextFechaInicio.setError("Elija la fecha");
                    editTextFechaInicio.requestFocus();
                }else if(horaInicioStr.trim().isEmpty()){
                    editTextHoraInicio.setError("Elija la hora");
                    editTextHoraInicio.requestFocus();
                }else if(fechaFinalStr.trim().isEmpty()){
                    editTextFechaFinal.setError("Elija la fecha");
                    editTextFechaFinal.requestFocus();
                }else if(horaFinalStr.trim().isEmpty()){
                    editTextHoraFinal.setError("Elija la hora");
                    editTextHoraFinal.requestFocus();
                }else if(Integer.parseInt(fechaFinalS[2])<Integer.parseInt(fechaInicioS[2])){
                    editTextFechaFinal.setError("La fecha final no puede ser menor a la inicial");
                    editTextFechaFinal.requestFocus();
                }else if(Integer.parseInt(fechaFinalS[1])<Integer.parseInt(fechaInicioS[1])){
                    editTextFechaFinal.setError("La fecha final no puede ser menor a la inicial");
                    editTextFechaFinal.requestFocus();
                } else if(Integer.parseInt(fechaFinalS[0])<Integer.parseInt(fechaInicioS[0])){
                    editTextFechaFinal.setError("La fecha final no puede ser menor a la inicial");
                    editTextFechaFinal.requestFocus();
                } else if(fechaInicioStr.equals(fechaFinalStr)){
                    if(Integer.parseInt(horaFinalS[0])<Integer.parseInt(horaInicioS[0])){
                        editTextHoraFinal.setError("La hora final no puede ser menor a la incial");
                        editTextHoraFinal.requestFocus();
                    }else if(Integer.parseInt(horaFinalS[0])<Integer.parseInt(horaInicioS[0])){
                        editTextHoraFinal.setError("La hora final no puede ser menor a la incial");
                        editTextHoraFinal.requestFocus();
                    }
                } else if((Integer.parseInt(horaInicioS[0])<6)||(Integer.parseInt(horaInicioS[0])>23)){
                    editTextHoraInicio.setError("No puede haber actividades antes de las 6:00 am");
                    editTextHoraInicio.requestFocus();
                } else if(Integer.parseInt(horaInicioS[0])==23){
                    if(Integer.parseInt(horaInicioS[1])>30){
                        editTextHoraInicio.setError("No puede haber actividades después de las 11:30 pm");
                        editTextHoraInicio.requestFocus();
                    }
                } else if((Integer.parseInt(horaFinalS[0])<6)||(Integer.parseInt(horaFinalS[0])>23)){
                    editTextHoraFinal.setError("No puede haber actividades antes de las 6:00 am");
                    editTextHoraFinal.requestFocus();
                } else if(Integer.parseInt(horaFinalS[0])==23){
                    if(Integer.parseInt(horaFinalS[1])>30){
                        editTextHoraInicio.setError("No puede haber actividades después de las 11:30 pm");
                        editTextHoraInicio.requestFocus();
                    }
                }else {
                    Actividad actividad = new Actividad();
                    actividad.setTitulo(tituloStr);
                    actividad.setDescripcion(descripcionStr);
                    actividad.setFechaInicio(fechaInicioStr);
                    actividad.setHoraInicio(horaInicioStr);
                    actividad.setFechaFin(fechaFinalStr);
                    actividad.setHoraFin(horaFinalStr);
                    databaseReference.child("users").child(user.getUid()).child("activities").push().setValue(actividad);
                    Intent intent = new Intent(AgregarActivity.this, ListarActivity.class);
                    startActivity(intent);
                }
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