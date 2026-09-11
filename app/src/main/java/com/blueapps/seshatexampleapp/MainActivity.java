package com.blueapps.seshatexampleapp;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.SeekBar;
import android.os.Handler;
import android.view.View;

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
import com.blueapps.seshat.SeshatListener;
import com.blueapps.seshatexampleapp.databinding.ActivityMainBinding;

import java.io.IOException;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity implements ActivityResultCallback<ActivityResult> , SeshatListener {

    private ActivityMainBinding binding;

    private String exportContent = "";
    private Seshat seshat;

    private boolean cssBackground = false;
    private boolean backgroundTransparent = true;

    private static final int[] Colors = {Color.BLACK, Color.WHITE, Color.GRAY, Color.CYAN, Color.MAGENTA, Color.YELLOW};
    private int textColorCursor = 0;
    private int bgColorCursor = 0;

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

        seshat = new Seshat(new Handler(getMainLooper()));

        binding.buttonExport.setOnClickListener(v -> {
            new Thread(() -> {
                seshat.setGlyphX(binding.input.getText().toString());
                seshat.addSeshatListener(MainActivity.this);
                exportContent = seshat.convertToSVGString(this, "Test", "Test description", cssBackground, backgroundTransparent);
                startSAF(activityResultLauncher);
            }).start();
        });

        // Define verticalOrientation RadioGroup
        binding.VerticalOrientation.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.top){
                seshat.setVerticalOrientation(0);
            } else if (i == R.id.middle){
                seshat.setVerticalOrientation(1);
            } else if (i == R.id.bottom){
                seshat.setVerticalOrientation(2);
            } else {
                seshat.setVerticalOrientation(1);
            }
        });

        // Define writingLayout RadioGroup
        binding.WritingLayout.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.lines){
                seshat.setWritingLayout(0);
            } else if (i == R.id.columns){
                seshat.setWritingLayout(1);
            } else {
                seshat.setWritingLayout(0);
            }
        });

        // Define writingDirection RadioGroup
        binding.WritingDirection.setOnCheckedChangeListener((radioGroup, i) -> {
            if (i == R.id.ltr){
                seshat.setWritingDirection(0);
            } else if (i == R.id.rtl){
                seshat.setWritingDirection(1);
            } else {
                seshat.setWritingDirection(0);
            }
        });

        // Define TextSize Seekbar
        binding.textSize.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.textSizeTitle.setText(getString(R.string.text_size_title) + i + "px");
                seshat.setTextSize(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        // Define text color Button
        binding.changeTextColor.setOnClickListener(view -> {
            seshat.setPrimarySignColor(Colors[textColorCursor]);
            binding.changeTextColor.setBackgroundColor(Colors[textColorCursor]);
            textColorCursor++;
            if (textColorCursor >= Colors.length){
                textColorCursor = 0;
            }
        });

        // Define bg color Button
        binding.changeBGColor.setOnClickListener(view -> {
            seshat.setBackgroundColor(Colors[bgColorCursor]);
            binding.changeBGColor.setBackgroundColor(Colors[bgColorCursor]);
            bgColorCursor++;
            if (bgColorCursor >= Colors.length){
                bgColorCursor = 0;
            }
        });

        // Define CSSBackground CheckBox
        binding.backgroundWithCSS.setOnCheckedChangeListener((compoundButton, b) -> {
            cssBackground = b;
        });

        // Define Transparent Background CheckBox
        binding.transparentBackground.setOnCheckedChangeListener((compoundButton, b) -> {
            backgroundTransparent = b;
        });

        // Lines
        binding.lineThickness.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.lineThicknessTitle.setText(getString(R.string.line_thickness_title) + i + "px");
                seshat.setLineThickness(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        binding.drawLines.setOnCheckedChangeListener((compoundButton, b) -> {
            seshat.setDrawLines(b);
        });

        // Paddings
        binding.paddingLeft.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.paddingLeftTitle.setText(getString(R.string.padding_left_title) + i + "px");
                seshat.setPagePaddingLeft(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.paddingTop.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.paddingTopTitle.setText(getString(R.string.padding_top_title) + i + "px");
                seshat.setPagePaddingTop(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.paddingRight.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.paddingRightTitle.setText(getString(R.string.padding_right_title) + i + "px");
                seshat.setPagePaddingRight(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.paddingBottom.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.paddingBottomTitle.setText(getString(R.string.padding_bottom_title) + i + "px");
                seshat.setPagePaddingBottom(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.signPadding.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.signPaddingTitle.setText(getString(R.string.sign_padding_title) + i + "px");
                seshat.setSignPadding(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.layoutSignPadding.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.layoutSignPaddingTitle.setText(getString(R.string.layout_sign_padding_title) + i + "px");
                seshat.setLayoutSignPadding(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        binding.interLinePadding.setProgress(25);
        binding.interLinePadding.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                binding.interLinePaddingTitle.setText(getString(R.string.inter_line_padding) + i + "px");
                seshat.setInterLinePadding(i);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
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

    @Override
    public void onExportStarted() {
        binding.progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onExportProgress(int progress, int total) {
        binding.progressText.setVisibility(View.VISIBLE);
        binding.progressText.setText(progress + "/" + total + " " + (int) ((float) progress / total * 100) + "%");
        binding.progressBar.setProgress((int) ((float) progress / total * 100));
    }

    @Override
    public void onPostProcessingStarted() {

    }

    @Override
    public void onExportCompleted() {
        binding.progressBar.setVisibility(View.INVISIBLE);
        binding.progressText.setVisibility(View.INVISIBLE);
    }
}