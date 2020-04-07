package nl.fontys.bikehub;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import nl.fontys.dbmethods.AppUser;
import nl.fontys.dbmethods.PostMethode;

public class RegisterPage extends AppCompatActivity {

    private TextView AllreadySignUp;
    private EditText FirstName;
    private EditText LastName;
    private EditText Username;
    private EditText Password;
    private EditText Email;
    private EditText Birthday;
    private EditText Address;
    private RadioGroup Gender;
    private RadioButton Male;
    private RadioButton Female;
    private RadioButton Diverse;
    private Button SignUp;
    public String cGender;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_page);

        getSupportActionBar().setTitle(Html.fromHtml("<font color='"+getColor(R.color.colorPrimaryDark)+"'>"+"BikeHub"+"</font>"));

        AllreadySignUp = (TextView)findViewById(R.id.tvAllreadySignUp);
        FirstName = (EditText)findViewById(R.id.ptFirstname);
        LastName = (EditText)findViewById(R.id.ptLastname);
        Username = (EditText)findViewById(R.id.ptUsername);
        Password = (EditText)findViewById(R.id.etPassword);
        Email = (EditText)findViewById(R.id.etEmail);
        Birthday = (EditText)findViewById(R.id.etBirthday);
        Address = (EditText)findViewById(R.id.etAddress);

        Gender = (RadioGroup)findViewById(R.id.radioGroupGender);
        Male = (RadioButton)findViewById(R.id.rbMale);
        Female = (RadioButton)findViewById(R.id.rbFemale);
        Diverse = (RadioButton)findViewById(R.id.rbDiverse);


        SignUp = (Button)findViewById(R.id.btnSignUp);

        Gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                changeGenderText(group, checkedId);
            }
        });


        AllreadySignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterPage.this, LogInPage.class));
            }
        });

        SignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendPostRequest();
                startActivity(new Intent(RegisterPage.this, StartPage.class));
            }
        });
    }

    private void changeGenderText(RadioGroup group, int checkedId) {
        int checkedRadioId = group.getCheckedRadioButtonId();

        if(checkedRadioId== R.id.rbMale) {
            Toast.makeText(this,"You choose: Male",Toast.LENGTH_SHORT).show();
            cGender = "male";
        } else if(checkedRadioId== R.id.rbFemale ) {
            Toast.makeText(this,"You choose: Female",Toast.LENGTH_SHORT).show();
            cGender = "female";
        } else if(checkedRadioId== R.id.rbDiverse) {
            Toast.makeText(this,"You choose: Diverse",Toast.LENGTH_SHORT).show();
            cGender = "diverse";
        }
    }


    public void sendPostRequest() {
        String postfirstName = FirstName.getText().toString();
        String postlastName = LastName.getText().toString();
        String postusername = Username.getText().toString();
        String postpassword = Password.getText().toString();
        String postemail = Email.getText().toString();
        String postbirthday = Birthday.getText().toString();
        String postaddress = Address.getText().toString();
        String postgender = cGender;


        new PostMethode(this, new AppUser(postfirstName,postlastName,postusername,
                postpassword,postemail,postbirthday,postaddress,postgender)).execute();
    }


}
