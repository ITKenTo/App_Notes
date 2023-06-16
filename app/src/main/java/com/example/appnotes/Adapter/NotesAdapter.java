package com.example.appnotes.Adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.appnotes.DetailActivity;
import com.example.appnotes.Entity.NoteEntity;
import com.example.appnotes.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.viewholder>{
    List<NoteEntity> list;
    Context context;
    private Timer timer;
    private List<NoteEntity> noteList;

    public NotesAdapter(List<NoteEntity> list, Context context) {
        this.list = list;
        this.context=context;
        noteList=list;

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_note,parent,false);
        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        NoteEntity note= list.get(position);

        holder.tv_title.setText(note.getTitle());
        if (note.getSubtitle()==null) {
            holder.tv_subtitle.setVisibility(View.GONE);
        }else {
            holder.tv_subtitle.setText(note.getSubtitle());
        }
        holder.tv_datetime.setText(note.getDatetime());
        if (note.getImagePath()!=null) {
            holder.img_note.setVisibility(View.VISIBLE);
            Glide.with(context).load(note.getImagePath()).into(holder.img_note);
        }else {
            holder.img_note.setVisibility(View.GONE);
        }
       // holder.img_note.setImageURI(Uri.parse(note.getImagePath()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent= new Intent(context, DetailActivity.class);
                NoteEntity noteEntity= new NoteEntity();
                noteEntity.setId(note.getId());
                noteEntity.setTitle(note.getTitle());
                noteEntity.setSubtitle(note.getSubtitle());
                noteEntity.setNoteText(note.getNoteText());
                noteEntity.setDatetime(note.getDatetime());
                noteEntity.setImagePath(note.getImagePath());
                intent.putExtra("Detail", noteEntity);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public static class viewholder extends RecyclerView.ViewHolder {
        TextView tv_title,tv_subtitle,tv_datetime;
        ImageView img_note;

        public viewholder(@NonNull View itemView) {
            super(itemView);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_subtitle=itemView.findViewById(R.id.tv_subTitle);
            tv_datetime=itemView.findViewById(R.id.tv_Datetime);
            img_note=itemView.findViewById(R.id.img_note);

        }
    }
    public void searchnote(final String searchkey){
        timer= new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (searchkey.trim().isEmpty()){
                    list=noteList;
                }else {
                    ArrayList<NoteEntity> temp= new ArrayList<>();
                    for (NoteEntity note: noteList
                         ) {
                         if (note.getTitle().toLowerCase().contains(searchkey.toLowerCase())
                                 ||note.getSubtitle().toLowerCase().contains(searchkey.toLowerCase())
                                 ||note.getNoteText().toLowerCase().contains(searchkey.toLowerCase())
                         ){
                             temp.add(note);
                         }
                    }
                    list=temp;
                }
                new Handler(Looper.getMainLooper()).post(new Runnable() {
                    @Override
                    public void run() {
                          notifyDataSetChanged();
                    }
                });
            }
        },500);
    }
    public void cancelTimer(){
        if (timer!=null) {
            timer.cancel();
        }
    }
}
