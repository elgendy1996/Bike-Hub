package nl.fontys.bikehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ReportIssuesPage extends AppCompatActivity {

    private Button btnBroken;
    private Button btnUnlock;
    private Button btnViolation;
    private Button btnOther;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_issues_page);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getColor(R.color.colorPrimaryDark)+"'>"+"Report your Issue"+"</font>"));

        btnBroken = (Button) findViewById(R.id.btnBroken);
        btnUnlock = (Button) findViewById(R.id.btnUnlock);
        btnViolation = (Button) findViewById(R.id.btnViolation);
        btnOther = (Button) findViewById(R.id.btnOther);




        btnBroken.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"fontys.bikehub@yahoo.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Report Issue: Broken Bike");
                i.putExtra(Intent.EXTRA_TEXT, "Hello Support, --Enter your Issues--");
                // we get the id with the email adress
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ReportIssuesPage.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
            });

        btnUnlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"fontys.bikehub@yahoo.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Report Issue: Can't unlock");
                i.putExtra(Intent.EXTRA_TEXT, "Hello Support, --Enter your Issues--");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ReportIssuesPage.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnViolation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"fontys.bikehub@yahoo.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Report Issue: Report a Violation");
                i.putExtra(Intent.EXTRA_TEXT, "Hello Support, --Enter your Issues--");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ReportIssuesPage.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_SEND);
                i.setType("message/rfc822");
                i.putExtra(Intent.EXTRA_EMAIL, new String[]{"fontys.bikehub@yahoo.com"});
                i.putExtra(Intent.EXTRA_SUBJECT, "Report Issue: Other");
                i.putExtra(Intent.EXTRA_TEXT, "Hello Support, --Enter your Issues--");
                try {
                    startActivity(Intent.createChooser(i, "Send mail..."));
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(ReportIssuesPage.this, "There are no email clients installed.", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}