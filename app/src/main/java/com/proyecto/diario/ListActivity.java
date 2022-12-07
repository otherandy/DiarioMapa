package com.proyecto.diario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.diario.model.Note;

import java.util.ArrayList;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListActivity extends AppCompatActivity {
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        LinearLayout linearLayout = findViewById(R.id.listLayout);
        listView = findViewById(R.id.listView);
        ArrayList <String> notas = new ArrayList<>();
        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Note> notes = realm.where(Note.class).findAll();
        realm.executeTransaction(r -> {
            for (Note note : notes) {
                notas.add(note.getTitle());
                //TextView textView = new TextView(this);
                //textView.setText(note.getContent());
                //linearLayout.addView(textView);
                //textView.setOnClickListener(view -> {
                //    Intent intent = new Intent(ListActivity.this, NoteActivity.class);
                //    intent.putExtra("note", note.getId());
                //    startActivity(intent);
                //});
            }
            //configurando adaptador
            ArrayAdapter <String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, notas);
            //mostrando adaptador en vista
            listView.setAdapter(adapter);
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(ListActivity.this, NoteActivity.class);
                String titleItem = (String) (listView.getItemAtPosition(i));
                Toast.makeText(ListActivity.this, "Nota Seleccionada: "+titleItem, Toast.LENGTH_SHORT).show();
                intent.putExtra("note", titleItem);
                startActivity(intent);
            }
        });
    }
}