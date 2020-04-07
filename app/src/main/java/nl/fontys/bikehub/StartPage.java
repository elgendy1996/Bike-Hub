package nl.fontys.bikehub;

import android.Manifest;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.GeoApiContext;

import nl.fontys.bikehub.R;
import nl.fontys.bikehub.PermissionUtils;
import nl.fontys.dbmethods.GetMethode;

public class StartPage extends AppCompatActivity

  implements
  GoogleMap.OnMyLocationButtonClickListener,
  GoogleMap.OnMyLocationClickListener,
  OnMapReadyCallback,
  ActivityCompat.OnRequestPermissionsResultCallback {

  /**
   * Request code for location permission request.
   *
   * @see #onRequestPermissionsResult(int, String[], int[])
   */
  private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

  /**
   * Flag indicating whether a requested permission has been denied after returning in
   * {@link #onRequestPermissionsResult(int, String[], int[])}.
   */
  private boolean mPermissionDenied = false;


    private GoogleMap mMap;
    private GeoApiContext mGeoApiContext;
    private Marker mSelectedMarker = null;
    private Button Scannerbtn;



  @Override
  protected void onCreate(Bundle savedInstanceState) {



    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_start_page);

    Drawable.Callback color;
    getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getColor(R.color.colorPrimaryDark)+"'>"+"BikeHub"+"</font>"));

    Scannerbtn = findViewById(R.id.Scannerbtn);



    SupportMapFragment mapFragment =
      (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
    assert mapFragment != null;
    mapFragment.getMapAsync(this);


    Scannerbtn.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(StartPage.this, QRScanPage.class));
      }
    });


  }

  @Override

  public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.menu, menu);
    getMenuInflater().inflate(R.menu.menubutton, menu);
    getMenuInflater().inflate(R.menu.qrmenu, menu);
    return super.onCreateOptionsMenu(menu);
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.btnBrokenbike) {
      startActivity(new Intent(StartPage.this, ReportIssuesPage.class));
    }

    if (id == R.id.btnmainmenu) {
      startActivity(new Intent(StartPage.this, MainMenuPage.class));
    }

    if (id == R.id.btqrmenu) {
      GetMethode.resetLogin();
      startActivity(new Intent(StartPage.this, LogInPage.class));
    }


    return super.onOptionsItemSelected(item);
  }

  @Override
  public void onMapReady(GoogleMap map) {
    mMap = map;

    LatLng fontysHub = new LatLng(51.353860, 6.154757);
    LatLng VenloStationHub = new LatLng(51.365674, 6.171406);
    LatLng BlerickStationHub = new LatLng(51.371758, 6.153930
    );


    Bitmap.Config conf = Bitmap.Config.ARGB_8888;
    Bitmap bmp = Bitmap.createBitmap(80, 80, conf);
    Canvas canvas1 = new Canvas(bmp);


// paint defines the text color, stroke width and size
    Paint color = new Paint();
    color.setTextSize(35);
    color.setColor(Color.BLACK);

// modify canvas
    canvas1.drawBitmap(BitmapFactory.decodeResource(getResources(),
      R.drawable.marker__bike), 0, 0, color);
    canvas1.drawText("Bike Hub", 70, 50, color);



    //@SuppressWarnings("deprecation")
    BitmapDrawable bitmap = (BitmapDrawable) ResourcesCompat.getDrawable(getResources(), R.drawable.marker__bike, null);

    assert bitmap != null;
    Bitmap b = bitmap.getBitmap();
    Bitmap smallMarker = Bitmap.createScaledBitmap(b, 150, 150, false);

// add marker to Map
    mMap.addMarker(new MarkerOptions()
      .position(BlerickStationHub).title("Blerick Hub ")
      .snippet("5 bikes")
      .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
      // Specifies the anchor to be at a particular point in the marker image.
      .anchor(0.5f, 1).flat(true));

    mMap.addMarker(new MarkerOptions()
      .position(fontysHub).title("Fontys Hub ")
      .snippet("3 bikes")
      .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
      // Specifies the anchor to be at a particular point in the marker image.
      .anchor(0.5f, 1).flat(true));

      mMap.addMarker(new MarkerOptions()
                      .position(VenloStationHub).title("Venlo Station Hub ")
                      .snippet("10 bikes")
                      .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
              .anchor(0.5f, 1).flat(true));

  mMap.setOnMyLocationButtonClickListener(this);

      mMap.setOnMyLocationClickListener(this);


    enableMyLocation();
  }


  /**
   * Enables the My Location layer if the fine location permission has been granted.
   */
  private void enableMyLocation() {
    if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
      != PackageManager.PERMISSION_GRANTED) {
      // Permission to access the location is missing.
      PermissionUtils.requestPermission(this, LOCATION_PERMISSION_REQUEST_CODE,
        Manifest.permission.ACCESS_FINE_LOCATION, true);
    } else if (mMap != null) {
      // Access to the location has been granted to the app.
      mMap.setMyLocationEnabled(true);
    }
  }


  @Override
  public boolean onMyLocationButtonClick() {
    Toast.makeText(this, "You are here!", Toast.LENGTH_SHORT).show();
    // Return false so that we don't consume the event and the default behavior still occurs
    // (the camera animates to the user's current position).
    return false;
  }




  @Override
  public void onMyLocationClick(@NonNull Location location) {
    Toast.makeText(this, "Current location:\n" + location, Toast.LENGTH_LONG).show();
  }

  @Override
  public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                         @NonNull int[] grantResults) {
    if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
      return;
    }

    if (PermissionUtils.isPermissionGranted(permissions, grantResults,
      Manifest.permission.ACCESS_FINE_LOCATION)) {
      // Enable the my location layer if the permission has been granted.
      enableMyLocation();
    } else {
      // Display the missing permission error dialog when the fragments resume.
      mPermissionDenied = true;
    }
  }

  @Override
  protected void onResumeFragments() {
    super.onResumeFragments();
    if (mPermissionDenied) {
      // Permission was not granted, display error dialog.
      showMissingPermissionError();
      mPermissionDenied = false;
    }
  }


  /**
   * Displays a dialog with error message explaining that the location permission is missing.
   */
  private void showMissingPermissionError() {
    PermissionUtils.PermissionDeniedDialog
      .newInstance(true).show(getSupportFragmentManager(), "dialog");
  }




   
}




