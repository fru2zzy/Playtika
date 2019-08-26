import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class PlayGame {
    public static void main(String[] args) throws IOException {
        Game game = new Game();
        game.setPlayer("X");
        int maxMovesCount = 5;
        System.out.println("To make a move, enter enter X, press Enter and then Y coordinate (in range 0-2), press Enter");

        for (int i = 0; i < maxMovesCount; i++) {
            game.selectPlayer();
            System.out.println("Player '" + game.getPlayer() + "', make your move");

            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            String xInput = reader.readLine();
            String yInput = reader.readLine();
            int x, y;
            try {
                x = Integer.parseInt(xInput);
                y = Integer.parseInt(yInput);
            } catch (Exception e) {
                throw new IllegalArgumentException("You've entered invalid coordinates\n" + e);
            }
            game.step(x, y);
            System.out.println(game.getFieldString());
        }
    }
}
