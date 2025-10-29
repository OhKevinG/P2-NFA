package fa.nfa;

import fa.State;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class NFAState extends State {

    private Map<Character, Set<NFAState>> transitions;

    // constructor
    public NFAState(String name) {
        super(name);
        transitions = new HashMap<>();
    }

    /**
     * returns the Set of NFA states that the character transitions to
     * @param symb
     * @return Set<NFAState> is the set of states mapped by symb
     */
    public Set<NFAState> toStates(char symb) {
        if (transitions.get(symb) == null) return Set.of();
        return transitions.get(symb);
    }

    /**
     * Adds a transition from this state to the state with the given name on the given symbol
     * @param name name of destination state
     * @param transitionChar symbol of transition
     */
    public void addTransition(Set<NFAState> states, char transitionChar) {
        transitions.put(transitionChar, states);
    }


    @Override
    public boolean equals(Object obj) {
        if (obj instanceof NFAState) {
            NFAState state = (NFAState) obj;
            return state.getName().equals(getName());
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        return getName().hashCode();
    }
}
