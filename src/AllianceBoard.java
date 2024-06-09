import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;


public class AllianceBoard {
    private int[][] allianceBoard;
    private ArrayList<Player> names;

    public ArrayList<Player> getNames() {
        return names;
    }

    public void setNames(ArrayList<Player> names) {
        this.names = names;
    }

    public static void main(String[] args) {
        AllianceBoard a = new AllianceBoard(16);
        System.out.println(a.toString(16));
    }

    public int getSpot(int i, int j){
        return allianceBoard[i][j];
    }

    public void setSpot(int i, int j, int val){
        allianceBoard[i][j] = val;
        allianceBoard[j][i] = val;
    }


    public int[][] getAllianceBoard() {
        return allianceBoard;
    }

    public void setAllianceBoard(int[][] allianceBoard) {
        this.allianceBoard = allianceBoard;
    }

    public AllianceBoard(int numPlayers) {
        Random r = new Random();
        allianceBoard =  new int[numPlayers][numPlayers];
        for(int i = 0;i<numPlayers;i++){
            for(int j = 0; j<numPlayers;j++){
                if(i==j){
                    allianceBoard[i][j] = 0;
                }
                else{
                    int num = r.nextInt(10)+1;
                    allianceBoard[i][j] = num;
                    allianceBoard[j][i] = num;
                }
            }
        }
    }
    public String toString(int numPlayers){
        StringBuilder b  = new StringBuilder();
        b.append("Alliance Grid\n");
        for(int i = 0;i<numPlayers;i++){
            String name = String.format("%22s",names.get(i) + ": ");
            b.append(name);
            for(int j = 0; j<numPlayers;j++) {
                String num = String.format("%3s",allianceBoard[i][j] + " ");
                b.append(num);
            }
            b.append("\n");
        }
        return b.toString();
    }


}
