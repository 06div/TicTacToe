package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Play_Setup extends AppCompatActivity {

    public EditText Player1;
    public EditText Player2;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_setup);

        Player1 = findViewById(R.id.etPlayer1);
        Player2 = findViewById(R.id.etPlayer2);

    }

    public void SubmitNameButton(View view){

        String p1 = Player1.getText().toString();
        String p2 = Player2.getText().toString();

        Intent intent = new Intent(this, GameDisplay.class);
        intent.putExtra("Players_Name",new String[] {p1,p2});
        startActivity(intent);
    }
}