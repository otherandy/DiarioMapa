package com.proyecto.diario;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.diario.model.Note;

import org.bson.types.ObjectId;

import java.util.Calendar;

import io.realm.Realm;

public class NoteActivity extends AppCompatActivity {
    Bundle extras;
    double currentLat;
    double currentLon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        //Get data from MainActivity
        extras = getIntent().getExtras();
        ObjectId noteId = (ObjectId) extras.get("note");
        currentLat = extras.getDouble("lat");
        currentLon = extras.getDouble("lon");

        Note note;
        if (noteId != null) {
            note = realm.where(Note.class).equalTo("_id", noteId).findFirst();
        } else {
            note = new Note();
            note.setCreated(Calendar.getInstance().getTime());
            note.setLocation(currentLat, currentLon);
        }

        Button saveButton = findViewById(R.id.saveButton);
        EditText titleText = findViewById(R.id.titleText);
        EditText noteText = findViewById(R.id.noteText);

        saveButton.setOnClickListener(view -> onBackPressed());

        titleText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                realm.executeTransactionAsync(bgRealm -> {
                    assert note != null;
                    note.setTitle(editable.toString());
                    note.setUpdated(Calendar.getInstance().getTime());
                    bgRealm.insertOrUpdate(note);
                }, () -> {
                    //Log.d("Note", note.toString());
                });
            }
        });

        noteText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                realm.executeTransactionAsync(bgRealm -> {
                    assert note != null;
                    note.setContent(editable.toString());
                    note.setUpdated(Calendar.getInstance().getTime());
                    bgRealm.insertOrUpdate(note);
                }, () -> {
                     //Log.d("Note", note.toString());
                });
            }
        });
    }
}