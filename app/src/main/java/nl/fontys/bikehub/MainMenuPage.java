package nl.fontys.bikehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

public class MainMenuPage extends AppCompatActivity {

    private ImageButton btCash;
    private ImageView avatar;
    private ImageButton btStats;
    private ImageButton btProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu_page);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getColor(R.color.colorPrimaryDark)+"'>"+"BikeHub"+"</font>"));

        btCash = findViewById(R.id.ibtnCash);
        avatar = findViewById(R.id.ivAvatar);
        btStats = findViewById(R.id.ibtnStats);
        btProfile = findViewById(R.id.ibtnEditProfile);

        btCash.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuPage.this, LoadCashPage.class));
            }
        });

        btStats.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuPage.this, StatsPage.class));
            }
        });

        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainMenuPage.this, ProfilePage.class));
            }
        });
    }
}
