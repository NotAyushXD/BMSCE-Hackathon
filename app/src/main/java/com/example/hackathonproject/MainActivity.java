package com.example.hackathonproject;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.vision.Frame;
import com.google.android.gms.vision.text.TextBlock;
import com.google.android.gms.vision.text.TextRecognizer;

public class MainActivity extends AppCompatActivity {

    private static final int CAMERA_REQUEST = 14234;
    private static final int MY_CAMERA_PERMISSION_CODE = 324;
    private static final int PICK_IMAGE = 34525;
    Button btnScan;
    TextView tvText;
    ImageView ivImage;
    Bitmap image;

    Uri imageUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnScan = findViewById(R.id.btnScan);
        tvText = findViewById(R.id.tvText);
        ivImage = findViewById(R.id.ivImage);
        ivImage.setImageResource(R.drawable.img);
//        BitmapDrawable bitmapDrawable = (BitmapDrawable) ivImage.getDrawable();
//        Bitmap bitmap = bitmapDrawable.getBitmap();
//
//        recogniseImageText(bitmap);

    }

    private void recogniseImageText(Bitmap bitmap) {
        TextRecognizer recognizer = new TextRecognizer.Builder(getApplicationContext()).build();
        Frame frame = new Frame.Builder().setBitmap(bitmap).build();
        SparseArray<TextBlock> items = recognizer.detect(frame);
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < items.size(); i++) {
            TextBlock myItem = items.valueAt(i);
            sb.append(myItem.getValue());
            sb.append("\n");
        }

        tvText.setText(sb.toString());
    }

    @Override
    protected void onStart() {
        super.onStart();
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);

                recogniseImageText(image);
            }
        });

        ivImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImageFromGallery();
            }
        });

    }

    private void openImageFromGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_CAMERA_PERMISSION_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show();
                Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAMERA_REQUEST);
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    protected void onActivityResult(int req, int resultCode, Intent data) {
        super.onActivityResult(req, resultCode, data);
        if(resultCode == RESULT_OK && req == PICK_IMAGE){
            imageUri = data.getData();
            ivImage.setImageURI(imageUri);
        }
    }

}