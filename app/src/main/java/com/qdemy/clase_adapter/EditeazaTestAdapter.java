package com.qdemy.clase_adapter;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.qdemy.AdaugaTestActivity;
import com.qdemy.Constante;
import com.qdemy.EditeazaTestActivity;
import com.qdemy.IntrebareActivity;
import com.qdemy.R;
import com.qdemy.clase.IntrebareGrila;

import java.util.List;

public class EditeazaTestAdapter extends ArrayAdapter<IntrebareGrila> {

    private Context context;
    private int resource;
    //private List<IntrebareGrila> intrebari;
    private LayoutInflater inflater;
    private EditeazaTestActivity activity;

    public EditeazaTestAdapter(@NonNull Context context, int resource,
                               @NonNull List<IntrebareGrila> objects, LayoutInflater inflater,
                               EditeazaTestActivity activity) {

        super(context, resource, objects);

        this.context=context;
        this.resource=resource;
        this.inflater=inflater;
        this.activity = activity;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable final View convertView, @NonNull ViewGroup parent) {

        View rand = inflater.inflate(resource, parent, false);

        final TextView nume = rand.findViewById(R.id.text_itb);
        final Button selectat = rand.findViewById(R.id.button_itb);
        selectat.setBackgroundResource(R.drawable.ic_promovat);


        final IntrebareGrila intrebare = activity.intrebari.get(position);
        nume.setText(intrebare.getText());

        if(activity.selectari.get(position)==false) selectat.setVisibility(View.INVISIBLE);
        else rand.setBackgroundColor(Color.LTGRAY);


        nume.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (activity.selectari.get(position) == false) {
                        activity.selectari.set(position, true);
                        activity.intrebariSelectate.add(activity.intrebari.get(position));
                    } else {
                        activity.selectari.set(position, false);
                        if (activity.intrebariSelectate.contains(activity.intrebari.get(position)))
                            activity.intrebariSelectate.remove(activity.intrebari.get(position));
                    }
                notifyDataSetChanged();
            }
        });


        return rand;
    }
}
