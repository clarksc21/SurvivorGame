import java.io.*;
import java.util.*;

public class Game  {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_MAROON = "\u001B[38;5;52m";
    private static final String ANSI_ORANGE = "\033[38;5;208m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_LIME = "\u001B[38;5;10m";
    private static final String ANSI_YELLOW = "\u001B[38;5;11m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_PURPLE = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_WHITE = "\u001B[38;5;15m";
    private static final String ANSI_PINK = "\u001B[38;5;211m";
    private static final String ANSI_GOLD = "\u001B[33m";
    private static final String ANSI_GREY = "\u001B[38;5;251m";
    private static final String ANSI_BLACK = "\u001B[38;5;0m";
    private static ArrayList<String> colors = new ArrayList<>();
    private static int numPlayers;
    private static boolean FR;
    private boolean sim = false;
    private boolean quitB = false;
    private boolean voteSwapActive = false;
    private boolean medB = false;
    private boolean rreturned = false;
    private static int swaptype =0;
    private boolean voteSwap = false;
    private static boolean Auction = false;
    private static boolean spite = false;
    private static int spiteCnt = 0;
    private static Player spiteTarget = null;
    private static Player spiteUser = null;
    private static boolean spiteActive = false;
    private static int spiteCD = 0;
    private Player immunityWinner = new Player();
    private static boolean tribeSwap = false;
    private static boolean AuctExile = false;
    private static boolean FFCoin = false;
    private static boolean FFCoinUsed = false;
    private boolean automatic = false;
    private static boolean Journey = false;
    private static int round = 0;
    private int legacyIdol = 1;
    private static ArrayList<Alliance> alliances = new ArrayList<>();
    private boolean extraVote = false;
    private boolean SAV = false;
    private boolean SwP = false;
    private boolean VB = false;
    private static boolean ETM = false;
    private boolean LA = false;
    private static boolean Idol = false;
    private int EdgeReturnCnt = 0;
    private boolean changes = false;
    private boolean CYCA = false;
    private boolean IB = false;
    private static boolean advantages = true;
    private ArrayList<String> confessionalSays = new ArrayList<>();
    private static ArrayList<Player> redemptionPlayers = new ArrayList<>();
    private static AllianceBoard allianceboard;
    private static boolean EOE = false;
    private Player swap1 = new Player();
    private Player swap2 = new Player();
    private Player votedOutLast = null;
    private ArrayList<Player> idolPlayers = new ArrayList<>();
    private Player championUser = new Player();
    private boolean championAdv = false;
    private boolean legacyUsable = false;
    private boolean merged = false;
    private static boolean EI = false;
    private static boolean split = false;
    private int exileIdol = 1;
    private Player secretActwinner = new Player();
    private Player exiled = new Player();
    private Player legacy = null;
    private boolean FRUsable = false;
    private Player hasExileIdol = new Player();
    private static boolean RI = false;
    private HashMap<Player, String> advName = new HashMap<>();
    public static boolean twoOrThree;
    private static ArrayList<Scores> totals = new ArrayList<>();
    private String seasonName = "";
    private static int numTribes;
    private static ArrayList<Tribe> tribes = new ArrayList<>();
    private static int day = 0;
    private static ArrayList<Player> players = new ArrayList<>();
    private static ArrayList<Player> playerStorage = new ArrayList<>();
    private static Random r = new Random();
    private static Tribe losingTribe;
    private static HashMap<Integer, Player> votedOut = new HashMap<>();
    private static ArrayList<Player> eliminated = new ArrayList<>();
    private static ArrayList<Player> jury = new ArrayList<>();
    private int juryNum = 0;
    private int numImmune = 0;
    private int lostTribe = 0;
    private HashMap<Integer, String> indChallenges = new HashMap<>();
    private ArrayList<String> votingConfessionals = new ArrayList<>();
    private HashMap<String, String> indChalDescriptions = new HashMap<>();
    private HashMap<Integer, String> teamChallenges = new HashMap<>();
    private HashMap<String, String> teamChalDescriptions = new HashMap<>();
    private HashMap<String, String> indChalTypes = new HashMap<>();
    private HashMap<Player, Integer> ftvotes = new HashMap<>();
    private HashMap<Player, String> elimvotes = new HashMap<>();
    private Player soleSurvivor = new Player();
    private boolean idolBlock = false;
    private ArrayList<String> alliancenames = new ArrayList<>();
    private Player secondPlace = new Player();
    private ArrayList<Player> losesVote = new ArrayList<>();
    private Player thirdPlace = new Player();
    private ArrayList<String> tribeNames =new ArrayList<>();
    private HashMap<Player, Integer> immunityWins = new HashMap<>();
    private HashMap<Player, Integer> rewardWins = new HashMap<>();
    private HashMap<Player, Player> finalTribalVotes = new HashMap<>();
    private HashMap<Player, Player> tribalCouncilVotes = new HashMap<>();
    private Integer Zero = Integer.valueOf(0);


    public static void main(String[] args) throws IOException {
        Scanner throwAway = new Scanner(System.in);
        Game a = new Game();
        a.gameSetUp();
        while (players.size() > 11) {
            if (round != 0 && tribeSwap) {
                a.tribeSwap(swaptype);
            }
            if (RI && redemptionPlayers.size() >= 3) {
                a.redemptionIsland();
            }
            a.campLife();
            a.rewardChallenge();
            a.confessional();
            if (EI) {
                a.exileIsland();
            }
            a.campLife();
            if(Journey && round%2 == 1){
                a.journey();
            }
            int advChance = r.nextInt(5) + 1;
            if (advChance == 4 && advantages) {
                a.advantageFind(players.get(r.nextInt(players.size())),false);
            }
            a.idolAndAdvSummary();
            a.tribalImmunityChallenge();
            int fa = r.nextInt(10)+1;
            if(fa > 6){
                a.formAlliance(tribes.get(r.nextInt(tribes.size())));
            }else if(fa == 5){
                if(alliances.size()>1) {
                    a.allianceDissolve(alliances.get(r.nextInt(alliances.size())));
                }else if(alliances.size()==1){
                    a.allianceDissolve(alliances.get(0));
                }
            }
            a.allianceSummary();
            a.confessional();
            int ImmuneChance = r.nextInt(5)+1;
            if (ImmuneChance == 4 && Idol) {
                a.idolFind(players.get(r.nextInt(players.size())), false);
            }
            if (!a.medevacOrQuit()) {
                a.tribalCouncil(losingTribe);
            }
            for (int i = 0; i < numTribes; i++) {
                System.out.println(tribes.get(i).toString());
            }
            if(a.sizeCheck()){
                a.dissolve();
            }
        }
        if(ETM){
            a.tribalCouncil(a.earnTheMerge());
        }
        a.merge();
        a.confessional();
        if (EOE) {
            a.edgeOfExtinction();
        }
        if (RI) {
            a.RedemptionReturn();
        }
        while (players.size() > 4) {
            if (RI && redemptionPlayers.size() >= 3) {
                a.redemptionIsland();
            }
            a.campLife();
            if(players.size()==7 && Auction){
                a.survivorAuction();
                if(AuctExile){
                    a.exileIsland();
                }
            }else {
                a.rewardChallenge();
            }
            a.confessional();
            if (EI) {
                a.exileIsland();
            }
            a.campLife();
            if(split && players.size()==10){
                int ImmuneChance = r.nextInt(5)+1;
                if (ImmuneChance == 4 && players.size() > 4 && Idol) {
                    a.idolFind(players.get(r.nextInt(players.size())), false);
                }
                int advChance = r.nextInt(5) + 1;
                if (advChance == 4 && players.size() > 4 && advantages) {
                    a.advantageFind(players.get(r.nextInt(players.size())),false);
                }
                a.idolAndAdvSummary();
                a.splitImmunityChallenge();
                int fa = r.nextInt(10)+1;
                if(fa > 6){
                    a.formAlliance(losingTribe);
                }else if(fa == 5){
                    if(alliances.size()>1) {
                        a.allianceDissolve(alliances.get(r.nextInt(alliances.size())));
                    }else if(alliances.size()==1){
                        a.allianceDissolve(alliances.get(0));
                    }
                }
                a.allianceSummary();
                a.confessional();
            }
            else {
                int advChance = r.nextInt(5) + 1;
                if (advChance == 4 && players.size() > 4 && advantages) {
                    a.advantageFind(players.get(r.nextInt(players.size())),false);
                }
                a.idolAndAdvSummary();
                a.individualImmunityChallenge();
                int fa = r.nextInt(10)+1;
                if(fa > 6){
                    a.formAlliance(losingTribe);
                }else if(fa == 5){
                    if(alliances.size()>1) {
                        a.allianceDissolve(alliances.get(r.nextInt(alliances.size())));
                    }else if(alliances.size()==1){
                        a.allianceDissolve(alliances.get(0));
                    }
                }
                a.allianceSummary();
                a.confessional();
                int ImmuneChance = r.nextInt(5)+1;
                if (ImmuneChance == 4 && players.size() > 4 && Idol) {
                    a.idolFind(players.get(r.nextInt(players.size())), false);
                }
                a.tribalCouncil(losingTribe);
            }
            System.out.println(tribes.get(0).toString());
            if (RI && players.size() == 4) {
                a.RedemptionReturn();
                break;
            }
        }
        if (RI) {
            a.idolAndAdvSummary();
            a.individualImmunityChallenge();
            a.allianceSummary();
            a.confessional();
            a.tribalCouncil(losingTribe);
            System.out.println(tribes.get(0).toString());
        }
        if (EOE) {
            a.edgeOfExtinction();
            a.idolAndAdvSummary();
            a.individualImmunityChallenge();
            a.allianceSummary();
            a.confessional();
            a.tribalCouncil(losingTribe);
            System.out.println(tribes.get(0).toString());
        }
        if (twoOrThree) {
            while(players.size()>2) {
                a.idolAndAdvSummary();
                a.individualImmunityChallenge();
                a.allianceSummary();
                a.confessional();
                a.tribalCouncil(losingTribe);
                System.out.println(tribes.get(0).toString());
            }
            System.out.println("Type 1 and Press Enter To See The Final Results");
            throwAway.next();
            System.out.println();
            a.juryfix();
            a.finalTribal();
        } else {
            a.idolAndAdvSummary();
            a.individualImmunityChallenge();
            a.allianceSummary();
            a.confessional();
            a.fireMakingChallenge(tribes.get(0));
            System.out.println(tribes.get(0).toString());
            System.out.println("Type 1 and Press Enter To See The Final Results");
            throwAway.next();
            a.juryfix();
            a.final3tribal();
        }
        System.out.println("Type 1 and Press Enter To See The Statistics");
        throwAway.next();
        a.finalTribalVotes();
        a.fanFavorite();
        a.placements();
        a.immunityWins();
        a.rewardWins();
        a.challengeWins();
        a.totals();
        a.advantagesFound();
        a.idolsFound();
        a.playerAttributes();
        a.votability();
        a.votesAgainst();
        System.out.println(allianceboard.toString(numPlayers));
    }

    public Player getSoleSurvivor() {
        return soleSurvivor;
    }

    public void setAuction(boolean auction) {
        Auction = auction;
    }

    public void Run(Game a) throws IOException {
        sim = true;
        a.gameSetUp();
        while (players.size() > 11) {
            if (round != 0 && tribeSwap) {
                a.tribeSwap(swaptype);
            }
            if (RI && redemptionPlayers.size() >= 3) {
                a.redemptionIsland();
            }
            a.campLife();
            a.rewardChallenge();
            a.confessional();
            if (EI) {
                a.exileIsland();
            }
            a.campLife();
            if(Journey && round%2 == 1){
                a.journey();
            }
            int advChance = r.nextInt(5) + 1;
            if (advChance == 4 && advantages) {
                a.advantageFind(players.get(r.nextInt(players.size())),false);
            }
            a.idolAndAdvSummary();
            a.allianceSummary();
            a.tribalImmunityChallenge();
            a.confessional();
            int ImmuneChance = r.nextInt(5)+1;
            if (ImmuneChance == 4 && Idol) {
                a.idolFind(players.get(r.nextInt(players.size())), false);
            }
            if (!a.medevacOrQuit()) {
                a.tribalCouncil(losingTribe);
            }
            for (int i = 0; i < numTribes; i++) {
                System.out.println(tribes.get(i).toString());
            }
            if(a.sizeCheck()){
                a.dissolve();
            }
        }
        if(ETM){
            tribalCouncil(earnTheMerge());
        }
        a.merge();
        a.confessional();
        if (EOE) {
            a.edgeOfExtinction();
        }
        if (RI) {
            a.RedemptionReturn();
        }
        while (players.size() > 5) {
            if (RI && redemptionPlayers.size() >= 3) {
                a.redemptionIsland();
            }
            a.campLife();
            if(players.size()==7 && Auction){
                a.survivorAuction();
            }else {
                a.rewardChallenge();
            }
            a.confessional();
            if (EI) {
                a.exileIsland();
            }
            a.campLife();
            if(split && players.size()==10){
                int ImmuneChance = r.nextInt(5)+1;
                if (ImmuneChance == 4 && players.size() > 4 && Idol) {
                    a.idolFind(players.get(r.nextInt(players.size())), false);
                }
                int advChance = r.nextInt(5) + 1;
                if (advChance == 4 && players.size() > 4 && advantages) {
                    a.advantageFind(players.get(r.nextInt(players.size())),false);
                }
                a.idolAndAdvSummary();
                a.allianceSummary();
                a.splitImmunityChallenge();
                a.confessional();
            }
            else {
                int advChance = r.nextInt(5) + 1;
                if (advChance == 4 && players.size() > 4 && advantages) {
                    a.advantageFind(players.get(r.nextInt(players.size())),false);
                }
                a.idolAndAdvSummary();
                a.allianceSummary();
                a.individualImmunityChallenge();
                a.confessional();
                int ImmuneChance = r.nextInt(5)+1;
                if (ImmuneChance == 4 && players.size() > 4 && Idol) {
                    a.idolFind(players.get(r.nextInt(players.size())), false);
                }
                a.tribalCouncil(losingTribe);
            }
            System.out.println(tribes.get(0).toString());
            if (RI && players.size() == 4) {
                a.RedemptionReturn();
                break;
            }
        }
        if (RI) {
            a.idolAndAdvSummary();
            a.allianceSummary();
            a.individualImmunityChallenge();
            a.confessional();
            a.tribalCouncil(losingTribe);
            System.out.println(tribes.get(0).toString());
        }
        if (EOE) {
            a.edgeOfExtinction();
            a.idolAndAdvSummary();
            a.allianceSummary();
            a.individualImmunityChallenge();
            a.confessional();
            a.tribalCouncil(losingTribe);
            System.out.println(tribes.get(0).toString());
        }
        if (twoOrThree) {
            while(players.size()>2) {
                a.idolAndAdvSummary();
                a.allianceSummary();
                a.individualImmunityChallenge();
                a.confessional();
                a.tribalCouncil(losingTribe);
                System.out.println(tribes.get(0).toString());
            }
            System.out.println("Type 1 and Press Enter To See The Final Results");
            System.out.println();
            a.juryfix();
            a.finalTribal();
        } else {
            a.idolAndAdvSummary();
            a.allianceSummary();
            a.individualImmunityChallenge();
            a.confessional();
            a.fireMakingChallenge(tribes.get(0));
            System.out.println(tribes.get(0).toString());
            System.out.println("Type 1 and Press Enter To See The Final Results");
            a.juryfix();
            a.final3tribal();
        }
        System.out.println("Type 1 and Press Enter To See The Statistics");
        a.finalTribalVotes();
        a.placements();
        a.immunityWins();
        a.rewardWins();
        a.challengeWins();
        a.totals();
        a.fanFavorite();
        a.playerAttributes();
        a.votability();
        a.votesAgainst();
        System.out.println(allianceboard.toString(numPlayers));
            }

    public void final3tribal() {
        int votesForOne = 0;
        int votesForTwo = 0;
        int votesForThree = 0;
        System.out.println("Jeff Probst: Welcome to the Final Tribal Council!");
        System.out.println("Jeff Probst: You've made it the farthest anyone can go");
        System.out.println("Jeff Probst: Now you have to convince the jury that you voted out to vote for you to win the million dollars");
        System.out.println("Jeff Probst: Goodluck!");
        System.out.println();
        System.out.println("Jeff Probst: I'll Read The Votes");
        System.out.println();
        for (Player player : jury) {
            int finalVote = r.nextInt(tribes.get(0).getTribePlayers().get(0).getScore() + tribes.get(0).getTribePlayers().get(1).getScore() + tribes.get(0).getTribePlayers().get(2).getScore());
            if (finalVote < tribes.get(0).getTribePlayers().get(0).getScore()) {
                finalTribalVotes.put(player, tribes.get(0).getTribePlayers().get(0));
                ftvotes.put(tribes.get(0).getTribePlayers().get(0), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(0), Zero)+1));
                votesForOne++;
                //System.out.println("Jeff Probst: That's " + votesForOne + " votes for " + tribes.get(0).getTribePlayers().get(0));
            } else if (finalVote < tribes.get(0).getTribePlayers().get(1).getScore() + tribes.get(0).getTribePlayers().get(0).getScore()) {
                finalTribalVotes.put(player, tribes.get(0).getTribePlayers().get(1));
                ftvotes.put(tribes.get(0).getTribePlayers().get(1), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(1),Zero)+1));
                votesForTwo++;
                //System.out.println("Jeff Probst: That's " + votesForTwo + " votes for " + tribes.get(0).getTribePlayers().get(1));
            } else {
                finalTribalVotes.put(player, tribes.get(0).getTribePlayers().get(2));
                ftvotes.put(tribes.get(0).getTribePlayers().get(2), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(2),Zero)+1));
                votesForThree++;
                //System.out.println("Jeff Probst: That's " + votesForThree + " votes for " + tribes.get(0).getTribePlayers().get(2));
            }
        }
        ArrayList<Player> moving = new ArrayList<>();
        for(Player player : jury){
            moving.add(finalTribalVotes.get(player));
        }
        ArrayList<Player> ordered = new ArrayList<>(dramatize(moving));
        ArrayList<Player> previous = new ArrayList<>();
        previous.add(null);
        HashMap<Integer, Player> second = new HashMap<>();
        Integer max = Integer.valueOf(0);
        HashMap<Player, Integer> votes = new HashMap<>();
        Player voteName = new Player();
        for (Player player : ordered) {
            for (int i = 0; i < previous.size(); i++) {
                if (previous.get(previous.size() - 1) != null && previous.get(previous.size() - 1).equals(player)) {
                    previous.remove(player);
                }
            }
            voteName = player;
                System.out.println("One vote: " + voteName);
                votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                if (votes.get(voteName) > max) {
                    second.put(Integer.valueOf(max), votedOut.get(Integer.valueOf(max)));
                    max = votes.get(voteName);
                }
                if (votes.get(voteName) == max) {
                    votedOut.put(Integer.valueOf(max), voteName);
                }
                System.out.println("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
            previous.add(player);
        }
        if (votesForOne == votesForTwo && votesForTwo > votesForThree) {
            System.out.println("Jeff Probst: We have a tie!");
            System.out.println("Jeff Probst: " + tribes.get(0).getTribePlayers().get(2) + " you now have the final vote.");
            int lVote = r.nextInt(2);
            System.out.println("Jeff Probst: I'll read the last vote.");
            if (lVote == 0) {
                System.out.println("Jeff Probst: " + tribes.get(0).getTribePlayers().get(0));
                ftvotes.put(tribes.get(0).getTribePlayers().get(0), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(0),Zero)+1));
                votesForOne++;
            } else {
                System.out.println("Jeff Probst: " + tribes.get(0).getTribePlayers().get(1));
                ftvotes.put(tribes.get(0).getTribePlayers().get(1), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(1),Zero)+1));
                votesForTwo++;
            }
        } else if (votesForOne == votesForThree && votesForThree > votesForTwo) {
            System.out.println("Jeff Probst: We have a tie!");
            System.out.println("Jeff Probst: " + tribes.get(0).getTribePlayers().get(1) + " you now have the final vote.");
            int lVote = r.nextInt(2);
            System.out.println("Jeff Probst: I'll read the last vote.");
            if (lVote == 0) {
                System.out.println("Jeff Probst: " + tribes.get(0).getTribePlayers().get(0));
                votesForOne++;
                ftvotes.put(tribes.get(0).getTribePlayers().get(0), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(0),Zero)+1));
            } else {
                System.out.println("Jeff Probst: " + tribes.get(0).getTribePlayers().get(2));
                ftvotes.put(tribes.get(0).getTribePlayers().get(2), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(2),Zero)+1));
                votesForThree++;
            }
        } else if (votesForTwo == votesForThree && votesForThree > votesForOne) {
            System.out.println("Jeff Probst: We have a tie!");
            System.out.println("Jeff Probst: " + tribes.get(0).getTribePlayers().get(0) + " you now have the final vote.");
            int lVote = r.nextInt(2);
            System.out.println("Jeff Probst: I'll read the last vote.");
            if (lVote == 0) {
                System.out.println("Jeff Probst: " + tribes.get(0).getTribePlayers().get(2));
                ftvotes.put(tribes.get(0).getTribePlayers().get(2), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(2),Zero)+1));
                votesForThree++;
            } else {
                System.out.println("Jeff Probst: " + tribes.get(0).getTribePlayers().get(1));
                ftvotes.put(tribes.get(0).getTribePlayers().get(1), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(1),Zero)+1));
                votesForTwo++;
            }
        }
        if (votesForTwo == votesForThree && votesForThree < votesForOne) {
            System.out.println("Jeff Probst: The Winner of Survivor " + seasonName + ": " + tribes.get(0).getTribePlayers().get(0));
            soleSurvivor = tribes.get(0).getTribePlayers().get(0);
            if (tribes.get(0).getTribePlayers().get(1).getScore() > tribes.get(0).getTribePlayers().get(2).getScore()) {
                secondPlace = tribes.get(0).getTribePlayers().get(1);
                thirdPlace = tribes.get(0).getTribePlayers().get(2);
                System.out.println();
                System.out.println(votesForOne + "-" + votesForThree + "-" + votesForTwo);
            } else if (tribes.get(0).getTribePlayers().get(1).getScore() < tribes.get(0).getTribePlayers().get(2).getScore()) {
                secondPlace = tribes.get(0).getTribePlayers().get(2);
                thirdPlace = tribes.get(0).getTribePlayers().get(1);
                System.out.println();
                System.out.println(votesForOne + "-" + votesForThree + "-" + votesForTwo);
            } else {
                int i = r.nextInt(2);
                secondPlace = tribes.get(0).getTribePlayers().get(i + 1);
                thirdPlace = tribes.get(0).getTribePlayers().get(2 - i);
                System.out.println();
                System.out.println(votesForOne + "-" + votesForThree + "-" + votesForTwo);
            }
        } else if (votesForTwo == votesForOne && votesForOne < votesForThree) {
            System.out.println("Jeff Probst: The Winner of Survivor " + seasonName + ": " + tribes.get(0).getTribePlayers().get(2));
            soleSurvivor = tribes.get(0).getTribePlayers().get(2);
            if (tribes.get(0).getTribePlayers().get(1).getScore() > tribes.get(0).getTribePlayers().get(0).getScore()) {
                secondPlace = tribes.get(0).getTribePlayers().get(1);
                thirdPlace = tribes.get(0).getTribePlayers().get(0);
                System.out.println();
                System.out.println(votesForThree + "-" + votesForOne + "-" + votesForTwo);
            } else if (tribes.get(0).getTribePlayers().get(1).getScore() < tribes.get(0).getTribePlayers().get(0).getScore()) {
                secondPlace = tribes.get(0).getTribePlayers().get(0);
                thirdPlace = tribes.get(0).getTribePlayers().get(1);
                System.out.println();
                System.out.println(votesForThree + "-" + votesForOne + "-" + votesForTwo);
            } else {
                int i = r.nextInt(2);
                secondPlace = tribes.get(0).getTribePlayers().get(i);
                thirdPlace = tribes.get(0).getTribePlayers().get(1 - i);
                System.out.println();
                System.out.println(votesForThree + "-" + votesForOne + "-" + votesForTwo);
            }
        } else if (votesForOne == votesForThree && votesForThree < votesForTwo) {
            System.out.println("Jeff Probst: The Winner of Survivor " + seasonName + ": " + tribes.get(0).getTribePlayers().get(1));
            soleSurvivor = tribes.get(0).getTribePlayers().get(1);
            if (tribes.get(0).getTribePlayers().get(0).getScore() > tribes.get(0).getTribePlayers().get(2).getScore()) {
                secondPlace = tribes.get(0).getTribePlayers().get(0);
                thirdPlace = tribes.get(0).getTribePlayers().get(2);
                System.out.println();
                System.out.println(votesForTwo + "-" + votesForOne + "-" + votesForThree);
            } else if (tribes.get(0).getTribePlayers().get(0).getScore() < tribes.get(0).getTribePlayers().get(2).getScore()) {
                secondPlace = tribes.get(0).getTribePlayers().get(2);
                thirdPlace = tribes.get(0).getTribePlayers().get(0);
                System.out.println();
                System.out.println(votesForTwo + "-" + votesForOne + "-" + votesForThree);
            } else {
                int i = r.nextInt(2);
                secondPlace = tribes.get(0).getTribePlayers().get(i * 2);
                thirdPlace = tribes.get(0).getTribePlayers().get((1 - i) * 2);
                System.out.println();
                System.out.println(votesForTwo + "-" + votesForOne + "-" + votesForThree);
            }
        }
        if (votesForOne > votesForTwo && votesForTwo > votesForThree) {
            System.out.println("Jeff Probst: The Winner of Survivor " + seasonName + ": " + tribes.get(0).getTribePlayers().get(0));
            soleSurvivor = tribes.get(0).getTribePlayers().get(0);
            secondPlace = tribes.get(0).getTribePlayers().get(1);
            thirdPlace = tribes.get(0).getTribePlayers().get(2);
            System.out.println();
            System.out.println(votesForOne + "-" + votesForTwo + "-" + votesForThree);

        } else if (votesForOne < votesForTwo && votesForOne > votesForThree) {
            System.out.println("Jeff Probst: The Winner of Survivor " + seasonName + ": " + tribes.get(0).getTribePlayers().get(1));
            soleSurvivor = tribes.get(0).getTribePlayers().get(1);
            secondPlace = tribes.get(0).getTribePlayers().get(0);
            thirdPlace = tribes.get(0).getTribePlayers().get(2);
            System.out.println();
            System.out.println(votesForTwo + "-" + votesForOne + "-" + votesForThree);
        } else if (votesForOne < votesForTwo && votesForTwo < votesForThree) {
            System.out.println("Jeff Probst: The Winner of Survivor " + seasonName + ": " + tribes.get(0).getTribePlayers().get(2));
            soleSurvivor = tribes.get(0).getTribePlayers().get(2);
            secondPlace = tribes.get(0).getTribePlayers().get(1);
            thirdPlace = tribes.get(0).getTribePlayers().get(0);
            System.out.println();
            System.out.println(votesForThree + "-" + votesForTwo + "-" + votesForOne);
        } else if (votesForOne > votesForThree && votesForThree > votesForTwo) {
            System.out.println("Jeff Probst: The Winner of Survivor " + seasonName + ": " + tribes.get(0).getTribePlayers().get(0));
            soleSurvivor = tribes.get(0).getTribePlayers().get(0);
            secondPlace = tribes.get(0).getTribePlayers().get(2);
            thirdPlace = tribes.get(0).getTribePlayers().get(1);
            System.out.println();
            System.out.println(votesForOne + "-" + votesForThree + "-" + votesForTwo);
        } else if (votesForOne < votesForThree && votesForThree < votesForTwo) {
            System.out.println("Jeff Probst: The Winner of Survivor " + seasonName + ": " + tribes.get(0).getTribePlayers().get(1));
            soleSurvivor = tribes.get(0).getTribePlayers().get(1);
            secondPlace = tribes.get(0).getTribePlayers().get(2);
            thirdPlace = tribes.get(0).getTribePlayers().get(0);
            System.out.println();
            System.out.println(votesForTwo + "-" + votesForThree + "-" + votesForOne);
        } else if (votesForOne > votesForTwo && votesForOne < votesForThree) {
            System.out.println("Jeff Probst: The Winner of Survivor " + seasonName + ": " + tribes.get(0).getTribePlayers().get(2));
            soleSurvivor = tribes.get(0).getTribePlayers().get(2);
            secondPlace = tribes.get(0).getTribePlayers().get(0);
            thirdPlace = tribes.get(0).getTribePlayers().get(1);
            System.out.println();
            System.out.println(votesForThree + "-" + votesForOne + "-" + votesForTwo);
        }
        System.out.println();
        System.out.println("Finalist Scores: ");
        Scores d = new Scores(soleSurvivor.getScore(), soleSurvivor, 1);
        totals.add(d);
        Scores p = new Scores(secondPlace.getScore(), secondPlace, 2);
        totals.add(p);
        Scores e = new Scores(thirdPlace.getScore(), thirdPlace, 3);
        totals.add(e);
        System.out.println(tribes.get(0).getTribePlayers().get(0) + " had " + tribes.get(0).getTribePlayers().get(0).getScore() + " points!");
        System.out.println(tribes.get(0).getTribePlayers().get(1) + " had " + tribes.get(0).getTribePlayers().get(1).getScore() + " points!");
        System.out.println(tribes.get(0).getTribePlayers().get(2) + " had " + tribes.get(0).getTribePlayers().get(2).getScore() + " points!");
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void idolFind(Player p, boolean auction) {
        System.out.println(ANSI_CYAN + p + " found an idol!" + ANSI_RESET);
        if(!auction) {
            System.out.println();
            System.out.println("-------------------------");
            System.out.println();
        }
        p.setIdolCount();
        p.setIdolsInPossesion(1);
    }

    public void formAlliance(Tribe t) throws FileNotFoundException {
        String name = alliancenames.get(r.nextInt(alliancenames.size()));
        Alliance a = new Alliance(name);
        alliancenames.remove(name);
        alliances.add(a);
        int size = r.nextInt(5)+2;
        if(size>=t.getTribePlayers().size()){
            size = size -2;
        }
        Player potentialadd = t.getTribePlayers().get(r.nextInt(t.getTribePlayers().size()));
        for(int i = 0; i<size;i++){
            while(a.getMembers().contains(potentialadd)){
                potentialadd = t.getTribePlayers().get(r.nextInt(t.getTribePlayers().size()));
            }
            a.addMember(potentialadd);
            potentialadd.setScore(5);
        }
        System.out.println("A New Alliance has formed: The " + a.getName() + " Alliance." );
        System.out.println("It consists of: " + a.memberPrint());
        System.out.println("This Alliance originated on the " + t.getColor() + t.tribeName() + ANSI_RESET + " Tribe");
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void advantageFind(Player p, boolean auction) {
        System.out.println(p + " found an advantage.");
        boolean found = false;
        while(!found){
        int advType = r.nextInt(11);
        if (advType == 0 && extraVote) {
            System.out.println(ANSI_ORANGE + "It is an Extra Vote!" + ANSI_RESET);
            p.setAdvantages(1, p.getAdvantages(1) + 1);
            p.addAdvFound("Extra Vote");
            found=true;
        } else if (advType == 1 && VB) {
            System.out.println(ANSI_PURPLE + "It is a Vote Blocker!" + ANSI_RESET);
            p.setAdvantages(2, p.getAdvantages(2) + 1);
            p.addAdvFound("Vote Blocker");
            found=true;
        } else if (advType == 2 && SAV) {
            System.out.println(ANSI_YELLOW + "It is a Steal a Vote!" + ANSI_RESET);
            p.setAdvantages(3, p.getAdvantages(3) + 1);
            p.addAdvFound("Steal a Vote");
            found=true;
        } else if (advType == 3 && IB) {
            System.out.println(ANSI_BLUE + "It is an Idol Blocker!" + ANSI_RESET);
            p.setAdvantages(4, p.getAdvantages(4) + 1);
            p.addAdvFound("Idol Blocker");
            found=true;
        }else if(advType == 4 && CYCA){
            if(!championAdv) {
                System.out.println(ANSI_RED + "It is the Choose your Champion Advantage!" + ANSI_RESET);
                p.setAdvantages(5, p.getAdvantages(5) + 1);
                championAdv = true;
                p.addAdvFound("Choose your Champion");
                championUser = p;
            }else{
                System.out.println("You found an Useless Advantage, tough luck.");
            }
            found = true;
        }else if(advType == 5 && FR){
            System.out.println(ANSI_PINK + "It is the Force Rocks Advantage!" + ANSI_RESET);
            p.setAdvantages(7, p.getAdvantages(7)+1);
            p.addAdvFound("Force Rocks");
            found = true;
        }
        else if(advType==6 && voteSwap){
            System.out.println(ANSI_LIME + "It is the Vote Swap Advantage" + ANSI_RESET);
            p.setAdvantages(8,p.getAdvantages(8)+1);
            p.addAdvFound("Vote Swap");
            found = true;
        }else if(advType==7 && SwP){
            System.out.println(ANSI_MAROON + "It is the Safety without Power Advantage" + ANSI_RESET);
            p.setAdvantages(9,p.getAdvantages(9)+1);
            p.addAdvFound("Safety without Power");
            found = true;
        }else if(advType == 8 && FFCoin){
            System.out.println(ANSI_BLACK + "It is the 50/50 Coin" + ANSI_RESET);
            p.setAdvantages(10,p.getAdvantages(10)+1);
            p.addAdvFound("50/50 Coin");
            found = true;
        }else if(advType == 9 && spite){
            System.out.println(ANSI_BLUE + "It is the Spite Advantage" + ANSI_RESET);
            p.setAdvantages(11,p.getAdvantages(11)+1);
            p.addAdvFound("Spite Advantage");
            found = true;
        }
        else if (legacyIdol == 1 && LA) {
            System.out.println(ANSI_GREEN + "It is a Legacy Advantage" + ANSI_RESET);
            p.setAdvantages(6, 1);
            p.addAdvFound("Legacy Advantage");
            legacyIdol = 0;
            found=true;
        } else if(legacyIdol == 0 && LA){
            System.out.println("You found an empty note, someone must have found the Legacy Advantage");
            found=true;
        }else{
            int hi =0;
        }
        }
        if(!auction){
            System.out.println();
            System.out.println("-------------------------");
            System.out.println();
        }
    }

    public void confessional() throws FileNotFoundException {
        Player interviewee = players.get(r.nextInt(players.size()));
        FileInputStream conf = new FileInputStream("confessionals.txt");
        Scanner confsc = new Scanner(conf);
        while(confsc.hasNextLine()){
            confessionalSays.add(confsc.nextLine());
        }
        System.out.println(interviewee.getName() + ": " + confessionalSays.get(r.nextInt(confessionalSays.size())));
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();

    }

    public void confessional(Player p) throws FileNotFoundException {
        FileInputStream conf = new FileInputStream("confessionals.txt");
        Scanner confsc = new Scanner(conf);
        while(confsc.hasNextLine()){
            confessionalSays.add(confsc.nextLine());
        }
        System.out.println(p.getName() + ": " + confessionalSays.get(r.nextInt(confessionalSays.size())));
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();

    }

    public int getNumTribes() {
        return numTribes;
    }

    public void setNumTribes(int numTribes) {
        Game.numTribes = numTribes;
    }

    public String getSeasonName() {
        return seasonName;
    }

    public static int getRound() {
        return round;
    }

    public void resetRound() {
        Game.round = 0;
    }

    public void setSeasonName(String seasonName) {
        this.seasonName = seasonName;
    }

    public static ArrayList<Player> getJury() {
        return jury;
    }

    public void clearJury() {
        Game.jury = new ArrayList<>();
    }

    public boolean isTwoOrThree() {
        return twoOrThree;
    }

    public static ArrayList<Player> getPlayers() {
        return players;
    }

    public void clearPlayers() {
        Game.players = new ArrayList<>();
    }

    public void resetDay() {
        Game.day = 0;
    }

    public static ArrayList<Scores> getTotals() {
        return totals;
    }

    public static ArrayList<Tribe> getTribes() {
        return tribes;
    }

    public void clearTribes() {
        tribes = new ArrayList<>();
    }

    public void clearTotals() {
        totals = new ArrayList<>();
    }

    public static ArrayList<Player> getPlayerStorage() {
        return playerStorage;
    }

    public void clearPlayerStorage() {
        Game.playerStorage = new ArrayList<>();
    }

    public Player getThirdPlace() {
        return thirdPlace;
    }

    public void setExtraVote(boolean extraVote) {
        this.extraVote = extraVote;
    }

    public void setSAV(boolean SAV) {
        this.SAV = SAV;
    }

    public void setJuryNum(int num){this.juryNum = num;}

    public int getJuryNum(){return juryNum;}

    public void setVB(boolean VB) {
        this.VB = VB;
    }

    public void setLA(boolean LA) {
        this.LA = LA;
    }

    public void setIdol(boolean idol) {
        Idol = idol;
    }

    public void setCYCA(boolean CYCA) {
        this.CYCA = CYCA;
    }

    public void setIB(boolean IB) {
        this.IB = IB;
    }

    public static ArrayList<Player> getEliminated() {
        return eliminated;
    }

    public Player getSecondPlace() {
        return secondPlace;
    }

    public void clearSecondPlace() {
        this.secondPlace = new Player();
    }

    public void clearSoleSurvivor() {
        this.soleSurvivor = new Player();
    }

    public void clearEliminated() {
        Game.eliminated = new ArrayList<>();
    }

    public void clearThirdPlace() {
        this.thirdPlace = new Player();
    }

    public void setTwoOrThree(boolean twoOrThree) {
        Game.twoOrThree = twoOrThree;
    }

    public static boolean isRI() {
        return RI;
    }

    public void setRI(boolean RI) {
        Game.RI = RI;
    }

    public void setFR(boolean FR){Game.FR = FR;}
    
    public static boolean isSplit() {
        return split;
    }

    public void setSplit(boolean split) {
        Game.split = split;
    }

    public static boolean isEI() {
        return EI;
    }

    public void setEI(boolean EI) {
        Game.EI = EI;
    }

    public static boolean isEOE() {
        return EOE;
    }

    public void setEOE(boolean EOE) {
        Game.EOE = EOE;
    }

    public static boolean isTribeSwap() {
        return tribeSwap;
    }

    public  void setTribeSwap(boolean tribeSwap) {
        Game.tribeSwap = tribeSwap;
    }

    public static int getNumPlayers() {
        return numPlayers;
    }

    public void setNumPlayers(int numPlayers) {
        Game.numPlayers = numPlayers;
    }

    public void gameSetUp() throws IOException {
        colors.add(ANSI_GREEN);
        colors.add(ANSI_ORANGE);
        colors.add(ANSI_YELLOW);
        colors.add(ANSI_BLUE);
        colors.add(ANSI_BLACK);
        colors.add(ANSI_PURPLE);
        colors.add(ANSI_CYAN);
        colors.add(ANSI_GOLD);
        colors.add(ANSI_PINK);
        colors.add(ANSI_LIME);
        colors.add(ANSI_MAROON);
        colors.add(ANSI_WHITE);
        FileInputStream fs = new FileInputStream("alliancenames.txt");
        Scanner s = new Scanner(fs);
        while(s.hasNextLine()){
            alliancenames.add(s.nextLine());
        }
        FileInputStream team = new FileInputStream("teamChallenges.txt");
        Scanner tchals = new Scanner(team);
        Integer o = Integer.valueOf(0);
        while (tchals.hasNextLine()) {
            String tchalllengeName = tchals.nextLine();
            teamChallenges.put(o, tchalllengeName);
            String descript = tchals.nextLine();
            Scanner parse = new Scanner(descript);
            parse.useDelimiter("");
            String parsedD = "";
            int letters = 0;
            while (parse.hasNext()) {
                String nextLet = parse.next();
                parsedD = parsedD + nextLet;
                letters++;
                if (letters >= 125 && nextLet.equals(" ")) {
                    parsedD = parsedD + System.lineSeparator();
                    letters = 0;
                }
            }
            teamChalDescriptions.put(tchalllengeName, parsedD);
            o++;
        }
        FileInputStream ind = new FileInputStream("indChallenges.txt");
        Scanner ichals = new Scanner(ind);
        Integer p = Integer.valueOf(0);
        while (ichals.hasNextLine()) {
            String ichalllengeName = ichals.nextLine();
            indChallenges.put(p, ichalllengeName);
            String descript = ichals.nextLine();
            Scanner parse = new Scanner(descript);
            parse.useDelimiter("");
            String parsedD = "";
            int letters = 0;
            while (parse.hasNext()) {
                String nextLet = parse.next();
                parsedD = parsedD + nextLet;
                letters++;
                if (letters >= 125 && nextLet.equals(" ")) {
                    parsedD = parsedD + System.lineSeparator();
                    letters = 0;
                }
            }
            indChalDescriptions.put(ichalllengeName, parsedD);
            p++;
            String chalType = ichals.nextLine();
            indChalTypes.put(ichalllengeName, chalType);
        }
        FileInputStream vconf = new FileInputStream("votingConfessionals.txt");
        Scanner v = new Scanner(vconf);
        while(v.hasNextLine()){
            String conf = v.nextLine();
            Scanner next = new Scanner(conf);
            next.useDelimiter("");
            int letters = 0;
            String parsed = "";
            while (next.hasNext()) {
                String nextLet = next.next();
                parsed = parsed + nextLet;
                letters++;
                if (letters >= 125 && nextLet.equals(" ")) {
                    parsed = parsed + System.lineSeparator();
                    letters = 0;
                }
            }
            votingConfessionals.add(parsed);
        }
        if(sim){
            FileInputStream names = new FileInputStream("Names.txt");
            Scanner nm = new Scanner(names);
            FileInputStream tribenames = new FileInputStream("TribeNames.txt");
            Scanner tscan = new Scanner (tribenames);
            tribeNames = new ArrayList<>();
            ArrayList<String> playerNames = new ArrayList<>();
            while (nm.hasNextLine()) {
                playerNames.add(nm.nextLine());
            }
            while(tscan.hasNextLine()){
                tribeNames.add(tscan.nextLine());
            }
            for (int i = 0; i < numPlayers; i++) {
                String name = playerNames.get(r.nextInt(playerNames.size()));
                playerNames.remove(name);
                Player a = new Player(name, i);
                players.add(a);
            }
            allianceboard = new AllianceBoard(numPlayers);
            playerStorage.addAll(players);
            allianceboard.setNames(playerStorage);
            System.out.println("Jeff Probst: Here are your players: ");
            for (Player player : players) {
                System.out.print(player.getName() + ", ");
            }
            System.out.println();
            ArrayList<Player> playersCopy = new ArrayList<>(players);
            for (int i = 0; i < numTribes; i++) {
                ArrayList<Player> tribeMems = new ArrayList<>();
                for (int j = 0; j < numPlayers / numTribes; j++) {
                    Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                    while (tribeMems.contains(nextPlayer)) {
                        nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                    }
                    tribeMems.add(nextPlayer);
                    playersCopy.remove(nextPlayer);
                }
                String tribenm = tribeNames.get(r.nextInt(tribeNames.size()));
                tribeNames.remove(tribenm);
                Tribe a = new Tribe(tribenm, tribeMems, r.nextInt(5) + 1);
                String color = colors.get(r.nextInt(colors.size()));
                colors.remove(color);
                a.setColor(color);
                tribes.add(a);
            }
        }else {
            Scanner sc = new Scanner(System.in);
            System.out.println("Jeff Probst: Welcome to Survivor!");
            System.out.println("Jeff Probst: What would you like to call this Season?");
            seasonName = sc.nextLine();
            if (seasonName.equals("Test") || seasonName.equals("test")) {
                System.out.println("Jeff Probst: How many players? (Pick 16, 18, or 20)");
                numPlayers = sc.nextInt();
                while (numPlayers != 16 && numPlayers != 18 && numPlayers != 20) {
                    System.out.println("Jeff Probst: Please enter 16, 18, or 20");
                    numPlayers = sc.nextInt();
                }
                allianceboard = new AllianceBoard(numPlayers);
                System.out.println("Jeff Probst: Would you like tribe swaps?");
                String TS = sc.next();
                tribeSwap = TS.equals("YES") || TS.equals("yes");
                if(tribeSwap){
                    System.out.println("Jeff Probst: Would you like a 1 player swap (1), a swap to two tribes (2), or a swap to three tribes (3)?");
                    swaptype=sc.nextInt();
                    while(swaptype != 1 && swaptype !=2 && swaptype!=3){
                        System.out.println("Jeff Probst: Pick a number 1,2, or 3");
                        swaptype=sc.nextInt();
                    }
                }
                System.out.println("Jeff Probst: Would you like Edge Of Extinction, Redemption Island, or Exile Island?");
                System.out.println("Jeff Probst: Type 0 for none, 1 for Edge of Extinction, 2 for Redemption Island, or 3 for Exile Island.");
                String EOEString = sc.next();
                EOE = EOEString.equals("1");
                RI = EOEString.equals("2");
                EI = EOEString.equals("3");
                sc.nextLine();
                System.out.println("Jeff Probst: Would you like the Auction?");
                String auction = sc.nextLine();
                Auction = auction.equals("Yes") || auction.equals("yes");
                System.out.println("Jeff Probst: Would you like Earn the Merge?");
                String etm = sc.nextLine();
                ETM = etm.equals("Yes") || etm.equals("yes");
                System.out.println("Jeff Probst: Would you like Journeys");
                String jo = sc.nextLine();
                Journey = jo.equals("Yes") || jo.equals("yes");
                System.out.println("Jeff Probst: What advantages would you like to have?");
                System.out.println("Pick T or F for each advantage.");
                System.out.println("Idols");
                String IdolString = sc.nextLine();
                Idol = IdolString.equals("t")||IdolString.equals("T");
                System.out.println("Advantages");
                String AdvString = sc.nextLine();
                advantages = AdvString.equals("T") || AdvString.equals("t");
                if(advantages) {
                    System.out.println("Extra Vote");
                    String EVString = sc.nextLine();
                    extraVote = EVString.equals("T") || EVString.equals("t");
                    System.out.println("Steal A Vote");
                    String SAVString = sc.nextLine();
                    SAV = SAVString.equals("t") || SAVString.equals("T");
                    System.out.println("Vote Block");
                    String VBString = sc.nextLine();
                    VB = VBString.equals("T") || VBString.equals("t");
                    System.out.println("Legacy Advantage");
                    String LAString = sc.nextLine();
                    LA = LAString.equals("T") || LAString.equals("t");
                    System.out.println("Idol Blocker");
                    String IBString = sc.nextLine();
                    IB = IBString.equals("t") || IBString.equals("T");
                    System.out.println("Choose Your Champion");
                    String CYCAString = sc.nextLine();
                    CYCA = CYCAString.equals("t") || CYCAString.equals("T");
                    System.out.println("Force Rocks");
                    String FRString = sc.nextLine();
                    FR = FRString.equals("t") || FRString.equals("T");
                    System.out.println("Vote Swap");
                    String vswap = sc.nextLine();
                    voteSwap = vswap.equals("t") || vswap.equals("T");
                    System.out.println("Safety without Power");
                    String swp = sc.nextLine();
                    SwP = swp.equals("t") || swp.equals("T");
                    System.out.println("50/50 Coin");
                    String ffcoin = sc.nextLine();
                    FFCoin = ffcoin.equals("t") || ffcoin.equals("T");
                    System.out.println("Spite Advantage");
                    String sp = sc.nextLine();
                    spite = sp.equals("t") || sp.equals("T");
                }else{
                    if(AdvString.equals("All") || AdvString.equals("all")){
                        extraVote = true;
                        SAV = true;
                        VB = true;
                        LA = true;
                        IB = true;
                        CYCA = true;
                        FR = true;
                        voteSwap = true;
                        SwP = true;
                        FFCoin = true;
                        spite = true;
                        advantages = true;
                    }else {
                        extraVote = false;
                        SAV = false;
                        VB = false;
                        LA = false;
                        IB = false;
                        CYCA = false;
                        FR = false;
                        voteSwap = false;
                        SwP = false;
                        FFCoin = false;
                        spite = false;
                    }
                }
                FileInputStream names = new FileInputStream("Names.txt");
                Scanner nm = new Scanner(names);
                ArrayList<String> playerNames = new ArrayList<>();
                while (nm.hasNextLine()) {
                    playerNames.add(nm.nextLine());
                }
                for (int i = 0; i < numPlayers; i++) {
                    String name = playerNames.get(r.nextInt(playerNames.size()));
                    playerNames.remove(name);
                    Player a = new Player(name, i);
                    players.add(a);
                }
                playerStorage.addAll(players);
                System.out.println("Jeff Probst: Here are your players: ");
                allianceboard.setNames(playerStorage);
                for (Player player : players) {
                    System.out.print(player.getName() + ", ");
                }
                System.out.println();
                System.out.println("Final 2 or Final 3 w/ Fire Making? (Type 2 or 3)");
                String ftc = sc.next();
                twoOrThree = ftc.equals("2");
                System.out.println("Jeff Probst: Would you like a split tribal at the final 10?");
                String splitDec = sc.next();
                split = splitDec.equals("yes") || splitDec.equals("Yes");
                System.out.println("Jeff Probst: How many Jury members would you like?");
                int l = 0;
                while(juryNum==0 || juryNum > numPlayers-5) {
                    try {
                        juryNum = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Jeff Probst: Please pick a number.");
                    }
                    if(l>0){
                        System.out.println("Jeff Probst: Please pick a valid number.");
                    }
                    l++;
                }
                System.out.println("Jeff Probst: How many tribes?");
                numTribes = sc.nextInt();
                sc.nextLine();
                while (numTribes > 4 || numTribes < 1) {
                    System.out.println("Jeff Probst: Invalid tribe amount, try again");
                    numTribes = sc.nextInt();
                }
                while (numPlayers % numTribes != 0) {
                    System.out.println("Jeff Probst: Tribes are not even, pick again");
                    numTribes = sc.nextInt();
                }
                ArrayList<Player> playersCopy = new ArrayList<>(players);
                for (int i = 0; i < numTribes; i++) {
                    ArrayList<Player> tribeMems = new ArrayList<>();
                    for (int j = 0; j < numPlayers / numTribes; j++) {
                        Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                        while (tribeMems.contains(nextPlayer)) {
                            nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                        }
                        tribeMems.add(nextPlayer);
                        playersCopy.remove(nextPlayer);
                    }
                    int tribeNum = i + 1;
                    System.out.println("Name Tribe " + tribeNum);
                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                    String color = colors.get(r.nextInt(colors.size()));
                    colors.remove(color);
                    a.setColor(color);
                    tribes.add(a);
                }
                System.out.println("Jeff Probst: Is there anything you would like to change?");
                String changer = sc.nextLine();
                changes = (changer.equals("Yes") || changer.equals("yes"));
                while(changes) {
                    System.out.println("Jeff Probst: Please type the number of what you would like to change:");
                    System.out.println("1. Season Name");
                    System.out.println("2. Num Players");
                    System.out.println("3. Tribe Swaps");
                    System.out.println("4. Type of Swap");
                    System.out.println("5. Modifiers (EOE RI EI)");
                    System.out.println("6. Auction");
                    System.out.println("7. Earn the Merge");
                    System.out.println("8. Journeys");
                    System.out.println("9. Idols");
                    System.out.println("10. Advantages");
                    System.out.println("11. Players");
                    System.out.println("12. Final Tribal");
                    System.out.println("13. Split Final 10");
                    System.out.println("14. Jury Size");
                    System.out.println("15. Tribe Process");
                    int change = sc.nextInt();
                    sc.nextLine();
                    switch (change) {
                        case 1 -> {
                            System.out.println("Jeff Probst: What would you like to call this Season?");
                            seasonName = sc.nextLine();
                        }
                        case 2 -> {
                            System.out.println("Jeff Probst: How many players? (Pick 16, 18, or 20)");
                            numPlayers = sc.nextInt();
                            while (numPlayers != 16 && numPlayers != 18 && numPlayers != 20) {
                                System.out.println("Jeff Probst: Please enter 16, 18, or 20");
                                numPlayers = sc.nextInt();
                                sc.nextLine();
                                sc.nextLine();
                            }
                            allianceboard = new AllianceBoard(numPlayers);
                            System.out.println("Would you like to name players or auto?");
                            sc.nextLine();
                            String bug = sc.nextLine();
                            players.clear();
                            boolean auto = bug.equals("auto");
                            if (auto) {
                                names = new FileInputStream("Names.txt");
                                nm = new Scanner(names);
                                playerNames = new ArrayList<>();
                                while (nm.hasNextLine()) {
                                    playerNames.add(nm.nextLine());
                                }
                                for (int i = 0; i < numPlayers; i++) {
                                    String name = playerNames.get(r.nextInt(playerNames.size()));
                                    playerNames.remove(name);
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            } else {
                                for (int i = 0; i < numPlayers; i++) {
                                    System.out.println("Jeff Probst: What is the name of the next player?");
                                    String name = sc.nextLine();
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            }
                            playerStorage.addAll(players);
                            System.out.println("Jeff Probst: Here are your players: ");
                            allianceboard.setNames(playerStorage);
                            for (Player player : players) {
                                System.out.print(player.getName() + ", ");
                            }
                            System.out.println();
                        }
                        case 3 -> {
                            System.out.println("Jeff Probst: Would you like tribe swaps?");
                            TS = sc.next();
                            tribeSwap = TS.equals("YES") || TS.equals("yes");
                        }
                        case 4 -> {
                            if(tribeSwap){
                                System.out.println("Jeff Probst: Would you like a 1 player swap (1), a swap to two tribes (2), or a swap to three tribes (3)?");
                                swaptype=sc.nextInt();
                                while(swaptype != 1 && swaptype !=2 && swaptype!=3){
                                    System.out.println("Jeff Probst: Pick a number 1,2, or 3");
                                    swaptype=sc.nextInt();
                                }
                            }
                        }
                        case 5 -> {
                            System.out.println("Jeff Probst: Would you like Edge Of Extinction, Redemption Island, or Exile Island?");
                            System.out.println("Jeff Probst: Type 0 for none, 1 for Edge of Extinction, 2 for Redemption Island, or 3 for Exile Island");
                            EOEString = sc.next();
                            EOE = EOEString.equals("1");
                            RI = EOEString.equals("2");
                            EI = EOEString.equals("3");
                            sc.nextLine();
                        }
                        case 6 -> {
                            System.out.println("Jeff Probst: Would you like the Auction?");
                            auction = sc.nextLine();
                            Auction = auction.equals("Yes") || auction.equals("yes");
                        }
                        case 7 -> {
                            System.out.println("Jeff Probst: Would you like Earn the Merge?");
                            etm = sc.nextLine();
                            ETM = etm.equals("Yes") || etm.equals("yes");
                        }
                        case 8 -> {
                            System.out.println("Jeff Probst: Would you like Journeys");
                            jo = sc.nextLine();
                            Journey = jo.equals("Yes") || jo.equals("yes");
                        }
                        case 9 -> {
                            System.out.println("Idols");
                            IdolString = sc.nextLine();
                            Idol = IdolString.equals("t")||IdolString.equals("T");
                        }
                        case 10 -> {
                            System.out.println("Advantages");
                            AdvString = sc.nextLine();
                            advantages = AdvString.equals("T") || AdvString.equals("t");
                            if(advantages) {
                                System.out.println("Extra Vote");
                                String EVString = sc.nextLine();
                                extraVote = EVString.equals("T") || EVString.equals("t");
                                System.out.println("Steal A Vote");
                                String SAVString = sc.nextLine();
                                SAV = SAVString.equals("t") || SAVString.equals("T");
                                System.out.println("Vote Block");
                                String VBString = sc.nextLine();
                                VB = VBString.equals("T") || VBString.equals("t");
                                System.out.println("Legacy Advantage");
                                String LAString = sc.nextLine();
                                LA = LAString.equals("T") || LAString.equals("t");
                                System.out.println("Idol Blocker");
                                String IBString = sc.nextLine();
                                IB = IBString.equals("t") || IBString.equals("T");
                                System.out.println("Choose Your Champion");
                                String CYCAString = sc.nextLine();
                                CYCA = CYCAString.equals("t") || CYCAString.equals("T");
                                System.out.println("Force Rocks");
                                String FRString = sc.nextLine();
                                FR = FRString.equals("t") || FRString.equals("T");
                                System.out.println("Vote Swap");
                                String vswap = sc.nextLine();
                                voteSwap = vswap.equals("t") || vswap.equals("T");
                                System.out.println("Safety without Power");
                                String swp = sc.nextLine();
                                SwP = swp.equals("t") || swp.equals("T");
                                System.out.println("50/50 Coin");
                                String ffcoin = sc.nextLine();
                                FFCoin = ffcoin.equals("t") || ffcoin.equals("T");
                                System.out.println("Spite Advantage");
                                String sp = sc.nextLine();
                                spite = sp.equals("t") || sp.equals("T");
                            }else{
                                if(AdvString.equals("All") || AdvString.equals("all")){
                                    extraVote = true;
                                    SAV = true;
                                    VB = true;
                                    LA = true;
                                    IB = true;
                                    CYCA = true;
                                    FR = true;
                                    voteSwap = true;
                                    SwP = true;
                                    FFCoin = true;
                                    spite = true;
                                    advantages = true;
                                }else {
                                    extraVote = false;
                                    SAV = false;
                                    VB = false;
                                    LA = false;
                                    IB = false;
                                    CYCA = false;
                                    FR = false;
                                    voteSwap = false;
                                    FFCoin = false;
                                    spite = false;
                                    SwP = false;
                                }
                            }
                        }
                        case 11 -> {
                            players.clear();
                            playerStorage.clear();
                            System.out.println("Would you like to name players or auto?");
                            sc.nextLine();
                            boolean auto = sc.nextLine().equals("auto");
                            if (auto) {
                                names = new FileInputStream("Names.txt");
                                nm = new Scanner(names);
                                playerNames = new ArrayList<>();
                                while (nm.hasNextLine()) {
                                    playerNames.add(nm.nextLine());
                                }
                                for (int i = 0; i < numPlayers; i++) {
                                    String name = playerNames.get(r.nextInt(playerNames.size()));
                                    playerNames.remove(name);
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            } else {
                                for (int i = 0; i < numPlayers; i++) {
                                    System.out.println("Jeff Probst: What is the name of the next player?");
                                    String name = sc.nextLine();
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            }
                            playerStorage.addAll(players);
                            System.out.println("Jeff Probst: Here are your players: ");
                            for (Player player : players) {
                                System.out.print(player.getName() + ", ");
                            }
                            System.out.println();
                            tribes.clear();
                            System.out.println("Jeff Probst: How many tribes?");
                            numTribes = sc.nextInt();
                            sc.nextLine();
                            while (numTribes > 4 || numTribes < 1) {
                                System.out.println("Jeff Probst: Too many, try again");
                                numTribes = sc.nextInt();
                            }
                            while (numPlayers % numTribes != 0) {
                                System.out.println("Jeff Probst: Tribes are not even, pick again");
                                numTribes = sc.nextInt();
                            }
                            String tribeSort = "";
                            System.out.println("Jeff Probst: Would you like to pick tribes or make them random? (Type Pick or Random)");
                            tribeSort = sc.nextLine();
                            if (tribeSort.equals("Random") || tribeSort.equals("random")) {
                                playersCopy = new ArrayList<>(players);
                                for (int i = 0; i < numTribes; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        while (tribeMems.contains(nextPlayer)) {
                                            nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                            } else if (tribeSort.equals("Pick") || tribeSort.equals("pick")) {
                                playersCopy = new ArrayList<>(players);
                                boolean selectedPerson = false;
                                for (int i = 0; i < numTribes - 1; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        selectedPerson = false;
                                        System.out.println("Jeff Probst: Please pick a player for tribe " + (i + 1) + "  \n(The players are in the order you input them)");
                                        Player nextPlayer = playersCopy.get(sc.nextInt());
                                        while (tribeMems.contains(nextPlayer)) {
                                            System.out.println("Jeff Probst: Please pick a different player");
                                            nextPlayer = playersCopy.get(sc.nextInt());
                                        }
                                        System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                        sc.nextLine();
                                        while (!selectedPerson) {
                                            System.out.println("Jeff Probst: Is this the correct player? (Type Y/N) ");
                                            String selectedString = sc.nextLine();
                                            selectedPerson = selectedString.equals("Y") || selectedString.equals("y");
                                            if (!selectedPerson) {
                                                System.out.println("Jeff Probst: Please pick a new player");
                                                nextPlayer = playersCopy.get(sc.nextInt());
                                                sc.nextLine();
                                                System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                            }
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                                int tribeNum = numTribes - 1;
                                System.out.println("Name Tribe " + (tribeNum + 1));
                                Tribe a = new Tribe(sc.nextLine(), playersCopy, r.nextInt(5) + 1);
                                String color = colors.get(r.nextInt(colors.size()));
                                colors.remove(color);
                                a.setColor(color);
                                tribes.add(a);
                            }
                        }
                        case 12 -> {
                            System.out.println("Final 2 or Final 3 w/ Fire Making? (Type 2 or 3)");
                            ftc = sc.next();
                            twoOrThree = ftc.equals("2");
                        }
                        case 13 -> {
                            System.out.println("Jeff Probst: Would you like a split tribal at the final 10?");
                            splitDec = sc.next();
                            split = splitDec.equals("yes") || splitDec.equals("Yes");
                        }
                        case 14 -> {
                            System.out.println("Jeff Probst: How many Jury members would you like?");
                            l = 0;
                            while(juryNum==0 || juryNum > numPlayers-5) {
                                try {
                                    juryNum = sc.nextInt();
                                } catch (Exception e) {
                                    System.out.println("Jeff Probst: Please pick a number.");
                                }
                                if(l>0){
                                    System.out.println("Jeff Probst: Please pick a valid number.");
                                }
                                l++;
                            }
                        }
                        case 15 -> {
                            System.out.println("Jeff Probst: How many tribes?");
                            numTribes = sc.nextInt();
                            sc.nextLine();
                            while (numTribes > 4 || numTribes < 1) {
                                System.out.println("Jeff Probst: Too many, try again");
                                numTribes = sc.nextInt();
                            }
                            while (numPlayers % numTribes != 0) {
                                System.out.println("Jeff Probst: Tribes are not even, pick again");
                                numTribes = sc.nextInt();
                            }
                            String tribeSort = "";
                            System.out.println("Jeff Probst: Would you like to pick tribes or make them random? (Type Pick or Random)");
                            tribeSort = sc.nextLine();
                            if (tribeSort.equals("Random") || tribeSort.equals("random")) {
                                playersCopy = new ArrayList<>(players);
                                for (int i = 0; i < numTribes; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        while (tribeMems.contains(nextPlayer)) {
                                            nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                            } else if (tribeSort.equals("Pick") || tribeSort.equals("pick")) {
                                playersCopy = new ArrayList<>(players);
                                boolean selectedPerson = false;
                                for (int i = 0; i < numTribes-1; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        selectedPerson = false;
                                        System.out.println("Jeff Probst: Please pick a player for tribe " + (i + 1) + "  \n(The players are in the order you input them)");
                                        Player nextPlayer = playersCopy.get(sc.nextInt());
                                        while (tribeMems.contains(nextPlayer)) {
                                            System.out.println("Jeff Probst: Please pick a different player");
                                            nextPlayer = playersCopy.get(sc.nextInt());
                                        }
                                        System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                        sc.nextLine();
                                        while (!selectedPerson) {
                                            System.out.println("Jeff Probst: Is this the correct player? (Type Y/N) ");
                                            String selectedString = sc.nextLine();
                                            selectedPerson = selectedString.equals("Y") || selectedString.equals("y");
                                            if (!selectedPerson) {
                                                System.out.println("Jeff Probst: Please pick a new player");
                                                nextPlayer = playersCopy.get(sc.nextInt());
                                                sc.nextLine();
                                                System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                            }
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                                int tribeNum = numTribes - 1;
                                System.out.println("Name Tribe " + (tribeNum + 1));
                                Tribe a = new Tribe(sc.nextLine(), playersCopy, r.nextInt(5) + 1);
                                String color = colors.get(r.nextInt(colors.size()));
                                colors.remove(color);
                                a.setColor(color);
                                tribes.add(a);
                            }
                        }
                    }
                    System.out.println("Jeff Probst: Do you have any more changes to make?");
                    changes = sc.nextLine().equals("Yes") || sc.nextLine().equals("yes");
                }
            } else if (seasonName.equals("fun") || seasonName.equals("Fun")) {
                System.out.println("Jeff Probst: How many players? (Pick 16, 18, or 20)");
                numPlayers = sc.nextInt();
                while (numPlayers != 16 && numPlayers != 18 && numPlayers != 20) {
                    System.out.println("Jeff Probst: Please enter 16, 18, or 20");
                    numPlayers = sc.nextInt();
                }
                allianceboard = new AllianceBoard(numPlayers);
                System.out.println("Jeff Probst: Would you like tribe swaps?");
                String TS = sc.next();
                tribeSwap = TS.equals("YES") || TS.equals("yes");
                if(tribeSwap){
                    System.out.println("Jeff Probst: Would you like a 1 player swap (1), a swap to two tribes (2), or a swap to three tribes (3)?");
                    swaptype=sc.nextInt();
                    while(swaptype != 1 && swaptype !=2 && swaptype!=3){
                        System.out.println("Jeff Probst: Pick a number 1,2, or 3");
                        swaptype=sc.nextInt();
                    }
                }
                System.out.println("Jeff Probst: Would you like Edge Of Extinction, Redemption Island, or Exile Island?");
                System.out.println("Jeff Probst: Type 0 for none, 1 for Edge of Extinction, 2 for Redemption Island, or 3 for Exile Island");
                String EOEString = sc.next();
                EOE = EOEString.equals("1");
                RI = EOEString.equals("2");
                EI = EOEString.equals("3");
                sc.nextLine();
                System.out.println("Jeff Probst: Would you like the Auction?");
                String auction = sc.nextLine();
                Auction = auction.equals("Yes") || auction.equals("yes");
                System.out.println("Jeff Probst: Would you like Earn the Merge?");
                String etm = sc.nextLine();
                ETM = etm.equals("Yes") || etm.equals("yes");
                System.out.println("Jeff Probst: Would you like Journeys");
                String jo = sc.nextLine();
                Journey = jo.equals("Yes") || jo.equals("yes");
                System.out.println("Jeff Probst: What advantages would you like to have?");
                System.out.println("Pick T or F for each advantage.");
                System.out.println("Idols");
                String IdolString = sc.nextLine();
                Idol = IdolString.equals("t")||IdolString.equals("T");
                System.out.println("Advantages");
                String AdvString = sc.nextLine();
                advantages = AdvString.equals("T") || AdvString.equals("t");
                if(advantages) {
                    System.out.println("Extra Vote");
                    String EVString = sc.nextLine();
                    extraVote = EVString.equals("T") || EVString.equals("t");
                    System.out.println("Steal A Vote");
                    String SAVString = sc.nextLine();
                    SAV = SAVString.equals("t") || SAVString.equals("T");
                    System.out.println("Vote Block");
                    String VBString = sc.nextLine();
                    VB = VBString.equals("T") || VBString.equals("t");
                    System.out.println("Legacy Advantage");
                    String LAString = sc.nextLine();
                    LA = LAString.equals("T") || LAString.equals("t");
                    System.out.println("Idol Blocker");
                    String IBString = sc.nextLine();
                    IB = IBString.equals("t") || IBString.equals("T");
                    System.out.println("Choose Your Champion");
                    String CYCAString = sc.nextLine();
                    CYCA = CYCAString.equals("t") || CYCAString.equals("T");
                    System.out.println("Force Rocks");
                    String FRString = sc.nextLine();
                    FR = FRString.equals("t") || FRString.equals("T");
                    System.out.println("Vote Swap");
                    String vswap = sc.nextLine();
                    voteSwap = vswap.equals("t")||vswap.equals("T");
                    System.out.println("Safety without Power");
                    String swp = sc.nextLine();
                    SwP = swp.equals("t") || swp.equals("T");
                    System.out.println("50/50 Coin");
                    String ffcoin = sc.nextLine();
                    FFCoin = ffcoin.equals("t") || ffcoin.equals("T");
                    System.out.println("Spite Advantage");
                    String sp = sc.nextLine();
                    spite = sp.equals("t") || sp.equals("T");
                }else{
                    if(AdvString.equals("All") || AdvString.equals("all")){
                        extraVote = true;
                        SAV = true;
                        VB = true;
                        LA = true;
                        IB = true;
                        CYCA = true;
                        FR = true;
                        voteSwap = true;
                        SwP = true;
                        FFCoin = true;
                        spite = true;
                        advantages = true;
                    }else {
                        extraVote = false;
                        SAV = false;
                        VB = false;
                        LA = false;
                        IB = false;
                        CYCA = false;
                        FR = false;
                        voteSwap = false;
                        FFCoin = false;
                        spite = true;
                        SwP = false;
                    }
                }
                FileInputStream names = new FileInputStream("Names.txt");
                Scanner nm = new Scanner(names);
                ArrayList<String> playerNames = new ArrayList<>();
                while (nm.hasNextLine()) {
                    playerNames.add(nm.nextLine());
                }
                Player Sam = new Player("Sam", numPlayers - 1);
                for (int i = 0; i < numPlayers - 1; i++) {
                    String name = playerNames.get(r.nextInt(playerNames.size()));
                    playerNames.remove(name);
                    Player a = new Player(name, i);
                    players.add(a);
                }
                players.add(Sam);
                playerStorage.addAll(players);
                System.out.println("Jeff Probst: Here are your players: ");
                allianceboard.setNames(playerStorage);
                for (Player player : players) {
                    System.out.print(player.getName() + ", ");
                }
                System.out.println();
                System.out.println("Final 2 or Final 3 w/ Fire Making? (Type 2 or 3)");
                String ftc = sc.next();
                twoOrThree = ftc.equals("2");
                System.out.println("Jeff Probst: Would you like a split tribal at the final 10?");
                String splitDec = sc.next();
                split = splitDec.equals("yes") || splitDec.equals("Yes");
                System.out.println("Jeff Probst: How many Jury members would you like?");
                int l = 0;
                while(juryNum==0 || juryNum > numPlayers-5) {
                    try {
                        juryNum = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Jeff Probst: Please pick a number.");
                    }
                    if(l>0){
                        System.out.println("Jeff Probst: Please pick a valid number.");
                    }
                    l++;
                }
                System.out.println("Jeff Probst: How many tribes?");
                numTribes = sc.nextInt();
                sc.nextLine();
                while (numTribes > 4 || numTribes < 1) {
                    System.out.println("Jeff Probst: Invalid tribe amount, try again");
                    numTribes = sc.nextInt();
                }
                while (numPlayers % numTribes != 0) {
                    System.out.println("Jeff Probst: Tribes are not even, pick again");
                    numTribes = sc.nextInt();
                }
                ArrayList<Player> playersCopy = new ArrayList<>(players);
                for (int i = 0; i < numTribes; i++) {
                    ArrayList<Player> tribeMems = new ArrayList<>();
                    for (int j = 0; j < numPlayers / numTribes; j++) {
                        Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                        while (tribeMems.contains(nextPlayer)) {
                            nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                        }
                        tribeMems.add(nextPlayer);
                        playersCopy.remove(nextPlayer);
                    }
                    int tribeNum = i + 1;
                    System.out.println("Name Tribe " + tribeNum);
                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                    String color = colors.get(r.nextInt(colors.size()));
                    colors.remove(color);
                    a.setColor(color);
                    tribes.add(a);
                }
                System.out.println("Jeff Probst: Is there anything you would like to change?");
                String changer =sc.nextLine();
                changes = (changer.equals("Yes") || changer.equals("yes"));
                while(changes) {
                    System.out.println("Jeff Probst: Please type the number of what you would like to change:");
                    System.out.println("1. Season Name");
                    System.out.println("2. Num Players");
                    System.out.println("3. Tribe Swaps");
                    System.out.println("4. Type of Swap");
                    System.out.println("5. Modifiers (EOE RI EI)");
                    System.out.println("6. Auction");
                    System.out.println("7. Earn the Merge");
                    System.out.println("8. Journeys");
                    System.out.println("9. Idols");
                    System.out.println("10. Advantages");
                    System.out.println("11. Players");
                    System.out.println("12. Final Tribal");
                    System.out.println("13. Split Final 10");
                    System.out.println("14. Jury Size");
                    System.out.println("15. Tribe Process");
                    int change = sc.nextInt();
                    switch (change) {
                        case 1 -> {
                            System.out.println("Jeff Probst: What would you like to call this Season?");
                            seasonName = sc.nextLine();
                        }
                        case 2 -> {
                            System.out.println("Jeff Probst: How many players? (Pick 16, 18, or 20)");
                            numPlayers = sc.nextInt();
                            while (numPlayers != 16 && numPlayers != 18 && numPlayers != 20) {
                                System.out.println("Jeff Probst: Please enter 16, 18, or 20");
                                numPlayers = sc.nextInt();
                                sc.nextLine();
                                sc.nextLine();
                            }
                            allianceboard = new AllianceBoard(numPlayers);
                            System.out.println("Would you like to name players or auto?");
                            sc.nextLine();
                            String bug = sc.nextLine();
                            players.clear();
                            playerStorage.clear();
                            boolean auto = bug.equals("auto");
                            if (auto) {
                                names = new FileInputStream("Names.txt");
                                nm = new Scanner(names);
                                playerNames = new ArrayList<>();
                                while (nm.hasNextLine()) {
                                    playerNames.add(nm.nextLine());
                                }
                                for (int i = 0; i < numPlayers; i++) {
                                    String name = playerNames.get(r.nextInt(playerNames.size()));
                                    playerNames.remove(name);
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            } else {
                                for (int i = 0; i < numPlayers; i++) {
                                    System.out.println("Jeff Probst: What is the name of the next player?");
                                    String name = sc.nextLine();
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            }
                            playerStorage.addAll(players);
                            System.out.println("Jeff Probst: Here are your players: ");
                            allianceboard.setNames(playerStorage);
                            for (Player player : players) {
                                System.out.print(player.getName() + ", ");
                            }
                            System.out.println();
                        }
                        case 3 -> {
                            System.out.println("Jeff Probst: Would you like tribe swaps?");
                            TS = sc.next();
                            tribeSwap = TS.equals("YES") || TS.equals("yes");
                        }
                        case 4 -> {
                            if(tribeSwap){
                                System.out.println("Jeff Probst: Would you like a 1 player swap (1), a swap to two tribes (2), or a swap to three tribes (3)?");
                                swaptype=sc.nextInt();
                                while(swaptype != 1 && swaptype !=2 && swaptype!=3){
                                    System.out.println("Jeff Probst: Pick a number 1,2, or 3");
                                    swaptype=sc.nextInt();
                                }
                            }
                        }
                        case 5 -> {
                            System.out.println("Jeff Probst: Would you like Edge Of Extinction, Redemption Island, or Exile Island?");
                            System.out.println("Jeff Probst: Type 0 for none, 1 for Edge of Extinction, 2 for Redemption Island, or 3 for Exile Island");
                            EOEString = sc.next();
                            EOE = EOEString.equals("1");
                            RI = EOEString.equals("2");
                            EI = EOEString.equals("3");
                            sc.nextLine();
                        }
                        case 6 -> {
                            System.out.println("Jeff Probst: Would you like the Auction?");
                            auction = sc.nextLine();
                            Auction = auction.equals("Yes") || auction.equals("yes");
                        }
                        case 7 -> {
                            System.out.println("Jeff Probst: Would you like Earn the Merge?");
                            etm = sc.nextLine();
                            ETM = etm.equals("Yes") || etm.equals("yes");
                        }
                        case 8 -> {
                            System.out.println("Jeff Probst: Would you like Journeys");
                            jo = sc.nextLine();
                            Journey = jo.equals("Yes") || jo.equals("yes");
                        }
                        case 9 -> {
                            System.out.println("Idols");
                            IdolString = sc.nextLine();
                            Idol = IdolString.equals("t")||IdolString.equals("T");
                        }
                        case 10 -> {
                            System.out.println("Advantages");
                            AdvString = sc.nextLine();
                            advantages = AdvString.equals("T") || AdvString.equals("t");
                            if(advantages) {
                                System.out.println("Extra Vote");
                                String EVString = sc.nextLine();
                                extraVote = EVString.equals("T") || EVString.equals("t");
                                System.out.println("Steal A Vote");
                                String SAVString = sc.nextLine();
                                SAV = SAVString.equals("t") || SAVString.equals("T");
                                System.out.println("Vote Block");
                                String VBString = sc.nextLine();
                                VB = VBString.equals("T") || VBString.equals("t");
                                System.out.println("Legacy Advantage");
                                String LAString = sc.nextLine();
                                LA = LAString.equals("T") || LAString.equals("t");
                                System.out.println("Idol Blocker");
                                String IBString = sc.nextLine();
                                IB = IBString.equals("t") || IBString.equals("T");
                                System.out.println("Choose Your Champion");
                                String CYCAString = sc.nextLine();
                                CYCA = CYCAString.equals("t") || CYCAString.equals("T");
                                System.out.println("Force Rocks");
                                String FRString = sc.nextLine();
                                FR = FRString.equals("t") || FRString.equals("T");
                                System.out.println("Vote Swap");
                                String vswap = sc.nextLine();
                                voteSwap = vswap.equals("t")||vswap.equals("T");
                                System.out.println("Safety without Power");
                                String swp = sc.nextLine();
                                SwP = swp.equals("t") || swp.equals("T");
                                System.out.println("50/50 Coin");
                                String ffcoin = sc.nextLine();
                                FFCoin = ffcoin.equals("t") || ffcoin.equals("T");
                                System.out.println("Spite Advantage");
                                String sp = sc.nextLine();
                                spite = sp.equals("t") || sp.equals("T");
                            }else{
                                if(AdvString.equals("All") || AdvString.equals("all")){
                                    extraVote = true;
                                    SAV = true;
                                    VB = true;
                                    LA = true;
                                    IB = true;
                                    CYCA = true;
                                    FR = true;
                                    voteSwap = true;
                                    SwP = true;
                                    FFCoin = true;
                                    spite = true;
                                    advantages = true;
                                }else {
                                    extraVote = false;
                                    SAV = false;
                                    VB = false;
                                    LA = false;
                                    IB = false;
                                    CYCA = false;
                                    FR = false;
                                    voteSwap = false;
                                    FFCoin = false;
                                    spite = false;
                                    SwP = false;
                                }
                            }
                        }
                        case 11 -> {
                            players.clear();
                            playerStorage.clear();
                            System.out.println("Would you like to name players or auto?");
                            sc.nextLine();
                            boolean auto = sc.nextLine().equals("auto");
                            if (auto) {
                                names = new FileInputStream("Names.txt");
                                nm = new Scanner(names);
                                playerNames = new ArrayList<>();
                                while (nm.hasNextLine()) {
                                    playerNames.add(nm.nextLine());
                                }
                                for (int i = 0; i < numPlayers; i++) {
                                    String name = playerNames.get(r.nextInt(playerNames.size()));
                                    playerNames.remove(name);
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            } else {
                                for (int i = 0; i < numPlayers; i++) {
                                    System.out.println("Jeff Probst: What is the name of the next player?");
                                    String name = sc.nextLine();
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            }
                            playerStorage.addAll(players);
                            System.out.println("Jeff Probst: Here are your players: ");
                            allianceboard.setNames(playerStorage);
                            for (Player player : players) {
                                System.out.print(player.getName() + ", ");
                            }
                            System.out.println();
                            tribes.clear();
                            System.out.println("Jeff Probst: How many tribes?");
                            numTribes = sc.nextInt();
                            sc.nextLine();
                            while (numTribes > 4 || numTribes < 1) {
                                System.out.println("Jeff Probst: Too many, try again");
                                numTribes = sc.nextInt();
                            }
                            while (numPlayers % numTribes != 0) {
                                System.out.println("Jeff Probst: Tribes are not even, pick again");
                                numTribes = sc.nextInt();
                            }
                            String tribeSort = "";
                            System.out.println("Jeff Probst: Would you like to pick tribes or make them random? (Type Pick or Random)");
                            tribeSort = sc.nextLine();
                            if (tribeSort.equals("Random") || tribeSort.equals("random")) {
                                playersCopy = new ArrayList<>(players);
                                for (int i = 0; i < numTribes; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        while (tribeMems.contains(nextPlayer)) {
                                            nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                            } else if (tribeSort.equals("Pick") || tribeSort.equals("pick")) {
                                playersCopy = new ArrayList<>(players);
                                boolean selectedPerson = false;
                                for (int i = 0; i < numTribes - 1; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        selectedPerson = false;
                                        System.out.println("Jeff Probst: Please pick a player for tribe " + (i + 1) + "  \n(The players are in the order you input them)");
                                        Player nextPlayer = playersCopy.get(sc.nextInt());
                                        while (tribeMems.contains(nextPlayer)) {
                                            System.out.println("Jeff Probst: Please pick a different player");
                                            nextPlayer = playersCopy.get(sc.nextInt());
                                        }
                                        System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                        sc.nextLine();
                                        while (!selectedPerson) {
                                            System.out.println("Jeff Probst: Is this the correct player? (Type Y/N) ");
                                            String selectedString = sc.nextLine();
                                            selectedPerson = selectedString.equals("Y") || selectedString.equals("y");
                                            if (!selectedPerson) {
                                                System.out.println("Jeff Probst: Please pick a new player");
                                                nextPlayer = playersCopy.get(sc.nextInt());
                                                sc.nextLine();
                                                System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                            }
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                                int tribeNum = numTribes - 1;
                                System.out.println("Name Tribe " + (tribeNum + 1));
                                Tribe a = new Tribe(sc.nextLine(), playersCopy, r.nextInt(5) + 1);
                                String color = colors.get(r.nextInt(colors.size()));
                                colors.remove(color);
                                a.setColor(color);
                                tribes.add(a);
                            }
                        }
                        case 12 -> {
                            System.out.println("Final 2 or Final 3 w/ Fire Making? (Type 2 or 3)");
                            ftc = sc.next();
                            twoOrThree = ftc.equals("2");
                        }
                        case 13 -> {
                            System.out.println("Jeff Probst: Would you like a split tribal at the final 10?");
                            splitDec = sc.next();
                            split = splitDec.equals("yes") || splitDec.equals("Yes");
                        }
                        case 14 -> {
                            System.out.println("Jeff Probst: How many Jury members would you like?");
                            l = 0;
                            while(juryNum==0 || juryNum > numPlayers-5) {
                                try {
                                    juryNum = sc.nextInt();
                                } catch (Exception e) {
                                    System.out.println("Jeff Probst: Please pick a number.");
                                }
                                if(l>0){
                                    System.out.println("Jeff Probst: Please pick a valid number.");
                                }
                                l++;
                            }
                        }
                        case 15 -> {
                            tribes.clear();
                            System.out.println("Jeff Probst: How many tribes?");
                            numTribes = sc.nextInt();
                            sc.nextLine();
                            while (numTribes > 4 || numTribes < 1) {
                                System.out.println("Jeff Probst: Too many, try again");
                                numTribes = sc.nextInt();
                            }
                            while (numPlayers % numTribes != 0) {
                                System.out.println("Jeff Probst: Tribes are not even, pick again");
                                numTribes = sc.nextInt();
                            }
                            String tribeSort = "";
                            System.out.println("Jeff Probst: Would you like to pick tribes or make them random? (Type Pick or Random)");
                            tribeSort = sc.nextLine();
                            if (tribeSort.equals("Random") || tribeSort.equals("random")) {
                                playersCopy = new ArrayList<>(players);
                                for (int i = 0; i < numTribes; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        while (tribeMems.contains(nextPlayer)) {
                                            nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                            } else if (tribeSort.equals("Pick") || tribeSort.equals("pick")) {
                                playersCopy = new ArrayList<>(players);
                                boolean selectedPerson = false;
                                for (int i = 0; i < numTribes-1; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        selectedPerson = false;
                                        System.out.println("Jeff Probst: Please pick a player for tribe " + (i + 1) + "  \n(The players are in the order you input them)");
                                        Player nextPlayer = playersCopy.get(sc.nextInt());
                                        while (tribeMems.contains(nextPlayer)) {
                                            System.out.println("Jeff Probst: Please pick a different player");
                                            nextPlayer = playersCopy.get(sc.nextInt());
                                        }
                                        System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                        sc.nextLine();
                                        while (!selectedPerson) {
                                            System.out.println("Jeff Probst: Is this the correct player? (Type Y/N) ");
                                            String selectedString = sc.nextLine();
                                            selectedPerson = selectedString.equals("Y") || selectedString.equals("y");
                                            if (!selectedPerson) {
                                                System.out.println("Jeff Probst: Please pick a new player");
                                                nextPlayer = playersCopy.get(sc.nextInt());
                                                sc.nextLine();
                                                System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                            }
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                                int tribeNum = numTribes - 1;
                                System.out.println("Name Tribe " + (tribeNum+1));
                                Tribe a = new Tribe(sc.nextLine(), playersCopy, r.nextInt(5) + 1);
                                String color = colors.get(r.nextInt(colors.size()));
                                colors.remove(color);
                                a.setColor(color);
                                tribes.add(a);
                            }
                        }
                    }
                    System.out.println("Jeff Probst: Do you have any more changes to make?");
                    changes = sc.nextLine().equals("Yes") || sc.nextLine().equals("yes");
                }
            } else {
                System.out.println("Jeff Probst: How many players? (Pick 16, 18, or 20)");
                numPlayers = sc.nextInt();
                while (numPlayers != 16 && numPlayers != 18 && numPlayers != 20) {
                    System.out.println("Jeff Probst: Please enter 16, 18, or 20");
                    numPlayers = sc.nextInt();
                }
                allianceboard = new AllianceBoard(numPlayers);
                System.out.println("Jeff Probst: Would you like tribe swaps?");
                String TS = sc.next();
                tribeSwap = TS.equals("YES") || TS.equals("yes");
                if(tribeSwap){
                    System.out.println("Jeff Probst: Would you like a 1 player swap (1), a swap to two tribes (2), or a swap to three tribes (3)?");
                    swaptype=sc.nextInt();
                    while(swaptype != 1 && swaptype !=2 && swaptype!=3){
                        System.out.println("Jeff Probst: Pick a number 1,2, or 3");
                        swaptype=sc.nextInt();
                    }
                }
                System.out.println("Jeff Probst: Would you like Edge Of Extinction, Redemption Island, or Exile Island?");
                System.out.println("Jeff Probst: Type 0 for none, 1 for Edge of Extinction, 2 for Redemption Island, or 3 for Exile Island");
                String EOEString = sc.next();
                EOE = EOEString.equals("1");
                RI = EOEString.equals("2");
                EI = EOEString.equals("3");
                sc.nextLine();
                System.out.println("Jeff Probst: Would you like the Auction?");
                String auction = sc.nextLine();
                Auction = auction.equals("Yes") || auction.equals("yes");
                System.out.println("Jeff Probst: Would you like Earn the Merge?");
                String etm = sc.nextLine();
                ETM = etm.equals("Yes") || etm.equals("yes");
                System.out.println("Jeff Probst: Would you like Journeys");
                String jo = sc.nextLine();
                Journey = jo.equals("Yes") || jo.equals("yes");
                System.out.println("Jeff Probst: What advantages would you like to have?");
                System.out.println("Pick T or F for each advantage.");
                System.out.println("Idols");
                String IdolString = sc.nextLine();
                Idol = IdolString.equals("t")||IdolString.equals("T");
                System.out.println("Advantages");
                String AdvString = sc.nextLine();
                advantages = AdvString.equals("T") || AdvString.equals("t");
                if(advantages) {
                    System.out.println("Extra Vote");
                    String EVString = sc.nextLine();
                    extraVote = EVString.equals("T") || EVString.equals("t");
                    System.out.println("Steal A Vote");
                    String SAVString = sc.nextLine();
                    SAV = SAVString.equals("t") || SAVString.equals("T");
                    System.out.println("Vote Block");
                    String VBString = sc.nextLine();
                    VB = VBString.equals("T") || VBString.equals("t");
                    System.out.println("Legacy Advantage");
                    String LAString = sc.nextLine();
                    LA = LAString.equals("T") || LAString.equals("t");
                    System.out.println("Idol Blocker");
                    String IBString = sc.nextLine();
                    IB = IBString.equals("t") || IBString.equals("T");
                    System.out.println("Choose Your Champion");
                    String CYCAString = sc.nextLine();
                    CYCA = CYCAString.equals("t") || CYCAString.equals("T");
                    System.out.println("Force Rocks");
                    String FRString = sc.nextLine();
                    FR = FRString.equals("t") || FRString.equals("T");
                    System.out.println("Vote Swap");
                    String vswap = sc.nextLine();
                    voteSwap = vswap.equals("t")||vswap.equals("T");
                    System.out.println("Safety without Power");
                    String swp = sc.nextLine();
                    SwP = swp.equals("t") || swp.equals("T");
                    System.out.println("50/50 Coin");
                    String ffcoin = sc.nextLine();
                    FFCoin = ffcoin.equals("t") || ffcoin.equals("T");
                    System.out.println("Spite Advantage");
                    String sp = sc.nextLine();
                    spite = sp.equals("t") || sp.equals("T");
                }else{
                    if(AdvString.equals("All") || AdvString.equals("all")){
                        extraVote = true;
                        SAV = true;
                        VB = true;
                        LA = true;
                        IB = true;
                        CYCA = true;
                        FR = true;
                        voteSwap = true;
                        SwP = true;
                        FFCoin = true;
                        spite = true;
                        advantages = true;
                    }else {
                        extraVote = false;
                        SAV = false;
                        VB = false;
                        LA = false;
                        IB = false;
                        CYCA = false;
                        FR = false;
                        voteSwap = false;
                        FFCoin = false;
                        spite = false;
                        SwP = false;
                    }
                }
                System.out.println("Would you like to name players or auto?");
                boolean auto = sc.nextLine().equals("auto");
                if (auto) {
                    FileInputStream names = new FileInputStream("Names.txt");
                    Scanner nm = new Scanner(names);
                    ArrayList<String> playerNames = new ArrayList<>();
                    while (nm.hasNextLine()) {
                        playerNames.add(nm.nextLine());
                    }
                    for (int i = 0; i < numPlayers; i++) {
                        String name = playerNames.get(r.nextInt(playerNames.size()));
                        playerNames.remove(name);
                        Player a = new Player(name, i);
                        players.add(a);
                    }
                } else {
                    for (int i = 0; i < numPlayers; i++) {
                        System.out.println("Jeff Probst: What is the name of the next player?");
                        String name = sc.nextLine();
                        Player a = new Player(name, i);
                        players.add(a);
                    }
                }
                playerStorage.addAll(players);
                System.out.println("Jeff Probst: Here are your players: ");
                allianceboard.setNames(playerStorage);
                for (Player player : players) {
                    System.out.print(player.getName() + ", ");
                }
                System.out.println();
                System.out.println("Final 2 or Final 3 w/ Fire Making? (Type 2 or 3)");
                String ftc = sc.next();
                twoOrThree = ftc.equals("2");
                System.out.println("Jeff Probst: Would you like a split tribal at the final 10?");
                String splitDec = sc.next();
                split = splitDec.equals("yes") || splitDec.equals("Yes");
                System.out.println("Jeff Probst: How many Jury members would you like?");
                int l = 0;
                while(juryNum==0 || juryNum > numPlayers-5) {
                    try {
                        juryNum = sc.nextInt();
                    } catch (Exception e) {
                        System.out.println("Jeff Probst: Please pick a number.");
                    }
                    if(l>0){
                        System.out.println("Jeff Probst: Please pick a valid number.");
                    }
                    l++;
                }
                System.out.println("Jeff Probst: How many tribes?");
                numTribes = sc.nextInt();
                sc.nextLine();
                while (numTribes > 4 || numTribes < 1) {
                    System.out.println("Jeff Probst: Too many, try again");
                    numTribes = sc.nextInt();
                }
                while (numPlayers % numTribes != 0) {
                    System.out.println("Jeff Probst: Tribes are not even, pick again");
                    numTribes = sc.nextInt();
                }
                String tribeSort = "";
                System.out.println("Jeff Probst: Would you like to pick tribes or make them random? (Type Pick or Random)");
                tribeSort = sc.nextLine();
                if (tribeSort.equals("Random") || tribeSort.equals("random")) {
                    ArrayList<Player> playersCopy = new ArrayList<>(players);
                    for (int i = 0; i < numTribes; i++) {
                        ArrayList<Player> tribeMems = new ArrayList<>();
                        for (int j = 0; j < numPlayers / numTribes; j++) {
                            Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                            while (tribeMems.contains(nextPlayer)) {
                                nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                            }
                            tribeMems.add(nextPlayer);
                            playersCopy.remove(nextPlayer);
                        }
                        int tribeNum = i + 1;
                        System.out.println("Name Tribe " + tribeNum);
                        Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                        String color = colors.get(r.nextInt(colors.size()));
                        colors.remove(color);
                        a.setColor(color);
                        tribes.add(a);
                    }
                } else if (tribeSort.equals("Pick") || tribeSort.equals("pick")) {
                    ArrayList<Player> playersCopy = new ArrayList<>(players);
                    boolean selectedPerson = false;
                    for (int i = 0; i < numTribes-1; i++) {
                        ArrayList<Player> tribeMems = new ArrayList<>();
                        for (int j = 0; j < numPlayers / numTribes; j++) {
                            selectedPerson = false;
                            System.out.println("Jeff Probst: Please pick a player for tribe " + (i + 1) + "  \n(The players are in the order you input them)");
                            Player nextPlayer = playersCopy.get(sc.nextInt());
                            while (tribeMems.contains(nextPlayer)) {
                                System.out.println("Jeff Probst: Please pick a different player");
                                nextPlayer = playersCopy.get(sc.nextInt());
                            }
                            System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                            sc.nextLine();
                            while (!selectedPerson) {
                                System.out.println("Jeff Probst: Is this the correct player? (Type Y/N) ");
                                String selectedString = sc.nextLine();
                                selectedPerson = selectedString.equals("Y") || selectedString.equals("y");
                                if (!selectedPerson) {
                                    System.out.println("Jeff Probst: Please pick a new player");
                                    nextPlayer = playersCopy.get(sc.nextInt());
                                    sc.nextLine();
                                    System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                }
                            }
                            tribeMems.add(nextPlayer);
                            playersCopy.remove(nextPlayer);
                        }
                        int tribeNum = i + 1;
                        System.out.println("Name Tribe " + tribeNum);
                        Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                        String color = colors.get(r.nextInt(colors.size()));
                        colors.remove(color);
                        a.setColor(color);
                        tribes.add(a);
                    }
                    int tribeNum = numTribes - 1;
                    System.out.println("Name Tribe " + (tribeNum+1));
                    Tribe a = new Tribe(sc.nextLine(), playersCopy, r.nextInt(5) + 1);
                    String color = colors.get(r.nextInt(colors.size()));
                    colors.remove(color);
                    a.setColor(color);
                    tribes.add(a);
                }
                System.out.println("Jeff Probst: Is there anything you would like to change?");
                String changer = sc.nextLine();
                changes = (changer.equals("Yes") || changer.equals("yes"));
                while(changes) {
                    System.out.println("Jeff Probst: Please type the number of what you would like to change:");
                    System.out.println("1. Season Name");
                    System.out.println("2. Num Players");
                    System.out.println("3. Tribe Swaps");
                    System.out.println("4. Type of Swap");
                    System.out.println("5. Modifiers (EOE RI EI)");
                    System.out.println("6. Auction");
                    System.out.println("7. Earn the Merge");
                    System.out.println("8. Journeys");
                    System.out.println("9. Idols");
                    System.out.println("10. Advantages");
                    System.out.println("11. Players");
                    System.out.println("12. Final Tribal");
                    System.out.println("13. Split Final 10");
                    System.out.println("14. Jury Size");
                    System.out.println("15. Tribe Process");
                    int change = sc.nextInt();
                    switch (change) {
                        case 1 -> {
                            System.out.println("Jeff Probst: What would you like to call this Season?");
                            seasonName = sc.nextLine();
                        }
                        case 2 -> {
                            System.out.println("Jeff Probst: How many players? (Pick 16, 18, or 20)");
                            numPlayers = sc.nextInt();
                            while (numPlayers != 16 && numPlayers != 18 && numPlayers != 20) {
                                System.out.println("Jeff Probst: Please enter 16, 18, or 20");
                                numPlayers = sc.nextInt();
                                sc.nextLine();
                                sc.nextLine();
                            }
                            allianceboard = new AllianceBoard(numPlayers);
                            System.out.println("Would you like to name players or auto?");
                            sc.nextLine();
                            String bug = sc.nextLine();
                            players.clear();
                            playerStorage.clear();
                            auto = bug.equals("auto");
                            if (auto) {
                                FileInputStream names = new FileInputStream("Names.txt");
                                Scanner nm = new Scanner(names);
                                ArrayList<String> playerNames = new ArrayList<>();
                                while (nm.hasNextLine()) {
                                    playerNames.add(nm.nextLine());
                                }
                                for (int i = 0; i < numPlayers; i++) {
                                    String name = playerNames.get(r.nextInt(playerNames.size()));
                                    playerNames.remove(name);
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            } else {
                                for (int i = 0; i < numPlayers; i++) {
                                    System.out.println("Jeff Probst: What is the name of the next player?");
                                    String name = sc.nextLine();
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            }
                            playerStorage.addAll(players);
                            System.out.println("Jeff Probst: Here are your players: ");
                            allianceboard.setNames(playerStorage);
                            for (Player player : players) {
                                System.out.print(player.getName() + ", ");
                            }
                            System.out.println();
                        }
                        case 3 -> {
                            System.out.println("Jeff Probst: Would you like tribe swaps?");
                            TS = sc.next();
                            tribeSwap = TS.equals("YES") || TS.equals("yes");
                        }
                        case 4 -> {
                            if(tribeSwap){
                                System.out.println("Jeff Probst: Would you like a 1 player swap (1), a swap to two tribes (2), or a swap to three tribes (3)?");
                                swaptype=sc.nextInt();
                                while(swaptype != 1 && swaptype !=2 && swaptype!=3){
                                    System.out.println("Jeff Probst: Pick a number 1,2, or 3");
                                    swaptype=sc.nextInt();
                                }
                            }
                        }
                        case 5 -> {
                            System.out.println("Jeff Probst: Would you like Edge Of Extinction, Redemption Island, or Exile Island?");
                            System.out.println("Jeff Probst: Type 0 for none, 1 for Edge of Extinction, 2 for Redemption Island, or 3 for Exile Island");
                            EOEString = sc.next();
                            EOE = EOEString.equals("1");
                            RI = EOEString.equals("2");
                            EI = EOEString.equals("3");
                            sc.nextLine();
                        }
                        case 6 -> {
                            System.out.println("Jeff Probst: Would you like the Auction?");
                            auction = sc.nextLine();
                            Auction = auction.equals("Yes") || auction.equals("yes");
                        }
                        case 7 -> {
                            System.out.println("Jeff Probst: Would you like Earn the Merge?");
                            etm = sc.nextLine();
                            ETM = etm.equals("Yes") || etm.equals("yes");
                        }
                        case 8 -> {
                            System.out.println("Jeff Probst: Would you like Journeys");
                            jo = sc.nextLine();
                            Journey = jo.equals("Yes") || jo.equals("yes");
                        }
                        case 9 -> {
                            System.out.println("Idols");
                            IdolString = sc.nextLine();
                            Idol = IdolString.equals("t")||IdolString.equals("T");
                        }
                        case 10 -> {
                            System.out.println("Advantages");
                            AdvString = sc.nextLine();
                            advantages = AdvString.equals("T") || AdvString.equals("t");
                            if(advantages) {
                                System.out.println("Extra Vote");
                                String EVString = sc.nextLine();
                                extraVote = EVString.equals("T") || EVString.equals("t");
                                System.out.println("Steal A Vote");
                                String SAVString = sc.nextLine();
                                SAV = SAVString.equals("t") || SAVString.equals("T");
                                System.out.println("Vote Block");
                                String VBString = sc.nextLine();
                                VB = VBString.equals("T") || VBString.equals("t");
                                System.out.println("Legacy Advantage");
                                String LAString = sc.nextLine();
                                LA = LAString.equals("T") || LAString.equals("t");
                                System.out.println("Idol Blocker");
                                String IBString = sc.nextLine();
                                IB = IBString.equals("t") || IBString.equals("T");
                                System.out.println("Choose Your Champion");
                                String CYCAString = sc.nextLine();
                                CYCA = CYCAString.equals("t") || CYCAString.equals("T");
                                System.out.println("Force Rocks");
                                String FRString = sc.nextLine();
                                FR = FRString.equals("t") || FRString.equals("T");
                                System.out.println("Vote Swap");
                                String vswap = sc.nextLine();
                                voteSwap = vswap.equals("t")||vswap.equals("T");
                                System.out.println("Safety without Power");
                                String swp = sc.nextLine();
                                SwP = swp.equals("t") || swp.equals("T");
                                System.out.println("50/50 Coin");
                                String ffcoin = sc.nextLine();
                                FFCoin = ffcoin.equals("t") || ffcoin.equals("T");
                                System.out.println("Spite Advantage");
                                String sp = sc.nextLine();
                                spite = sp.equals("t") || sp.equals("T");
                            }else{
                                if(AdvString.equals("All") || AdvString.equals("all")){
                                    extraVote = true;
                                    SAV = true;
                                    VB = true;
                                    LA = true;
                                    IB = true;
                                    CYCA = true;
                                    FR = true;
                                    voteSwap = true;
                                    SwP = true;
                                    FFCoin = true;
                                    spite = true;
                                    advantages = true;
                                }else {
                                    extraVote = false;
                                    SAV = false;
                                    VB = false;
                                    LA = false;
                                    IB = false;
                                    CYCA = false;
                                    FR = false;
                                    voteSwap = false;
                                    SwP = false;
                                    FFCoin = false;
                                    spite = false;
                                    advantages = false;
                                }
                            }
                        }
                        case 11 -> {
                            players.clear();
                            playerStorage.clear();
                            System.out.println("Would you like to name players or auto?");
                            sc.nextLine();
                            auto = sc.nextLine().equals("auto");
                            if (auto) {
                                FileInputStream names = new FileInputStream("Names.txt");
                                Scanner nm = new Scanner(names);
                                ArrayList<String> playerNames = new ArrayList<>();
                                while (nm.hasNextLine()) {
                                    playerNames.add(nm.nextLine());
                                }
                                for (int i = 0; i < numPlayers; i++) {
                                    String name = playerNames.get(r.nextInt(playerNames.size()));
                                    playerNames.remove(name);
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            } else {
                                for (int i = 0; i < numPlayers; i++) {
                                    System.out.println("Jeff Probst: What is the name of the next player?");
                                    String name = sc.nextLine();
                                    Player a = new Player(name, i);
                                    players.add(a);
                                }
                            }
                            playerStorage.addAll(players);
                            System.out.println("Jeff Probst: Here are your players: ");
                            allianceboard.setNames(playerStorage);
                            for (Player player : players) {
                                System.out.print(player.getName() + ", ");
                            }
                            System.out.println();
                            tribes.clear();
                            System.out.println("Jeff Probst: How many tribes?");
                            numTribes = sc.nextInt();
                            sc.nextLine();
                            while (numTribes > 4 || numTribes < 1) {
                                System.out.println("Jeff Probst: Too many, try again");
                                numTribes = sc.nextInt();
                            }
                            while (numPlayers % numTribes != 0) {
                                System.out.println("Jeff Probst: Tribes are not even, pick again");
                                numTribes = sc.nextInt();
                            }
                            System.out.println("Jeff Probst: Would you like to pick tribes or make them random? (Type Pick or Random)");
                            tribeSort = sc.nextLine();
                            if (tribeSort.equals("Random") || tribeSort.equals("random")) {
                                ArrayList<Player> playersCopy = new ArrayList<>(players);
                                for (int i = 0; i < numTribes; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        while (tribeMems.contains(nextPlayer)) {
                                            nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                            } else if (tribeSort.equals("Pick") || tribeSort.equals("pick")) {
                                ArrayList<Player> playersCopy = new ArrayList<>(players);
                                boolean selectedPerson = false;
                                for (int i = 0; i < numTribes - 1; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        selectedPerson = false;
                                        System.out.println("Jeff Probst: Please pick a player for tribe " + (i + 1) + "  \n(The players are in the order you input them)");
                                        Player nextPlayer = playersCopy.get(sc.nextInt());
                                        while (tribeMems.contains(nextPlayer)) {
                                            System.out.println("Jeff Probst: Please pick a different player");
                                            nextPlayer = playersCopy.get(sc.nextInt());
                                        }
                                        System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                        sc.nextLine();
                                        while (!selectedPerson) {
                                            System.out.println("Jeff Probst: Is this the correct player? (Type Y/N) ");
                                            String selectedString = sc.nextLine();
                                            selectedPerson = selectedString.equals("Y") || selectedString.equals("y");
                                            if (!selectedPerson) {
                                                System.out.println("Jeff Probst: Please pick a new player");
                                                nextPlayer = playersCopy.get(sc.nextInt());
                                                sc.nextLine();
                                                System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                            }
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                                int tribeNum = numTribes - 1;
                                System.out.println("Name Tribe " + (tribeNum + 1));
                                Tribe a = new Tribe(sc.nextLine(), playersCopy, r.nextInt(5) + 1);
                                String color = colors.get(r.nextInt(colors.size()));
                                colors.remove(color);
                                a.setColor(color);
                                tribes.add(a);
                            }
                        }
                        case 12 -> {
                            System.out.println("Final 2 or Final 3 w/ Fire Making? (Type 2 or 3)");
                            ftc = sc.next();
                            twoOrThree = ftc.equals("2");
                        }
                        case 13 -> {
                            System.out.println("Jeff Probst: Would you like a split tribal at the final 10?");
                            splitDec = sc.next();
                            split = splitDec.equals("yes") || splitDec.equals("Yes");
                        }
                        case 14 -> {
                            System.out.println("Jeff Probst: How many Jury members would you like?");
                            l = 0;
                            while(juryNum==0 || juryNum > numPlayers-5) {
                                try {
                                    juryNum = sc.nextInt();
                                } catch (Exception e) {
                                    System.out.println("Jeff Probst: Please pick a number.");
                                }
                                if(l>0){
                                    System.out.println("Jeff Probst: Please pick a valid number.");
                                }
                                l++;
                            }
                        }
                        case 15 -> {
                            System.out.println("Jeff Probst: How many tribes?");
                            numTribes = sc.nextInt();
                            sc.nextLine();
                            while (numTribes > 4 || numTribes < 1) {
                                System.out.println("Jeff Probst: Too many, try again");
                                numTribes = sc.nextInt();
                            }
                            while (numPlayers % numTribes != 0) {
                                System.out.println("Jeff Probst: Tribes are not even, pick again");
                                numTribes = sc.nextInt();
                            }
                            tribeSort = "";
                            System.out.println("Jeff Probst: Would you like to pick tribes or make them random? (Type Pick or Random)");
                            tribeSort = sc.nextLine();
                            if (tribeSort.equals("Random") || tribeSort.equals("random")) {
                                ArrayList<Player> playersCopy = new ArrayList<>(players);
                                for (int i = 0; i < numTribes; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        Player nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        while (tribeMems.contains(nextPlayer)) {
                                            nextPlayer = playersCopy.get(r.nextInt(playersCopy.size()));
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                            } else if (tribeSort.equals("Pick") || tribeSort.equals("pick")) {
                                ArrayList<Player> playersCopy = new ArrayList<>(players);
                                boolean selectedPerson = false;
                                for (int i = 0; i < numTribes-1; i++) {
                                    ArrayList<Player> tribeMems = new ArrayList<>();
                                    for (int j = 0; j < numPlayers / numTribes; j++) {
                                        selectedPerson = false;
                                        System.out.println("Jeff Probst: Please pick a player for tribe " + (i + 1) + "  \n(The players are in the order you input them)");
                                        Player nextPlayer = playersCopy.get(sc.nextInt());
                                        while (tribeMems.contains(nextPlayer)) {
                                            System.out.println("Jeff Probst: Please pick a different player");
                                            nextPlayer = playersCopy.get(sc.nextInt());
                                        }
                                        System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                        sc.nextLine();
                                        while (!selectedPerson) {
                                            System.out.println("Jeff Probst: Is this the correct player? (Type Y/N) ");
                                            String selectedString = sc.nextLine();
                                            selectedPerson = selectedString.equals("Y") || selectedString.equals("y");
                                            if (!selectedPerson) {
                                                System.out.println("Jeff Probst: Please pick a new player");
                                                nextPlayer = playersCopy.get(sc.nextInt());
                                                sc.nextLine();
                                                System.out.println("Jeff Probst: This is the player you selected: " + nextPlayer);
                                            }
                                        }
                                        tribeMems.add(nextPlayer);
                                        playersCopy.remove(nextPlayer);
                                    }
                                    int tribeNum = i + 1;
                                    System.out.println("Name Tribe " + tribeNum);
                                    Tribe a = new Tribe(sc.nextLine(), tribeMems, r.nextInt(5) + 1);
                                    String color = colors.get(r.nextInt(colors.size()));
                                    colors.remove(color);
                                    a.setColor(color);
                                    tribes.add(a);
                                }
                                int tribeNum = numTribes-1;
                                System.out.println("Name Tribe " + (tribeNum+1));
                                Tribe a = new Tribe(sc.nextLine(), playersCopy, r.nextInt(5) + 1);
                                String color = colors.get(r.nextInt(colors.size()));
                                colors.remove(color);
                                a.setColor(color);
                                tribes.add(a);
                            }
                        }
                    }
                    System.out.println("Jeff Probst: Do you have any more changes to make?");
                    changes = sc.nextLine().equals("Yes") || sc.nextLine().equals("yes");
                }
            }
            for(Tribe t: tribes){
                System.out.println(t.toString());
            }
            team.close();
            ind.close();
        }
    }

    public boolean champion(Player championUser, Player immunityWinner){
        System.out.println("Jeff Probst: Earlier at the challenge, " + championUser + " used the Choose Your Champion Advantage.");
        if(championUser.equals(immunityWinner)){
            System.out.println("Jeff Probst: But then you went on to win immunity, so there will be no need to reveal who you bet on.");
            return false;
        }else {
            System.out.println("Jeff Probst: " + immunityWinner + " won the challenge. Let's see who " + championUser + " bet on!");
            Player betOn = players.get(r.nextInt(players.size()));
            while (betOn.equals(championUser)) {
                betOn = players.get(r.nextInt(players.size()));
            }
            System.out.println("Jeff Probst: " + championUser + " bet on " + betOn);
            if (betOn.equals(immunityWinner)) {
                System.out.println("Jeff Probst: " + championUser + " was correct!");
                System.out.println("Jeff Probst: " + championUser + " is now immune, any votes cast for " + championUser + " will not count.");
                return true;
            } else {
                System.out.println("Jeff Probst: All votes for " + championUser + " will still count.");
                return false;
            }
        }
    }

    public void fireMakingChallenge(Tribe final4) {
        System.out.println("Jeff Probst: Welcome to the penultimate tribal council.");
        Player chooser = new Player();
        chooser = immunityWinner;
        System.out.println("Jeff Probst: " + chooser + ", you won immunity, so now you decide who joins you in the final tribal and who makes fire!");
        System.out.println("Jeff Probst: Please make your decision now.");
        Player goat = new Player();
        goat.setScore(10000);
        for (int i = 0; i < final4.getTribePlayers().size(); i++) {
            if (final4.getTribePlayers().get(i) != chooser && final4.getTribePlayers().get(i).getScore() < goat.getScore()) {
                goat = final4.getTribePlayers().get(i);
            }
        }
        System.out.println(chooser + ": I select " + goat + " to join me, and the others will make fire.");
        Player fm1 = new Player();
        Player fm2 = new Player();
        int beta = 0;
        for (int i = 0; i < final4.getTribePlayers().size(); i++) {
            if (!final4.getTribePlayers().get(i).equals(goat) && !final4.getTribePlayers().get(i).equals(chooser)) {
                if (beta == 0) {
                    fm1 = final4.getTribePlayers().get(i);
                    beta++;
                } else {
                    fm2 = final4.getTribePlayers().get(i);
                }
            }
        }
        ArrayList<Player> fire = new ArrayList<>();
        fire.add(fm1);
        fire.add(fm2);
        int loserNum = r.nextInt(2);
        int winNum = 1 - loserNum;
        Player loser = fire.get(loserNum);
        Player winner = fire.get(winNum);
        System.out.println("Jeff Probst: Congratulations " + winner + ", you have made fire and survive!");
        winner.setScore(3);
        System.out.println("Jeff Probst: " + loser + ", unfortunately your game will come short. Please bring me your torch.");
        System.out.println("Jeff Probst: " + loser + " the Tribe has Spoken");
        elimvotes.put(loser," -> Lost Fire Making");
        ArrayList<Player> final3 = new ArrayList<>();
        for (int i = 0; i < final4.getTribePlayers().size(); i++) {
            if (!final4.getTribePlayers().get(i).equals(loser)) {
                final3.add(final4.getTribePlayers().get(i));
            }
        }
        /*Scores a = new Scores(loser.getScore(),loser,4);
        totals.add(a);*/
        eliminated.add(loser);
        for(Alliance a: alliances){
            a.removeMember(loser);
        }
        jury.add(loser);
        tribes.get(0).setTribePlayers(final3);
    }

    public void merge() {
        Tribe merge;
        for(Tribe t: tribes){
            for(Player p: t.getTribePlayers()){
                p.setLastTribeColor(t.getColor());
            }
        }
        merged = true;
        Scanner sc = new Scanner(System.in);
        ArrayList<Player> mergeSet = new ArrayList<>();
        for (int i = 0; i < tribes.size(); i++) {
            mergeSet.addAll(tribes.get(i).getTribePlayers());
        }
        while (tribes.size() != 0) {
            tribes.remove(0);
        }
        if(!ETM) {
            System.out.println("Jeff Probst: Everyone drop your buffs!");
        }
        System.out.println("Jeff Probst: Congratulations, you are merged!");
        System.out.println("Jeff Probst: Please select a merge tribe name!");
        if(sim){
            String tribenm = "";
            tribenm= tribeNames.get(r.nextInt(tribeNames.size()));
            tribeNames.remove(tribenm);
            merge = new Tribe(tribenm, mergeSet, r.nextInt(5) + 1);
        }else{
            merge = new Tribe(sc.nextLine(), mergeSet, r.nextInt(5) + 1);
        }
        String color = colors.get(r.nextInt(colors.size()));
        colors.remove(color);
        merge.setColor(color);
        tribes.add(merge);
        numTribes = 1;
        lostTribe = 0;
    }

    public boolean colorOpt(String color){
        if(colors.contains(color)){
            return true;
        }
        return false;
    }

    public void individualImmunityChallenge() {
        System.out.println("Jeff Probst: Come on in guys!");
        String chalName = "";
        if(EI && exiled!=null) {
            System.out.println("Jeff Probst: " + exiled + " coming back from Exile Island");
        }
        System.out.println("Jeff Probst: Welcome to the Day " + (day + 2) + " Immunity Challenge");
        System.out.println("Jeff Probst: I'll take back immunity");
        System.out.println("Jeff Probst: Once again, individual immunity is back up for grabs");
        chalName = indChallenges.get(Integer.valueOf(r.nextInt(indChallenges.size())));
        if(championAdv && merged && championUser != null && players.contains(championUser)){
            System.out.println(championUser + ": I found the Choose Your Champion Advantage earlier today and would like to use it.");
            System.out.println("Jeff Probst: This is the Choose Your Champion Advantage.");
            System.out.println("Jeff Probst: If "+ championUser + " bets on the right survivor, they also win immunity.");
        }
        System.out.println("Jeff Probst: Today's challenge is called " + chalName);
        System.out.println("Jeff Probst: Here is the challenge description:");
        System.out.println(indChalDescriptions.get(chalName));
        String type = indChalTypes.get(chalName);
        int total = 0;
        System.out.println("Jeff Probst: Let's get to it!");
        switch (type) {
            case "Balance" -> {
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).getBalance()) > 1) {
                        total = tribes.get(0).getTribePlayers().get(i).getBalance()+  total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).getBalance()) > 1) {
                        picker = tribes.get(0).getTribePlayers().get(i).getBalance() + picker;
                    }else{
                        picker = picker + 1;
                    }
                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Strength" -> {
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).getStrength()) > 1) {
                        total = tribes.get(0).getTribePlayers().get(i).getStrength() + total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).getStrength()) > 1) {
                        picker = tribes.get(0).getTribePlayers().get(i).getStrength() + picker;
                    }else{
                        picker = picker + 1;
                    }                        if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Strategy" -> {
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).getStrategy()) > 1) {
                        total = tribes.get(0).getTribePlayers().get(i).getStrategy() + total;
                    }else{
                        total = total + 1;
                    }
                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).getStrategy()) > 1) {
                        picker = tribes.get(0).getTribePlayers().get(i).getStrategy() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Balance and Strength" -> {
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).balnstrength()) > 1) {
                        total = tribes.get(0).getTribePlayers().get(i).balnstrength() + total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).balnstrength()) > 1) {
                        picker = tribes.get(0).getTribePlayers().get(i).balnstrength()+ picker;
                    }else{
                        picker = picker + 1;
                    }                            if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Balance and Strategy" -> {
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).balnstrat()) > 1) {
                        total = tribes.get(0).getTribePlayers().get(i).balnstrat() + total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).balnstrat()) > 1) {
                        picker = tribes.get(0).getTribePlayers().get(i).balnstrat() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Strength and Strategy" -> {
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).strengthnstrat()) > 1) {
                        total = tribes.get(0).getTribePlayers().get(i).strengthnstrat() + total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < tribes.get(0).getTribePlayers().size(); i++) {
                    if((tribes.get(0).getTribePlayers().get(i).strengthnstrat()) > 1) {
                        picker = tribes.get(0).getTribePlayers().get(i).strengthnstrat() + picker;
                    }else{
                        picker = picker + 1;
                    }
                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
        }
        Player immune = tribes.get(0).getTribePlayers().get(numImmune);
        immunityWinner = immune;
        System.out.println("Jeff Probst: Survivors ready, GO!!!");
        System.out.println("Jeff Probst: " + ANSI_GOLD + immune + ANSI_RESET + " wins immunity and is safe tonight at Tribal Council");
        tribes.get(0).getTribePlayers().get(numImmune).setImmune(true);
        tribes.get(0).getTribePlayers().get(numImmune).setThreatlvl(3);
        tribes.get(0).getTribePlayers().get(numImmune).setScore(3);
        tribes.get(0).getTribePlayers().get(numImmune).setImmWins(1);
        immunityWins.put(tribes.get(0).getTribePlayers().get(numImmune), Integer.valueOf(tribes.get(0).getTribePlayers().get(numImmune).getImmWins()));
        System.out.println("Jeff Probst: For the rest of you, one of you will be sent home tonight and become the next member of the Jury");
        System.out.println("Jeff Probst: Head back to camp, I'll see you tonight");
        losingTribe = tribes.get(0);
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public int lowestSize(ArrayList<Tribe> t) {
        int min = 11;
        for (Tribe tribe : t) {
            if (tribe.getTribePlayers().size() < min) {
                min = tribe.getTribePlayers().size();
            }
        }
        return min;
    }

    public Tribe earnTheMerge() throws IOException {
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        System.out.println("Treemail: Drop your buffs, you will be living on the " + tribes.get(r.nextInt(tribes.size())).tribeName() + " beach!");
        campLife();
        System.out.println("Jeff Probst: You have made it past the tribe stage of this game, but you have not made the merge.");
        System.out.println("Jeff Probst: You will have to earn the merge.");
        System.out.println("Jeff Probst: For today's challenge, you will be split into two teams.");
        Player oddmanout = new Player();
        if(players.size()%2==0){
            System.out.println("Jeff Probst: The teams will be even and the winning tribe will all have immunity at tribal council.");
        }else{
            System.out.println("Jeff Probst: There will be one odd man out, that person will have to decide which team to back.");
            System.out.println("Jeff Probst: They will win or lose immunity with the tribe that they pick.");
        }
        System.out.println("Jeff Probst: We will draw rocks for teams.");
        oddmanout = players.get(r.nextInt(players.size()));
        System.out.println("Jeff Probst: Everyone reveal.");
        Tribe a = new Tribe();
        a.setTribeName("Red");
        a.setColor(ANSI_RED);
        Tribe b = new Tribe();
        a.setTribeName("Blue");
        a.setColor(ANSI_BLUE);
        int count = 0;
        ArrayList<Player> group = new ArrayList<>(players);
        while(count<5){
            Player picked = group.get(r.nextInt(group.size()));
            a.addPlayer(picked);
            group.remove(picked);
            count++;
        }
        if(players.size()%2==0){
            while(group.size()>0){
                Player picked = group.get(r.nextInt(group.size()));
                b.addPlayer(picked);
                group.remove(picked);
            }
        }else{
            while(group.size()>1){
                Player picked = group.get(r.nextInt(group.size()));
                b.addPlayer(picked);
                group.remove(picked);
            }
            oddmanout = group.get(0);
            System.out.println("Jeff Probst: The odd man out is " + oddmanout);
        }
//        for(int i = 0; i<players.size()-1;i++) {
//            if (players.get(i) != oddmanout) {
//                int team = r.nextInt(2);
//                if (team == 0) {
//                    if (b.getTribePlayers() == null || b.getTribePlayers().size() != players.size() / 2) {
//                        b.addPlayer(players.get(i));
//                    } else {
//                        a.addPlayer(players.get(i));
//                    }
//                } else {
//                    if (a.getTribePlayers() == null || a.getTribePlayers().size() != players.size() / 2) {
//                        a.addPlayer(players.get(i));
//                    } else {
//                        b.addPlayer(players.get(i));
//                    }
//                }
//            }else{
//                System.out.println("Jeff Probst: The odd man out is " + oddmanout);
//            }
//        }
        System.out.println("Jeff Probst: Group 1 is: " + a.getTribePlayers());
        System.out.println("Jeff Probst: Group 2 is: " + b.getTribePlayers());
        String chalName = teamChallenges.get(Integer.valueOf(r.nextInt(teamChallenges.size())));
        System.out.println("Jeff Probst: Today's challenge is called " + chalName);
        System.out.println("Jeff Probst: Here is a description of the challenge: ");
        System.out.println(teamChalDescriptions.get(chalName));
        Tribe pick = new Tribe();
        if(players.size()%2!=0){
            System.out.println("Jeff Probst: " + oddmanout.getName() + ", pick which group you will back.");
            if(a.tribeStats()>b.tribeStats()){
                System.out.println(oddmanout + ": I will back " + a.getTribePlayers().toString());
                pick = a;
            }else{
                System.out.println(oddmanout + ": I will back " + b.getTribePlayers().toString());
                pick = b;
            }
        }
        ArrayList<Tribe> copy = new ArrayList<>();
        copy.add(a);
        copy.add(b);
        int full;
        int half;
        full = 0;
        half = 0;
        for (Tribe tribe : copy) {
            full = full + tribe.tribeStats() + tribe.getBoost();
        }
        int spice = r.nextInt(full);
        half = half + copy.get(0).tribeStats() + copy.get(0).getBoost();
        if (half < spice) {
            losingTribe = copy.get(0);
        }else{
            losingTribe = copy.get(1);
        }
        for (int i = 0; i < copy.size(); i++) {
            if (copy.get(i)!=losingTribe) {
                for(Player player:copy.get(i).getTribePlayers()){
                    player.setScore(2);
                    player.setImmune(true);
                }
                System.out.println("Jeff Probst: " +ANSI_GOLD + copy.get(i).getTribePlayers().toString() + ANSI_RESET + " you have earned the merge!");
            }
        }
        if(pick!=losingTribe){
            System.out.println("Jeff Probst: " + oddmanout + ", you picked the right team, you also have earned the merge!");
            oddmanout.setScore(1);
            oddmanout.setImmune(true);
        }else{
            System.out.println("Jeff Probst: " + oddmanout + ", you picked the wrong group, you have not earned the merge.");
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        lostTribe = 0;
        Tribe c = new Tribe();
        for(int i = 0; i<players.size();i++){
            c.addPlayer(players.get(i));
        }
        tribes.clear();
        tribes.add(c);
        return c;
    }

    public boolean unevenTribes(ArrayList<Tribe> t) {
        int size = tribes.get(0).getTribePlayers().size();
        for (int i = 1; i < tribes.size(); i++) {
            if (size != tribes.get(i).getTribePlayers().size()) {
                return true;
            }
        }
        return false;
    }

    public Player sitout(Tribe t) {
        int min = 100;
        Player sitout = null;
        for (Player player : t.getTribePlayers()) {
            if (player.numStats() < min) {
                sitout = player;
            }
        }
        return sitout;
    }

    public void tribalImmunityChallenge() {
        System.out.println("Jeff Probst: Come on in guys!");
        String chalName = "";
        if (EI && exiled != null) {
            System.out.println("Jeff Probst: Let's welcome back " + exiled + " from Exile Island.");
        }
        System.out.println("Jeff Probst: Welcome to the Day " + (day + 2) + " Immunity Challenge");
        System.out.println("Jeff Probst: Alright, I'll take back immunity");
        System.out.println("Jeff Probst: Once again, tribal immunity is back up for grabs");
        chalName = teamChallenges.get(Integer.valueOf(r.nextInt(teamChallenges.size())));
        System.out.println("Jeff Probst: Today's challenge is called " + chalName);
        System.out.println("Jeff Probst: Here is a description of the challenge: ");
        System.out.println(teamChalDescriptions.get(chalName));
        //how to do even tribes when the numbers are uneven
        ArrayList<Tribe> copy = new ArrayList<>();
        ArrayList<Player> sitouts = new ArrayList<>();
        if (unevenTribes(tribes)) {
            int min = lowestSize(tribes);
            for (Tribe tribe : tribes) {
                ArrayList<Player> temp = new ArrayList<>(tribe.getTribePlayers());
                Tribe tribecopy = new Tribe(tribe.tribeName(), temp, Zero);
                if (tribe.getTribePlayers().size() != min) {
                    int diff = tribe.getTribePlayers().size() - min;
                    System.out.println("Jeff Probst: " + tribe.tribeName() + ", you have " + diff + " extra players.");
                    System.out.println("Jeff Probst: Who are you gonna sit out?");
                    while (tribecopy.getTribePlayers().size() != min) {
                        Player sitout = sitout(tribecopy);
                        sitouts.add(sitout);
                        tribecopy.getTribePlayers().remove(sitout);
                        System.out.println(tribecopy.tribeName() + ": We will sit out " + sitout + ".");
                    }
                }
                copy.add(tribecopy);
            }
        } else {
            copy = new ArrayList<>(tribes);
        }
        //this is where Jeff would describe said challenge, gonna just be random now
        int full;
        int half;
        while (copy.size() != 1) {
            full = 0;
            half = 0;
            for (Tribe tribe : copy) {
                full = full + tribe.tribeStats() + tribe.getBoost();
            }
            int spice = r.nextInt(full);
            for (int i = 0; i < tribes.size(); i++) {
                half = half + copy.get(i).tribeStats() + copy.get(i).getBoost();
                if (half > spice) {
                    copy.remove(i);
                    break;
                }
            }
        }
        for (int i = 0; i < tribes.size(); i++) {
            if (tribes.get(i).tribeName().equals(copy.get(0).tribeName())) {
                lostTribe = i;
                losingTribe = tribes.get(i);
            }
        }
        //lostTribe = r.nextInt(numTribes);
        //losingTribe = tribes.get(lostTribe);
        for (int i = 0; i < tribes.size(); i++) {
            if (i != lostTribe) {
                System.out.println("Jeff Probst: " + tribes.get(i).getColor() + tribes.get(i).tribeName() + ANSI_RESET + " wins immunity!");
                tribes.get(i).setBoost(5);
                for(Player player:tribes.get(i).getTribePlayers()){
                    player.setScore(2);
                }
            }
        }
        System.out.println("Jeff Probst: " + losingTribe.getColor() + losingTribe.tribeName() + ANSI_RESET + " I've got nothing for you but a date with me at tribal council");
        System.out.println("Jeff Probst: Where one of you will be sent home tonight and lose your flame");
        System.out.println("Jeff Probst: Head back to camp, I'll see you tonight");
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public int avgVotability(ArrayList<Player> p) {
        int count = 0;
        for (Player player : p) {
            count = count + player.getVotability();
        }
        return count / p.size();
    }

    public void tribalCouncil(Tribe losers) throws FileNotFoundException {
        if (losers.getTribePlayers().size() == 2) {
            System.out.println("Jeff Probst: Welcome back to tribe council, where fire represents your life in the game");
            System.out.println("Jeff Probst: Well this is uncommon.");
            System.out.println("Jeff Probst: Only in Survivor Palau did a tribe come down to two survivors.");
            System.out.println("Jeff Probst: Well in any case, you two will battle by FIRE!");
            int lostOut = r.nextInt(2);
            Player out = losers.getTribePlayers().get(lostOut);
            Player winner = losers.getTribePlayers().get(1 - lostOut);
            System.out.println("Jeff Probst: " + winner + " you have made fire!");
            System.out.println("Jeff Probst: " + out + " unfortunately your flame will no longer burn");
            System.out.println("Jeff Probst: " + out + " the game has spoken!");
            votedOutLast = out;
            exit(out);
            if(!RI) {
                eliminated.add(out);
            }else if(players.size()<6 && rreturned){
                eliminated.add(out);
            }

            for(Alliance a: alliances){
                a.removeMember(out);
            }
            /*Scores p = new Scores(out.getScore(), out, players.size());
            totals.add(p);*/
            players.remove(out);
            tribes.get(lostTribe).getTribePlayers().remove(out);
        } else if (players.size() == 3 && twoOrThree) {
            System.out.println("Jeff Probst: Welcome to the penultimate final tribal council!");
            Player voteCaster = new Player();
            for (Player player : losers.getTribePlayers()) {
                if (player.isImmune()) {
                    voteCaster = player;
                }
            }
            System.out.println("Jeff Probst: " + voteCaster + " has immunity, and the other two votes will cancel out.");
            System.out.println("Jeff Probst: Therefore, " + voteCaster + " will cast the sole vote");
            WeightedSpinner<Player> s = new WeightedSpinner<>();
            Player vote = new Player();
            for (int j = 0; j < losers.getTribePlayers().size(); j++) {
                if (voteCaster != losers.getTribePlayers().get(j)) {
                    s.add(losers.getTribePlayers().get(j), (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), voteCaster.getNum())));
                }
            }
            vote = s.spin();
            System.out.println("Jeff Probst: I'll read the sole vote");
            int placement = numPlayers - players.size() + 1;
            System.out.println("Jeff Probst: The " + placement + "th and final person voted out of Survivor: " + ANSI_RED + vote + ANSI_RESET);
            vote.setVotesAgainst();
            exit(vote);
            allianceboard.setSpot(voteCaster.getNum(), vote.getNum(), allianceboard.getSpot(voteCaster.getNum(), vote.getNum()) + 2);
            eliminated.add(vote);
            for(Alliance a: alliances){
                a.removeMember(vote);
            }
            elimvotes.put(vote, " -> 1");
            players.remove(vote);
            jury.add(vote);
            losers.getTribePlayers().remove(vote);
            thirdPlace = vote;
        } else {
            boolean ChampRight = false;
            ArrayList<Player> safe = new ArrayList<>();
            String tied = "";
            HashMap<Player, Integer> votes = new HashMap<>();
            ArrayList<Player> voteCnt = new ArrayList<>();
            Player match = new Player();
            Player blockUser = new Player();
            boolean ev = false;
            int max = 0;
            System.out.println("Jeff Probst: Welcome back to tribe council, where fire represents your life in the game");
            if (players.size() > 11&& !merged) {
                System.out.println("Jeff Probst: " + losers.getColor() + losers.tribeName() + ANSI_RESET + " tough loss out there");
            }
            if (jury.size() > 0) {
                System.out.println("Jeff Probst: Here are the members of the Jury: " + jury.toString());
            }
            System.out.println("Jeff Probst: It's time to vote");
            for (Player player : losers.getTribePlayers()) {
                if(((player.getVotability() > avgVotability(losers.getTribePlayers()) + 2.15 && numPlayers - eliminated.size() > 4)||numPlayers-eliminated.size()==5)) {
                    boolean played = false;
                    int asel = -1;
                    if(player.sumAdv()==1){
                        asel = 0;
                    }else if(player.sumAdv()>1){
                        asel = r.nextInt(player.sumAdv());
                    }
                    if (player.getAdvantages(1) > 0 && !played) {
                        if(asel == 0) {
                            System.out.println(player + ": I'd like to play my Extra Vote");
                            ev = true;
                            advName.put(player, "Extra");
                            player.setAdvantages(1, player.getAdvantages(1) - 1);
                            player.setScore(3);
                            played = true;
                        }else{
                            asel--;
                        }
                    } else if (player.getAdvantages(2) > 0 && !played) {
                        if(asel == 0) {
                            Player lostVote = player;
                            while (lostVote == player) {
                                lostVote = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                            }
                            System.out.println(player + ": I'd like to play my Vote Blocker on " + lostVote);
                            losesVote.add(lostVote);
                            lostVote.setCanVote(false);
                            player.setAdvantages(2, player.getAdvantages(2) - 1);
                            player.setScore(3);
                            played = true;
                        }else{
                            asel--;
                        }
                    } else if (player.getAdvantages(3) > 0 && !played) {
                        if(asel == 0) {
                            Player lostVote = player;
                            while (lostVote == player) {
                                lostVote = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                            }
                            System.out.println(player + ": I'd like to play my Steal A Vote on " + lostVote);
                            lostVote.setCanVote(false);
                            losesVote.add(lostVote);
                            ev = true;
                            advName.put(player, "Steal");
                            player.setAdvantages(3, player.getAdvantages(3) - 1);
                            player.setScore(4);
                            played = true;
                        }else{
                            asel--;
                        }
                    } else if (player.getAdvantages(4) > 0 && !played) {
                        if(asel == 0) {
                            idolBlock = true;
                            blockUser = player;
                            played = true;
                        }else{
                            asel--;
                        }
                    } else if (player.getAdvantages(5) > 0 && !played) {
                        if(asel == 0) {
                            championAdv = true;
                            championUser = player;
                            played = true;
                        }else{
                            asel--;
                        }
                    } else if (player.getAdvantages(6) > 0 && (players.size() == 13 || players.size() == 6) && !played) {
                        if(asel == 0) {
                            legacy = player;
                            safe.add(legacy);
                            legacyUsable = true;
                            player.setScore(4);
                            played = true;
                        }else{
                            asel--;
                        }
                    } else if (player.getAdvantages(7) > 0 && !played) {
                        if(asel == 0) {
                            FRUsable = true;
                            System.out.println(player + ": I would like to use the Force Rocks Advantage!");
                            System.out.println("Jeff Probst: This is the Force Rocks Advantage, " + player + " will now be safe, and the rest of you will draw rocks.");
                            safe.add(player);
                            rocks(safe, losingTribe.getTribePlayers());
                            player.setScore(3);
                            FRUsable = false;
                            round++;
                            player.setAdvantages(7, player.getAdvantages(7) - 1);
                            played = true;
                            return;
                        }else{
                            asel--;
                        }
                    } else if (player.getAdvantages(8) > 0 && !played) {
                        if(asel == 0) {
                            voteSwapActive = true;
                            played = true;
                        }else{
                            asel--;
                        }
                    } else if (player.getAdvantages(9) > 0 && !played && !safe.contains(player)) {
                        if(asel == 0) {
                            System.out.println(player + ": I would like to play my Safety without Power Advantage!");
                            player.setCanVote(false);
                            losesVote.add(player);
                            player.setImmune(true);
                            player.setScore(3);
                            player.setAdvantages(9, player.getAdvantages(9) - 1);
                            System.out.println("Jeff Probst: " + player + " will now leave Tribal Council and not vote.");
                            played = true;
                        }else{
                            asel--;
                        }
                    } else if (player.getAdvantages(10) > 0 && !played && !safe.contains(player)){
                        FFCoinUsed = true;
                    }
                }
            }
            Player starter = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
            while (losesVote.contains(starter)) {
                starter = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
            }
            System.out.println(starter + " start us off!");
            for (Player player : losers.getTribePlayers()) {
                if ((player.getVotability() > avgVotability(losers.getTribePlayers()) + 2.15 || players.size() == player.getIdolsInPossesion() + 4) && player.getIdolsInPossesion() > 0 && !player.isImmune() && numPlayers - eliminated.size() > 4) {
                    idolPlayers.add(player);
                    safe.add(player);
                    player.setIdolsInPossesion(-1);
                }
            }
            int total = 0;
            for (int i = 0; i < losers.getTribePlayers().size(); i++) {
                if (!losers.getTribePlayers().get(i).isImmune()) {
                    total = total + losers.getTribePlayers().get(i).getVotability();//* allianceboard.getSpot(0,losers.getTribePlayers().get(i).getNum());
                }
            }
            Player vote = new Player();
            ArrayList<Player> voters = new ArrayList<>();
            for (int i = 0; i < losers.getTribePlayers().size(); i++) {
                /*if (!losers.getTribePlayers().get(i).isImmune()) {
                    split = losers.getTribePlayers().get(i).getVotability();
                }*/
                WeightedSpinner<Player> s = new WeightedSpinner<>();
                for (int j = 0; j < losers.getTribePlayers().size(); j++) {
                    if (i != j && !losers.getTribePlayers().get(j).isImmune() && (!losers.getTribePlayers().get(j).isInAllianceWith(losers.getTribePlayers().get(i)) || losers.getTribePlayers().get(i).getTargets().contains(losers.getTribePlayers().get(j)))) {
                        if(losers.getTribePlayers().get(i).getTargets().contains(losers.getTribePlayers().get(j))){
                            s.add(losers.getTribePlayers().get(j), (losers.getTribePlayers().get(j).getVotability() * 10 *allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum())));
                        }else {
                            //         alvotes += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum()));
                            s.add(losers.getTribePlayers().get(j), (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum())));
                        }
                    }
//                    vt = r.nextInt(alvotes);
                }
                if (losers.getTribePlayers().get(i).isImmune()) {
                    safe.add(losers.getTribePlayers().get(i));
                }
                if(s.size() == 0){
                    for (int j = 0; j < losers.getTribePlayers().size(); j++) {
                        if (i != j && !losers.getTribePlayers().get(j).isImmune()){
                            s.add(losers.getTribePlayers().get(j), (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum())));
                        }
                    }
                }
                vote = s.spin();
//                for (int j = 0; j < losers.getTribePlayers().size(); j++) {
//                    if (!losers.getTribePlayers().get(j).isImmune() && i != j) {
//                        sectioned += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(),losers.getTribePlayers().get(i).getNum()));
//                        //int vt = r.nextInt(total - split);
//                        if (vt < sectioned) {
//                            vote = losers.getTribePlayers().get(j);
//                            sectioned = 0;
//                            j = losers.getTribePlayers().size() + 1;
//                        }
//                    }
//            /*Player vote = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
//            while(vote.isImmune() || vote==losers.getTribePlayers().get(i)){
//                vote = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
//            }*/
//                }
                if (losers.getTribePlayers().get(i).CanVote()) {
                    voteCnt.add(vote);
                    allianceboard.setSpot(losers.getTribePlayers().get(i).getNum(), vote.getNum(), allianceboard.getSpot(losers.getTribePlayers().get(i).getNum(), vote.getNum()) + 2);
                    tribalCouncilVotes.put(losers.getTribePlayers().get(i), vote);
                    voters.add(losers.getTribePlayers().get(i));
                    vote.setVotesAgainst();
                }
                if (advName.containsKey(losers.getTribePlayers().get(i)) && (advName.get(losers.getTribePlayers().get(i)).equals("Extra")||advName.get(losers.getTribePlayers().get(i)).equals("Steal")) && ev) {
                    Player EV = new Player();
                    if(tribalCouncilVotes.get(losers.getTribePlayers().get(i))!=null) {
                        EV.setName(losers.getTribePlayers().get(i).getName() + " EV");
                        vote = tribalCouncilVotes.get(losers.getTribePlayers().get(i));
                        tribalCouncilVotes.put(EV, vote);
                        voteCnt.add(vote);
                    }else {
                        Player newVote = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                        while (newVote.equals(losers.getTribePlayers().get(i))) {
                            newVote = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                        }
                        EV.setName(losers.getTribePlayers().get(i).getName());
                        tribalCouncilVotes.put(EV, newVote);
                        voteCnt.add(newVote);
                    }
                    advName.remove(losers.getTribePlayers().get(i));

                }
            }
            if (idolBlock) {
                match = tribalCouncilVotes.get(blockUser);
            }
            if (players.size() > 4) {
                System.out.println("Jeff Probst: If anybody has an idol or an advantage and would like to play it, now would be the time to do so.");
            }
            if (idolPlayers.size() > 0) {
                Player failed = new Player();
                for (Player player : idolPlayers) {
                    double risk = r.nextDouble();
                    System.out.println(player + ": Jeff, I feel like there is a big target on my back and I am not safe tonight.");
                    if(risk>=.025) {
                        System.out.println("Jeff Probst: This is an immunity idol, all votes for " + player + " will not count.");
                        player.setScore(5);
                    }else{
                        System.out.println("Jeff Probst: This is " + ANSI_RED + "NOT" + ANSI_RESET + " an immunity idol, all votes for " + player + " will still count.");
                        failed = player;
                        Player tricker  = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                        while(tricker==player){
                            tricker  = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                        }
                        System.out.println(tricker + ": Gotcha!");
                        tricker.setScore(5);
                        tricker.setVotability(10);
                        player.setIdolsInPossesion(-1);
                        safe.remove(player);
                        player.setScore(-5);
                    }
                }
                idolPlayers.removeIf(p -> !safe.contains(p));
            }
            if(voteSwapActive){
                for(Player player:losers.getTribePlayers()) {
                    if(player.getAdvantages(8)>0) {
                        System.out.println(player + ": I would like to use the Vote Swap Advantage!");
                        player.setAdvantages(8,player.getAdvantages(8)-1);
                        System.out.println("Jeff Probst: This is the Vote Swap Advantage, " + player + " please select two players for their number of votes to be swapped.");
                        swap1 = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                        swap2 = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                        while (swap1 == swap2) {
                            swap2 = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                        }
                        if(swap1==player){
                            System.out.println(player + ": I will be swapping myself and " + swap2);
                        }else if(swap2==player) {
                            System.out.println(player + ": I will be swapping " + swap1 + " and myself");
                        }else{
                            System.out.println(player + ": I will be swapping " + swap1 + " and " + swap2);
                        }
                    }

                }
            }
            if(FFCoinUsed && FFCoin){
                for(Player player:losers.getTribePlayers()) {
                    if(player.getAdvantages(10) > 0 && ((player.getVotability() > avgVotability(losers.getTribePlayers()) + 2.15 && numPlayers - eliminated.size() > 4)||numPlayers-eliminated.size()==5) && !idolPlayers.contains(player)) {
                        System.out.println(player + ": I would like to play this 50/50 Coin");
                        System.out.println("Jeff Probst: Ok, if the coin lands on what you call, you will be safe.");
                        System.out.println("Jeff Probst: Call it in the air.");
                        int choice = r.nextInt(2);
                        int coinflip = r.nextInt(2);
                        if (choice == 0) {
                            System.out.println(player + ": I call Heads!");
                            if (coinflip == 0) {
                                System.out.println("Jeff Probst: It is Heads! Any votes for " + player + " will NOT count.");
                                safe.add(player);
                                player.setScore(5);
                            } else {
                                System.out.println("Jeff Probst: It is Tails! Any votes for " + player + " will STILL count.");
                                player.setScore(-1);
                            }
                        } else {
                            System.out.println(player + ": I call Tails!");
                            if (coinflip == 0) {
                                System.out.println("Jeff Probst: It is Heads! Any votes for " + player + " will STILL count.");
                                player.setScore(-1);
                            } else {
                                System.out.println("Jeff Probst: It is Tails! Any votes for " + player + " will NOT count.");
                                safe.add(player);
                                player.setScore(5);
                            }
                        }
                        player.setAdvantages(10,player.getAdvantages(10)-1);
                    }
                }
                FFCoinUsed = false;
            }
            if(spite) {
                ArrayList<Player> tgopts = new ArrayList<>();
                if(spiteActive && losingTribe.getTribePlayers().contains(spiteTarget)){
                    System.out.println("Jeff Probst: The Spite Advantage was secretly played last tribal council.");
                    if (spiteUser == votedOutLast && immunityWinner != spiteTarget) {
                        System.out.println("Jeff Probst: " + spiteCnt + " votes will be added to " + spiteTarget + "'s total votes.");
                        for(int sp = 0; sp < spiteCnt; sp++){
                            voteCnt.add(spiteTarget);
                        }
                    }else{
                        if(spiteUser != votedOutLast) {
                            System.out.println("Jeff Probst: " + spiteUser + " was not voted out during that tribal, so nothing happens.");
                            spiteUser.setScore(-2);
                        }else{
                            System.out.println("Jeff Probst: " + spiteTarget + " won immunity, so the additional votes against them do not count.");
                        }
                    }
                    spiteActive = false;
                    spiteUser = null;
                    spiteTarget = null;
                }
                for (Player player : losers.getTribePlayers()) {
                    if(!spiteActive){
                        if (player.getAdvantages(11) > 0 && ((player.getVotability() > avgVotability(losers.getTribePlayers()) + 2.15 && numPlayers - eliminated.size() > 4) || numPlayers - eliminated.size() == 5) && !idolPlayers.contains(player)) {
                            System.out.println(player + ": I would like to play the Spite Advantage");
                            System.out.println("Jeff Probst: This is the Spite Advantage, if you are voted out, the target of your choice will receive the same count of votes in addition to any votes they receive at the next tribal council.");
                            for(Player vt:tribalCouncilVotes.keySet()){
                                if(player == tribalCouncilVotes.get(vt)){
                                    tgopts.add(vt);
                                }
                            }
                            spiteUser = player;
                            if(tgopts.isEmpty()){
                                System.out.println("Jeff Probst: No one voted for " + player + " so the Spite Advantage fails.");
                            }else {
                                spiteTarget = tgopts.get(r.nextInt(tgopts.size()));
                                System.out.println(player + ": I will be spiting " + spiteTarget);
                                spiteCnt = tgopts.size();
                            }
                            player.setAdvantages(11,player.getAdvantages(11) -1);
                            spiteActive = true;
                        }
                    }
                }
            }
            if (legacy != null && legacyUsable) {
                System.out.println(legacy + ": Jeff, I can only use this at 13 or 6 players left and I think I need it now.");
                System.out.println("Jeff Probst: This is the Legacy Advantage, all votes for " + legacy + " will not count.");
                legacy.setAdvantages(6, Zero);
                legacyIdol = 1;
            }
            if (idolPlayers.contains(hasExileIdol)) {
                exileIdol = 1;
                hasExileIdol = null;
            }
            for(int i = 0; i<losers.getTribePlayers().size();i++) {
                if (players.size()<12 && championAdv && championUser != null && championUser.equals(losers.getTribePlayers().get(i)) && !(split && players.size() == 10)) {
                    ChampRight = champion(championUser, immunityWinner);
                    championUser.setAdvantages(5, losers.getTribePlayers().get(i).getAdvantages(5)-1);
                    championAdv = false;
                    if(ChampRight){
                        safe.add(losers.getTribePlayers().get(i));
                        championUser.setScore(3);
                        championUser = null;
                        break;
                    }else{
                        championUser = null;
                    }
                }
            }
            Player voteName;
            System.out.println("Jeff Probst: I'll read the votes.");
            System.out.println();
            for (int i = 0; i < idolPlayers.size(); i++) {
                if (idolBlock && match!=null && match.equals(idolPlayers.get(i))) {
                    System.out.println("Jeff Probst: There is a idol block in here.");
                    System.out.println("Jeff Probst: It has been played on " + idolPlayers.get(i) + ".");
                    System.out.println("Jeff Probst: All votes for " + idolPlayers.get(i) + " will still count");
                    blockUser.setScore(5);
                    safe.remove(idolPlayers.get(i));
                    idolPlayers.remove(idolPlayers.get(i));
                    i = i - 1;
                }
            }
            HashMap<Integer, Player> second = new HashMap<>();
            ArrayList<Player> ordered = new ArrayList<>(dramatize(voteCnt));
            ArrayList<Player> previous = new ArrayList<>();
            List<Player> countedVotes = new LinkedList<>();
            previous.add(null);
            int votesLeft = ordered.size() - 1;
            for (Player player : ordered) {
                for (int i = 0; i < previous.size(); i++) {
                    if (previous.get(previous.size() - 1) != null && previous.get(previous.size() - 1).equals(player)) {
                        previous.remove(player);
                    }
                }
                voteName = player;
                if(player == swap1 && voteSwapActive){
                    voteName = swap2;
                }else if(player == swap2 && voteSwapActive){
                    voteName = swap1;
                }
                if ((legacyUsable && voteName == legacy) || (idolPlayers.contains(player)) || safe.contains(player)) {
                    if (legacyUsable && voteName == legacy) {
                        System.out.println("Jeff Probst: " + ANSI_RED + voteName + ", does not count." + ANSI_RESET);
                        System.out.println();
                        legacy.setScore(3);
                    } else {
                        System.out.println("Jeff Probst: " + ANSI_RED + voteName + ", does not count." + ANSI_RESET);
                        System.out.println();
                        voteName.setScore(3);
                    }
                } else {
                    votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                    if (votes.get(voteName) > max) {
                        second.put(Integer.valueOf(Integer.valueOf(max)), votedOut.get(Integer.valueOf(Integer.valueOf(max))));
                        max = votes.get(voteName);
                    }
                    if (votes.get(voteName) == max) {
                        votedOut.put(Integer.valueOf(Integer.valueOf(max)), voteName);
                    }
                    boolean sparkle = !Objects.equals(votes.get(previous.get(previous.size() - 1)), votes.get(voteName));
                    if ((votesLeft == 0 && sparkle) || votes.get(voteName) > (voteCnt.size() / 2) || (previous.get(previous.size() - 1) != null && votes.get(previous.get(previous.size() - 1)) != null && votes.get(previous.get(previous.size() - 1)) + votesLeft < votes.get(voteName))) {
                        int placement = numPlayers - players.size() + 1;
                        if (placement == 1) {
                            System.out.println("Jeff Probst: The " + placement + "st player voted out of the game: " + voteName);
                            break;
                        } else if (placement == 2) {
                            System.out.println("Jeff Probst: The " + placement + "nd player voted out of the game: " + voteName);
                            break;
                        } else if (placement == 3) {
                            System.out.println("Jeff Probst: The " + placement + "rd player voted out of the game: " + voteName);
                            break;
                        } else {
                            System.out.println("Jeff Probst: The " + placement + "th player voted out of the game: " + voteName);
                            break;
                        }
                    } else {
                        System.out.println("One vote: " + voteName);
                        StringBuilder voteList = new StringBuilder("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                        ArrayList<Player> used = new ArrayList<>();
                        countedVotes = dramatize(countedVotes);
                        for (Player p: countedVotes){
                            if(p!=voteName && !used.contains(p)){
                                voteList.append(", ").append(votes.get(p)).append(" votes for ").append(p);
                                used.add(p);
                            }
                        }
                        voteList.append(".");
                        Scanner parse = new Scanner(String.valueOf(voteList));
                        parse.useDelimiter("");
                        String parsedD = "";
                        int letters = 0;
                        while (parse.hasNext()) {
                            String nextLet = parse.next();
                            parsedD = parsedD + nextLet;
                            letters++;
                            if (letters >= 140 && nextLet.equals(" ")) {
                                parsedD = parsedD + System.lineSeparator();
                                letters = 0;
                            }
                        }
                        System.out.println(parsedD);
                        System.out.println("Jeff Probst: " + votesLeft + " votes left.");
                        System.out.println();
                    }
                }
                votesLeft--;
                countedVotes.add(player);
                previous.add(player);
            }
            List<Player> listOfKeys = new ArrayList<>();
            if (votes.containsValue(Integer.valueOf(Integer.valueOf(max)))) {
                listOfKeys = new ArrayList<>();
                for (Map.Entry<Player, Integer> entry : votes.entrySet()) {
                    if (entry.getValue().equals(Integer.valueOf(Integer.valueOf(max)))) {
                        listOfKeys.add(entry.getKey());
                        entry.getKey().setCanVote(false);
                    }
                }
            }
            if (listOfKeys.isEmpty()) {
                System.out.println("Jeff Probst: There are no votes for anyone! We must revote.");
                System.out.print("Vote Summary: ");
                HashMap<Player, Integer> voteSum = new HashMap<>();
                for(Player p: voteCnt){
                    voteSum.put(p, Integer.valueOf(voteSum.getOrDefault(p,Zero)+1));
                }
                ArrayList<Integer> numVotes = new ArrayList<Integer>(voteSum.values());
                numVotes.sort(Comparator.reverseOrder());
                System.out.print(numVotes.get(0));
                numVotes.remove(0);
                for(Integer v: numVotes){
                    System.out.print("-"+v);
                }
                tied = " -> None";
                System.out.println();
                HashMap<Player, ArrayList<Player>> summary = new HashMap<>();
                ArrayList<Player> votedpeeps = new ArrayList<>(tribalCouncilVotes.values());
                for(Player votee: votedpeeps) {
                    if (!summary.containsKey(votee)) {
                        ArrayList<Player> play = new ArrayList<>();
                        for (Player voter : tribalCouncilVotes.keySet()) {
                            if (tribalCouncilVotes.get(voter).equals(votee)) {
                                play.add(voter);
                                summary.put(votee, play);
                            }
                        }
                        System.out.print(votee + ": ");
                        for (Player pl : summary.get(votee)) {
                            System.out.print(pl + " | ");
                            if(pl.isInAllianceWith(votee)){
                                pl.setDeceitfulness(5);
                            }
                        }
                        System.out.println();
                    }
                }
                System.out.println();
                System.out.println("Voting Confessionals: ");
                System.out.println();
                for(Player voter: voters) {
                    if (!tribalCouncilVotes.get(voter).equals(null)) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter));
                    int chance = r.nextInt(100);
                    if (chance == 50) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter).getName().toUpperCase() + "!");
                    } else {
                        System.out.println(voter + ": " + votingConfessionals.get(r.nextInt(votingConfessionals.size())));
                    }
                    System.out.println();
                    }
                }
                tribalCouncilVotes.clear();
                countedVotes.clear();
                votes.clear();
                voters.clear();
                previous.clear();
                second.clear();
                votedOut.clear();
                voteCnt.clear();
                if (safe.size() == losers.getTribePlayers().size() - 1) {
                    for (Player player : losers.getTribePlayers()) {
                        if (!safe.contains(player)) {
                            max = 10000;
                            votedOut.put(Integer.valueOf(Integer.valueOf(max)), player);
                            automatic = true;
                            System.out.println("Jeff Probst: " + player + ", you are the only person who can receive votes.");
                            System.out.println("Jeff Probst: By defualt, you have been voted out.");
                        }
                    }
                } else {
                    int bounce = 0;
                    boolean reset = false;
                    for (int i = 0; i < losers.getTribePlayers().size(); i++) {
                        if (safe.contains(losers.getTribePlayers().get(i))) {
                            bounce++;
                        }
                    }
                    if (bounce == losers.getTribePlayers().size()) {
                        System.out.println("Jeff Probst: All of you are safe! Well, I'm going to change that!");
                        System.out.println("Jeff Probst: You are now all vulnerable, including the immunity necklace winner!");
                        reset = true;
                    }
                    if (reset) {
                        for (int i = 0; i < losers.getTribePlayers().size(); i++) {
                            WeightedSpinner<Player> s = new WeightedSpinner<>();
                            for (int j = 0; j < losers.getTribePlayers().size(); j++) {
                                if(losers.getTribePlayers().get(i).getTargets().contains(losers.getTribePlayers().get(j))){
                                    s.add(losers.getTribePlayers().get(j), (losers.getTribePlayers().get(j).getVotability() * 10 *allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum())));
                                }else {
                                    //         alvotes += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum()));
                                    s.add(losers.getTribePlayers().get(j), (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum())));
                                }                            }
                            vote = s.spin();
                            voteCnt.add(vote);
                            tribalCouncilVotes.put(losers.getTribePlayers().get(i), vote);
                            voters.add(losers.getTribePlayers().get(i));
                            vote.setVotesAgainst();
                        }
                    } else {
                        for (int i = 0; i < losers.getTribePlayers().size(); i++) {
                            WeightedSpinner<Player> s = new WeightedSpinner<>();
                            for (int j = 0; j < losers.getTribePlayers().size(); j++) {
                                if (i != j && !safe.contains(losers.getTribePlayers().get(j))) {
                                    if(losers.getTribePlayers().get(i).getTargets().contains(losers.getTribePlayers().get(j))){
                                        s.add(losers.getTribePlayers().get(j), (losers.getTribePlayers().get(j).getVotability() * 10 *allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum())));
                                    }else {
                                        //         alvotes += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum()));
                                        s.add(losers.getTribePlayers().get(j), (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum())));
                                    }                                }
                            }
                            vote = s.spin();
                            voteCnt.add(vote);
                            tribalCouncilVotes.put(losers.getTribePlayers().get(i), vote);
                            voters.add(losers.getTribePlayers().get(i));
                            vote.setVotesAgainst();
                        }
                    }
                    System.out.println();
                    System.out.println("-------------------------");
                    System.out.println();
                    System.out.println("Jeff Probst: I'll read the votes");
                    System.out.println();
                    ArrayList<Player> ordered2 = new ArrayList<>(dramatize(voteCnt));
                    votesLeft = ordered2.size() -1;
                    for (Player player : ordered2) {
                        voteName = player;
                        voteName.setVotesAgainst();
                        System.out.println("One vote: " + voteName);
                        votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                        StringBuilder voteList = new StringBuilder("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                        ArrayList<Player> used = new ArrayList<>();
                        countedVotes = dramatize(countedVotes);
                        for (Player p: countedVotes){
                            if(p!=voteName && !used.contains(p)){
                                voteList.append(", ").append(votes.get(p)).append(" votes for ").append(p);
                                used.add(p);
                            }
                        }
                        voteList.append(".");
                        Scanner parse = new Scanner(String.valueOf(voteList));
                        parse.useDelimiter("");
                        String parsedD = "";
                        int letters = 0;
                        while (parse.hasNext()) {
                            String nextLet = parse.next();
                            parsedD = parsedD + nextLet;
                            letters++;
                            if (letters >= 140 && nextLet.equals(" ")) {
                                parsedD = parsedD + System.lineSeparator();
                                letters = 0;
                            }
                        }
                        System.out.println(parsedD);
                        System.out.println("Jeff Probst: " + votesLeft + " votes left.");
                        votesLeft--;
                        System.out.println();
                        countedVotes.add(player);
                        //System.out.println("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                        if (votes.get(voteName) > max) {
                            second.put(Integer.valueOf(Integer.valueOf(max)), votedOut.get(Integer.valueOf(Integer.valueOf(max))));
                            max = votes.get(voteName);
                        } else {
                            second.put(votes.get(voteName), voteName);
                        }
                        if (votes.get(voteName) == max) {
                            votedOut.put(Integer.valueOf(Integer.valueOf(max)), voteName);
                        }
                    }
                }
            } else if (listOfKeys.size() != 1) {
                System.out.println("Jeff Probst: There is a tie, we must revote!");
                System.out.println();
                System.out.println("Vote Summary: ");
                HashMap<Player, Integer> voteSum = new HashMap<>();
                for (Player p : voteCnt) {
                    voteSum.put(p, Integer.valueOf(voteSum.getOrDefault(p, Zero) + 1));
                }
                ArrayList<Integer> numVotes = new ArrayList<Integer>(voteSum.values());
                numVotes.sort(Comparator.reverseOrder());
                System.out.print(numVotes.get(0));
                tied = " -> " + numVotes.remove(0).toString();
                for (Integer v : numVotes) {
                    System.out.print("-" + v);
                    tied = tied + "-" + v;
                }
                System.out.println();
                HashMap<Player, ArrayList<Player>> summary = new HashMap<>();
                ArrayList<Player> votedpeeps = new ArrayList<>(tribalCouncilVotes.values());
                for (Player votee : votedpeeps) {
                    if (!summary.containsKey(votee)) {
                        ArrayList<Player> play = new ArrayList<>();
                        for (Player voter : tribalCouncilVotes.keySet()) {
                            if (tribalCouncilVotes.get(voter).equals(votee)) {
                                play.add(voter);
                                summary.put(votee, play);
                            }
                        }
                        System.out.print(votee + ": ");
                        for (Player pl : summary.get(votee)) {
                            System.out.print(pl + " | ");
                            if (pl.isInAllianceWith(votee)) {
                                pl.setDeceitfulness(5);
                            }
                        }
                        System.out.println();
                    }
                }
                System.out.println();
                System.out.println("Voting Confessionals: ");
                System.out.println();
                for (Player voter : voters) {
                    if (!tribalCouncilVotes.get(voter).equals(null)) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter));
                        int chance = r.nextInt(100);
                        if (chance == 50) {
                            System.out.println(voter + ": " + tribalCouncilVotes.get(voter).getName().toUpperCase() + "!");
                        } else {
                            System.out.println(voter + ": " + votingConfessionals.get(r.nextInt(votingConfessionals.size())));
                        }
                        System.out.println();
                    }
                }
                tribalCouncilVotes.clear();
                countedVotes.clear();
                votes.clear();
                second.clear();
                votedOut.clear();
                voteCnt.clear();
                boolean noRevoters = true;
                for (int i = 0; i < voters.size(); i++) {
                    if (voters.get(i).CanVote()) {
                        noRevoters = false;
                    }
                    if (listOfKeys.contains(voters.get(i))) {
                        continue;
                    }
                    Player voted = listOfKeys.get(r.nextInt(listOfKeys.size()));
                    while (voted.isImmune() || voted == voters.get(i)) {
                        voted = listOfKeys.get(r.nextInt(listOfKeys.size()));
                    }
                    voteCnt.add(voted);
                    if (!listOfKeys.contains(voters.get(i))) {
                        tribalCouncilVotes.put(voters.get(i), voted);
                    }
                    voted.setVotesAgainst();
                }
                if (noRevoters) {
                    System.out.println("Jeff Probst: No one was eligible to revote and therefore we will go to rocks!");
                    rocks(safe, losers.getTribePlayers());
                    return;
                } else {
                    System.out.println();
                    System.out.println("-------------------------");
                    System.out.println();
                    System.out.println("Jeff Probst: I'll read the votes");
                    System.out.println();
                    ArrayList<Player> ordered2 = new ArrayList<>(dramatize(voteCnt));
                    int votesLeft2 = ordered2.size() - 1;
                    for (Player player : ordered2) {
                        for (int i = 0; i < previous.size(); i++) {
                            if (previous.get(previous.size() - 1) != null && previous.get(previous.size() - 1).equals(player)) {
                                previous.remove(player);
                            }
                        }
                        voteName = player;
                        votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                        if (votes.get(voteName) > max) {
                            second.put(Integer.valueOf(Integer.valueOf(max)), votedOut.get(Integer.valueOf(Integer.valueOf(max))));
                            max = votes.get(voteName);
                        }
                        if (votes.get(voteName) == max) {
                            votedOut.put(Integer.valueOf(Integer.valueOf(max)), voteName);
                        }
                        boolean sparkle = !Objects.equals(votes.get(previous.get(previous.size() - 1)), votes.get(voteName));
                        if ((votesLeft2 == 0 && sparkle) || votes.get(voteName) > losers.getTribePlayers().size() / 2 || (previous.get(previous.size() - 1) != null && votes.get(previous.get(previous.size() - 1)) != null && votes.get(previous.get(previous.size() - 1)) + votesLeft2 < votes.get(voteName))) {
                            int placement = numPlayers - players.size() + 1;
                            if (placement == 1) {
                                System.out.println("Jeff Probst: The " + placement + "st player voted out of the game: " + voteName);
                                break;
                            } else if (placement == 2) {
                                System.out.println("Jeff Probst: The " + placement + "nd player voted out of the game: " + voteName);
                                break;
                            } else if (placement == 3) {
                                System.out.println("Jeff Probst: The " + placement + "rd player voted out of the game: " + voteName);
                                break;
                            } else {
                                System.out.println("Jeff Probst: The " + placement + "th player voted out of the game: " + voteName);
                                break;
                            }
                        } else {
                            System.out.println("One vote: " + voteName);
                            StringBuilder voteList = new StringBuilder("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                            ArrayList<Player> used = new ArrayList<>();
                            countedVotes = dramatize(countedVotes);
                            for (Player p : countedVotes) {
                                if (p != voteName && !used.contains(p) && votes.get(p)!=null) {
                                    voteList.append(", ").append(votes.get(p)).append(" votes for ").append(p);
                                    used.add(p);
                                }
                            }
                            voteList.append(".");
                            Scanner parse = new Scanner(String.valueOf(voteList));
                            parse.useDelimiter("");
                            String parsedD = "";
                            int letters = 0;
                            while (parse.hasNext()) {
                                String nextLet = parse.next();
                                parsedD = parsedD + nextLet;
                                letters++;
                                if (letters >= 140 && nextLet.equals(" ")) {
                                    parsedD = parsedD + System.lineSeparator();
                                    letters = 0;
                                }
                            }
                            System.out.println(parsedD);
                            System.out.println("Jeff Probst: " + votesLeft2 + " votes left.");
                            System.out.println();
                        }
                        countedVotes.add(player);
                        votesLeft2--;
                        previous.add(player);
                    }
                }
            }
            if (votes.containsValue(Integer.valueOf(Integer.valueOf(max)))) {
                listOfKeys = new ArrayList<>();
                for (Map.Entry<Player, Integer> entry : votes.entrySet()) {
                    if (entry.getValue().equals(Integer.valueOf(Integer.valueOf(max)))) {
                        listOfKeys.add(entry.getKey());
                    }
                }
            }
            if (listOfKeys.size() != 1 && !automatic) {
                rocks(listOfKeys, losers.getTribePlayers());
            } else {
                Player gone;
                gone = votedOut.get(Integer.valueOf(Integer.valueOf(max)));
//                boolean boll = true;
//                if (gone.getIdolCount()>0) {
//                    System.out.println(gone.getName() + ": Hey Jeff, I'm not going home tonight.");
//                    System.out.println("Jeff Probst: This is an immunity idol, all votes for " + gone.getName() + " will not count.");
//                    gone.setIdolCount(gone.getIdolCount()-1);
//                    gone.setScore(gone.getScore() + 3);
//                    gone = idoledOut(voteCnt, gone);
////                    int l = 0;
////                    while (gone) {
////                        gone = d.get(l);
////                        l++;
////                        if (l > 5) {
////                            gone = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
////                        }
////                    }
//                    boll = false;
//                /*for(int i = max;i>0;i--){
//                    if(second.get(Integer.valueOf(max)-i)!=gone){
//                        gone = second.get(Integer.valueOf(max)-i);
//                        if(gone!=null && gone!=votedOut.get(Integer.valueOf(max))){
//                            boll=false;
//                            break;
//                        }
//                    }
//                }*/
                tribes.get(0).getTribePlayers().get(numImmune).setImmune(false);
                System.out.println("Jeff Probst: That is enough votes, " + ANSI_RED + gone.getName() + ANSI_RESET + " please bring me your torch.");
                System.out.println("Jeff Probst: " + ANSI_RED + gone + ANSI_RESET + " the tribe has spoken");
                exit(gone);
                votedOutLast=gone;
                if(!RI) {
                    eliminated.add(gone);
                }else if(players.size()<6 && rreturned){
                    eliminated.add(gone);
                }
                for(Alliance a: alliances){
                    a.removeMember(gone);
                }
                if (players.size() == 3) {
                    thirdPlace = gone;
                }
//                if(championUser.equals(gone)){
//                    championUser = null;
//                    championAdv = false;
//                }
                players.remove(gone);
                if (gone == hasExileIdol) {
                    exileIdol = 1;
                    hasExileIdol = null;
                }
                tribes.get(lostTribe).getTribePlayers().remove(gone);
                if (RI && players.size() >= 5) {
                    redemptionPlayers.add(gone);
                } else {
                    /*Scores p = new Scores(gone.getScore(), gone, players.size());
                    totals.add(p);*/
                    if(twoOrThree){
                        if (players.size() <= juryNum+1) {
                            if((RI || EOE) && players.size()>6){
                            }else{
                                jury.add(gone);
                            }
                        }
                    }else{
                        if (players.size() <= juryNum+2) {
                            if((RI || EOE) && players.size()>6){
                            }else{
                                jury.add(gone);
                            }
                        }
                    }
                }
                if (gone.getAdvantages(6) > 0) {
                    gone.setAdvantages(6, Zero);
                    legacy = losers.getTribePlayers().get(r.nextInt(losers.getTribePlayers().size()));
                    System.out.println(gone + ": I trusted the wrong people. I guess I'll give this to " + legacy);
                    legacyUsable = false;
                    legacy.setAdvantages(6, 1);
                    legacyIdol = 0;
                }
                System.out.println();
                System.out.println("Vote Summary: ");
                HashMap<Player, Integer> voteSum = new HashMap<>();
                for(Player p: voteCnt) {
                    voteSum.put(p, Integer.valueOf(voteSum.getOrDefault(p, Zero) + 1));
                }
                ArrayList<Integer> numVotes = new ArrayList<Integer>(voteSum.values());
                numVotes.sort(Comparator.reverseOrder());
                System.out.print(numVotes.get(0));
                String vct = " -> " + numVotes.remove(0).toString();
                for(Integer v: numVotes){
                    vct = vct+"-"+v.toString();
                    System.out.print("-" + v);
                }
                elimvotes.put(gone,tied + vct);
                System.out.println();
                HashMap<Player, ArrayList<Player>> summary = new HashMap<>();
                ArrayList<Player> votedpeeps = new ArrayList<>(tribalCouncilVotes.values());
                for(Player votee: votedpeeps) {
                    if (!summary.containsKey(votee)) {
                        ArrayList<Player> play = new ArrayList<>();
                        for (Player voter : tribalCouncilVotes.keySet()) {
                            if (tribalCouncilVotes.get(voter).equals(votee)) {
                                play.add(voter);
                                summary.put(votee, play);
                            }
                        }
                        System.out.print(votee + ": ");
                        for (Player pl : summary.get(votee)) {
                            System.out.print(pl + " | ");
                            if(pl.isInAllianceWith(votee)){
                                pl.setDeceitfulness(5);
                            }
                        }
                        System.out.println();
                    }
                }
                System.out.println();
                System.out.println("Voting Confessionals: ");
                System.out.println();
                for(Player voter: tribalCouncilVotes.keySet()) {
                    if (!tribalCouncilVotes.get(voter).equals(null)) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter));
                        int chance = r.nextInt(100);
                        if (chance == 50) {
                            System.out.println(voter + ": " + tribalCouncilVotes.get(voter).getName().toUpperCase() + "!");
                        } else {
                            System.out.println(voter + ": " + votingConfessionals.get(r.nextInt(votingConfessionals.size())));
                        }
                        System.out.println();
                    }
                }
                voteAdd(losers.getTribePlayers(), max);
            }
        }
        idolPlayers.clear();
        for(Player player: players){
            player.setImmune(false);
        }
        tribalCouncilVotes.clear();
        votedOut.clear();
        idolBlock = false;
        legacyUsable = false;
        Tribe b = new Tribe();
        b.setColor(tribes.get(lostTribe).getColor());
        b.setTribePlayers(tribes.get(lostTribe).getTribePlayers());
        for(Player p: b.getTribePlayers()){
            p.setCanVote(true);
        }
        b.setTribeName(tribes.get(lostTribe).tribeName());
        tribes.set(lostTribe, b);
        losingTribe = b;
        day = day + 2;
        round = round + 1;
        losesVote.clear();
        voteSwapActive=false;
        automatic = false;
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void splitTribalCouncil(ArrayList<Player> groupA, ArrayList<Player> groupB) throws FileNotFoundException {
        ArrayList<Player> safe = new ArrayList<>();
        String tied = "";
        HashMap<Player, Integer> votes = new HashMap<>();
        ArrayList<Player> voteCnt = new ArrayList<>();
        Player match = new Player();
        Player blockUser = new Player();
        boolean ev = false;
        int max = 0;
        System.out.println("Jeff Probst: Welcome to Tribal Council!");
        System.out.println("Jeff Probst: As you know, it is a split tribal council.");
        for(Player player: groupA){
            if(player.isImmune()){
                System.out.println("Jeff Probst: " +ANSI_GOLD + player+ ANSI_RESET + " has immunity.");
            }
        }
        System.out.println("Jeff Probst: The first group consists of:");
        System.out.println(groupA);
        Player starter = groupA.get(r.nextInt(groupA.size()));
        if (jury.size() > 0) {
            System.out.println("Jeff Probst: Here are the members of the Jury: " + jury.toString());
        }
        System.out.println("Jeff Probst: It's time to vote");
        for (Player player : groupA) {
            if(((player.getVotability() > avgVotability(groupA) + 2.15 && numPlayers - eliminated.size() > 4)||numPlayers - eliminated.size()==5)) {
                boolean played = false;
                int asel = -1;
                if(player.sumAdv()==1){
                    asel = 0;
                }else if(player.sumAdv()>1){
                    asel = r.nextInt(player.sumAdv());
                }
                if (player.getAdvantages(1) > 0 && !played) {
                    if(asel==0) {
                        System.out.println(player + ": I'd like to play my Extra Vote");
                        ev = true;
                        advName.put(player, "Extra");
                        player.setAdvantages(1, player.getAdvantages(1) - 1);
                        player.setScore(3);
                        played=true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(2) > 0 && !played) {
                    if(asel == 0) {
                        Player lostVote = player;
                        while (lostVote == player) {
                            lostVote = groupA.get(r.nextInt(groupA.size()));
                        }
                        System.out.println(player + ": I'd like to play my Vote Blocker on " + lostVote);
                        losesVote.add(lostVote);
                        lostVote.setCanVote(false);
                        player.setAdvantages(2, player.getAdvantages(2) - 1);
                        player.setScore(3);
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(3) > 0 && !played) {
                    if(asel == 0) {
                        Player lostVote = player;
                        while (lostVote == player) {
                            lostVote = groupA.get(r.nextInt(groupA.size()));
                        }
                        System.out.println(player + ": I'd like to play my Steal A Vote on " + lostVote);
                        lostVote.setCanVote(false);
                        losesVote.add(lostVote);
                        ev = true;
                        advName.put(player, "Steal");
                        player.setAdvantages(3, player.getAdvantages(3) - 1);
                        player.setScore(4);
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(4) > 0 && !played) {
                    if(asel==0) {
                        idolBlock = true;
                        blockUser = player;
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(5) > 0 && !played) {
                    if(asel == 0) {
                        championAdv = true;
                        championUser = player;
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(6) > 0 && (players.size() == 13 || players.size() == 6) && !played) {
                    if(asel == 0) {
                        legacy = player;
                        safe.add(legacy);
                        legacyUsable = true;
                        player.setScore(4);
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(7) > 0 && !played) {
                    if(asel == 0) {
                        FRUsable = true;
                        System.out.println(player + ": I would like to use the Force Rocks Advantage!");
                        System.out.println("Jeff Probst: This is the Force Rocks Advantage, " + player + " will now be safe, and the rest of you will draw rocks.");
                        safe.add(player);
                        rocks(safe, losingTribe.getTribePlayers());
                        player.setScore(3);
                        FRUsable = false;
                        round++;
                        player.setAdvantages(7, player.getAdvantages(7) - 1);
                        played = true;
                        return;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(8) > 0 && !played) {
                    if(asel == 0) {
                        voteSwapActive = true;
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(9) > 0 && !played) {
                    if(asel == 0) {
                        System.out.println(player + ": I would like to play my Safety without Power Advantage!");
                        player.setCanVote(false);
                        losesVote.add(player);
                        player.setImmune(true);
                        player.setScore(3);
                        player.setAdvantages(9, player.getAdvantages(9) - 1);
                        System.out.println("Jeff Probst: " + player + " will now leave Tribal Council and not vote.");
                        played = true;
                    } else if (player.getAdvantages(10) > 0 && !played && !safe.contains(player)){
                        FFCoinUsed = true;
                    }
                    else{
                        asel--;
                    }
                }
            }
        }
        while (losesVote.contains(starter)) {
            starter = groupA.get(r.nextInt(groupA.size()));
        }
        System.out.println(starter + " start us off!");
        for (Player player : groupA) {
            if ((player.getVotability() > avgVotability(groupA) + 2.15 || players.size() == player.getIdolsInPossesion() + 4) && player.getIdolsInPossesion() > 0 && !player.isImmune() && numPlayers - eliminated.size() > 4) {
                idolPlayers.add(player);
                safe.add(player);
                player.setIdolsInPossesion(-1);
            }
        }
        int total = 0;
        for (int i = 0; i < groupA.size(); i++) {
            if (!groupA.get(i).isImmune()) {
                total = total + groupA.get(i).getVotability();//* allianceboard.getSpot(0,losers.getTribePlayers().get(i).getNum());
            }
        }
        Player vote = new Player();
        ArrayList<Player> voters = new ArrayList<>();
        for (int i = 0; i < groupA.size(); i++) {

            WeightedSpinner<Player> s = new WeightedSpinner<>();
            for (int j = 0; j < groupA.size(); j++) {
                if (i != j && !groupA.get(j).isImmune() && (!groupA.get(j).isInAllianceWith(groupA.get(i))||groupA.get(i).getTargets().contains(groupA.get(j)))) {
                    if(groupA.get(i).getTargets().contains(groupA.get(j))){
                        s.add(groupA.get(j), (groupA.get(j).getVotability() * 10 *allianceboard.getSpot(groupA.get(j).getNum(), groupA.get(i).getNum())));
                    }else {
                        //         alvotes += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum()));
                        s.add(groupA.get(j), (groupA.get(j).getVotability() * allianceboard.getSpot(groupA.get(j).getNum(), groupA.get(i).getNum())));
                    }
                }
//
            }
            if (groupA.get(i).isImmune()) {
                safe.add(groupA.get(i));
            }
            if(s.size() == 0){
                for (int j = 0; j < groupA.size(); j++) {
                    if (i != j && !groupA.get(j).isImmune()){
                        s.add(groupA.get(j), (groupA.get(j).getVotability() * allianceboard.getSpot(groupA.get(j).getNum(), groupA.get(i).getNum())));
                    }
                }
            }
            vote = s.spin();
            if (groupA.get(i).CanVote()) {
                voteCnt.add(vote);
                allianceboard.setSpot(groupA.get(i).getNum(), vote.getNum(), allianceboard.getSpot(groupA.get(i).getNum(), vote.getNum()) + 2);
                tribalCouncilVotes.put(groupA.get(i), vote);
                voters.add(groupA.get(i));
                vote.setVotesAgainst();
            }
            if (advName.containsKey(groupA.get(i)) && (advName.get(groupA.get(i)).equals("Extra")||advName.get(groupA.get(i)).equals("Steal")) && ev) {
                Player EV = new Player();
                if(tribalCouncilVotes.get(groupA.get(i))!=null) {
                    EV.setName(groupA.get(i).getName() + " EV");
                    vote = tribalCouncilVotes.get(groupA.get(i));
                    tribalCouncilVotes.put(EV, vote);
                    voteCnt.add(vote);
                }else {
                    Player newVote = groupA.get(r.nextInt(groupA.size()));
                    while (newVote.equals(groupA.get(i))) {
                        newVote = groupA.get(r.nextInt(groupA.size()));
                    }
                    EV.setName(groupA.get(i).getName());
                    tribalCouncilVotes.put(EV, newVote);
                    voteCnt.add(newVote);
                }
                advName.remove(groupA.get(i));
            }
        }
        if (idolBlock) {
            match = tribalCouncilVotes.get(blockUser);
        }
        if (players.size() > 4) {
            System.out.println("Jeff Probst: If anybody has an idol or an advantage and would like to play it, now would be the time to do so.");
        }
        ArrayList<Player> nocount = new ArrayList<>();
        if (idolPlayers.size() > 0) {
            for (Player player : idolPlayers) {
                double risk = r.nextDouble();
                System.out.println(player + ": Jeff, I feel like there is a big target on my back and I am not safe tonight.");
                if(risk>=.025) {
                    System.out.println("Jeff Probst: This is an immunity idol, all votes for " + player + " will not count.");
                    player.setScore(5);
                }else{
                    System.out.println("Jeff Probst: This is " + ANSI_RED + "NOT" + ANSI_RESET + " an immunity idol, all votes for " + player + " will still count.");
                    nocount.add(player);
                    Player tricker  = groupA.get(r.nextInt(groupA.size()));
                    while(tricker==player){
                        tricker  = groupA.get(r.nextInt(groupA.size()));
                    }
                    System.out.println(tricker + ": Gotcha!");
                    tricker.setScore(5);
                    tricker.setVotability(10);
                    player.setIdolsInPossesion(-1);
                    safe.remove(player);
                    player.setScore(-5);
                }
            }
            for(Player p: nocount){
                idolPlayers.remove(p);
            }
        }
        if(voteSwapActive){
            for(Player player:groupA) {
                if(player.getAdvantages(8)>0) {
                    System.out.println(player + ": I would like to use the Vote Swap Advantage!");
                    player.setAdvantages(8,player.getAdvantages(8)-1);
                    System.out.println("Jeff Probst: This is the Vote Swap Advantage, " + player + " please select two players for their number of votes to be swapped.");
                    swap1 = groupA.get(r.nextInt(groupA.size()));
                    swap2 = groupA.get(r.nextInt(groupA.size()));
                    while (swap1 == swap2) {
                        swap2 = groupA.get(r.nextInt(groupA.size()));
                    }
                    if(swap1==player){
                        System.out.println(player + ": I will be swapping myself and " + swap2);
                    }else if(swap2==player) {
                        System.out.println(player + ": I will be swapping " + swap1 + " and myself");
                    }else{
                        System.out.println(player + ": I will be swapping " + swap1 + " and " + swap2);
                    }
                }

            }
        }
        if(FFCoinUsed && FFCoin){
            for(Player player:groupA) {
                if(player.getAdvantages(10) > 0 && ((player.getVotability() > avgVotability(groupA) + 2.15 && numPlayers - eliminated.size() > 4)||numPlayers-eliminated.size()==5) && !idolPlayers.contains(player)) {
                    System.out.println(player + ": I would like to play this 50/50 Coin");
                    System.out.println("Jeff Probst: Ok, if the coin lands on what you call, you will be safe.");
                    System.out.println("Jeff Probst: Call it in the air.");
                    int choice = r.nextInt(2);
                    int coinflip = r.nextInt(2);
                    if (choice == 0) {
                        System.out.println(player + ": I call Heads!");
                        if (coinflip == 0) {
                            System.out.println("Jeff Probst: It is Heads! Any votes for " + player + " will NOT count.");
                            safe.add(player);
                            player.setScore(5);
                        } else {
                            System.out.println("Jeff Probst: It is Tails! Any votes for " + player + " will STILL count.");
                            player.setScore(-1);
                        }
                    } else {
                        System.out.println(player + ": I call Tails!");
                        if (coinflip == 0) {
                            System.out.println("Jeff Probst: It is Heads! Any votes for " + player + " will STILL count.");
                            player.setScore(-1);
                        } else {
                            System.out.println("Jeff Probst: It is Tails! Any votes for " + player + " will NOT count.");
                            safe.add(player);
                            player.setScore(5);
                        }
                    }
                    player.setAdvantages(10,player.getAdvantages(10)-1);
                }
            }
            FFCoinUsed = false;
        }
        if(spite) {
            ArrayList<Player> tgopts = new ArrayList<>();
            if(spiteActive && losingTribe.getTribePlayers().contains(spiteTarget)){
                System.out.println("Jeff Probst: The Spite Advantage was secretly played last tribal council.");
                if (spiteUser == votedOutLast && immunityWinner != spiteTarget) {
                    System.out.println("Jeff Probst: " + spiteCnt + " votes will be added to " + spiteTarget + "'s total votes.");
                    for(int sp = 0; sp < spiteCnt; sp++){
                        voteCnt.add(spiteTarget);
                    }
                }else{
                    if(spiteUser != votedOutLast) {
                        System.out.println("Jeff Probst: " + spiteUser + " was not voted out during that tribal, so nothing happens.");
                        spiteUser.setScore(-2);
                    }else{
                        System.out.println("Jeff Probst: " + spiteTarget + " won immunity, so the additional votes against them do not count.");
                    }
                }
                spiteActive = false;
                spiteUser = null;
                spiteTarget = null;
            }
            for (Player player : groupA) {
                if(!spiteActive){
                    if (player.getAdvantages(11) > 0 && ((player.getVotability() > avgVotability(groupA) + 2.15 && numPlayers - eliminated.size() > 4) || numPlayers - eliminated.size() == 5) && !idolPlayers.contains(player)) {
                        System.out.println(player + ": I would like to play the Spite Advantage");
                        System.out.println("Jeff Probst: This is the Spite Advantage, if you are voted out, the target of your choice will receive the same count of votes in addition to any votes they receive at the next tribal council.");
                        for(Player vt:tribalCouncilVotes.keySet()){
                            if(player == tribalCouncilVotes.get(vt)){
                                tgopts.add(vt);
                            }
                        }
                        spiteUser = player;
                        if(tgopts.isEmpty()){
                            System.out.println("Jeff Probst: No one voted for " + player + " so the Spite Advantage fails.");
                        }else {
                            spiteTarget = tgopts.get(r.nextInt(tgopts.size()));
                            System.out.println(player + ": I will be spiting " + spiteTarget);
                            spiteCnt = tgopts.size();
                        }
                        player.setAdvantages(11,player.getAdvantages(11) -1);
                        spiteActive = true;
                    }
                }
            }
        }
        if (legacy != null && legacyUsable) {
            System.out.println(legacy + ": Jeff, I can only use this at 13 or 6 players left and I think I need it now.");
            System.out.println("Jeff Probst: This is the Legacy Advantage, all votes for " + legacy + " will not count.");
            legacy.setAdvantages(6, Zero);
            legacyIdol = 1;
        }
        if (idolPlayers.contains(hasExileIdol)) {
            exileIdol = 1;
            hasExileIdol = null;
        }
        Player voteName;
        System.out.println("Jeff Probst: I'll read the votes.");
        System.out.println();
        for (int i = 0; i < idolPlayers.size(); i++) {
            if (idolBlock && match.equals(idolPlayers.get(i))) {
                System.out.println("Jeff Probst: There is a idol block in here.");
                System.out.println("Jeff Probst: It has been played on " + idolPlayers.get(i) + ".");
                System.out.println("Jeff Probst: All votes for " + idolPlayers.get(i) + " will still count");
                blockUser.setScore(5);
                safe.remove(idolPlayers.get(i));
                idolPlayers.remove(idolPlayers.get(i));
                i = i - 1;
            }
        }
        if(spiteActive && votedOutLast == spiteUser){
            for(int sp = 0; sp < spiteCnt; sp++){
                voteCnt.add(spiteTarget);
                votes.put(spiteTarget,votes.get(spiteTarget)+1);
            }
            spiteActive = false;
            spiteUser = null;
            spiteTarget = null;
        }
        HashMap<Integer, Player> second = new HashMap<>();
        ArrayList<Player> ordered = new ArrayList<>(dramatize(voteCnt));
        ArrayList<Player> previous = new ArrayList<>();
        List<Player> countedVotes = new LinkedList<>();
        previous.add(null);
        int votesLeft = ordered.size() - 1;
        for (Player player : ordered) {
            for (int i = 0; i < previous.size(); i++) {
                if (previous.get(previous.size() - 1) != null && previous.get(previous.size() - 1).equals(player)) {
                    previous.remove(player);
                }
            }
            voteName = player;
            if(player == swap1 && voteSwapActive){
                voteName = swap2;
            }else if(player == swap2 && voteSwapActive){
                voteName = swap1;
            }
            if ((legacyUsable && voteName == legacy) || (idolPlayers.contains(player))) {
                if (legacyUsable && voteName == legacy) {
                    System.out.println("Jeff Probst: " + ANSI_RED + voteName + ", does not count." + ANSI_RESET);
                    legacy.setScore(3);
                    System.out.println();
                } else {
                    System.out.println("Jeff Probst: " + ANSI_RED + voteName + ", does not count." + ANSI_RESET);
                    voteName.setScore(3);
                    System.out.println();
                }
            } else {
                votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                if (votes.get(voteName) > max) {
                    second.put(Integer.valueOf(Integer.valueOf(max)), votedOut.get(Integer.valueOf(Integer.valueOf(max))));
                    max = votes.get(voteName);
                }
                if (votes.get(voteName) == max) {
                    votedOut.put(Integer.valueOf(Integer.valueOf(max)), voteName);
                }
                boolean sparkle = !Objects.equals(votes.get(previous.get(previous.size() - 1)), votes.get(voteName));
                if ((votesLeft == 0 && sparkle) || votes.get(voteName) > groupA.size() / 2 || (previous.get(previous.size() - 1) != null && votes.get(previous.get(previous.size() - 1)) != null && votes.get(previous.get(previous.size() - 1)) + votesLeft < votes.get(voteName))) {
                    int placement = numPlayers - players.size() + 1;
                    if (placement == 1) {
                        System.out.println("Jeff Probst: The " + placement + "st player voted out of the game: " + voteName);
                        break;
                    } else if (placement == 2) {
                        System.out.println("Jeff Probst: The " + placement + "nd player voted out of the game: " + voteName);
                        break;
                    } else if (placement == 3) {
                        System.out.println("Jeff Probst: The " + placement + "rd player voted out of the game: " + voteName);
                        break;
                    } else {
                        System.out.println("Jeff Probst: The " + placement + "th player voted out of the game: " + voteName);
                        break;
                    }
                } else {
                    System.out.println("One vote: " + voteName);
                    StringBuilder voteList = new StringBuilder("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                    ArrayList<Player> used = new ArrayList<>();
                    countedVotes = dramatize(countedVotes);
                    for (Player p: countedVotes){
                        if(p!=voteName && !used.contains(p) && votes.get(p)!=null){
                            voteList.append(", ").append(votes.get(p)).append(" votes for ").append(p);
                            used.add(p);
                        }
                    }
                    voteList.append(".");
                    Scanner parse = new Scanner(String.valueOf(voteList));
                    parse.useDelimiter("");
                    String parsedD = "";
                    int letters = 0;
                    while (parse.hasNext()) {
                        String nextLet = parse.next();
                        parsedD = parsedD + nextLet;
                        letters++;
                        if (letters >= 140 && nextLet.equals(" ")) {
                            parsedD = parsedD + System.lineSeparator();
                            letters = 0;
                        }
                    }
                    System.out.println(parsedD);
                    System.out.println("Jeff Probst: " + votesLeft + " votes left.");
                    System.out.println();
                }
            }
            votesLeft--;
            countedVotes.add(player);
            previous.add(player);
        }
        List<Player> listOfKeys = new ArrayList<>();
        if (votes.containsValue(Integer.valueOf(Integer.valueOf(max)))) {
            listOfKeys = new ArrayList<>();
            for (Map.Entry<Player, Integer> entry : votes.entrySet()) {
                if (entry.getValue().equals(Integer.valueOf(Integer.valueOf(max)))) {
                    listOfKeys.add(entry.getKey());
                }
            }
        }
        if (listOfKeys.size() == 0) {
            System.out.println("Jeff Probst: There are no votes for anyone! We must revote.");
            System.out.println();
            System.out.println("Vote Summary: ");
            HashMap<Player, Integer> voteSum = new HashMap<>();
            for(Player p: voteCnt){
                voteSum.put(p, Integer.valueOf(voteSum.getOrDefault(p,Zero)+1));
            }
            ArrayList<Integer> numVotes = new ArrayList<Integer>(voteSum.values());
            numVotes.sort(Comparator.reverseOrder());
            System.out.print(numVotes.get(0));
            numVotes.remove(0);
            for(Integer v: numVotes){
                System.out.print("-"+v);
            }
            tied = " -> None ";
            System.out.println();
            HashMap<Player, ArrayList<Player>> summary = new HashMap<>();
            ArrayList<Player> votedpeeps = new ArrayList<>(tribalCouncilVotes.values());
            for(Player votee: votedpeeps) {
                if (!summary.containsKey(votee)) {
                    ArrayList<Player> play = new ArrayList<>();
                    for (Player voter : tribalCouncilVotes.keySet()) {
                        if (tribalCouncilVotes.get(voter).equals(votee)) {
                            play.add(voter);
                            summary.put(votee, play);
                        }
                    }
                    System.out.print(votee + ": ");
                    for (Player pl : summary.get(votee)) {
                        System.out.print(pl + " | ");
                        if(pl.isInAllianceWith(votee)){
                            pl.setDeceitfulness(5);
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("Voting Confessionals: ");
            System.out.println();
            for(Player voter: voters) {
                if (!tribalCouncilVotes.get(voter).equals(null)) {
                    System.out.println(voter + ": " + tribalCouncilVotes.get(voter));
                    int chance = r.nextInt(100);
                    if (chance == 50) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter).getName().toUpperCase() + "!");
                    } else {
                        System.out.println(voter + ": " + votingConfessionals.get(r.nextInt(votingConfessionals.size())));
                    }
                    System.out.println();
                }
            }
            tribalCouncilVotes.clear();
            countedVotes.clear();
            votes.clear();
            voters.clear();
            previous.clear();
            second.clear();
            votedOut.clear();
            voteCnt.clear();
            if (safe.size() == groupA.size() - 1) {
                for (Player player : groupA) {
                    if (!safe.contains(player)) {
                        max = 10000;
                        votedOut.put(Integer.valueOf(Integer.valueOf(max)), player);
                        automatic = true;
                        System.out.println("Jeff Probst: " + player + ", you are the only person who can receive votes.");
                        System.out.println("Jeff Probst: By defualt, you have been voted out.");
                    }
                }
            } else {
                int bounce = 0;
                boolean reset = false;
                for (int i = 0; i < groupA.size(); i++) {
                    if (safe.contains(groupA.get(i))) {
                        bounce++;
                    }
                }
                if (bounce == groupA.size()) {
                    System.out.println("Jeff Probst: All of you are safe! Well, I'm going to change that!");
                    System.out.println("Jeff Probst: You are now all vulnerable, including the immunity necklace winner!");
                    reset = true;
                }
                if (reset) {
                    for (int i = 0; i < groupA.size(); i++) {
                        WeightedSpinner<Player> s = new WeightedSpinner<>();
                        for (int j = 0; j < groupA.size(); j++) {
                            if(groupA.get(i).getTargets().contains(groupA.get(j))){
                                s.add(groupA.get(j), (groupA.get(j).getVotability() * 10 *allianceboard.getSpot(groupA.get(j).getNum(), groupA.get(i).getNum())));
                            }else {
                                //         alvotes += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum()));
                                s.add(groupA.get(j), (groupA.get(j).getVotability() * allianceboard.getSpot(groupA.get(j).getNum(), groupA.get(i).getNum())));
                            }
                        }
                        vote = s.spin();
                        voteCnt.add(vote);
                        tribalCouncilVotes.put(groupA.get(i), vote);
                        voters.add(groupA.get(i));
                        vote.setVotesAgainst();
                    }
                } else {
                    for (int i = 0; i < groupA.size(); i++) {
                        WeightedSpinner<Player> s = new WeightedSpinner<>();
                        for (int j = 0; j < groupA.size(); j++) {
                            if (i != j && !safe.contains(groupA.get(j))) {
                                if(groupA.get(i).getTargets().contains(groupA.get(j))){
                                    s.add(groupA.get(j), (groupA.get(j).getVotability() * 10 *allianceboard.getSpot(groupA.get(j).getNum(), groupA.get(i).getNum())));
                                }else {
                                    //         alvotes += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum()));
                                    s.add(groupA.get(j), (groupA.get(j).getVotability() * allianceboard.getSpot(groupA.get(j).getNum(), groupA.get(i).getNum())));
                                }
                            }
                        }
                        vote = s.spin();
                        voteCnt.add(vote);
                        tribalCouncilVotes.put(groupA.get(i), vote);
                        voters.add(groupA.get(i));
                        vote.setVotesAgainst();
                    }
                }
                System.out.println();
                System.out.println("-------------------------");
                System.out.println();
                System.out.println("Jeff Probst: I'll read the votes");
                System.out.println();
                ArrayList<Player> ordered2 = new ArrayList<>(dramatize(voteCnt));
                votesLeft = ordered2.size() - 1;
                for (Player player : ordered2) {
                    voteName = player;
                    voteName.setVotesAgainst();
                    System.out.println("One vote: " + voteName);
                    votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                    StringBuilder voteList = new StringBuilder("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                    ArrayList<Player> used = new ArrayList<>();
                    countedVotes = dramatize(countedVotes);
                    for (Player p: countedVotes){
                        if(p!=voteName && !used.contains(p)&&votes.get(p)!=null){
                            voteList.append(", ").append(votes.get(p)).append(" votes for ").append(p);
                            used.add(p);
                        }
                    }
                    voteList.append(".");
                    Scanner parse = new Scanner(String.valueOf(voteList));
                    parse.useDelimiter("");
                    String parsedD = "";
                    int letters = 0;
                    while (parse.hasNext()) {
                        String nextLet = parse.next();
                        parsedD = parsedD + nextLet;
                        letters++;
                        if (letters >= 140 && nextLet.equals(" ")) {
                            parsedD = parsedD + System.lineSeparator();
                            letters = 0;
                        }
                    }
                    System.out.println(parsedD);
                    System.out.println("Jeff Probst: " + votesLeft + " votes left.");
                    System.out.println();
                    countedVotes.add(player);
                    votesLeft--;
                    //System.out.println("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                    if (votes.get(voteName) > max) {
                        second.put(Integer.valueOf(Integer.valueOf(max)), votedOut.get(Integer.valueOf(Integer.valueOf(max))));
                        max = votes.get(voteName);
                    } else {
                        second.put(votes.get(voteName), voteName);
                    }
                    if (votes.get(voteName) == max) {
                        votedOut.put(Integer.valueOf(Integer.valueOf(max)), voteName);
                    }
                }
            }
        } else if (listOfKeys.size() != 1) {
            System.out.println("Jeff Probst: There is a tie, we must revote!");
            System.out.println();
            System.out.println("Vote Summary: ");
            HashMap<Player, Integer> voteSum = new HashMap<>();
            for(Player p: voteCnt){
                voteSum.put(p, Integer.valueOf(voteSum.getOrDefault(p,Zero)+1));
            }
            ArrayList<Integer> numVotes = new ArrayList<Integer>(voteSum.values());
            numVotes.sort(Comparator.reverseOrder());
            System.out.print(numVotes.get(0));
            tied = " -> " + numVotes.remove(0).toString();
            for(Integer v: numVotes){
                tied = tied + "-" + v.toString();
                System.out.print("-"+v);
            }
            System.out.println();
            HashMap<Player, ArrayList<Player>> summary = new HashMap<>();
            ArrayList<Player> votedpeeps = new ArrayList<>(tribalCouncilVotes.values());
            for(Player votee: votedpeeps) {
                if (!summary.containsKey(votee)) {
                    ArrayList<Player> play = new ArrayList<>();
                    for (Player voter : tribalCouncilVotes.keySet()) {
                        if (tribalCouncilVotes.get(voter).equals(votee)) {
                            play.add(voter);
                            summary.put(votee, play);
                        }
                    }
                    System.out.print(votee + ": ");
                    for (Player pl : summary.get(votee)) {
                        System.out.print(pl + " | ");
                        if(pl.isInAllianceWith(votee)){
                            pl.setDeceitfulness(5);
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("Voting Confessionals: ");
            System.out.println();
            for(Player voter: voters) {
                if (!tribalCouncilVotes.get(voter).equals(null)) {
                    System.out.println(voter + ": " + tribalCouncilVotes.get(voter));
                    int chance = r.nextInt(100);
                    if (chance == 50) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter).getName().toUpperCase() + "!");
                    } else {
                        System.out.println(voter + ": " + votingConfessionals.get(r.nextInt(votingConfessionals.size())));
                    }
                    System.out.println();
                }
            }
            tribalCouncilVotes.clear();
            countedVotes.clear();
            votes.clear();
            second.clear();
            votedOut.clear();
            voteCnt.clear();
            for (int i = 0; i < voters.size(); i++) {
                if (listOfKeys.contains(groupA.get(i))) {
                    continue;
                }
                Player voted = listOfKeys.get(r.nextInt(listOfKeys.size()));
                while (voted.isImmune() || voted == groupA.get(i)) {
                    voted = listOfKeys.get(r.nextInt(listOfKeys.size()));
                }
                voteCnt.add(voted);
                if (!listOfKeys.contains(voters.get(i))) {
                    tribalCouncilVotes.put(voters.get(i), voted);
                }
            }
            System.out.println();
            System.out.println("-------------------------");
            System.out.println();
            System.out.println("Jeff Probst: I'll read the votes");
            System.out.println();
            ArrayList<Player> ordered2 = new ArrayList<>(dramatize(voteCnt));
            votesLeft = ordered2.size() - 1;
            for (Player player : ordered2) {
                for (int i = 0; i < previous.size(); i++) {
                    if (previous.get(previous.size() - 1) != null && previous.get(previous.size() - 1).equals(player)) {
                        previous.remove(player);
                    }
                }
                voteName = player;
                votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                if (votes.get(voteName) > max) {
                    second.put(Integer.valueOf(Integer.valueOf(max)), votedOut.get(Integer.valueOf(Integer.valueOf(max))));
                    max = votes.get(voteName);
                }
                if (votes.get(voteName) == max) {
                    votedOut.put(Integer.valueOf(Integer.valueOf(max)), voteName);
                }
                boolean sparkle = !Objects.equals(votes.get(previous.get(previous.size() - 1)), votes.get(voteName));
                if ((votesLeft == 0 && sparkle) || votes.get(voteName) > groupA.size() / 2 || (previous.get(previous.size() - 1) != null && votes.get(previous.get(previous.size() - 1)) != null && votes.get(previous.get(previous.size() - 1)) + votesLeft < votes.get(voteName))) {
                    int placement = numPlayers - players.size() + 1;
                    if (placement == 1) {
                        System.out.println("Jeff Probst: The " + placement + "st player voted out of the game: " + voteName);
                        break;
                    } else if (placement == 2) {
                        System.out.println("Jeff Probst: The " + placement + "nd player voted out of the game: " + voteName);
                        break;
                    } else if (placement == 3) {
                        System.out.println("Jeff Probst: The " + placement + "rd player voted out of the game: " + voteName);
                        break;
                    } else {
                        System.out.println("Jeff Probst: The " + placement + "th player voted out of the game: " + voteName);
                        break;
                    }
                } else {
                    System.out.println("One vote: " + voteName);
                    StringBuilder voteList = new StringBuilder("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                    ArrayList<Player> used = new ArrayList<>();
                    countedVotes = dramatize(countedVotes);
                    for (Player p: countedVotes){
                        if(p!=voteName && !used.contains(p) && votes.get(p)!=null){
                            voteList.append(", ").append(votes.get(p)).append(" votes for ").append(p);
                            used.add(p);
                        }
                    }
                    voteList.append(".");
                    Scanner parse = new Scanner(String.valueOf(voteList));
                    parse.useDelimiter("");
                    String parsedD = "";
                    int letters = 0;
                    while (parse.hasNext()) {
                        String nextLet = parse.next();
                        parsedD = parsedD + nextLet;
                        letters++;
                        if (letters >= 140 && nextLet.equals(" ")) {
                            parsedD = parsedD + System.lineSeparator();
                            letters = 0;
                        }
                    }
                    System.out.println(parsedD);
                    System.out.println("Jeff Probst: " + votesLeft + " votes left.");
                    System.out.println();
                    //System.out.println("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                }
                countedVotes.add(player);
                votesLeft--;
                previous.add(player);
            }
        }
        if (votes.containsValue(Integer.valueOf(Integer.valueOf(max)))) {
            listOfKeys = new ArrayList<>();
            for (Map.Entry<Player, Integer> entry : votes.entrySet()) {
                if (entry.getValue().equals(Integer.valueOf(Integer.valueOf(max)))) {
                    listOfKeys.add(entry.getKey());
                }
            }
        }
        if (listOfKeys.size() != 1 && !automatic) {
            rocks(listOfKeys, groupA);
        } else {
            Player gone;
            gone = votedOut.get(Integer.valueOf(Integer.valueOf(max)));
            tribes.get(0).getTribePlayers().get(numImmune).setImmune(false);
            System.out.println("Jeff Probst: That is enough votes, " + ANSI_RED + gone.getName() + ANSI_RESET + " please bring me your torch.");
            System.out.println("Jeff Probst: " + ANSI_RED + gone + ANSI_RESET + " the tribe has spoken");
            exit(gone);
            if(!RI) {
                eliminated.add(gone);
            }else if(players.size()<6 && rreturned){
                eliminated.add(gone);
            }
            for(Alliance a: alliances){
                a.removeMember(gone);
            }
            if (players.size() == 3) {
                thirdPlace = gone;
            }
            groupA.remove(gone);
            players.remove(gone);
            if (gone == hasExileIdol) {
                exileIdol = 1;
                hasExileIdol = null;
            }
            if (RI && players.size() >= 5) {
                redemptionPlayers.add(gone);
            } else {
                if(twoOrThree){
                    if (players.size() <= juryNum+1) {
                        if((RI || EOE) && players.size()>6){
                        }else{
                            jury.add(gone);
                        }
                    }
                }else{
                    if (players.size() <= juryNum+2) {
                        if((RI || EOE) && players.size()>6){
                        }else{
                            jury.add(gone);
                        }
                    }
                }
            }
            if (gone.getAdvantages(6) > 0) {
                gone.setAdvantages(6, Zero);
                legacy = groupA.get(r.nextInt(groupA.size()));
                System.out.println(gone + ": I trusted the wrong people. I guess I'll give this to " + legacy);
                legacyUsable = false;
                legacy.setAdvantages(6, 1);
                legacyIdol = 0;
            }
            System.out.println();
            System.out.println("Vote Summary: ");
            HashMap<Player, Integer> voteSum = new HashMap<>();
            for(Player p: voteCnt){
                voteSum.put(p, Integer.valueOf(voteSum.getOrDefault(p,Zero)+1));
            }
            ArrayList<Integer> numVotes = new ArrayList<Integer>(voteSum.values());
            numVotes.sort(Comparator.reverseOrder());
            System.out.print(numVotes.get(0));
            String vct = " -> " + numVotes.remove(0).toString();
            for(Integer v: numVotes){
                System.out.print("-"+v);
                vct = vct + "-" + v.toString();
            }
            elimvotes.put(gone, tied + vct);
            System.out.println();
            HashMap<Player, ArrayList<Player>> summary = new HashMap<>();
            ArrayList<Player> votedpeeps = new ArrayList<>(tribalCouncilVotes.values());
            for(Player votee: votedpeeps) {
                if (!summary.containsKey(votee)) {
                    ArrayList<Player> play = new ArrayList<>();
                    for (Player voter : tribalCouncilVotes.keySet()) {
                        if (tribalCouncilVotes.get(voter).equals(votee)) {
                            play.add(voter);
                            summary.put(votee, play);
                        }
                    }
                    System.out.print(votee + ": ");
                    for (Player pl : summary.get(votee)) {
                        System.out.print(pl + " | ");
                        if(pl.isInAllianceWith(votee)){
                            pl.setDeceitfulness(5);
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("Voting Confessionals: ");
            System.out.println();
            for(Player voter: voters) {
                if (tribalCouncilVotes.get(voter)!=null) {
                    System.out.println(voter + ": " + tribalCouncilVotes.get(voter));
                    int chance = r.nextInt(100);
                    if (chance == 50) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter).getName().toUpperCase() + "!");
                    } else {
                        System.out.println(voter + ": " + votingConfessionals.get(r.nextInt(votingConfessionals.size())));
                    }
                    System.out.println();
                }
            }
            voteAdd(groupA, max);
            }
        ArrayList<Player> remain = new ArrayList<>(groupA);
        idolPlayers.clear();
        tribalCouncilVotes.clear();
        countedVotes.clear();
        votedOut.clear();
        idolBlock =false;
        legacyUsable =false;
        losesVote.clear();
        automatic =false;
        safe = new ArrayList<>();
        votes = new HashMap<>();
        voteCnt = new ArrayList<>();
        match = new Player();
        blockUser = new Player();
        ev = false;
        max = 0;
        System.out.println("Jeff Probst: Welcome to Tribal Council!");
        System.out.println("Jeff Probst: As you know, it is a split tribal council.");
        for(Player player: groupB){
            if(player.isImmune()){
                System.out.println("Jeff Probst: "+ ANSI_GOLD + player + ANSI_RESET + " has immunity.");
            }
        }
        System.out.println("Jeff Probst: The second group consists of:");
        System.out.println(groupB);
        starter = groupB.get(r.nextInt(groupB.size()));
        if (jury.size() > 0) {
            System.out.println("Jeff Probst: Here are the members of the Jury: " + jury.toString());
        }
        System.out.println("Jeff Probst: It's time to vote");
        for (Player player : groupB) {
            boolean played = false;
            int asel = -1;
            if(player.sumAdv()==1){
                asel = 0;
            }else if(player.sumAdv()>1){
                asel = r.nextInt(player.sumAdv());
            }
            if(((player.getVotability() > avgVotability(groupB) + 2.15 && numPlayers - eliminated.size() > 4)||numPlayers-eliminated.size()==5)) {
                if (player.getAdvantages(1) > 0 && !played) {
                    if(asel == 0) {
                        System.out.println(player + ": I'd like to play my Extra Vote");
                        ev = true;
                        advName.put(player, "Extra");
                        player.setAdvantages(1, player.getAdvantages(1) - 1);
                        player.setScore(3);
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(2) > 0 && !played) {
                    if(asel == 0) {
                        Player lostVote = player;
                        while (lostVote == player) {
                            lostVote = groupB.get(r.nextInt(groupB.size()));
                        }
                        losesVote.add(lostVote);
                        System.out.println(player + ": I'd like to play my Vote Blocker on " + lostVote);
                        lostVote.setCanVote(false);
                        player.setAdvantages(2, player.getAdvantages(2) - 1);
                        player.setScore(3);
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(3) > 0 && !played) {
                    if(asel == 0) {
                        Player lostVote = player;
                        while (lostVote == player) {
                            lostVote = groupB.get(r.nextInt(groupB.size()));
                        }
                        losesVote.add(lostVote);
                        System.out.println(player + ": I'd like to play my Steal A Vote on " + lostVote);
                        lostVote.setCanVote(false);
                        ev = true;
                        advName.put(player, "Steal");
                        player.setAdvantages(3, player.getAdvantages(3) - 1);
                        player.setScore(4);
                        played=true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(4) > 0 && !played) {
                    if(asel==0) {
                        idolBlock = true;
                        blockUser = player;
                        played=true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(5) > 0 && !played) {
                    if(asel==0) {
                        championAdv = true;
                        championUser = player;
                        played=true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(6) > 0 && (players.size() == 13 || players.size() == 6) && !played) {
                    if(asel==0) {
                        legacy = player;
                        safe.add(legacy);
                        legacyUsable = true;
                        player.setScore(4);
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(7) > 0 && !played) {
                    if(asel==0) {
                        FRUsable = true;
                        System.out.println(player + ": I would like to use the Force Rocks Advantage!");
                        System.out.println("Jeff Probst: This is the Force Rocks Advantage, " + player + " will now be safe, and the rest of you will draw rocks.");
                        safe.add(player);
                        rocks(safe, losingTribe.getTribePlayers());
                        player.setScore(3);
                        FRUsable = false;
                        round++;
                        player.setAdvantages(7, player.getAdvantages(7) - 1);
                        played=true;
                        return;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(8) > 0 && !played) {
                    if(asel==0) {
                        voteSwapActive = true;
                        played = true;
                    }else{
                        asel--;
                    }
                } else if (player.getAdvantages(9) > 0 && !played) {
                    if(asel==0) {
                        System.out.println(player + ": I would like to play my Safety without Power Advantage!");
                        player.setCanVote(false);
                        losesVote.add(player);
                        player.setImmune(true);
                        player.setScore(3);
                        player.setAdvantages(9, player.getAdvantages(9) - 1);
                        System.out.println("Jeff Probst: " + player + " will now leave Tribal Council and not vote.");
                        played=true;
                    } else if (player.getAdvantages(10) > 0 && !played && !safe.contains(player)){
                        FFCoinUsed = true;
                    }else{
                        asel--;
                    }
                }
            }
        }
        while (losesVote.contains(starter)) {
            starter = groupB.get(r.nextInt(groupB.size()));
        }
        System.out.println(starter + " start us off!");
        for (Player player : groupB) {
            if ((player.getVotability() > avgVotability(groupB) + 2.15 || players.size() == player.getIdolsInPossesion() + 4) && player.getIdolsInPossesion() > 0 && !player.isImmune() && numPlayers - eliminated.size() > 4) {
                idolPlayers.add(player);
                safe.add(player);
                player.setIdolsInPossesion(-1);
            }
        }
        total = 0;
        for (int i = 0; i < groupA.size(); i++) {
            if (!groupA.get(i).isImmune()) {
                total = total + groupB.get(i).getVotability();//* allianceboard.getSpot(0,losers.getTribePlayers().get(i).getNum());
            }
        }
        voters = new ArrayList<>();
        for (int i = 0; i < groupB.size(); i++) {

            WeightedSpinner<Player> s = new WeightedSpinner<>();
            for (int j = 0; j < groupB.size(); j++) {
                if (i != j && !groupB.get(j).isImmune() && (!groupB.get(j).isInAllianceWith(groupB.get(i))||groupB.get(i).getTargets().contains(groupB.get(j)))) {
                    if(groupB.get(i).getTargets().contains(groupB.get(j))){
                        s.add(groupB.get(j), (groupB.get(j).getVotability() * 10 *allianceboard.getSpot(groupB.get(j).getNum(), groupB.get(i).getNum())));
                    }else {
                        //         alvotes += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum()));
                        s.add(groupB.get(j), (groupB.get(j).getVotability() * allianceboard.getSpot(groupB.get(j).getNum(), groupB.get(i).getNum())));
                    }
                }
            }
            if (groupB.get(i).isImmune()) {
                safe.add(groupB.get(i));
            }
            if(s.size() == 0){
                for (int j = 0; j < groupB.size(); j++) {
                    if (i != j && !groupB.get(j).isImmune()){
                        s.add(groupB.get(j), (groupB.get(j).getVotability() * allianceboard.getSpot(groupB.get(j).getNum(), groupB.get(i).getNum())));
                    }
                }
            }
            vote = s.spin();
            if (groupB.get(i).CanVote()) {
                voteCnt.add(vote);
                allianceboard.setSpot(groupB.get(i).getNum(), vote.getNum(), allianceboard.getSpot(groupB.get(i).getNum(), vote.getNum()) + 2);
                tribalCouncilVotes.put(groupB.get(i), vote);
                voters.add(groupB.get(i));
                vote.setVotesAgainst();
            }
            if (advName.containsKey(groupB.get(i)) && (advName.get(groupB.get(i)).equals("Extra")||advName.get(groupB.get(i)).equals("Steal")) && ev) {
                Player EV = new Player();
                if(tribalCouncilVotes.get(groupB.get(i))!=null) {
                    EV.setName(groupB.get(i).getName() + " EV");
                    vote = tribalCouncilVotes.get(groupB.get(i));
                    tribalCouncilVotes.put(EV, vote);
                    voteCnt.add(vote);
                }else{
                    Player newVote = groupB.get(r.nextInt(groupB.size()));
                    while(newVote.equals(groupB.get(i))){
                        newVote = groupB.get(r.nextInt(groupB.size()));
                    }
                    EV.setName(groupB.get(i).getName() + " EV");
                    tribalCouncilVotes.put(EV, newVote);
                    voteCnt.add(newVote);
                }
                advName.remove(groupB.get(i));
            }
        }
        if (idolBlock) {
            match = tribalCouncilVotes.get(blockUser);
        }
        if (players.size() > 4) {
            System.out.println("Jeff Probst: If anybody has an idol or an advantage and would like to play it, now would be the time to do so.");
        }
        nocount.clear();
        if (idolPlayers.size() > 0) {
            for (Player player : idolPlayers) {
                double risk = r.nextDouble();
                System.out.println(player + ": Jeff, I feel like there is a big target on my back and I am not safe tonight.");
                if(risk>=.025) {
                    System.out.println("Jeff Probst: This is an immunity idol, all votes for " + player + " will not count.");
                    player.setScore(5);
                }else{
                    System.out.println("Jeff Probst: This is " + ANSI_RED + "NOT" + ANSI_RESET + " an immunity idol, all votes for " + player + " will still count.");
                    nocount.add(player);
                    Player tricker  = groupB.get(r.nextInt(groupB.size()));
                    while(tricker==player){
                        tricker  = groupB.get(r.nextInt(groupB.size()));
                    }
                    System.out.println(tricker + ": Gotcha!");
                    tricker.setScore(5);
                    tricker.setVotability(10);
                    player.setIdolsInPossesion(-1);
                    safe.remove(player);
                    player.setScore(-5);
                }
            }
            for(Player p: nocount){
                idolPlayers.remove(p);
            }
        }
        if(voteSwapActive){
            for(Player player:groupB) {
                if(player.getAdvantages(8)>0) {
                    System.out.println(player + ": I would like to use the Vote Swap Advantage!");
                    player.setAdvantages(8,player.getAdvantages(8)-1);
                    System.out.println("Jeff Probst: This is the Vote Swap Advantage, " + player + " please select two players for their number of votes to be swapped.");
                    swap1 =groupB.get(r.nextInt(groupB.size()));
                    swap2 = groupB.get(r.nextInt(groupB.size()));
                    while (swap1 == swap2) {
                        swap2 = groupB.get(r.nextInt(groupB.size()));
                    }
                    if(swap1==player){
                        System.out.println(player + ": I will be swapping myself and " + swap2);
                    }else if(swap2==player) {
                        System.out.println(player + ": I will be swapping " + swap1 + " and myself");
                    }else{
                        System.out.println(player + ": I will be swapping " + swap1 + " and " + swap2);
                    }
                }

            }
        }
        if(FFCoinUsed && FFCoin){
            for(Player player:groupB) {
                if(player.getAdvantages(10) > 0 && ((player.getVotability() > avgVotability(groupB) + 2.15 && numPlayers - eliminated.size() > 4)||numPlayers-eliminated.size()==5) && !idolPlayers.contains(player)) {
                    System.out.println(player + ": I would like to play this 50/50 Coin");
                    System.out.println("Jeff Probst: Ok, if the coin lands on what you call, you will be safe.");
                    System.out.println("Jeff Probst: Call it in the air.");
                    int choice = r.nextInt(2);
                    int coinflip = r.nextInt(2);
                    if (choice == 0) {
                        System.out.println(player + ": I call Heads!");
                        if (coinflip == 0) {
                            System.out.println("Jeff Probst: It is Heads! Any votes for " + player + " will NOT count.");
                            safe.add(player);
                            player.setScore(5);
                        } else {
                            System.out.println("Jeff Probst: It is Tails! Any votes for " + player + " will STILL count.");
                            player.setScore(-1);
                        }
                    } else {
                        System.out.println(player + ": I call Tails!");
                        if (coinflip == 0) {
                            System.out.println("Jeff Probst: It is Heads! Any votes for " + player + " will STILL count.");
                            player.setScore(-1);
                        } else {
                            System.out.println("Jeff Probst: It is Tails! Any votes for " + player + " will NOT count.");
                            safe.add(player);
                            player.setScore(5);
                        }
                    }
                    player.setAdvantages(10,player.getAdvantages(10)-1);
                }
            }
            FFCoinUsed = false;
        }
        if(spite) {
            ArrayList<Player> tgopts = new ArrayList<>();
            if(spiteActive && losingTribe.getTribePlayers().contains(spiteTarget)){
                System.out.println("Jeff Probst: The Spite Advantage was secretly played last tribal council.");
                if (spiteUser == votedOutLast && immunityWinner != spiteTarget) {
                    System.out.println("Jeff Probst: " + spiteCnt + " votes will be added to " + spiteTarget + "'s total votes.");
                    for(int sp = 0; sp < spiteCnt; sp++){
                        voteCnt.add(spiteTarget);
                    }
                }else{
                    if(spiteUser != votedOutLast) {
                        System.out.println("Jeff Probst: " + spiteUser + " was not voted out during that tribal, so nothing happens.");
                        spiteUser.setScore(-2);
                    }else{
                        System.out.println("Jeff Probst: " + spiteTarget + " won immunity, so the additional votes against them do not count.");
                    }
                }
                spiteActive = false;
                spiteUser = null;
                spiteTarget = null;
            }
            for (Player player : groupB) {
                if(!spiteActive){
                    if (player.getAdvantages(11) > 0 && ((player.getVotability() > avgVotability(groupB) + 2.15 && numPlayers - eliminated.size() > 4) || numPlayers - eliminated.size() == 5) && !idolPlayers.contains(player)) {
                        System.out.println(player + ": I would like to play the Spite Advantage");
                        System.out.println("Jeff Probst: This is the Spite Advantage, if you are voted out, the target of your choice will receive the same count of votes in addition to any votes they receive at the next tribal council.");
                        for(Player vt:tribalCouncilVotes.keySet()){
                            if(player == tribalCouncilVotes.get(vt)){
                                tgopts.add(vt);
                            }
                        }
                        spiteUser = player;
                        if(tgopts.isEmpty()){
                            System.out.println("Jeff Probst: No one voted for " + player + " so the Spite Advantage fails.");
                        }else {
                            spiteTarget = tgopts.get(r.nextInt(tgopts.size()));
                            System.out.println(player + ": I will be spiting " + spiteTarget);
                            spiteCnt = tgopts.size();
                        }
                        player.setAdvantages(11,player.getAdvantages(11) -1);
                        spiteActive = true;
                    }
                }
            }
        }
        if (legacy != null && legacyUsable) {
            System.out.println(legacy + ": Jeff, I can only use this at 13 or 6 players left and I think I need it now.");
            System.out.println("Jeff Probst: This is the Legacy Advantage, all votes for " + legacy + " will not count.");
            legacy.setAdvantages(6, Zero);
            legacyIdol = 1;
        }
        if (idolPlayers.contains(hasExileIdol)) {
            exileIdol = 1;
            hasExileIdol = null;
        }
        System.out.println("Jeff Probst: I'll read the votes.");
        System.out.println();
        for (int i = 0; i < idolPlayers.size(); i++) {
            if (idolBlock && match.equals(idolPlayers.get(i))) {
                System.out.println("Jeff Probst: There is a idol block in here.");
                System.out.println("Jeff Probst: It has been played on " + idolPlayers.get(i) + ".");
                System.out.println("Jeff Probst: All votes for " + idolPlayers.get(i) + " will still count");
                blockUser.setScore(5);
                safe.remove(idolPlayers.get(i));
                idolPlayers.remove(idolPlayers.get(i));
                i = i - 1;
            }
        }
        if(spiteActive && votedOutLast == spiteUser){
            for(int sp = 0; sp < spiteCnt; sp++){
                voteCnt.add(spiteTarget);
                votes.put(spiteTarget,votes.get(spiteTarget)+1);
            }
            spiteActive = false;
            spiteUser = null;
            spiteTarget = null;
        }
        second = new HashMap<>();
        ordered = new ArrayList<>(dramatize(voteCnt));
        previous = new ArrayList<>();
        previous.add(null);
        votesLeft = ordered.size() - 1;
        for (Player player : ordered) {
            for (int i = 0; i < previous.size(); i++) {
                if (previous.get(previous.size() - 1) != null && previous.get(previous.size() - 1).equals(player)) {
                    previous.remove(player);
                }
            }
            voteName = player;
            if(player == swap1 && voteSwapActive){
                voteName = swap2;
            }else if(player == swap2 && voteSwapActive){
                voteName = swap1;
            }
            if ((legacyUsable && voteName == legacy) || (idolPlayers.contains(player))) {
                if (legacyUsable && voteName == legacy) {
                    System.out.println("Jeff Probst: " + voteName + ", does not count.");
                    legacy.setScore(3);
                    System.out.println();
                } else {
                    System.out.println("Jeff Probst: " + voteName + ", does not count.");
                    voteName.setScore(3);
                    System.out.println();
                }
            } else {
                votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                if (votes.get(voteName) > max) {
                    second.put(Integer.valueOf(Integer.valueOf(max)), votedOut.get(Integer.valueOf(Integer.valueOf(max))));
                    max = votes.get(voteName);
                }
                if (votes.get(voteName) == max) {
                    votedOut.put(Integer.valueOf(Integer.valueOf(max)), voteName);
                }
                boolean sparkle = !Objects.equals(votes.get(previous.get(previous.size() - 1)), votes.get(voteName));
                if ((votesLeft == 0 && sparkle) || votes.get(voteName) > groupB.size() / 2 || (previous.get(previous.size() - 1) != null && votes.get(previous.get(previous.size() - 1)) != null && votes.get(previous.get(previous.size() - 1)) + votesLeft < votes.get(voteName))) {
                    int placement = numPlayers - players.size() + 1;
                    if (placement == 1) {
                        System.out.println("Jeff Probst: The " + placement + "st player voted out of the game: " + voteName);
                        break;
                    } else if (placement == 2) {
                        System.out.println("Jeff Probst: The " + placement + "nd player voted out of the game: " + voteName);
                        break;
                    } else if (placement == 3) {
                        System.out.println("Jeff Probst: The " + placement + "rd player voted out of the game: " + voteName);
                        break;
                    } else {
                        System.out.println("Jeff Probst: The " + placement + "th player voted out of the game: " + voteName);
                        break;
                    }
                } else {
                    System.out.println("One vote: " + voteName);
                    StringBuilder voteList = new StringBuilder("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                    ArrayList<Player> used = new ArrayList<>();
                    countedVotes = dramatize(countedVotes);
                    for (Player p: countedVotes){
                        if(p!=voteName && !used.contains(p) &&  votes.get(p)!=null){
                            voteList.append(", ").append(votes.get(p)).append(" votes for ").append(p);
                            used.add(p);
                        }
                    }
                    voteList.append(".");
                    Scanner parse = new Scanner(String.valueOf(voteList));
                    parse.useDelimiter("");
                    String parsedD = "";
                    int letters = 0;
                    while (parse.hasNext()) {
                        String nextLet = parse.next();
                        parsedD = parsedD + nextLet;
                        letters++;
                        if (letters >= 140 && nextLet.equals(" ")) {
                            parsedD = parsedD + System.lineSeparator();
                            letters = 0;
                        }
                    }
                    System.out.println(parsedD);
                    System.out.println("Jeff Probst: " + votesLeft + " votes left.");
                    System.out.println();
                    //System.out.println("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                }
            }
            countedVotes.add(player);
            votesLeft--;
            previous.add(player);
        }
        listOfKeys = new ArrayList<>();
        if (votes.containsValue(Integer.valueOf(Integer.valueOf(max)))) {
            listOfKeys = new ArrayList<>();
            for (Map.Entry<Player, Integer> entry : votes.entrySet()) {
                if (entry.getValue().equals(Integer.valueOf(Integer.valueOf(max)))) {
                    listOfKeys.add(entry.getKey());
                }
            }
        }
        if (listOfKeys.size() == 0) {
            System.out.println("Jeff Probst: There are no votes for anyone! We must revote.");
            System.out.println();
            System.out.println("Vote Summary: ");
            HashMap<Player, Integer> voteSum = new HashMap<>();
            for(Player p: voteCnt){
                voteSum.put(p, Integer.valueOf(voteSum.getOrDefault(p, Zero)+1));
            }
            ArrayList<Integer> numVotes = new ArrayList<Integer>(voteSum.values());
            numVotes.sort(Comparator.reverseOrder());
            System.out.print(numVotes.get(0));
            numVotes.remove(0);
            for(Integer v: numVotes){
                System.out.print("-"+v);
            }
            tied = " -> None ";
            System.out.println();
            HashMap<Player, ArrayList<Player>> summary = new HashMap<>();
            ArrayList<Player> votedpeeps = new ArrayList<>(tribalCouncilVotes.values());
            for(Player votee: votedpeeps) {
                if (!summary.containsKey(votee)) {
                    ArrayList<Player> play = new ArrayList<>();
                    for (Player voter : tribalCouncilVotes.keySet()) {
                        if (tribalCouncilVotes.get(voter).equals(votee)) {
                            play.add(voter);
                            summary.put(votee, play);
                        }
                    }
                    System.out.print(votee + ": ");
                    for (Player pl : summary.get(votee)) {
                        System.out.print(pl + " | ");
                        if(pl.isInAllianceWith(votee)){
                            pl.setDeceitfulness(5);
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("Voting Confessionals: ");
            System.out.println();
            for(Player voter: voters) {
                if (!tribalCouncilVotes.get(voter).equals(null)) {
                    System.out.println(voter + ": " + tribalCouncilVotes.get(voter));
                    int chance = r.nextInt(100);
                    if (chance == 50) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter).getName().toUpperCase() + "!");
                    } else {
                        System.out.println(voter + ": " + votingConfessionals.get(r.nextInt(votingConfessionals.size())));
                    }
                    System.out.println();
                }
            }
            tribalCouncilVotes.clear();
            countedVotes.clear();
            votes.clear();
            voters.clear();
            previous.clear();
            second.clear();
            votedOut.clear();
            voteCnt.clear();
            if (safe.size() == groupB.size() - 1) {
                for (Player player : groupB) {
                    if (!safe.contains(player)) {
                        max = 10000;
                        votedOut.put(Integer.valueOf(Integer.valueOf(max)), player);
                        automatic = true;
                        System.out.println("Jeff Probst: " + player + ", you are the only person who can receive votes.");
                        System.out.println("Jeff Probst: By defualt, you have been voted out.");
                    }
                }
            } else {
                int bounce = 0;
                boolean reset = false;
                for (int i = 0; i < groupB.size(); i++) {
                    if (safe.contains(groupB.get(i))) {
                        bounce++;
                    }
                }
                if (bounce == groupB.size()) {
                    System.out.println("Jeff Probst: All of you are safe! Well, I'm going to change that!");
                    System.out.println("Jeff Probst: You are now all vulnerable, including the immunity necklace winner!");
                    reset = true;
                }
                if (reset) {
                    for (int i = 0; i < groupB.size(); i++) {
                        WeightedSpinner<Player> s = new WeightedSpinner<>();
                        for (int j = 0; j < groupB.size(); j++) {
                            if(groupB.get(i).getTargets().contains(groupB.get(j))){
                                s.add(groupB.get(j), (groupB.get(j).getVotability() * 10 *allianceboard.getSpot(groupB.get(j).getNum(), groupB.get(i).getNum())));
                            }else {
                                //         alvotes += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum()));
                                s.add(groupB.get(j), (groupB.get(j).getVotability() * allianceboard.getSpot(groupB.get(j).getNum(), groupB.get(i).getNum())));
                            }
                        }
                        vote = s.spin();
                        voteCnt.add(vote);
                        tribalCouncilVotes.put(groupB.get(i), vote);
                        voters.add(groupB.get(i));
                        vote.setVotesAgainst();
                    }
                } else {
                    for (int i = 0; i < groupB.size(); i++) {
                        WeightedSpinner<Player> s = new WeightedSpinner<>();
                        for (int j = 0; j < groupB.size(); j++) {
                            if (i != j && !safe.contains(groupB.get(j))) {
                                if(groupB.get(i).getTargets().contains(groupB.get(j))){
                                    s.add(groupB.get(j), (groupB.get(j).getVotability() * 10 *allianceboard.getSpot(groupB.get(j).getNum(), groupB.get(i).getNum())));
                                }else {
                                    //         alvotes += (losers.getTribePlayers().get(j).getVotability() * allianceboard.getSpot(losers.getTribePlayers().get(j).getNum(), losers.getTribePlayers().get(i).getNum()));
                                    s.add(groupB.get(j), (groupB.get(j).getVotability() * allianceboard.getSpot(groupB.get(j).getNum(), groupB.get(i).getNum())));
                                }
                            }
                        }
                        vote = s.spin();
                        voteCnt.add(vote);
                        tribalCouncilVotes.put(groupB.get(i), vote);
                        voters.add(groupB.get(i));
                        vote.setVotesAgainst();
                    }
                }
                System.out.println();
                System.out.println("-------------------------");
                System.out.println();
                System.out.println("Jeff Probst: I'll read the votes");
                System.out.println();
                ArrayList<Player> ordered2 = new ArrayList<>(dramatize(voteCnt));
                votesLeft = ordered2.size() - 1;
                for (Player player : ordered2) {
                    voteName = player;
                    voteName.setVotesAgainst();
                    System.out.println("One vote: " + voteName);
                    votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                    StringBuilder voteList = new StringBuilder("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                    ArrayList<Player> used = new ArrayList<>();
                    countedVotes = dramatize(countedVotes);
                    for (Player p: countedVotes){
                        if(p!=voteName && !used.contains(p)&&votes.get(p)!=null){
                            voteList.append(", ").append(votes.get(p)).append(" votes for ").append(p);
                            used.add(p);
                        }
                    }
                    voteList.append(".");
                    Scanner parse = new Scanner(String.valueOf(voteList));
                    parse.useDelimiter("");
                    String parsedD = "";
                    int letters = 0;
                    while (parse.hasNext()) {
                        String nextLet = parse.next();
                        parsedD = parsedD + nextLet;
                        letters++;
                        if (letters >= 140 && nextLet.equals(" ")) {
                            parsedD = parsedD + System.lineSeparator();
                            letters = 0;
                        }
                    }
                    System.out.println(parsedD);
                    System.out.println("Jeff Probst: " + votesLeft + " votes left.");
                    System.out.println();
                    countedVotes.add(player);
                    votesLeft--;
                    //System.out.println("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                    if (votes.get(voteName) > max) {
                        second.put(Integer.valueOf(max), votedOut.get(Integer.valueOf(max)));
                        max = votes.get(voteName);
                    } else {
                        second.put(votes.get(voteName), voteName);
                    }
                    if (votes.get(voteName) == max) {
                        votedOut.put(Integer.valueOf(max), voteName);
                    }
                }
            }
        } else if (listOfKeys.size() != 1) {
            System.out.println("Jeff Probst: There is a tie, we must revote!");
            System.out.println();
            System.out.println("Vote Summary: ");
            HashMap<Player, Integer> voteSum = new HashMap<>();
            for(Player p: voteCnt){
                voteSum.put(p, Integer.valueOf(voteSum.getOrDefault(p, Zero)+1));
            }
            ArrayList<Integer> numVotes = new ArrayList<Integer>(voteSum.values());
            numVotes.sort(Comparator.reverseOrder());
            System.out.print(numVotes.get(0));
            tied = " -> " + numVotes.remove(0);
            for(Integer v: numVotes){
                System.out.print("-"+v);
                tied = tied + "-" + v.toString();
            }
            System.out.println();
            HashMap<Player, ArrayList<Player>> summary = new HashMap<>();
            ArrayList<Player> votedpeeps = new ArrayList<>(tribalCouncilVotes.values());
            for(Player votee: votedpeeps) {
                if (!summary.containsKey(votee)) {
                    ArrayList<Player> play = new ArrayList<>();
                    for (Player voter : tribalCouncilVotes.keySet()) {
                        if (tribalCouncilVotes.get(voter).equals(votee)) {
                            play.add(voter);
                            summary.put(votee, play);
                        }
                    }
                    System.out.print(votee + ": ");
                    for (Player pl : summary.get(votee)) {
                        System.out.print(pl + " | ");
                        if(pl.isInAllianceWith(votee)){
                            pl.setDeceitfulness(5);
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("Voting Confessionals: ");
            System.out.println();
            for(Player voter: voters) {
                if (!tribalCouncilVotes.get(voter).equals(null)) {
                    System.out.println(voter + ": " + tribalCouncilVotes.get(voter));
                    int chance = r.nextInt(100);
                    if (chance == 50) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter).getName().toUpperCase() + "!");
                    } else {
                        System.out.println(voter + ": " + votingConfessionals.get(r.nextInt(votingConfessionals.size())));
                    }
                    System.out.println();
                }
            }
            tribalCouncilVotes.clear();
            countedVotes.clear();
            votes.clear();
            second.clear();
            votedOut.clear();
            voteCnt.clear();
            for (int i = 0; i < voters.size(); i++) {
                if (listOfKeys.contains(groupB.get(i))) {
                    continue;
                }
                Player voted = listOfKeys.get(r.nextInt(listOfKeys.size()));
                while (voted.isImmune() || voted == groupB.get(i)) {
                    voted = listOfKeys.get(r.nextInt(listOfKeys.size()));
                }
                voteCnt.add(voted);
                if (!listOfKeys.contains(voters.get(i))) {
                    tribalCouncilVotes.put(voters.get(i), voted);
                }
            }
            System.out.println();
            System.out.println("-------------------------");
            System.out.println();
            System.out.println("Jeff Probst: I'll read the votes");
            System.out.println();
            ArrayList<Player> ordered2 = new ArrayList<>(dramatize(voteCnt));
            votesLeft = ordered2.size() - 1;
            for (Player player : ordered2) {
                for (int i = 0; i < previous.size(); i++) {
                    if (previous.get(previous.size() - 1) != null && previous.get(previous.size() - 1).equals(player)) {
                        previous.remove(player);
                    }
                }
                voteName = player;
                votes.put(voteName, Integer.valueOf(votes.getOrDefault(voteName, Zero) + 1));
                if (votes.get(voteName) > max) {
                    second.put(Integer.valueOf(max), votedOut.get(Integer.valueOf(max)));
                    max = votes.get(voteName);
                }
                if (votes.get(voteName) == max) {
                    votedOut.put(Integer.valueOf(max), voteName);
                }
                boolean sparkle = !Objects.equals(votes.get(previous.get(previous.size() - 1)), votes.get(voteName));
                if ((votesLeft == 0 && sparkle) || votes.get(voteName) > groupB.size() / 2 || (previous.get(previous.size() - 1) != null && votes.get(previous.get(previous.size() - 1)) != null && votes.get(previous.get(previous.size() - 1)) + votesLeft < votes.get(voteName))) {
                    int placement = numPlayers - players.size() + 1;
                    if (placement == 1) {
                        System.out.println("Jeff Probst: The " + placement + "st player voted out of the game: " + voteName);
                        break;
                    } else if (placement == 2) {
                        System.out.println("Jeff Probst: The " + placement + "nd player voted out of the game: " + voteName);
                        break;
                    } else if (placement == 3) {
                        System.out.println("Jeff Probst: The " + placement + "rd player voted out of the game: " + voteName);
                        break;
                    } else {
                        System.out.println("Jeff Probst: The " + placement + "th player voted out of the game: " + voteName);
                        break;
                    }
                } else {
                    System.out.println("One vote: " + voteName);
                    StringBuilder voteList = new StringBuilder("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                    ArrayList<Player> used = new ArrayList<>();
                    countedVotes = dramatize(countedVotes);
                    for (Player p: countedVotes){
                        if(p!=voteName && !used.contains(p)&&votes.get(p)!=null){
                            voteList.append(", ").append(votes.get(p)).append(" votes for ").append(p);
                            used.add(p);
                        }
                    }
                    voteList.append(".");
                    Scanner parse = new Scanner(String.valueOf(voteList));
                    parse.useDelimiter("");
                    String parsedD = "";
                    int letters = 0;
                    while (parse.hasNext()) {
                        String nextLet = parse.next();
                        parsedD = parsedD + nextLet;
                        letters++;
                        if (letters >= 140 && nextLet.equals(" ")) {
                            parsedD = parsedD + System.lineSeparator();
                            letters = 0;
                        }
                    }
                    System.out.println(parsedD);
                    System.out.println("Jeff Probst: " + votesLeft + " votes left.");
                    System.out.println();
                    //System.out.println("Jeff Probst: That is " + votes.get(voteName) + " votes for " + voteName);
                }
                countedVotes.add(player);
                votesLeft--;
                previous.add(player);
            }
        }
        if (votes.containsValue(Integer.valueOf(max))) {
            listOfKeys = new ArrayList<>();
            for (Map.Entry<Player, Integer> entry : votes.entrySet()) {
                if (entry.getValue().equals(Integer.valueOf(Integer.valueOf(max)))) {
                    listOfKeys.add(entry.getKey());
                }
            }
        }
        if (listOfKeys.size() != 1 && !automatic) {
            rocks(listOfKeys, groupB);
        } else {
            Player gone;
            gone = votedOut.get(Integer.valueOf(max));
            tribes.get(0).getTribePlayers().get(numImmune).setImmune(false);
            System.out.println("Jeff Probst: That is enough votes, " + ANSI_RED + gone.getName() + ANSI_RESET + " please bring me your torch.");
            System.out.println("Jeff Probst: " + ANSI_RED + gone + ANSI_RESET + " the tribe has spoken");
            exit(gone);
            if(!RI) {
                eliminated.add(gone);
            }else if(players.size()<6 && rreturned){
                eliminated.add(gone);
            }
            for(Alliance a: alliances){
                a.removeMember(gone);
            }
            if (players.size() == 3) {
                thirdPlace = gone;
            }
            groupB.remove(gone);
            players.remove(gone);
            if (gone == hasExileIdol) {
                exileIdol = 1;
                hasExileIdol = null;
            }
            if (RI && players.size() >= 5) {
                redemptionPlayers.add(gone);
            } else {
                if(twoOrThree){
                    if (players.size() <= juryNum+1) {
                        if((RI || EOE) && players.size()>6){
                        }else{
                            jury.add(gone);
                        }
                    }
                }else{
                    if (players.size() <= juryNum+2) {
                        if((RI || EOE) && players.size()>6){
                        }else{
                            jury.add(gone);
                        }
                    }
                }
            }
            if (gone.getAdvantages(6) > 0) {
                gone.setAdvantages(6, Zero);
                legacy = groupB.get(r.nextInt(groupB.size()));
                System.out.println(gone + ": I trusted the wrong people. I guess I'll give this to " + legacy);
                legacyUsable = false;
                legacy.setAdvantages(6, 1);
                legacyIdol = 0;
            }
            System.out.println();
            System.out.println("Vote Summary:");
            HashMap<Player, Integer> voteSum = new HashMap<>();
            for(Player p: voteCnt){
                voteSum.put(p, Integer.valueOf(voteSum.getOrDefault(p, Zero)+1));
            }
            ArrayList<Integer> numVotes = new ArrayList<Integer>(voteSum.values());
            numVotes.sort(Comparator.reverseOrder());
            System.out.print(numVotes.get(0));
            String vct = " -> " + numVotes.remove(0);
            for(Integer v: numVotes){
                System.out.print("-"+v);
                vct = vct + "-" + v.toString();
            }
            elimvotes.put(gone,tied + vct);
            System.out.println();
            HashMap<Player, ArrayList<Player>> summary = new HashMap<>();
            ArrayList<Player> votedpeeps = new ArrayList<>(tribalCouncilVotes.values());
            for(Player votee: votedpeeps) {
                if (!summary.containsKey(votee)) {
                    ArrayList<Player> play = new ArrayList<>();
                    for (Player voter : tribalCouncilVotes.keySet()) {
                        if (tribalCouncilVotes.get(voter).equals(votee)) {
                            play.add(voter);
                            summary.put(votee, play);
                        }
                    }
                    System.out.print(votee + ": ");
                    for (Player pl : summary.get(votee)) {
                        System.out.print(pl + " | ");
                        if(pl.isInAllianceWith(votee)){
                            pl.setDeceitfulness(5);
                        }
                    }
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("Voting Confessionals: ");
            System.out.println();
            for(Player voter: voters) {
                if (tribalCouncilVotes.get(voter)!=null) {
                    System.out.println(voter + ": " + tribalCouncilVotes.get(voter));
                    int chance = r.nextInt(100);
                    if (chance == 50) {
                        System.out.println(voter + ": " + tribalCouncilVotes.get(voter).getName().toUpperCase() + "!");
                    } else {
                        System.out.println(voter + ": " + votingConfessionals.get(r.nextInt(votingConfessionals.size())));
                    }
                    System.out.println();
                }
            }
            voteAdd(groupB, max);
        }
        remain.addAll(groupB);
        for(Player p: remain){
            p.setCanVote(true);
        }
        idolPlayers.clear();
        tribalCouncilVotes.clear();
        votedOut.clear();
        idolBlock =false;
        legacyUsable =false;
        losesVote.clear();
        automatic =false;
        Tribe b = new Tribe();
        b.setColor(tribes.get(lostTribe).getColor());
        b.setTribePlayers(remain);
        b.setTribeName(tribes.get(lostTribe).tribeName());
        tribes.set(lostTribe,b);
        losingTribe=b;
        day =day +2;
        round =round +1;
    }

    public void juryfix(){
        jury.clear();
        for (int i = eliminated.size()-1; i > eliminated.size()-juryNum-1; i--) {
            jury.add(eliminated.get(i));
        }
    }

    public void splitImmunityChallenge() throws FileNotFoundException {
        System.out.println("Jeff Probst: Welcome to the Day " + day +" Immunity Challenge!");
        System.out.println("Jeff Probst: Today I will split you into two teams because TWO immunity necklaces are on the line.");
        System.out.println("Jeff Probst: That means, two groups going to Tribal Council, two people being voted out.");
        System.out.println("Jeff Probst: High stakes for the game.");
        Tribe a = new Tribe();
        Tribe b = new Tribe();
        for(int i = 0; i<players.size();i++){
            int team = r.nextInt(2);
            if(team==0){
                if(b.getTribePlayers()==null || b.getTribePlayers().size()!=players.size()/2){
                    b.addPlayer(players.get(i));
                }else{
                    a.addPlayer(players.get(i));}
            }else {
                if (a.getTribePlayers()==null || a.getTribePlayers().size() != players.size() / 2) {
                    a.addPlayer(players.get(i));
                } else {
                    b.addPlayer(players.get(i));
                }
            }
        }
        System.out.println("Jeff Probst: Group 1 is: " + a.getTribePlayers());
        System.out.println("Jeff Probst: Group 2 is: " + b.getTribePlayers());
        String chalName = indChallenges.get(Integer.valueOf(r.nextInt(indChallenges.size())));
        System.out.println("Jeff Probst: Today's challenge is called " + chalName);
        System.out.println("Jeff Probst: Here is a description of the challenge: ");
        System.out.println(indChalDescriptions.get(chalName));
        String type = indChalTypes.get(chalName);
        int total = 0;
        switch (type) {
            case "Balance" -> {
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).getBalance()> 1) {
                        total = a.getTribePlayers().get(i).getBalance()+ total;
                    }else{
                        total = total + 1;
                    }
                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).getBalance() > 1) {
                        picker = a.getTribePlayers().get(i).getBalance() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Strength" -> {
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).getStrength() > 1) {
                        total = a.getTribePlayers().get(i).getStrength()+ total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).getStrength()> 1) {
                        picker = a.getTribePlayers().get(i).getStrength() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Strategy" -> {
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).getStrategy() > 1) {
                        total = a.getTribePlayers().get(i).getStrategy() + total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).getStrategy() > 1) {
                        picker = a.getTribePlayers().get(i).getStrategy() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Balance and Strength" -> {
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).balnstrength() > 1) {
                        total = a.getTribePlayers().get(i).balnstrength() + total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).balnstrength() > 1) {
                        picker = a.getTribePlayers().get(i).balnstrength() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Balance and Strategy" -> {
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).balnstrat() > 1) {
                        total = a.getTribePlayers().get(i).balnstrat() + total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).balnstrat() > 1) {
                        picker = a.getTribePlayers().get(i).balnstrat() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Strength and Strategy" -> {
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).strengthnstrat() > 1) {
                        total = a.getTribePlayers().get(i).strengthnstrat() + total;
                    }else{
                        total = total + 1;
                    }                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < a.getTribePlayers().size(); i++) {
                    if(a.getTribePlayers().get(i).strengthnstrat() > 1) {
                        picker = a.getTribePlayers().get(i).strengthnstrat() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
        }
        Player immunity = a.getTribePlayers().get(numImmune);
        System.out.println("Jeff Probst: " + ANSI_GOLD + immunity + ANSI_RESET + " wins immunity and is safe tonight at Tribal Council");
        a.getTribePlayers().get(numImmune).setImmune(true);
        a.getTribePlayers().get(numImmune).setThreatlvl(3);
        a.getTribePlayers().get(numImmune).setScore(4);
        a.getTribePlayers().get(numImmune).setImmWins(1);
        immunityWins.put(a.getTribePlayers().get(numImmune), Integer.valueOf(a.getTribePlayers().get(numImmune).getImmWins()));
        total = 0;
        switch (type) {
            case "Balance" -> {
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).getBalance() > 1) {
                        total = b.getTribePlayers().get(i).getBalance() + total;
                    }else{
                        total = total + 1;
                    }
                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).getBalance() > 1) {
                        picker = b.getTribePlayers().get(i).getBalance() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Strength" -> {
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).getStrength() > 1) {
                        total = b.getTribePlayers().get(i).getStrength() + total;
                    }else{
                        total = total + 1;
                    }
                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).getStrength() > 1) {
                        picker = b.getTribePlayers().get(i).getStrength() + picker;
                    }else{
                        picker = picker + 1;
                    }                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Strategy" -> {
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).getStrategy() > 1) {
                        total = b.getTribePlayers().get(i).getStrategy() + total;
                    }else{
                        total = total + 1;
                    }
                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).getStrategy() > 1) {
                        picker = b.getTribePlayers().get(i).getStrategy() + picker;
                    }else{
                        picker = picker + 1;
                    }
                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Balance and Strength" -> {
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).balnstrength() > 1) {
                        total = b.getTribePlayers().get(i).balnstrength() + total;
                    }else{
                        total = total + 1;
                    }
                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).balnstrength() > 1) {
                        picker = b.getTribePlayers().get(i).balnstrength() + picker;
                    }else{
                        picker = picker + 1;
                    }
                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Balance and Strategy" -> {
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).balnstrat()> 1) {
                        total = b.getTribePlayers().get(i).balnstrat() + total;
                    }else{
                        total = total + 1;
                    }
                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).balnstrat() > 1) {
                        picker = b.getTribePlayers().get(i).balnstrat() + picker;
                    }else{
                        picker = picker + 1;
                    }
                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
            case "Strength and Strategy" -> {
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).strengthnstrat() > 1) {
                        total = b.getTribePlayers().get(i).strengthnstrat() + total;
                    }else{
                        total = total + 1;
                    }
                }
                int immune = r.nextInt(total);
                int picker = 0;
                for (int i = 0; i < b.getTribePlayers().size(); i++) {
                    if(b.getTribePlayers().get(i).strengthnstrat() > 1) {
                        picker = b.getTribePlayers().get(i).strengthnstrat() + picker;
                    }else{
                        picker = picker + 1;
                    }
                    if (immune < picker) {
                        numImmune = i;
                        break;
                    }
                }
            }
        }
        immunity = b.getTribePlayers().get(numImmune);
        System.out.println("Jeff Probst: " + ANSI_GOLD + immunity + ANSI_RESET + " wins immunity and is safe tonight at Tribal Council");
        b.getTribePlayers().get(numImmune).setImmune(true);
        b.getTribePlayers().get(numImmune).setThreatlvl(3);
        b.getTribePlayers().get(numImmune).setScore(4);
        b.getTribePlayers().get(numImmune).setImmWins(1);
        immunityWins.put(b.getTribePlayers().get(numImmune), Integer.valueOf(b.getTribePlayers().get(numImmune).getImmWins()));
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        splitTribalCouncil(a.getTribePlayers(),b.getTribePlayers());
    }

    public List<Player> dramatize(List<Player> players) {
        Map<Player, Integer> counts = new HashMap<>();
        for (Player p : players) {
            counts.put(p, Integer.valueOf(counts.getOrDefault(p, Zero) + 1));
        }
        List<Player> ordered = new LinkedList<>();
        while (ordered.size() < players.size()) {
            for (Player p : counts.keySet()) {
                if (counts.get(p) > 0) {
                    ordered.add(p);
                    counts.put(p, Integer.valueOf(counts.get(p)-1));
                }
            }
        }
        return ordered;
    }

    public boolean isIdol() {
        return Idol;
    }

    public void setR() {
        r = new Random();
    }

    public boolean isAdvantages() {
        return advantages;
    }

    public void setAdvantages(boolean advantages) {
        Game.advantages = advantages;
    }

    public Player getSecretActwinner() {
        return secretActwinner;
    }

    public void setSecretActwinner(Player secretActwinner) {
        this.secretActwinner = secretActwinner;
    }

    public void setPlayers(ArrayList<Player> players) {
        Game.players = players;
    }

    public void survivorAuction(){
        System.out.println("Jeff Probst: Welcome to the Survivor Auction!");
        int bid = 20;
        HashMap<Player, Integer> amounts = new HashMap<>();
        for (Player player : players) {
            amounts.put(player, Integer.valueOf(500));
        }
        System.out.println("Jeff Probst: You have each been given $500 to bid on various items.");
        System.out.println("Jeff Probst: We will start of the bidding with a covered item, starting bid $20");
        bid(20, amounts);
        System.out.println("Jeff Probst: You won a stick!");
        for(int i = 0; i<4; i++){
            int pick = r.nextInt(15);
            if(pick<5){
                System.out.println("Jeff Probst: Here is another hidden item, starting bid $20");
                int covered = r.nextInt(11);
                if(covered==0){
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won a bowl of river water!");
                }else if(covered==1){
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won a secret advantage!");
                    advantageFind(secretActwinner, true);
                }else if(covered==2) {
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won a night on a yacht!");
                }else if(covered==3) {
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won skewered chicken hearts!");
                }else if(covered==4){
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won some yummy guacamole with a whole chicken quesadilla!");
                }else if(covered==5){
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won a brand new car!");
                }else if(covered==6){
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won bat soup!");
                }else if(covered==7 && Idol){
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won a hidden immunity idol!");
                    idolFind(secretActwinner,true);
                }else if(covered==8){
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won a steak dinner with mashed potatoes!");
                }else if(covered==9) {
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won the opportunity to send someone to Exile Island!");
                    exiled = tribes.get(0).getTribePlayers().get(r.nextInt(tribes.get(0).getTribePlayers().size()));
                    while(exiled.equals(secretActwinner)){
                        exiled = tribes.get(0).getTribePlayers().get(r.nextInt(tribes.get(0).getTribePlayers().size()));
                    }
                    System.out.println(secretActwinner+ ": I choose " + exiled + " to go to Exile Island.");
                    AuctExile = true;
                }else{
                    bid(20, amounts);
                    System.out.println("Jeff Probst: You won absolutely NOTHING!");
                }
            }
            else if(pick<6){
                System.out.println("Jeff Probst: Two ice cold beers, starting bid $40");
                bid(40, amounts);
            }
            else if(pick<7){
                System.out.println("Jeff Probst: Chips and Salsa! Starting bid $40");
                bid(40, amounts);
            }
            else if(pick<8){
                System.out.println("Jeff Probst: Here is a nice, juicy cheeseburger and fries, with a Mountain Dew!, starting bid $60");
                bid(60, amounts);
            }
            else if(pick<9){
                System.out.println("Jeff Probst: Letters from home! Starting Bid $100");
                bid(100, amounts);
                secretActwinner.setStrength(secretActwinner.getStrength()+5);
                secretActwinner.setStrategy(secretActwinner.getStrategy()+5);
                secretActwinner.setBalance(secretActwinner.getBalance()+5);
            }
            else if(pick<10){
                System.out.println("Jeff Probst: A steaming hot bath! Starting Bid $120");
                bid(120, amounts);
            }
            else if(pick<11){
                System.out.println("Jeff Probst: Cookies for the tribe! Starting Bid $80");
                bid(80, amounts);
            }
            else if(pick<12){
                System.out.println("Jeff Probst: Grilled Cheese and Tomato Soup! Starting Bid $20");
                bid(20, amounts);
            }
            else if(pick<13){
                System.out.println("Jeff Probst: BLT with all the fixin's! Starting Bid $60");
                bid(60, amounts);
            }
            else if(pick<14 && advantages){
                System.out.println("Jeff Probst: An advantage in this game! Starting Bid $200");
                bid(200, amounts);
                advantageFind(secretActwinner, true);
            }
            else {
                System.out.println("Jeff Probst: A true Survivor Classic: Chocolate CAKE! Starting Bid $60");
                bid(60, amounts);
            }
        }
    }

    public void bid(int start, HashMap<Player, Integer> amounts){
        int numBids = r.nextInt(players.size())+1;
        Player bidder = new Player();
        int currBid = start;
        Player previousBid = new Player();
        for(int i = 0; i<numBids;i++){
            bidder = players.get(r.nextInt(players.size()));
            while(bidder==previousBid || amounts.get(bidder)<currBid){
                bidder = players.get(r.nextInt(players.size()));
            }if(i==0){
                System.out.println(bidder + ": $" + currBid + "!");
                previousBid = bidder;
            }else {
                currBid+= 20;
                System.out.println(bidder + ": $" + (currBid) + "!");
                previousBid = bidder;
            }
            if(i == numBids-1){
                System.out.println("Jeff Probst: Going once, going twice, sold to " + bidder + " for $" + currBid);
                amounts.replace(bidder, Integer.valueOf(amounts.get(bidder) - currBid));
            }
            secretActwinner = bidder;
        }
    }

    public boolean medevacOrQuit(){
        int rand = r.nextInt(100);
        if(rand<2){
            System.out.println("---QUIT ALERT---");
            Player quit = players.get(r.nextInt(players.size()));
            System.out.println(quit + ": I have decided that I will now quit the game, goodluck everyone.");
            System.out.println("Jeff Probst: As per your wishes, get out.");
            players.remove(quit);
            eliminated.add(quit);
            for(Alliance a: alliances){
                a.removeMember(quit);
            }
            elimvotes.put(quit, " -> Quit");
            for(Tribe tribe : tribes){
                tribe.getTribePlayers().remove(quit);
            }
            if(quit.getAdvantages(4)>0){
                championAdv =false;
            }
            quitB = true;
            votedOutLast = quit;
            System.out.println();
            System.out.println("-------------------------");
            System.out.println();
            day=day+2;
            round++;
            return true;
        }else if(rand<4){
            System.out.println("---MEDICAL EVACUATION---");
            Player medevac = players.get(r.nextInt(players.size()));
            System.out.println("Jeff Probst: " + medevac+ " Unfortunately the medics have decided that you can no longer continue in this game.");
            System.out.println("Jeff probst: Please say goodbye to your tribe");
            players.remove(medevac);
            eliminated.add(medevac);
            for(Alliance a: alliances){
                a.removeMember(medevac);
            }
            elimvotes.put(medevac, " -> Medevac");
            for(Tribe tribe : tribes){
                tribe.getTribePlayers().remove(medevac);
            }
            if(medevac.getAdvantages(4)>0){
                championAdv=false;
            }
            if(players.size()<=juryNum+1){
                jury.add(medevac);
            }
            votedOutLast=medevac;
            medB = true;
            System.out.println();
            System.out.println("-------------------------");
            System.out.println();
            day=day+2;
            round++;
            return true;
        }
        return false;
    }

    public Player idoledOut(ArrayList<Player> b, Player immune){
        HashMap<Player, Integer> a = new HashMap<>();
        int max = 0;
        Player next = new Player();
        for(Player player: b){
            if(player!=immune){
                a.put(player, Integer.valueOf(a.getOrDefault(player, Zero)+1));
                if(a.get(player)>max){
                    max = a.get(player);
                    next = player;
                }
            }
            if(next==null){
                next = b.get(r.nextInt(b.size()));
                break;
            }
        }
        return next;
    }

    public void edgeOfExtinction(){
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        System.out.println("Jeff Probst: The Edge of Extinction Challenge is here!");
        Player returner;
        returner = eliminated.get(r.nextInt(eliminated.size()));
        players.add(returner);
        tribes.get(0).getTribePlayers().add(returner);
        jury.remove(returner);
        /*for(int i = 0; i<totals.size();i++) {
            if(totals.get(i).getPlayer().getName().equals(returner.getName())){
                totals.remove(totals.get(i));
            }
        }*/
        eliminated.remove(returner);
        returner.setScore(3);
        System.out.println("Jeff Probst: " + returner + ", you will return to the game with new life!");
        System.out.println("Jeff Probst: Here is a buff and a 50/50 Coin, good luck!");
        returner.setAdvantages(10,returner.getAdvantages(10)+1);
        EdgeReturnCnt++;
        if(EdgeReturnCnt==2){
            for(int i = eliminated.size()-1; i>eliminated.size()-juryNum+1; i--){
                jury.add(eliminated.get(i));
            }
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void campLife() throws IOException {
        System.out.println();
        System.out.println("-----Camp Life-----");
        for(Tribe tribe:tribes) {
            ArrayList<Player> pcopy = new ArrayList<>(players);
            Player p1 = pcopy.get(r.nextInt(pcopy.size()));
            while(!tribe.getTribePlayers().contains(p1)) {
                p1 = pcopy.get(r.nextInt(pcopy.size()));
            }
            pcopy.remove(p1);
            Player p2 = pcopy.get(r.nextInt(pcopy.size()));
            while(!tribe.getTribePlayers().contains(p2)) {
                p2 = pcopy.get(r.nextInt(pcopy.size()));
            }
            pcopy.remove(p2);
            FileInputStream campLife = new FileInputStream("CampLife.txt");
            Scanner sc = new Scanner(campLife);
            ArrayList<String> phrases = new ArrayList<>();
            HashMap<String, Integer> values = new HashMap<>();
            System.out.println(tribe.getColor() + tribe.tribeName() + " Tribe" + ANSI_RESET + ":");
            int i = 0;
            while (sc.hasNextLine()) {
                phrases.add(sc.nextLine());
                values.put(phrases.get(i), Integer.valueOf(sc.nextLine()));
                i++;
            }
            String phrase = phrases.get(r.nextInt(phrases.size()));
            System.out.println(p1 + " " + phrase);
            if (p1.getVotability() + (values.get(phrase)) > 0) {
                p1.setVotability(values.get(phrase));
            }
            phrases.remove(phrase);
            phrase = phrases.get(r.nextInt(phrases.size()));
            System.out.println(p2 + " " + phrase);
            if (p2.getVotability() + (values.get(phrase)) > 0) {
                p2.setVotability(values.get(phrase));
            }
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void totals(){
        for(int i = eliminated.size()-1; i>=0;i--) {
            Scores p = new Scores(eliminated.get(i).getScore(), eliminated.get(i),numPlayers-i);
            totals.add(p);
        }
        System.out.println("Point Totals: ");
        for (Scores total : totals) {
            System.out.println(total);
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void tribeSwap(int swaptype){
        for(Tribe t: tribes){
            for(Player p: t.getTribePlayers()){
                p.setLastTribeColor(t.getColor());
            }
        }
        if(swaptype==2) {
            if(round%4== 0 && round!=0) {
                ArrayList<Player> function = new ArrayList<>();
                ArrayList<Player> swap = new ArrayList<>(players);
                System.out.println("Jeff Probst: Everybody drop your buffs, we are switching up tribes!");
                for (int i = 0; i < 2; i++) {
                    ArrayList<Player> tribeMems = new ArrayList<>();
                    for (int j = 0; j < players.size() / 2; j++) {
                        int swapCnt = r.nextInt(swap.size());
                        Player nextPlayer = swap.get(swapCnt);
                        while (function.contains(nextPlayer)) {
                            swapCnt = r.nextInt(swap.size());
                            nextPlayer = swap.get(swapCnt);
                        }
                        tribeMems.add(nextPlayer);
                        function.add(nextPlayer);
                        swap.remove(swapCnt);
                    }
                    Tribe a = new Tribe(tribes.get(i).tribeName(), tribeMems, Zero);
                    a.setColor(tribes.get(i).getColor());
                    tribes.set(i, a);
                    System.out.println("Jeff Probst: Here is the new " + tribes.get(i).getColor() + tribes.get(i).tribeName() + ANSI_RESET + " Tribe!");
                    System.out.println(tribes.get(i).toString());
                }
                if (numTribes > 2) {
                    for (int i = 2; i < tribes.size(); i++) {
                        tribes.remove(i);
                        numTribes--;
                        i--;
                    }
                }
                System.out.println();
                System.out.println("-------------------------");
                System.out.println();
            }
        }else if (swaptype==3){
            if(players.size() == 15) {
                ArrayList<Player> function = new ArrayList<>();
                ArrayList<Player> swap = new ArrayList<>(players);
                System.out.println("Jeff Probst: Everybody drop your buffs, we are switching up tribes!");
                ArrayList<Player> pie = new ArrayList<>();
                Tribe three = new Tribe("Tika",pie , Zero);
                numTribes=1+numTribes;
                three.setColor(colors.get(r.nextInt(colors.size())));
                colors.remove(three.getColor());

                tribes.add(three);
                for (int i = 0; i < 3; i++) {
                    ArrayList<Player> tribeMems = new ArrayList<>();
                    for (int j = 0; j < players.size() / 3; j++) {
                        int swapCnt = r.nextInt(swap.size());
                        Player nextPlayer = swap.get(swapCnt);
                        while (function.contains(nextPlayer)) {
                            swapCnt = r.nextInt(swap.size());
                            nextPlayer = swap.get(swapCnt);
                        }
                        tribeMems.add(nextPlayer);
                        function.add(nextPlayer);
                        swap.remove(swapCnt);
                    }
                    Tribe a = new Tribe(tribes.get(i).tribeName(), tribeMems, Zero);
                    a.setColor(tribes.get(i).getColor());
                    tribes.set(i, a);
                    System.out.println("Jeff Probst: Here is the new " + tribes.get(i).getColor() + tribes.get(i).tribeName() + ANSI_RESET + " Tribe!");
                    System.out.println(tribes.get(i).toString());
                }
                if (numTribes > 3) {
                    for (int i = 3; i < tribes.size(); i++) {
                        tribes.remove(i);
                        numTribes--;
                        i--;
                    }
                }
                System.out.println();
                System.out.println("-------------------------");
                System.out.println();
            }
        }else if(round%4 == 0 && swaptype==1){
            System.out.println("Jeff Probst: " + numTribes + " players will be sent on a journey.");
            ArrayList<Player> switchedalready = new ArrayList<>();
            for(int i = 0; i<tribes.size();i++){
                Player switched = tribes.get(i).getTribePlayers().get(r.nextInt(tribes.get(i).getTribePlayers().size()));
                while(switchedalready.contains(switched)){
                    switched = tribes.get(i).getTribePlayers().get(r.nextInt(tribes.get(i).getTribePlayers().size()));
                }
                try{
                    tribes.get(i+1).addPlayer(switched);
                    switchedalready.add(switched);
                    tribes.get(i).getTribePlayers().remove(switched);
                    System.out.println("Jeff Probst: " +switched + " will now be on the " +tribes.get(i+1).getColor() +tribes.get(i+1).tribeName()+ ANSI_RESET + " tribe!");
                }catch(IndexOutOfBoundsException e){
                    tribes.get(0).addPlayer(switched);
                    switchedalready.add(switched);
                    tribes.get(i).getTribePlayers().remove(switched);
                    System.out.println("Jeff Probst: " +switched + " will now be on the " + tribes.get(0).getColor() +tribes.get(0).tribeName()+ ANSI_RESET + " tribe!");

                }
            }
        }
    }

    public void voteAdd(ArrayList<Player> losers, int max){
        for(int i = 0; i<losers.size(); i++){
            if(tribalCouncilVotes.containsKey(losers.get(i))){
                if(tribalCouncilVotes.get(losers.get(i)).equals(votedOut.get(Integer.valueOf(max)))){
                    losers.get(i).setScore(2);
                }
            }
        }
    }

    public boolean sizeCheck(){
        for(int i = 0; i<tribes.size();i++){
            if(tribes.get(i).getTribePlayers().size()<2){
                return true;
            }
        }
        return false;
    }

    public void dissolve(){
        Tribe dissolved = new Tribe();
        for(int i = 0; i<tribes.size();i++){
            if(tribes.get(i).getTribePlayers().size()<2){
                System.out.println("Jeff Probst: The " + tribes.get(i).getColor()+tribes.get(i).tribeName() + ANSI_RESET + " Tribe is now down to one member, they will be designated to another tribe.");
                dissolved = tribes.get(i);
                break;
            }
        }
        tribes.remove(dissolved);
        numTribes-=1;
        Tribe newTribe;
        Player switched = dissolved.getTribePlayers().get(0);
        if(tribes.size()>1) {
            newTribe = tribes.get(r.nextInt(tribes.size()));
        }else{
            newTribe = tribes.get(0);
        }
        System.out.println("Jeff Probst: " + switched + " will now join the " + newTribe.getColor() + newTribe.tribeName() +ANSI_RESET + " Tribe!");
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void votesAgainst(){
        System.out.println("Votes Against");
        for(Player player: playerStorage){
            System.out.println(player + ": " + player.getVotesAgainst());
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void rocks(List<Player> listOfKeys,ArrayList<Player >losers) throws FileNotFoundException {
        if(!FRUsable){
            System.out.println("Jeff Probst: We have tied again, therefore we go to rocks!");
        }
        votedOut.clear();
        ArrayList<Player> safe = new ArrayList<>(listOfKeys);
        ArrayList<Player> risk = new ArrayList<>();
        for (int i = 0; i < losers.size(); i++) {
            if (safe.contains(losers.get(i)) || losers.get(i).isImmune()) {
            } else {
                risk.add(losers.get(i));
            }
        }
        /*int jump = 0;
        for (Player player : losers) {
            if (player.isImmune()) {
                jump++;
            }
        }*/
        int rocks = risk.size();
        int whiteRock = 0;
        if(rocks==0){
            System.out.println("Jeff Probst: Everyone is safe, we must change that!");
            whiteRock = r.nextInt(losers.size());
            Player dang = losers.get(whiteRock);
            votedOut.put(Integer.valueOf(whiteRock), dang);
        }else {
            whiteRock = r.nextInt(rocks);
            votedOut.put(Integer.valueOf(whiteRock), risk.get(whiteRock));
        }
            /* if(jump == listOfKeys.size()){
                votedOut.put(whiteRock,listOfKeys.get(r.nextInt(listOfKeys.size())));
            }else{
                votedOut.put(whiteRock,losers.get(whiteRock));
                while(votedOut.get(Integer.valueOf(whiteRock)).equals(listOfKeys.get(0)) || votedOut.get(Integer.valueOf(whiteRock)).equals(listOfKeys.get(1)) || votedOut.get(Integer.valueOf(whiteRock)).equals(losers.get(numImmune))){
                    whiteRock = r.nextInt(rocks);
                    votedOut.put(whiteRock,losers.get(whiteRock));
                }
            }*/
        System.out.println("Jeff Probst: " + ANSI_PURPLE + votedOut.get(Integer.valueOf(whiteRock)) + ANSI_RESET + ", you have drawn the purple rock!");
        System.out.println("Jeff Probst: Please bring me your torch");
        System.out.println("Jeff Probst: The game has spoken");
        exit(votedOut.get(Integer.valueOf(whiteRock)));
        elimvotes.put(votedOut.get(Integer.valueOf(whiteRock))," -> Rocks");
        for (Player loser : losers) {
            loser.setImmune(false);
        }
        if (votedOut.get(Integer.valueOf(whiteRock)).equals(championUser)) {
            championAdv = false;
            championUser = null;
        }
        for(Tribe tribe: tribes) {
            tribe.getTribePlayers().remove(votedOut.get(Integer.valueOf(whiteRock)));
        }
        players.remove(votedOut.get(Integer.valueOf(whiteRock)));
        losingTribe.getTribePlayers().remove(votedOut.get(Integer.valueOf(whiteRock)));
        if(!RI) {
            eliminated.add(votedOut.get(Integer.valueOf(whiteRock)));
        }else if(players.size()<6 && rreturned){
            eliminated.add(votedOut.get(Integer.valueOf(whiteRock)));
        }
        for(Alliance a: alliances){
            a.removeMember(votedOut.get(Integer.valueOf(whiteRock)));
        }
        if (twoOrThree) {
            if (players.size() <= juryNum+1) {
                if(RI || EOE || ETM){
                    if(players.size()<=juryNum) {
                        jury.add(votedOut.get(Integer.valueOf(whiteRock)));
                    }
                }else{
                    jury.add(votedOut.get(Integer.valueOf(whiteRock)));
                }
            }
        } else {
            if (players.size() <= juryNum+2) {
                if(RI || EOE || ETM){
                    if(players.size()<=juryNum+1) {
                        jury.add(votedOut.get(Integer.valueOf(whiteRock)));
                    }
                }else{
                    jury.add(votedOut.get(Integer.valueOf(whiteRock)));
                }
            }
        }
        for(Player p: losers){
            p.setCanVote(true);
        }
        if (votedOut.get(Integer.valueOf(whiteRock)) == legacy) {
            legacy = players.get(r.nextInt(players.size()));
            System.out.println(votedOut.get(Integer.valueOf(whiteRock)) + ": The game just wasn't on my side, I guess I'll give this to " + legacy);
        }
        if (votedOut.get(Integer.valueOf(whiteRock)) == hasExileIdol) {
            exileIdol = 1;
        }
        if (RI) {
            redemptionPlayers.add(votedOut.get(Integer.valueOf(whiteRock)));
        }
        if (merged) {
            tribes.get(0).getTribePlayers().remove(votedOut.get(Integer.valueOf(whiteRock)));
        } else {
            tribes.get(lostTribe).getTribePlayers().remove(votedOut.get(Integer.valueOf(whiteRock)));
        }
        votedOutLast = votedOut.get(Integer.valueOf(whiteRock));
        idolPlayers.clear();
        tribalCouncilVotes.clear();
        votedOut.clear();
        idolBlock = false;
        legacyUsable = false;
        FRUsable = false;
        Tribe b = new Tribe();
        b.setColor(tribes.get(lostTribe).getColor());
        b.setTribePlayers(tribes.get(lostTribe).getTribePlayers());
        b.setTribeName(tribes.get(lostTribe).tribeName());
        tribes.set(lostTribe, b);
        losingTribe = b;
        losesVote.clear();
        automatic = false;
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void rewardChallenge(){
        System.out.println("Jeff Probst: Come on in guys!");
        Tribe winner = new Tribe();
        System.out.println("Jeff Probst: Welcome to the Day: " +(day+1) + " reward challenge!");
        if(players.size()>11&&!merged){
            for (int i = 0; i < numTribes; i++) {
                if (!tribes.get(i).equals(losingTribe) && day >= 2 && tribes.contains(losingTribe)) {
                    System.out.println("Jeff Probst: " + tribes.get(i).getColor() + tribes.get(i).tribeName() + ANSI_RESET + " take a look at the new " + losingTribe.getColor() + losingTribe.tribeName() + ANSI_RESET + " Tribe");
                }
            }
            if(quitB){
                System.out.println("Jeff Probst: " + ANSI_RED + votedOutLast + ANSI_RESET + " decided to quit the game.");
                quitB = false;
            }else if(medB){
                System.out.println("Jeff Probst: " + ANSI_RED + votedOutLast + ANSI_RESET + " was medically evacuated from the game.");
                medB = false;
            }else if(votedOutLast!=null || day>2) {
                System.out.println("Jeff Probst: " + ANSI_RED + votedOutLast + ANSI_RESET+" voted out at the last Tribal Council.");
            }
            String chalName = teamChallenges.get(Integer.valueOf(r.nextInt(teamChallenges.size())));
            System.out.println("Jeff Probst: Today's challenge is called " + chalName);
            System.out.println("Jeff Probst: Here is a description of the challenge: ");
            System.out.println(teamChalDescriptions.get(chalName));
            System.out.println("Jeff Probst: Survivors Ready? Go!");
            int all = 0;
            for(Tribe tribe : tribes){
                all = all + tribe.boostNStats();
            }
            int pick = r.nextInt(all);
            int semi = 0;
            int count = 0;
            boolean wins = false;
            for(Tribe tribe : tribes){
                semi = semi + tribe.boostNStats();
                if(semi>pick){
                    wins = true;
                }
                if(wins && count==0){
                    winner = tribe;
                    System.out.println("Jeff Probst: " + tribe.getColor()+tribe.tribeName()+ ANSI_RESET +" wins reward!");
                    for(Player player : tribe.getTribePlayers()){
                        player.setBalance(player.getBalance()+1);
                        player.setStrategy(player.getStrategy()+1);
                        player.setStrength(player.getStrength()+1);
                    }
                    count++;
                }else{
                        /*System.out.println("Jeff Probst: " + tribe.getColor()+tribe.tribeName()+ ANSI_RESET +", one of you will now be sent to Exile Island!");
                        System.out.println("Jeff Probst: You will return to your tribe before the next immunity challenge.");
                        exiled = tribe.getTribePlayers().get(r.nextInt(tribe.getTribePlayers().size()));
                        System.out.println("Jeff Probst: Looks like " + exiled + " will be sent to Exile Island.");
                        System.out.println("Jeff Probst: Grab your stuff and head out!");*/
                    System.out.println("Jeff Probst: Sorry " + tribe.getColor() + tribe.tribeName()+ ANSI_RESET + " I've got nothing for you, head back to camp.");
                }
            }
            if(EI){
                System.out.println("Jeff Probst: One of the losers will now be sent to Exile Island!");
                System.out.println("Jeff Probst: You will return to your tribe before the next immunity challenge.");
                exiled = players.get(r.nextInt(players.size()));
                while(winner.getTribePlayers().contains(exiled)){
                    exiled = players.get(r.nextInt(players.size()));
                }
                System.out.println("Jeff Probst: Looks like " + exiled + " will be sent to Exile Island.");
                System.out.println("Jeff Probst: Grab your stuff and head out!");
            }
        }
        else if(players.size()%2!=0){
            System.out.println("Jeff Probst: Today will be an individual reward challenge.");
            String chalName= indChallenges.get(Integer.valueOf(r.nextInt(indChallenges.size())));
            System.out.println("Jeff Probst: Today's challenge is called " + chalName);
            System.out.println("Jeff Probst: Here is the challenge description:");
            System.out.println(indChalDescriptions.get(chalName));
            Player dubs = players.get(r.nextInt(players.size()));
            System.out.println("Jeff Probst: Congrats "+ ANSI_LIME + dubs+ ANSI_RESET + " you have won reward!");
            dubs.setRewardWins();
            dubs.setThreatlvl(2);
            rewardWins.put(dubs, Integer.valueOf(dubs.getRewardWins()));
            if(EI) {
                System.out.println("Jeff Probst: Now pick someone to head to Exile Island.");
                System.out.println("Jeff Probst: They will be there until the next immunity challenge.");
                exiled = players.get(r.nextInt(players.size()));
                while(exiled==dubs){
                    exiled = players.get(r.nextInt(players.size()));
                }
                System.out.println(dubs + ": I pick " +exiled);
                System.out.println("Jeff Probst: Alright " + exiled + ", head to Exile Island!");
            }
            dubs.setBalance(dubs.getBalance()+1);
            dubs.setStrategy(dubs.getStrategy()+1);
            dubs.setStrength(dubs.getStrength()+1);
        }else{
            System.out.println("Jeff Probst: Today I will split you into two teams");
            Tribe a = new Tribe();
            Tribe b = new Tribe();
            for(int i = 0; i<players.size();i++){
                int team = r.nextInt(2);
                if(team==0){
                    if(b.getTribePlayers()==null || b.getTribePlayers().size()!=players.size()/2){
                        b.addPlayer(players.get(i));
                    }else{
                    a.addPlayer(players.get(i));}
                }else {
                    if (a.getTribePlayers()==null || a.getTribePlayers().size() != players.size() / 2) {
                        a.addPlayer(players.get(i));
                    } else {
                        b.addPlayer(players.get(i));
                    }
                }
            }
            System.out.println("Jeff Probst: Team 1 is: " + a.getTribePlayers());
            System.out.println("Jeff Probst: Team 2 is: " + b.getTribePlayers());
            String chalName = teamChallenges.get(Integer.valueOf(r.nextInt(teamChallenges.size())));
            System.out.println("Jeff Probst: Today's challenge is called " + chalName);
            System.out.println("Jeff Probst: Here is a description of the challenge: ");
            System.out.println(teamChalDescriptions.get(chalName));
            int wins = r.nextInt(a.boostNStats()+b.boostNStats());
            if(wins<a.boostNStats()){
                System.out.println("Jeff Probst: Team 1 wins!");
                System.out.println("Jeff Probst: " + ANSI_LIME + a.getTribePlayers().toString()+ ANSI_RESET + " enjoy your reward!");
                if(EI){
                    System.out.println("Jeff Probst: Now pick someone to head to Exile Island.");
                    System.out.println("Jeff Probst: They will be there until the next immunity challenge.");
                    exiled = b.getTribePlayers().get(r.nextInt(b.getTribePlayers().size()));
                    System.out.println(a.getTribePlayers().get(r.nextInt(a.getTribePlayers().size())) + ": We select " + exiled +".");
                    System.out.println("Jeff Probst: Alright " + exiled + ", head to Exile Island!");
                }
                System.out.println("Jeff Probst: " + b.getTribePlayers().toString() + " I've got nothing for you, head back to camp");
                for(Player player : a.getTribePlayers()){
                    player.setRewardWins();
                    rewardWins.put(player, Integer.valueOf(player.getRewardWins()));
                    player.setBalance(player.getBalance()+1);
                    player.setStrategy(player.getStrategy()+1);
                    player.setStrength(player.getStrength()+1);
                }
            }else{
                System.out.println("Jeff Probst: Team 2 wins!");
                System.out.println("Jeff Probst: " + ANSI_LIME + b.getTribePlayers().toString() + ANSI_RESET + " enjoy your reward!");
                if(EI){
                    System.out.println("Jeff Probst: Now pick someone to head to Exile Island.");
                    System.out.println("Jeff Probst: They will be there until the next immunity challenge.");
                    exiled = a.getTribePlayers().get(r.nextInt(a.getTribePlayers().size()));
                    System.out.println(b.getTribePlayers().get(r.nextInt(b.getTribePlayers().size())) + ": We select " + exiled +".");
                    System.out.println("Jeff Probst: Alright " + exiled + ", head to Exile Island!");
                }
                System.out.println("Jeff Probst: " + a.getTribePlayers().toString() + " I've got nothing for you, head back to camp");
                for(Player player : b.getTribePlayers()){
                    player.setRewardWins();
                    rewardWins.put(player, Integer.valueOf(player.getRewardWins()));
                    player.setBalance(player.getBalance()+1);
                    player.setStrategy(player.getStrategy()+1);
                    player.setStrength(player.getStrength()+1);
                }
            }
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void exit(Player p) throws FileNotFoundException {
        FileInputStream exits = new FileInputStream("exitInterviews.txt");
        Scanner exitInts = new Scanner(exits);
        ArrayList<String> exitStmts = new ArrayList<>();
        while(exitInts.hasNextLine()){
            exitStmts.add(exitInts.nextLine());
        }
        System.out.println(p.getName() + ": " + exitStmts.get(r.nextInt(exitStmts.size())));
    }

    public void exileIsland() throws FileNotFoundException {
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        System.out.println(exiled + " is on Exile Island.");
        confessional(exiled);
        int find = r.nextInt(6)+1;
        if(find == 4 && exileIdol == 1){
            System.out.println(exiled + " found the Exile Idol!");
            exiled.setIdolsInPossesion(1);
            exileIdol = 0;
            hasExileIdol = exiled;
        }
        exiled.setBalance(exiled.getBalance()-1);
        exiled.setStrategy(exiled.getStrategy()-1);
        exiled.setStrength(exiled.getStrength()-1);
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void redemptionIsland(){
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        System.out.println("Jeff Probst: Welcome to Redemption Island, where you get a chance to get back into this game!");
        System.out.println("Jeff Probst: The Rules are simple, win and stay in, lose and you will be eliminated.");
        System.out.print("Jeff Probst: Today's participants will be: ");
        for(int i =0; i< redemptionPlayers.size()-1;i++){
            System.out.print(redemptionPlayers.get(i).getName() + ", ");
        }
        System.out.print("and " + redemptionPlayers.get(redemptionPlayers.size()-1).getName());
        System.out.println();
        if(redemptionPlayers.size()==4){
            System.out.println("Jeff Probst: Because we have 4 from the double tribal, today's elimination will be 2v2.");
            Player loser = redemptionPlayers.get(r.nextInt(redemptionPlayers.size()));
            redemptionPlayers.remove(loser);
            System.out.println("Jeff Probst: " + loser + " you have lost Redemption Island.");
            System.out.println("Jeff Probst: Place your buff in the fire and head out.");
            players.remove(loser);
            if (players.size() <= juryNum) {
                jury.add(loser);
            }
        }
        Player loser = redemptionPlayers.get(r.nextInt(redemptionPlayers.size()));
        redemptionPlayers.remove(loser);
        System.out.println("Jeff Probst: " + loser + " you have lost Redemption Island.");
        System.out.println("Jeff Probst: Place your buff in the fire and head out.");
        System.out.print("Jeff Probst: Congratulations ");
        for (Player redemptionPlayer : redemptionPlayers) {
            System.out.print(redemptionPlayer.getName());
            System.out.print(", ");
        }
        System.out.println("Head back to Redemption Island for your next duel!");
        //Scores p = new Scores(loser.getScore(), loser, players.size());
        //totals.add(p);
        players.remove(loser);
        eliminated.add(loser);
        if (players.size() <= juryNum+2) {
            jury.add(loser);
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void fanFavorite(){
        Player max = new Player();
        for(Scores score: totals){
            if(score.getScore()> max.getScore()){
                max = score.getPlayer();
            }
        }
        System.out.println("The Fan Favorite is: " + max);
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void RedemptionReturn(){
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        System.out.println("Jeff Probst: This is your big chance to return to the game!");
        System.out.println("Jeff Probst: Goodluck!");
        Player winner = redemptionPlayers.get(r.nextInt(redemptionPlayers.size()));
        System.out.println("Jeff Probst: " + winner + " you will return to the game!");
        System.out.println("Jeff Probst: Here is a buff and a 50/50 Coin to welcome you back!");
        winner.setAdvantages(10,winner.getAdvantages(10)+1);
        if(players.size()>6) {
            System.out.println("Jeff Probst: For the rest of you, head back to Redemption Island for your next opponent.");
        }else{
            System.out.println("Jeff Probst: Unfortunately for the remaining two, your game will come short.");
            jury.addAll(redemptionPlayers);
            eliminated.addAll(redemptionPlayers);
        }
        jury.remove(winner);
        redemptionPlayers.remove(winner);
        players.add(winner);
        rreturned = true;
        eliminated.remove(winner);
        tribes.get(0).getTribePlayers().add(winner);
        winner.setCanVote(true);
        for(int i = 0; i<totals.size();i++) {
            if(totals.get(i).getPlayer().getName().equals(winner.getName())){
                totals.remove(totals.get(i));
            }
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void finalTribal(){
        System.out.println("Jeff Probst: Welcome to the Final Tribal Council!");
        System.out.println("Jeff Probst: You've made it the farthest anyone can go");
        System.out.println("Jeff Probst: Now you have to convince the jury that you voted out to vote for you to win the million dollars");
        System.out.println("Jeff Probst: Goodluck!");
        System.out.println();
        System.out.println("Jeff Probst: I'll Read The Votes");
        System.out.println();
        ArrayList<Player> ftcvotes = new ArrayList<>();
        HashMap<Player, Integer> votes = new HashMap<>();
        Player vote = new Player();
        for (Player player : jury) {
            int finalVote = r.nextInt(tribes.get(0).getTribePlayers().get(0).getScore()*allianceboard.getSpot(player.getNum(),tribes.get(0).getTribePlayers().get(0).getNum()) + tribes.get(0).getTribePlayers().get(1).getScore()* allianceboard.getSpot(player.getNum(), tribes.get(0).getTribePlayers().get(1).getNum()));
            if (finalVote < tribes.get(0).getTribePlayers().get(0).getScore()*allianceboard.getSpot(player.getNum(), tribes.get(0).getTribePlayers().get(0).getNum())) {
                finalTribalVotes.put(player, tribes.get(0).getTribePlayers().get(0));
                ftvotes.put(tribes.get(0).getTribePlayers().get(0), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(0), Zero)+1));
                vote = tribes.get(0).getTribePlayers().get(0);
                ftcvotes.add(vote);
            } else {
                finalTribalVotes.put(player, tribes.get(0).getTribePlayers().get(1));
                vote = tribes.get(0).getTribePlayers().get(1);
                ftvotes.put(tribes.get(0).getTribePlayers().get(1), Integer.valueOf(ftvotes.getOrDefault(tribes.get(0).getTribePlayers().get(1), Zero)+1));
                ftcvotes.add(vote);
            }
        }
        ArrayList<Player> orderedftc = new ArrayList<>(dramatize(ftcvotes));
        Player first = orderedftc.get(0);
        Player other = orderedftc.get(1);
        int votesLeft = jury.size();
        boolean wonalready = false;
        if(first.equals(other)){
            for(Player player : orderedftc) {
                if (player.getName().equals(first.getName())) {
                    votes.put(player, Integer.valueOf(votes.getOrDefault(player, Zero) + 1));
                    votesLeft--;
                    if (wonalready) {
                    } else if ((votes.get(player) > jury.size() / 2) || (votes.get(other) != null && votes.get(player) > (votes.get(other) + votesLeft))) {
                        System.out.println("The Winner of Survivor " + seasonName + ": " + player);
                        soleSurvivor = player;
                        secondPlace = other;
                        wonalready = true;
                    } else if (votes.get(player) == 1) {
                        System.out.println("One vote: " + player);
                        System.out.println("Jeff Probst: That is 1 vote " + player);
                    } else {
                        System.out.println("One vote: " + player);
                        System.out.println("Jeff Probst: That is " + votes.get(player) + " for " + player);
                    }
                }
            }
        }else {
            for (Player player : orderedftc) {
                if (player.getName().equals(first.getName())) {
                    votes.put(player, Integer.valueOf(votes.getOrDefault(player, Zero) + 1));
                    votesLeft--;
                    if (wonalready) {
                    } else if ((votes.get(player) > jury.size() / 2) || (votes.get(other) != null && votes.get(player) > (votes.get(other) + votesLeft))) {
                        System.out.println("The Winner of Survivor " + seasonName + ": " + player);
                        soleSurvivor = player;
                        secondPlace = other;
                        wonalready = true;
                    } else if (votes.get(player) == 1) {
                        System.out.println("One vote: " + player);
                        System.out.println("Jeff Probst: That is 1 vote " + player);
                    } else {
                        System.out.println("One vote: " + player);
                        System.out.println("Jeff probst: That is " + votes.get(player) + " for " + player);
                    }
                } else {
                    votes.put(player, Integer.valueOf(votes.getOrDefault(player, Zero) + 1));
                    votesLeft--;
                    if (wonalready) {
                    } else if ((votes.get(player) > jury.size() / 2) || (votes.get(first) != null && votes.get(player) > (votes.get(first) + votesLeft))) {
                        System.out.println("The Winner of Survivor " + seasonName + ": " + player);
                        soleSurvivor = player;
                        for(Player finalist : tribes.get(0).getTribePlayers()) {
                            secondPlace = finalist;
                            if(soleSurvivor!=secondPlace){
                                break;
                            }
                        }
                        wonalready = true;
                    } else if (votes.get(player) == 1) {
                        System.out.println("One vote: " + player);
                        System.out.println("Jeff Probst: That is 1 vote " + player);
                    } else {
                        System.out.println("One vote: " + player);
                        System.out.println("Jeff Probst: That is " + votes.get(player) + " votes " + player);
                    }
                }
            }
        }
        if(votes.get(other).equals(votes.get(first))){
                if(other.getScore()>first.getScore()){
                    soleSurvivor = other;
                    secondPlace = first;
                }else if(other.getScore()<first.getScore()){
                    soleSurvivor = first;
                    secondPlace = other;
                }else{
                    if(other.numStats()>first.numStats()){
                        soleSurvivor = other;
                        secondPlace = first;
                    }
                    else if(other.numStats()<first.numStats()){
                        soleSurvivor = first;
                        secondPlace = other;
                    }
                    else{
                        int laststraw = r.nextInt(2);
                        if(laststraw==0){
                            soleSurvivor = other;
                            secondPlace = first;
                        }else{
                            soleSurvivor = first;
                            secondPlace = other;
                        }
                    }
                }
            }
        System.out.println();
        System.out.println(votes.get(soleSurvivor) + "-" + votes.get(secondPlace));
        System.out.println();
        System.out.println("Finalist Scores: ");
        Scores d = new Scores(soleSurvivor.getScore(),soleSurvivor,1);
        totals.add(d);
        Scores p = new Scores(secondPlace.getScore(),secondPlace,2);
        totals.add(p);
        System.out.println(tribes.get(0).getTribePlayers().get(0) + " had " + tribes.get(0).getTribePlayers().get(0).getScore() + " points!");
        System.out.println(tribes.get(0).getTribePlayers().get(1) + " had " + tribes.get(0).getTribePlayers().get(1).getScore() + " points!");
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void finalTribalVotes(){
        System.out.println("Jeff Probst: Final Tribal Votes:");
        System.out.println(finalTribalVotes);
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void votability(){
        System.out.println("Player votability");
        for(Player player: playerStorage){
            System.out.println(player + ": " + player.getVotability());
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void placements(){
        System.out.println("Placements:");
        System.out.println("Sole Survivor: " + soleSurvivor + " -> " + ftvotes.get(soleSurvivor) +" votes to win.");
        System.out.println("2: " + secondPlace + " -> " + ftvotes.getOrDefault(secondPlace, Zero) + " votes to win.");
        if(!twoOrThree) {
            System.out.println("3: " + thirdPlace + " -> " + ftvotes.getOrDefault(thirdPlace, Zero) + " votes to win.");
        }
        int j;
        for(int i = eliminated.size();i>0;i--){
            j= eliminated.size()+3-i;
            if(twoOrThree) {
                System.out.println(j + ": " + eliminated.get(i - 1) + elimvotes.get(eliminated.get(i-1)));
            }else{
                System.out.println(j+1 + ": " + eliminated.get(i - 1) + elimvotes.get(eliminated.get(i-1)));
            }
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void allianceDissolve(Alliance a){
        a.removeAll();
        System.out.println("The " + a.getName() + " Alliance dissolved.");
        alliances.remove(a);
    }

    public void journey(){
        System.out.println("A boat has arrived at each camp. Each tribe must pick someone to go on a journey.");
        HashMap<Player,Boolean> journeying = new HashMap<>();
        for(Tribe tribe: tribes){
            Player chosen = tribe.getTribePlayers().get(r.nextInt(tribe.getTribePlayers().size()));
            System.out.println(chosen + " has been selected for the journey from the " + tribe.getColor() + tribe.tribeName() + ANSI_RESET + " tribe.");
            journeying.put(chosen, Boolean.valueOf(false));
        }
        System.out.println("Welcome to Shipwheel Island: Where you may earn an advantage or lose your vote.");
        System.out.println("Each player will be given a choice to risk or protect your vote. If all protect, nothing happens.");
        System.out.println("If all players risk, all players lose their vote. If one or two of you risk, you will receive an advantage");
        System.out.println("Let the decisions begin!");
        for(Player p: journeying.keySet()){
            if(r.nextInt(10)>4){
                System.out.println(p + ": I am risking my vote.");
                journeying.replace(p,Boolean.valueOf(true));
            }else{
                System.out.println(p + ": I am protecting my vote.");
            }
        }
        if (journeying.containsValue(Boolean.valueOf(false))){
            for (Player p: journeying.keySet()){
                if(journeying.get(p)) {
                    System.out.println(p + " risked their vote and has been rewarded.");
                    if(!advantages && !Idol) {
                        p.setScore(5);
                    }else if(!advantages){
                        idolFind(p,true);
                    }else {
                        advantageFind(p, true);
                    }
                }
            }
        }else if (!journeying.containsValue(Boolean.valueOf(true))){
            System.out.println("All players protected their votes.");
        }
        else{
            System.out.println("All players risked their vote, and thus will lose their vote.");
            for(Player p:journeying.keySet()){
                p.setCanVote(false);
            }
            losesVote.addAll(journeying.keySet());
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void immunityWins(){
        System.out.println("Immunity Wins: ");
        int max = -1;
        ArrayList<Player> base = new ArrayList<>();
        for(Player player: playerStorage){
            if(player.getImmWins()>0){
                base.add(player);
                if(Integer.valueOf(max)<player.getImmWins()){
                    max = player.getImmWins();
                }
            }
        }while(Integer.valueOf(max)>0) {
            for (Player player : playerStorage) {
                if (player.getImmWins() == max) {
                    System.out.println(player.getImmWins() + ": " + player);
                }
            }
            max--;
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void rewardWins(){
        System.out.println("Reward Wins: ");
        int max = -1;
        ArrayList<Player> base = new ArrayList<>();
        for(Player player: playerStorage){
            if(player.getRewardWins()>0){
                base.add(player);
                if(Integer.valueOf(max)<player.getRewardWins()){
                    max = player.getRewardWins();
                }
            }
        }while(Integer.valueOf(max)>0) {
            for (Player player : playerStorage) {
                if (player.getRewardWins() == max) {
                    System.out.println(player.getRewardWins() + ": " + player);
                }
            }
            max--;
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void challengeWins(){
        System.out.println("Challenge Wins: ");
        int max = -1;
        ArrayList<Player> base = new ArrayList<>();
        for(Player player: playerStorage){
            if(player.challengeWins()>0){
                base.add(player);
                if(Integer.valueOf(max)<player.challengeWins()){
                    max = player.challengeWins();
                }
            }
        }while(Integer.valueOf(max)>0) {
            for (Player player : playerStorage) {
                if (player.challengeWins() == max) {
                    System.out.println(player.challengeWins() + ": " + player);
                }
            }
            max--;
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void advantagesFound() {
        System.out.println("Advantages Found in Game:");
        for (Player p : playerStorage) {
            for (String adv : p.getAdvFound()) {
                System.out.println(p + ": " + adv);
            }
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
        System.out.println("Number of Advantages Found: ");
        int max = -1;
        ArrayList<Player> base = new ArrayList<>();
        for (Player player : playerStorage) {
            if (player.cntAdvFound() > 0) {
                base.add(player);
                if (Integer.valueOf(max) < player.cntAdvFound()) {
                    max = player.cntAdvFound();
                }
            }
        }
        while (Integer.valueOf(max) > 0) {
            for (Player player : base) {
                if (player.cntAdvFound() == max) {
                    System.out.println(player.cntAdvFound() + ": " + player);
                }
            }
            max--;
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void idolsFound(){
        System.out.println("Idols Found: ");
        int max = -1;
        ArrayList<Player> base = new ArrayList<>();
        for(Player player: playerStorage){
            if(player.getIdolCount()>0){
                base.add(player);
                if(Integer.valueOf(max)<player.getIdolCount()){
                    max = player.getIdolCount();
                }
            }
        }while(Integer.valueOf(max)>0) {
            for (Player player : base) {
                if (player.getIdolCount() == max) {
                    System.out.println(player.getIdolCount() + ": " + player);
                }
            }
            max--;
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void playerAttributes(){
        System.out.println("Player Mindset and Attributes: ");
        for (Player player : playerStorage) {
            System.out.println(player.fullstats());
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void allianceSummary(){
        System.out.println("Alliance Summary:");
        if (alliances.size() == 0) {
            System.out.println("There are no alliances.");
        }else{
            ArrayList<Alliance> copy = new ArrayList<>(alliances);
            for(Alliance a: copy){
                System.out.println(a.toString());
                if(a.getMembers().size()<=1){
                    allianceDissolve(a);
                }else if(a.getMembers().size()==players.size()) {
                    allianceDissolve(a);
                }else{
                    if(losingTribe.getTribePlayers().contains(a.getMembers().get(0))) {
                        a.strategize(losingTribe.getTribePlayers());
                    }
                }
                System.out.println("-------------------------");
            }
        }
        Alliance comp = null;
        Alliance toss = null;
        for(Alliance a: alliances){
            if(comp != null && a.getMembers().containsAll(comp.getMembers()) && comp.getMembers().containsAll(a.getMembers())){
                toss = a;
            }
            comp = a;
        }
        alliances.remove(toss);
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }

    public void idolAndAdvSummary(){
        for(Tribe tribe: tribes){
            boolean none = true;
            System.out.println("Idol and Advantage Summary for the " + tribe.getColor() + tribe.tribeName() + ANSI_RESET + " Tribe: ");
            for(Player p: tribe.getTribePlayers()){
                if(p.getIdolsInPossesion()>0){
                    System.out.println(p + " has " + p.getIdolsInPossesion() + " Idol(s)");
                    none = false;
                }
                for(int i = 0; i<12;i++){
                    if(p.getAdvantages(i) > 0){
                        System.out.println(p + " has " + p.getAdvantages(i) + " " + p.getAdvName(i));
                        none = false;
                    }
                }
            }
            if(none){
                System.out.println("There are no Idols or Advantages on the " + tribe.getColor() + tribe.tribeName() + ANSI_RESET + " Tribe");
            }
            System.out.println();
        }
        System.out.println();
        System.out.println("-------------------------");
        System.out.println();
    }
}