import java.util.*;

public class Alliance{
    private String name;
    private static final String ANSI_RESET = "\u001B[0m";
    private ArrayList<Player> members;
    private Player target;
    private Random r = new Random();


    public Player getTarget() {
        return target;
    }

    public Alliance(String name){
        this.name = name;
        this.members = new ArrayList<>();
        this.target = null;
    }

    public String getName(){
        return name;
    }

    public ArrayList<Player> getMembers(){
        return members;
    }

    public void addMember(Player player){
        if(!members.contains(player)){
            members.add(player);
            player.addAlliance(this);
        }
    }

    public void removeMember(Player player) {
        members.remove(player);
        player.getAlliances().remove(this);
    }

    public int avgVotability(){
        int sum = 0;
        for(Player p: members){
            sum += p.getVotability();
        }
        return sum/members.size();
    }

    public int minVotability(){
        int min = 10000;
        for(Player p:members){
            if(min<p.getVotability()){
                min = p.getVotability();
            }
        }
        return min;
    }

    public void strategize(List<Player> players){
        // Identify threats outside the alliance
        List<Player> threats = new ArrayList<>();
        for (Player player : players) {
            if (!members.contains(player) && player.getVotability() > avgVotability() && !player.isImmune()) {
                threats.add(player);
            }
        }

        threats.sort((p1, p2) -> Integer.compare(
                p2.getStrength() + p2.getStrategy() + p2.getVotability(),
                p1.getStrength() + p1.getStrategy() + p1.getVotability()));

        Player mainThreat = !threats.isEmpty() ? threats.get(0) : null;

        // Decide on a voting strategy
        if (mainThreat != null) {
            System.out.println(name + " alliance is targeting " + mainThreat.getName() + " as the main threat.");
            target = mainThreat;
        }else{
            List<Player> goats = new ArrayList<>();
            for (Player player : players) {
                if (!members.contains(player) && player.getVotability() < minVotability() && !player.isImmune()) {
                    goats.add(player);
                }
            }
            goats.sort((p1, p2) -> Integer.compare(
                    p2.getStrength() + p2.getStrategy() + p2.getVotability(),
                    p1.getStrength() + p1.getStrategy() + p1.getVotability()));
            Collections.reverse(goats); //fixing
            Player biggestGoat = !goats.isEmpty() ? goats.get(0) : null;
            if(biggestGoat!=null) {
                System.out.println(name + " alliance is targeting " + biggestGoat.getName() + " as the biggest goat.");
                target = biggestGoat;
            }
        }

        // Internal alliance dynamics
        double internal = r.nextDouble();
        if(internal<.01) {
            Player weakestMember = members.stream().min(Comparator.comparingInt(Player::numStats)).orElse(null);
            if (weakestMember != null && members.size() > 2) {
                System.out.println("Alliance " + name + " is considering voting out weakest member: " + weakestMember.getName());
                // Evaluate trustworthiness
                for (Player member : members) {
                    if (member.getTrustworthiness() < weakestMember.getTrustworthiness()) {
                        System.out.println("Alliance " + name + " may keep " + weakestMember.getName() + " due to trustworthiness.");
                        weakestMember = null;
                        break;
                    }
                }
            }

            if (weakestMember != null && members.size()>2) {
                System.out.println("Alliance " + name + " decides to remove their weakest member: " + weakestMember.getName());
                members.remove(weakestMember);
            }
        }else if(internal<.03) {
            Player strongestMember = members.stream().max(Comparator.comparingInt(Player::numStats)).orElse(null);
            if (strongestMember != null && members.size() > 2) {
                System.out.println("Alliance " + name + " is considering voting out its strongest member: " + strongestMember.getName());
                // Evaluate trustworthiness
                for (Player member : members) {
                    if (member.getTrustworthiness() < strongestMember.getTrustworthiness()) {
                        System.out.println("Alliance " + name + " may keep " + strongestMember.getName() + " due to trustworthiness.");
                        strongestMember = null;
                        break;
                    }
                }
            }

            if (strongestMember != null && members.size()>2) {
                System.out.println("Alliance " + name + " decides to remove their strongest member: " + strongestMember.getName());
                System.out.println("They are now the target of a blindside!");
                members.remove(strongestMember);
                target = strongestMember;
            }
        }
    }

    public Tribe majorityTribe(){
        Tribe majority = null;
        for(Player p: members){
            majority = p.getTribe();
        }
        return majority;
    }

    public void removeAll(){
        ArrayList<Player> copy = new ArrayList<>();
        copy.addAll(members);
        for(Player p: copy){
            members.remove(p);
        }
    }

    public Alliance(){
    }//

    public StringBuilder memberPrint(){
        StringBuilder m = new StringBuilder();
        for(Player p: members){
            m.append(p.getLastTribeColor() + p.getName() + ANSI_RESET).append(" | ");
        }
        return m;
    }

    @Override
    public String toString(){
        return name + "- " + memberPrint();
    }
}
