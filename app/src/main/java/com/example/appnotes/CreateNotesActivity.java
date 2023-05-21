package com.example.appnotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.appnotes.Database.NotesDatabase;
import com.example.appnotes.Entity.NoteEntity;
import com.example.appnotes.databinding.ActivityCreateNotesBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.SplittableRandom;

public class CreateNotesActivity extends AppCompatActivity {
    Uri imageUri;
    String img_url;
    ActivityCreateNotesBinding binding;
    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a" , Locale.getDefault());

    NotesDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_create_notes);

        db= NotesDatabase.getInstance(this);
        binding.tvDatetime.setText(simpleDateFormat.format(new Date()));

        binding.imgSave.setOnClickListener(view -> {
            insertNotes();
        });

        binding.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });


       binding.imgAddimag.setOnClickListener(view -> {
           ImagePicker.with(this)
                   .crop()	    			//Crop image(Optional), Check Customization for more option
                   .compress(1024)			//Final image size will be less than 1 MB(Optional)
                   .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                   .start();
       });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
             imageUri = data.getData();
             img_url=imageUri.toString();
             binding.imgNot.setVisibility(View.VISIBLE);
             binding.imgNot.setImageURI(imageUri);
    }

    public void insertNotes(){
        if (binding.inputNoteTitle.getText().toString().isEmpty()) {
            Toast.makeText(this, "Note title can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }else if (binding.inputNotesSubtitle.getText().toString().isEmpty() && binding.inputNotes.getText().toString().isEmpty()){
            Toast.makeText(this, "Note can't be empty", Toast.LENGTH_SHORT).show();
            return;
        }

        NoteEntity note= new NoteEntity();
        note.setTitle(binding.inputNoteTitle.getText().toString());
        note.setSubtitle(binding.inputNotesSubtitle.getText().toString());
        note.setNoteText(binding.inputNotes.getText().toString());
        note.setDatetime(binding.tvDatetime.getText().toString());
        note.setImagePath(img_url);

        db.noteDao().Insert(note);
        startActivity(new Intent(getApplication(),MainActivity.class));
    }
}