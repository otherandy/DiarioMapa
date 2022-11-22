package com.proyecto.diario;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.TextView;

import io.realm.Realm;
import io.realm.RealmResults;

public class ListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        LinearLayout linearLayout = findViewById(R.id.listLayout);

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        RealmResults<Note> notes = realm.where(Note.class).findAll();
        realm.executeTransaction(r -> {
            for (Note note : notes) {
                TextView textView = new TextView(this);
                textView.setText(note.getContent());
                linearLayout.addView(textView);
            }
        });
    }
}