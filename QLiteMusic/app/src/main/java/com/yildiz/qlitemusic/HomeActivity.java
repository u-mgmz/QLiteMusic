package com.yildiz.qlitemusic;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    Button btAllSongs, btPlaylists;
    TextView tvWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btAllSongs = (Button) findViewById(R.id.btAllSongs);
        btPlaylists = (Button) findViewById(R.id.btPlaylists);
        tvWelcome = (TextView) findViewById(R.id.tvWelcome);
        tvWelcome.setText("Welcome " + getIntent().getStringExtra("username"));
        btAllSongs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, PlayerActivity.class);
                startActivity(intent);
            }
        });

        btPlaylists.setEnabled(false);
    }
}