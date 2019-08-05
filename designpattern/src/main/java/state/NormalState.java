package state;

public class NormalState implements VoteState {

    @Override
    public void vote(String user, String candidate, VoteManager manager) {
        manager.getVoteCountMap().merge(user, 1, (oldValue, value) -> oldValue+value);
        manager.getVoteMap().merge(candidate, 1, (oldValue, value) -> oldValue+value);
    }
}
