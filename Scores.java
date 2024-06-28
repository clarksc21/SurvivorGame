public class Scores {
    private int score;
    private Player player;
    private int placement;

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getPlacement() {
        return placement;
    }

    public void setPlacement(int placement) {
        this.placement = placement;
    }

    public Scores(int score, Player player, int placement) {
        this.score = score;
        this.player = player;
        this.placement = placement;
    }

    @Override
    public String toString() {
        return placement + ": " + player + " had " + score + " points.";
    }
}
