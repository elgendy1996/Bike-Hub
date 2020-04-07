package nl.fontys.bikehub;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.annotation.MainThread;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import nl.fontys.dbmethods.Bike;
import nl.fontys.dbmethods.GetBikeMethod;


public class QRScanPage extends AppCompatActivity {

    private SurfaceView svBarcode;
    private Button barCodeButton;
    private Button manualBtn;


    private BarcodeDetector detector;
    private CameraSource cameraSource;
    final private Activity activity = this;
    QRScanPage qr = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qrscan_page);


        svBarcode = findViewById(R.id.surfaceView);
        //barCodeButton = findViewById(R.id.cancelButton);
        manualBtn = findViewById(R.id.manualButton);

        detector = new BarcodeDetector.Builder(getApplicationContext()).setBarcodeFormats(Barcode.CODE_39).build();

        detector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() { }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> barcodes = detections.getDetectedItems();
                if (barcodes.size() != 0) {

                    int barcode = barcodes.valueAt(0).format;
                    Toast.makeText(getApplicationContext(), "barcode = "+ barcode, Toast.LENGTH_SHORT)
                            .show();
                    new GetBikeMethod(activity, barcode, qr, 1);
                }
            }
        });

        cameraSource = new CameraSource.Builder(this, detector)//.setRequestedPreviewSize(1024, 768)
                .setRequestedFps(25f).setAutoFocusEnabled(true).build();
        svBarcode.getHolder().addCallback(new SurfaceHolder.Callback() {
            @Override
            public void surfaceCreated(SurfaceHolder holder) {

                String[] pm = new String[1];
                pm[0] = Manifest.permission.CAMERA;

                if (ActivityCompat.checkSelfPermission(getApplicationContext(), Manifest.permission
                        .CAMERA) == PackageManager.PERMISSION_GRANTED) {
                    try {
                        cameraSource.start(holder);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "This app won't work without camera permission!", Toast.LENGTH_SHORT)
                            .show();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) { }

            @Override
            public void surfaceDestroyed(SurfaceHolder holder) {
                cameraSource.stop();
            }
        });

        manualBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(QRScanPage.this, BikeInputPage.class));
            }
        });


    }

    public void checkBike(final Bike bike){
        Intent intent = new Intent(QRScanPage.this, DialogUnlockPage.class);
        intent.putExtra("idKey", bike.getId());
        intent.putExtra("barcode", bike.getBarCode());
        intent.putExtra("lastlocklat", bike.getLastLockLat());
        intent.putExtra("lastlocklng", bike.getLastLockLng());
        intent.putExtra("currentlocklat", bike.getCurrentLockLat());
        intent.putExtra("currentlocklng", bike.getCurrentLockLng());
        intent.putExtra("lockstatus", bike.getLockStatus());
        intent.putExtra("storagehub", bike.getHub());
        startActivity(intent);

    }

    @Override
    public void onDestroy(){
        super.onDestroy();
        detector.release();
        cameraSource.stop();
        cameraSource.release();
    }
}
