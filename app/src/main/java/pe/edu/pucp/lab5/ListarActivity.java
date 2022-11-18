package pe.edu.pucp.lab5;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.PopupMenu;

import org.jetbrains.annotations.NotNull;

public class ListarActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar);
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
                popupMenu.getMenuInflater().inflate(R.menu.menu_listar,popupMenu.getMenu());
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
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}