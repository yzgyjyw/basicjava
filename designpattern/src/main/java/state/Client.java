package state;

public class Client {
    public static void main(String[] args) {
        VoteManager manager = new VoteManager();
        for (int i = 0; i < 10; i++) {
            manager.vote("A", "B");
            System.out.println("Candidate B's votes count\t" + manager.getVoteMap().get("B"));
        }
    }
}
