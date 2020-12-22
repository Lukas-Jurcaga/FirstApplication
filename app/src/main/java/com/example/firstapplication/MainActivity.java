package com.example.firstapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button[][] fields = new Button[3][3];
    private final String[] symbols = {"O", "X"};
    private final String firstSymbol = "O";
    private String currentPlayersTurn = firstSymbol;
    private int moves = 0;
    private int numOfFields = 0;
    private final int winnerColour = Color.RED;
    private final int defaultColour = Color.GRAY;


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
        resetFields();
    }

    public void fieldClicked(View v) {
        Button button = (Button) v;
        Button startGameButton = findViewById(R.id.startBTN);

        //Changes the fields text depending on who's turn it is and disables the button
        button.setEnabled(false);
        button.setText(currentPlayersTurn);
        moves++;

        //Switches the turn to the other player
        if (currentPlayersTurn.equals(symbols[0]))
            currentPlayersTurn = symbols[1];
        else
            currentPlayersTurn = symbols[0];

        //Checks if a player has won or drew
        String winner = checkWin();
        if (winner.equals("X")){
            startGameButton.setText("X Won! Click to start a new game.");
            startGameButton.setEnabled(true);
            disableFields();
        }else if (winner.equals("O")){
            startGameButton.setText("O Won! CLick to start a new game.");
            startGameButton.setEnabled(true);
            disableFields();
        }else if (moves == numOfFields){
            startGameButton.setText("Draw! Click to start a new game.");
            startGameButton.setEnabled(true);
            disableFields();
        }else
            startGameButton.setText("It is now " + currentPlayersTurn + "'s turn");
    }

    public void resetFields(){
        //Rests important move variables
        moves = 0;
        numOfFields = 0;
        currentPlayersTurn = firstSymbol;

        //Disables all the field buttons and resets their text
        for(Button[] row: fields){
            for(Button field: row){
                field.setEnabled(true);
                field.setText("");
                field.setTextColor(defaultColour);
                numOfFields++;
            }
        }
    }

    public String checkWin() {
        //Variables to check the if the noughts or crosses have a 3 in a row vertically
        int[] numOfXInColumn = {0, 0, 0};
        int[] numOfOInColumn = {0, 0, 0};
        //Variables to check if a nought or cross is in a diagonal
        int numOfXDiagonally = 0;
        int numOfODiagonally = 0;

        //Checks if the user won by looping through the rows and columns
        for (int i = 0; i < fields.length; i++) {
            //Variables to check if the noughts or crosses have a three in a row horizontally - rests each loop
            int numOfXInRow = 0;
            int numOfOInRow = 0;

            //Loops though each of the row's buttons and checks if their either X or O
            for (int j = 0; j < fields[i].length; j++) {
                if (fields[i][j].getText().toString().equals("O")) {
                    numOfOInRow++;
                    numOfOInColumn[j]++;
                } else if (fields[i][j].getText().toString().equals("X")) {
                    numOfXInRow++;
                    numOfXInColumn[j]++;
                }
            }

            //Checks if the row had a 3 in a row and returns the winner
            if (numOfOInRow == fields.length) {
                highlightWin(winnerColour, "row", i);
                return "O";
            } else if (numOfXInRow == fields.length){
                highlightWin(winnerColour, "row", i);
                return "X";
            }
        }

        //Checks if a column had a three in a rows and returns the winner
        for (int i = 0; i < numOfOInColumn.length; i++) {
            if (numOfOInColumn[i] == 3) {
                highlightWin(winnerColour, "col", i);
                return "O";
            } else if (numOfXInColumn[i] == 3) {
                highlightWin(winnerColour, "col", i);
                return "X";
            }
        }

        //Checks if there are three in a row diagonally from left to right
        for (int i = 0, j = 0; i < fields.length; i++, j++) {
            if (fields[i][j].getText().toString().equals("O"))
                numOfODiagonally++;
            else if (fields[i][j].getText().toString().equals("X"))
                numOfXDiagonally++;
        }
        if (numOfODiagonally == 3) {
            highlightWin(winnerColour, "diaLR", -1);
            return "O";
        }else if (numOfXDiagonally == 3) {
            highlightWin(winnerColour, "diaLR", -1);
            return "X";
        }

        numOfODiagonally = 0;
        numOfXDiagonally = 0;

        //Checks if there are three in a row diagonally from right to left
        for (int i = 0, j = fields.length - 1; i < fields.length; i++, j--) {
            if (fields[i][j].getText().toString().equals("O")) {
                numOfODiagonally++;
            } else if (fields[i][j].getText().toString().equals("X"))
                numOfXDiagonally++;
        }
        if (numOfODiagonally == 3){
            highlightWin(winnerColour, "diaRL", -1);
            return "O";
        }else if(numOfXDiagonally == 3) {
            highlightWin(winnerColour, "diaRL", -1);
            return "X";
        }


        //Returns nothing if no one won
        return "";
    }

    //Disables all field buttons
    public void disableFields(){
        for(Button[] row: fields){
            for(Button button: row)
                button.setEnabled(false);
        }
    }

    public void highlightWin(int colour, String direction, int p){
        switch (direction) {
            case "col":
                for (Button[] row : fields)
                    row[p].setTextColor(colour);
                break;
            case "row":
                for (Button button : fields[p])
                    button.setTextColor(colour);
                break;
            case "diaLR":
                for (int i = 0, j = 0; i < fields.length; i++, j++)
                    fields[i][j].setTextColor(colour);
                break;
            case "diaRL":
                for (int i = 0, j = fields.length - 1; i < fields.length; i++, j--)
                    fields[i][j].setTextColor(colour);
                break;
            default:
                System.out.println("Invalid direction!");
                break;
        }
    }

}