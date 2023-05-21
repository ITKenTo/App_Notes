package com.example.appnotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.appnotes.Database.NotesDatabase;
import com.example.appnotes.Entity.NoteEntity;
import com.example.appnotes.databinding.ActivityDetailBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    ActivityDetailBinding binding;
    Uri imageUri;
    String img_url, old_img;
    SimpleDateFormat simpleDateFormat= new SimpleDateFormat("EEEE, dd MMMM yyyy HH:mm a" , Locale.getDefault());
    NotesDatabase db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      binding= DataBindingUtil.setContentView(this,R.layout.activity_detail);

      db=NotesDatabase.getInstance(this);
        NoteEntity note= (NoteEntity) getIntent().getSerializableExtra("Detail");
        old_img=note.getImagePath();
        binding.inputNoteTitle.setText(note.getTitle());
        binding.tvDatetime.setText(note.getDatetime());
        binding.inputNotesSubtitle.setText(note.getSubtitle());
        binding.inputNotes.setText(note.getNoteText());
        Glide.with(this).load(note.getImagePath()).into(binding.imgNoteDetail);
        binding.imgBack.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.imgDelete.setOnClickListener(view -> {
            Delete(note);
        });

        binding.imgSave.setOnClickListener(view -> {
            Update(note);
        });

        binding.imgAddimageDetail.setOnClickListener(view -> {
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
        binding.imgNoteDetail.setVisibility(View.VISIBLE);
        binding.imgNoteDetail.setImageURI(imageUri);
    }
    public void Delete(NoteEntity note){
        AlertDialog.Builder builder= new AlertDialog.Builder(this);
        builder.setTitle("Thông báo");
        builder.setMessage("Bạn có chắc muốn xóa ghi chú");

        builder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                db.noteDao().Delete(note);
                Toast.makeText(DetailActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                onBackPressed();
            }
        });

        builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        AlertDialog dialog=builder.create();
        dialog.show();
    }

    public void Update(NoteEntity note) {
        NoteEntity noteEntity= new NoteEntity();
        noteEntity.setId(note.getId());
        noteEntity.setDatetime(simpleDateFormat.format(new Date()));
        noteEntity.setTitle(binding.inputNoteTitle.getText().toString());
        noteEntity.setNoteText(binding.inputNotes.getText().toString());
        noteEntity.setSubtitle(binding.inputNotesSubtitle.getText().toString());
        if (img_url!=null) {
            noteEntity.setImagePath(img_url);
        }else {
            noteEntity.setImagePath(old_img);
        }
        db.noteDao().update(noteEntity);
        Toast.makeText(this, "Đã Cập Nhật", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(this,MainActivity.class));

    }
}