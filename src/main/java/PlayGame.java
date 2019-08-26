import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayGame {
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        game.setPlayer("X"); // Replace null value
        int maxMovesCount = 9;
        System.out.println("To make a move, enter enter X, press Enter and then Y coordinate (in range 1-3), press Enter");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        for (int i = 0; i < maxMovesCount; i++) {
            game.selectPlayer();
            System.out.println("Player '" + game.getPlayer() + "', make your move");

            String xInput = reader.readLine();
            String yInput = reader.readLine();
            int x, y;
            try {
                y = Integer.parseInt(xInput) - 1;
                x = Integer.parseInt(yInput) - 1;
            } catch (Exception e) {
                throw new IllegalArgumentException("You've entered invalid coordinates\n" + e);
            }
            game.step(x, y);
            System.out.println(game.getFieldString());
        }
    }
}
