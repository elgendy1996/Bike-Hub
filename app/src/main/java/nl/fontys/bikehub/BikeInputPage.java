package nl.fontys.bikehub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import nl.fontys.dbmethods.Bike;
import nl.fontys.dbmethods.GetBikeMethod;

public class BikeInputPage extends AppCompatActivity {
    private EditText bikeCode;
    private Button unlockBtn;
    private Button cancelBtn;
    private TextView questionText;
    final private Activity activity = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bikeinput_page);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getColor(R.color.colorPrimaryDark)+"'>"+"BikeHub"+"</font>"));

        bikeCode = (EditText)findViewById(R.id.bikeNumberInput);
        unlockBtn = (Button)findViewById(R.id.bikeUnlockButton);
        cancelBtn = (Button)findViewById(R.id.cancelUnlockButton);
        final Activity activity = BikeInputPage.this;
        questionText = findViewById(R.id.questionText);

        unlockBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getBike();

            }
        });

        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(BikeInputPage.this, StartPage.class));
            }
        });

    }

    public void getBike(){
        final int barCode = Integer.parseInt(bikeCode.getText().toString());
        //Bike bike = new Bike(2, 2, 1, 1, 1, 1,false,1 );
        //new PutBikeMethod(this, bike).execute();
        new GetBikeMethod(this, barCode, this, 0).execute();
    }

    public void checkBike(final Bike bike){
        Intent intent = new Intent(BikeInputPage.this, DialogUnlockPage.class);
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


}


