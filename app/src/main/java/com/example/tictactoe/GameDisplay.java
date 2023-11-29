package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameDisplay extends AppCompatActivity {

    private TicTacToeBoard ticTacToeBoard;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Game display ","on create is called ");
        setContentView(R.layout.activity_game_display);
        Log.d("Set content View is called", "set cantent view");

        Button playAgainBTN = findViewById(R.id.play_Again);
        Button homeBTN = findViewById(R.id.home_btn);
        TextView playerTurn = findViewById(R.id.player_Display);

        playAgainBTN.setVisibility(View.GONE);
        homeBTN.setVisibility(View.GONE);

        String[] playerName = getIntent().getStringArrayExtra("Players_Name");

        if(playerName != null){
            playerTurn.setText((playerName[0]+"'s Turn"));
        }

        ticTacToeBoard =findViewById(R.id.ticTacToeBoard);

        ticTacToeBoard.setUpGame(playAgainBTN, homeBTN, playerTurn, playerName);
    }

    public void PlayAgainButton(View View){
//        Intent intent = new Intent(this,GameDisplay.class);
//        startActivity(intent);  // it was working and resetting game basically it was calling again.
        ticTacToeBoard.resetGame();
        ticTacToeBoard.invalidate();

    }

    public void BackToHomeButton(View view){

        Intent intent= new Intent(this,MainActivity.class);
        startActivity(intent);
    }
}