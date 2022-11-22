package com.proyecto.diario;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.proyecto.diario.model.Note;

import java.util.Calendar;

import io.realm.Realm;

public class NoteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        // Get a Realm instance for this thread
        Realm realm = Realm.getDefaultInstance();

        Note note = new Note();
        note.setCreated(Calendar.getInstance().getTime());
        note.setLocation(31.8573718, -116.6189615);

        Button saveButton = findViewById(R.id.saveButton);
        EditText editText = findViewById(R.id.noteText);

        saveButton.setOnClickListener(view -> onBackPressed());
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                realm.executeTransactionAsync(bgRealm -> {
                    note.setContent(editable.toString());
                    note.setUpdated(Calendar.getInstance().getTime());
                    bgRealm.insertOrUpdate(note);
                }, () -> {
                    // Log.d("Note", note.toString());
                });
            }
        });
    }
}