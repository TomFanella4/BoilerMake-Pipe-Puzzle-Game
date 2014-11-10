package edu.purdue.tom.tubefluid;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.Arrays;


public class MainPipeActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pipe);

        TableLayout tubeTable = new TableLayout(this);
        tubeTable.setLayoutParams(new TableLayout.LayoutParams());

        tubeTable.setPadding(1, 1, 1, 1);

        final String[] actions = new String[]{"|", "-", "_|", "|_", "|-", "-|"};
        String[][] choice = new String[6][6];
        int[] count = new int[6];

        int randStart = (int) (Math.random() * 2);
        if (randStart == 0) {
            choice[1][0] = actions[5];
            choice[2][0] = actions[0];
            choice[3][0] = actions[2];
            choice[5][4] = actions[0];
        } else {
            choice[1][0] = actions[2];
            choice[0][0] = actions[4];
            choice[0][1] = actions[1];
            choice[0][2] = actions[2];
            choice[5][4] = actions[5];
        }

        while (true) {
            String[][] choiceCopy = new String[choice.length][choice[0].length];
            for (int i = 0; i < choiceCopy.length; i++) {
                for (int j = 0; j < choiceCopy[i].length; j++) {
                    choiceCopy[i][j] = choice[i][j];
                }
            }
            int[] countCopy = Arrays.copyOf(count, count.length);
            for (int i = 0; i < choice.length; i++) {
                for (int j = 0; j < choice[i].length; j++) {
                    if (choice[i][j] == null) {
                        int randomGen = (int) (Math.random() * 6);
                        choiceCopy[i][j] = actions[randomGen];
                        countCopy[randomGen]++;
                    }
                }
            }
            if (countCopy[0] > 1 && countCopy[1] > 2 && countCopy[3] > 0 && countCopy[5] > 1) {
                choice = choiceCopy;
                break;
            }
        }

        final String[][] finalChoice = choice;
        Button[][] buttonGrid = new Button[6][6];

        for (int i = 0; i < 6; i++) {
            TableRow tr = new TableRow(this);
            for (int j = 0; j < 6; j++) {
                Button b = new Button(this);
                if (i == 1 && j == 0) {
                    b.setBackgroundColor(Color.GREEN);
                    b.setText(finalChoice[i][j]);
                }
                else if (i == 5 && j == 4) {
                    b.setBackgroundColor(Color.RED);
                    b.setText(finalChoice[i][j]);
                }
                else if (randStart == 0 && j == 0 && (i == 2 || i == 3))
                    b.setText(finalChoice[i][j]);
                else if (randStart == 1 && ((i == 1 && j == 0) || (i == 0 && (j == 0 || j == 1 || j == 2))))
                    b.setText(finalChoice[i][j]);
                else
                    b.setText("?");
                b.setTextSize(30.0f);
                buttonGrid[i][j] = b;
                tr.addView(b, 165, 185);
            }
            tubeTable.addView(tr);
        }

        final Button[][] finalButtonGrid = buttonGrid;
        final Button switchButton = (Button) findViewById(R.id.buttonToBeSwapped);
        final TextView prevButtonCoord = (TextView) findViewById(R.id.prevButtonCoord);

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 6; j++) {
                final int fi = i;
                final int fj = j;
                Button b = finalButtonGrid[i][j];
                b.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (((Button) v).getText().equals("?"))
                            ((Button) v).setText(finalChoice[fi][fj]);
                        else {
                            if (switchButton.getText().equals("")) {
                                ((Button) v).setTextColor(Color.YELLOW);
                                switchButton.setText(((Button) v).getText());
                                prevButtonCoord.setText(fi + "" + fj);
                            } else {
                                int prevCol = Integer.parseInt(prevButtonCoord.getText().toString().substring(0, 1));
                                int prevRow = Integer.parseInt(prevButtonCoord.getText().toString().substring(1));
                                finalButtonGrid[prevCol][prevRow].setText(((Button) v).getText());
                                ((Button) v).setText(switchButton.getText());
                                finalButtonGrid[prevCol][prevRow].setTextColor(Color.BLACK);
                                prevButtonCoord.setText("");
                                switchButton.setText("");
                            }
                        }
                    }
                });
            }
        }

        Button finish = (Button) findViewById(R.id.finished);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int row = 1;
                int col = 0;
                String start = finalButtonGrid[1][0].getText().toString();
                String end = finalButtonGrid[5][4].getText().toString();
                Button prevButton;
                Button currentButton;
                String prevCommand = "";
                String currentCommand = start;
                String nextCommand = "";
                while(true) {
                    if (currentButton.getText().toString().equals(actions[0])) {
                        if (row - 1 >= 0 && !finalButtonGrid[row - 1][col].equals(prevButton)) {
                            currentButton = finalButtonGrid[row - 1][col];
                            if ()
                            prevButton = finalButtonGrid[row][col];
                            currentButton = finalButtonGrid[row - 1][col];
                        }
                        else if (row + 1 <= 6 && !finalButtonGrid[row + 1][col].equals(prevButton)) {
                            prevButton = finalButtonGrid[row][col];
                            currentButton = finalButtonGrid[row + 1][col];
                        }
                        break;
                    }
                    else if (currentButton.getText().toString().equals(actions[1])) {
                        if (prevCommand.equals("")) {
                            prevCommand = currentCommand;
                            currentCommand = finalChoice[row][col++];
                        }
                        else {
                            if (finalChoice[row][col - 1].equals(prevCommand))
                                currentCommand = finalChoice[row][col++];
                            else
                                currentCommand = finalChoice[row][col--];
                            prevCommand = currentCommand;
                        }
                    }
                    else if (currentButton.getText().toString().equals(actions[2])) {
                        if (prevCommand.equals("")) {
                            prevCommand = currentCommand;
                            currentCommand = finalChoice[row][col];
                        }
                    }
                    else if (currentButton.getText().toString().equals(actions[3])) {

                    }
                    else if (currentButton.getText().toString().equals(actions[4])) {

                    }
                    else if (currentButton.getText().toString().equals(actions[5])) {
                        if (prevCommand.equals("")) {
                            prevCommand = currentCommand;
                            currentCommand = finalChoice[row++][col];
                        }
                    }
                }
                if (row == 5 && col == 4)

            }
        });

        RelativeLayout temp = (RelativeLayout) findViewById(R.id.rLay);
        temp.addView(tubeTable);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_pipe, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
