package state;

public interface VoteState  {
    void vote(String user, String candidate, VoteManager manager);
}
