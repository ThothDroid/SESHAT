package com.blueapps.seshatexampleapp;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.blueapps.seshat.Seshat;
import com.blueapps.seshatexampleapp.databinding.ActivityMainBinding;

import org.w3c.dom.Document;

import javax.xml.transform.TransformerException;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(binding.main, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.buttonExport.setOnClickListener(v -> {
            Seshat seshat = new Seshat(binding.input.getText().toString());
            try {
                binding.output.setText(seshat.convertToSVGString());
            } catch (TransformerException e) {
                e.printStackTrace();
            }
        });
    }
}