package com.example.testing;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.testing.Model.Note;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NotesActivity extends AppCompatActivity {

    EditText editText_title, editText_notes;
    ImageView image_save;
    Note note;
    boolean is_old_note=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);


        image_save=findViewById(R.id.image_view_save);
        editText_title=findViewById(R.id.edit_text_title);
        editText_notes=findViewById(R.id.ed_notes);
        is_old_note=true;

        note=new Note();
        try {
            note= (Note) getIntent().getSerializableExtra("old_note");
            editText_title.setText(note.getTitle());
            editText_notes.setText(note.getNotes());


        }catch (Exception e){
            e.printStackTrace();
        }



        image_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=editText_title.getText().toString();
                String description=editText_notes.getText().toString();
                
                
                
                if (description.isEmpty()){
                    Toast.makeText(NotesActivity.this, "Please, add some notes!", Toast.LENGTH_SHORT).show();
                    return;
                }
                SimpleDateFormat format=new SimpleDateFormat("EEE, d MMM yyyy HH : mm: a");
                Date date=new Date();

                if(is_old_note){
                    note=new Note();
                }


                note.setTitle(title);
                note.setNotes(description);
                note.setDate(format.format(date));

                Intent intent=new Intent();
                intent.putExtra("note", note);
                setResult(Activity.RESULT_OK,intent);
                finish();

            }
        });
    }
}