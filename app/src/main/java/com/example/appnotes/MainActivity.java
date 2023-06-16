package com.example.appnotes;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;

import com.example.appnotes.Adapter.NotesAdapter;
import com.example.appnotes.Database.NotesDatabase;
import com.example.appnotes.Entity.NoteEntity;
import com.example.appnotes.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    List<NoteEntity> list;
    NotesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.imgAddnotesmain.setOnClickListener(view -> {
//            Intent intent = new Intent(getApplicationContext(),CreateNotesActivity.class);
//            startActivity(intent);
            startActivityForResult(new Intent(getApplicationContext(),CreateNotesActivity.class),1);
        });
        list= new ArrayList<>();
        list= NotesDatabase.getInstance(this).noteDao().getAllNotes();
        adapter= new NotesAdapter(list,getApplication());
        binding.noterecycleview.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        binding.noterecycleview.setAdapter(adapter);

        binding.edIpsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                   adapter.cancelTimer();
            }

            @Override
            public void afterTextChanged(Editable editable) {
                  if (list.size()!=0) {
                      adapter.searchnote(editable.toString());
                  }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        list.clear();
        list.addAll(NotesDatabase.getInstance(this).noteDao().getAllNotes());
        adapter.notifyDataSetChanged();
    }
}