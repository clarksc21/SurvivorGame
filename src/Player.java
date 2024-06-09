import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

public class Player {
    private String name;
    private static final String ANSI_WHITE = "\u001B[0m";
    private int num;
    private String mindset;
    private ArrayList<String> mindsets = new ArrayList<>();
    private boolean immune;
    private int immWins;
    private int morale;
    private String lastTribeColor;
    private int threatlvl;
    private int age;
    private int trustworthiness;
    private int deceitfulness;
    private int rewardWins;
    private List<Alliance> alliances;
    private boolean canVote;
    private int score;
    private Random r =  new Random();
    private int strength;
    private int strategy;
    private int balance;
    private int idolCount;
    private int votability;
    private HashMap<Integer, Integer> advantages = new HashMap<>();
    private int votesAgainst = 0;

    public int getAdvantages(int type) {
        return advantages.get(type);
    }

    public void setVotesAgainst(){
        votesAgainst++;
    }

    public ArrayList<Player> getTargets(){
        ArrayList<Player> t = new ArrayList<>();
        for(Alliance a:alliances){
            if(!t.contains(a.getTarget())) {
                t.add(a.getTarget());
            }
        }
        return t;
    }

    public int getVotesAgainst(){
        return votesAgainst;
    }

    public String getLastTribeColor(){
        return lastTribeColor;
    }

    public void setLastTribeColor(String ltc){
        this.lastTribeColor = ltc;
    }

    public void setAdvantages(int type,int amt) {
        advantages.replace(type,amt);
    }

    public int getVotability() {
        return votability + strength + strategy + deceitfulness - trustworthiness + threatlvl;
    }

    public void setVotability(int votability) {
        if(votability<0) {
            this.trustworthiness -= votability;
        }else if(votability>0){
            this.deceitfulness += votability;
        }
    }

    public int getIdolCount() {
        return idolCount;
    }

    public void setIdolCount(int idolCount) {
        this.idolCount += idolCount;
    }

    public int getScore() {
        return score;
    }

    public int getTrustworthiness() {
        return trustworthiness;
    }

    public void setTrustworthiness(int trustworthiness) {
        this.trustworthiness = trustworthiness;
    }

    public int getDeceitfulness() {
        return deceitfulness;
    }

