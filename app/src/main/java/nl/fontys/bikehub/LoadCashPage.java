package nl.fontys.bikehub;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;

public class LoadCashPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_load_cash_page);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getColor(R.color.colorPrimaryDark)+"'>"+"LoadCash"+"</font>"));
    }
}
