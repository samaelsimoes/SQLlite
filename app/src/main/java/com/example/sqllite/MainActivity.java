package com.example.sqllite;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextAvaliacao;

    private Button   buttonBuscar;
    private Button   buttonCadastrar;
    private TextView editTextBusca;
    private TextView textViewDadosBuscadoNome;
    private TextView textViewDadosBuscadosAvaliacao;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );

        editTextName      = findViewById( R.id.editTextName );
        editTextAvaliacao = findViewById( R.id.editTextAvaliacao );
        buttonBuscar      = findViewById( R.id.buttonBuscar );

        buttonCadastrar = findViewById( R.id.buttonCadastrar );

        editTextBusca  = findViewById( R.id.editTextBusca );
        buttonBuscar   = findViewById( R.id.buttonBuscar );

        textViewDadosBuscadoNome       = findViewById( R.id.textViewDadosBuscadoNome );
        textViewDadosBuscadosAvaliacao = findViewById( R.id.textViewDadosBuscadosAvaliacao );

        database = openOrCreateDatabase(
            "movies.db", MODE_PRIVATE, null
        );

        database.execSQL("CREATE TABLE IF NOT EXISTS MOVIES(NAME VARCHAR(95), RATING INT(5))");


        buttonBuscar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor cursor = database.rawQuery("SELECT NAME, RATING FROM MOVIES ", null);
                int indexName = cursor.getColumnIndex("NAME");
                int indexRating = cursor.getColumnIndex("RATING");

                while (cursor.moveToNext()) {
                    textViewDadosBuscadoNome.setText(cursor.getString(indexName));
                    textViewDadosBuscadosAvaliacao.setText(cursor.getString(indexRating));

                    Log.e("NAME:", cursor.getString(indexName));
                    Log.e("RATING:", cursor.getString(indexRating));
                }
            }
        } );

        buttonCadastrar.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String  nomeFilme    = editTextName.getText().toString();
                Integer avaliacaoFil = Integer.valueOf(editTextAvaliacao.getText().toString());

                String sql = "INSERT INTO MOVIES(NAME,RATING) VALUES ('" + nomeFilme + "', " + avaliacaoFil + ")";

                database.execSQL(sql);
            }
        } );


    }
}
