package com.yildiz.qlitemusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private EditText etUsername, etPassword;
    private Button btLogin;
    private TextView tvRemain;
    private Button btRegister;
    int regcheck = 1;
    private int remain_count = 3;
    ArrayList<User> userlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadData();

        etUsername = (EditText) findViewById(R.id.etUsername);
        etPassword = (EditText) findViewById(R.id.etPassword);
        btLogin = (Button) findViewById(R.id.btLogin);
        btRegister = (Button) findViewById(R.id.btRegister);
        tvRemain = (TextView) findViewById(R.id.tvRemain);

        tvRemain.setText("Remaining attempts left: " + String.valueOf(remain_count));

        btRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                control(etUsername.getText().toString(), etPassword.getText().toString());
            }
        });
    }

    private void control(String username, String password){
        int fail = 1;
        for (User user: userlist) {
            if(user.getUsername().equals(username) && user.getPassword().equals(password)){
                remain_count = 4;
                fail = 0;
                Toast.makeText(this, "Login successful.", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                intent.putExtra("username", user.getName() + " " + user.getSurname());
                startActivity(intent);
            }
        }
        remain_count--;
        tvRemain.setText("Remaining attempts left: " + String.valueOf(remain_count));
        if(remain_count == 0){
            btLogin.setEnabled(false);
            Toast.makeText(this, "You mistyped three times no more entries allowed.", Toast.LENGTH_SHORT).show();
            regcheck = 0;
            Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
            intent.putExtra("regcheck", regcheck);
            startActivity(intent);
            finish();
        }
        else if(fail != 0){
            Toast.makeText(this, "Wrong username or password.", Toast.LENGTH_SHORT).show();
        }
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
}