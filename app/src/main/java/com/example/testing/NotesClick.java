package com.example.testing;

import androidx.cardview.widget.CardView;

import com.example.testing.Model.Note;

public interface NotesClick {

    void onClick(Note note);
    void onLongClick(Note note, CardView cardView);

}
