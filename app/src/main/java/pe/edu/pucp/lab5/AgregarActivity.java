package pe.edu.pucp.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

import pe.edu.pucp.lab5.Entity.Actividad;

public class AgregarActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agregar);

        firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();

        EditText editTextTitulo = findViewById(R.id.editText_titulo);
        String tituloStr = editTextTitulo.getText().toString();

        EditText editTextDescripcion = findViewById(R.id.editText_descripcion);
        String descripcionStr = editTextDescripcion.getText().toString();

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

        
    }
}