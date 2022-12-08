package com.proyecto.diario;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

        listView = findViewById(R.id.listView);
        ArrayList<String> titleList = new ArrayList<>();

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Note> notes = realm.where(Note.class).findAll();

        realm.executeTransaction(r -> {
            for (Note note : notes) {
                titleList.add(note.getTitle());
            }

            //configurando adaptador
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(),
                    android.R.layout.simple_list_item_1, titleList);
            //mostrando adaptador en vista
            listView.setAdapter(adapter);
        });

        listView.setOnItemClickListener((adapterView, view, i, l) -> {
            Intent intent = new Intent(ListActivity.this, NoteActivity.class);
            String titleItem = (String) (listView.getItemAtPosition(i));
            Toast.makeText(ListActivity.this, "Nota Seleccionada: " + titleItem, Toast.LENGTH_SHORT).show();
            intent.putExtra("note", titleItem);
            intent.putExtra("id", notes.get(i).getId());
            startActivity(intent);
        });
    }
}