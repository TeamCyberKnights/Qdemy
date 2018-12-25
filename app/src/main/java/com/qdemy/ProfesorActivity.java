package com.qdemy;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.qdemy.clase.Profesor;
import com.qdemy.db.App;

import java.util.ArrayList;
import java.util.List;

public class ProfesorActivity extends AppCompatActivity {

    private Button intrebari;
    private Button istoric;
    private Button teste;
    private Button desfasurare;
    private ImageView inapoi;
    private ImageView start;

    private Profesor profesor;
    private List<String> testeProfesor = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profesor);

        initializare();
    }

    private void initializare() {

        //region Initializare componente vizuale
        intrebari = findViewById(R.id.intrebari_button_profesor);
        istoric = findViewById(R.id.istoric_button_profesor);
        teste = findViewById(R.id.teste_button_profesor);
        desfasurare = findViewById(R.id.desfasurare_button_profesor);
        inapoi = findViewById(R.id.back_image_profesor);
        start = findViewById(R.id.start_image_profesor);
        //endregion

        profesor = ((App) getApplication()).getProfesor();
        Toast.makeText(getApplicationContext(), getString(R.string.salutare) + " " + profesor.getNume() + " " + profesor.getPrenume(), Toast.LENGTH_LONG).show();
        for(int i=0;i<profesor.getTeste().size();i++)
           testeProfesor.add(profesor.getTeste(i).getNume());



        inapoi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(v.getContext());
                dlgAlert.setMessage(R.string.deconectare_message);
                dlgAlert.setTitle(R.string.deconectare_title);
                dlgAlert.setPositiveButton(R.string.da, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
                dlgAlert.setNegativeButton(R.string.nu, null);
                dlgAlert.setCancelable(true);
                AlertDialog dialog = dlgAlert.create();
                dialog.show();
            }
        });

        istoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), IstoricProfesorActivity.class);
                //intent.putExtra(Constante.CHEIE_TRANSFER, profesor);
                startActivity(intent);
            }
        });

        teste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.testele_mele));
                startActivity(intent);
            }
        });

        intrebari.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MateriiActivity.class);
                intent.putExtra(Constante.CHEIE_TRANSFER, getString(R.string.ntreb_rile_mele));
                startActivity(intent);
            }
        });

        desfasurare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Intent intent = new Intent(getApplicationContext(), ScorLiveActivity.class);
                //startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());
                builder.setTitle(R.string.selecteaza_testul);
                View view = getLayoutInflater().inflate(R.layout.spinner_dialog, null);
                final Spinner spinner = view.findViewById(R.id.spinner_dialog);

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
                        android.R.layout.simple_spinner_item, testeProfesor);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(adapter);

                builder.setPositiveButton(getString(R.string.start), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(getApplicationContext(), StartingQuizProfesorActivity.class);
                        intent.putExtra(Constante.CHEIE_TRANSFER, spinner.getSelectedItem().toString());
                        startActivityForResult(intent, Constante.REQUEST_CODE_START_TEST);
                    }
                });


                builder.setView(view);
                builder.show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK)
           desfasurare.setVisibility(View.VISIBLE);
        else
            desfasurare.setVisibility(View.INVISIBLE);

    }
}
