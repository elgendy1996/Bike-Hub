package nl.fontys.bikehub;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;

import nl.fontys.dbmethods.GetMethode;

import static java.lang.Thread.sleep;

public class LogInPage extends AppCompatActivity {

    Thread ait = null;
    private EditText Username;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private TextView Register;
    private int counter = 0;

    //FIREBASE TAG
    private static final String TAG = "myTag";
    public static String FirebaseDeviceToken;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        Username = (EditText)findViewById(R.id.ptUsername);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView) findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        Register = (TextView)findViewById(R.id.tvSignUp);



        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = Username.getText().toString();
                String password = Password.getText().toString();
                new GetMethode(LogInPage.this, username,password).execute();


                    validate();





            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LogInPage.this, RegisterPage.class));
            }
        });

        System.out.println("BEFORE GET TOKEN");
        // FIREBASE REQUEST DEVIDE TOKEN!
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        FirebaseDeviceToken = token;
                        System.out.println(token);
                    }
                });


    }



    public void validate() {
        if(GetMethode.loginAllowed==true){
            startActivity(new Intent(LogInPage.this, StartPage.class));
        }else if(GetMethode.loginAllowed==false){
            counter++;

            Info.setText("Try Again!");
        }
    }




        }




