package com.example.instagram;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignUp extends AppCompatActivity implements View.OnClickListener {

    private EditText signid,signpass,email;
    private Button signup,login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        signid=findViewById(R.id.signid);
        signpass=findViewById(R.id.signpass);
        email=findViewById(R.id.email);
        signup=findViewById(R.id.signup);
        login=findViewById(R.id.login);

        signup.setOnClickListener(this);
        login.setOnClickListener(this);

        signpass.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if(keyCode==KeyEvent.KEYCODE_ENTER&&event.getAction()==KeyEvent.ACTION_DOWN){
                    onClick(signup);
                }
                return false;
            }
        });

        if(ParseUser.getCurrentUser()!=null)
        {
            transition();
        }
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){

            case R.id.signup:

                if(email.getText().toString().equals("")||signid.getText().toString().equals("")||signpass.getText().toString().equals("")){
                    Toast.makeText(SignUp.this, "Email,UserId and password are necessary", Toast.LENGTH_SHORT).show();
                }
                else {

                    final ProgressDialog progressDialog=new ProgressDialog(SignUp.this);
                    progressDialog.setMessage("Signing up");
                    progressDialog.show();

                    final ParseUser user= new ParseUser();
                    user.setEmail(email.getText().toString());
                    user.setUsername(signid.getText().toString());
                    user.setPassword(signpass.getText().toString());

                    user.signUpInBackground(new SignUpCallback() {
                        @Override
                        public void done(ParseException e) {
                            if (e == null) {
                                Toast.makeText(SignUp.this, signid.getText() + "is signed up", Toast.LENGTH_SHORT).show();
                                transition();
                            }
                            else{
                                Toast.makeText(SignUp.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                    progressDialog.dismiss();
                }
                break;

            case R.id.login:
                Intent intent=new Intent(SignUp.this,Login.class);
                startActivity(intent);
                break;
        }

    }

    public void root(View v) {

        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void transition(){

        Intent intent=new Intent(SignUp.this,Activity.class);
        startActivity(intent);
    }
}
