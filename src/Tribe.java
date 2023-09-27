import java.util.ArrayList;
import java.util.Random;

public class Tribe {
    private String tribeName;
    private ArrayList<Player> tribePlayers = new ArrayList<>();
    private int boost;
    private String color;
    private ArrayList<String> colors = new ArrayList<>();
    private ArrayList<Integer> usedColors = new ArrayList<>();
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_ORANGE = "\033[38;5;208m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[37m";
    private static final String ANSI_BLACK = "\u001B[01m";

    public String tribeColors(){
        Random r = new Random();
        colors.add(ANSI_GREEN);
        colors.add(ANSI_ORANGE);
        colors.add(ANSI_RED);
        colors.add(ANSI_YELLOW);
        colors.add(ANSI_BLUE);
        colors.add(ANSI_PURPLE);
        colors.add(ANSI_CYAN);
        colors.add(ANSI_WHITE);
        int used = r.nextInt(colors.size());
        while(usedColors.contains(used)){
            used = r.nextInt(colors.size());
        }
        usedColors.add(used);
        return colors.get(used);
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int boostNStats(){
        return boost + tribeStats();
    }

    public void addPlayer(Player p){
        tribePlayers.add(p);
    }

    public String getTribeName() {
        return tribeName;
    }

    public void setTribeName(String tribeName) {
        this.tribeName = tribeName;
    }

    public ArrayList<Player> getTribePlayers() {
        return tribePlayers;
    }

    public void setTribePlayers(ArrayList<Player> tribePlayers) {
        this.tribePlayers = tribePlayers;
    }

    public int getBoost() {
        return boost;
    }

    public void setBoost(int add) {
        this.boost = getBoost() + add;
    }

    public int tribeStats(){
        int allStats=0;
        for (Player tribePlayer : tribePlayers) {
            allStats = allStats + tribePlayer.numStats();
        }
        return allStats;
    }

    public Tribe(String tribeName, ArrayList<Player> tribePlayers, int boost) {
        this.tribeName = tribeName;
        this.tribePlayers = tribePlayers;
        this.boost = boost;
//        this.color = tribeColors();
    }

    @Override
    public String toString() {
        return color + "" + tribeName + ANSI_RESET + ": " + tribePlayers.toString() + ", Rating: " +boostNStats();
    }

    public String tribeName(){
        return tribeName;
    }

    public Tribe(){

    }
}
