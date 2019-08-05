package state;

import com.google.common.collect.Maps;

import java.util.Map;

public class VoteManager {

    private VoteState voteState = null;

    private Map<String, Integer> voteMap = Maps.newHashMap();

    private Map<String, Integer> voteCountMap = Maps.newHashMap();

    public Map<String, Integer> getVoteMap() {
        return voteMap;
    }

    public Map<String, Integer> getVoteCountMap() {
        return voteCountMap;
    }

    public void vote(String user, String candidate) {
        Integer oldCount = voteCountMap.getOrDefault(user, 0);
        if (oldCount <= 0) {
            voteState = new NormalState();
        } else if (oldCount < 5) {
            voteState = new RepeatedState();
        } else {
            voteState = new SpiteState();
        }

        voteState.vote(user,candidate,this);
    }

}
