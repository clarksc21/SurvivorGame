import java.util.ArrayList;
import java.util.Scanner;

public class Test {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_ORANGE = "\033[38;5;208m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BLACK = "\u001B[01m";
    public static void main(String[] args) {
        Game g = new Game();
        Scanner sc = new Scanner(System.in);
        ArrayList<Player> p = new ArrayList<>();
        g.setIdol(true);
        g.setAdvantages(true);
        g.setR();
        for (int i = 0; i < 7; i++) {
            System.out.println("Pick a player");
            Player pl = new Player();
            pl.setName(sc.nextLine());
            p.add(pl);
        }
        g.setPlayers(p);
        for (int i = 0; i < 10; i++) {
            g.survivorAuction();
        }
    }
}
