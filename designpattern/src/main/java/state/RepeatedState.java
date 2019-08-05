package state;

public class RepeatedState implements VoteState {

    @Override
    public void vote(String user, String candidate, VoteManager manager) {
        System.out.println("重复投票");
        manager.getVoteCountMap().merge(user, 1, (oldValue, value) -> oldValue+value);
        //什么儿都不处理,直接丢掉
    }
}
