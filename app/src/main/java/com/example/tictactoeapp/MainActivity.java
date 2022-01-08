package com.example.tictactoeapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import javax.net.ssl.SSLEngineResult;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private TextView Jucator1Score, Jucator2Score, StatusJucator;
    private Button [] buttons = new Button[9];
    private Button ResetareJoc;

    private int Jucator1ScoreCount, Jucator2ScoreCount, rundaCount;
    boolean JucatorActiv;

    // j1 => 0
    // j2 => 1
    // gol => 2

    int [] gameState = {2,2,2,2,2,2,2,2};
    int [][] PozitieCastigatoare = {
            {0,1,2} , {3,4,5} , {6,7,8} ,//linii
            {0,3,6} , {1,4,7} , {2,5,8} , //coloane
            {0,4,8} , {2,4,6}  // diagonale
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Jucator1Score = (TextView) findViewById(R.id.PlayerOneScore);
        Jucator2Score = (TextView) findViewById(R.id.PlayerTwoScore);
        StatusJucator = (TextView) findViewById(R.id.PlayerStatus);

        ResetareJoc = (Button) findViewById(R.id.ResetGame);
        for(int i=0; i < buttons.length; i++){
            String buttonID = "btn_" + i;
            int resourceID = getResources().getIdentifier(buttonID, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceID);
            buttons[i].setOnClickListener(this);
        }
        rundaCount = 0;
        Jucator1ScoreCount = 0;
        Jucator2ScoreCount = 0;
        JucatorActiv = true;


    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals("")){
            return;
        }
        String buttonID = v.getResources().getResourceEntryName(v.getId());
        int gameStatePointer = Integer.parseInt(buttonID.substring(buttonID.length()-1, buttonID.length()));

        if (JucatorActiv){
            ((Button) v).setText("X");
            ((Button) v).setTextColor(Color.parseColor("#FFC34A"));
            gameState[gameStatePointer] = 1;
        }else {
            ((Button) v).setText("O");
            ((Button) v).setTextColor(Color.parseColor("#70FFEA"));
            gameState[gameStatePointer] = 1;
        }
        rundaCount++;

        if(checkWinner()){
            if(JucatorActiv){
                Jucator1ScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }else{
                Jucator2ScoreCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();

            }

        }else if(rundaCount == 9){
                playAgain();
                Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        }else {
            JucatorActiv = !JucatorActiv;
                 }
        if(Jucator1ScoreCount > Jucator2ScoreCount){
            StatusJucator.setText("Player One is Winning!");

        }else if(Jucator2ScoreCount > Jucator1ScoreCount){
            StatusJucator.setText("Player Two is Winning!");

        }else
            StatusJucator.setText("");
    }
       
    public boolean checkWinner(){
        boolean winnerResult = false;

        for(int [] winningPosition : PozitieCastigatoare)
        {
            if(gameState[winningPosition[0]] == gameState[winningPosition[1]] &&
                    gameState[winningPosition[1]] == gameState[winningPosition[2]] &&
                    gameState[winningPosition[0]] !=2) {
                winnerResult = true;
            }

        }
        return winnerResult;
    }
    public void updatePlayerScore(){
        Jucator1Score.setText(Integer.toString(Jucator1ScoreCount));
        Jucator2Score.setText(Integer.toString(Jucator2ScoreCount));

    }

    public void playAgain(){
        rundaCount = 0;
        JucatorActiv = true;
        for(int i=0; i < buttons.length; i++)
        {
            gameState[i] = 2;
            buttons[i].setText("");
        }
    }
}
