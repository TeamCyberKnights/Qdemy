package com.qdemy.clase_adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.qdemy.Constante;
import com.qdemy.IntrebareActivity;
import com.qdemy.R;
import com.qdemy.clase.IntrebareGrila;

import java.util.List;

public class IntrebareTestAdapter extends ArrayAdapter<IntrebareGrila> {

    private Context context;
    private int resource;
    private List<IntrebareGrila> intrebari;
    private LayoutInflater inflater;
    private Boolean removable;

    public IntrebareTestAdapter(@NonNull Context context, int resource,
                            @NonNull List<IntrebareGrila> objects, LayoutInflater inflater, Boolean removable) {
        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.intrebari = objects;
        this.inflater=inflater;
        this.removable= removable;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        TextView nume = rand.findViewById(R.id.text_itb);
        Button sterge = rand.findViewById(R.id.button_itb);
        sterge.setBackgroundResource(R.drawable.ic_close_black_24dp);
        if(!removable) sterge.setVisibility(View.INVISIBLE);

        final IntrebareGrila intrebare = intrebari.get(position);
        nume.setText(intrebare.getNume());

        nume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), IntrebareActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, intrebari.get(position));
                v.getContext().startActivity(intent);
            }
        });

        sterge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage(R.string.stergere_intrebare_message);
                //dlgAlert.setTitle("Ștergere întrebare");
                dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        intrebari.remove(position);
                        notifyDataSetChanged();
                    }
                });
                dlgAlert.setNegativeButton(R.string.nu, null);
                AlertDialog dialog = dlgAlert.create();
                dialog.show();

            }
        });



        return rand;
    }
}
