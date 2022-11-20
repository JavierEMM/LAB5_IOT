package pe.edu.pucp.lab5;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;

import pe.edu.pucp.lab5.Entity.Actividad;

public class ListarActivity extends AppCompatActivity {

    FirebaseDatabase firebaseDatabase;
    ArrayList<Actividad> listaActividades = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);

        RecyclerView recyclerView = findViewById(R.id.recyclerActividades);

        ListaActividadesAdapter adapter = new ListaActividadesAdapter();
        adapter.setContext(ListarActivity.this);
        firebaseDatabase= FirebaseDatabase.getInstance();

        DatabaseReference databaseReference = firebaseDatabase.getReference().child("Actividades");


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot children : snapshot.getChildren() ){
                    Actividad actividad = children.getValue(Actividad.class);
                    listaActividades.add(actividad);
                    adapter.setListaActividades(listaActividades);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(ListarActivity.this));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_listar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NotNull MenuItem item){
        switch(item.getItemId()){
            case R.id.btn_calendar:
                View menuItemView = findViewById(R.id.btn_calendar);
                PopupMenu popupMenu = new PopupMenu(this,menuItemView);
                popupMenu.getMenuInflater().inflate(R.menu.menu_popup,popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch(item.getItemId()){
                            case R.id.btn_fechaInicio:

                                return true;
                            case R.id.btn_horaInicio:

                                return true;
                            case R.id.btn_fechaFinal:

                                return true;
                            case R.id.btn_horaFinal:

                                return true;
                            default:
                                return false;
                        }
                    }
                });
                popupMenu.show();
        }
        return super.onOptionsItemSelected(item);
    }
}