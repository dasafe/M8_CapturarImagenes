package com.example.capturaimagenes;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;


public class MainActivity extends AppCompatActivity {

    static final int REQUEST_IMAGE_CAPTURE = 1;
    Intent takePictureIntent;
    File dir = new File("data" + File.separator + "data"
            + File.separator + "com.example.capturaimagenes"
            + File.separator + "fotos");

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            ImageView iv = findViewById(R.id.imageView);
            iv.setImageBitmap(imageBitmap);
            OutputStream os = null;
            try {
                for (int i = 0; i <= dir.listFiles().length; i++) {
                    File file = new File(dir, "foto" + i + ".png");
                    if (!file.exists()) {
                        os = new FileOutputStream(file);
                        imageBitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
                        i = dir.listFiles().length;
                    }
                }
            } catch (IOException e) {
                System.out.println("ERROR");
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!dir.exists()) {
            dir.mkdir();
        }
        ImageView img = findViewById(R.id.imageView);
        if (dir.listFiles().length > 0) {
            File image = new File(dir, dir.listFiles()[dir.listFiles().length - 1].getName());
            if (image.exists()) {
                Uri uri = Uri.fromFile(image);
                img.setImageURI(uri);
            }
        }
        Button b = findViewById(R.id.button);
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });
    }
}