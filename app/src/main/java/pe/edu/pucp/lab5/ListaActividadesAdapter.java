package pe.edu.pucp.lab5;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import pe.edu.pucp.lab5.Entity.Actividad;

public class ListaActividadesAdapter  extends RecyclerView.Adapter<ListaActividadesAdapter.ActividadViewHolder> {

    private ArrayList<Actividad> listaActividades;
    private Context context;

    public ArrayList<Actividad> getListaActividades() {
        return listaActividades;
    }

    public void setListaActividades(ArrayList<Actividad> listaActividades) {
        this.listaActividades = listaActividades;
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context){
        this.context = context;
    }


    public class ActividadViewHolder extends RecyclerView.ViewHolder{
        Actividad actividad;

        public ActividadViewHolder (@NonNull View itemView){
            super(itemView);
        }
    }


    @NonNull
     @Override
    public  ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType){
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv,parent,false);
        return new ActividadViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ActividadViewHolder holder, int position) {
        Actividad a = listaActividades.get(position);
        holder.actividad = a;
        TextView textViewTitulo = holder.itemView.findViewById(R.id.textViewTítuloActividad);
        TextView textViewFechaInicio = holder.itemView.findViewById(R.id.textViewFechaInicio);
        TextView textViewFechaFin = holder.itemView.findViewById(R.id.textViewFechafin);
        TextView textViewHoraInicio = holder.itemView.findViewById(R.id.textViewHoraInicio);
        TextView textViewHoraFin = holder.itemView.findViewById(R.id.textViewHoraFin);


        textViewTitulo.setText(a.getTitulo());
        textViewFechaInicio.setText(a.getFechaInicio());
        textViewHoraFin.setText(a.getHoraFin());
        textViewHoraInicio.setText(a.getHoraInicio());

    }

    @Override
    public int getItemCount(){
        return listaActividades.size();
    }

}


