import java.util.Arrays;
import java.util.Random;


public class Alliance {
    private int[][] allianceBoard;
    public static void main(String[] args) {
        Alliance a = new Alliance(16);
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

    public Alliance(int numPlayers) {
        Random r = new Random();
        allianceBoard =  new int[numPlayers][numPlayers];
        for(int i = 0;i<numPlayers;i++){
            for(int j = 0; j<numPlayers;j++){
                if(i==j){
                    allianceBoard[i][j] = 0;
                }
                else{
                    int num = r.nextInt(5)+1;
                    allianceBoard[i][j] = num;
                    allianceBoard[j][i] = num;
                }
            }
        }
    }
    public String toString(int numPlayers){
        StringBuilder b  = new StringBuilder();
        for(int i = 0;i<numPlayers;i++){
            for(int j = 0; j<numPlayers;j++) {
                b.append(allianceBoard[i][j] + " ");
            }
            b.append("\n");
        }
        return b.toString();
    }


}
