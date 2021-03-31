package io.realworld.android.torch;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.Manifest;
import android.content.Context;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

public class MainActivity extends AppCompatActivity {

    SwitchCompat imageButton;
    ImageView imageView;
    boolean state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageButton = findViewById(R.id.torchbtn);
        imageView = findViewById(R.id.imageView);

        Dexter.withContext(this).withPermission(Manifest.permission.CAMERA)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        runFlashlight();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                    }
                }).check();
    }

    private void runFlashlight() {
//        imageButton.setOnClickListener(v -> {
//            if(!state){
//                CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//                try {
//                    String cameraId = cameraManager.getCameraIdList()[0];
//                    cameraManager.setTorchMode(cameraId, true);
//                    state = true;
//                } catch (CameraAccessException e) {
//                    e.printStackTrace();
//                }
//            }else {
//                CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
//                try {
//                    String cameraId = cameraManager.getCameraIdList()[0];
//                    cameraManager.setTorchMode(cameraId, false);
//                    state = false;
//                } catch (CameraAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
        imageButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId, true);
                        state = true;
                        imageView.setImageResource(R.drawable.torch_on);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }else {
                    CameraManager cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
                    try {
                        String cameraId = cameraManager.getCameraIdList()[0];
                        cameraManager.setTorchMode(cameraId, false);
                        state = false;
                        imageView.setImageResource(R.drawable.torch_off);
                    } catch (CameraAccessException e) {
                        e.printStackTrace();
                    }
                }

            }
        });
    }
}