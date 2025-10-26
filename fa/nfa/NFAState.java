package fa.nfa;

import fa.State;

import java.util.Map;
import java.util.Set;

public class NFAState extends State {
    private Map<Character, Set<NFAState>> transitions;

    /**
     * REWRITE THIS LATER: toStates returns the Set of NFA states that the character transitions to
     * @param symb
     * @return
     */
    public Set<NFAState> toStates(char symb) {
        return null;
    }
}
