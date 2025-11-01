package fa.nfa;

import fa.State;

import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
* Creates a state for an NFA.
* The state includes outgoing transitions and methods for interacting with them.
* @author evanlauer, kevingutierrez
*/
public class NFAState extends State {

    private Map<Character, Set<NFAState>> transitions;

    /**
     * The NFAState contructor requires a name for the state be provided. Then it calls the parent State constructor
     * and instantiates the map δ (transitions).
     * @param name
     */
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
        Set<NFAState> oldStates = new LinkedHashSet<>();
        Set<NFAState> newStates = new LinkedHashSet<>();
        if (transitions.containsKey(transitionChar)) {
            oldStates = transitions.get(transitionChar);
        }
        newStates.addAll(oldStates);
        newStates.addAll(states);
        transitions.put(transitionChar, newStates);
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
