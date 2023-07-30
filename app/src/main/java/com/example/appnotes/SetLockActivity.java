package com.example.appnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appnotes.databinding.ActivitySetLockBinding;

public class SetLockActivity extends AppCompatActivity {

    ActivitySetLockBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= DataBindingUtil.setContentView(this,R.layout.activity_set_lock);

        binding.imageView.setOnClickListener(view -> {
            onBackPressed();
        });

        binding.btnPass.setOnClickListener(view -> {
            if (!binding.edPassword.getText().toString().isEmpty()){
                SharedPreferences sharedPreferences= getSharedPreferences("PASS", MODE_PRIVATE);
                SharedPreferences.Editor editor=sharedPreferences.edit();
                editor.putInt("PASSAPP", Integer.parseInt(binding.edPassword.getText().toString()));
                editor.putBoolean("STATUS",true);
                editor.apply();
                Log.d("TAG", "onCreate: "+binding.edPassword.getText().toString());
                Toast.makeText(this, "Đã Lưu", Toast.LENGTH_SHORT).show();
                onBackPressed();

            }
        });

        binding.tvDeletepass.setOnClickListener(view -> {
            SharedPreferences sharedPreferences= getSharedPreferences("PASS", MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putInt("PASSAPP",0);
            editor.putBoolean("STATUS",false);
            editor.apply();
            onBackPressed();
        });
    }
}