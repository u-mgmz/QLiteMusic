package com.yildiz.qlitemusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class RegisterActivity extends AppCompatActivity {
    EditText etName, etSurname, etPhone, etEmail, etPassword, etRePassword, etUsername;
    ImageView ivProfile;
    Button btProfile, btRegisterComplete, btLoginBack;
    ArrayList<User> userlist;
    Uri URI = null;
    int regcheck = 0;
    private static final int PICK_FROM_GALLERY = 101;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loadData();

        etName = (EditText) findViewById(R.id.etName);
        etSurname = (EditText) findViewById(R.id.etSurname);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etEmail = (EditText) findViewById(R.id.etEmail);
        etUsername = (EditText) findViewById(R.id.etRUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);
        ivProfile = (ImageView) findViewById(R.id.ivProfile);
        btProfile = (Button) findViewById(R.id.btProfile);
        btLoginBack = (Button) findViewById(R.id.btLoginBack);
        btRegisterComplete = (Button) findViewById(R.id.btRegisterComplete);

        btProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });

        btRegisterComplete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendMail(etName.getText().toString(), etSurname.getText().toString(), etPhone.getText().toString(),
                        etEmail.getText().toString(), etUsername.getText().toString(), etPassword.getText().toString(),
                        etRePassword.getText().toString());
            }
        });

        btLoginBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(getIntent().getIntExtra("regcheck", 12) == 0){
                    if(regcheck == 0){
                        btLoginBack.setEnabled(false);
                    }
                    else if(regcheck == 1){
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
                else{
                    Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void sendMail(String name, String surname,String phone, String email, String username, String password, String repassword){
        for (User user: userlist) {
            if(user.getUsername().equals(username)){
                Toast.makeText(this, "This user already exists", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        if(name.equals("") || surname.equals("") || phone.equals("") || email.equals("") ||
                username.equals("") || password.equals("")){
            Toast.makeText(this, "Fill in the blank spaces", Toast.LENGTH_SHORT).show();
            return;
        }
        else if(!password.equals(repassword)){
            Toast.makeText(this, "Passwords aren't same check again.", Toast.LENGTH_SHORT).show();
            return;
        }
        try{
            User user = new User(name, surname, email, phone, username, password, URI);
            saveData(user);

            final Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
            emailIntent.setType("plain/text");
            emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{email});
            emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, name + " " + surname + " QLiteMusic User Informations");
            emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Phone: " + phone + "\nEmail: " + email + "\nUsername: "
                    + username + "\nPassword: " + password);
            if (URI != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, URI);
            }
            this.startActivity(Intent.createChooser(emailIntent, "Sending email..."));
            regcheck = 1;
            btLoginBack.setEnabled(true);
        }catch (Throwable t){
            Toast.makeText(this, "Request failed try again: "+ t.toString(), Toast.LENGTH_LONG).show();
        }
    }

    public void saveData(User user){
        SharedPreferences sp = getSharedPreferences("users", MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        Gson gson = new Gson();
        userlist.add(user);
        String json = gson.toJson(userlist);
        editor.putString("userlist", json);
        editor.apply();
    }

    private void loadData(){
        SharedPreferences sp = getSharedPreferences("users", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = sp.getString("userlist", null);
        Type type = new TypeToken<ArrayList<User>>() {}.getType();
        userlist = gson.fromJson(json, type);
        if(userlist == null){
            userlist = new ArrayList<User>();
        }
    }

    public void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.putExtra("return-data", true);
        startActivityForResult(Intent.createChooser(intent, "Complete action using"), PICK_FROM_GALLERY);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_FROM_GALLERY && resultCode == RESULT_OK) {
            URI = data.getData();
            ivProfile.setImageURI(URI);
        }
    }
}