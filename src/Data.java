import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;


public class Data {
    private static HashMap<String,Integer> soleSurvivors = new HashMap<>();
    private static HashMap<String,String> seasonWinners = new HashMap<>();

    public static void main(String[] args) throws IOException {
        System.out.println("How many Sims?");
        Scanner sc = new Scanner(System.in);
        int simCount = sc.nextInt();
        FileInputStream seasonNames = new FileInputStream("seasonName.txt");
        Scanner scan = new Scanner(seasonNames);
        ArrayList<String> Snames = new ArrayList<>();
        while(scan.hasNextLine()){
            Snames.add(scan.nextLine());
        }
        Random r = new Random();
        for(int i = 0;i<simCount;i++) {
            Game a = new Game();
            String title = Snames.get(r.nextInt(Snames.size()));
            a.setSeasonName(title);
            Snames.remove(title);
            a.setNumPlayers(18);
            a.setNumTribes(3);
            a.setTwoOrThree(true);
            a.setTribeSwap(true);
            a.setSplit(false);
            a.setEI(false);
            a.setCYCA(true);
            a.setExtraVote(true);
            a.setIB(true);
            a.setLA(true);
            a.setVB(true);
            a.setSAV(true);
            a.setIdol(true);
            a.setEOE(true);
            a.setRI(false);
            a.setAuction(true);
            a.Run(a);
            seasonWinners.put(a.getSeasonName(),a.getSoleSurvivor().getName());
            soleSurvivors.put(a.getSoleSurvivor().getName(), soleSurvivors.getOrDefault(a.getSoleSurvivor().getName(),0)+1);
            a.clearJury();
            a.resetDay();
            a.clearPlayers();
            a.clearSoleSurvivor();
            a.clearSecondPlace();
            a.clearThirdPlace();
            a.clearEliminated();
            a.clearTotals();
            a.clearPlayerStorage();
            a.clearTribes();
            a.resetRound();
        }
        for(String name: soleSurvivors.keySet()){
            System.out.println(name + ": " + soleSurvivors.get(name));
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        for(String sname : seasonWinners.keySet()){
            System.out.println(sname + ": " + seasonWinners.get(sname));
        }
        /*String path = "C:\\Users\\CLARKSC21\\OneDrive - Grove City College\\TEST.xlsx";
        FileInputStream fis = new FileInputStream(path);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        System.setProperty("log4j.configurationFile","./path_to_the_log4j2_config_file/log4j2.xml");
        XSSFSheet sheet = workbook.getSheet("Sheet1");
        Row row = sheet.getRow(0);
        System.out.println(row.getCell(0));*/
    }
}
