import java.util.ArrayList;

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
        ArrayList<String> colors = new ArrayList();
        colors.add(ANSI_GREEN);
        colors.add(ANSI_ORANGE);
        colors.add(ANSI_RED);
        colors.add(ANSI_YELLOW);
        colors.add(ANSI_BLUE);
        colors.add(ANSI_PURPLE);
        colors.add(ANSI_CYAN);
        colors.add(ANSI_WHITE);
        colors.add(ANSI_BLACK);
        for (String color : colors) {
            System.out.println(color + "COLOR" + ANSI_RESET);
        }
        System.out.println(colors.get(3) + "SWAG" + ANSI_RESET);
        System.out.println(colors.get(5)+ "BEANS" + ANSI_RESET);
    }
}
