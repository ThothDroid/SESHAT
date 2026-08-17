package com.blueapps.seshatexampleapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.blueapps.seshat.Seshat;
import com.blueapps.seshatexampleapp.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements ActivityResultCallback<ActivityResult> {

    private ActivityMainBinding binding;

    private String exportContent = "";

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

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), this);

        binding.buttonExport.setOnClickListener(v -> {
            Seshat seshat = new Seshat(binding.input.getText().toString(), null, null);
            exportContent = seshat.convertToSVGString(this);
            startSAF(activityResultLauncher);
        });
    }

    private void startSAF(ActivityResultLauncher<Intent> activityResultLauncher){

        // Start SAF
        Intent intent = new Intent(Intent.ACTION_CREATE_DOCUMENT);
        intent.setType("image/svg+xml");
        intent.putExtra(Intent.EXTRA_MIME_TYPES, new String[]{"image/svg+xml"});
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.putExtra(Intent.EXTRA_TITLE, "test.svg");
        activityResultLauncher.launch(intent);

    }

    public void writeFile(ContentResolver contentResolver, Uri uri, String content) throws IOException {
        try (OutputStream outputStream = contentResolver.openOutputStream(uri)) {
            if (outputStream != null) {
                outputStream.write(content.getBytes());
            } else {
                throw new IOException("Unable to open OutputStream for URI: " + uri);
            }
        }
    }

    @Override
    public void onActivityResult(ActivityResult result) {
        if (result.getResultCode() == Activity.RESULT_OK) {
            // There are no request codes
            Intent data = result.getData();
            if (data != null) {
                Uri uri = data.getData();
                if (uri != null) {
                    try {
                        writeFile(getContentResolver(), uri, exportContent);
                        exportContent = "";
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}