import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class Player {
    private String name;
    private int num;
    private String mindset;
    private ArrayList<String> mindsets = new ArrayList<>();
    private boolean immune;
    private int immWins;
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

    public int getVotesAgainst(){
        return votesAgainst;
    }

    public void setAdvantages(int type,int amt) {
        advantages.replace(type,amt);
    }

    public int getVotability() {
        return votability;
    }

    public void setVotability(int votability) {
        this.votability = votability;
    }

    public int getIdolCount() {
        return idolCount;
    }

    public void setIdolCount(int idolCount) {
        this.idolCount = idolCount;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
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

    public void setImmune(boolean immune) {
        this.immune = immune;
    }

    public int getImmWins() {
        return immWins;
    }

    public void setImmWins(int immWins) {
        this.immWins = immWins;
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

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public int getNum() {
        return num;
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

    public void setNum(int num) {
        this.num = num;
    }

    public Player(String name, int num) {
        Random r = new Random();
        this.name = name;
        this.immune = false;
        this.immWins = 0;
        this.score = 0;
        this.mindset = setMindset();
        this.strategy = startStrategy();
        this.strength = startStrength();
        this.balance = startBalance();
        this.idolCount = 0;
        this.votability = startVotability();
        this.num = num;
        for(int i = 0;i<8;i++){
            advantages.put(i,0);
        }
    }

    @Override
    public String toString() {
        return name;
    }

    public Player(){
    }

    public Player(String name){
        name = this.name;
    }

    public int numStats(){
        return balance + strength +strategy;
    }

    public String fullstats(){
        return name + ": Mindset " + mindset +", Balance " + balance + ", Strength  " + strength + ", Strategy " + strategy;
    }
}
