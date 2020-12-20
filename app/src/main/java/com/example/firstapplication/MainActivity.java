package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button[][] fields = new Button[3][3];
    private final String[] symbols = {"O", "X"};
    private final String firstSymbol = "O";
    private String currentPlayersTurn = firstSymbol;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fields = new Button[][]{{findViewById(R.id.buttonX1Y1), findViewById(R.id.buttonX2Y1), findViewById(R.id.buttonX3Y1)},
                {findViewById(R.id.buttonX1Y2), findViewById(R.id.buttonX2Y2), findViewById(R.id.buttonX3Y2)},
                {findViewById(R.id.buttonX1Y3), findViewById(R.id.buttonX2Y3), findViewById(R.id.buttonX3Y3)}};
    }

    public void startGame(View v){
        Button button = (Button) v;
        button.setEnabled(false);
        button.setTextSize(12);
        button.setText("Game Started! First one to go is " + firstSymbol);

        for(Button[] row: fields){
            for(Button field: row)
                field.setEnabled(true);
        }

    }

    public void fieldClicked(View v){
        Button button = (Button) v;
        Button startGameButton = findViewById(R.id.startBTN);
        button.setEnabled(false);
        button.setText(currentPlayersTurn);

        if(currentPlayersTurn.equals(symbols[0]))
            currentPlayersTurn = symbols[1];
        else
            currentPlayersTurn = symbols[0];

        startGameButton.setText("It is now " + currentPlayersTurn + "'s turn");

    }
}