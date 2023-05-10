package com.example.testing;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.appcompat.widget.SearchView;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.testing.Adapter.NotesListAdapter;
import com.example.testing.Database.RoomData;
import com.example.testing.Model.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {

    RecyclerView recyclerView;
    NotesListAdapter notesListAdapter;
    List<Note> notes=new ArrayList<>();
    RoomData database;
    FloatingActionButton fab_btn;
    SearchView searchView;
    Note selected_notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView=findViewById(R.id.recyclerView);
        fab_btn=findViewById(R.id.fab_add_btn);

        searchView=findViewById(R.id.search_view);

        database=RoomData.getInstance(this);
        notes=database.dao().getAll();
        updateRecycler(notes);

        fab_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent=new Intent(MainActivity.this, NotesActivity.class);
                startActivityForResult(intent, 101);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });

    }

    private void filter(String newText) {
        List<Note> filter_list= new ArrayList<>();
        for (Note single_notes : notes){
            if (single_notes.getTitle().toLowerCase().contains(newText.toLowerCase())
                || single_notes.getNotes().toLowerCase().contains(newText.toLowerCase())){
                filter_list.add(single_notes);

            }
        }
        notesListAdapter.filter_list(filter_list);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==101){
            if(resultCode== Activity.RESULT_OK){
                Note new_notes= (Note) data.getSerializableExtra("note");
                database.dao().insert(new_notes);
                notes.clear();
                notes.addAll(database.dao().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }


        else if(requestCode==102){
            if(resultCode==Activity.RESULT_OK){
                Note new_notes= (Note) data.getSerializableExtra("note");
                database.dao().update(new_notes.getID(),new_notes.getTitle(),new_notes.getNotes());
                notes.addAll(database.dao().getAll());
                notesListAdapter.notifyDataSetChanged();
            }
        }



    }

    private void updateRecycler(List<Note> notes) {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayoutManager.VERTICAL));
        notesListAdapter= new NotesListAdapter(MainActivity.this,notes, notesClick);
        recyclerView.setAdapter(notesListAdapter);

    }
    private final NotesClick notesClick=new NotesClick() {
        @Override
        public void onClick(Note note) {
            
            Intent intent=new Intent(MainActivity.this, NotesActivity.class);
            intent.putExtra("old_note",note);
            startActivityForResult(intent, 102);

        }

        @Override
        public void onLongClick(Note note, CardView cardView) {
                selected_notes=new Note();
                selected_notes=note;
                showPopup(cardView);

        }
    };

    private void showPopup(CardView cardView) {

        PopupMenu popupMenu=new PopupMenu(this, cardView);
        popupMenu.setOnMenuItemClickListener(this);
        popupMenu.inflate(R.menu.pop_up);
        popupMenu.show();


    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {

        switch (item.getItemId()){
            case R.id.pin:
                if (selected_notes.isPinned()){
                    database.dao().pin(selected_notes.getID(), false);
                    Toast.makeText(this, "Unpinned", Toast.LENGTH_SHORT).show();
                }
                else{
                    database.dao().pin(selected_notes.getID(), true);
                    Toast.makeText(this, "Pinned", Toast.LENGTH_SHORT).show();
                }

                notes.clear();
                notes.addAll(database.dao().getAll());
                return true;

            case R.id.del:
                database.dao().delete(selected_notes);
                notes.remove(selected_notes);
                notesListAdapter.notifyDataSetChanged();
                Toast.makeText(this, "Note Deleted", Toast.LENGTH_SHORT).show();
                return true;
            default:
                return false;
        }

    }
}















