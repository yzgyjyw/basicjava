package state;

public class SpiteState implements VoteState {

    @Override
    public void vote(String user, String candidate, VoteManager manager) {
        //恶意刷票,取消该用户的投票
        manager.getVoteCountMap().remove(user);
        manager.getVoteMap().remove(user);
        System.out.println("恶意刷票,取消投票资格");
    }
}
