package com.example.appnotes;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.appnotes.databinding.ActivityLockBinding;

public class LockActivity extends AppCompatActivity {
    int check;
    int idButton;
    Boolean status;
    String passstring="";
    ActivityLockBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       binding= DataBindingUtil.setContentView(this,R.layout.activity_lock);

        SharedPreferences preferences = getSharedPreferences("PASS",MODE_PRIVATE);
        check= preferences.getInt("PASSAPP",0);
        status= preferences.getBoolean("STATUS",false);
        if (status==false){
            startActivity(new Intent(this,MainActivity.class));
        }

        setNum();
        Log.d("TAG", "onCreate: "+check);
    }
    public void chekPass(){
        int pass= Integer.parseInt(binding.edPass.getText().toString());
        if (pass==check){
            startActivity(new Intent(this,MainActivity.class));
        }else {
            Toast.makeText(this, "Mật Khẩu không Đúng", Toast.LENGTH_SHORT).show();
        }
    }

    @SuppressLint("NonConstantResourceId")
    public void setNum(){
      binding.num0.setOnClickListener(view -> {
//          binding.edPass.setText("0");
          passstring+=0;
          binding.edPass.setText(passstring);
      });
        binding.num1.setOnClickListener(view -> {
            passstring+=1;
            binding.edPass.setText(passstring);
        });
        binding.num2.setOnClickListener(view -> {
            passstring+=2;
            binding.edPass.setText(passstring);
        });
        binding.num3.setOnClickListener(view -> {
            passstring+=3;
            binding.edPass.setText(passstring);
        });
        binding.num4.setOnClickListener(view -> {
            passstring+=4;
            binding.edPass.setText(passstring);
        });
        binding.num5.setOnClickListener(view -> {
            passstring+=5;
            binding.edPass.setText(passstring);
        });
        binding.num6.setOnClickListener(view -> {
            passstring+=6;
            binding.edPass.setText(passstring);
        });
        binding.num7.setOnClickListener(view -> {
            passstring+=7;
            binding.edPass.setText(passstring);
        });
        binding.num8.setOnClickListener(view -> {
            passstring+=8;
            binding.edPass.setText(passstring);
        });
        binding.num9.setOnClickListener(view -> {
            passstring+=9;
            binding.edPass.setText(passstring);
        });
        binding.clear.setOnClickListener(view -> {
           binding.edPass.setText("");
           passstring="";
        });

        binding.btnOk.setOnClickListener(view -> {
            chekPass();
        });

    }
}