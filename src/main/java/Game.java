import java.util.Arrays;

public class Game {

    private boolean gameEnded;
    private String player;
    private int stepCounter = 0;
    private final String[][] field = {{"", "", ""}, {"", "", ""}, {"", "", ""}};


    public String[][] getField() {
        return field;
    }

    public int getStepCounter() {
        return stepCounter;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public String getPlayer() {
        return player;
    }

    public void step(int x, int y) {
        String player = selectPlayer();

        checkIfFilled(field[x][y]);

        field[x][y] = player;

        calculateEndOfTheGame();
        if (gameEnded) {
            throw new GameAlreadyEndedException();
        }

        stepCounter++;
    }

    private void calculateEndOfTheGame() {
        // Diagonal verification
        if (field[0][0].equals(field[1][1]) && field[1][1].equals(field[2][2]) && !field[1][1].equals("")) { // X O -
            gameEnded = true;                                                                                // O X -
            System.out.println("Win by Diagonal by player " + player);                                       // - - X
        }
        if (field[2][0].equals(field[1][1]) && field[0][2].equals(field[1][1]) && !field[1][1].equals("")) { // - - X
            gameEnded = true;                                                                                // O X O
            System.out.println("Win by Diagonal by player " + player);                                       // X - -
        }

        // Line and Column verification
        for (int i = 0; i < field.length; i++) {
            if ((field[i][0].equals(field[i][1]) && field[i][1].equals(field[i][2])) && !field[i][2].equals("")) { // X O -
                gameEnded = true;                                                                                  // X - -
                System.out.println("Win by column by player " + player);                                           // X O -
            }
            if ((field[0][i].equals(field[1][i]) && field[1][i].equals(field[2][i])) && !field[2][i].equals("")) { // X X X
                gameEnded = true;                                                                                  // O - -
                System.out.println("Win by row by player " + player);                                              // O - -
            }
        }
    }

    private String selectPlayer() {
        if (stepCounter % 2 == 0) {
            player = "X";
        } else {
            player = "O";
        }
        return player;
    }

    private void checkIfFilled(String s) {
        if (!s.equals("")) {
            throw new AlreadyFilledFieldException();
        }
    }

    public String getFieldString() {
        String result = "";
        for (int i = 0; i < field.length; i++) {
            String[] arr = field[i];
            result += Arrays.toString(arr) + (i == field.length - 1 ? "" : "\n"); // Do not add new line ('\n') at the end
        }
        return result;
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameEnded=" + gameEnded +
                ", player='" + player + '\'' +
                ", stepCounter=" + stepCounter +
                ", field=" + getFieldString() +
                '}';
    }
}
