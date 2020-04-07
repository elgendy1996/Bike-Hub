package nl.fontys.bikehub;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import nl.fontys.dbmethods.Bike;
import nl.fontys.dbmethods.PutBikeMethod;

public class DialogUnlockPage extends AppCompatActivity {

    private TextView questionText;
    private Button yesBtn;
    private Button noBtn;
    final private Activity activity = this;

    Bike bike;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialog_page);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getColor(R.color.colorPrimaryDark)+"'>"+"BikeHub"+"</font>"));

        questionText = findViewById(R.id.questionText);
        yesBtn = findViewById(R.id.yesBtn);
        noBtn = findViewById(R.id.noLockBtn);

        final Activity activity = DialogUnlockPage.this;

        Bundle extras = getIntent().getExtras();
        int id = extras.getInt("idKey");
        int barcode = extras.getInt("barcode");
        int lastlocklat = extras.getInt("lastlocklat");
        int lastlocklng = extras.getInt("lastlocklng");
        int currentlocklat = extras.getInt("currentlocklat");
        int currentlocklng = extras.getInt("currentlocklng");
        boolean lockstatus = extras.getBoolean("lockstatus");
        int storagehub = extras.getInt("storagehub");

        if (lockstatus == false){
            questionText.setText("Do you want to lock this bike?");
        }

        bike = new Bike(id, barcode, lastlocklat, lastlocklng, currentlocklat, currentlocklng, lockstatus, storagehub);

        noBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DialogUnlockPage.this, BikeInputPage.class));

            }
        });

        if (bike.getLockStatus() == true){
            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    unlockBike(bike);

                    Toast.makeText(activity,"Your Bike is unlocked!",Toast.LENGTH_LONG).show();

                }
            });
        }else if (bike.getLockStatus() == false){
            yesBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    lockBike(bike);

                    Toast.makeText(activity,"Your Bike is locked!",Toast.LENGTH_LONG).show();

                }
            });
        }
    }

    public void unlockBike(Bike fBike){
        Bike bike = fBike;
        bike.setLockStatus(false);
        new PutBikeMethod(activity, bike).execute();
        Intent intent = new Intent(DialogUnlockPage.this, StartPage.class);
        startActivity(intent);
    }

    public void lockBike(Bike fBike){
        Bike bike = fBike;
        bike.setLockStatus(true);
        new PutBikeMethod(activity, bike).execute();
        Intent intent = new Intent(DialogUnlockPage.this, StartPage.class);
        startActivity(intent);
    }
}

