package nl.fontys.bikehub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

public class StatsPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_page);
        getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getColor(R.color.colorPrimaryDark)+"'>"+"Statistics"+"</font>"));
    }
}
