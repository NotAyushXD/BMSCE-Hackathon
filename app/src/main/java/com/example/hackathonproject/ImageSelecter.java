package com.example.hackathonproject;


import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageSelecter extends Activity {

    Button btnScan;
    private final int GALLERY_ACTIVITY_CODE = 200;
    private final int RESULT_CROP = 400;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnScan = findViewById(R.id.btnScan);
        btnScan.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) { //Start Activity To Select Image From Gallery
                Intent gallery_Intent = new Intent(getApplicationContext(), GalleryUtil.class);
                startActivityForResult(gallery_Intent, GALLERY_ACTIVITY_CODE);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_ACTIVITY_CODE) {
            if (resultCode == Activity.RESULT_OK) {
                picturePath = data.getStringExtra("picturePath"); //perform Crop on the Image Selected from Gallery
                performCrop(picturePath);
            }
        }
        if (requestCode == RESULT_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap selectedBitmap = extras.getParcelable("data"); // Set The Bitmap Data To
                ImageView image_capture1.setImageBitmap(selectedBitmap);
                image_capture1.setScaleType(ImageView.ScaleType.FIT_XY);
            }
        }
    }

    private void performCrop(String picUri) {
        try { //Start Crop Activity
            Intent cropIntent = new Intent("com.android.camera.action.CROP"); // indicate image type and Uri File f = new File(picUri); Uri contentUri = Uri.fromFile(f); cropIntent.setDataAndType(contentUri, "image/*"); // set crop properties
            cropIntent.putExtra("crop", "true"); // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1); // indicate output X and Y
            cropIntent.putExtra("outputX", 280);
            cropIntent.putExtra("outputY", 280); // retrieve data on return
            cropIntent.putExtra("return-data", true); // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, RESULT_CROP);
        } // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) { // display an error message
            String errorMessage = "your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}

