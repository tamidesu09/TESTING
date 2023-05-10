package com.example.testing.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.testing.Model.Note;
import com.example.testing.NotesClick;
import com.example.testing.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapter extends RecyclerView.Adapter<NotesViewHolder> {
    Context context;
    List<Note> list;
    NotesClick notesClick;

    public NotesListAdapter(Context context, List<Note> list, NotesClick notesClick) {
        this.context = context;
        this.list = list;
        this.notesClick = notesClick;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list,parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {

        holder.textView_title.setText(list.get(position).getTitle());
        holder.textView_title.setSelected(true);

        holder.textView_note.setText(list.get(position).getNotes());
        holder.textView_date.setText(list.get(position).getDate());
        holder.textView_date.setSelected(true);

        if(list.get(position).isPinned()){
            holder.pin_image.setImageResource(R.drawable.ic_baseline_push_pin_24);
        }
        else
        {
            holder.pin_image.setImageResource(0);
        }
        int color_code=getRandomColor();
        holder.notes_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code, null));

        holder.notes_container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notesClick.onClick(list.get(holder.getAdapterPosition()));
            }
        });
        holder.notes_container.setOnLongClickListener(new View.OnLongClickListener(){
            @Override
            public boolean onLongClick(View view) {
                notesClick.onLongClick(list.get(holder.getAdapterPosition()), holder.notes_container);

                return true;
            }
        });

    }

    private int getRandomColor(){
        List<Integer> colorCode =new ArrayList<>();

        colorCode.add(R.color.color1);
        colorCode.add(R.color.color2);
        colorCode.add(R.color.color3);
        colorCode.add(R.color.color4);
        colorCode.add(R.color.color4);

        Random random=new Random();
        int random_color=random.nextInt(colorCode.size());
        return random_color;

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
class NotesViewHolder extends RecyclerView.ViewHolder{


    CardView notes_container;
    TextView textView_title, textView_note, textView_date;
    ImageView pin_image;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        notes_container=itemView.findViewById(R.id.notes_card_container);
        textView_title=itemView.findViewById(R.id.title_text);
        textView_note=itemView.findViewById(R.id.textview_note);
        textView_date=itemView.findViewById(R.id.textview_date);
        pin_image=itemView.findViewById(R.id.image_view_pin);


    }
}