    public void setDeceitfulness(int deceitfulness) {
        this.deceitfulness = deceitfulness;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setScore(int score) {
        this.score += score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isImmune() {
        return immune;
    }

    public void setRewardWins(){
        this.rewardWins++;
    }

    public int getRewardWins(){
        return rewardWins;
    }

    public int challengeWins(){
        return immWins + rewardWins;
    }

    public void setImmune(boolean immune) {
        this.immune = immune;
    }

    public int getImmWins() {
        return immWins;
    }

    public void setImmWins(int immWins) {
        this.immWins += immWins;
    }

    public int getStrength() {
        return strength;
    }

    public void setStrength(int strength) {
        this.strength = strength;
    }

    public int getStrategy() {
        return strategy;
    }

    public int balnstrength(){
        return balance + strength;
    }

    public int balnstrat(){
        return balance + strategy;
    }

    public int strengthnstrat(){
        return strength + strategy;
    }

    public void setStrategy(int strategy) {
        this.strategy = strategy;
    }

    public int getBalance() {
        return balance;
    }

    public int startBalance(){
        if(getMindset().equals("Villain")){
            return r.nextInt(10)+1;
        }else if(getMindset().equals("Hero")){
            return r.nextInt(10)+1;
        }else if(getMindset().equals("Challenge Beast")){
            return r.nextInt(4)+7;
        }else if(getMindset().equals("GOAT")){
            return r.nextInt(4)+1;
        }else if(getMindset().equals("Elusive")){
            return r.nextInt(3)+8;
        }else if(getMindset().equals("ENERGY")){
            return r.nextInt(5)+3;
        }else if(getMindset().equals("Amiable")){
            return r.nextInt(10)+1;
        }else{
            return r.nextInt(10)+1;
        }
    }

    public int startStrength(){
        if(getMindset().equals("Villain")){
            return r.nextInt(10)+1;
        }else if(getMindset().equals("Hero")){
            return r.nextInt(10)+1;
        }else if(getMindset().equals("Challenge Beast")){
            return r.nextInt(4)+7;
        }else if(getMindset().equals("GOAT")){
            return r.nextInt(4)+1;
        }else if(getMindset().equals("Elusive")){
            return r.nextInt(3)+5;
        }else if(getMindset().equals("ENERGY")){
            return r.nextInt(5)+3;
        }else if(getMindset().equals("Amiable")){
            return r.nextInt(10)+1;
        }else{
            return r.nextInt(10)+1;
        }
    }

    public int startStrategy(){
        if(getMindset().equals("Villain")){
            return r.nextInt(4)+7;
        }else if(getMindset().equals("Hero")){
            return r.nextInt(4)+7;
        }else if(getMindset().equals("Challenge Beast")){
            return r.nextInt(4)+3;
        }else if(getMindset().equals("GOAT")){
            return r.nextInt(4)+1;
        }else if(getMindset().equals("Elusive")){
            return r.nextInt(5)+2;
        }else if(getMindset().equals("ENERGY")){
            return r.nextInt(5)+2;
        }else if(getMindset().equals("Amiable")){
            return r.nextInt(10)+1;
        }else{
            return 10;
        }
    }

    public int startVotability(){
        if(getMindset().equals("Villain")){
            return r.nextInt(3)+1;
        }else if(getMindset().equals("Hero")){
            return r.nextInt(3)+1;
        }else if(getMindset().equals("Challenge Beast")){
            return r.nextInt(4)+1;
        }else if(getMindset().equals("GOAT")){
            return r.nextInt(4)+1;
        }else if(getMindset().equals("Elusive")){
            return r.nextInt(5)+1;
        }else if(getMindset().equals("ENERGY")){
            return r.nextInt(3)+2;
        }else if(getMindset().equals("Amiable")){
            return r.nextInt(2)+1;
        }else{
            return r.nextInt(3)+3;
        }
    }

    public int startTrustworthiness(){
        if(getMindset().equals("Villain")){
            return r.nextInt(7)+1;
        }else if(getMindset().equals("Hero")){
            return r.nextInt(7)+4;
        }else if(getMindset().equals("Challenge Beast")){
            return r.nextInt(5)+1;
        }else if(getMindset().equals("GOAT")){
            return r.nextInt(9)+3;
        }else if(getMindset().equals("Elusive")){
            return r.nextInt(5)+1;
        }else if(getMindset().equals("ENERGY")){
            return r.nextInt(5)+2;
        }else if(getMindset().equals("Amiable")){
            return r.nextInt(9)+1;
        }else{
            return r.nextInt(3)+1;
        }
    }

    public int startDeceitfulness(){
        if(getMindset().equals("Villain")){
            return r.nextInt(7)+4;
        }else if(getMindset().equals("Hero")){
            return r.nextInt(4)+1;
        }else if(getMindset().equals("Challenge Beast")){
            return r.nextInt(5)+1;
        }else if(getMindset().equals("GOAT")){
            return r.nextInt(4)+1;
        }else if(getMindset().equals("Elusive")){
            return r.nextInt(6)+3;
        }else if(getMindset().equals("ENERGY")){
            return r.nextInt(5)+2;
        }else if(getMindset().equals("Amiable")){
            return r.nextInt(4)+1;
        }else{
            return r.nextInt(10)+5;
        }
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getNum() {
        return num;
    }

    public String getAdvName(int i){
        if(i==1){
            return "Extra Vote";
        }else if(i==2){
            return "Vote Blocker";
        }else if(i==3){
            return "Steal a Vote";
        }else if(i==4){
            return "Idol Blocker";
        }else if(i==5){
            return "Choose your Champion Advantage";
        }else if(i==6){
            return "Legacy Advantage";
        }else if(i==7){
            return "Force Rocks Advantage";
        }else if(i==8){
            return "Vote Swap Advantage";
        }else if(i==9){
            return "Safety without Power Advantage";
        }else{
            return "";
        }
    }

    public String getMindset(){return mindset;}

    public String setMindset(){
        Random r = new Random();
        mindsets.add("Villain");
        mindsets.add("Hero");
        mindsets.add("Challenge Beast");
        mindsets.add("GOAT");
        mindsets.add("Elusive");
        mindsets.add("ENERGY");
        mindsets.add("Amiable");
        mindsets.add("LuminsLiar");
        return mindsets.get(r.nextInt(mindsets.size()));
    }

    public boolean CanVote() {
        return canVote;
    }

    public void setCanVote(boolean canVote) {
        this.canVote = canVote;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getMorale() {
        return morale;
    }

    public void setMorale(int morale) {
        this.morale = morale;
    }

    public List<Alliance> getAlliances(){
        return alliances;
    }

    public void addAlliance(Alliance alliance) {
        if (!alliances.contains(alliance)) {
            alliances.add(alliance);
            alliance.addMember(this);
        }
    }

    public void leaveAlliance(Alliance alliance) {
        if (alliances.contains(alliance)) {
            alliances.remove(alliance);
            alliance.removeMember(this);
        }
    }

    public void setThreatlvl(int tl){
        this.threatlvl += tl;
    }

    public boolean isInAllianceWith(Player player) {
        for (Alliance alliance : alliances) {
            if (alliance.getMembers().contains(player)) {
                return true;
            }
        }
        return false;
    }

    public Player(String name, int num) {
        Random r = new Random();
        this.canVote = true;
        this.name = name;
        this.immune = false;
        this.immWins = 0;
        this.score = 0;
        this.alliances = new ArrayList<>();
        this.age = r.nextInt(50) +18;
        this.morale = 0;
        this.mindset = setMindset();
        this.strategy = startStrategy();
        this.strength = startStrength();
        this.balance = startBalance();
        this.trustworthiness = startTrustworthiness();
        this.deceitfulness = startDeceitfulness();
        this.idolCount = 0;
        this.lastTribeColor = ANSI_WHITE;
        this.threatlvl = 1;
        this.votability = startVotability();
        this.num = num;
        for(int i = 0;i<10;i++){
            advantages.put(i,0);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public Player(){
    }

    public int sumAdv(){
        int sum = 0;
        for (int i = 0; i < 10; i++) {
            sum+=advantages.get(i);
        }
        return sum;
    }

    public Player(String name){
        name = this.name;
    }

    public int numStats(){
        return balance + strength +strategy;
    }

    public String fullstats(){
        return name + ": Mindset " + mindset +", Balance " + balance + ", Strength  " + strength + ", Strategy " + strategy + ", Morale " + morale + ", Trustworthiness " + trustworthiness + ", Deceitfulness " + deceitfulness + ", Threat Level " + threatlvl;
    }
}
