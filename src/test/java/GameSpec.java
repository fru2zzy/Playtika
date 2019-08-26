import org.junit.Before;
import org.junit.Test;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.*;

public class GameSpec {

    private Game game;

    @Before
    public void init() {
        game = new Game();
    }

    @Test
    public void mustGenerateTicTacToeField() {
        int expectedSize = 3;

        String[][] field = game.getField();

        assertEquals(field.length, expectedSize);
        for (int i = 0; i < field.length; i++) {
            int actual = field[i].length;
            assertEquals(expectedSize, actual);
        }
    }

    @Test
    public void mustGenerateAllFieldsEmptyStrings() {
        String[][] field = game.getField();

        for (int i = 0; i < field.length; i++) {
            String[] row = field[i];
            for (int j = 0; j < row.length; j++) {
                assertEquals("", field[i][j]);
            }
        }
    }

    @Test
    public void mustSetSymbolByCoords() {
        String expected = "X";

        game.step(1, 1);

        String[][] field = game.getField();
        String actual = field[1][1];
        assertEquals(expected, actual);
    }

    @Test
    public void mustChangePlayerAfterMove() {
        game.step(1, 1);
        game.step(1, 2);

        String[][] field = game.getField();

        assertEquals("X", field[1][1]);
        assertEquals("O", field[1][2]);
    }

    @Test(expected = AlreadyFilledFieldException.class)
    public void mustNotOverrideExistingSteps() {
        game.step(1, 1);
        game.step(1, 1);
    }

    @Test(expected = IllegalArgumentException.class)
    public void mustNotSetOutOfBoundsOfField() {
        game.step(5, 5);
    }


    @Test(expected = GameAlreadyEndedException.class)
    public void mustNotAbleToDoMoveAfterEndOfTheGameByRow() {
        game.step(0, 0); // X
        game.step(1, 0); // O
        game.step(0, 1); // X
        game.step(1, 1); // O
        game.step(0, 2); // X
        game.step(2, 2); // O
    }

    @Test(expected = GameAlreadyEndedException.class)
    public void mustNotAbleToDoMoveAfterEndOfTheGameByColumn() {
        game.step(0, 0); // X
        game.step(0, 1); // O
        game.step(1, 0); // X
        game.step(1, 1); // O
        game.step(2, 0); // X
        game.step(2, 2); // O
    }

    @Test(expected = GameAlreadyEndedException.class)
    public void mustNotAbleToDoMoveAfterEndOfTheGameByDiagonalFromZero() {
        game.step(0, 0); // X
        game.step(1, 0); // O
        game.step(1, 1); // X
        game.step(0, 1); // O
        game.step(2, 2); // X
        game.step(2, 1); // O
    }

    @Test(expected = GameAlreadyEndedException.class)
    public void mustNotAbleToDoMoveAfterEndOfTheGameByDiagonalFromEnd() {
        game.step(2, 0); // X
        game.step(0, 1); // O
        game.step(1, 1); // X
        game.step(2, 1); // O
        game.step(0, 2); // X
        game.step(1, 2); // O
    }

    @Test(expected = GameAlreadyEndedException.class)
    public void mustWinIfAllInRowFilledByOnePlayer() {
        game.step(0, 0); // X
        game.step(0, 1); // O
        game.step(1, 0); // X
        game.step(1, 1); // O
        game.step(2, 0); // X
    }

    @Test(expected = GameAlreadyEndedException.class)
    public void firstPlayerShouldWin() {
        int finishStep = 4;
        game.step(0, 0); // X
        game.step(0, 1); // O
        game.step(1, 0); // X
        game.step(1, 1); // O
        try {
            game.step(2, 0); // X
        } catch (GameAlreadyEndedException e) {
            assertTrue("Game should be ended after getting 'GameAlreadyEndedException'", game.isGameEnded());
            assertEquals("Game should be ended on " + finishStep + " step", game.getStepCounter(), finishStep);
            assertEquals("Payer 'X' should win the game", game.getPlayer(), "X");

            throw new GameAlreadyEndedException("");
        }
    }

    @Test(expected = GameAlreadyEndedException.class)
    public void secondPlayerShouldWin() {
        int finishStep = 5;
        game.step(1, 2); // X
        game.step(0, 0); // O
        game.step(1, 1); // X
        game.step(1, 0); // O
        game.step(2, 2); // X
        try {
            game.step(2, 0); // O
        } catch (GameAlreadyEndedException e) {
            assertTrue("Game should be ended after getting 'GameAlreadyEndedException'", game.isGameEnded());
            assertEquals("Game should be ended on " + finishStep + " step", game.getStepCounter(), finishStep);
            assertEquals("Payer 'O' should win the game", game.getPlayer(), "O");

            throw new GameAlreadyEndedException("");
        }
    }

    @Test
    public void correctValuesInitialization() {
        assertEquals("Step counter value should be initialized by 0", game.getStepCounter(), 0);
        assertNull("Player should not be initialized by any value", game.getPlayer());
        assertFalse("gameEnded value should be false", game.isGameEnded());
    }

    @Test
    public void fieldShouldBeFilledAfterStep() {
        game.step(0, 0);
        assertEquals("After step by 'X' player, field (0, 0) should be filled", game.getField()[0][0], "X");
        game.step(1, 1);
        assertEquals("After step by 'O' player, field (1, 1) should be filled", game.getField()[1][1], "O");
    }

    @Test
    public void gameToStringTest() {
        game.step(1, 2); // X
        game.step(0, 0); // O
        game.step(1, 1); // X
        game.step(1, 0); // O
        game.step(2, 2); // X

        String expectedString = "Game{gameEnded=false, player='X', stepCounter=5, field=" +
                "[O, , ]\n" +
                "[O, X, X]\n" +
                "[, , X]" + "}";
        assertEquals("toString method works incorrectly or modified", game.toString(), expectedString);
    }

    @Test
    public void printFieldTest() {
        game.step(0, 0);
        game.step(1, 1);

        String expectedString = "[X, , ]\n" + "[, O, ]\n" + "[, , ]";
        assertEquals("getFieldString() method works incorrectly or modified", game.getFieldString(), expectedString);
    }
}